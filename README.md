# tcss343-string-match

String Matching Algorithms Comparison
Project Purpose
This project implements and compares four classic string matching algorithms to analyze their performance characteristics across different pattern types and text sizes. The goal is to understand when each algorithm performs best in practice, beyond what theoretical time complexity analysis alone would suggest.
Implemented Algorithms

Naive Algorithm: The straightforward character-by-character comparison approach that checks each possible position in the text.
Knuth-Morris-Pratt (KMP): Utilizes a preprocessed partial match table to avoid redundant comparisons and backtracking.
Boyer-Moore: Implements the bad character heuristic to skip portions of the text, searching from right to left.
Rabin-Karp: Uses a rolling hash function to quickly identify potential matches before character-by-character verification.

All algorithms implement the same StringMatcher interface, ensuring consistent testing and fair comparison.
Running the Tests

Setup:

Clone this repository: git clone https://github.com/Linda-Miao/tcss343-string-match.git
Ensure you have JDK 21 or later installed
Open the project in IntelliJ IDEA or your preferred Java IDE


Prepare test data:

Test data files are located in the data/ directory
For new tests, add text files to this directory


Run the comparison tests:

Execute the TextLoaderTest class to run all comparisons
For individual algorithm testing, modify the main method to use specific patterns or texts



Key Findings
Our comprehensive testing revealed several important insights:

Character Comparisons: Rabin-Karp consistently performed the fewest character comparisons (often 90-95% fewer than Naive), with Boyer-Moore ranking second.
Execution Time: Despite more comparisons, the Naive algorithm often achieved the fastest execution times for shorter patterns due to its simplicity and lack of preprocessing overhead.
Pattern Length Impact: Both Boyer-Moore and Rabin-Karp improved dramatically in efficiency as pattern length increased, while Naive and KMP maintained relatively constant performance.
Best Use Cases:

Naive: Best for short patterns or small texts
KMP: Suitable when consistent, predictable performance is required
Boyer-Moore: Excellent for long patterns in large texts
Rabin-Karp: Ideal for multi-pattern searching scenarios


Practical vs. Theoretical: Our empirical results demonstrated that theoretical time complexity doesn't always predict practical performance, highlighting the importance of implementation factors and pattern characteristics.

Full Report
For detailed methodology, complete results analysis, visualizations, and comprehensive discussion, please refer to the full project report: String Matching Algorithms Comparison - Project Report.pdf
Note: Replace the placeholder link with the actual link to your PDF once you've uploaded it to a sharing service.
Project Structure

src/StringMatcher.java: Common interface for all algorithms
src/algorithms/: Directory containing all algorithm implementations
src/tests/: Testing framework and performance comparison tools
data/: Test text files of various sizes

References

Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009). Introduction to Algorithms (3rd ed.). MIT Press.
Knuth, D. E., Morris, J. H., & Pratt, V. R. (1977). Fast pattern matching in strings. SIAM Journal on Computing, 6(2), 323-350.
Boyer, R. S., & Moore, J. S. (1977). A fast string searching algorithm. Communications of the ACM, 20(10), 762-772.
Karp, R. M., & Rabin, M. O. (1987). Efficient randomized pattern-matching algorithms. IBM Journal of Research and Development, 31(2), 249-260.

Author
Linda Miao
TCSS 343: Design and Analysis of Algorithms
University of Washington Tacoma
Spring 2025
