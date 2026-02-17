package Ada.ui;

import java.io.File;
import java.io.IOException;

import Ada.Ada;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    static final String WELCOME_STRING = "Hello! I'm Artificial Directory and Assistant, or Ada\n";
    static final String GOODBYE = "Bye. Hope to see you again soon!\n";
    static final String DATA_PROMPT = "Enter data file path or press Enter to use default:\n";
    static final String DEFAULT_DATA_PATH = "./data/ada.txt";
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Ada ada;
    private boolean awaitingDataPath = true;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));


    /**
     * Starts the dialog box up and enters welcome message
     * .
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
            DialogBox.getAdaDialog(WELCOME_STRING, dukeImage),
            DialogBox.getAdaDialog(DATA_PROMPT, dukeImage)
        );
    }

    /** Injects the chatbot instance */
    public void setAda(Ada d) {
        ada = d;
        this.awaitingDataPath = false;
        String output = ada.getTasks();
        dialogContainer.getChildren().addAll(
                DialogBox.getAdaDialog(output, dukeImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (this.awaitingDataPath) {
            getDataPath();
            this.awaitingDataPath = false;
            return;
        }
        String response = ada.getResponse(input);
        if (ada.isExit()) {
            dialogContainer.getChildren().add(DialogBox.getAdaDialog(GOODBYE, dukeImage));
            Platform.exit(); // shuts down the JavaFX application
        }

        if (ada.isError()) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getAdaErrorDialog(response, dukeImage)
            );
            userInput.clear();
            return;
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAdaDialog(response, dukeImage)
        );
        userInput.clear();
    }

    private boolean isValidDataFilePath(String path) {
        if (path.isBlank()) {
            return false;
        }
        if (!path.endsWith(".txt")) {
            return false;
        }
        File file = new File(path);
        if (file.isDirectory()) {
            return false;
        }
        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    void getDataPath() {
        String dataFileinput = userInput.getText().trim();
        boolean isValid = isValidDataFilePath(dataFileinput);
        String dataFilePath = isValid ? dataFileinput : DEFAULT_DATA_PATH;
        String statusMessage = buildDataPathMessage(dataFileinput, isValid, dataFilePath);
        ada = new Ada(dataFilePath);
        this.awaitingDataPath = false;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(dataFileinput.isEmpty() ? "(default)" : dataFileinput, userImage),
                DialogBox.getAdaDialog(statusMessage, dukeImage),
                DialogBox.getAdaDialog(ada.getTasks(), dukeImage)
        );
        userInput.clear();
    }

    private String buildDataPathMessage(String input, boolean isValid, String dataFilePath) {
        if (input.isBlank()) {
            return "Using default data file: " + DEFAULT_DATA_PATH;
        }
        if (!isValid) {
            return "Invalid file path. Using default data file: " + DEFAULT_DATA_PATH;
        }
        return "Using data file: " + dataFilePath;
    }
}
