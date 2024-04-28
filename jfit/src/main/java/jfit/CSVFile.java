package jfit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFile {
    private File file;
    private FileWriter writer;
    private int maxRows = -1;
    private boolean isOpen = false;

    /**
     * Creates a CSV file at the specified path.
     * 
     * @param path
     * @throws IllegalArgumentException If the file is not a CSV file.
     */
    public CSVFile(String path) {
        openFile(path);
    }

    public CSVFile() {
    };

    public void openFile(String path) {
        if (!path.endsWith(".csv")) {
            throw new IllegalArgumentException("File must be a CSV file");
        }
        this.file = new File(path);
        this.isOpen = true;
        try {
            if (!file.createNewFile())
                System.out.println("File already exists");
            this.writer = new FileWriter(file);
        } catch (IOException e) {
            this.isOpen = false;
            e.printStackTrace();
        }
    }

    public void defineColumns(String... rows) {
        checkIfOpen();
        if (rows.length == 0)
            throw new IllegalArgumentException("Rows cannot be empty");
        if (file.length() != 0)
            throw new IllegalArgumentException("File already has rows defined");
        this.maxRows = rows.length;
        addRows(rows);
    }

    public void addRows(String... cells) {
        checkIfOpen();
        if (cells.length > maxRows)
            throw new IllegalArgumentException("Row length cannot exceed number of columns");
        if (maxRows == -1)
            throw new IllegalArgumentException("Rows must be defined first");
        for (int i = 0; i < cells.length; i++) {
            try {
                if (i == cells.length - 1) {
                    writer.write(cells[i] + "\n");
                } else {
                    writer.write(cells[i] + ",");
                }
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeFile() {
        checkIfOpen();
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
        this.isOpen = false;
    }

    public String getFileName() {
        checkIfOpen();
        return file.getName();
    }

    public void checkIfOpen() {
        if (!isOpen)
            throw new IllegalArgumentException("File is not open");
    }

    public boolean isOpen() {
        return isOpen;
    }
}
