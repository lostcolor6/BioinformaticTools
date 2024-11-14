// Assignment 03 - Pre-implemented code. You do not need to modify this file.

import java.util.ArrayList;

/**
 * Stores the results of pairwise sequence alignment.
 */
public record AlignedSequences(String alignedSequence1, String alignedSequence2, int alignmentScore,
                               ArrayList<Integer> gapsAlignedSequence1,
                               ArrayList<Integer> gapsAlignedSequence2) {
}
