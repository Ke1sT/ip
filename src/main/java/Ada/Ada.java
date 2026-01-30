package Ada;
import Ada.task.TaskList;
import Ada.storage.Storage;
import Ada.ui.Ui;
import Ada.command.Command;
import Ada.parser.Parser;


public class Ada {
    private Storage storage;
    private Ui ui;
    private TaskList tasks;

    public Ada(String filepath) {
        this.storage = new Storage(filepath);
        tasks = storage.load();
        this.ui = new Ui();
    }

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

    public static void main(String[] args) {
        new Ada("./data/ada.txt").run();
    }

}