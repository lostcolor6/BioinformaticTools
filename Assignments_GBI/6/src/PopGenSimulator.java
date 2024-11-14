// Assignment 06 - Task 03

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopGenSimulator {
    
    private static final List<String> studentNames = List.of(
            "Polina Novikova", "Marius Maier"
    );

    /**
     * Random number generator.
     */
    private final Random random = new Random();
    
    /**
     * Transforms the passed size into a string ABC... to represent labels in the simulation.
     * 
     * @param size The initial generation size.
     * @return String representation of the generation (size).
     */
    public String initialGeneration(int size) {
        StringBuilder g = new StringBuilder();
        int border = 'A' + size;  // Capital letters A, B, C, etc.
        for (char c = 'A'; c < border; c++) {
            g.append(c);
        }
        return g.toString();
    }

    /**
     *
     * calculates the waiting time for the next coalescence event.
     *
     * the waiting time is exponentially distributed and depends on the current population size.
     * where U_k is a uniform random variable between 0 and 1
     * (k choose 2) is the number of possible pairs of lineages in the current population.
     *
     * @param k current number of lineages in the population
     * @return waiting time for the next coalescence event
     */
    public double calculateWaitingTime(int k) {
        //generate a uniform random number between 0 and 1
        double U_k = random.nextDouble();
        //calculate the combination (k choose 2), which is the number of pairs
        double combination = k * (k - 1) / 2.0;
        return -Math.log(U_k) / combination;
    }


    /**
     * Simulates the previous generation by combining pairs of edges into a single edge/lineage,
     * (moving one generation backward in time)
     *
     * function reduces the number of lineages in the population by randomly selecting pairs
     * and combining them into a new lineage (their common ancestor)
     * process continues until only one remains
     * The waiting time for each coalescence event is used as the distance for the new branches
     *
     * @param currentGeneration  list of BinaryTree objects representing the current generation of lineages
     * @return list of BinaryTree objects representing the new generation after coalescence events.
     */
    public List<BinaryTree> simulatePreviousGeneration(List<BinaryTree> currentGeneration) {
        //create a new list to store the new generation.
        List<BinaryTree> newGeneration = new ArrayList<>();
        int size = currentGeneration.size();

        //calculate the waiting time for the next coalescence event.
        double waitingTime = calculateWaitingTime(size);

        //continue combining pairs of lineages until only one lineage remains
        while (currentGeneration.size() > 1) {
            // randomly select the first parent from the current generation
            int parent1Index = random.nextInt(currentGeneration.size());
            BinaryTree parent1 = currentGeneration.remove(parent1Index);

            // randomly select the second parent from the remaining lineages
            int parent2Index = random.nextInt(currentGeneration.size());
            BinaryTree parent2 = currentGeneration.remove(parent2Index);

            // create a new tree representing the common ancestor of the two parents
            //the distance to the parents are set to the calculated waiting time
            BinaryTree newTree = new BinaryTree(parent1, parent2, waitingTime, waitingTime);
            //addnew tree to the new generation
            newGeneration.add(newTree);
        }
        //if there is an unpaired lineage left, we add it to the new generation
        if (!currentGeneration.isEmpty()) {
            newGeneration.add(currentGeneration.get(0));
        }

        return newGeneration;
    }

   /**
     * This method implements the iterative procedure of algorithm 7.5.2 of the lecture script. You don't have to change this code.
     *
     * @param size The initial population size.
     * @return A genealogy tree as {@link BinaryTree} instance.
     */
    public BinaryTree generateTree(int size) {
        List<BinaryTree> trees = new ArrayList<>();
        String generation = initialGeneration(size);
        for (char c : generation.toCharArray()) {
            trees.add(new BinaryTree(String.valueOf(c)));
        }
        while (trees.size() > 1) {
            trees = simulatePreviousGeneration(trees);
        }
        return trees.get(0);
    }

    public static void main(String[] args) {
        System.out.println("GBI - Assignment 06 Task 03 - " + String.join(", ", studentNames));
        PopGenSimulator simulator = new PopGenSimulator();
        int size = 6; // fixed size as specified
        BinaryTree tree = simulator.generateTree(size);
        System.out.println("Newick Format: " + tree.toNewick() + ";");

        BinaryTree tree2 = simulator.generateTree(size);
        System.out.println("Newick Format: " + tree2.toNewick() + ";");
        BinaryTree tree3 = simulator.generateTree(size);
        System.out.println("Newick Format: " + tree3.toNewick() + ";");

    }
}
