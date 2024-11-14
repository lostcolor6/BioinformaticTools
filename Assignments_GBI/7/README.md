[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/qobWpDHN)
<sub>Prof. K. Nieselt and Prof. S. Nahnsen - Institute for Bioinformatics and Medical Informatics - University of Tübingen - SoSe 2024</sub>

# Grundlagen der Bioinformatik - Assignment 07

**Hand out:** Thursday, June, 13, 6 pm

**Hand in:** Thursday, June, 20, 6 pm

> 💡Please read the task descriptions carefully. If there are any questions, you may ask them during the tutorial session or in the forum of ILIAS. You will usually get an answer in time, but late e-mails (e.g. the evening of the hand-in) might not be answered in time. You can push local changes to your fork as often as necessary, but make sure your final solution is pushed before the deadline!

> 📈 For this assignment, you will be asked to submit plots. You can use a framework of your choice or draw the plots (correctly scaled and recognizable) by hand. You do not need to submit the code to create the plots. We recommend the use of [WolframAlpha](https://www.wolframalpha.com); You may want to take a look at the output of the commands "boxplot 525.859 535.268 549.38" and "plot y=3x-5 from 10 to 50".


## Theoretical Assignments

> 💡Please provide your answer in this document. If you want to include pictures, please include only good quality pictures or scans. Make your life easier by using a markdown preview tool such as https://dillinger.io/ for editing. Please double check correct formatting on your GitHub fork before you hand in.

### 1. Phred Quality Score (2P)

The Phred Quality score introduced in the lecture, is a measure to assess the quality individual bases in sequencing reads.

(a) **Q-Score Table** Set up a table summarizing the following details for the given Phred Quality scores: $Q =1, 5, 10, 20, 30, 35, 40$
 - The probability of incorrect base calls expressed as `1 in ? bases`.
 - The ASCII_BASE 33 encoding of the respective Q-scores

#### Answer

| Q-score | Probability                            | ASCII_BASE=33           |
|---------|----------------------------------------|-------------------------|
| 1       | 1 in 1.259                             | " (ASCII 34)            |
| 5       | 1 in 3.162                             | & (ASCII 38)            |
| 10      | 1 in 10                                | + (ASCII 43)            |
| 20      | 1 in 100                               | 5 (ASCII 53)            |
| 30      | 1 in 1000                              | ? (ASCII 63)            |
| 35      | 1 in 3162                              | D (ASCII 68)            |
| 40      | 1 in 10000                             | I (ASCII 73)            |



(b) **Q-scores for sequences** For the following read in fastq format:

 > @Sequence \
 >GATTCGGGGCAG \
 >+seq \
 >8=F@HII51#&)

 - What are the Q-scores for this read? 
 - Draw the corresponding box-plot for the Q-scores.

#### Answer

According to ACII_BASE=33, Q = 23, 28, 37, 31, 39, 40, 40, 20, 16, 2, 5, 8

![Rplot01](https://github.com/GBI-teaching/assignment-7-maier-novikova/assets/167859658/f189801c-d288-4ec1-80dc-a948b0170752)

### 2. Sequencing approaches (2P)

Inform yourselves on `one` of the following next-generation sequencing technologies: 

 - Solexa/Illumina Sequencing
 - PacBio Sequencing
 - Oxford Nanopore Sequencing

Summarize the most important characteristic of your chosen technology in a nutshell. For this, hand-in 3-4 bullet points (at most one sentence per bullet point) with the core information of the chosen technology and cite your sources. 

### Answer

The characteristic of Solexa/Illumina Sequencing:

- Utilizes reversible terminators: The utilization of reversible terminators involves the integration of fluorescently labeled nucleotides, which have the ability to temporarily halt the extension of DNA strands after the addition of each nucleotide, enabling high accuracy and parallel processing.

- High Throughput: Generates millions of short reads simultaneously, allowing comprehensive coverage of genomes or transcriptomes in a single sequencing run.

- Base-by-Base Analysis: Illumina sequencing analyzes DNA sequences one base at a time, ensuring high accuracy and precise determination of nucleotide sequences.

- Similarity to Sanger Sequencing: Involves temporary termination of DNA strand extension after each incorporation of a modified nucleotide.

https://www.yourgenome.org/theme/what-is-the-illumina-method-of-dna-sequencing/
https://www.cd-genomics.com/resource-illumina-ngs-principles-and-workflow.html

## Practical Assignments

> 💡For the practical assignments you should keep a good structure in your code, e.g. implement separate functions to solve the sub-tasks presented. All code must be well documented. Points will be deducted for insufficient comments. If we can’t run your program, it will not be graded. _All code and reference files are found within the repository, please implement your solution and push. Ensure that your final solution is merged into the main branch of your repository._

### 3. Contigs versus coverage (6P)
When using the shotgun method, short (partial) sequences (reads) randomly distributed over
the genome are determined in the sequencing phase. They are combined into larger contigs in the
assembly phase. The following equation states the relationship between the number of fragments
and the expected number of contigs.
For this let:
>
> - $G$ = genome length in base pairs
> - $L$ = mean length of a read
> - $N$ = number of sequenced reads
> - $c$ = $\frac {LN} G$ = expected coverage
> - $T$ = length of the overlap between two reads
> - $θ$ = $\frac TL$, the proportion that two reads must overlap to be concatenated 
>
> Then according to the Lander-Waterman model the expected number of contigs is:\
> $Ne^{−c(1−θ)}$

For each of the four different theta, $θ ∈$ \{0.25, 0.35, 0.5, 0.65\}, generate a plot of this function
against the coverage $0 ≤ c ≤ 20$. Scale the y-axis in units $\frac GL$, independent of the genome size
itself.
Explain the plots (you do not need to explain the equation nor the Lander-Waterman model,
only the resulting plots).

### Answer

Used R for plotting. (used own parameters for N, L and G, see below ind code)


Code:


````
# parameters
N <- 1000000  # number of sequenced reads
L <- 100      # mean length of a read (in base pairs)
G <- 1000000  # genome length (in base pairs)

# theta values
theta_values <- c(0.25, 0.35, 0.5, 0.65)

# toverage range
c_values <- seq(0, 20, by = 0.1)

# function to calculate expected number of contigs
expected_contigs <- function(N, L, G, c, theta) {
  return(N * exp(-c * (1 - theta)))
}

# plotting
par(mfrow=c(2, 2))  # Arrange plots in a 2x2 grid

for (theta in theta_values) {
  contig_counts <- sapply(c_values, function(c_val) {
    expected_contigs(N, L, G, c_val, theta)
  })
  
  plot(c_values, contig_counts, type = "l",
       xlab = "Coverage (c)", ylab = "Expected Contigs (G/L)",
       main = paste("Theta =", theta))
}


````


Resulting Plots:


![image](https://github.com/GBI-teaching/assignment-7-maier-novikova/assets/148355768/7ec63760-e52c-436b-afdc-dff507a53500)



Explanation of the Plots:


Theta = 0.25: 

- This plot will show a rapid decline in the number of expected contigs as coverage c increases (indicating a high likelihood of assembling longer contigs with lower coverage)

Theta = 0.35:

- Similar to theta = 0.25 but with a slightly "shallower" decline (indicating a moderate overlap requirement between reads for contig assembly)

Theta = 0.5: 

- The decline in expected contigs with increasing coverage is more gradual, which could mean, that reads with a 50% overlap are still capable of forming contigs even at higher coverage levels.

Theta = 0.65: 

- This plot will show the most gradual/ slowest decline in expected contigs with increasing coverage (reads with a substantial overlap (65%) could still probably form contigs effectively even at higher coverage levels)



### 4. Sequencing Read Quality Control (10P)

In this exercise you will simulate 3 sets of reads, perform a quality control on the reads, and compare the results of the different simulations. For each of the sub-tasks include the command you used to solve the task.

(a) Make yourself familiar with the tool [ART](https://www.niehs.nih.gov/research/resources/software/biostatistics/art) for read simulation. Based on the provided fasta file `/data/CP004010.fasta`, generate 3 sets of `paired-end` reads that have length of `150bp` and are generated with an `Illumina HiSeq 2500`. To run the tool properly you need to provide the uncompressed fasta file as input. For the remainder of the tasks you will only need the FastQ files. Change the coverage and quality for each run:

- Run 1: Coverage: 30X, shift the quality score by -20 for the first read and -35 for the second read.
- Run 2: Coverage: 20X, shift the quality score by 7 for the first read and 5 for the second read.
- Run 3: Coverage: 10X, shift the quality score by 2 for the first read and 3 for the second read.

In addition, set `-m 300 -s 20 -k 0 -na`.

(b) Compress your fastq files (e.g. with bgzip2). Hand-in the compressed files. Also, report the full command you used to generate each read set, as well as explain each parameter.

### Answer 

compressed files in reportData folder

commands used:

````
#RUN1:
./art_illumina -ss HS25 -i D:/BioInformatikTools/Run1/CP004010.fasta -p -l 150 -f 30 -m 300 -k 0 -s 20 -qs -20 -qs2 -35 -na -o run1_30X_qs-20_-35

#RUN2:
./art_illumina -ss HS25 -i D:/BioInformatikTools/Run1/CP004010.fasta -p -l 150 -f 20 -m 300 -k 0 -s 20 -qs 7 -qs2 5 -na -o run2_20X_qs7_5

#RUN3:
./art_illumina -ss HS25 -i D:/BioInformatikTools/Run1/CP004010.fasta -p -l 150 -f 10 -m 300 -k 0 -s 20 -qs 2 -qs2 3 -na -o run3_10X_qs2_3
````

parameters explained:


````
-ss HS25: `specifies the Illumina HiSeq 2500 sequencing system.
-i .../CP004010.fasta: Path to the input FASTA file
-p: indicates paired-end read simulation.
-l 150: Length of reads (150bp).
-f 30/20/10: Fold coverage (30X, 20X, 10X).
-m 300: mean size of the DNA fragments.
-s 20: standard deviation of the DNA fragment size.
-qs -20/7/2: quality score shift for the first read.
-qs2 -35/5/3: quality score shift for the second read.
-na: disables the output of the ALN alignment file.
-o [output_prefix]: output file prefix for each run.
````




(c) Investigate the quality of your reads using [FastQC](https://www.bioinformatics.babraham.ac.uk/projects/fastqc/) on each file.


### Answer

As you can see Run 1 has a poor quality (with the first one of the 30X beeing slightly better then the second)

![image](https://github.com/GBI-teaching/assignment-7-maier-novikova/assets/148355768/f9220232-94a6-4efb-9cf2-edd60297b99d)


![image](https://github.com/GBI-teaching/assignment-7-maier-novikova/assets/148355768/095a4423-3411-4867-bd02-e3893fb1683a)


![image](https://github.com/GBI-teaching/assignment-7-maier-novikova/assets/148355768/0ddd3a84-c69f-4932-a05b-930ae0f4c169)


![image](https://github.com/GBI-teaching/assignment-7-maier-novikova/assets/148355768/7b0b9f1d-fc4f-4d71-8314-559c12849ca8)


![image](https://github.com/GBI-teaching/assignment-7-maier-novikova/assets/148355768/f998cb83-cea8-40cd-bf93-1b9ec774d438)


![image](https://github.com/GBI-teaching/assignment-7-maier-novikova/assets/148355768/0ca6aba7-9006-4682-be74-1b31aaee49e8)



(d) Summarize all FastQC results into one common HTML report using [MultiQC](https://multiqc.info/). Hand in the created HTML report as an additional file.

### Answer


html file in folder dataReport

command used: 

````
multiqc D:/BioInformatikTools/GenomeSeqTools/art_bin_MountRainier/Runs/FastaQCReports
````

(e) Discuss the quality of the three different paired-end sequencing experiments. What is the impact of the quality scores of each run? Include meaningful figures from the MultiQC report in your discussion. In your text, make sure that you correctly refer to the included figures. How certain would be the results of an assembly for each of the different sequencing runs?

Write about maximally 300 words.

### Answer

````
As general Assumptions we can say:

Run 1: Low certainty due to high error rates, despite high coverage.
Run 2: Moderate to high certainty, with balanced coverage and improved read quality.
Run 3: Low certainty due to insufficient coverage, despite improved read quality.

(From Figure FastQC: Mean Quality Scores)
Run 2 seems to result into the best quality scores, while run 3 is slightly worse but still in the green area of good quality.
Run 1 is the worst of all, with both sub runs being in the red area corresponding to a very bad quality score.

(From Figure FastQC: General Statistics table %Dups)
Run 1:
Low Duplication: Since Run 1 has very few duplicated sequences, it suggests a more diverse set of reads covering different regions of the genome.
Impact: This diversity can potentially lead to a more complete assembly, as different regions of the genome are covered with unique reads, reducing bias in coverage and improving the accuracy of repetitive regions.

Run 2:
High Duplication: Higher duplication rates indicate that certain regions of the genome are overly represented in the dataset.
Impact: This can lead to bias in coverage and potentially cause assembly issues in repetitive regions.
(Assembly software might struggle to correctly resolve repeats or may collapse duplicated regions into fewer copies, leading to a fragmented assembly.)

Run 3:
Moderate Duplication: Similar to Run 2, moderate duplication rates indicate some bias in coverage but to a lesser extent.
Impact: While not as severe as in Run 2, moderate duplication can still affect assembly quality, especially in complex genomic regions. The assembly may have more gaps or misassemblies compared to Run 1
````


> 💡Hint: To install FastQC and MultiQC you can use the package manager [mamba](https://mamba.readthedocs.io/en/latest/installation/mamba-installation.html). The link guides you to the installation documentation. To install the software with mamba see [FastQC](https://bioconda.github.io/recipes/fastqc/README.html) and [MultiQC](https://bioconda.github.io/recipes/multiqc/README.html). However it is not required to use mamba for installation.



