package Ada.ui;

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
    static final String WELCOME_STRING = "Hello! I'm Artificial Directory and Assistant, or Ada\n"
            + " What can I do for you?\n";
    static final String GOODBYE = "Bye. Hope to see you again soon!\n";
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Ada ada;

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
            DialogBox.getAdaDialog(WELCOME_STRING, dukeImage)
        );
    }

    /** Injects the Duke instance */
    public void setAda(Ada d) {
        ada = d;
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
        String response = ada.getResponse(input);
        if (ada.isExit()) {
            dialogContainer.getChildren().add(DialogBox.getAdaDialog(GOODBYE, dukeImage));
            Platform.exit(); // shuts down the JavaFX application
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAdaDialog(response, dukeImage)
        );
        userInput.clear();
    }
}
