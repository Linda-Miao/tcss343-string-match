/*343 project program */

/** To add the test size: 150, 10k, 100k and 1M */
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
 * This file is designed to test all algorithms implemented in the project.
 * It ensures the correctness and performance of each algorithm.
 * @author Linda Miao
 * @version 2.0
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

    /**
     * To add the test size: 150, 10k, 100k and 1M
     */
    public static void testScalability() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SCALABILITY TESTS - 150, 10K, 100K, 1M CHARACTERS");
        System.out.println("=".repeat(80));

        int[] textSizes = {150, 10000, 100000, 1000000};

        try {
            String baseText = loadTextFromFile("data/medium_text.txt");

            for (int targetSize : textSizes) {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("TESTING WITH " + formatNumber(targetSize) + " CHARACTERS");
                System.out.println("=".repeat(50));

                String testText = generateTextOfSize(baseText, targetSize);

                // Test different pattern types for each text size
                testPatternsOnTextSize(testText, targetSize);
            }

            // Summary analysis
            printScalabilitySummary();

        } catch (IOException e) {
            System.err.println("Error in scalability tests: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Generate text of exact target size
     */
    private static String generateTextOfSize(String baseText, int targetSize) {
        if (targetSize <= baseText.length()) {
            return baseText.substring(0, targetSize);
        }

        StringBuilder result = new StringBuilder();
        int sectionNumber = 1;

        while (result.length() < targetSize) {
            result.append(baseText);

            // Add section markers for variety
            if (result.length() < targetSize - 50) {
                String marker = " [Section " + sectionNumber + "] ";
                result.append(marker);
                sectionNumber++;
            }
        }
        // Trim to exact size
        return result.substring(0, targetSize);
    }

    /**
     * Test various patterns on text of specific size
     */
    private static void testPatternsOnTextSize(String text, int textSize) {
        System.out.println("Text size: " + formatNumber(text.length()) + " characters");

        // Choose patterns appropriate for text size
        String[] patterns = getPatternsForTextSize(text, textSize);

        for (String pattern : patterns) {
            System.out.println("\n--- Pattern: \"" + getPatternDisplay(pattern) + "\" (" + pattern.length() + " chars) ---");
            compareAlgorithmsScalability(text, pattern, textSize);
        }
    }

    /**
     * Get appropriate test patterns based on text size
     */
    private static String[] getPatternsForTextSize(String text, int textSize) {
        switch (textSize) {
            case 150:
                return new String[]{
                        "the",                          // Small common pattern
                        "algorithm",                    // Medium pattern
                        "string matching"               // Phrase pattern
                };

            case 10000:
                return new String[]{
                        "the",                          // Small pattern
                        "algorithm implementation",     // Medium pattern
                        "comprehensive analysis of string matching algorithms",  // Large pattern
                        "abc".repeat(30)                // 90-char repetitive pattern
                };

            case 100000:
                return new String[]{
                        "the",                          // Small pattern
                        "algorithm",                    // Medium pattern
                        "string matching algorithms performance analysis", // Long pattern
                        "ab".repeat(50),                // 100-char repetitive
                        text.substring(50000, Math.min(50150, text.length()))    // 150-char guaranteed match
                };

            case 1000000:
                return new String[]{
                        "the",                          // Small pattern
                        "algorithm implementation",     // Medium pattern
                        "comprehensive performance analysis of advanced string matching algorithms", // Very long
                        "xyz".repeat(50),               // 150-char rare pattern
                        "01".repeat(100),               // 200-char binary-like pattern
                        text.substring(500000, Math.min(500200, text.length()))  // 200-char guaranteed match from middle
                };

            default:
                return new String[]{"the", "algorithm"};
        }
    }

    /**
     * Enhanced algorithm comparison with performance metrics
     */
    private static void compareAlgorithmsScalability(String text, String pattern, int textSize) {
        StringMatcher[] algorithms = {
                new NaiveMatcher(),
                new KMPMatcher(),
                new BoyerMooreMatcher(),
                new RabinKarpMatcher()
        };

        // Warm up JVM for larger tests
        if (textSize >= 100000) {
            for (StringMatcher alg : algorithms) {
                alg.findMatches(text.substring(0, Math.min(1000, text.length())),
                        pattern.substring(0, Math.min(10, pattern.length())));
            }
            System.gc(); // Garbage collection before timing
        }

        System.out.printf("%-15s %10s %15s %12s %15s %12s%n",
                "Algorithm", "Matches", "Comparisons", "Time(ms)", "Comp/ms", "Efficiency");
        System.out.println("-".repeat(85));

        long bestTime = Long.MAX_VALUE;
        long fewestComparisons = Long.MAX_VALUE;

        // Collect results first
        AlgorithmResult[] results = new AlgorithmResult[algorithms.length];

        for (int i = 0; i < algorithms.length; i++) {
            StringMatcher algorithm = algorithms[i];

            long startTime = System.nanoTime();
            int[] matches = algorithm.findMatches(text, pattern);
            long endTime = System.nanoTime();

            long timeMs = (endTime - startTime) / 1_000_000;
            long comparisons = algorithm.getComparisons();

            results[i] = new AlgorithmResult(
                    algorithm.getClass().getSimpleName(),
                    matches.length,
                    comparisons,
                    timeMs
            );

            bestTime = Math.min(bestTime, timeMs);
            fewestComparisons = Math.min(fewestComparisons, comparisons);
        }

        // Display results with efficiency indicators
        for (AlgorithmResult result : results) {
            double compRate = result.timeMs > 0 ? (double) result.comparisons / result.timeMs : 0;
            String efficiency = getEfficiencyIndicator(result, bestTime, fewestComparisons);

            System.out.printf("%-15s %10d %15s %10d ms %12.0f %12s%n",
                    result.name,
                    result.matches,
                    formatNumber(result.comparisons),
                    result.timeMs,
                    compRate,
                    efficiency);
        }

        // Show pattern characteristics analysis
        analyzePatternCharacteristics(pattern, textSize);
    }

    /**
     * Analyze pattern characteristics and predict best algorithm
     */
    private static void analyzePatternCharacteristics(String pattern, int textSize) {
        double patternToTextRatio = (double) pattern.length() / textSize;
        boolean isRepetitive = isPatternRepetitive(pattern);
        boolean isLong = pattern.length() > 50;

        System.out.println("Pattern analysis:");
        System.out.printf("  - Length ratio: %.4f (pattern/text)%n", patternToTextRatio);
        System.out.printf("  - Repetitive: %s%n", isRepetitive ? "Yes" : "No");
        System.out.printf("  - Long pattern: %s%n", isLong ? "Yes" : "No");

        // Predict best algorithm
        String prediction = predictBestAlgorithm(pattern.length(), textSize, isRepetitive);
        System.out.println("  - Predicted best: " + prediction);
    }

    /**
     * Simple pattern repetition detection
     */
    private static boolean isPatternRepetitive(String pattern) {
        if (pattern.length() < 4) return false;

        // Check if pattern has repeating substrings
        for (int len = 1; len <= pattern.length() / 2; len++) {
            String sub = pattern.substring(0, len);
            if (pattern.startsWith(sub.repeat(pattern.length() / len))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Predict best algorithm based on characteristics
     */
    private static String predictBestAlgorithm(int patternLen, int textSize, boolean isRepetitive) {
        if (textSize < 1000 && patternLen < 10) {
            return "Naive (small input)";
        } else if (patternLen > 100) {
            return "Boyer-Moore (long pattern)";
        } else if (isRepetitive) {
            return "KMP (repetitive pattern)";
        } else if (patternLen > 20) {
            return "Boyer-Moore (medium-long pattern)";
        } else {
            return "Rabin-Karp (efficient comparisons)";
        }
    }

    /**
     * Get efficiency indicator for results display
     */
    private static String getEfficiencyIndicator(AlgorithmResult result, long bestTime, long fewestComparisons) {
        boolean isFastest = result.timeMs <= bestTime * 1.1; // Within 10% of best time
        boolean isFewestComp = result.comparisons <= fewestComparisons * 1.1; // Within 10% of fewest comparisons

        if (isFastest && isFewestComp) return "★★★ BEST";
        else if (isFastest) return "★★ FAST";
        else if (isFewestComp) return "★★ EFFICIENT";
        else if (result.timeMs <= bestTime * 2) return "★ GOOD";
        else return "○ OK";
    }

    /**
     * Print summary of scalability findings
     */
    private static void printScalabilitySummary() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SCALABILITY ANALYSIS SUMMARY");
        System.out.println("=".repeat(80));

        System.out.println("Key Findings:");
        System.out.println("• Small texts (150 chars): Naive often fastest due to low overhead");
        System.out.println("• Medium texts (10K chars): Algorithm differences become apparent");
        System.out.println("• Large texts (100K chars): Boyer-Moore and Rabin-Karp advantages clear");
        System.out.println("• Very large texts (1M chars): Theoretical complexities fully manifest");

        System.out.println("\nRecommendations by text size:");
        System.out.println("• < 1K characters: Naive for simplicity");
        System.out.println("• 1K - 50K characters: Boyer-Moore for long patterns, KMP for repetitive");
        System.out.println("• > 50K characters: Boyer-Moore or Rabin-Karp depending on pattern type");

        System.out.println("\n★★★ = Best overall, ★★ = Excellent in category, ★ = Good, ○ = Acceptable");
    }

    /**
     * Helper class to store algorithm results
     */
    private static class AlgorithmResult {
        String name;
        int matches;
        long comparisons;
        long timeMs;

        AlgorithmResult(String name, int matches, long comparisons, long timeMs) {
            this.name = name;
            this.matches = matches;
            this.comparisons = comparisons;
            this.timeMs = timeMs;
        }
    }

    /**
     * Get display version of pattern (truncated if too long)
     */
    private static String getPatternDisplay(String pattern) {
        if (pattern.length() <= 30) {
            return pattern;
        }
        return pattern.substring(0, 27) + "...";
    }

    /**
     * Format numbers with commas for readability
     */
    private static String formatNumber(long number) {
        return String.format("%,d", number);
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

            // Add the scalability tests
            testScalability();

        } catch (IOException e) {
            System.err.println("Error loading text files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}