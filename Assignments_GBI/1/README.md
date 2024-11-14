[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/AkQrHP5P)
<sub>Prof. K. Nieselt and Prof. S. Nahnsen - Institute for Bioinformatics and Medical Informatics - University of Tübingen - SoSe 2024</sub>

# Grundlagen der Bioinformatik - Assignment 01

**Hand out:** Thursday, April 25, 6 pm

**Hand in:** Thursday, May 2, 6 pm

> 💡Please read the task descriptions carefully. If there are any questions, you may ask them during the tutorial session or in the forum of ILIAS. You will usually get an answer in time, but late e-mails (e.g. the evening of the hand-in) might not be answered in time. You can push local changes to your fork as often as necessary, but make sure your final solution is pushed before the deadline!

## Theoretical Assignments

> 💡Please provide your answer in this document. If you want to include pictures, please include only good quality pictures or scans. Make your life easier by using a markdown preview tool such as https://dillinger.io/ for editing. Please double check correct formatting on your GitHub fork before you hand in.

### 1. Substitution matrices (6P)

For the given observed alignment and the alphabet $\Sigma =$ $`\{ V, K, Q, D \}`$ compute the substitution matrix $S$.

<div align="center">

VKQDVQKDVD  
VKQDVQQDVD  
VKQDVKDVDV

</div>

Please hand-in the substitution matrix in a clear format as well as the intermediate steps on how you calculated the probabilities.

**Hint**: You can use https://www.tablesgenerator.com/markdown_tables to create a table in Markdown format.

### Answer

**Single probabilities**
- P(V)= 10/30
- P(K)= 5/30
- P(Q)= 6/30
- P(D)= 9/30


**Match with same probabilities**
- P(V,V)= 7/30
- P(K,K)= 3/30
- P(Q,Q)= 4/30
- P(D,D)= 5/30


**Match with different probabilities**
- P(V,K)= 0/30 = 0 bzw nicht def
- P(V,Q)= 0/30 = 0 bzw nicht def
- P(V,D)= 6/30
- P(K,Q)= 3/30
- P(K,D)= 1/30
- P(Q,D)= 1/30


**calculate every scoring functions using the formula**

**example for (V,V) cell:**

log calculations were made with: https://de.symbolab.com/solver/logarithmic-equation-calculator/%5Clog_%7B2%7D%5Cleft(%5Cfrac%7B%5Cleft(%5Cfrac%7B7%7D%7B30%7D%5Cright)%7D%7B%5Cleft(%5Cfrac%7B10%7D%7B30%7D%5Cright)%5Ccdot%5Cleft(%5Cfrac%7B10%7D%7B30%7D%5Cright)%7D%5Cright

**Resulting matrix:**
```
| 0 | V    | K     | Q     | D     |
|---|------|-------|-------|-------|
| V | 1.07 | null  | null  | 1     |
| K | null | 1.85  | 1.58  | -0.58 |
| Q | null | 1.58  | 1.74  | -0.85 |
| D | 1    | -0.58 | -0.85 | 0.89  |
```

## Practical Assignments

> 💡For the practical assignments you should keep a good structure in your code, e.g. implement separate functions to solve the sub-tasks presented. All code must be well documented. Points will be deducted for insufficient comments. If we can’t run your program, it will not be graded.

_All code and reference files are found within the repository, please implement your solution and push._

For this task, you **must** use the provided template. File paths must not be hard-coded, but it
must be possible to provide arbitrary files as input. Use the separate classes `FastaReader.java`
and `EditTranscript.java` for task 2 and 3.

### 2. Reading and Writing Sequences in FASTA Format (4P)

Implement a Java program that:

1. Reads entries from a given FASTA file that may contain one or more entries and extracts the corresponding header lines and sequences. Use the `Fasta.java` class to save this information, i.e., your program should handle the sequence data in a way that allows for subsequent processing. You are **not allowed** to use the FASTA-reader from any established library.
2. Computes the length, base frequency and RY-sequence (where R stands for the purine-based and Y for the pyrimidine-based nucleobases) for each of the saved entries.
3. Outputs the saved information in the following format to the terminal per entry:

```
Sequence Header: <String>
Length: <Integer>
Base Frequency: A=<Integer>, T=<Integer>, C=<Integer>, G=<Integer>
RY Sequence: <String>
```

Use the files `single-sequence.fasta` and `multi-sequence.fasta` provided in the `data` folder to test your program. Your program must run with:

`FastaReader.java file.fasta`

# Addition 

Currently the code can run in Git bash terminal:

Make sure you are in the correct folders otherwise navigate to them with "cd" command or right click and "open git bash here" on Win10

```
javac FastaReader.java
java FastaReader filepath
```
For example:
```
javac FastaReader.java

java FastaReader D:/GIT/assignment-1-maier-novikova/data/single-sequence.fasta
```
Code will show the test inputs in main before processing the path given trough command/terminal

### 3. Computation of the edit transcript using DP (10P)

Write a program that computes the edit transcript between two sequences using Dynamic Programming as discussed in the lecture. Your program should expect two sequences (i.e. strings) as input and report their edit transcript as output.

Use the two sequences

`CTTTACTAAGTACTGGATCTTATTTCAGCAAGATTTTTTATCTAAAAACAATGAGAGAAGTATTTGTTAAACCACATAGCTTTCATGTTTTGATCAAAAG`

`GACCGTTGGCGCCCGACCCTCAGGCTCTGTAGTGAGTTCCATGTCCGGGCCATTGCATGCGAGGTCGGTAGATTGATAGGGGACACGGAA`

to test your program. The result (edit transcript) should be printed to the console. Your program must run with:

`EditTranscript.java <String> <String>`

# Addition 
Currently the code can run in Git bash terminal:

Make sure you are in the correct folders otherwise navigate to them with "cd" command or right click and "open git bash here" on Win10

```
javac EditTranscript.java
java EditTranscript string1 string2
```
For example:
```
javac EditTranscript.java

java EditTranscript ATTAC GATTAG
```
Code will show the test inputs in main before processing the Strings given trough command/terminal