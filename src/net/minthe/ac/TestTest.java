package net.minthe.ac;

import net.minthe.ac.math.FlatMat;
import net.minthe.ac.math.Mat;
import org.ejml.simple.SimpleMatrix;

import java.util.Random;

/**
 * Created by Michael Kelley on 5/9/sz17.
 */
public class TestTest {
    public static void main(String[] args) {
        int sz = 20;
        SimpleMatrix sm = SimpleMatrix.random(sz, sz,-10,10,new Random());
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            sm.mult(sm);
        }
        long end = System.currentTimeMillis();
        System.out.println("sm: " + (end-start));

        Mat rand = Mat.randMat(sz,sz,-10,10);
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            rand = rand.multiply(rand);

        }
        end = System.currentTimeMillis();
        System.out.println("mat: " + (end-start));

        FlatMat fm = FlatMat.randFlatMat(sz, sz, -10, 10);
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            fm = fm.multiply(fm);

        }
        end = System.currentTimeMillis();
        System.out.println("fm: " + (end-start));
    }
}
