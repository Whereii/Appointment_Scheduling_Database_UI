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
import java.util.ResourceBundle;

/**
 * <h1>Reports Contacts Controller</h1>
 * This class is used to manage the contact reports page.
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Reports_Contacts_Controller implements Initializable {

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
    private ChoiceBox<Integer> choice;

    @FXML
    private TableView contact_table;

    @FXML
    private TableColumn appointment_id;

    @FXML
    private TableColumn title;

    @FXML
    private TableColumn description;

    @FXML
    private TableColumn type;

    @FXML
    private TableColumn start_time;

    @FXML
    private TableColumn end_time;

    @FXML
    private TableColumn start_date;

    @FXML
    private TableColumn end_date;

    @FXML
    private TableColumn customer_id;

    ObservableList<Appointments> appointments_list = DBConnections.get_all_appointments();

    public static int picker = 0;


    /**
     * <H1>Initialize</H1>
     * This method is used to fill in the tableview with the appropriate data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        choice.setItems(get_id(appointments_list));

        appointment_id.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointment_id"));
        title.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
        description.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
        type.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
        start_time.setCellValueFactory(new PropertyValueFactory<Appointments, String>("start_time"));
        end_time.setCellValueFactory(new PropertyValueFactory<Appointments, String>("end_time"));
        start_date.setCellValueFactory(new PropertyValueFactory<Appointments, String>("start_date"));
        end_date.setCellValueFactory(new PropertyValueFactory<Appointments, String>("end_date"));
        customer_id.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customer_id"));

        choice.setOnAction(this::pick_contact);


    }

    /**
     * <h1>Pick Contact</h1>
     * This method is used to update the tableview based on the contact the user chose.
     * @param event
     */
    private void pick_contact(Event event){
        ObservableList<Appointments> pick_list = get_appoinments_for_contact(choice.getValue());
        contact_table.setItems(pick_list);
    }

    /**
     * <h1>Get ID</h1>
     * This method is used to get the id's of the appointments and make them into an observable list that way they can
     * be displayed for the user to select in a choicebox.
     * @param list
     * @return
     */
    private ObservableList<Integer> get_id(ObservableList<Appointments> list){
        ObservableList<Integer> int_list = FXCollections.observableArrayList();
        for(Appointments appointment : list){
            int_list.add(appointment.getContact());
        }
        return int_list;
    }

    /**
     * <h1>Click Cancel</h1>
     * This method is used to change the root back to the main menu.
     * @param event
     * @throws IOException
     */
    public void click_cancel(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Appointments.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }

    /**
     * <h1>Click Customer</h1>
     * This method is used to change the root to one of the customer reports menus.
     * @param event
     * @throws IOException
     */
    public void click_customer(ActionEvent event) throws IOException{
        //Need to figure out which option the user pressed
        if (customers_type.isSelected()){
            picker = 0;
        } else if(month.isSelected()){
            picker = 1;
        } else if(location.isSelected()){
            picker = 2;
        }

        Parent root = FXMLLoader.load(getClass().getResource("Customer_Reports.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reports");
        stage.show();
    }


    /**
     * <h1>Get Appointments for Contacts</h1>
     * I can use this method to get the list of appointments based on the contact chosen.
     * @param id
     * @return
     */
    private ObservableList<Appointments> get_appoinments_for_contact(int id){
        ObservableList<Appointments> return_list = FXCollections.observableArrayList();
        for(Appointments appointment : appointments_list){
            if(appointment.getContact() == id){
                return_list.add(appointment);
            }
        }
        return return_list;
    }
}
