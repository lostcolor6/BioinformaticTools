[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/eEJSD-E2)
<sub>Prof. K. Nieselt and Prof. S. Nahnsen - Institute for Bioinformatics and Medical Informatics - University of Tübingen - SoSe 2024</sub>

# Grundlagen der Bioinformatik - Assignment 09

**Hand out:** Thursday, June, 27, 18:00

**Hand in:** Thursday, July, 4, 18:00

> 💡Please read the task descriptions carefully. If there are any questions, you may ask them during the tutorial session or in the forum of ILIAS. You will usually get an answer in time, but late e-mails (e.g. the evening of the hand-in) might not be answered in time. You can push local changes to your fork as often as necessary, but make sure your final solution is pushed before the deadline!

## Theoretical Assignments

> 💡Please provide your answer in this document. If you want to include pictures, please include only good quality pictures or scans. Make your life easier by using a markdown preview tool such as https://dillinger.io/ for editing. Please double check correct formatting on your GitHub fork before you hand in.

### 1. Applying Viterbi (6P)

In this task you are asked to work on a HMM to distinguish exonic and intronic gene regions from nucleotide sequences: You are given the problem to decode the most likely path sequence of an HMM with the states `exon (E)` and `intron (I)` for the sequence `AATCC` using the Viterbi algorithm. The HMM is (informally) specified as follows:
  - Begin state `(B)`, from which the exon state is reached with probability $1$.
  - A probability $10^{-3}$ for the chain to end for both states (`(E)` and `(I)`).
  - The likelihood to stay in the exon state is $0.8$ and to change from exon state to intron state is $0.2$.
  - The likelihood to stay within intron state is $0.95$ and to change from intron state to exon state is 0.05.
  - The likelihood for the emission of the symbols (A,G,T,C) from the exon state is ($0.25, 0.2, 0.3, 0.25$) and, respectively, ($0.15, 0.3, 0.2, 0.35$) are the emission probabilities for (A,G,T,C) for the intron state. 

(a) Provide the HMM and draw its topology.

(b) Manually decode the most likely path sequence of states for the sequence `AATCC`, using the Viterbi algorithm.

### Answer
![photo1720105715](https://github.com/GBI-teaching/assignment-9-maier-novikova/assets/167859658/13930a8a-db37-40c9-b9d1-c728363bd6d9)
![photo1720107348](https://github.com/GBI-teaching/assignment-9-maier-novikova/assets/167859658/d66d6191-514b-4982-bea8-2cad888f32c2)

## Practical Assignments

> 💡For the practical assignments you should keep a good structure in your code, e.g. implement separate functions to solve the sub-tasks presented. All code must be well documented. Points will be deducted for insufficient comments. If we can’t run your program, it will not be graded. _All code and reference files are found within the repository, please implement your solution and push. Ensure that your final solution is merged into the main branch of your repository._

### 2. Hidden Markov Models (14P)

This task builds upon the _Markov Chains_ task from Assignment 8. **Recap**: The genome sequences on the planet Vogsphere consist of two nucleotides R and Y, and can be divided in **genic** and **non-genic** regions. In the previous task you already computed the transition probabilities of the two nucleotides for **genic** and **non-genic** regions, based on the training sequences. With the computed transition matrices you were able to discriminate if a given sequence x is more likely to originate from a **genic** or a **non-genic** region. Now we want to be more precise and localize **genic** and **non-genic** regions in a genome that we newly sequenced on this planet. To achieve this, you need to build a Hidden Markov Model (HMM).

We have provided you with some pre-implemented classes that you can use to solve the task:

- **FastaIO.java** implements methods to read a fasta file.
- **HMM.java** is a class that is used to represent a HMM.

Furthermore, please use the following transition probabilities: 

| From \ To | Genic (G) | Non-Genic (N) | End (E) |
|-----------|------------|---------------|---------|
| Begin (B) | 0.5        | 0.5           | 0.0     |
| Genic (G) | 0.8        | 0.19          | 0.01    |
| Non-Genic (N) | 0.24   | 0.75          | 0.01    |

  (a) What is the emission alphabet $Σ$ and what is the set of states $Q$? 

  ### Answer
  ````
  $Σ$ = {R, Y}
  
  
  $Q$ = {B, N, G, E} 
  start/begin (B), non-genic (N), genic (G) and the end (E)
  ````
  ### Note
  ````
  checkValid was commented out in HMM because apparently the rows of the transitionMatrix don't add up to 1?
  Probably an error in the transition or emission matrix and therefore the wrong output. ;(
  ````


  (b) **Calculate Emission Probabilities** \
  Implement a function that calculates the emission probabilities for the symbols under a given state using the training sequences in `data/train_genic.fasta` and `data/train_non-genic.fasta`. Please use the code template `src/Main.java`.

  (c) **Set up the HMM** \
  Use the HMM class to store the information of the states, symbol alphabet as well as emission- and transition matrices in `src/Main.java`.

  (d) **Implement the Viterbi Algorithm** \
  With the ready HMM, implement the Viterbi algorithim in the provided code template '`src/Viterbi.java` and predict for the given sequence `data/test.fasta` the **genic** and **non-genic** regions and print it to the console.
  Your output should look like this : 
  ```
  > Symbols: YRYYYYRRYYYYYRRRYY...
  
  > States : GGGGGNNGNGNGGGNGNG...
  ```

  (e)  **Computational Underflow** \
  Using the Viterbi algorithm for long sequences, there is the potential for a computational underflow. Think of a possibility to avoid this issue. Explain your solution briefly and modify the recursion accordingly.

### Answer
````
Instead of working with probabilities directly, we can use the logarithm:

-> log converts multiplications into additions (more stable and mostly better runtime)
-> log of small probabilities can result in a lower risk of underflow (they stay within a manageable range of negative values.)

changes:

initialization: 
we initialize the viterbi scores and backpointers as usual, but use logarithms of initial probabilities and transition probabilities.

recursion:
update the log probability of each state based on the maximum log probability of reaching the current state from any of the previous states:

log_prob[i,j] = max_k(log_prob[i-1,k] + log P(x_i ∣ x_(i-1) = k))

````



