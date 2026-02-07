package Ada;

import java.io.IOException;

import Ada.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Entry point for the Ada JavaFX application.
 * Sets up the main UI components (dialog container, input field, and send button)
 * and displays them on the primary stage.
 */
public class Main extends Application {

    private Ada ada = new Ada("./data/ada.txt");


    /**
     * Initializes and shows the primary UI scene for Ada.
     *
     * @param stage the primary stage provided by the JavaFX runtime
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setAda(ada); // inject the Ada instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
