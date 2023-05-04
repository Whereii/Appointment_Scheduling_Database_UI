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
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import java.time.format.DateTimeFormatter;

/**
 * <h1>Add Customer Controller</h1>
 * This class's main purpose is to provide the ability for the user to add customers to the database.
 * It should be fully able to handle any error or faulty input the user provides.
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Add_Customer_Controller implements Initializable {

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

    private final String[] countries_list = {"U.S", "UK", "Canada"};

    private final ObservableList<Division> divisions_list_us = FXCollections.observableArrayList();
    private final ObservableList<Division> divisions_list_uk = FXCollections.observableArrayList();
    private final ObservableList<Division> divisions_list_canada = FXCollections.observableArrayList();


    /**
     * <h1>Initialize</h1>
     * This method is used to setup everythin in the add customer display including, the id field, and the choicebox
     * filled with the 3 countries and then handling all of the options for the divisions.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int id_next = 0;
        for(Customers cust : DBConnections.get_all_customers()){
            if(cust.getDivision_id() > id_next){
                id_next = cust.getCustomer_id();
            }
        }

        id.setText(Integer.toString(id_next + 1));

        countries.getItems().addAll(countries_list);
        countries.setValue("U.S");
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
        divisions.setItems(get_names(divisions_list_us));


    }

    /**
     * <h1>Click Cancel</h1>
     * This method is used to return the user to the main menu without adding a customer.
     *
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
     * This method is used to change the different district options based on the inputted country in real time.
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

    //Used to convert the observable lists into lists of just the names to be displayed
    public ObservableList<String> get_names(ObservableList<Division> division){
        ObservableList<String> names = FXCollections.observableArrayList();
        for(Division div : division){
            names.add(div.getDivision());
        }
        return names;
    }

    /**
     * <h1>Add Customer</h1>
     * This is the core of this class. Upon press, it checks all the users inputs to make sure they are correct
     * and then will add the users inputted information to insert a new customer to the database.
     * @param event
     * @throws IOException
     */
    public void add_customer(ActionEvent event) throws IOException {

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
        //Add new customer with the proper formatting
        DBConnections.add_customer(Integer.valueOf(id.getText()), name.getText(), address.getText(), postal_code.getText(), phone_number.getText(), final_now, Login_Controller.current_user.get_name(), final_now, Login_Controller.current_user.get_name(), district_id);

        Parent root = FXMLLoader.load(getClass().getResource("Customers.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }

    /**
     * <h1>Throw Error Box</h1>
     * This method is used for showing a custom popup box.
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
