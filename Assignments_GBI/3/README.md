[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/-tTOVqQv)
<sub>Prof. K. Nieselt and Prof. S. Nahnsen - Institute for Bioinformatics and Medical Informatics - University of Tübingen - SoSe 2024</sub>

# Grundlagen der Bioinformatik - Assignment 3

**Hand out:** Thursday, May, 9, 6pm

**Hand in:** Thursday, May, 16, 6pm

> 💡Please read the task descriptions carefully. If there are any questions, you may ask them during the tutorial session or in the forum of ILIAS. You will usually get an answer in time, but late e-mails (e.g. the evening of the hand-in) might not be answered in time. You can push local changes to your fork as often as necessary, but make sure your final solution is pushed before the deadline!

## Theoretical Assignments

> 💡Please provide your answer in this document. If you want to include pictures, please include only good quality pictures or scans. Make your life easier by using a markdown preview tool such as https://dillinger.io/ for editing. Please double check correct formatting on your GitHub fork before you hand in.

### 1. Sum of pair scores (4P)

Consider a position/column $i$ in a sum of pairs (SP)-optimal multi-alignment $A∗$ that is constant, i.e. has the same residue in all $r$ sequences.

- What happens when we add a new sequence?

If the number $r$ of aligned sequences is small, then we would not be too surprised if the new sequence shows a different residue at the previously constant position $i$.
However, if the number of sequences is large, then we would expect the constant position $i$ to remain constant, if possible.

- Prove that, unfortunately, the SP score favors the opposite behavior: The more sequences there are in an MSA, the easier it is, relatively speaking, for a differing residue to be placed in an otherwise constant column.

Your task is to set up an equation that relates the SP scores of these two types of columns (adding same residue vs. adding different residue), and compute its relative difference w.r.t. the score of the constant column. Discuss the behavior of this equation.

Hint: Take the column $i$, but add a mismatching residue $b \neq a$. Then look at the relative difference of the scores.

### Answer

![photo1715858823](https://github.com/GBI-teaching/assignment-3-maier-novikova/assets/167859658/33be6c85-4791-422b-8910-c1f488644a6d)

We see that the larger the value of r, the smaller the relative difference. 
This means that SP scoring tends to accept mismatches in constant columns, especially with a larger number of sequences. 
The drawback of the Sum-of-Pairs (SP) score is particularly evident in situations where the precise identification of conserved positions in biological sequences is crucial, as the tendency of the SP score to accept mismatches in constant columns can compromise the reliability of the analysis.

### 2. An exact multiple sequence alignment (2P)

For the computation of an optimal multiple sequence alignment (MSA) of $n$ sequences, you need an $n$-dimensional matrix (i.e. an $n$-dimensional hypercube). A resulting alignment corresponds to a path through the hypercube. For the following MSA of 3 nucleotide sequences

```
Sequence X: AATG
Sequence Y: A-TG
Sequence Z: --TG
```

the path is: $(0, 0, 0), (1, 1, 0), (2, 1, 0), (3, 2, 1), (4, 3, 2)$.

- Provide the coordinates of the cells $(x, y, z)$ from the 3D-hypercube that were taken into account in the computation of the values of the cells $(3, 2, 1)$ and $(4, 3, 2)$.

### Answer

For the cell (3, 2, 1):
```
F(2, 1, 0) + s(A, -, -)
F(2, 1, 1) + s(A, -, T)
F(2, 2, 0) + s(A, T, -)
F(3, 1, 0) + s(G, -, -)
F(3, 1, 1) + s(G, -, T)
F(3, 2, 0) + s(G, T, -)
```

For the cell $(4, 3, 2)$:
```
F(3, 2, 1) + s(A, T, -)
F(3, 2, 2) + s(A, T, G)
F(3, 3, 1) + s(A, -, -)
F(3, 3, 2) + s(A, -, G)
F(4, 2, 1) + s(G, T, -)
F(4, 2, 2) + s(G, T, G)
F(4, 3, 1) + s(G, -, -)
F(4, 3, 2) + s(G, -, G)
```


### 3. MSA: The human kinome? (4P)

You are asking the question "How similar are all kinases that are expressed from the human genome on the protein level?". Refer to [this article](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC4128285/) to learn about the human kinome. To find the similarity you choose to do a pairwise alignment of all possible combinations using dynamic programming (DP). For simplicity, assume all kinases are of length $L$. Further, assume that every pairwise alignment needs 10 seconds to run.

- How long would the experiment run? What alternative method would you suggest to the all vs. all alignment (using a pairwise DP) to reduce the runtime?

Your experiment hints to the fact that only little similarity is visible among the kinases.

- What parameters in your experiment should be changed so that more similarity gets visible?

Eventually you identified the right parameters and your MSA is concluded.
- Why would one do such an analysis? What can you learn from an MSA of all human kinases? Which results of the article above should be reflected in your MSA?

### Answer

1. First, we calculate the number of pairwise alignments using the formula r(r-1)/2, which for us is 518*517/2 = 133,903 pairs. Assuming each pairwise alignment takes 10 seconds, the total duration would be 133,903 * 10 seconds = 1,339,030 seconds, approximately 37 hours. 
Additionally, we can express the runtime complexity using the known formula O(r^2 * n^r * 2^r), which for us would be O(518^2 * n^518 * 2^518) <=> O(n^518). 
An alternative method for alignment would be one of the progressive alignment methods, which also utilize pairwise alignment, for example, ClustalW with a runtime complexity of O(n^2).

2. To make the similarities between kinases more noticeable, you have the option to make changes to the following parameters:

- Length constraints: Reduce the minimum length requirement for sequences to include even shorter segments

- Gap penalties: Decrease the penalties for introducing gaps, allowing for a higher tolerance towards gaps and better identification of potential similarities

- Adjustment of match and mismatch scores: Adjust the scores assigned to matches and mismatches to give more emphasis to similar sequences

- Reduction of filtering: Relax the filtering of sequences to include more sequences in the analysis and not miss potential similarities

3. A comprehensive analysis of all human kinases allows for the identification of conserved regions that represent functional domains and motifs. 
It provides insights into the evolutionary relationships between kinase families und helps in the annotation of functionally important areas.
Additionally, it contributes to the identification of potential drug targets, which can support the development of targeted therapeutics, what was described in the above article.

The article reflects that the kinase domain is approximately 250 amino acids in length and is further classified into eight major groups based on sequence similarity within this domain

## Practical Assignments

> 💡For the practical assignments you should keep a good structure in your code, e.g. implement separate functions to solve the sub-tasks presented. All code must be well documented. Points will be deducted for insufficient comments. If we can’t run your program, it will not be graded. _All code and reference files are found within the repository, please implement your solution and push. Ensure that your final solution is merged into the main branch of your repository._

### 4. Progressive alignment (10P)

In this task you are asked to implement the program `ProgressiveAlignment.java` to compute a multiple sequence alignment using a progressive pair-guided approach as explained in the lecture Chapter 5, slides 6 to 30. As this is quite a complex task, we provide you with a set of pre-implemented code:

- The `FastaIO.java` and `Fasta.java` classes can be used to read sequences from FASTA format files.
- The `Profile.java` class is a container to store a list of one unaligned or multiple already aligned sequences.
- The `SequenceAlignment.java` class comprises the `static` method `adaptedNeedlemanWunsch` to compute a global pairwise sequence alignment of sequences that already contain gap symbols. The results will be returned as an `AlignedSequences.java` instance.

Continue by following the subtasks below:

1. Start by implementing a method to parse a list of sequences from a FASTA format file specified via a CLI and return those as a list of `Profile.java` instances.
2. Implement the `static` `pairGuidedAlignment` method of the `SequenceAlignment.java` class. The method accepts two `Profile.java` instances as well as two integer values $i$ and $j$ as input and combines the two alignment profiles as follows:
   - Choose sequence $i$ of the first profile and sequence $j$ of the second profile.
   - Compute a global sequence alignment of these two sequences with the modified version of the Needleman-Wunsch algorithm.
   - Insert the respective new gaps into the two profiles (cf. slide 23 and 24) and return the combined profile.
3. Finally, you need to implement the _progressive_ part of the MSA computation. For this, we propose a very naive algorithm based on the idea of [Kruskal's algorithm](https://en.wikipedia.org/wiki/Kruskal%27s_algorithm), but we encourage you to find your own, more sophisticated solution. Please implement the `computeMSA` method of the `ProgressiveAlignment.java` class. The method accepts a list of `Profile.java` instances and proceeds as follows:
   - Select the pair of profiles with the highest global alignment score from the list. To do this for a pair of profiles, you can use the adapted Needleman-Wunsch algorithm and randomly select a representative sequence per profile.
   - Merge the two profiles with the implemented `pairGuidedAlignment` method and update the list.
   - Repeat the first and second step until only one entry is left in the list.
4. Implement a method to print the final MSA to the console.
5. Apply your program to the sequences in the file `data/sequences.fasta` with the following parameters: $s(a, b) = −2$ if $a \neq b$, $s(a, a) = +3$ and $d = +4$.
6. Review the results of your run: Describe concisely (200 words maximum) how your progressive alignment program could be improved.

### Answer

`<Provide answer here>`

Your final implementation should run with `ProgressiveAlignment <matchScore> <mismatchScore> <gapPenalty> </path/to/sequences.fasta>`

and produce output like (each aligned sequence ends with an EOL symbol!):

```
AL-VK
APF-K
ALFVK
```
