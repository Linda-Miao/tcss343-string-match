//package src;
package src.algorithms;
import src.StringMatcher;
import java.util.ArrayList;

/**
 * Implements the naive string matching algorithm
 * @author Linda Miao.
 * @version 1.0
 */
public class KMPMatcher implements StringMatcher {
    private long comparisons;

    @Override
    public int[] findMatches(String text, String pattern) {
        comparisons = 0; // Reset counter

        if (text == null || pattern == null || pattern.length() > text.length()) {
            return new int[0];
        }

        int n = text.length();
        int m = pattern.length();

        // Using ArrayList to store matches since we don't know how many there will be
        ArrayList<Integer> matches = new ArrayList<>();

        // Edge case: empty pattern matches at every position
        if (m == 0) {
            for (int i = 0; i <= n; i++) {
                matches.add(i);
            }
            return listToArray(matches);
        }

        // Preprocess: Compute the LPS (Longest Prefix Suffix) array
        int[] lps = computeLPSArray(pattern);

        // KMP search algorithm
        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < n) {
            // Compare current characters in pattern and text
            comparisons++;

            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            // If we've reached the end of the pattern, we found a match
            if (j == m) {
                // Found a match at index (i - j)
                matches.add(i - j);

                // Look for the next match by using the LPS values
                j = lps[j - 1];
            }
            // If there's a mismatch after j matches
            else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    // Use the LPS array to skip already matched characters
                    j = lps[j - 1];
                } else {
                    // If we are at beginning of pattern, simply move to next text character
                    i++;
                }
            }
        }

        return listToArray(matches);
    }

    /**
     * Computes the Longest Proper Prefix which is also Suffix array.
     * This is the key preprocessing step in KMP algorithm.
     *
     * @param pattern The pattern for which to compute the LPS array
     * @return The LPS array where lps[i] = length of longest proper prefix which is also suffix for pattern[0...i]
     */
    private int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];

        // lps[0] is always 0
        lps[0] = 0;

        // Length of the previous longest prefix & suffix
        int len = 0;

        // Loop to fill the rest of the lps array
        int i = 1;
        while (i < m) {
            comparisons++; // Count character comparison

            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    // This is the key part that makes KMP efficient
                    // Instead of starting from scratch, we use previously computed values
                    len = lps[len - 1];
                    // Note: we do not increment i here
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
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
