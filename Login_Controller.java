package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.Buffer;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * <h1>Login_Controller</h1>
 * This class is used to handle the login page of the application
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Login_Controller implements Initializable{

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    @FXML
    private Button reset;
    @FXML
    private ChoiceBox<String> language_choice;
    @FXML
    private Label timezone;
    @FXML
    private Label language;
    @FXML
    private Label time;
    @FXML
    private Label username_label;
    @FXML
    private Label password_label;

    private final String[] languages = {"English", "French"};

    public static Users current_user;


    //This zoneId is used to get the timezone of the user's device
    public static ZoneId zone = ZoneId.systemDefault();
    //This zoneId is used as the base utc zone throughout the whole program
    public static ZoneId utc_zone = ZoneId.of("UTC");
    //This zoneId is used as the base est zone throughout the whole program
    public static ZoneId est_zone = ZoneId.of("America/New_York");

    //This is used to create a login message only appear right when you login and not later on
    public static int login_switch = 0;

    //language will get the language code that the current computer has for it's default. That way we can have it display
    //in either English(en) or French(fr)
    private final String language_check = System.getProperty("user.language");

    /**
     * <h1>Initialize</h1>
     * This method is used to initialize the contents of my login page including the users current timezone and language.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timezone.setText(String.valueOf(zone));
        language_choice.getItems().addAll(languages);
        language_choice.setValue("English");
        language_choice.setOnAction(this::change_language);

        if(language_check.equals("fr")){
            login.setText("Connexion");
            reset.setText("Réinitialiser");
            time.setText("Fuseau horaire");
            language.setText("Langue");
            username_label.setText("Nom d'utilisateur");
            password_label.setText("Mot de passe");
            language_choice.setValue("French");
        }


    }


    /**
     * <h1>Login Click</h1>
     * This method is used to check the inputted data and see if it matches any user login info in the database.
     * If the login info is correct, it changes the root to the main menu of the application.
     * @param event
     * @throws IOException
     */
    public void login_click(ActionEvent event) throws IOException {
        ObservableList<Users> user_list = DBConnections.get_all_users();
        int checker = 0;

        for (Users user : user_list) {
            if (username.getText().equals(user.get_name()) && password.getText().equals(user.get_password())) {
                LocalDateTime time_now = LocalDateTime.now();

                //Need to log the successful login attempt
                try {
                    FileWriter my_writer = new FileWriter("login_activity.txt", true);
                    BufferedWriter bw = new BufferedWriter(my_writer);
                    PrintWriter pw = new PrintWriter(bw);
                    pw.println("User:" + user.get_name() + " successfully logged in at " + time_now);
                    pw.close();
                    bw.close();
                    my_writer.close();
                } catch (IOException e){
                    e.printStackTrace();
                }


                current_user = user;

                //Need to check if the user is just logging in and if so we need to check if there are any upcoming appointments
                if(Login_Controller.login_switch == 0){
                    LocalDateTime now = LocalDateTime.now();
                    for(Appointments appointment : DBConnections.get_all_appointments()){

                        int check_T = 0;
                        String start_time = "";

                        //Check for the appointment start
                        for (int i = 0; i < appointment.getStart_whole().length(); i++) {
                            if(appointment.getStart_whole().charAt(i) == ' '){
                                start_time = start_time + "T";
                                check_T = 1;
                            } else {
                                start_time = start_time + appointment.getStart_whole().charAt(i);
                            }
                        }
                        if(check_T == 1){
                            start_time = start_time + ".00";
                        } else {
                            start_time = start_time + ":00.00";
                        }

                        LocalDateTime start = LocalDateTime.parse(start_time);

                        //Duration is used to check the difference between the two dates in minutes
                        Duration duration = Duration.between(now, start);

                        //Checking to see if there are any appointments that start within 15 minutes
                        if(duration.toMinutes() <= 15 && duration.toMinutes() >= 0){
                            //Error popup
                            Popup error = new Popup();


                            Label pLabel = new Label();

                            pLabel.setText("Alert: You have an upcoming appointment with ID:" + appointment.getAppointment_id() + " Time:" + appointment.getStart_time() + " Date:" + appointment.getStart_date());

                            pLabel.setMinWidth(300);
                            pLabel.setMinHeight(160);
                            pLabel.setStyle("-fx-background-color:#D5D5D5; -fx-font-size:15;-fx-font-weight:bold");
                            pLabel.setPadding(new Insets(20));

                            Button pButton = new Button();

                            pButton.setText("Close");

                            pButton.setMinWidth(50);
                            pButton.setMinHeight(30);

                            EventHandler<ActionEvent> close = new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    error.hide();
                                }
                            };

                            pButton.setOnAction(close);

                            error.getContent().add(pLabel);
                            error.getContent().add(pButton);

                            if (!error.isShowing()) {
                                error.show(login.getScene().getWindow());
                            }
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Appointments.fxml"));
                            Parent root = loader.load();
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setTitle("Menu");
                            stage.show();
                            return;
                        }
                    }
                }

                //Error popup
                Popup error = new Popup();


                Label pLabel = new Label();

                pLabel.setText("Alert: There are no upcoming appointments");

                pLabel.setMinWidth(300);
                pLabel.setMinHeight(160);
                pLabel.setStyle("-fx-background-color:#D5D5D5; -fx-font-size:15;-fx-font-weight:bold");
                pLabel.setPadding(new Insets(20));

                Button pButton = new Button();

                pButton.setText("Close");

                pButton.setMinWidth(50);
                pButton.setMinHeight(30);

                EventHandler<ActionEvent> close = new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        error.hide();
                    }
                };

                pButton.setOnAction(close);

                error.getContent().add(pLabel);
                error.getContent().add(pButton);

                if (!error.isShowing()) {
                    error.show(login.getScene().getWindow());
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Appointments.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Menu");
                stage.show();
                return;
            }
        }
        LocalDateTime time_now = LocalDateTime.now();
        //Need to log the attempt to login
        try {
            FileWriter my_writer = new FileWriter("login_activity.txt", true);
            BufferedWriter bw = new BufferedWriter(my_writer);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("Unsuccessful attempt to login occurred on " + time_now + " User input:" + username.getText() + " Password:" + password.getText());
            pw.close();
            bw.close();
            my_writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }


        //Error popup
        Popup error = new Popup();


        Label pLabel = new Label();
        if (language_choice.getSelectionModel().getSelectedItem().equals("English")) {
            pLabel.setText("Error: Enter a correct username and password");
        } else {
            pLabel.setText("Erreur : Entrez un nom d'utilisateur et un mot de passe corrects");
        }
        pLabel.setMinWidth(300);
        pLabel.setMinHeight(160);
        pLabel.setStyle("-fx-background-color:#D5D5D5; -fx-font-size:15;-fx-font-weight:bold");
        pLabel.setPadding(new Insets(20));

        Button pButton = new Button();
        if (language_choice.getSelectionModel().getSelectedItem().equals("English")) {
            pButton.setText("Close");
        } else {
            pButton.setText("Fermer");
        }
        pButton.setMinWidth(50);
        pButton.setMinHeight(30);

        EventHandler<ActionEvent> close = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                error.hide();
            }
        };

        pButton.setOnAction(close);

        error.getContent().add(pLabel);
        error.getContent().add(pButton);

        if (!error.isShowing()) {
            error.show(reset.getScene().getWindow());
        }
    }

    /**
     * <h1>Reset Click</h1>
     * This method clears the textfields in the login page.
     * @param event
     * @throws IOException
     */
    public void reset_click(ActionEvent event) throws IOException{
        username.setText("");
        password.setText("");
    }

    /**
     * <H1>Change Language</H1>
     * This method is used to change the language of the page to either french or english.
     * @param event
     */
    public void change_language(ActionEvent event){
        if(language_choice.getSelectionModel().getSelectedItem().equals("French")){
            login.setText("Connexion");
            reset.setText("Réinitialiser");
            time.setText("Fuseau horaire:");
            language.setText("Langue");
            username_label.setText("Nom d'utilisateur");
            password_label.setText("Mot de passe");
        }
        if(language_choice.getSelectionModel().getSelectedItem().equals("English")){
            login.setText("Login");
            reset.setText("Reset");
            time.setText("TimeZone:");
            language.setText("Language");
            username_label.setText("Username");
            password_label.setText("Password");
        }
    }
}
