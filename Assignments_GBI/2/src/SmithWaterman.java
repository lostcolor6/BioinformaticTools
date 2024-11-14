// Assignment 02 - Task 04

/**
 * This class provides methods to compute the Smith-Waterman algorithm for local sequence alignment.
 *
 *
 * Code runs in git terminal with:
 * javac SmithWaterman.java
 * java -cp . SmithWaterman <matchScore> <mismatchScore> <gapPenalty> </path/to/sequence1.fasta> </path/to/sequence2.fasta>
 *
 *
 * Note:
 * added the simple tests sequences from Workflow test in test1.fasta and test2.fasta
 *
 *
 */

import java.util.List;

public class SmithWaterman {
    public static final String studentNameA = "Polina Novikova";
    public static final String studentNameB = "Marius Maier";

    public static void main(String[] args) {

       System.out.println(" GBI - Assignment 02 Task 04 - " + studentNameA +", " + studentNameB);
        //check if correct number of arguments are provided
        if (args.length != 5) {
            System.err.println("Usage: SmithWaterman <matchScore> <mismatchScore> <gapPenalty> <sequence1.fasta> <sequence2.fasta>");
            System.exit(1);
        }

        //pass the commandline arguments
        int matchScore = Integer.parseInt(args[0]);
        int mismatchScore = Integer.parseInt(args[1]);
        int gapPenalty = Integer.parseInt(args[2]);
        String sequence1FilePath = args[3];
        String sequence2FilePath = args[4];

        //read sequences from FASTA files
        List<Fasta> sequences1 = FastaIO.readInFasta(sequence1FilePath);
        List<Fasta> sequences2 = FastaIO.readInFasta(sequence2FilePath);

        //both files contain one sequence each
        String sequence1 = sequences1.get(0).getSequence();
        String sequence2 = sequences2.get(0).getSequence();

        //initialize dynamic matrix
        int[][] dpMatrix = initializeMatrix(sequence1.length() + 1, sequence2.length() + 1);

        //fill dynamic matrix and get maximum score
        int maxScore = fillMatrix(dpMatrix, sequence1, sequence2, matchScore, mismatchScore, gapPenalty);
        System.out.println("Optimal local alignment score: " + maxScore);

        //traceback to find optimal alignment
        String[] alignment = traceback(dpMatrix, sequence1, sequence2, matchScore, mismatchScore, gapPenalty);
        System.out.println("Aligned sequence one: " + alignment[0]);
        System.out.println("Aligned sequence two: " + alignment[1]);
    }



    /**
     * method to initialize matrix
     * @param rows
     * @param cols
     * @return matrix with rows/cols filled with 0
     */
    private static int[][] initializeMatrix(int rows, int cols) {
        int[][] dpMatrix = new int[rows][cols];
        //initialize first row and first column to 0
        for (int i = 0; i < rows; i++) {
            dpMatrix[i][0] = 0;
        }
        for (int j = 0; j < cols; j++) {
            dpMatrix[0][j] = 0;
        }
        return dpMatrix;
    }



    /**
     * Method to fill the dynamic programming matrix and return the maximum optimal allignent score
     * @param dpMatrix
     * @param sequence1
     * @param sequence2
     * @param matchScore
     * @param mismatchScore
     * @param gapPenalty
     * @return max optimal allignent score
     */
    private static int fillMatrix(int[][] dpMatrix, String sequence1, String sequence2,
                                    int matchScore, int mismatchScore, int gapPenalty) {
        int maxScore = 0;
        for (int i = 1; i <= sequence1.length(); i++) {
            for (int j = 1; j <= sequence2.length(); j++) {
                // Calculate match, delete, and insert scores
                int match = dpMatrix[i - 1][j - 1] + (sequence1.charAt(i - 1) == sequence2.charAt(j - 1) ? matchScore : mismatchScore);
                int delete = dpMatrix[i - 1][j] - gapPenalty;
                int insert = dpMatrix[i][j - 1] - gapPenalty;
                // Update cell with maximum score
                dpMatrix[i][j] = Math.max(0, Math.max(match, Math.max(delete, insert)));
                // Update maximum score
                maxScore = Math.max(maxScore, dpMatrix[i][j]);
            }
        }
        return maxScore;
    }



    /**
     * method for traceback to find optimal alignment
     * @param dpMatrix
     * @param sequence1
     * @param sequence2
     * @param matchScore
     * @param mismatchScore
     * @param gapPenalty
     * @return string array with the alignment sequences
     */
    private static String[] traceback(int[][] dpMatrix, String sequence1, String sequence2,
                                      int matchScore, int mismatchScore, int gapPenalty) {
        int maxScore = 0;
        int maxI = 0;
        int maxJ = 0;
        // Find cell with maximum score
        for (int i = 0; i < sequence1.length() + 1; i++) {
            for (int j = 0; j < sequence2.length() + 1; j++) {
                if (dpMatrix[i][j] > maxScore) {
                    maxScore = dpMatrix[i][j];
                    maxI = i;
                    maxJ = j;
                }
            }
        }
        StringBuilder alignedSequence1 = new StringBuilder();
        StringBuilder alignedSequence2 = new StringBuilder();
        // traceback to reconstruct alignment
        while (maxI > 0 && maxJ > 0 && dpMatrix[maxI][maxJ] != 0) {
            if (dpMatrix[maxI][maxJ] == dpMatrix[maxI - 1][maxJ - 1] + (sequence1.charAt(maxI - 1) == sequence2.charAt(maxJ - 1) ? matchScore : mismatchScore)) {
                alignedSequence1.insert(0, sequence1.charAt(maxI - 1));
                alignedSequence2.insert(0, sequence2.charAt(maxJ - 1));
                maxI--;
                maxJ--;
            } else if (dpMatrix[maxI][maxJ] == dpMatrix[maxI - 1][maxJ] - gapPenalty) {
                alignedSequence1.insert(0, sequence1.charAt(maxI - 1));
                alignedSequence2.insert(0, '-');
                maxI--;
            } else {
                alignedSequence1.insert(0, '-');
                alignedSequence2.insert(0, sequence2.charAt(maxJ - 1));
                maxJ--;
            }
        }
        return new String[]{alignedSequence1.toString(), alignedSequence2.toString()};
    }
}

