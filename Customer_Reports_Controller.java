package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * <h1>Customer Reports Controller</h1>
 * This class used used to manage the customer reports page of the application.
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Customer_Reports_Controller implements Initializable {

    @FXML
    private Button cancel;

    @FXML
    private RadioButton contact;

    @FXML
    private RadioButton customers_type;

    @FXML
    private RadioButton month;

    @FXML
    private RadioButton location;

    @FXML
    private Label choice_label;

    @FXML
    private ChoiceBox<String> choice;

    @FXML
    private TableView customer_table;

    @FXML
    private TableColumn customer_id;

    @FXML
    private TableColumn customer_name;

    @FXML
    private TableColumn address;

    @FXML
    private TableColumn postal_code;

    @FXML
    private TableColumn phone_number;

    @FXML
    private TableColumn created_date;

    @FXML
    private TableColumn created_by;

    @FXML
    private TableColumn last_update;

    @FXML
    private TableColumn last_updated_by;

    @FXML
    private TableColumn division_id;

    private final ObservableList<Customers> customers_list = DBConnections.get_all_customers();
    private final ObservableList<Appointments> appointment_list = DBConnections.get_all_appointments();

    private final String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private final ObservableList<String> month_list = FXCollections.observableArrayList();


    /**
     * <h1>Initialize</h1>
     * This method is used to setup the view for the customer reports page and fill in any information needed.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customer_id.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customer_id"));
        customer_name.setCellValueFactory(new PropertyValueFactory<Appointments, String>("customer_name"));
        address.setCellValueFactory(new PropertyValueFactory<Appointments, String>("address"));
        postal_code.setCellValueFactory(new PropertyValueFactory<Appointments, String>("postal_code"));
        phone_number.setCellValueFactory(new PropertyValueFactory<Appointments, String>("phone_number"));
        created_date.setCellValueFactory(new PropertyValueFactory<Appointments, String>("created_date"));
        created_by.setCellValueFactory(new PropertyValueFactory<Appointments, String>("created_by"));
        last_update.setCellValueFactory(new PropertyValueFactory<Appointments, String>("last_update"));
        last_updated_by.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("last_update_by"));
        division_id.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("division_id"));

        //Set which option was chosen
        if(Reports_Contacts_Controller.picker == 0){
            customers_type.setSelected(true);
        } else if(Reports_Contacts_Controller.picker == 1){
            month.setSelected(true);
        } else if(Reports_Contacts_Controller.picker == 2){
            location.setSelected(true);
        }


        for(String month : months){
            month_list.add(month);
        }

        if(customers_type.isSelected()) {
            choice_label.setText("Type");
            choice.setItems(type(appointment_list));
            choice.setOnAction(this::pick_type);
        } else if (month.isSelected()){
            choice_label.setText("Month");
            choice.setItems(month_list);
            choice.setOnAction(this::pick_month);
        } else if(location.isSelected()){
            choice_label.setText("Location");
            choice.setItems(location(appointment_list));
            choice.setOnAction(this::pick_location);
        }

    }

    /**
     * <h1>Pick Type</h1>
     * This method is used to check which type was chosen by the user and then update the tableview accordingly.
     * @param event
     */
    private void pick_type(Event event){
        ObservableList<Customers> pick_list = get_customer_for_type(choice.getValue());
        customer_table.setItems(pick_list);
    }

    /**
     * <h1>Pick Month</h1>
     * This method is used to check which month was chosen by the user and then update the tableview accordingly.
     * @param event
     */
    private void pick_month(Event event){
        ObservableList<Customers> pick_list = get_customer_for_month(choice.getValue());
        customer_table.setItems(pick_list);
    }

    /**
     * <h1>Pick Location</h1>
     * This method is used to check which location was chosen by the user and then update the tableview accordingly.
     * @param event
     */
    private void pick_location(Event event){
        ObservableList<Customers> pick_list = get_customer_for_location(choice.getValue());
        customer_table.setItems(pick_list);
    }

    /**
     * <h1>Type</h1>
     * This method allows me to retrieve all types from appointments and fill the choicebox with them.
     * @param list
     * @return
     */
    private ObservableList<String> type(ObservableList<Appointments> list){
        ObservableList<String> string_list = FXCollections.observableArrayList();
        for(Appointments appointment : list){
            if(!string_list.contains(appointment.getType())) {
                string_list.add(appointment.getType());
            }
        }
        return string_list;
    }

    /**
     * <h1>Location</h1>
     * This method allows me to retrieve all locations from appointments and fill the choicebox with them
     * @param list
     * @return
     */
    private ObservableList<String> location(ObservableList<Appointments> list){
        ObservableList<String> string_list = FXCollections.observableArrayList();
        for(Appointments appointment : list){
            if(!string_list.contains(appointment.getLocation())){
                string_list.add(appointment.getLocation());
            }
        }
        return string_list;
    }


    /**
     * <h1>Get Customer for Type</h1>
     * I can use this method to get the list of customers based on the provided type.
     * @param type
     * @return
     */
    private ObservableList<Customers> get_customer_for_type(String type){
        ObservableList<Customers> return_list = FXCollections.observableArrayList();
        for(Appointments appointment : appointment_list){
            if(appointment.getType().equals(type)){
                for(Customers customer : customers_list){
                    if(customer.getCustomer_id() == appointment.getCustomer_id()){
                        return_list.add(customer);
                        break;
                    }
                }
            }
        }
        return return_list;
    }

    /**
     * <h1>Get Customer for Month</h1>
     * Used to find customers based on month chosen.
     * @param month
     * @return
     */
    private ObservableList<Customers> get_customer_for_month(String month){

        LocalDate now = LocalDate.now();

        ObservableList<Customers> return_list = FXCollections.observableArrayList();
        for(Appointments appointment : appointment_list){
            if(now.getYear() == LocalDate.parse(appointment.getStart_date()).getYear() && String.valueOf(LocalDate.parse(appointment.getStart_date()).getMonth()).equals(month)){
                for(Customers customer : customers_list){
                    if(customer.getCustomer_id() == appointment.getCustomer_id()){
                        return_list.add(customer);
                        break;
                    }
                }
            }
        }
        return return_list;
    }

    /**
     * <h1>Get Customer for Location</h1>
     * Used to find customers based on location chosen.
     * @param location
     * @return
     */
    private ObservableList<Customers> get_customer_for_location(String location){

        ObservableList<Customers> return_list = FXCollections.observableArrayList();

        for(Appointments appointment : appointment_list){
            if(appointment.getLocation().equals(location)){
                for(Customers customer : customers_list){
                    if(customer.getCustomer_id() == appointment.getCustomer_id()){
                        return_list.add(customer);
                        break;
                    }
                }
            }
        }
        return return_list;
    }

    /**
     * <h1>Click Cancel</h1>
     * This method is used to change the root back to the main menu
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
     * <h1>Click Contacts</h1>
     * This method is used to change the root to the contact reports menu.
     * @param event
     * @throws IOException
     */
    public void click_contacts(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Reports_Contacts.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reports");
        stage.show();
    }

    /**
     * <h1>Click Month</h1>
     * This method is used to change the page to the by month customer reports page.
     * @param event
     * @throws IOException
     */
    public void click_month(ActionEvent event) throws IOException {
        Reports_Contacts_Controller.picker = 1;

        Parent root = FXMLLoader.load(getClass().getResource("Customer_Reports.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reports");
        stage.show();
    }

    /**
     * <h1>Click by Type</h1>
     *
     * This method is used to change the page to the by type customer reports page.
     * @param event
     * @throws IOException
     */
    public void click_by_type(ActionEvent event) throws IOException {
        Reports_Contacts_Controller.picker = 0;

        Parent root = FXMLLoader.load(getClass().getResource("Customer_Reports.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reports");
        stage.show();

    }

    /**
     * <h1>Click by Location</h1>
     * This method is used to change the page to the by location customer reports page.
     * @param event
     * @throws IOException
     */
    public void click_by_location(ActionEvent event) throws IOException {
        Reports_Contacts_Controller.picker = 2;

        Parent root = FXMLLoader.load(getClass().getResource("Customer_Reports.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reports");
        stage.show();

    }
}
