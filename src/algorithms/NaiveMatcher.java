/*343 project program */
package src.algorithms;
import src.StringMatcher;
import java.util.ArrayList;

/**
 * Implements the naive string matching algorithm
 * @author Linda Miao
 * @version 1.0
 */
public class NaiveMatcher implements StringMatcher {
    private long comparisons;

    /**
     * Finds all occurrences of pattern in text using naive string matching
     * @param text The text to search within
     * @param pattern The pattern to search for
     * @return Array of starting indices where pattern was found
     */
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

        // Main loop: slide the pattern over the text
        for (int i = 0; i <= n - m; i++) {
            boolean isMatch = true;

            // Inner loop: compare pattern chars to text window
            for (int j = 0; j < m; j++) {
                comparisons++; // Count this comparison

                if (text.charAt(i + j) != pattern.charAt(j)) {
                    isMatch = false;
                    break;
                }
            }

            if (isMatch) {
                matches.add(i);
            }
        }

        return listToArray(matches);
    }

    /**
     * Converts ArrayList of integers to int array
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

    /**
     * Returns the number of character comparisons from the last search
     * @return Count of character comparisons
     */
    @Override
    public long getComparisons() {
        return comparisons;
    }
}