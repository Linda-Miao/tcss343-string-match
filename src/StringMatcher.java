package src;
import src.StringMatcher;
/**
 * Interface that all string matching algorithms will implement
 */
public interface StringMatcher {
    /**
     * Finds all occurrences of pattern in text
     * @param text The text to search within
     * @param pattern The pattern to search for
     * @return Array of starting indices where pattern was found
     */
    int[] findMatches(String text, String pattern);

    /**
     * Returns the number of character comparisons performed in the last search
     * @return Count of character comparisons
     */
    long getComparisons();
}