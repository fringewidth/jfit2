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
                throw new IllegalArgumentException("File already exists");
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
                if (i == cells.length - 1)
                    writer.write(cells[i] + "\n");
                else
                    writer.write(cells[i] + ",");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeFile() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: Delete file
    }
}
