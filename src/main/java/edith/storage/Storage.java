package edith.storage;

import edith.task.Deadline;
import edith.task.Event;
import edith.task.Task;
import edith.task.Todo;
import edith.exception.EdithException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles the loading of tasks from and
 * saving of tasks to disk.
 */
public class Storage {
    public static final String TODO = "T";
    public static final String DEADLINE = "D";
    public static final String EVENT = "E";
    private final File dataFile;

    /**
     * Creates a storage object that reads and
     * writes to the specified filepath.
     *
     * @param filePath Path to the data file.
     */
    public Storage(String filePath) {
        this.dataFile = new File(filePath);
    }

    /**
     * Creates the file/folder if they do not exist.
     *
     * @throws IOException If the operation fails.
     */
    public void createFile() throws IOException {
        try {
            if (dataFile.exists()) {
                return;
            }
            File parent = dataFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            dataFile.createNewFile();
        } catch (IOException e) {
            throw new IOException("Cannot create file; reason: " + e.getMessage());
        }
    }

    private ArrayList<String> readFile() throws IOException {
        createFile();
        if (!dataFile.exists()) {
            throw new FileNotFoundException();
        }
        if (dataFile.length() == 0) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Files.readAllLines(dataFile.toPath(), Charset.defaultCharset()));
    }

    /**
     * Loads tasks from data file.
     *
     * @return A list of tasks loaded from the disk.
     * @throws EdithException If hte file contains corrupted lines.
     */
    public ArrayList<Task> loadFromFile() throws EdithException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        try {
            ArrayList<String> lines = readFile();
            loadedTasks = parse(lines);
        } catch (IOException e) {
            System.out.println("Fail to load data file: " + e.getMessage());
        }
        return loadedTasks;
    }

    /**
     * Returns the tasks read from the data line.
     *
     * @param lines Lines in the data file.
     * @return List of tasks parsed.
     */
    private ArrayList<Task> parse(ArrayList<String> lines) {
        ArrayList<Task> allTasks = new ArrayList<>();

        for (String line: lines) {
            if (line == null || line.trim().isEmpty()) {
                continue;
            }

            try {
                Task task = parseTaskData(line.trim());
                allTasks.add(task);
            } catch (EdithException e) {
                System.out.println("Skipping corrupted line: "+ line);
            }
        }
        return allTasks;
    }

    private static LocalDateTime convertToFormat(String date, String line) throws EdithException {
        try {
            return LocalDateTime.parse(date);
        } catch (DateTimeParseException e) {
            throw new EdithException("Skipping corrupted line: " + line);
        }

    }

    /**
     * Parse a given line from the data file into a Task object.
     *
     * @param line A line from the data file.
     * @return Task object identified.
     * @throws EdithException If the line is corrupted.
     */
    private static Task parseTaskData(String line) throws EdithException {
        String [] parts = line.split("\\|");
        if (parts.length < 2) {
            throw new EdithException("Skip corrupted line: " + line);
        }
        String command = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");

        Task task;
        switch (command) {
        case TODO:
            if (parts.length < 3) {
                throw new EdithException("Skip corrupted line: " + line);
            }
            task = new Todo(parts[2].trim());
            break;
        case DEADLINE:
            if (parts.length < 4) {
                throw new EdithException("Skip corrupted line: " + line);
            }
            String deadlineDescription = parts[2].trim();
            LocalDateTime by = convertToFormat(parts[3].trim(), line);
            task = new Deadline(deadlineDescription, by);
            break;
        case EVENT:
            if (parts.length < 5) {
                throw new EdithException("Skip corrupted line: " + line);
            }
            String eventDescription = parts[2].trim();
            LocalDateTime from = convertToFormat(parts[3].trim(), line);
            LocalDateTime to = convertToFormat(parts[4].trim(), line);
            task = new Event(eventDescription, from, to);
            break;
        default:
            throw new EdithException("Skip corrupted line: " + line);
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Writes the content into the data file.
     *
     * @param textToAdd Content to write to the data file.
     * @throws IOException If the operation fails.
     */
    public void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(dataFile);
        fw.write(textToAdd);
        fw.close();
    }

    /**
     * Saves the given list of tasks to the disk.
     *
     * @param tasks List of tasks to be saved.
     * @throws EdithException If the operation fails.
     */
    public void save(ArrayList<Task> tasks) throws EdithException {
        try {
            createFile();

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
