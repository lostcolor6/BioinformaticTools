// Assignment 09 - Task 02

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Main {

    private static final List<String> studentNames = List.of(
            "Polina Novikova", "Marius Maier"
    );

    /**
     * Do not forget to document your code!
     */
    private static double[] trainEmissionProbabilities(char stateName, List<Fasta> trainingData) {
        // TODO: b) Implement a function that calculates the emission probabilities for the symbols of a HMM model under a given state.
        //      - 1. Think of a way to parse the training data and pass it to this method; You may want to switch the parameter type from Object to something different.
        //      - 2. Use the respective training data to compute the emission probabilities.
        //      - 3. The returned probabilities have to be collected in a single 2D double[][] array to be passed to the HMM model!
        double[] emissionProbabilities = new double[3]; // 2 symbols: R and Y and       int totalSymbols = 0;
        int totalSymbols = 0;
        for (Fasta fasta : trainingData) {
            for (char symbol : fasta.sequence().toCharArray()) {
                if (symbol == 'R') {
                    emissionProbabilities[0]++;
                } else if (symbol == 'Y') {
                    emissionProbabilities[1]++;
                }

                totalSymbols++;
            }
        }


        // Normalize the probabilities
        /**
         for (int i = 0; i < emissionProbabilities.length; i++) {
         emissionProbabilities[i] = emissionProbabilities[i]/ totalSymbols;
         }
         */

        emissionProbabilities[0] = (double) emissionProbabilities[0] / totalSymbols;
        emissionProbabilities[1] = (double) emissionProbabilities[1] / totalSymbols;


        return emissionProbabilities;
    }


    public static void main(String[] args) {
        System.out.println("GBI - Assignment 09 Task 02 - " + String.join(", ", studentNames));

        // TODO: c.1) Define state and symbol names and instantiate the HMM.
        char[] stateNames = {'B', 'N', 'G', 'E'};
        //char[] symbolNames = {'R', 'Y',' ' };
        char[] symbolNames = {'R', 'Y', ' ' };

        // Instantiate the HMM
        HMM hmm = new HMM(stateNames.length, symbolNames.length);
        hmm.setStateNames(stateNames);
        hmm.setSymbolNames(symbolNames);

        // TODO: c.2) Update the transition probabilities provided in the task
        double[][] transProb = {
                {0.5, 0.5, 0.0, 0.0}, // Begin state
                {0.8, 0.19, 0.01, 0.0}, // Genic state
                {0.24, 0.75, 0.01, 0.0}, // Non-genic state
                {0.0, 0.0, 0.0, 1.0} // End state
        };
        hmm.setTransitionProbabilities(transProb);

        // TODO: c.3) Read in the training files containing genic and non-genic sequences and compute the emission probabilities the function you have implemented in subtask b).
        double[][] emissionProb = new double[stateNames.length][symbolNames.length];
        emissionProb[1] = trainEmissionProbabilities('G', FastaIO.readInFasta("data/train_genic.fasta"));
        emissionProb[2] = trainEmissionProbabilities('N', FastaIO.readInFasta("data/train_non_genic.fasta"));


        System.out.println( "rows of computed emissionMatrix: " + Arrays.deepToString(emissionProb));


        //how i think the layout should have been (based on the forum questions) but doesnt work
        double[][] emissionProbHardCoded = {

                {0.51, 0.49, 0},
                {0.494, 0.506, 0},
                {0, 0, 1.0},
                {0, 0, 1.0},

        };



        hmm.setEmissionProbabilities(emissionProb);
        //hmm.setEmissionProbabilities(emissionProbHardCoded);

        // TODO: d) Implement the Viterbi Algorithm, parse data/test.fasta and decode the sequence.

        List<Fasta> sequencesToDecode = FastaIO.readInFasta("data/test.fasta");

        Viterbi viterbi = new Viterbi();
        for (Fasta fasta : sequencesToDecode) {
            String decodedSequence = viterbi.decode(fasta.sequence(), hmm);
            System.out.println("Symbols: " + fasta.sequence());
            System.out.println("States: " + decodedSequence);
        }
    }

}

