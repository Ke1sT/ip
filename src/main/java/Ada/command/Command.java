package Ada.command;

import java.time.LocalDateTime;

import Ada.AdaException;
import Ada.parser.Parser;
import Ada.storage.Storage;
import Ada.task.Deadline;
import Ada.task.Event;
import Ada.task.Task;
import Ada.task.TaskList;
import Ada.task.Todo;
import Ada.ui.Ui;

/**
 * Represents a parsed user command and provides execution logic
 * against a {@code TaskList}, {@code Ui}, and {@code Storage}.
 */
public class Command {
    private final CommandType type;
    private final boolean isExit;
    private final String[] arguments;

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
    public String execute(TaskList tasks, Ui ui, Storage storage) throws AdaException {
        switch (type) {
        case BYE:
            break;
        case LIST:
            String listing = "";
            for (int i = 0; i < tasks.size(); i++) {
                listing = listing.concat(((i + 1) + ". " + tasks.get(i).toString()) + "\n");
            }
            return listing;
        case MARK: {
            try {
                int taskNumber = Integer.parseInt(this.arguments[0]) - 1;
                if (taskNumber >= 0 && taskNumber < tasks.size()) {
                    tasks.get(taskNumber).markAsDone();
                    storage.save(tasks);
                    return ("Nice! I've marked this task as done:\n"
                            + tasks.get(taskNumber).toString());
                } else {
                    throw new AdaException("Invalid task number.");
                }
            } catch (AdaException e) {
                throw e;
            } catch (NumberFormatException e) {
                throw new AdaException("Please enter a valid task number");
            }
        }
        case UNMARK: {
            try {
                int taskNumber = Integer.parseInt(this.arguments[0]) - 1;
                if (taskNumber >= 0 && taskNumber < tasks.size()) {
                    tasks.get(taskNumber).unmarkAsDone();
                    storage.save(tasks);
                    return ("OK, I've marked this task as not done yet:\n"
                            + tasks.get(taskNumber).toString());
                } else {
                    throw new AdaException("Invalid task number.");
                }
            } catch (AdaException e) {
                throw e;
            } catch (NumberFormatException e) {
                throw new AdaException("Please enter a valid task number");
            }
        }
        case DELETE: {
            try {
                int taskNumber = Integer.parseInt(this.arguments[0]) - 1;
                if (taskNumber >= 0 && taskNumber < tasks.size()) {
                    Task removedTask = tasks.delete(taskNumber);
                    storage.save(tasks);
                    return ("Noted. I've removed this task:\n"
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
        }
        case FIND: {
            if (this.arguments[0].isEmpty()) {
                throw new AdaException("Please provide at least one keyword");
            }
            TaskList matchingTasks = tasks.findMatchingTasks(this.arguments);
            return ("Here are the matching tasks in your list:\n" + matchingTasks.toString());
        }
        case TODO:
            String description = this.arguments[0];
            if (description.isEmpty()) {
                throw new AdaException("The description of a todo cannot be empty.");
            }
            Todo newTodo = new Todo(description);
            tasks.add(newTodo);
            storage.save(tasks);
            assert tasks.get(tasks.size() - 1) == newTodo : "The last task in the list should be the newly added todo.";
            return ("Got it. I've added this task:\n"
                    + newTodo.toString() + "\n"
                    + "Now you have " + tasks.size() + " tasks in the list.");
        case DEADLINE: {
            try {
                LocalDateTime by = Parser.parseDateTime(this.arguments[1]);
                Deadline newDeadline = new Deadline(this.arguments[0], by);
                tasks.add(newDeadline);
                storage.save(tasks);
                assert tasks.get(tasks.size() - 1) == newDeadline : "The last task in the list should be the newly added deadline.";
                return ("Got it. I've added this task:\n"
                        + newDeadline.toString() + "\n"
                        + "Now you have " + tasks.size() + " tasks in the list.");
            } catch (Exception e) {
                throw new AdaException("Please enter valid dates in the format yyyy-MM-dd [HH:mm]");
            }
        }
        case EVENT: {
            try {
                LocalDateTime from = Parser.parseDateTime(this.arguments[1]);
                LocalDateTime to = Parser.parseDateTime(this.arguments[2]);
                Event newEvent = new Event(this.arguments[0], from, to);
                tasks.add(newEvent);
                assert tasks.get(tasks.size() - 1) == newEvent : "The last task in the list should be the newly added event.";
                storage.save(tasks);
                return ("Got it. I've added this task:\n"
                        + newEvent.toString() + "\n"
                        + "Now you have " + tasks.size() + " tasks in the list.");
            } catch (Exception e) {
                throw new AdaException("Please enter valid dates in the format yyyy-MM-dd [HH:mm]");
            }
        }
        default:
            throw new AdaException("I'm sorry, but I don't know what that means.");
        }

        return "Unknown Error. Failed to execute command";
    }
}
