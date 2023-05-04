package sample;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * <h1>Modify Appointments Controller</h1>
 * This class is used to manage the modify appointments page.
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Modify_Appointments_Controller implements Initializable {

    @FXML
    private TextField id;

    @FXML
    private TextField title;

    @FXML
    private TextField description;

    @FXML
    private TextField location;

    @FXML
    private TextField type;

    @FXML
    private ChoiceBox<Integer> contact_id;

    @FXML
    private ChoiceBox<Integer> customer_id;

    @FXML
    private ChoiceBox<Integer> user_id;

    @FXML
    private DatePicker start_date;

    @FXML
    private DatePicker end_date;

    @FXML
    private Spinner<Integer> start_time_hour;

    @FXML
    private Spinner<Integer> start_time_minute;

    @FXML
    private Spinner<Integer> end_time_hour;

    @FXML
    private Spinner<Integer> end_time_minute;

    @FXML
    private Button save;

    @FXML
    private Button cancel;

    private Appointments modify_appointment;

    //Current time
    LocalDateTime now = LocalDateTime.now();
    //Convert time into usable numbers
    DateTimeFormatter hour_format = DateTimeFormatter.ofPattern("HH");
    String hour_time = now.format(hour_format);
    int current_hour = Integer.valueOf(hour_time);
    //Convert time into usable numbers
    DateTimeFormatter minute_format = DateTimeFormatter.ofPattern("mm");
    String minute_time = now.format(minute_format);
    int current_minute = Integer.valueOf(minute_time);

    //Calander formatter
    DateTimeFormatter date_format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ObservableList<Integer> contact_options = FXCollections.observableArrayList();
    ObservableList<Integer> customer_options = FXCollections.observableArrayList();
    ObservableList<Integer> user_options = FXCollections.observableArrayList();

    /**
     * <h1>Initialize</h1>
     * This method is used to fill in all of the textfields and choiceboxes with the information for the desired appointment.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Find which appointment the user selected
        for(Appointments appointment: DBConnections.get_all_appointments()){
            if(appointment.getAppointment_id() == Appointments_Controller.modify_appointment_id){
                modify_appointment = appointment;
                break;
            }
        }
        id.setText(Integer.toString(modify_appointment.getAppointment_id()));
        title.setText(modify_appointment.getTitle());
        description.setText(modify_appointment.getDescription());
        location.setText(modify_appointment.getLocation());
        type.setText(modify_appointment.getType());

        //Set the contact ids into the choicebox
        DBConnections.get_all_contacts().forEach( i -> contact_options.add(i.getContact_id()));
        contact_id.setItems(contact_options);
        contact_id.setValue(modify_appointment.getContact());

        //Set the customer ids into the choicebox
        DBConnections.get_all_customers().forEach(i -> customer_options.add(i.getCustomer_id()));
        customer_id.setItems(customer_options);
        customer_id.setValue(modify_appointment.getCustomer_id());

        //Set the user ids into the choicebox
        DBConnections.get_all_users().forEach(i -> user_options.add(i.get_id()));
        user_id.setItems(user_options);
        user_id.setValue(modify_appointment.getUser_id());


        //Disable dates before today
        start_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        //Setting up the time section
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime start = LocalDateTime.parse(modify_appointment.getStart_whole());
        LocalDateTime end = LocalDateTime.parse(modify_appointment.getEnd_whole());

        int start_h = Integer.valueOf(start.format(hour_format));
        int start_m = Integer.valueOf(start.format(minute_format));

        int end_h = Integer.valueOf(end.format(hour_format));
        int end_m = Integer.valueOf(end.format(minute_format));

        //Set start date
        start_date.setValue(LocalDate.parse(start.format(date_format)));
        end_date.setValue(LocalDate.parse(start.format(date_format)));

        //Setting the hours picker start time
        SpinnerValueFactory<Integer> start_hour_Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        start_hour_Factory.setValue(Integer.valueOf(start_h));
        start_time_hour.setValueFactory(start_hour_Factory);

        //Setting the minutes picker start time
        SpinnerValueFactory<Integer> start_minute_factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);
        start_minute_factory.setValue(Integer.valueOf(start_m));
        start_time_minute.setValueFactory(start_minute_factory);


        //Setting the hours picker end time
        SpinnerValueFactory<Integer> end_hour_Factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        end_hour_Factory.setValue(Integer.valueOf(end_h));
        end_time_hour.setValueFactory(end_hour_Factory);

        //Setting the minutes picker end time
        SpinnerValueFactory<Integer> end_minute_factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);
        end_minute_factory.setValue(Integer.valueOf(end_m));
        end_time_minute.setValueFactory(end_minute_factory);

    }

    /**
     * <h1>Click Cancel</h1>
     * This method changes the root back to the main menu of the application
     * @param event
     * @throws IOException
     */
    public void click_cancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Appointments.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }

    /**
     * <h1>Click Modify Appointment</h1>
     * This method has to handle all of the error checking of the regular add appointment controller.
     * It then runs the modify method in the DBConnections which deletes the previous object and adds the updated one.
     * @param event
     * @throws IOException
     */
    public void click_modify_appointment(ActionEvent event) throws IOException{
        //Need to make sure that all the text areas are filled
        if(title.getText().isEmpty()){
            throw_error_box_input("Please fill in the title field");
            return;
        } else if(location.getText().isEmpty()){
            throw_error_box_input("Please fill in the location field");
            return;
        } else if(description.getText().isEmpty()){
            throw_error_box_input("Please fill in the description field");
            return;
        } else if(type.getText().isEmpty()){
            throw_error_box_input("Please fill in the type field");
            return;
        } else if(customer_id.getSelectionModel().isEmpty()){
            throw_error_box_input("Please choose a customer ID");
            return;
        } else if(user_id.getSelectionModel().isEmpty()){
            throw_error_box_input("Please choose user ID");
            return;
        } else if(contact_id.getSelectionModel().isEmpty()){
            throw_error_box_input("Please choose contact ID");
            return;
        }

        LocalDate today = LocalDate.now();
        if(start_date.getValue().equals(today) && start_time_hour.getValue() < current_hour){
            throw_error_box();
            return;
        } else if(start_date.getValue().equals(today) && start_time_hour.getValue() > end_time_hour.getValue()){
            throw_error_box();
            return;
        } else if(start_date.getValue().equals(today) && start_time_hour.getValue().equals(end_time_hour.getValue()) && start_time_minute.getValue() > end_time_minute.getValue()){
            throw_error_box();
            return;
        } else if(!start_date.getValue().equals(today) && start_time_hour.getValue() > end_time_hour.getValue()){
            throw_error_box();
            return;
        } else if(!start_date.getValue().equals(today) && start_time_hour.getValue().equals(end_time_hour.getValue()) && start_time_minute.getValue() > end_time_minute.getValue()){
            throw_error_box();
            return;
        }  else if(start_date.getValue().equals(today) && start_time_hour.getValue().equals(current_hour) && start_time_minute.getValue() < current_minute){
            throw_error_box();
            return;
        }

        //Need to checck the hours and minutes to make sure that they have 2 digits
        String check_start = String.valueOf(start_time_hour.getValue());
        if(check_start.length() < 2){
            check_start = "0" + check_start;
        }

        String check_end = String.valueOf(end_time_hour.getValue());
        if(check_end.length() < 2){
            check_end = "0" +  check_end;
        }

        String check_start_m = String.valueOf(end_time_minute.getValue());
        if(check_start_m.length() < 2){
            check_start_m = "0" +  check_start_m;
        }

        String check_end_m = String.valueOf(end_time_minute.getValue());
        if(check_end_m.length() < 2){
            check_end_m = "0" +  check_end_m;
        }


        String start = start_date.getValue() + "T" + check_start + ":" + check_start_m + ":00.00";
        String end = start_date.getValue() + "T" + check_end + ":" + check_end_m + ":00.00";
        ZonedDateTime now = ZonedDateTime.now();
        String create_date_string = "";



        for (int i = 0; i < modify_appointment.getCreate_date().length(); i++) {
            if(modify_appointment.getCreate_date().charAt(i) == ' '){
                create_date_string = create_date_string + "T";
            } else {
                create_date_string = create_date_string + modify_appointment.getCreate_date().charAt(i);
            }
        }
        create_date_string = create_date_string + ".00";

        //Convert the string into a ldt
        LocalDateTime start_ldt = LocalDateTime.parse(start);
        LocalDateTime end_ldt = LocalDateTime.parse(end);
        LocalDateTime create_ldt = LocalDateTime.parse(create_date_string);
        //Convert ldt to a zdt
        ZonedDateTime start_zdt = ZonedDateTime.of(start_ldt, Login_Controller.zone);
        ZonedDateTime end_zdt = ZonedDateTime.of(end_ldt, Login_Controller.zone);
        ZonedDateTime create_zdt = ZonedDateTime.of(create_ldt, Login_Controller.zone);
        //Convert zdt to utc zdt
        ZonedDateTime start_utc_zdt = ZonedDateTime.ofInstant(start_zdt.toInstant(), Login_Controller.utc_zone);
        ZonedDateTime end_utc_zdt = ZonedDateTime.ofInstant(end_zdt.toInstant(), Login_Controller.utc_zone);
        ZonedDateTime now_utc_zdt = ZonedDateTime.ofInstant(now.toInstant(), Login_Controller.utc_zone);
        ZonedDateTime create_utc_zdt = ZonedDateTime.ofInstant(create_zdt.toInstant(), Login_Controller.utc_zone);
        //Format the zdt to localdatetime
        DateTimeFormatter ldt_format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String final_start = start_utc_zdt.format(ldt_format);
        String final_end = end_utc_zdt.format(ldt_format);
        String final_now = now_utc_zdt.format(ldt_format);
        String create_final = create_utc_zdt.format(ldt_format);

        //Need to  make sure there are no overlapping times
        for(Appointments appointment : DBConnections.get_all_appointments()){
            //Skip the appointment that we are modifying
            if(appointment.getAppointment_id() == modify_appointment.getAppointment_id()){
                continue;
            }

            //Need to first get and convert the start time and end time from the appointment
            int check_T = 0;
            String start_time = "";
            String end_time = "";

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


            //Check for the appointment end
            for (int i = 0; i < appointment.getEnd_whole().length(); i++) {
                if(appointment.getEnd_whole().charAt(i) == ' '){
                    end_time = end_time + "T";
                } else {
                    end_time = end_time + appointment.getEnd_whole().charAt(i);
                }
            }
            if(check_T == 1){
                end_time = end_time + ".00";
            } else {
                end_time = end_time + ":00.00";
            }


            //Convert to a localdatetime
            LocalDateTime start_time_ldt = LocalDateTime.parse(start_time);
            LocalDateTime end_time_ldt = LocalDateTime.parse(end_time);


            //Compare to the desired time
            if((start_ldt.isBefore(start_time_ldt) && end_ldt.isAfter(start_time_ldt))){
                throw_error_box_input("You already have an overlapping appointment during that time with ID:" + appointment.getAppointment_id());
                return;
            } else if(start_ldt.isBefore(end_time_ldt) && end_ldt.isAfter(end_time_ldt)){
                throw_error_box_input("You already have an appointment in that time slot with the ID:" + appointment.getAppointment_id());
                return;
            }
        }


        //Need to check if the inputted time is within standard business hours
        ZonedDateTime est_start = ZonedDateTime.ofInstant(start_zdt.toInstant(), Login_Controller.est_zone);
        if(est_start.getHour() < 8 || est_start.getHour() > 22){
            Popup error = new Popup();


            Label pLabel = new Label();

            pLabel.setText("Error: Inputted starting hours are outside of business hours. Please enter hours between 8am and 10pm EST");

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
                error.show(id.getScene().getWindow());
            }
            return;
        }

        ZonedDateTime est_end = ZonedDateTime.ofInstant(end_zdt.toInstant(), Login_Controller.est_zone);
        if(est_end.getHour() < 8 || est_end.getHour() > 22){
            Popup error = new Popup();


            Label pLabel = new Label();

            pLabel.setText("Error: Inputted ending hours are outside of business hours. Please enter hours between 8am and 10pm EST");

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
                error.show(id.getScene().getWindow());
            }
            return;
        }


        DBConnections.modify_appointment(modify_appointment.getAppointment_id(), title.getText(), description.getText(), location.getText(), type.getText(), final_start, final_end, create_final, modify_appointment.getCreated_by(), final_now, Login_Controller.current_user.get_name(), customer_id.getValue(), user_id.getValue(), contact_id.getValue());

        Parent root = FXMLLoader.load(getClass().getResource("Appointments.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }

    /**
     * <h1>Throw Error Box</h1>
     * This method is used to throw a generic error popup box.
     */
    public void throw_error_box(){
        Popup error = new Popup();


        Label pLabel = new Label();

        pLabel.setText("Error: Please input a proper date and time");

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
            error.show(id.getScene().getWindow());
        }
    }

    /**
     * <h1>Throw Error Box Input</h1>
     * This method is used to show a custom popup box
     * @param message
     */
    public void throw_error_box_input(String message){
        Popup error = new Popup();


        Label pLabel = new Label();

        pLabel.setText(message);

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
            error.show(id.getScene().getWindow());
        }
    }

}
