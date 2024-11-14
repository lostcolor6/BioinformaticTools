import java.util.ArrayList;

/**
 * Implements methods for sequence alignment.
 */
public class SequenceAlignment {

    /**
     * Adapted version of the Needleman-Wunsch algorithm to compute optimal global sequence alignments of sequences that may already contain gaps.
     *
     * @param sequence1     The first sequence to align.
     * @param sequence2     The second sequence to align.
     * @param matchScore    Integer to score matches (additive).
     * @param mismatchScore Integer to score mismatches (additive).
     * @param gapPenalty    Integer to penalize InDels (subtractive).
     * @return A {@link AlignedSequences} record that stores the aligned sequences, their score and novel gaps inserted.
     */
    public static AlignedSequences adaptedNeedlemanWunsch(String sequence1, String sequence2, int matchScore, int mismatchScore, int gapPenalty) {
        // Initialize the DP matrix.
        int[][] dpMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];
        for (int i = 0; i <= sequence1.length(); i++) {
            dpMatrix[i][0] = i * -gapPenalty;
        }
        for (int j = 0; j <= sequence2.length(); j++) {
            dpMatrix[0][j] = dpMatrix[0][j] = j * -gapPenalty;
        }
        // Fill the DP matrix.
        for (int i = 1; i <= sequence1.length(); i++) {
            for (int j = 1; j <= sequence2.length(); j++) {
                dpMatrix[i][j] = Math.max(dpMatrix[i - 1][j] - gapPenalty,
                        Math.max(dpMatrix[i][j - 1] - gapPenalty, dpMatrix[i - 1][j - 1] +
                                (sequence1.charAt(i - 1) == sequence2.charAt(j - 1) ? matchScore : (sequence1.charAt(i - 1) == '-' || sequence2.charAt(j - 1) == '-' ? -gapPenalty : mismatchScore))));
            }
        }
        // Run traceback.
        StringBuilder alignedSequenceBuilder1 = new StringBuilder();
        StringBuilder alignedSequenceBuilder2 = new StringBuilder();
        ArrayList<Integer> gapsAlignedSequence1 = new ArrayList<>();
        ArrayList<Integer> gapsAlignedSequence2 = new ArrayList<>();
        int i = sequence1.length();
        int j = sequence2.length();
        int score = dpMatrix[i][j];
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && dpMatrix[i][j] == (dpMatrix[i - 1][j - 1] + (sequence1.charAt(i - 1) == sequence2.charAt(j - 1) ? matchScore : (sequence1.charAt(i - 1) == '-' || sequence2.charAt(j - 1) == '-' ? -gapPenalty : mismatchScore)))) {
                alignedSequenceBuilder1.insert(0, sequence1.charAt(i - 1));
                alignedSequenceBuilder2.insert(0, sequence2.charAt(j - 1));
                i--;
                j--;
            } else if (j > 0 && dpMatrix[i][j] == (dpMatrix[i][j - 1] - gapPenalty)) {
                alignedSequenceBuilder1.insert(0, "-");
                alignedSequenceBuilder2.insert(0, sequence2.charAt(j - 1));
                gapsAlignedSequence1.add(0, j - 1);
                j--;
            } else if (i > 0 && dpMatrix[i][j] == (dpMatrix[i - 1][j] - gapPenalty)) {
                alignedSequenceBuilder1.insert(0, sequence1.charAt(i - 1));
                alignedSequenceBuilder2.insert(0, "-");
                gapsAlignedSequence2.add(0, i - 1);
                i--;
            }
        }
        // Return aligned sequences.
        return new AlignedSequences(alignedSequenceBuilder1.toString(), alignedSequenceBuilder2.toString(), score, gapsAlignedSequence1, gapsAlignedSequence2);
    }

}
