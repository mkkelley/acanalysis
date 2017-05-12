package net.minthe.ac;

import net.minthe.ac.math.Mat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Michael Kelley on 4/22/2017.
 */
public class AlienReader {
    public HashMap<Integer, Mat> values;
    public AlienReader() {}
    public AlienReader(String filename) {
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

            Double[][] mat = new Double[mat_rows][mat_cols];

            for (int i = 0; i < mat_rows; i++) {
                for (int j = 0; j < mat_cols; j++) {
                    mat[i][j] = (double) sc.nextInt();
                }
            }
            values.put(n, new Mat(mat));
        }
    }
}
