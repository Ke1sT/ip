import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class Ada {
    public static void main(String[] args) {

        String WelcomeString = "Hello! I'm Ada\n"
                + " What can I do for you?\n";
        String GoodbyeString = "Bye. Hope to see you again soon!\n";

        display(WelcomeString);

        
        Scanner scanner = new Scanner(System.in);
        String userInput;
        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            try {
                userInput = scanner.nextLine();
                Command command = Command.valueOf(userInput.split(" ")[0].toUpperCase());
                if (command == Command.BYE) {
                    break;
                }

                switch (command) {
                case LIST:
                    for (int i = 0; i < tasks.size(); i++) {
                        display((i + 1) + ". " + tasks.get(i).toString());
                    }
                    break;
                case MARK: {
                    int taskNumber = Integer.parseInt(userInput.substring(5)) - 1;
                    if (taskNumber >= 0 && taskNumber < tasks.size()) {
                        tasks.get(taskNumber).markAsDone();
                        display("Nice! I've marked this task as done:\n"
                                + tasks.get(taskNumber).toString());
                    } else {
                        throw new AdaException("Invalid task number.");
                    }
                    break;
                }
                case UNMARK: {
                    int taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
                    if (taskNumber >= 0 && taskNumber < tasks.size()) {
                        tasks.get(taskNumber).unmarkAsDone();
                        display("OK, I've marked this task as not done yet:\n"
                                + tasks.get(taskNumber).toString());
                    } else {
                        throw new AdaException("Invalid task number.");
                    }
                    break;
                }
                case TODO:
                    String description = userInput.substring(5);
                    if (description.isEmpty()) {
                        throw new AdaException("The description of a todo cannot be empty.");
                    }
                    tasks.add(new Todo(description));
                    display("Got it. I've added this task:\n"
                            + tasks.get(tasks.size() - 1).toString() + "\n"
                            + "Now you have " + tasks.size() + " tasks in the list.");
                    break;
                case DEADLINE: {
                    String[] parts = userInput.substring(9).split(" /by ");
                    if (parts.length == 2) {
                        tasks.add(new Deadline(parts[0], parts[1]));
                        display("Got it. I've added this task:\n"
                                + tasks.get(tasks.size() - 1).toString() + "\n"
                                + "Now you have " + tasks.size() + " tasks in the list.");
                    } else {
                        throw new AdaException("Invalid deadline format. Use: deadline <description> /by <time>");
                    }
                    break;
                }
                case EVENT: {
                    String[] parts = userInput.substring(6).split(" /from | /to ");
                    if (parts.length == 3) {
                        tasks.add(new Event(parts[0], parts[1], parts[2]));
                        display("Got it. I've added this task:\n"
                                + tasks.get(tasks.size() - 1).toString() + "\n"
                                + "Now you have " + tasks.size() + " tasks in the list.");
                    } else {
                        throw new AdaException(
                                "Invalid event format. Use: event <description> /from <start time> /to <end time>");
                    }
                    break;
                }
                case DELETE: {
                    int taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
                    if (taskNumber >= 0 && taskNumber < tasks.size()) {
                        Task removedTask = tasks.remove(taskNumber);
                        display("Noted. I've removed this task:\n"
                                + removedTask.toString() + "\n"
                                + "Now you have " + tasks.size() + " tasks in the list.");
                    } else {
                        throw new AdaException("Invalid task number.");
                    }
                    break;
                }
                default:
                    throw new AdaException("I'm sorry, but I don't know what that means.");
                }
            } catch (AdaException e) {
                display("Error: " + e.getMessage());
            }
        }

        display(GoodbyeString);
        scanner.close();

    }

    static void display(String message) {
        System.out.println("___________________________________________________________\n");
        System.out.println(message);
        System.out.println("\n___________________________________________________________");
    }
    
    static ArrayList<Task> loadTasks(File saveFile) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(saveFile);
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
                        String by = parts[3];
                        task = new Deadline(description, by);
                        break;
                    case "E":
                        String from = parts[3];
                        String to = parts[4];
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
            display("No existing task file found. Starting with an empty task list.");
            return tasks;
        }
        catch (AdaException e) {
            // Handle exceptions during loading
            display("Error: " + e.getMessage() + "\nStarting with an empty task list.");
            return tasks;
        }
        return tasks;
    }

    static void saveTasks(File saveFile, ArrayList<Task> tasks) {
        try {
            FileWriter writer = new FileWriter(saveFile);
            for (Task task : tasks) {
                writer.write(task.toDataString() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            display("Error saving tasks to file: " + e.getMessage());
        }
    }

    
}
