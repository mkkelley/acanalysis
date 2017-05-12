package net.minthe.ac.jen;

import org.ejml.simple.SimpleMatrix;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Michael Kelley on 5/9/2017.
 */
public class AlienReaderSM {
    public HashMap<Integer, SimpleMatrix> values;
    public AlienReaderSM(String filename) {
        Scanner sc = null;
        try {
            sc = new Scanner(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            System.out.println("File" + filename + " not found.");
            System.exit(1);
        }

        values = new HashMap<>();

        int mat_rows = sc.nextInt();
        int mat_cols = sc.nextInt();


        while (sc.hasNextInt()) {

            int n = sc.nextInt();

            double[][] mat = new double[mat_rows][mat_cols];

            for (int i = 0; i < mat_rows; i++) {
                for (int j = 0; j < mat_cols; j++) {
                    mat[i][j] = (double) sc.nextInt();
                }
            }
            values.put(n, new SimpleMatrix(mat));
        }
    }
}
