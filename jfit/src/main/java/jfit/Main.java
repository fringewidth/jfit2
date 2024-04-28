package jfit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    private static int start = 1;
    private static int end = 10000;
    private static int increment = 100;
    private static boolean sorted = true;

    private static String csvPath = "runningTimes.csv";

    public static void __method(long[] array) {
        // your code here
        // Example:
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    long temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Creating CSV file...");
        try {
            CSVFile runningTimes = new CSVFile(csvPath);
            System.out.println("Generating running times...");
            Gen gen = new Gen(runningTimes, start, end, increment, sorted);
            gen.genCSV();
            System.out.println("Running Python script...");
            callPy("analyse.py", csvPath);
            runningTimes.closeFile();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    private static void callPy(String pyPath, String csvPath) {
        ProcessBuilder processBuilder = new ProcessBuilder("python", pyPath, csvPath);
        try {
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
