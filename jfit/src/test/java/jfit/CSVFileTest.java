package jfit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

public class CSVFileTest {
    @Test
    public void fileCreatedMustBeACSV() {
        assertThrows(IllegalArgumentException.class, () -> {
            CSVFile csvFile = new CSVFile("someCSV.txt");
        });
    }

    @Test
    public void ColumnsCanOnlyBeDefinedOnce() {
        CSVFile csvFile = new CSVFile("someCSV.csv");
        csvFile.defineColumns("1", "2", "3");
        assertThrows(IllegalArgumentException.class, () -> {
            csvFile.defineColumns("4", "5", "6");
        });
        csvFile.closeFile();
    }

    @Test
    public void columnsDefinedCannotBeEmpty() {
        CSVFile csvFile = new CSVFile("someCSV.csv");
        assertThrows(IllegalArgumentException.class, () -> {
            csvFile.defineColumns();
        });
    }

    @Test
    public void definedColumnsAreDefined() {
        CSVFile csvFile = new CSVFile("someCSV.csv");
        csvFile.defineColumns("1", "2", "3");
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile.getFileName()))) {
            assertTrue(br.readLine().equals("1,2,3"));
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void rowLengthCannotExceedNumberOfColumns() {
        CSVFile csvFile = new CSVFile("someCSV.csv");
        csvFile.defineColumns("1", "2", "3");
        assertThrows(IllegalArgumentException.class, () -> {
            csvFile.addRows("1", "2", "3", "4");
        });
        csvFile.closeFile();
    }

    @Test
    public void columnsMustBeDefinedBeforeAdding() {
        CSVFile csvFile = new CSVFile("someCSV.csv");
        assertThrows(IllegalArgumentException.class, () -> {
            csvFile.addRows("1", "2", "3");
        });
        csvFile.closeFile();
    }

    @Test
    public void addedRowsAreAdded() {
        CSVFile csvFile = new CSVFile("someCSV.csv");
        csvFile.defineColumns("1", "2", "3");
        csvFile.addRows("4", "5", "6");
        csvFile.addRows("7", "8");
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile.getFileName()))) {
            br.readLine(); // skip headers
            assertTrue(br.readLine().equals("4,5,6"));
            assertTrue(br.readLine().equals("7,8"));
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        csvFile.closeFile();
    }

    @Test
    public void closedFileIsClosed() {
        CSVFile csvFile = new CSVFile("someCSV.csv");
        csvFile.closeFile();
        assertFalse(csvFile.isOpen());
    }

    @Test
    public void fileIsDeletedAfterClosing() {
        CSVFile csvFile = new CSVFile("someCSV.csv");
        csvFile.closeFile();
        assertThrows(IllegalArgumentException.class, () -> csvFile.getFileName());
    }

    @Test
    public void rowCannotBeAddedToClosedFile() {
        CSVFile csvFile = new CSVFile();
        assertThrows(IllegalArgumentException.class, () -> {
            csvFile.addRows("1", "2", "3");
        });
    }

    @Test
    public void columnCannotBeDefinedOnClosedFile() {
        CSVFile csvFile = new CSVFile();
        assertThrows(IllegalArgumentException.class, () -> {
            csvFile.defineColumns("1", "2", "3");
        });
    }

    @Test
    public void fileIsUnopenedIfNoPathIsSpecified() {
        CSVFile csvFile = new CSVFile();
        assertFalse(csvFile.isOpen());
    }
}
