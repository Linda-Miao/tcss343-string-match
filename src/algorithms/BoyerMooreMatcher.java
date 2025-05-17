package src.algorithms;

import src.StringMatcher;
import java.util.ArrayList;

/**
 * Implements the Boyer-Moore string matching algorithm
 *
 * References:
 * - Original paper: Boyer, R. S., & Moore, J. S. (1977). A fast string searching algorithm. Communications of the ACM, 20(10), 762-772.
 *   https://doi.org/10.1145/359842.359859
 * - Algorithm explanation: https://www.geeksforgeeks.org/boyer-moore-algorithm-for-pattern-searching/
 */
public class BoyerMooreMatcher implements StringMatcher {
    private long comparisons;
    // Size of the alphabet (extended ASCII)
    private static final int ALPHABET_SIZE = 256;

    @Override
    public int[] findMatches(String text, String pattern) {
        comparisons = 0;

        if (text == null || pattern == null || pattern.length() > text.length()) {
            return new int[0];
        }

        int n = text.length();
        int m = pattern.length();
        ArrayList<Integer> matches = new ArrayList<>();

        // Edge case: empty pattern matches at every position
        if (m == 0) {
            for (int i = 0; i <= n; i++) {
                matches.add(i);
            }
            return listToArray(matches);
        }

        // Preprocess: Build the bad character table
        int[] badChar = preprocessBadChar(pattern);

        // The pattern is aligned with the text at shift
        int shift = 0;
        while (shift <= (n - m)) {
            // Start matching from the end of the pattern
            int j = m - 1;

            // Keep matching characters as long as they match
            while (j >= 0) {
                comparisons++;
                if (pattern.charAt(j) != text.charAt(shift + j)) {
                    break;
                }
                j--;
            }

            // If we matched the entire pattern, record a match
            if (j < 0) {
                matches.add(shift);
                // Move the pattern so the next character in text aligns with the last occurrence
                // of it in pattern
                if (shift + m < n) {
                    char nextChar = text.charAt(shift + m);
                    // Make sure we don't exceed the array bounds
                    int badCharIndex = nextChar % ALPHABET_SIZE;
                    shift += m - badChar[badCharIndex];
                } else {
                    shift += 1;
                }
            } else {
                // Character mismatch - use the bad character rule to shift
                // Get the last occurrence of the mismatched character in pattern
                char mismatchChar = text.charAt(shift + j);
                // Make sure we don't exceed the array bounds
                int badCharIndex = mismatchChar % ALPHABET_SIZE;
                int badCharShift = j - badChar[badCharIndex];

                // Ensure we move at least one position
                shift += Math.max(1, badCharShift);
            }
        }

        return listToArray(matches);
    }

    /**
     * Preprocess the pattern for the bad character heuristic.
     * For each character c in the alphabet, badChar[c] contains the rightmost
     * position of c in the pattern, or -1 if c does not occur in the pattern.
     *
     * @param pattern The pattern to preprocess
     * @return The bad character table
     */
    private int[] preprocessBadChar(String pattern) {
        int m = pattern.length();
        int[] badChar = new int[ALPHABET_SIZE];

        // Initialize all entries to -1 (character not found in pattern)
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            badChar[i] = -1;
        }

        // Fill the actual positions
        for (int i = 0; i < m; i++) {
            char c = pattern.charAt(i);
            // Make sure we don't exceed the array bounds
            int index = c % ALPHABET_SIZE;
            badChar[index] = i;
        }

        return badChar;
    }

    /**
     * Converts ArrayList of integers to int array
     *
     * @param list ArrayList to convert
     * @return int array with the same elements
     */
    private int[] listToArray(ArrayList<Integer> list) {
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    @Override
    public long getComparisons() {
        return comparisons;
    }
}