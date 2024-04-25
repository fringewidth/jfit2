package jfit;

import java.util.Arrays;
import java.util.Random;

public class Gen {

    private boolean sorted;
    private int start, end, increment;
    private CSVFile outputCSV;

    public Gen(CSVFile runningTimes, int start, int end, int increment, boolean sorted)
            throws IllegalArgumentException {
        this.outputCSV = runningTimes;
        this.sorted = sorted;
        this.start = start;
        this.end = end;
        this.increment = increment;
        validateArgs();
    }

    public Gen(CSVFile runningTimes, int start, int end, int increment, int size) throws IllegalArgumentException {
        this(runningTimes, start, end, increment, false);
    }

    public void validateArgs() throws IllegalArgumentException {
        if (this.start < 1) {
            throw new IllegalArgumentException("Start length must be positive and non-zero");
        }
        if (this.end <= this.start) {
            throw new IllegalArgumentException("End must be greater than start");
        }
        if (this.increment < 1) {
            throw new IllegalArgumentException("Increment must be positive and non-zero");
        }
        if (this.increment > this.end - this.start) {
            throw new IllegalArgumentException("Increment must be lesser than the difference between start and end");
        }
    }

    public long[] arrayGenerator(int size) {
        Random random = new Random();
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextLong();
        }
        if (this.sorted) {
            Arrays.sort(array);
        }
        return array;
    }

    public long[] measureRunningTimes() {
        long[] runningTimes = new long[(this.end - this.start) / this.increment + 1];
        for (int i = this.start; i < this.end; i += this.increment) {
            long[] array = arrayGenerator(i);
            long startTime = System.nanoTime();
            Main.__method(array);
            long endTime = System.nanoTime();
            runningTimes[(i - this.start) / this.increment] = (endTime - startTime);
        }
        return runningTimes;
    }

    public void genCSV() {
        long[] runningTimes = measureRunningTimes();
        outputCSV.defineRows("Size", "Running Time");
        for (int i = 0; i < runningTimes.length; i++) {
            outputCSV.addRows(String.valueOf(i * this.increment + this.start), String.valueOf(runningTimes[i]));
        }
    }
}