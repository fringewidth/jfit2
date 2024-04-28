package jfit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

public class GenTest {
    private CSVFile csvFile = new CSVFile("someCSV.csv");

    @Test
    public void startShouldBeLessThanEnd() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(csvFile, 10, 5, 1));
    }

    @Test
    public void incrementMustNotBeGreaterThanDifferenceBetweenStartAndEnd() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(csvFile, 1, 10, 10));
    }

    @Test
    public void startCannotBeZero() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(csvFile, 0, 10, 1));
    }

    @Test
    public void startCannotBeEqualToEnd() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(csvFile, 10, 10, 1));
    }

    @Test
    public void incrementCannotBeZero() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(csvFile, 1, 10, 0));
    }

    @Test
    public void incrementCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(csvFile, 1, 10, -1));
    }

    @Test
    public void columnCountInGeneratedCSVMustBeTwo() {
        Gen gen = new Gen(csvFile, 1, 120, 3);
        gen.genCSV();
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile.getFileName()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                assertEquals(values.length, 2);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void numberOfRowsInGeneratedCSVMustBeDifferenceOfStartAndEndDividedByIncrement() {
        Gen gen = new Gen(csvFile, 1, 120, 3);
        gen.genCSV();
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile.getFileName()));
            int count = 0;
            br.readLine(); // Skip header
            while (br.readLine() != null) {
                count++;
            }
            br.close();
            assertEquals(count, (120 - 1) / 3 + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sortedArrayShouldBeSorted() {
        Gen gen = new Gen(csvFile, 1, 120, 3, true);
        long[] array = gen.arrayGenerator(10);
        boolean sorted = true;
        ;
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                sorted = false;
                break;
            }
        }
        assertEquals(sorted, true);
    }

    @After
    public void tearDown() {
        csvFile.closeFile();
    }

}
