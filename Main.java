package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * <h1>Main</h1>
 * <h2>Type</h2>
 * Root
 * This class is the initializer of the whole application.
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Main extends Application {

    /**
     * <h1>Start</h1>
     * This method starts the application by opening the login page.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Right once my application loads I am calling methods to load the data from the database into containers
        //for me to be able to use and access while the software is active, that way there is no need to keep
        // getting the data from the database anymore.

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * <h1>main</h1>
     * This method starts the connection to the database.
     * @param args
     */
    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
    }
}
