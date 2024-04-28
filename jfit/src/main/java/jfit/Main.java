package jfit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            String pyOp = callPy("analyse.py", csvPath);
            System.out.println(pyOp);
            runningTimes.closeFile();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String callPy(String pyPath, String... args) {
        String pyOp = "";
        List<String> argsList = new ArrayList<>(Arrays.asList(args));
        argsList.add(0, "python");
        argsList.add(1, pyPath);
        ProcessBuilder processBuilder = new ProcessBuilder(argsList);
        try {
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                pyOp += line + "\n";
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return pyOp;
    }
}
