// Assignment 03 - Task 04

import java.util.ArrayList;
import java.util.List;

/**
 * Computes pair-guided progressive multiple sequence alignment.
 */
public class ProgressiveAlignment {
    /**
     * Adjust or add your names here!
     */
    private static final List<String> studentNames = List.of(
            "Student 1", "Student 2"
    );

    private static ArrayList<Profile> parseProfileListFromFasta(String filePath) {
        ArrayList<Profile> parsedSequences = new ArrayList<>();
        // TODO Implement this method.
        return parsedSequences;
    }

    private static Profile computeMSA(ArrayList<Profile> profiles, int matchScore, int mismatchScore, int gapPenalty) {
        Profile msa = new Profile();
        // TODO Implement this method.
        return msa;
    }

    public static void main(String[] args) {
        System.out.println("GBI - Assignment 03 Task 04 - " + String.join(", ", studentNames));
        // Call all functions from here and organise the output. You may want to implement additional helper methods and variables.
    }
}