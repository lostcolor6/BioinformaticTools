// Assignment 09 - Task 02

import java.util.ArrayList;

/**
 * Code skeleton for Viterbi implementation.
 * The parts we expect you to change the code are indicated with a TODO.
 *
 * @author ADD YOUR NAMES HERE
 */
@SuppressWarnings("unused")
public class Viterbi {

    private double[][] dp;  // Dynamic programming table for storing probabilities
    private int[][] backpointer;  // Backpointer table for storing paths

    /**
     * Runs the Viterbi decoding on a given string of symbols and HMM.
     *
     * @param symbols The string of symbols to decode.
     * @param hmm     The {@link HMM} model to use.
     * @return The string of decoded states
     */
    public String decode(String symbols, HMM hmm) {

        dp = new double[symbols.length()][hmm.getNumStates()];
        backpointer = new int[symbols.length()][hmm.getNumStates()];

        // 1. Initialization
        initialize(symbols, hmm);

        // 2. Recursion
        for (int l = 1; l < symbols.length(); l++) {
            recurse(l, symbols, hmm);
        }

        // 3. Termination
        int finalState = terminate(symbols.length(), hmm);

        // 4. Traceback
        String decodedStr = traceback(symbols.length(), finalState, hmm);

        return decodedStr;
    }

    /**
     * Initialization step of the Viterbi algorithm.
     *
     * @param symbols The string of symbols to decode.
     * @param hmm     The {@link HMM} model to use.
     */
    private void initialize(String symbols, HMM hmm) {
        int firstSymbolRank = hmm.getSymbolRank(symbols.charAt(0));

        for (int i = 0; i < hmm.getNumStates(); i++) {
            dp[0][i] = hmm.getEmissionProb(i, firstSymbolRank);
            backpointer[0][i] = 0;  // No backpointer for the first observation
        }
    }

    /**
     * Recursion step of the Viterbi algorithm for a given time step t.
     *
     * @param t       The current time step.
     * @param symbols The string of symbols to decode.
     * @param hmm     The {@link HMM} model to use.
     */
    private void recurse(int t, String symbols, HMM hmm) {
        int currentSymbolRank = hmm.getSymbolRank(symbols.charAt(t));
        // TODO: Implement
        for (int i = 0; i < hmm.getNumStates(); i++) {
            double maxProb = -1;
            int maxState = 0;
            for (int j = 0; j < hmm.getNumStates(); j++) {
                double prob = dp[t - 1][j] * hmm.getTransProb(j, i) * hmm.getEmissionProb(i, currentSymbolRank);
                if (prob > maxProb) {
                    maxProb = prob;
                    maxState = j;
                }
            }
            dp[t][i] = maxProb;
            backpointer[t][i] = maxState;
        }
    }

    /**
     * Termination step of the Viterbi algorithm.
     *
     * @param L   The length of the decoded symbols.
     * @param hmm The {@link HMM} model to use.
     * @return The final state with the highest probability.
     */
    private int terminate(int L, HMM hmm) {
        double maxProb = -1;
        int finalState = 0;
        // TODO: Implement
        for (int i = 0; i < hmm.getNumStates(); i++) {
            if (dp[L - 1][i] > maxProb) {
                maxProb = dp[L - 1][i];
                finalState = i;
            }
        }
        return finalState;
    }

    /**
     * Traceback step of the Viterbi algorithm to construct the sequence of states.
     *
     * @param L          The length of the decoded symbols.
     * @param finalState The final state with the highest probability.
     * @param hmm        The {@link HMM} model to use.
     * @return The string of decoded states.
     */
    private String traceback(int L, int finalState, HMM hmm) {
        StringBuilder decodedStates = new StringBuilder();
        int currentState = finalState;
        // TODO: Implement
        for (int t = L - 1; t >= 0; t--) {
            decodedStates.append(hmm.getStateName(currentState));
            currentState = backpointer[t][currentState];
        }
        return decodedStates.toString();
    }

    /**
     * Returns the reverse of a list of characters as a string.
     *
     * @param list The list of characters to reverse.
     * @return The reversed string representation of the input list.
     */
    private static String getReverse(ArrayList<Character> list) {
        final StringBuilder buf = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            buf.append(list.get(i));
        }
        return buf.toString();
    }
}
