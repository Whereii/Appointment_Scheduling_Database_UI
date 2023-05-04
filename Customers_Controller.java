package sample;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>Customers Controller</h1>
 * This class is used to manage the customers menu page.
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Customers_Controller implements Initializable {

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
    private RadioButton customers;

    @FXML
    private RadioButton by_month;

    @FXML
    private RadioButton by_week;

    @FXML
    private TableColumn<Customers, Integer> customer_id;

    @FXML
    private TableColumn<Customers, String> customer_name;

    @FXML
    private TableColumn<Customers, String> address;

    @FXML
    private TableColumn<Customers, String> postal_code;

    @FXML
    private TableColumn<Customers, String> phone_number;

    @FXML
    private TableColumn<Customers, String> created_date;

    @FXML
    private TableColumn<Customers, String> created_by;

    @FXML
    private TableColumn<Customers, String> last_update;

    @FXML
    private TableColumn<Customers, String> last_updated_by;

    @FXML
    private TableColumn<Customers, Integer> state_province;

    @FXML
    private TableView<Customers> customers_table;

    public static int modify_customer_id;

    private ObservableList<Customers> customer_list = DBConnections.get_all_customers();

    public static int appointments_picker = 0;

    /**
     * <h1>Initialize</h1>
     * This method is used to initialize the customer menu with the proper data in the table.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populate table
        customer_id.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("customer_id"));
        customer_name.setCellValueFactory(new PropertyValueFactory<Customers, String>("customer_name"));
        address.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
        postal_code.setCellValueFactory(new PropertyValueFactory<Customers, String>("postal_code"));
        phone_number.setCellValueFactory(new PropertyValueFactory<Customers, String>("phone_number"));
        created_date.setCellValueFactory(new PropertyValueFactory<Customers, String>("created_date"));
        created_by.setCellValueFactory(new PropertyValueFactory<Customers, String>("created_by"));
        last_update.setCellValueFactory(new PropertyValueFactory<Customers, String>("last_update"));
        last_updated_by.setCellValueFactory(new PropertyValueFactory<Customers, String>("last_update_by"));
        state_province.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("division_id"));


        customers_table.setItems(customer_list);

    }

    /**
     * <h1>Click Logout</h1>
     * This method is used ot change the root back to the logout page.
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
     * <h1>Click View Appointments</h1>
     * This method is used to change the root to the appointments page and also figure out how the user wants to view the appointments.
     * @param event
     * @throws IOException
     */
    public void click_view_appointments(ActionEvent event) throws IOException{
        if(all.isSelected()){
            appointments_picker = 0;
        } else if(by_month.isSelected()){
            appointments_picker = 1;
        } else if(by_week.isSelected()){
            appointments_picker = 2;
        }

        Parent root = FXMLLoader.load(getClass().getResource("Appointments.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }

    /**
     * <h1>Click Add Customer</h1>
     * This method is used to change the root to the add customers menu
     * @param event
     * @throws IOException
     */
    public void click_add_customer(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Add_Customer.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Customer");
        stage.show();
    }

    /**
     * <h1>Click Modify Customer</h1>
     * This method is used to change the root to the modify customer page and also figure out which customer the user wants to modify.
     * @param event
     * @throws IOException
     */
    public void click_modify_customer(ActionEvent event) throws IOException{
        try{
            modify_customer_id = customers_table.getSelectionModel().getSelectedItem().getCustomer_id();

            Parent root = FXMLLoader.load(getClass().getResource("Modify_Customer.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Modify Customer");
            stage.show();

        } catch (Exception e){
            //Error popup
            Popup error = new Popup();


            Label pLabel = new Label();

            pLabel.setText("Error: Please choose a customer to modify");

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
     * <h1>Click Reports</h1>
     * This method is used to change the root to the reports menu.
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
     * <h1>Click Delete Customer</h1>
     * This method is used to delete a chosen customer from the customer database and update the table.
     * If no customer is chosen it will throw a popup box.
     * @param event
     * @throws IOException
     */
    public void click_delete_customer(ActionEvent event) throws IOException{
        try {
            int customer_id = customers_table.getSelectionModel().getSelectedItem().getCustomer_id();

            for(Appointments appointment : DBConnections.get_all_appointments()){
                if(appointment.getCustomer_id() == customer_id){
                    //Error popup
                    Popup error = new Popup();


                    Label pLabel = new Label();

                    pLabel.setText("Error: This customer still has upcoming appointments");

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
                    return;
                }
            }

            DBConnections.delete_customer(customer_id);
            customer_list = DBConnections.get_all_customers();

            customers_table.setItems(customer_list);

            //Notification
            Popup error = new Popup();


            Label pLabel = new Label();

            pLabel.setText("You have deleted a customer from the database");

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

            pLabel.setText("Error: Please choose a customer to delete");

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
