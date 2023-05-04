package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h1>DBConnections</h1>
 * This class is at the core of my program. It is used to retrieve and insert data to and from the database it is connected to.
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class DBConnections {

    /**
     * <h1>Get All Contacts</h1>
     * This method is used to retrieve all contacts from the database.
     * @return
     */
    public static ObservableList<Contact> get_all_contacts(){
        ObservableList<Contact> contacts_list = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contact_id = rs.getInt("Contact_ID");
                String contact_name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact contact = new Contact(contact_id, contact_name, email);
                contacts_list.add(contact);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return contacts_list;

    }

    /**
     * <h1>Add Customer</h1>
     * This method is used to add a customer to the database using the parameters.
     * @param id
     * @param name
     * @param address
     * @param postal_code
     * @param phone
     * @param create_date
     * @param created_by
     * @param last_update
     * @param last_updated_by
     * @param division_id
     */
    public static void add_customer(int id, String name, String address, String postal_code, String phone, String create_date, String created_by, String last_update, String last_updated_by, int division_id){
        try{
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES ('" + id + "', '" + name + "', '" + address + "', '" + postal_code + "', '" + phone + "', '" + create_date + "', '" + created_by + "', '" + last_update + "', '" + last_updated_by +"', '" + division_id + "')";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            int rs = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * <h1>Delete Customer</h1>
     * This method is used to delete a customer from the database using a customer ID.
     * @param customer_id
     */
    public static void delete_customer(int customer_id){
        try{
            String sql = "DELETE FROM customers WHERE Customer_ID='" + customer_id + "'";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            int rs = ps.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * <h1>Modify Customer</h1>
     * This method is used to first delete a customer using the given ID and then recreate that customer with the updated information.
     * @param customer_id
     * @param name
     * @param address
     * @param postal_code
     * @param phone
     * @param create_date
     * @param created_by
     * @param last_update
     * @param last_updated_by
     * @param division_id
     */
    public static void modify_customer(int customer_id, String name, String address, String postal_code, String phone, String create_date, String created_by, String last_update, String last_updated_by, int division_id){
        delete_customer(customer_id);
        try{
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES ('" + customer_id + "', '" + name + "', '" + address + "', '" + postal_code + "', '" + phone + "', '" + create_date + "', '" + created_by + "', '" + last_update + "', '" + last_updated_by +"', '" + division_id + "')";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            int rs = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <h1>Get All Divisions</h1>
     * This method is used to get all the divisions from the database.
     * @return
     */
    public static ObservableList<Division> get_all_divisions(){
        ObservableList<Division> division_list = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first_level_divisions";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int id = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                String create_date = rs.getString("Create_Date");
                String created_by = rs.getString("Created_By");
                String last_update = rs.getString("Last_Update");
                String last_updated_by = rs.getString("Last_Updated_By");
                int country_id = rs.getInt("Country_ID");

                Division division_obj = new Division(id, division, create_date, created_by, last_update, last_updated_by, country_id);
                division_list.add(division_obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return division_list;
    }


    /**
     * <h1>Get All Appointments</h1>
     * This method is used to get all appointments from the database.
     * It also converts the time that they are stored in (utc) to local time based on the zoneid that the user is login on.
     * @return
     */
    public static ObservableList<Appointments> get_all_appointments(){
        ObservableList<Appointments> appointment_list = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointment_id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String start = rs.getString("Start");
                String end = rs.getString("End");
                String create_date = rs.getString("Create_Date");
                String created_by = rs.getString("Created_By");
                String last_update = rs.getString("Last_Update");
                String last_updated_by = rs.getString("Last_Updated_By");
                int customer_id = rs.getInt("Customer_ID");
                int user_id = rs.getInt("User_ID");
                int contact_id = rs.getInt("Contact_ID");

                DateTimeFormatter ldt_format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                //Must convert the time from UTC to the local time
                LocalDateTime start_ldt = LocalDateTime.parse(start, ldt_format);
                LocalDateTime end_ldt = LocalDateTime.parse(end, ldt_format);
                LocalDateTime create_date_ldt = LocalDateTime.parse(create_date, ldt_format);
                LocalDateTime last_update_ldt = LocalDateTime.parse(last_update, ldt_format);
                //Convert ldt to zdt
                ZonedDateTime start_zdt_utc = ZonedDateTime.of(start_ldt, Login_Controller.utc_zone);
                ZonedDateTime end_zdt_utc = ZonedDateTime.of(end_ldt, Login_Controller.utc_zone);
                ZonedDateTime create_date_zdt_utc = ZonedDateTime.of(create_date_ldt, Login_Controller.utc_zone);
                ZonedDateTime last_update_zdt_utc = ZonedDateTime.of(last_update_ldt, Login_Controller.utc_zone);
                //Convert utc zdt to ldt zdt
                ZonedDateTime start_zdt_ldt = ZonedDateTime.ofInstant(start_zdt_utc.toInstant(), Login_Controller.zone);
                ZonedDateTime end_zdt_ldt = ZonedDateTime.ofInstant(end_zdt_utc.toInstant(), Login_Controller.zone);
                ZonedDateTime create_date_zdt_ldt = ZonedDateTime.ofInstant(create_date_zdt_utc.toInstant(), Login_Controller.zone);
                ZonedDateTime last_update_zdt_ldt = ZonedDateTime.ofInstant(last_update_zdt_utc.toInstant(), Login_Controller.zone);
                //Convert them to usable ldt string format
                String start_final = start_zdt_ldt.format(ldt_format);
                String end_final = end_zdt_ldt.format(ldt_format);
                String create_date_final = create_date_zdt_ldt.format(ldt_format);
                String last_update_final = last_update_zdt_ldt.format(ldt_format);


                Appointments appointment = new Appointments(appointment_id, title, description, location, contact_id, type, start_final, end_final, create_date_final, created_by, customer_id, user_id, last_update_final, last_updated_by);
                appointment_list.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointment_list;

    }

    /**
     * <h1>Add Appointment</h1>
     * This method is used to add an appointment to the database using the given parameters.
     * @param id
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param create_date
     * @param created_by
     * @param last_update
     * @param last_updated_by
     * @param customer_id
     * @param user_id
     * @param contact_id
     */
    public static void add_Appointment(int id, String title, String description, String location, String type, String start, String end, String create_date, String created_by, String last_update, String last_updated_by, int customer_id, int user_id, int contact_id){
        try{
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES ('" + id + "', '" + title + "', '" + description + "', '" + location + "', '" + type + "', '" +  start + "', '" + end +  "', '" + create_date + "', '" + created_by + "', '" + last_update + "', '" + last_updated_by +"', '" + customer_id +  "', '" + user_id + "', '" + contact_id + "')";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            int rs = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *<h1>Delete Appointment</h1>
     * This method deletes an appointment from the database based on the given id.
     * @param appointment_id
     */
    public static void delete_appointment(int appointment_id){
        try{
            String sql = "DELETE FROM appointments WHERE Appointment_ID='" + appointment_id + "'";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            int rs = ps.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * <h1>Modify Appointment</h1>
     * This method first deletes an appointment in the database based on the given id and then adds a new appointment with the updated information.
     * @param appointment_id
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param create_date
     * @param created_by
     * @param last_update
     * @param last_updated_by
     * @param customer_id
     * @param user_id
     * @param contact_id
     */
    public static void modify_appointment(int appointment_id, String title, String description, String location, String type, String start, String end, String create_date, String created_by, String last_update, String last_updated_by, int customer_id, int user_id, int contact_id){
        delete_appointment(appointment_id);
        try{
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES ('" + appointment_id + "', '" + title + "', '" + description + "', '" + location + "', '" + type + "', '" + start + "', '" + end + "', '" + create_date + "', '" + created_by +"', '" + last_update + "', '" + last_updated_by + "', '" + customer_id + "', '" + user_id + "', '" + contact_id + "')";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            int rs = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <h1>Get All Customers</h1>
     * This method is used to get all customers from the database.
     * @return
     */
    public static ObservableList<Customers> get_all_customers(){
        ObservableList<Customers> customer_list = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int customer_id = rs.getInt("Customer_ID");
                String customer_name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal_code = rs.getString("Postal_Code");
                String c_phone = rs.getString("Phone");
                String create_date = rs.getString("Create_Date");
                String created_by = rs.getString("Created_By");
                String last_update = rs.getString("Last_Update");
                String updated_by = rs.getString("Last_Updated_By");
                int division_id = rs.getInt("Division_ID");
                Customers customer = new Customers(customer_id, customer_name, address, postal_code, c_phone, create_date, created_by, last_update, updated_by, division_id);
                customer_list.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer_list;
    }

    /**
     * <h1>Get All Users</h1>
     * This method is used to get all users from the database.
     * @return
     */
    public static ObservableList<Users> get_all_users() {
        ObservableList<Users> user_list = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int country_id = rs.getInt("User_ID");
                String user_name = rs.getString("User_Name");
                String password = rs.getString("Password");
                String create_date = rs.getString("Create_Date");
                String created_by = rs.getString("Created_By");
                String last_updated = rs.getString("Last_Update");
                String update_by = rs.getString("Last_Updated_By");
                Users user = new Users(country_id, user_name, password, create_date, created_by, last_updated, update_by);
                user_list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user_list;
    }

}
