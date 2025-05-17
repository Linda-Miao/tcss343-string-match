/*343 project program */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * it is expanding the medium text size that is enough to text result.
 * @author Linda Miao
 * @version 1.0
 */

public class ExpandMediumText {
    public static void main(String[] args) {
        try {
            // Read the existing medium text file
            String existingText = new String(Files.readAllBytes(Paths.get("data/medium_text.txt")));

            // Get current size in KB
            long currentSizeKB = existingText.length() / 1024;
            System.out.println("Current size: " + currentSizeKB + " KB");

            // Target size in KB
            int targetSizeKB = 100;

            if (currentSizeKB >= targetSizeKB) {
                System.out.println("File is already large enough. No changes made.");
                return;
            }

            // Calculate how many times to repeat to reach target size
            int repeatCount = (int) Math.ceil((double)(targetSizeKB - currentSizeKB) / currentSizeKB);
            System.out.println("Repeating content " + repeatCount + " times to reach target size");

            // Create expanded content
            StringBuilder expandedText = new StringBuilder(existingText);
            for (int i = 0; i < repeatCount; i++) {
                expandedText.append("\n--- REPEATED CONTENT SECTION " + (i+1) + " ---\n");
                expandedText.append(existingText);
            }

            // Write back to the file
            Files.write(Paths.get("data/medium_text.txt"), expandedText.toString().getBytes());

            // Verify new size
            long newSizeKB = Files.size(Paths.get("data/medium_text.txt")) / 1024;
            System.out.println("New size: " + newSizeKB + " KB");
            System.out.println("File expanded successfully!");

        } catch (IOException e) {
            System.err.println("Error expanding medium_text.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}