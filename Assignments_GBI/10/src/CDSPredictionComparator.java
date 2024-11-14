// Assignment 10 - Task 03

import java.util.List;

public class CDSPredictionComparator {

    private static final List<String> studentNames = List.of(
            "Polina Novikova", "Marius Maier"
    );

    // initialize counters for True Positives, True Negatives, False Positives, and False Negatives
    private static int TP = 0;
    private static int TN = 0;
    private static int FP = 0;
    private static int FN = 0;

    /**
     * method to compare two lists of CDS (coding sequences).
     *
     * @param cds1          First list of CDS.
     * @param cds2          Second list of CDS.
     * @param totalPositions Total number of positions.
     */
    private static void compareCDS(List<String[]> cds1, List<String[]> cds2, int totalPositions) {
        boolean[] referenceCoding = new boolean[totalPositions];
        boolean[] predictionCoding = new boolean[totalPositions];

        for (String[] cds : cds1) {
            int start = Integer.parseInt(cds[0]) - 1;
            int end = Integer.parseInt(cds[1]);
            for (int i = start; i < end; i++) {
                referenceCoding[i] = true;
            }
        }

        for (String[] cds : cds2) {
            int start = Integer.parseInt(cds[0]) - 1;
            int end = Integer.parseInt(cds[1]);
            for (int i = start; i < end; i++) {
                predictionCoding[i] = true;
            }
        }

        for (int i = 0; i < totalPositions; i++) {
            if (referenceCoding[i] && predictionCoding[i]) {
                TP++;
            } else if (!referenceCoding[i] && !predictionCoding[i]) {
                TN++;
            } else if (!referenceCoding[i] && predictionCoding[i]) {
                FP++;
            } else if (referenceCoding[i] && !predictionCoding[i]) {
                FN++;
            }
        }
    }

     /**
     * computes Sensitivity
     *
     * @param TP True Positives.
     * @param FN False Negatives.
     * @return Sensitivity
     */
    private static float computeSensitivity(int TP, int FN) {
        return TP / (float) (TP + FN);
    }

     /**
     * computes specificity
     *
     * @param TN True Negatives.
     * @param FP False Positives.
     * @return Specificity
     */
    private static float computeSpecificity(int TN, int FP) {
        return TN / (float) (TN + FP);
    }

    /**
     * computes accuracy
     *
     * @param TP True Positives.
     * @param TN True Negatives.
     * @param FP False Positives.
     * @param FN False Negatives.
     * @return Accuracy
     */
    private static float computeAccuracy(int TP, int TN, int FP, int FN) {
        return (TP + TN) / (float) (TP + TN + FP + FN);
    }

    public static void main(String[] args) {
        System.out.println("GBI - Assignment 10 Task 03 - " + String.join(", ", studentNames));
        // read the reference and test CDS data from TSV files
        List<String[]> cds_reference = TSVReader.readTSV(args[0], 1);
        List<String[]> cds_test = TSVReader.readTSV(args[1], 1);
        int totalPositions = Integer.parseInt(args[2]);

         //compare the CDS predictions
        compareCDS(cds_reference, cds_test, totalPositions);

        //generate output string with the results
        String cli_output = String.format(
                """
                Predicted CDS: %s
                True CDS: %s
                TP: %d
                TN: %d
                FP: %d
                FN: %d
                Sns.: %.6f
                Spc.: %.6f
                Acc.: %.6f
                """,
                args[1],
                args[0],
                TP,
                TN,
                FP,
                FN,
                computeSensitivity(TP, FN),
                computeSpecificity(TN, FP),
                computeAccuracy(TP, TN, FP, FN)
        );
        System.out.println(cli_output);
    }

}

