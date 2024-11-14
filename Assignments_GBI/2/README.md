<sub>Prof. K. Nieselt and Prof. S. Nahnsen - Institute for Bioinformatics and Medical Informatics - University of Tübingen - SoSe 2024</sub>

# Grundlagen der Bioinformatik - Assignment 2

**Hand out:** Thursday, May, 2nd, 6 pm

**Hand in:** Thursday, May, 9th, 6 pm

> 💡Please read the task descriptions carefully. If there are any questions, you may ask them during the tutorial session or in the forum of ILIAS. You will usually get an answer in time, but late e-mails (e.g. the evening of the hand-in) might not be answered in time. You can push local changes to your fork as often as necessary, but make sure your final solution is pushed before the deadline!

## Theoretical Assignments

> 💡Please provide your answer in this document. If you want to include pictures, please include only good quality pictures or scans. Make your life easier by using a markdown preview tool such as https://dillinger.io/ for editing. Please double check correct formatting on your GitHub fork before you hand in.

### 1. Pairwise Sequence Alignment by Hand (4P)

1.  For the two sequences $s_1 = {\sf ATGCAC}$ and $s_2={\sf ATGACGACT}$, compute an optimal global alignment using the Needleman-Wunsch algorithm. For the computation, use a match-, mismatch-score and gap-penalty of $1$, $-1$ and $2$, respectively.
2.  Modify the Needleman–Wunsch dynamic programming algorithm, such that it finds the longest common sequence between two sequences.

### Answer

````
1.

| F |     | A  | T  | G  | A  | C   | G   | A   | C   | T   |
|---|-----|----|----|----|----|-----|-----|-----|-----|-----|
|   | 0   | -2 | -4 | -6 | -8 | -10 | -12 | -14 | -16 | -18 |
| A | -2  | 1  | -1 | -3 | -5 | -7  | -9  | -11 | -13 | -15 |
| T | -4  | -1 | 2  | 0  | -2 | -4  | -6  | -8  | -10 | -12 |
| G | -6  | -3 | 0  | 3  | 1  | -1  | -3  | -5  | -7  | -9  |
| C | -8  | -5 | -2 | 1  | 2  | 2   | 0   | -2  | -4  | -6  |
| A | -10 | -7 | -4 | -1 | 2  | 1   | 1   | 1   | -1  | -3  |
| C | -12 | -9 | -6 | -3 | 0  | 3   | 1   | 0   | 2   | 0   |

Von unten links:

0 -> 2 -> 1 -> 0 -> 2 -> 1 -> 3 -> 2 -> 1 -> 0

Optimal global alignment:
ATG-C-AC-
ATGACGACT




2.

we "ignore" mismatch/gappenalty because we only care if a char of the string matches the other
match = 1
mismatch = 0
gap-penalty = 0


Pseudocode: 

 LongestCommonSubsequence(seq1, seq2)
 
    //get the length of the sequences
    Initialize m as the length of seq1
    Initialize n as the length of seq2

    //initialize a 2D array to keep track of the lengths of common subsequences
    Initialize dp as a 2D array of size (m+1) x (n+1) with all values as 0

    //variables to keep track of the maximum length and ending position of the longest common subsequence
    Initialize max_length as 0
    Initialize end_pos as 0

    //iterate over the sequences
    For i from 1 to m
        For j from 1 to n
        
            //if the current characters match, increment the length of the current common subsequence
            If seq1[i-1] equals seq2[j-1]
                Set dp[i][j] to dp[i-1][j-1] + 1
                
                //if this is the longest common subsequence so far, update max_length and end_pos
                If dp[i][j] is greater than max_length
                    Set max_length to dp[i][j]
                    Set end_pos to i
                    
            //if the current characters do not match, end the current common subsequence
            Else
                Set dp[i][j] to 0

    //rturn the longest common subsequence
    Return the substring of seq1 from position (end_pos - max_length) to end_pos




````



### 2. BLAST - Theoretical Considerations (4P)

1. Discuss briefly the impact of word size on a BLAST search. Cases to consider include changing the inital word-size, de-/increase in size, which impact short sequences might have. What happens when the word-size is almost the query size?
2. How do the E-value and the Bit score S' relate to each other? (Hint: Proof Equation 4.6 from the script).

### Answer

`````
Changing initial word size:
Decreasing the initial word size increases sensitivity but also it takes longer to compute. 
With smaller word sizes we can find more matches ( maybe capturing more distant homologs, but it also increases the chance of random matches.)
Increasing the initial word size reduces sensitivity but improves efficiency/ speed. 
Larger word sizes lead to less and more significant matches, but we might miss some distant homologs.

Impact on short sequences:
For short query sequences, a smaller word size would be better. 
This is because shorter sequences have fewer potential matching regions, 
and a smaller word size helps capture these limited matches more effectively.

Word-size almost the query size:
When the word size is almost the same as the query size, 
it reduces the BLAST search to a local alignment of the query with/against itself.
(maybe useful for identifying regions of self-similarity within the query sequence itself)





How do the E-value and the Bit score S' relate to each other? (Hint: Proof Equation 4.6 from the script).
As the E-value decreases (indicating higher significance), the bit score tends to increase (indicating a stronger match)



`````
![proof_E_S](https://github.com/GBI-teaching/assignment-2-maier-novikova/assets/148355768/c521f40a-060c-4a84-b6c1-fed6a72e5797)




## Practical Assignments

> 💡For the practical assignments you should keep a good structure in your code, e.g. implement separate functions to solve the sub-tasks presented. All code must be well documented. Points will be deducted for insufficient comments. If we can’t run your program, it will not be graded.

_All code and reference files are found within the repository, please implement your solution and push._ You can use the pre-implemented `FastaReader` and `Fasta` classes to read FASTA files or use the code you have implemented for assignment one. Feel free to recycle your code for the dynamic programming matrix and traceback computation for task four.

### 3. Frame Translation and BLAST (6P)

The first part of this task asks you to implement the `TranslateNucleotides.java` class, that reads a single nucleotide sequence from a FASTA format file, translates it with respect to all possible frames (cf. chapter 4, slide 23 of the lecture) and writes the resulting aminoacid sequences into a FASTA format file. For this:

- Implement the `writeOutFasta` method of the `FastaIO.java` class to write FASTA format files.
- Familiarize yourself with the `CodonTable.java` class and its functions.
- Implement the `translateNucleotides` method of the `TranslateNucleotides.java` class.

Next, apply your program to the sequence in `data/unknown.fasta` and the codon table `data/codon_table.txt`. Your program must run with:

`TranslateNucleotides </path/to/input/sequence.fasta> </path/to/codon_table.txt> </path/to/output/sequences.fasta>`

Identify the nucleotide sequence provided in the file `data/unknown.fasta` using [BLASTp](https://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastp&PAGE_TYPE=BlastSearch&BLAST_SPEC=&LINK_LOC=blasttab&LAST_PAGE=blastp) based on the translated sequences computed with your implemented program. Query against the `nr` database. From the results of the BLAST search, discuss the following question:

- From which organism does the target sequence probably come from?
- What is the function of the protein?
- How trustworthy are the results of your search? Try to argument using the different search values (such as _E-value_, _percentage identity_, etc.)
- How about the other hits? Do they confirm the result of your search?
- Which alternative blast program could be used to identify the origin of the unknown target sequence and why?

_Please limit your answers to a maximum of 3-4 sentences per subtask!_


### Answer

````

used the longest ORF the code computed 
"MDDLMLSPDDIEQWFTEDPGPDEAPRMPEAAPPVAPAPAAPTPAAPAPAPSWPLSSSVPSQKTYQGS
YGFRLGFLHSGTAKSVTCTYSPALNKMFCQLAKTCPVQLWVDSTPPPGTRVRAMAIYKQSQHMTEVVR
RCPHHERCSDSDGLAPPQHLIRVEGNLRVEYLDDRNTFRHSVVVPYEPPEVGSDCTTIHYNYMCNSSC
MGGMNRRPILTIITLEDSSGNLLGRNSFEVRVCACPGRDRRTEEENLRKKGEPHHELPPGSTKRALPN
NTSSSPQPKKKPLDGEYFTLQIRGRERFEMFRELNEALELKDAQAGKEPGGSRAHSSHLKSKKGQSTS
RHKKLMFKTEGPDSDO"

another one that showed results
MSAAQIAMVWPLLSILSEWKEICVWSIWMTETLFDIVWWCPMSRLRLALTVPPSTTTTCVTVPAWAAO

- From which organism does the target sequence probably come from?
Best and most results say its the Homo sapiens, so its probably from a human (or a near relative like the gorilla)

- What is the function of the protein?
Tumor antigens are molecules found on cancer cells that trigger an immune response. 
They are essentially flags that tell the immune system to attack the cancer cells

- How trustworthy are the results of your search? Try to argument using the different search values (such as _E-value_, _percentage identity_, etc.)
Very trustworthy, since the max Scroe representing the bit Score is 738, the E-Value is 0. 
This means the number E of local alignments (HSPs) achieved by chance with score of at least S (738) is 0. 
The lower the E value, the lower the P-value gets (probability of finding at least one HSP "by chance") in our case 0.
The percentage identity parameter describes how similar the Seq. are to each other, in our case 100%. 
So the result is very precise and trustworthy. 

- How about the other hits? Do they confirm the result of your search?
for some of the smaller ones: "No significant similarity found."
for the one we did find two: 
p53 transformation suppressor [Homo sapiens]
TP53 [Homo sapiens]
which both also show in the search from our longest ORF but much lower in the list.
these also now have a E value (5e-29) and the bit scores are much lower (114), but still very unlikley to have a HSP hit by chance.
Also percentage identity are 100% or near that. So yes you could say these confirm the result of the search.


- Which alternative blast program could be used to identify the origin of the unknown target sequence and why?
BLASTx could be used to search nucleotide databases using a nucleotide query. 
This might be useful for new unknown (novel) genes and you want to find similar genes in other organisms.


````

### 4. Smith-Waterman (6P)

In this task, you are asked to implement a program that applies the Smith-Waterman algorithm with linear gap penalties to two DNA sequences to compute an optimal local alignment. The sequences should be read from FASTA files, each containing one sequence. The parameters for the file input, match score, mismatch score, and gap penalty should be parsed from the command line.

- Implement your program to the point where the DP matrix is correctly initialized, filled in and the optimal local alignment score is output to the console.
- Extend your program such that a traceback is computed while or after filling in the DP matrix. Your extended program should reconstruct _one_ optimal local alignment and write the aligned sequences to the console.

Apply your program to the sequences `data/sequence_1.fasta` and `data/sequence_2.fasta` and the scoring parameters: _s_(_a_, *b*) = −3 if *a* ≠ *b* and _s_(_a_, *a*) = +3 and *d* = 5. Your program must run with:

`SmithWaterman <matchScore> <mismatchScore> <gapPenalty> </path/to/sequence1.fasta> </path/to/sequence2.fasta>`

and produce output like

```
GBI - Assignment 02 Task 04 - StudentA, StudentB
Optimal local alignment score: <Number>
Aligned sequence one: <String>
Aligned sequence two: <String>
```
