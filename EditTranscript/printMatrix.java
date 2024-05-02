package EditTranscript;

public class printMatrix {
    /**
     * function to print out a 2d matrix
     * @param matrix
     */
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Attempt at creating a nicer output of a matrix
     * @param matrix
     * @param sequence1
     * @param sequence2
     */
    public static void printMatrixWithHeaders(int[][] matrix, String sequence1, String sequence2) {
        char[] seqChar1 = sequence1.toCharArray();
        char[] seqChar2 = sequence2.toCharArray();

        // Print column headers (sequence2)
        System.out.print("  ");
        for (int j = 0; j < seqChar2.length; j++) {
            System.out.print(seqChar2[j] + " ");
        }
        System.out.println();

        for (int i = 0; i < matrix.length; i++) {
            if (i == 0) {
                System.out.print(" ");
            } else {
                System.out.print(seqChar1[i - 1] + " ");
            }
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }


    /**
     * Function to print out a matrix in a nice format
     * source: https://programming.guide/java/print-2d-matrix.html  (01.05.2024)
     * @param matrix
     */
    public static void printMatrixFancy(int[][] matrix) {
        int cols = matrix[0].length;
        int[] colWidths = new int[cols];
        for (int[] row : matrix) {
            for (int c = 0; c < cols; c++) {
                int width = String.valueOf(row[c]).length();
                colWidths[c] = Math.max(colWidths[c], width);
            }
        }
        for (int[] row : matrix) {
            for (int c = 0; c < cols; c++) {
                String fmt = String.format("%s%%%dd%s",
                        c == 0 ? "|" : "  ",
                        colWidths[c],
                        c < cols - 1 ? "" : "|%n");
                System.out.printf(fmt, row[c]);
            }
        }
    }


}
