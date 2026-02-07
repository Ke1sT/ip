package Ada;

import Ada.command.Command;
import Ada.parser.Parser;
import Ada.storage.Storage;
import Ada.task.TaskList;
import Ada.ui.Ui;

/**
 * Main application that manages tasks with persistent storage and a console UI.
 * Coordinates parsing user commands and executing operations until exit.
 */
public class Ada {
    private Storage storage;
    private Ui ui;
    private TaskList tasks = new TaskList();
    private boolean isExit = false;

    /**
     * Constructs an Ada instance backed by a storage file.
     *
     * @param filepath path to the save file used by storage
     */
    public Ada(String filepath) {
        this.storage = new Storage(filepath);
        this.ui = new Ui();
    }

    /**
     * Runs the main interaction loop: reads commands, executes them,
     * persists changes when needed, and terminates when exit is requested.
     */
    public void run() {
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(this.tasks, this.ui, this.storage);
                isExit = c.isExit();
            } catch (AdaException e) {
                ui.display("Error: " + e.getMessage());
            }
        }
        this.ui.goodbye();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            this.isExit = c.isExit();
            return c.execute(this.tasks, this.ui, this.storage);
        } catch (AdaException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Retrieves taskslist from storage
     */
    public String getTasks() {
        try {
            this.tasks = storage.load();
            return "Successfully loaded task list";
        } catch (AdaException e) {
            return "Error: " + e.getMessage() + " Starting with empty task list";
        }
    }

    /**
     * Returns value of isExit.
     */
    public boolean isExit() {
        return this.isExit;
    }
}

