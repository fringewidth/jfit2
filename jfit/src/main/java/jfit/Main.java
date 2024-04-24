package jfit;

import java.io.IOException;

public class Main {
    private static int start = 1;
    private static int end = 10000;
    private static int increment = 100;
    private static boolean sorted = false;

    private static String csvPath = "../runningTimes.csv";

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
        CSVFile runningTimes = new CSVFile(csvPath);
        Gen gen = new Gen(runningTimes, start, end, increment, sorted);
        gen.genCSV();
        callPy("../analyse.py", csvPath);
        runningTimes.closeFile();
    }

    private static void callPy(String pyPath, String csvPath) {
        ProcessBuilder processBuilder = new ProcessBuilder("python3", pyPath, csvPath);
        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
