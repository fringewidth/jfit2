package jfit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFile {
    private File file;
    private FileWriter writer;

    public CSVFile(String path) {
        this.file = new File(path);
        try {
            if (!file.createNewFile())
                // TODO: Change this to an IllegalArgumentException
                System.out.println("File already exists");
            this.writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void defineRows(String... rows) {
        if (file.length() != 0)
            throw new IllegalArgumentException("File already has rows defined");
        addRows(rows);
    }

    public void addRows(String... cells) {
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
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
    }
}
