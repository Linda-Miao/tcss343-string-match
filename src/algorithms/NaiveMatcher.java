//Naive string-matching algorithm    // https://www.geeksforgeeks.org/naive-algorithm-for-pattern-searching/
//https://www.youtube.com/watch?v=nK7SLhXcqRo
//package src.algorithms;
//
//import utils.ResultLogger;
//import utils.TimerUtil;
/*
Line Checker Analogy – Naive Pattern Search
Scene: A guy is checking a line of people to find a specific pattern—for example, 4 people from China standing together.

Step-by-Step Story
Start Fresh:
Before checking a new line of people, he resets his timer and clears his notes (like resetting counters and logs in code).

Check if Pattern is Empty:
If there’s no pattern to look for, he just records every possible position. (Empty pattern matches everywhere.)

Scan One Line:
He walks along the line, stopping at every possible group of 4 people:
Looks at each group one by one (like a sliding window).
Compares each person in the group to see if they are all from China (match pattern).
If even one isn’t, he stops checking that group early and moves to the next.

Match Found?
If all 4 people are from China, he records that starting position in his notebook.

Finish the Line:
Once he finishes checking the whole line, he stops the timer and records the total time taken and how many matching groups he found.

New Line, New Check:
When a new line of people arrives, he starts over—clears old data and starts timing again.

What This Teaches
Pattern = group we want to find
Text = the line of people
Loop = walking step by step through the line
Resetting = important for each new check
Comparison = checking each person one by one
Efficiency = stops early if mismatch is found

Time complexity:
if checking the all line have no any pattern, base case O(n)
if checking the all line have many patterns, worse case O(n*m)

Variables & Their Roles
Variable	Role	            What It Represents
n	       Size of the text	    How long the full input string is
m	       Size of the pattern	How long the string we're searching for is
i	       Outer loop index	    The current position in text to start checking
j	       Inner loop index	    The position in the pattern that’s being compared to text[i + j]

Summary of the formulas:
Total number of checks (n * m):
This represents the worst-case time complexity when checking the entire text to find the pattern.
n * m: This is the total number of character comparisons we might perform in the worst case,
assuming that every character in the text needs to be compared with every character
in the pattern.

How many groups to check (n - m + 1):
This formula calculates how many positions in the text we need to check to match the pattern.
n - m + 1: This gives the number of possible starting positions for the pattern in the text. It's the total number of
positions in the text where the pattern can potentially start and fit.

i.g:
Text = "2242232224" (Length: n = 10)
Pattern = "22" (Length: m = 2)
n - m + 1 = 10 - 2 + 1 = 9 => index 0,1,2,3,4,5,6,7,8,9 check: 01,12,23,34,45,56,67,78,89 pair check for total 9

 */

package src.algorithms;
import src.algorithms.NaiveMatcher;

// Import the StringMatcher interface
import src.StringMatcher;
import java.util.ArrayList;

/**
 * Implements the naive string matching algorithm
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