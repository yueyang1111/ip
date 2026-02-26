package edith.storage;

import edith.task.Deadline;
import edith.task.Event;
import edith.task.Task;
import edith.task.Todo;
import edith.exception.EdithException;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    private static Task parseTaskData(String line) throws EdithException {
        String [] parts = line.split("\\|");
        if (parts.length < 2) {
            throw new EdithException("Skip corrupted line: " + line);
        }
        String command = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");

        Task task;
        switch (command) {
        case "T":
            if (parts.length < 3) {
                throw new EdithException("Skip corrupted line: " + line);
            }
            task = new Todo(parts[2].trim());
            break;
        case "D":
            if (parts.length < 4) {
                throw new EdithException("Skip corrupted line: " + line);
            }
            task = new Deadline(parts[2].trim(), parts[3].trim());
            break;
        case "E":
            if (parts.length < 5) {
                throw new EdithException("Skip corrupted line: " + line);
            }
            task = new Event(parts[2].trim(), parts[3].trim(), parts[4].trim());
            break;
        default:
            throw new EdithException("Skip corrupted line: " + line);
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    public ArrayList<Task> loadFromFile() throws FileNotFoundException, EdithException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File f = new File(filePath);

        if (!f.exists()) {
            return loadedTasks;
        }

        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String line = s.nextLine();
            try {
                Task task = parseTaskData(line);
                loadedTasks.add(task);
            } catch (EdithException e) {
                throw new EdithException("Corrupted file: " + e.getMessage());
            }
        }
        s.close();
        return loadedTasks;
    }

    public void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }

    public void save(ArrayList<Task> tasks) throws EdithException {
        try {
            File f = new File(filePath);
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            String textToWrite = "";
            for (int i = 0; i < tasks.size(); i++) {
                textToWrite += tasks.get(i).toFileString() + System.lineSeparator();
            }
            writeToFile(textToWrite);
        } catch (IOException e) {
            throw new EdithException("Fail to save file: " + e.getMessage());
        }
    }
}
