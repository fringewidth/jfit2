package jfit;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class GenTest {
    @Test
    public void startShouldBeLessThanEnd() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(new CSVFile("someCSV.csv"), 10, 5, 1, false));
    }

    @Test
    public void incrementMustNotBeGreaterThanDifferenceBetweenStartAndEnd() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(new CSVFile("someCSV.csv"), 1, 10, 10, false));
    }

    public void startCannotBeZero() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(new CSVFile("someCSV.csv"), 0, 10, 1, false));
    }

    @Test
    public void startCannotBeEqualToEnd() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(new CSVFile("someCSV.csv"), 10, 10, 1, false));
    }

    @Test
    public void incrementCannotBeZero() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(new CSVFile("someCSV.csv"), 1, 10, 0, false));
    }

    @Test
    public void incrementCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Gen(new CSVFile("someCSV.csv"), 1, 10, -1, false));
    }

    @Test
    public void testGenCSV() {

    }

}
