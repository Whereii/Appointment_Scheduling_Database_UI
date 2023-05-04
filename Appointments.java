package sample;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

/**
 * <h1>Appointments</h1>
 * <h2>Type</h2>
 *  Constructor
 *
 *  This class is used to take the information from the database and convert it into usable objects. In this case it is an appointment object.
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Appointments {

    private int appointment_id;
    private String title;
    private String description;
    private String location;
    private int contact;
    private String type;
    private String start_time;
    private String end_time;
    private String start_date;
    private String end_date;
    private int customer_id;
    private int user_id;
    private String create_date;
    private String start_whole;
    private String end_whole;
    private String created_by;
    private String last_update;
    private String last_update_by;

    /**
     * This method is simply a constructor for the applications class.
     * It does something somewhat unique in which it formats the time received into a couple different fields for easier use.
     * @param appointment_id
     * @param title
     * @param description
     * @param location
     * @param contact
     * @param type
     * @param start_time
     * @param end_time
     * @param create_date
     * @param created_by
     * @param customer_id
     * @param user_id
     * @param last_update
     * @param last_update_by
     */
    public Appointments(int appointment_id, String title, String description, String location, int contact, String type, String start_time, String end_time, String create_date, String created_by, int customer_id, int user_id, String last_update, String last_update_by){
        this.appointment_id = appointment_id;
        this.title = title;
        this.description = description;
        this.location = location;
        this. contact = contact;
        this.type = type;
        this.create_date = create_date;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_update_by = last_update_by;

        //Needed a way to store the whole localdatetime format for later use when modifying the appointment
        //That is what start_whole and end_whole are for. Used in Modify_customer_Controller
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(start_time, format);
        LocalDateTime end = LocalDateTime.parse(end_time, format);
        this.start_whole = String.valueOf(start);
        this.end_whole = String.valueOf(end);

        //Convert time into usable numbers
        DateTimeFormatter hour_format = DateTimeFormatter.ofPattern("HH:mm");
        String start_hour_time = start.format(hour_format);
        String end_hour_time = end.format(hour_format);
        this.start_time = start_hour_time;
        this.end_time = end_hour_time;
        //Convert time into usable numbers
        DateTimeFormatter year_format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String start_date = start.format(year_format);
        String end_date = end.format(year_format);
        this.start_date = start_date;
        this.end_date = end_date;

    }

    /**
     * <h1>Get Appointment ID</h1>
     * This is a getter
     * @return
     */
    public int getAppointment_id() {
        return appointment_id;
    }

    /**
     * <h1>Get Title</h1>
     * This is a getter
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * <h1>Get Description</h1>
     * This is a getter
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * <h1>Get Location</h1>
     * This is a getter
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * <h1>Get Contact</h1>
     * This is a getter
     * @return
     */
    public int getContact() {
        return contact;
    }

    /**
     * <h1>Get Type</h1>
     * This is a getter
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * <h1>Get Start Time</h1>
     * This is a getter
     * @return
     */
    public String getStart_time() {
        return start_time;
    }

    /**
     * <h1>Get End Time</h1>
     * This is a getter
     * @return
     */
    public String getEnd_time() {
        return end_time;
    }

    /**
     * <h1>Get Start Date</h1>
     * This is a getter
     * @return
     */
    public String getStart_date() {
        return start_date;
    }

    /**
     * <h1>Get End Date</h1>
     * This is a getter
     * @return
     */
    public String getEnd_date() {
        return end_date;
    }

    /**
     * <h1>Get Customer ID</h1>
     * This is a getter
     * @return
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * <h1>Get User ID</h1>
     * This is a getter
     * @return
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * <h1>Set Appointment ID</h1>
     * This is a setter
     * @return
     */
    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    /**
     * <h1>Set Title</h1>
     * This is a setter
     * @return
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <h1>Set Description</h1>
     * This is a setter
     * @return
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <h1>Set Location</h1>
     * This is a setter
     * @return
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * <h1>Set Contact</h1>
     * This is a setter
     * @return
     */
    public void setContact(int contact) {
        this.contact = contact;
    }

    /**
     * <h1>Set Type</h1>
     * This is a setter
     * @return
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <h1>Set Start Time</h1>
     * This is a setter
     * @return
     */
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    /**
     * <h1>Set End Time</h1>
     * This is a setter
     * @return
     */
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    /**
     * <h1>Set Start Date</h1>
     * This is a setter
     * @return
     */
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    /**
     * <h1>Set End Date</h1>
     * This is a setter
     * @return
     */
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    /**
     * <h1>Set Customer ID</h1>
     * This is a setter
     * @return
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * <h1>Set User ID</h1>
     * This is a setter
     * @return
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * <h1>Get Start Whole</h1>
     * This is a getter
     * @return
     */
    public String getStart_whole() {
        return start_whole;
    }

    /**
     * <h1>Set Start Whole</h1>
     * This is a setter
     * @return
     */
    public void setStart_whole(String start) {
        this.start_whole = start;
    }

    /**
     * <h1>Get End Whole</h1>
     * This is a getter
     * @return
     */
    public String getEnd_whole() {
        return end_whole;
    }

    /**
     * <h1>Set End Whole</h1>
     * This is a setter
     * @return
     */
    public void setEnd_whole(String end) {
        this.end_whole = end;
    }

    /**
     * <h1>Get Create Date</h1>
     * This is a getter
     * @return
     */
    public String getCreate_date() {
        return create_date;
    }

    /**
     * <h1>Set Create Date</h1>
     * This is a setter
     * @return
     */
    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    /**
     * <h1>Get Created By</h1>
     * This is a getter
     * @return
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * <h1>Set Created By</h1>
     * This is a setter
     * @return
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * <h1>Get Last Update</h1>
     * This is a getter
     * @return
     */
    public String getLast_update() {
        return last_update;
    }

    /**
     * <h1>Set Last Update</h1>
     * This is a setter
     * @return
     */
    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    /**
     * <h1>Get Last Update By</h1>
     * This is a getter
     * @return
     */
    public String getLast_update_by() {
        return last_update_by;
    }

    /**
     * <h1>Set Last Update By</h1>
     * This is a setter
     * @return
     */
    public void setLast_update_by(String last_update_by) {
        this.last_update_by = last_update_by;
    }
}
