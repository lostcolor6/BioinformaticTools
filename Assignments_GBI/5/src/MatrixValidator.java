
// Assignment 5 - Task 4
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Methods to read and validate a distance matrix from a .txt file.
 */
public class MatrixValidator {
    /**
     * Adjust or add your names here!
     */
    private static final List<String> studentNames = List.of(
            "Marius Maier", "Polina Novikova");






    /**
     * 4.1.1: Method to read a distance matrix from a .txt file.
     *
     * @param filePath the path to the input file
     * @return double 2D array representing the distance matrix
     * @throws IOException if error occurs while reading the file
     *
     *
     */
    public static double[][] readMatrixFromFile(String filePath) throws IOException {
        //list to hold rows of the matrix as they are read
        List<double[]> matrixList = new ArrayList<>();

        //bufferedReader to read the file line by line
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        //read each line of the file
        while ((line = reader.readLine()) != null) {
            //split the line by whitespace to get the individual elements
            String[] parts = line.split("\\s+");
            double[] row = new double[parts.length];

            //convert each element to double and store in the row array
            for (int i = 0; i < parts.length; i++) {
                row[i] = Double.parseDouble(parts[i]);
            }
            //adding row to the matrix list
            matrixList.add(row);
        }
        reader.close();

        //convert the list of rows into a 2D array
        double[][] matrix = new double[matrixList.size()][];
        for (int i = 0; i < matrixList.size(); i++) {
            matrix[i] = matrixList.get(i);
        }
        return matrix;
    }





    /**
     * 4.1.2: Method to validate a distance matrix from a .txt file.
     * @param matrix the distance matrix
     * @return true if the matrix is valid distance matrix / false if not
     *
     */
    public static boolean isValidDistanceMatrix(double[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            //matrix is not square
            if (matrix[i].length != n) return false;
            //diagonal elements are not zero
            if (matrix[i][i] != 0) return false;

            for (int j = 0; j < n; j++) {
                //matrix is not symmetric
                if (matrix[i][j] != matrix[j][i]) return false;
            }
        }
        return true;
    }




    /**
     * 4.2: Method to evaluate the distance matrix for ultrametricity.
     * @param matrix dist matrix
     * @return an object array with boolean (if its ultrametric/ number of validTriplets/ nr. of invalid ones)
     */
    public static Object[] isUltrametric(double[][] matrix) {
        //number of rows in the matrix
        int n = matrix.length;
        int validTriplets = 0;
        int invalidTriplets = 0;

        //check over all combinations of i, j, k
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    double d_ij = matrix[i][j];
                    double d_ik = matrix[i][k];
                    double d_jk = matrix[j][k];

                    //check the 3PC for ultrametricity

                        if (d_ij > Math.max(d_ik, d_jk) || d_ik > Math.max(d_ij, d_jk) || d_jk > Math.max(d_ij, d_ik)) {
                            invalidTriplets++;
                        } else {
                            validTriplets++;
                        }
                    }
                }
            }
            boolean isUltrametric = (invalidTriplets == 0);
            return new Object[]{isUltrametric, validTriplets, invalidTriplets};
        }




    /**
     * 4.3: Method to evaluate the distance matrix for additivity.
     *
     * @param matrix the distance matrix to check
     * @return an object array with boolean (if its treemetric/ number of valid quartets/ nr. of invalid ones)
     *
     */
    public static Object[] isTreeMetric(double[][] matrix) {
        //number of rows in the matrix
        int n = matrix.length;
        int validQuartets = 0;
        int invalidQuartets = 0;

        //check over all combinations of i, j, k
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        double d_ij = matrix[i][j];
                        double d_ik = matrix[i][k];
                        double d_il = matrix[i][l];
                        double d_jk = matrix[j][k];
                        double d_jl = matrix[j][l];
                        double d_kl = matrix[k][l];
                        double s1 = d_ij + d_kl;
                        double s2 = d_ik + d_jl;
                        double s3 = d_il + d_jk;

                        //check the 4PC for tree metric
                        if (s1 > Math.max(s2, s3)) {
                            invalidQuartets++;
                        } else {
                            validQuartets++;
                        }
                    }
                }
            }
        }
        boolean isTreeMetric = (invalidQuartets == 0);
        return new Object[]{isTreeMetric, validQuartets, invalidQuartets};
    }


    /**
     * small function for printing String yes/no
     * @param bool a boolean
     * @return yes if true/ else no
     */

    public static String ynString(boolean bool) {
        if(bool){
            return "yes";
        }
        else return "no";
    }

    /**
     * own function to print out a 2d matrix used for checking if the convert matrix from txt function works correctly
     * @param matrix
     */
    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     *
     * Throws IoException was added because of tests (commented out below)
     *
     * Example Usage:
     *
     * Input:
     *      java MatrixValidator.java D:/GIT/assignment-5-maier-novikova/data/matrix_3.txt
     *
     *
     * Output:
     *      GBI - Assignment 05 Task 04 - Marius Maier, Polina Novikova
     *      input file: D:/GIT/assignment-5-maier-novikova/data/matrix_3.txt
     *      is valid distance matrix: true
     *      is ultrametric: false
     *      - no. valid/invalid triplets: 4/116
     *      is tree metric: false
     *      - no. valid/invalid quartets: 179/31
     *
     *
     */

    public static void main(String[] args) throws IOException {
        System.out.println("GBI - Assignment 05 Task 04 - " + String.join(", ", studentNames));
        // Call all functions from here and organise the output. You may want to
        // implement additional helper methods and variables.

        //check if the correct number of arguments are provided
        if (args.length != 1) {
            System.err.println("Usage: java MatrixValidator <path/to/file.txt>");
            return;
        }

        //path to the matrix file
        String filePath = args[0];

        try {
            //read the matrix from the file
            double[][] matrix = readMatrixFromFile(filePath);

            //validate the matrix
            boolean isValid = isValidDistanceMatrix(matrix);

            //check if the matrix is ultrametric
            Object[] ultrametricResults = isUltrametric(matrix);
            boolean isUltrametric = (boolean) ultrametricResults[0];
            int validTriplets = (int) ultrametricResults[1];
            int invalidTriplets = (int) ultrametricResults[2];

            //check if the matrix is a tree metric
            Object[] treeMetricResults = isTreeMetric(matrix);
            boolean isTreeMetric = (boolean) treeMetricResults[0];
            int validQuartets = (int) treeMetricResults[1];
            int invalidQuartets = (int) treeMetricResults[2];


            //print the results
            System.out.println("input file: " + filePath);
            System.out.println("is valid distance matrix: " + isValid);
            System.out.println("is ultrametric: " + isUltrametric);
            System.out.println(ynString(isUltrametric) + ". valid/invalid triplets:" + validTriplets + "/" + invalidTriplets);
            System.out.println("is tree metric: " + isTreeMetric);
            System.out.println(ynString(isUltrametric) + ". valid/invalid quartets: " + validQuartets + "/" + invalidQuartets);
        } catch (IOException e) {
            //handle file reading errors
            System.err.println("Error reading the file: " + e.getMessage());
        }



        //Test for own use
        /*
        double [][] testmatrix1  = readMatrixFromFile("D:/GIT/assignment-5-maier-novikova/data/matrix_1.txt");
        double [][] testmatrix2  = readMatrixFromFile("D:/GIT/assignment-5-maier-novikova/data/matrix_2.txt");
        double [][] testmatrix3  = readMatrixFromFile("D:/GIT/assignment-5-maier-novikova/data/matrix_3.txt");

        printMatrix(testmatrix1);

        System.out.println(isValidDistanceMatrix(testmatrix1));
        System.out.println(isUltrametric(testmatrix1));
        System.out.println(isTreeMetric(testmatrix1));

        printMatrix(testmatrix2);

        System.out.println(isValidDistanceMatrix(testmatrix2));
        System.out.println(isUltrametric(testmatrix2));
        System.out.println(isTreeMetric(testmatrix2));

        printMatrix(testmatrix3);

        System.out.println(isValidDistanceMatrix(testmatrix3));
        System.out.println(isUltrametric(testmatrix3));
        System.out.println(isTreeMetric(testmatrix3));
        */
    }

}




