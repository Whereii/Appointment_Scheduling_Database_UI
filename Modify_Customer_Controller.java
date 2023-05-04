package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * <h1>Modify Customer Controller</h1>
 * This class manages all the inner workings of the modify customer menu.
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Modify_Customer_Controller implements Initializable {

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField postal_code;

    @FXML
    private TextField phone_number;

    @FXML
    private ChoiceBox countries;

    @FXML
    private ChoiceBox divisions;

    @FXML
    private Button save;

    @FXML
    private Button cancel;

    private Customers modify_customer;
    private Division customer_division;

    private final String[] countries_list = {"U.S", "UK", "Canada"};

    private final ObservableList<Division> divisions_list_us = FXCollections.observableArrayList();
    private final ObservableList<Division> divisions_list_uk = FXCollections.observableArrayList();
    private final ObservableList<Division> divisions_list_canada = FXCollections.observableArrayList();

    /**
     * <h1>Initialize</h1>
     * This method is used to fill in all of the text fields and choiceboxes for the desired customer.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Get the customer that we are editing
        for(Customers customer : DBConnections.get_all_customers()){
            if(customer.getCustomer_id() == Customers_Controller.modify_customer_id){
                modify_customer = customer;
                break;
            }
        }
        id.setText(Integer.toString(modify_customer.getCustomer_id()));
        name.setText(modify_customer.getCustomer_name());
        address.setText(modify_customer.getAddress());
        postal_code.setText(modify_customer.getPostal_code());
        phone_number.setText(modify_customer.getPhone_number());

        countries.getItems().addAll(countries_list);

        //Find which division the customer is in
        for(Division div : DBConnections.get_all_divisions()){
            if(div.getDivision_id() == modify_customer.getDivision_id()){
                customer_division = div;
                break;
            }
        }


        if(customer_division.getCountry_id() == 1){
            countries.setValue("U.S");
        } else if(customer_division.getCountry_id() == 2){
            countries.setValue("UK");
        } else{
            countries.setValue("Canada");
        }

        countries.setOnAction(this::change_country);
        for(Division division : DBConnections.get_all_divisions()){
            if(division.getCountry_id() == 1){
                divisions_list_us.add(division);
            } else if(division.getCountry_id() == 2){
                divisions_list_uk.add(division);
            } else {
                divisions_list_canada.add(division);
            }
        }

        if(customer_division.getCountry_id() == 1){
            divisions.setItems(get_names(divisions_list_us));
            divisions.setValue(customer_division.getDivision());
        } else if(customer_division.getCountry_id() == 2){
            divisions.setItems(get_names(divisions_list_uk));
            divisions.setValue(customer_division.getDivision());
        } else{
            divisions.setItems(get_names(divisions_list_canada));
            divisions.setValue(customer_division.getDivision());
        }




    }

    /**
     * <h1>Click Cancel</h1>
     * This method is used to change the root back to the main menu.
     * @param event
     * @throws IOException
     */
    public void click_cancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Customers.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }

    /**
     * <h1>Change Country</h1>
     * This method is used to change the division choicebox items based on the country the user chooses.
     * @param event
     */
    public void change_country(Event event){
        if(countries.getSelectionModel().getSelectedItem().equals("U.S")){
            divisions.setItems(get_names(divisions_list_us));
        } else if(countries.getSelectionModel().getSelectedItem().equals("UK")) {
            divisions.setItems(get_names(divisions_list_uk));
        } else if(countries.getSelectionModel().getSelectedItem().equals("Canada")){
            divisions.setItems(get_names(divisions_list_canada));
        }
    }

    /**
     * <h1>Get Names</h1>
     * Used to convert the observable lists into lists of just the names to be displayed
     * @param division
     * @return
     */
    public ObservableList<String> get_names(ObservableList<Division> division){
        ObservableList<String> names = FXCollections.observableArrayList();
        for(Division div : division){
            names.add(div.getDivision());
        }
        return names;
    }

    /**
     * <h1>Click Modify Customer</h1>
     * This method is used to delete the existing customer from the database and then add a new updated one using the provided information.
     * If any of the information is incorrect then this method will raise popup errors.
     * @param event
     * @throws IOException
     */
    public void click_modify_customer(ActionEvent event) throws IOException{
        if(name.getText().isEmpty()){
            throw_error_box("Please provide a name for the customer");
            return;
        } else if(address.getText().isEmpty()){
            throw_error_box("Please provide an address for the customer");
            return;
        } else if(postal_code.getText().isEmpty()){
            throw_error_box("Please provide a postal code for the customer");
            return;
        } else if(phone_number.getText().isEmpty()){
            throw_error_box("Please provide a phone number for the customer");
            return;
        } else if(divisions.getSelectionModel().isEmpty()){
            throw_error_box("Please select a division that the customer is located in");
            return;
        }

        ZonedDateTime now = ZonedDateTime.now();

        //Convert zdt to utc zdt
        ZonedDateTime now_utc_zdt = ZonedDateTime.ofInstant(now.toInstant(), Login_Controller.utc_zone);
        //Format the zdt to localdatetime
        DateTimeFormatter ldt_format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String final_now = now_utc_zdt.format(ldt_format);

        //Get the name of the division selected from choicebox
        String district_name = String.valueOf(divisions.getSelectionModel().getSelectedItem());
        int district_id = 0;

        //Get the division id using the name selected from the list
        for(Division div : DBConnections.get_all_divisions()){
            if(div.getDivision().equals(district_name)){
                district_id = div.getDivision_id();
            }
        }

        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //Add new customer with the proper formating
        DBConnections.modify_customer(modify_customer.getCustomer_id(), name.getText(), address.getText(), postal_code.getText(), phone_number.getText(), modify_customer.getCreated_date(), Login_Controller.current_user.get_name(), final_now, Login_Controller.current_user.get_name(), district_id);

        Parent root = FXMLLoader.load(getClass().getResource("Customers.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }

    /**
     * <h1>Throw Error Box</h1>
     * This method is used to show a custom popup box
     * @param message
     */
    public void throw_error_box(String message){
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
