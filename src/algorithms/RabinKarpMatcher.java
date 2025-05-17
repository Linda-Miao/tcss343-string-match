/* 343 Project Program */

package src.algorithms;
import src.StringMatcher;
import java.util.ArrayList;

/**
 * Implements the Boyer-Moore string matching algorithm
 * @author Linda Miao
 * @version 1.0
 */
public class RabinKarpMatcher implements StringMatcher {
    private long comparisons;

    // A large prime number to avoid hash collisions
    private static final int PRIME = 101;

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

        // Base value for the hash function (can use any value, typically a power of the alphabet size)
        int d = 256; // For ASCII

        // Calculate the hash value of pattern and the first window of text
        int patternHash = 0;
        int textHash = 0;
        int h = 1;

        // Calculate h = d^(m-1) % PRIME
        // This value is used when removing the leading digit
        for (int i = 0; i < m - 1; i++) {
            h = (h * d) % PRIME;
        }

        // Calculate the hash value of pattern and the first window of text
        for (int i = 0; i < m; i++) {
            patternHash = (d * patternHash + pattern.charAt(i)) % PRIME;
            textHash = (d * textHash + text.charAt(i)) % PRIME;
        }

        // Slide the pattern over text one by one
        for (int i = 0; i <= n - m; i++) {
            // Check if the hash values match
            if (patternHash == textHash) {
                // If hash values match, verify character by character
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    comparisons++;
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    matches.add(i);
                }
            }

            // Calculate hash value for the next window of text
            if (i < n - m) {
                // Remove leading digit, add trailing digit
                textHash = (d * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % PRIME;

                // Handle negative hash values
                if (textHash < 0) {
                    textHash += PRIME;
                }
            }
        }

        return listToArray(matches);
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