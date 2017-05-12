package net.minthe.ac;

import net.minthe.ac.math.LinearRegression;
import net.minthe.ac.math.Mat;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Michael Kelley on 4/22/2017.
 */
public class AlienTester {
    public static void main(String[] args) {
        AlienReader ar = new AlienReader("C:\\Users\\purti\\ClionProjects\\aliencounter\\numbers.txt");
        double min = -100;
        double max = 100;

        HashMap<Integer, Mat> kv = ar.values;

        Random r = new Random();

        double bestr2 = 0;
        while (true) {
            Mat leftReduce = null;
            Mat rightReduce = null;
            double[] xs = new double[ar.values.size()];
            double[] ys = new double[ar.values.size()];
            for (int i = 1; i <= kv.size(); i++) {
                Mat alienCount = kv.get(i);
                if (leftReduce == null) {
                    ArrayList<Mat> leftMults = new ArrayList<>();
                    ArrayList<Mat> rightMults = new ArrayList<>();
                    while (!alienCount.isUnary()) {
                        if (r.nextBoolean()) {
                            Mat rMult = Mat.randRMult(alienCount, min, max);
                            rightMults.add(rMult);
                            alienCount = alienCount.multiply(rMult);
                        } else {
                            Mat lMult = Mat.randLMult(alienCount, min, max);
                            leftMults.add(lMult);
                            alienCount = lMult.multiply(alienCount);
                        }
                    }
                    rightReduce = rightMults.get(0);
                    for (int j = 1; j < rightMults.size(); j++) {
                        rightReduce = rightReduce.multiply(rightMults.get(i));
                    }
                    leftReduce = leftMults.get(0);
                    for (int j = 1; j < leftMults.size(); j++) {
                        leftReduce = leftMults.get(i).multiply(leftReduce);
                    }
                } else {
                    alienCount = leftReduce.multiply(alienCount).multiply(rightReduce);
                }
                xs[i - 1] = i;
                ys[i - 1] = alienCount.values[0][0];
            }
            OLSMultipleLinearRegression model = LinearRegression.polyReg(xs, ys, 1);
            double r2 = model.calculateRSquared();
            if (r2 > bestr2) {
                double[] beta = model.estimateRegressionParameters();
                System.out.print("beta: ");
                for (double betaN : beta) {
                    System.out.print(betaN + " ");
                }
                System.out.println();
                bestr2 = r2;
                System.out.println(leftReduce);
                System.out.println(rightReduce);
                System.out.println(r2);
                System.out.println("================================");
            }
        }
    }
}
