/**
 * Assignment 01 - Task 03
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EditTranscript {
    /**
     * Adjust your names here:
     */
    private static final String studentNameA = "Polina Novikova";
    private static final String studentNameB = "Marius Maier";

    /**
     * Compute the Allignment matrix (class just copied from below with less functionality only for printing purpose
     *
     * @param sequence1 The first sequence
     * @param sequence2 The second sequence
     * @return Allignmentmatrix
     */
    public static int[][]  computeAllignmentMatrix(String sequence1, String sequence2){

        char[] seqChar1 = sequence1.toCharArray();
        char[] seqChar2 = sequence2.toCharArray();
        //length + 1 because first cell is 0
        int[][] computeMatrix = new int[seqChar1.length + 1][seqChar2.length + 1];



        //filling the first row with values 0, 1, 2, 3, ...
        for(int j = 0; j<computeMatrix[0].length; j++){
            computeMatrix[0][j] = j;
        }
        //filling the first column with values 0, 1, 2, 3, ...
        for(int i = 0; i<computeMatrix.length; i++){
            computeMatrix[i][0] = i;
        }

        //filling the remaining cells in the matrix (each row get filled)

        for (int j = 1; j < computeMatrix[0].length; j++) {
            for (int i = 1; i < computeMatrix.length; i++) {
                computeMatrix[i][j] =
                        min(
                                computeMatrix[i - 1][j - 1] + computeDelta(seqChar1[i - 1], seqChar2[j - 1]),
                                computeMatrix[i - 1][j] + 1,
                                computeMatrix[i][j - 1] + 1
                        );


            }
        }





         return computeMatrix;

    }

    /**
     * Compute the Tracebackmatrix using Dynamic Programming
     *
     * @param sequence1 The first sequence
     * @param sequence2 The second sequence
     * @return Tracebackmatrix
     */
    public static int[][]  computeTracebackMatrix(String sequence1, String sequence2){

        char[] seqChar1 = sequence1.toCharArray();
        char[] seqChar2 = sequence2.toCharArray();
        //length + 1 because first cell is 0
        int[][] computeMatrix = new int[seqChar1.length + 1][seqChar2.length + 1];

        int[][] tracebackMatrix = new int[seqChar1.length + 1][seqChar2.length + 1];

        //filling the first row with values 0, 1, 2, 3, ...
        for(int j = 0; j<computeMatrix[0].length; j++){
            computeMatrix[0][j] = j;
        }
        //filling the first column with values 0, 1, 2, 3, ...
        for(int i = 0; i<computeMatrix.length; i++){
            computeMatrix[i][0] = i;
        }

        //filling the remaining cells in the matrix (each row get filled)

            for (int j = 1; j < computeMatrix[0].length; j++) {
                for (int i = 1; i < computeMatrix.length; i++) {
                    computeMatrix[i][j] =
                            min(
                                    computeMatrix[i - 1][j - 1] + computeDelta(seqChar1[i - 1], seqChar2[j - 1]),
                                    computeMatrix[i - 1][j] + 1,
                                    computeMatrix[i][j - 1] + 1
                            );




                    //Save current mincost
                    int minCost = computeMatrix[i][j];


                    //determine from which predecessor cell the minimum was derived
                    if (minCost == computeMatrix[i - 1][j - 1] + computeDelta(seqChar1[i - 1], seqChar2[j - 1])) {
                        tracebackMatrix[i][j] = 1; // diagonal predecessor edit was M or R (match or replace)
                    } else if (minCost == computeMatrix[i - 1][j] + 1) {
                        tracebackMatrix[i][j] = 2; // vertical predecessor edit was I (insetion)
                    } else {
                        tracebackMatrix[i][j] = 3; // horizontal predecessor edit was D (delete
                    }
                }
            }




            return tracebackMatrix;
    }


    public static String computeEditTranscript(int[][] array, String seq1, String seq2){
        List<String> editTranscriptArray = new ArrayList<>();
        int j = array[0].length -1;
        int i = array.length -1 ;

        char[] seqChar1 = seq1.toCharArray();
        char[] seqChar2 = seq2.toCharArray();


        while (i >= 0 && j >= 0) {
            int currentValue = array[i][j];
            if (currentValue == 1) {
                if (seqChar1[i-1] == seqChar2[j-1]) {
                    editTranscriptArray.add("M"); //Diagonal
                    i--;
                    j--;
                } else {
                    editTranscriptArray.add("R"); //Diagonal
                    i--;
                    j--;
                }
            }

            else if (currentValue == 2) {
                editTranscriptArray.add("D"); //Vertical
                j--;
                //i = i;
            } else {
                editTranscriptArray.add("I"); //Horizontal
                //j = j;
                i--;

            }
        }
        //Test print
        //System.out.println("Size of editTranscript: " + editTranscriptArray.size());

        //couse it didnt work before on terminal before with return "editTranscriptArray.reverse.toString();" idk why
        Collections.reverse(editTranscriptArray);

        String editTranscriptString = editTranscriptArray.toString();
        return editTranscriptString;
    }



    /**
     * little helper function to find the minimum of
     * @param a
     * @param b
     * @param c
     * @return min of the 3 values
     */
    public static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * little helper function to compute delta function
     * @param a
     * @param b
     * @return 0 if a = b else 1
     */
    public static int computeDelta(int a, int b){
        if(a == b){
            return 0;
        }
        else{
            return 1;
        }
    }



public static void EditTranscript(String a, String b){
    System.out.println("GBI - Assignment 1 Task 3 - " + studentNameA + ", " + studentNameB );
    System.out.println("Sequence 1: " + a);
    System.out.println("Sequence 2: " + b);

    char[] seqChar1 = a.toCharArray();
    char[] seqChar2 = b.toCharArray();

    System.out.println("prints out Allignemnt Matrix :");
    printMatrix.printMatrixFancy(computeAllignmentMatrix(a,b));


    System.out.println("prints out Traceback Matrix :");
    printMatrix.printMatrixFancy(computeTracebackMatrix(a,b));
    System.out.println(" ");
    System.out.println("prints out edit Transcript :");
    System.out.println(computeEditTranscript(computeTracebackMatrix(a,b), a,b));

    System.out.println(" ");

}






    public static void main(String[] args) {



        System.out.println("GBI - Assignment 1 Task 3 - " + studentNameA + ", " + studentNameB );
        // TODO: Call all functions from here and organise the output.

        //length = 100
        String seq1 = "CTTTACTAAGTACTGGATCTTATTTCAGCAAGATTTTTTATCTAAAAACAATGAGAGAAGTATTTGTTAAACCACATAGCTTTCATGTTTTGATCAAAAG";
        //length = 90
        String seq2 = "GACCGTTGGCGCCCGACCCTCAGGCTCTGTAGTGAGTTCCATGTCCGGGCCATTGCATGCGAGGTCGGTAGATTGATAGGGGACACGGAA";


        char[] seqChar1 = seq1.toCharArray();
        char[] seqChar2 = seq2.toCharArray();

        printMatrix.printMatrixFancy(computeAllignmentMatrix(seq1,seq2));
        System.out.println("prints out Allignemnt Matrix :");
       printMatrix.printMatrixFancy(computeAllignmentMatrix(seq1,seq2));
       System.out.println(" ");
        System.out.println("prints out edit Transcript :");
       System.out.println(computeEditTranscript(computeTracebackMatrix(seq1,seq2), seq1,seq2));

        System.out.println(" ");


        System.out.println("Beispiel aus den VL-Folien: ");
        String seq3 ="ATTAC";
        String seq4= "GATTAG";
        char[] seqChar3 = seq1.toCharArray();
        char[] seqChar4= seq2.toCharArray();

        System.out.println("prints out Allignment Matrix :");
        printMatrix.printMatrixFancy(computeAllignmentMatrix(seq3,seq4));
        System.out.println("prints out Traceback Matrix :");
        printMatrix.printMatrixFancy(computeTracebackMatrix(seq3,seq4));
        System.out.println(" ");
        System.out.println("prints out edit Transcript :");
        System.out.println(computeEditTranscript(computeTracebackMatrix(seq3,seq4),seq3, seq4));



        /**
         * main with Tests
         *
         * IMPORTANT!:
         * currently the code can run in Git bash terminal using:
         * Make sure you are in the correct folders otherwise navigate to them with "cd" command or right click and "open git bash here" on Win10
         *
         * javac EditTranscript.java
         * java EditTranscript string1 string2
         *
         * For example:
         *
         *  javac EditTranscript.java
         *
         *  java EditTranscript ATTAC GATTAG
         *
         *
         *
         *
         *
         *
         *
         * these Test inputs below will run before the given terminal command (but you can comment them out)
         *
         * @param args
         */

        //check if the correct number of arguments is provided (in this case 2)
        if (args.length != 2) {
            System.out.println("Usage: java EditTranscript <String> <String>");
            return;
        }

        //call method to read and display fasta file information

        EditTranscript(args[0], args[1]);


        /**
         * Tests with given sequences and a sequence from Lectures
         *
         */

    }

}