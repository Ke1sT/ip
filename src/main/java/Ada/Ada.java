package Ada;

import Ada.command.Command;
import Ada.parser.Parser;
import Ada.storage.Storage;
import Ada.task.TaskList;

/**
 * Main application that manages tasks with persistent storage and a console UI.
 * Coordinates parsing user commands and executing operations until exit.
 */
public class Ada {
    private Storage storage;
    private TaskList tasks = new TaskList();
    private boolean isExit = false;
    private boolean isError = false;

    /**
     * Constructs an Ada instance backed by a storage file.
     *
     * @param filepath path to the save file used by storage
     */
    public Ada(String filepath) {
        this.storage = new Storage(filepath);
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        this.isError = false;

        try {
            Command c = Parser.parse(input);
            this.isExit = c.isExit();
            return c.execute(this.tasks, this.storage);
        } catch (AdaException e) {
            this.isError = true;
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Retrieves taskslist from storage
     */
    public String getTasks() {
        this.isError = false;

        try {
            this.tasks = storage.load();
            return "Successfully loaded task list";
        } catch (AdaException e) {
            this.isError = true;
            return "Error: " + e.getMessage() + " Starting with empty task list";
        }
    }

    /**
     * Returns value of isExit.
     */
    public boolean isExit() {
        return this.isExit;
    }

    /**
     * Returns value of isError.
     */
    public boolean isError() {
        return this.isError;
    }
}

