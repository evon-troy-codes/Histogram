/*Create a program Histogram.java for printing a histogram bar-chart. Your program should read
data from a file and this data can be any number of integers (range from 1 to 100), uppercase and
lowercase letters, and symbols. Note that some integers may be out of the range (1 to 100)
and must be handled appropriately by your program.  */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Histogram {
    public static void main(String[] args) {

        List<String> filesToRead = List.of("test1.txt");

        int[] counts = new int[101];
        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int othersCount = 0;

        for (String fileName : filesToRead) {

            // Read data from files
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        int value = Integer.parseInt(line);
                        if (value >= 1 && value <= 100) {
                            counts[value]++;
                        } else {
                            othersCount++;
                        }
                    } catch (NumberFormatException e) {
                        char c = line.charAt(0);
                        if (c >= 'A' && c <= 'Z') { // Match uppercase letters
                            uppercaseCount++;
                        } else if (c >= 'a' && c <= 'z') { // Match lowercase letters
                            lowercaseCount++;
                        } else { // Match symbols
                            othersCount++;
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Failed to read data from file");
                System.exit(1);
            }

            // Write the results to the output file

            try (FileWriter writer = new FileWriter("result.txt", true)) {
                writer.write("Reading integers from file: test1.txt\n");
                writer.write(String.format("Number of integers in the interval [1,100]: %d\n", countInRange(counts)));
                writer.write(String.format("Others: %d\n", othersCount));
                writer.write("Histogram:\n");
                for (int i = 1; i <= 10; i++) {
                    int start = (i - 1) * 10 + 1;
                    int end = i * 10;
                    int count = countInRange(counts, start, end);
                    writer.write(String.format(" %2d - %2d | %s\n", start, end, repeat(count)));
                }
                writer.write(String.format(" Uppercase | %s\n", repeat(uppercaseCount)));
                writer.write(String.format(" Lowercase | %s\n", repeat(lowercaseCount)));
                writer.flush();
            } catch (IOException e) {
                System.err.println("Failed to write output to file");
                System.exit(1);
            }
        }
    }

    private static int countInRange(int[] counts) {
        int count = 0;
        for (int i = 1; i <= 100; i++) {
            count += counts[i];
        }
        return count;
    }

    private static int countInRange(int[] counts, int start, int end) {
        int count = 0;
        for (int i = start; i <= end; i++) {
            count += counts[i];
        }
        return count;
    }

    private static String repeat(int n) {
        return new String(new char[n]).replace('\0', '*');
    }
}
