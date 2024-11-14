// Assignment 09 - Pre-implemented code. You do not need to modify this file.

import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * Simple HMM class.
 */
@SuppressWarnings("unused")
public class HMM {

    /**
     * The number of states this HMM yields.
     */
    private final int numStates;

    /**
     * The names of the states this HMM yields, represented as {@link Character}s.
     */
    private char[] stateNames;

    /**
     * The number of (emission) symbols this HMM yields.
     */
    private final int numSymbols;

    /**
     * The actual (emission) symbols this HMM yields, represented as {@link Character}s.
     */
    private char[] symbolNames;

    /**
     * The state transition probability matrix.
     */
    private double[][] transProb;

    /**
     * The symbol emission probability matrix.
     */
    private double[][] emissionProb;

    /**
     * Construct an empty HMM, i.e., without any transition and emission probabilities set.
     *
     * @param numStates  the number of states that the HMM will hold
     * @param numSymbols the number of symbols that the HMM will hold
     */
    public HMM(int numStates, int numSymbols) {
        this.numStates = numStates;
        this.stateNames = new char[numStates];
        this.numSymbols = numSymbols;
        this.symbolNames = new char[numSymbols];
        this.transProb = new double[numStates][numStates];
        this.emissionProb = new double[numStates][numSymbols];
    }

    /**
     * Sets the state names for this HMM.
     *
     * @param stateNames {@code char[]} specifying the state names to use. Ensure that {@code stateNames.length == this.numStates}!
     * @throws RuntimeException When the HMM is not valid anymore after setting state names.
     */
    public void setStateNames(char[] stateNames) throws InvalidParameterException {
        this.stateNames = stateNames;
        //checkValid();
    }

    /**
     * Sets the symbol names for this HMM.
     *
     * @param symbolNames {@code char[]} specifying the symbol names to use. Ensure that {@code symbolNames.length == this.numSymbols}!
     * @throws RuntimeException When the HMM is not valid anymore after setting symbol names.
     */
    public void setSymbolNames(char[] symbolNames) throws InvalidParameterException {
        this.symbolNames = symbolNames;
        //checkValid();
    }

    /**
     * Check that the HMM contains valid and consistent data.
     */
    public void checkValid() throws InvalidParameterException {
        if (numStates < 0 || numSymbols < 0)
            throw new InvalidParameterException("Invalid model, numStates=" + numStates + ", numSymbols=" + numSymbols);
        // Test transition probabilities:
        for (int i = 0; i < numStates; i++) {
            double sum = 0;
            for (int j = 0; j < numStates; j++) {
                if (transProb[i][j] < 0)
                    throw new InvalidParameterException("Invalid model, transProb[" + i + "][" + j + "]=" + transProb[i][j]);
                sum += transProb[i][j];
            }
            if (Math.abs(1.0 - sum) > 0.000001)
                throw new InvalidParameterException("Invalid model, transProb(row=" + i + "): " + sum);
        }
        // (Check for begin and end state ignored...)

        // Test emission probabilities:
        for (int i = 0; i < numStates; i++) {
            double sum = 0;
            for (int j = 0; j < numSymbols; j++) {
                if (emissionProb[i][j] < 0)
                    throw new InvalidParameterException("Invalid model, emissionProb[" + i + "][" + j + "]=" + emissionProb[i][j]);
                sum += emissionProb[i][j];
            }
            if (sum > 1.0)
                throw new InvalidParameterException("Invalid model, emissionProb(row=" + i + "): " + sum);
        }
    }

    /**
     * Sets the transition probabilities for the Hidden Markov Model.
     *
     * @param transProb the transition probability matrix
     * @throws IllegalArgumentException if the dimensions of the transition probability matrix do not match the number of states
     */
    public void setTransitionProbabilities(double[][] transProb) {
        if (transProb.length != numStates || transProb[0].length != numStates) {
            throw new IllegalArgumentException("Transition probability matrix dimensions do not match the number of states.");
        }
        this.transProb = transProb;
    }

    /**
     * Sets the emission probabilities for the Hidden Markov Model (HMM).
     *
     * @param emissionProb The emission probability matrix.
     *                     The dimensions of the matrix must match the number of states and symbols.
     * @throws IllegalArgumentException if the dimensions of the emission probability matrix do not match the number of states and symbols.
     */
    public void setEmissionProbabilities(double[][] emissionProb) {
        if (emissionProb.length != numStates || emissionProb[0].length != numSymbols) {
            throw new IllegalArgumentException("Emission probability matrix dimensions do not match the number of states and symbols.");
        }
        this.emissionProb = emissionProb;
    }

    /**
     * Get the number of states
     *
     * @return number of states
     */
    public int getNumStates() {
        return numStates;
    }

    /**
     * Get the name of the i-th state
     *
     * @param i the rank of the state
     * @return the name of the i-th state
     */
    public char getStateName(int i) {
        return stateNames[i];
    }

    /**
     * Get the rank i of a state s_i
     *
     * @param s the state
     * @return the rank 0 ... numStates-1 of the symbol
     */
    public int getStateRank(char s) throws IOException {
        for (int i = 0; i < getNumStates(); i++)
            if (s == getStateName(i))
                return i;
        throw new IOException("Unknown state name: " + s);
    }

    /**
     * Get the number of symbols
     *
     * @return number of symbols
     */
    public int getNumSymbols() {
        return numSymbols;
    }

    /**
     * Get the name of the i-th symbol
     *
     * @param i the rank of the symbol
     * @return the name of the i-th symbol
     */
    public char getSymbolName(int i) {
        // Careful here: emitSymbol will return -1, if called while
        // in the beginning/end state 0. Need to make sure you don't call
        // getSymbolName then!
        return symbolNames[i];
    }

    /**
     * Get the rank i of a symbol s_i
     *
     * @param z the symbol
     * @return the rank 0 ... numSymbols-1 of the symbol
     */
    public int getSymbolRank(char z) throws InvalidParameterException {
        for (int i = 0; i < getNumSymbols(); i++)
            if (z == getSymbolName(i))
                return i;
        throw new InvalidParameterException("Unknown symbol name: " + z);
    }

    /**
     * Get the transition probability for s to t
     *
     * @param s the source state
     * @param t the target state
     * @return transition probability s to t
     */
    public double getTransProb(int s, int t) {
        return transProb[s][t];
    }

    /**
     * Get the emission probability for state k and symbol s
     *
     * @param s the state
     * @param z the symbol
     * @return emission probability for state k and symbol s
     */
    public double getEmissionProb(int s, int z) {
        return emissionProb[s][z];
    }
}
