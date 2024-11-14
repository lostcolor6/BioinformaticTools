import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TransitionMatrixCalculator {

    private static final List<String> studentNames = List.of(
                "Polina Novikova", "Marius Maier"
        );


    private static Map<String, Integer> computeTransitionCounts(String filename) {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("RR", 0);
        counts.put("RY", 0);
        counts.put("YR", 0);
        counts.put("YY", 0);
        
    
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String previousNucleotide = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(">")) {
                    previousNucleotide = null; // New sequence, reset previous nucleotide
                } else {
                    for (char nucleotide : line.toCharArray()) {
                        if (previousNucleotide != null) {
                            // TODO: Implement the code that counts the transitions
                            String transition = previousNucleotide + nucleotide;
                            counts.put(transition, counts.get(transition) + 1);
                        }
                        previousNucleotide = String.valueOf(nucleotide);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return counts;
    }


        private static double[][] computeTransitionMatrix(Map<String, Integer> counts) {
            //TODO: Implement the method that computes the transition matrix from the counts
            double[][] matrix = new double[2][2];
            double totalR = counts.get("RR") + counts.get("RY");
            double totalY = counts.get("YR") + counts.get("YY");

            matrix[0][0] = counts.get("RR") / totalR;
            matrix[0][1] = counts.get("RY") / totalR;
            matrix[1][0] = counts.get("YR") / totalY;
            matrix[1][1] = counts.get("YY") / totalY;


            return matrix;
        }


        // Method to print the transition matrix
        private static void printMatrix(double[][] matrix) {
            System.out.println("    R      Y");
            System.out.printf("R %.4f %.4f%n", matrix[0][0], matrix[0][1]);
            System.out.printf("Y %.4f %.4f%n", matrix[1][0], matrix[1][1]);
        }

        // Method to read a sequence from a file
        private static String readSequence(String filename) {
        StringBuilder sequence = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(">")) {
                    sequence.append(line.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sequence.toString();
    }


    private static double computeSequenceProbability(String sequence, double[][] matrix) {
        //TODO: Implement the method that computes the probability of the sequence given the transition matrix
        double probability = 1.0;
        for (int i = 1; i < sequence.length(); i++) {
            char prev = sequence.charAt(i - 1);
            char curr = sequence.charAt(i);
            if (prev == 'R' && curr == 'R') probability *= matrix[0][0];
            else if (prev == 'R' && curr == 'Y') probability *= matrix[0][1];
            else if (prev == 'Y' && curr == 'R') probability *= matrix[1][0];
            else if (prev == 'Y' && curr == 'Y') probability *= matrix[1][1];
        }
        return probability;
    }

    private static double computeLogOddsRatio(double probGenic, double probNonGenic) {
        //TODO: Implement the method that computes the log odds ratio of the two probabilities
        return Math.log(probGenic / probNonGenic);
    }


    public static void main(String[] args) {
        System.out.println("GBI - Assignment 06 Task 03 - " + String.join(", ", studentNames));
        //compute transition counts for genic and non-genic regions
        Map<String, Integer> genicCounts = computeTransitionCounts("data/train_genic.fasta");
        Map<String, Integer> nonGenicCounts = computeTransitionCounts("data/train_non_genic.fasta");

        //compute transition matrices
        double[][] genicMatrix = computeTransitionMatrix(genicCounts);
        double[][] nonGenicMatrix = computeTransitionMatrix(nonGenicCounts);

        // print transition matrices
        System.out.println("Genic Transition Matrix:");
        printMatrix(genicMatrix);
        System.out.println("Non-Genic Transition Matrix:");
        printMatrix(nonGenicMatrix);

        //read the test sequence
        String testSequence = readSequence("data/test.fasta");

        //calculate probabilities of the test sequence under each model
        double probGenic = computeSequenceProbability(testSequence, genicMatrix);
        double probNonGenic = computeSequenceProbability(testSequence, nonGenicMatrix);

        // calc and print the log-odds ratio
        double logOddsRatio = computeLogOddsRatio(probGenic, probNonGenic);
        System.out.println("Log-Odds Ratio: " + logOddsRatio);

        //conclusions
        if (logOddsRatio > 0) {
            System.out.println("The sequence x is more likely to be genic.");
        } else {
            System.out.println("The sequence x is more likely to be non-genic.");
        }
    
    }
}
