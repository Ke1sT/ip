package Ada.storage;

import Ada.AdaException;
import Ada.parser.Parser;
import Ada.task.*;
import Ada.ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Storage {
    File saveFile;

    public Storage(String filepath) {
        this.saveFile = new File(filepath);
    }

    public TaskList load() {
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
        } catch(FileNotFoundException e) {
            Ui.display("No existing task file found. Starting with an empty task list.");
            return tasks;
        }
        catch (AdaException e) {
            // Handle exceptions during loading
            Ui.display("Error: " + e.getMessage() + "\nStarting with an empty task list.");
            return tasks;
        }
        return tasks;
    }

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