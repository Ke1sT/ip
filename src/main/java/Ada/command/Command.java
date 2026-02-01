package Ada.command;

import Ada.AdaException;
import Ada.parser.Parser;
import Ada.storage.Storage;
import Ada.task.*;
import Ada.ui.Ui;

import java.time.LocalDateTime;

/**
 * Represents a parsed user command and provides execution logic
 * against a {@code TaskList}, {@code Ui}, and {@code Storage}.
 */
public class Command {
    private CommandType type;
    private boolean isExit;
    private String[] arguments;

    /**
     * Creates a command of a given type with its arguments.
     *
     * @param type      command type
     * @param arguments parsed arguments for the command
     */
    public Command(CommandType type, String[] arguments) {
        this.type = type;
        this.arguments = arguments;
        this.isExit = (type == CommandType.BYE);
    }

    /**
     * Returns whether this command terminates the application.
     *
     * @return {@code true} if the command is {@code BYE}; {@code false} otherwise
     */
    public boolean isExit() {
        return this.isExit;
    }

    /**
     * Executes this command against the provided components. May mutate
     * the task list and persist changes via storage.
     *
     * @param tasks   task list to operate on
     * @param ui      UI for user interaction
     * @param storage storage used to load/save tasks
     * @throws AdaException if input is invalid or operation fails
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AdaException {
        boolean saveFile = true;
        switch (type) {
        case BYE:
            saveFile = false;
            break;
        case LIST:
            String listing = "";
            for (int i = 0; i < tasks.size(); i++) {
                listing = listing.concat(((i + 1) + ". " + tasks.get(i).toString()) + "\n");
            }
            ui.display(listing);
            saveFile = false;
            break;
        case MARK: {
            try {
                int taskNumber = Integer.parseInt(this.arguments[0]) - 1;
                if (taskNumber >= 0 && taskNumber < tasks.size()) {
                    tasks.get(taskNumber).markAsDone();
                    ui.display("Nice! I've marked this task as done:\n"
                            + tasks.get(taskNumber).toString());
                } else {
                    throw new AdaException("Invalid task number.");
                }
            } catch (AdaException e) {
                throw e;
            } catch (NumberFormatException e) {
                throw new AdaException("Please enter a valid task number");
            }
            break;
        }
        case UNMARK: {
            try {
                int taskNumber = Integer.parseInt(this.arguments[0]) - 1;
                if (taskNumber >= 0 && taskNumber < tasks.size()) {
                    tasks.get(taskNumber).unmarkAsDone();
                    ui.display("OK, I've marked this task as not done yet:\n"
                            + tasks.get(taskNumber).toString());
                } else {
                    throw new AdaException("Invalid task number.");
                }
            } catch (AdaException e) {
                throw e;
            } catch (NumberFormatException e) {
                throw new AdaException("Please enter a valid task number");
            }
            break;
        }
        case DELETE: {
            try {
                int taskNumber = Integer.parseInt(this.arguments[0]) - 1;
                if (taskNumber >= 0 && taskNumber < tasks.size()) {
                    Task removedTask = tasks.delete(taskNumber);
                    ui.display("Noted. I've removed this task:\n"
                            + removedTask.toString() + "\n"
                            + "Now you have " + tasks.size() + " tasks in the list.");
                } else {
                    throw new AdaException("Invalid task number.");
                }
            } catch (AdaException e) {
                throw e;
            } catch (NumberFormatException e) {
                throw new AdaException("Please enter a valid task number");
            }
            break;
        }
        case FIND: {
            saveFile = false;
            if (this.arguments[0].isEmpty()) {
                throw new AdaException("Please provide at least one keyword");
            }
            boolean taskMatch[] = new boolean[tasks.size()];

            for (int i = 0; i < tasks.size(); i++) {
                for (String keyword : this.arguments) {
                    if (tasks.get(i).getDescription().contains(keyword)) {
                        taskMatch[i] = true;
                        continue;
                    }
                }
            }

            String matches = "";
            for (int i = 0; i < tasks.size(); i++) {
                if (taskMatch[i]) {
                    matches = matches.concat(tasks.get(i).toString()) + "\n";
                }
            }
            ui.display("Here are the matching tasks in your list:\n" + matches);
            break;
        }
        case TODO:
            String description = this.arguments[0];
            if (description.isEmpty()) {
                throw new AdaException("The description of a todo cannot be empty.");
            }
            tasks.add(new Todo(description));
            ui.display("Got it. I've added this task:\n"
                    + tasks.get(tasks.size() - 1).toString() + "\n"
                    + "Now you have " + tasks.size() + " tasks in the list.");
            break;
        case DEADLINE: {
            try {
                LocalDateTime by = Parser.parseDateTime(this.arguments[1]);
                tasks.add(new Deadline(this.arguments[0], by));
                ui.display("Got it. I've added this task:\n"
                        + tasks.get(tasks.size() - 1).toString() + "\n"
                        + "Now you have " + tasks.size() + " tasks in the list.");
            } catch (Exception e) {
                throw new AdaException("Please enter valid dates in the format yyyy-MM-dd [HH:mm]");
            }
            break;
        }
        case EVENT: {
            try {
                LocalDateTime from = Parser.parseDateTime(this.arguments[1]);
                LocalDateTime to = Parser.parseDateTime(this.arguments[2]);
                tasks.add(new Event(this.arguments[0], from, to));
                ui.display("Got it. I've added this task:\n"
                        + tasks.get(tasks.size() - 1).toString() + "\n"
                        + "Now you have " + tasks.size() + " tasks in the list.");
            } catch (Exception e) {
                throw new AdaException("Please enter valid dates in the format yyyy-MM-dd [HH:mm]");
            }
            break;
        }
        default:
            throw new AdaException("I'm sorry, but I don't know what that means.");
        }
        if (saveFile) {
            storage.save(tasks);
        }

    }
}