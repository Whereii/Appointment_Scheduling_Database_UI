package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * <h1>Appointments_Controller</h1>
 * This classes main function is to control the main menu appointments section.
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Appointments_Controller implements Initializable {

    @FXML
    private Button logout;

    @FXML
    private Button delete;

    @FXML
    private Button modify;

    @FXML
    private Button add;

    @FXML
    private Button reports;

    @FXML
    private RadioButton all;

    @FXML
    private RadioButton by_week;

    @FXML
    private RadioButton by_month;

    @FXML
    private RadioButton customers;

    @FXML
    private TextField search_box;

    @FXML
    private TableColumn<Appointments, Integer> appointment_id;

    @FXML
    private TableColumn<Appointments, String> title;

    @FXML
    private TableColumn<Appointments, String> description;

    @FXML
    private TableColumn<Appointments, String> location;

    @FXML
    private TableColumn<Appointments, Integer> contact;

    @FXML
    private TableColumn<Appointments, String> type;

    @FXML
    private TableColumn<Appointments, String> start_time;

    @FXML
    private TableColumn<Appointments, String> end_time;

    @FXML
    private TableColumn<Appointments, String> start_date;

    @FXML
    private TableColumn<Appointments, String> end_date;

    @FXML
    private TableColumn<Appointments, Integer> customer_id;

    @FXML
    private TableColumn<Appointments, Integer> user_id;

    @FXML
    private TableView<Appointments> appointment_table;

    public static int modify_appointment_id;

    private ObservableList<Appointments> appointment_list;

    private final ObservableList<Appointments> all_appointments = DBConnections.get_all_appointments();

    private final ObservableList<Appointments> month_appointments = FXCollections.observableArrayList();
    private final ObservableList<Appointments> week_appointments = FXCollections.observableArrayList();


    /**
     * <h1>Initialize</h1>
     * This methods main function is to initialize the appointment main menu including filling out the table with values.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Finding all appointments this month
        for(Appointments appointment : DBConnections.get_all_appointments()){
            //Get year
            int this_year = LocalDateTime.now().getYear();

            //Get year and month from the appointment;
            int this_month = LocalDateTime.now().getMonthValue();
            LocalDateTime date = LocalDateTime.parse(appointment.getStart_whole());
            int year = date.getYear();
            int month = date.getMonthValue();
            if(this_year == year && this_month == month){
                month_appointments.add(appointment);
            }
        }


        //Finding all appointments this week
        for(Appointments appointment : DBConnections.get_all_appointments()){
            //Get year
            int this_year = LocalDateTime.now().getYear();
            //Get month
            int this_month = LocalDateTime.now().getMonthValue();
            //Get week
            int this_week = LocalDateTime.now().getDayOfMonth();
            int week = (int)Math.ceil(this_week/7);

            //Get year month and week from appointment
            LocalDateTime date = LocalDateTime.parse(appointment.getStart_whole());
            int year = date.getYear();
            int month = date.getMonthValue();
            int appt_week = date.getDayOfMonth();
            int week_chech = (int)Math.ceil(appt_week/7);
            if(this_year == year && this_month == month && week == week_chech){
                week_appointments.add(appointment);
            }
        }
        if(Customers_Controller.appointments_picker == 0){
            all.setSelected(true);
        } else if(Customers_Controller.appointments_picker == 1){
            by_month.setSelected(true);
        } else if(Customers_Controller.appointments_picker == 2){
            by_week.setSelected(true);
        }

        //Find which view they are using
        if(all.isSelected()){
            appointment_list = all_appointments;
        } else if(by_month.isSelected()){
            appointment_list = month_appointments;
        } else if(by_week.isSelected()){
            appointment_list = week_appointments;
        }


        //Populate table
        appointment_id.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointment_id"));
        title.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
        description.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
        location.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
        start_time.setCellValueFactory(new PropertyValueFactory<Appointments, String>("start_time"));
        end_time.setCellValueFactory(new PropertyValueFactory<Appointments, String>("end_time"));
        start_date.setCellValueFactory(new PropertyValueFactory<Appointments, String>("start_date"));
        end_date.setCellValueFactory(new PropertyValueFactory<Appointments, String>("end_date"));
        customer_id.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customer_id"));
        user_id.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("user_id"));

        FilteredList<Appointments> filtered_appointments = new FilteredList<>(appointment_list, b -> true);

        search_box.textProperty().addListener((observable, oldValue, newValue)-> {
            filtered_appointments.setPredicate(newAppointment -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return Integer.toString(newAppointment.getAppointment_id()).indexOf(lowerCaseFilter) != -1;
            });
        });

        SortedList<Appointments> sorted_appointments = new SortedList<>(filtered_appointments);

        sorted_appointments.comparatorProperty().bind(appointment_table.comparatorProperty());

        appointment_table.setItems(sorted_appointments);

    }

    /**
     * <h1>Click Logout</h1>
     * This method is used to change the root back to the login page
     * @param event
     * @throws IOException
     */
    public void click_logout(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    /**
     * <h1>Click View Customers</h1>
     * This method is used to change the root the the customers main menu
     * @param event
     * @throws IOException
     */
    public void click_view_customers(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Customers.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }

    /**
     * <h1>Click Reports</h1>
     * This method is used to change the root the the reports page
     * @param event
     * @throws IOException
     */
    public void click_reports(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Reports_Contacts.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reports");
        stage.show();
    }

    /**
     * <h1>Check Which View</h1>
     * This method is used to see which view button the user is currently trying to look at
     * @param event
     * @throws IOException
     */
    public void check_which_view(ActionEvent event) throws IOException{
        if(all.isSelected()){
            appointment_list = all_appointments;
            appointment_table.setItems(appointment_list);
        } else if(by_month.isSelected()){
            appointment_list = month_appointments;
            appointment_table.setItems(appointment_list);
        } else if(by_week.isSelected()){
            appointment_list = week_appointments;
            appointment_table.setItems(appointment_list);
        }
    }

    public void click_add_appointment(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Add_Appointment.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Appointment");
        stage.show();
    }

    /**
     * <h1>Click Delete Appointment</h1>
     * This method is used to delete the selected appointment.
     * If none is selected then it will throw an error.
     * @param event
     * @throws IOException
     */
    public void click_delete_appointment(ActionEvent event) throws IOException{
        try {
            int appointment_id = appointment_table.getSelectionModel().getSelectedItem().getAppointment_id();

            Appointments apt_holder = DBConnections.get_all_appointments().get(1);
            for(Appointments apt : DBConnections.get_all_appointments()){
                if(apt.getAppointment_id() == appointment_id){
                    apt_holder = apt;
                }
            }

            DBConnections.delete_appointment(appointment_id);
            appointment_list = DBConnections.get_all_appointments();

            appointment_table.setItems(appointment_list);

            //Popup
            Popup error = new Popup();


            Label pLabel = new Label();

            pLabel.setText("Notice: You have deleted appointmentID:" + apt_holder.getAppointment_id() + " Type:" + apt_holder.getType());

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
                error.show(add.getScene().getWindow());
            }


        } catch (Exception e){
            //Error popup
            Popup error = new Popup();


            Label pLabel = new Label();

            pLabel.setText("Error: Please choose an appointment to delete");

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
                error.show(add.getScene().getWindow());
            }
        }
    }

    /**
     * <h1>Click Modify Appointment</h1>
     * This method is used to change the root to the modify appointment page and also check which appointment the user wants to modify.
     * @param event
     * @throws IOException
     */
    public void click_modify_appointment(ActionEvent event) throws IOException{
        try{
            modify_appointment_id = appointment_table.getSelectionModel().getSelectedItem().getAppointment_id();
            Parent root = FXMLLoader.load(getClass().getResource("Modify_Appointments.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Modify Appointments");
            stage.show();

        } catch (Exception e){
            //Error popup
            Popup error = new Popup();


            Label pLabel = new Label();

            pLabel.setText("Error: Please choose an appointment to modify");

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
                error.show(add.getScene().getWindow());
            }
        }
    }
}
