//https://www.geeksforgeeks.org/naive-algorithm-for-pattern-searching/
//https://doi.org/10.1137/0206024
// https://opendsa-server.cs.vt.edu/ODSA/Books/Everything/html/StringMatching.html
//https://rosettacode.org/wiki/String_matching
//* References:
//        * 1. Naive Algorithm: https://www.geeksforgeeks.org/naive-algorithm-for-pattern-searching/
//        * 2. KMP Algorithm: https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/
//        * 3. Boyer-Moore Algorithm: https://www.geeksforgeeks.org/boyer-moore-algorithm-for-pattern-searching/
//        * 4. Rabin-Karp Algorithm: https://www.geeksforgeeks.org/rabin-karp-algorithm-for-pattern-searching/
//        *
//        * Academic Papers:
//        * 5. Original KMP Paper: https://doi.org/10.1137/0206024
//        * 6. Boyer-Moore Paper: https://doi.org/10.1145/359842.359859


package src.tests;

import src.StringMatcher;
import src.algorithms.NaiveMatcher;
import src.algorithms.KMPMatcher;
import src.algorithms.BoyerMooreMatcher;
import src.algorithms.RabinKarpMatcher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;




/**
 * Class for testing and comparing string matching algorithms
 */
public class TextLoaderTest {

    // Method to load text from a file into a string
    public static String loadTextFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    // Method to run tests with a specific algorithm and text
    public static void runTest(StringMatcher matcher, String text, String pattern) {
        System.out.println("\nTesting " + matcher.getClass().getSimpleName());
        System.out.println("Text length: " + text.length() + " characters");
        System.out.println("Pattern: \"" + pattern + "\" (length: " + pattern.length() + ")");

        long startTime = System.nanoTime();
        int[] matches = matcher.findMatches(text, pattern);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        System.out.println("Found " + matches.length + " matches");
        System.out.println("First 5 matches at positions: ");
        for (int i = 0; i < Math.min(5, matches.length); i++) {
            System.out.print(matches[i] + " ");
        }
        System.out.println();

        System.out.println("Time taken: " + duration + " nanoseconds");
        System.out.println("Character comparisons: " + matcher.getComparisons());
    }

    // Method to compare multiple algorithms on the same text and pattern
    public static void compareAlgorithms(String text, String pattern) {
        StringMatcher[] matchers = {
                new NaiveMatcher(),
                 new KMPMatcher(),
                 new BoyerMooreMatcher(),
                 new RabinKarpMatcher()
        };

        System.out.println("\n==== ALGORITHM COMPARISON ====");
        System.out.println("Text length: " + text.length());
        System.out.println("Pattern: \"" + pattern + "\" (length: " + pattern.length() + ")");

        for (StringMatcher matcher : matchers) {
            long startTime = System.nanoTime();
            int[] matches = matcher.findMatches(text, pattern);
            long endTime = System.nanoTime();

            System.out.println("\n" + matcher.getClass().getSimpleName() + ":");
            System.out.println("Matches found: " + matches.length);
            System.out.println("Time: " + (endTime - startTime) + " ns");
            System.out.println("Comparisons: " + matcher.getComparisons());
        }
    }

    // Method to test with different pattern types
    public static void testPatternTypes(String text, StringMatcher matcher) {
        // Common pattern (frequent in text)
        String commonPattern = "the"; // Typically common in English text

        // Rare pattern (infrequent in text)
        String rarePattern = "xyzabc123"; // Unlikely to occur

        // Long pattern
        String longPattern = "implementation of string matching algorithms"; // Longer pattern

        // Repeating pattern
        String repeatingPattern = "ababababab"; // Pattern with repetition

        System.out.println("\n==== PATTERN TYPE TESTS ====");
        runTest(matcher, text, commonPattern);
        runTest(matcher, text, rarePattern);
        runTest(matcher, text, longPattern);
        runTest(matcher, text, repeatingPattern);
    }


    public static void main(String[] args) {
        try {
            // Load different text sizes
            String smallText = loadTextFromFile("data/small_text.txt");
            String mediumText = loadTextFromFile("data/medium_text.txt");

            // Create algorithm instances
            StringMatcher naiveMatcher = new NaiveMatcher();

            // 1. Test with different patterns that are more likely to exist in your text
            System.out.println("===== TESTING COMMON PATTERNS =====");
            String[] testPatterns = {"the", "and", "in", "algorithm"};
            for (String pattern : testPatterns) {
                runTest(naiveMatcher, mediumText, pattern);
            }

            // 2. Test with small text too
            System.out.println("\n===== TESTING WITH SMALL TEXT =====");
            for (String pattern : testPatterns) {
                runTest(naiveMatcher, smallText, pattern);
            }

            // 3. Verify with a known example
            String sampleText = "ababababa";
            String samplePattern = "aba";
            System.out.println("\n===== VERIFICATION TEST =====");
            System.out.println("Sample text: " + sampleText);
            System.out.println("Sample pattern: " + samplePattern);
            runTest(naiveMatcher, sampleText, samplePattern);

            // 4. Test different pattern types
            System.out.println("\n===== PATTERN TYPE TESTS =====");
            testPatternTypes(mediumText, naiveMatcher);

            // for KMP
            System.out.println("\n===== COMPARING ALGORITHMS =====");
            String[] comparisonPatterns = {"the", "algorithm", "ababababab"};
            for (String pattern : comparisonPatterns) {
                compareAlgorithms(mediumText, pattern);
            }

            // Add this to your main method after the existing tests
            System.out.println("\n===== TESTING BOYER-MOORE STRENGTHS =====");

// Test with a longer pattern (Boyer-Moore should excel here)
            String longPattern = "implementation of string matching algorithms";
            System.out.println("\nTesting with long pattern:");
            compareAlgorithms(mediumText, longPattern);

// Test with a pattern with rare characters at the end
            String rareEndPattern = "theXYZ";
            System.out.println("\nTesting with rare-ended pattern:");
            compareAlgorithms(mediumText, rareEndPattern);

// Test with a larger text (if available)
            try {
                String largeText = loadTextFromFile("data/large_text.txt");
                System.out.println("\nTesting with large text:");
                compareAlgorithms(largeText, "the");
            } catch (Exception e) {
                System.out.println("Large text file not available. Skipping test.");
            }

            // Add this to your main method after the Boyer-Moore tests
            System.out.println("\n===== TESTING RABIN-KARP STRENGTHS =====");

// Test with multiple patterns
// Rabin-Karp is particularly good when searching for multiple patterns
// because it can reuse hash calculations
            String[] multiplePatterns = {"the", "and", "for", "algorithm"};
            System.out.println("\nTesting with multiple patterns:");
            for (String pattern : multiplePatterns) {
                compareAlgorithms(mediumText, pattern);
            }

// Test with patterns that would cause hash collisions
// This is a theoretical test and might not produce actual collisions
            String pattern1 = "abcdef";
            String pattern2 = "fedcba";
            System.out.println("\nTesting patterns prone to hash collision:");
            compareAlgorithms(mediumText, pattern1);
            compareAlgorithms(mediumText, pattern2);

        } catch (IOException e) {
            System.err.println("Error loading text files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
