package Ada.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

import Ada.AdaException;
import Ada.parser.Parser;
import Ada.task.Deadline;
import Ada.task.Event;
import Ada.task.Task;
import Ada.task.TaskList;
import Ada.task.Todo;
import Ada.ui.Ui;

/**
 * Handles loading and saving tasks to a file for persistence.
 */
public class Storage {
    private File saveFile;

    /**
     * Creates a storage bound to the given file path.
     *
     * @param filepath path to the save file
     */
    public Storage(String filepath) {
        this.saveFile = new File(filepath);
    }

    /**
     * Loads tasks from the backing file.
     *
     * @return a {@code TaskList} populated from the file; empty list when the
     *         file is missing or when errors occur
     */
    public TaskList load() throws AdaException {
        TaskList tasks = new TaskList();
        try {
            Scanner scanner = new Scanner(this.saveFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" \\| ");
                String taskType = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                Task task;
                switch (taskType) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    LocalDateTime by = Parser.parseDateTime(parts[3]);
                    task = new Deadline(description, by);
                    break;
                case "E":
                    LocalDateTime from = Parser.parseDateTime(parts[3]);
                    LocalDateTime to = Parser.parseDateTime(parts[4]);
                    task = new Event(description, from, to);
                    break;
                default:
                    throw new AdaException("Unknown task type in file.");
                }

                if (isDone) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
        } catch (FileNotFoundException e) {
            throw new AdaException("No existing task file found.");
        }
        return tasks;
    }

    /**
     * Saves all tasks to the backing file, creating parent directories if needed.
     *
     * @param tasks task list to persist
     * @throws AdaException if writing to the file fails
     */
    public void save(TaskList tasks) throws AdaException {
        try {
            if (!this.saveFile.getParentFile().exists()) {
                this.saveFile.getParentFile().mkdirs();
            }
            FileWriter writer = new FileWriter(saveFile);
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).toDataString() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            throw new AdaException("Error saving tasks to file: " + e.getMessage());
        }
    }
}
