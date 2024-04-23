package jfit;

public class Main {

    public static void __method(long[] array) {
        // Your code here
    }

    public static void main(String[] args) {
        CSVFile runningTimes = new CSVFile("../runningTimes.csv");
        Gen gen = new Gen(runningTimes, 1, 10000, 100, false);
        gen.genCSV();
        callPy();
        runningTimes.closeFile();
    }

    private static void callPy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'callPy'");
    }
}
