import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class KiksAndAjee {

    /**
     * Utility method that:
     * 1. Reads the text content from the input file
     * 2. Transforms the text:
     *      - Capitalizes every word fully (all characters in the word)
     *      - Replaces spaces with underscores
     * 3. Writes the transformed content into the output file
     *
     * @param inputFile  Path to the input .txt file
     * @param outputFile Path to the output .txt file
     */
    public static void processFile(String inputFile, String outputFile) {
        try {
            // Read the file into a single string
            String content = Files.readString(Path.of(inputFile));

            // Split text into words using whitespace
            String[] words = content.split("\\s+");

            // Capitalize each word
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].toUpperCase();
            }

            // Join back with underscores instead of spaces
            String result = String.join("_", words);

            // Write modified content to output file
            Files.writeString(
                    Path.of(outputFile),
                    result,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (IOException e) {
            System.out.println("Error processing file: " + inputFile);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // Example file names — replace with your actual file paths
        String file1In  = "book1.txt";
        String file1Out = "book1_updated.txt";

        String file2In  = "book2.txt";
        String file2Out = "book2_updated.txt";

        // Start timer
        long startTime = System.currentTimeMillis();

        // NEW: Create threads for each file-processing task
        Thread t1 = new Thread(() -> processFile(file1In, file1Out)); // NEW
        Thread t2 = new Thread(() -> processFile(file2In, file2Out)); // NEW

        // NEW: Start both threads
        t1.start();
        t2.start();

        // NEW: Wait for both threads to finish (join)
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // End timer
        long endTime = System.currentTimeMillis();

        // Display elapsed time
        System.out.println("Processing completed in " + (endTime - startTime) + " ms");
    }
}
