package sample;

/**
 * <h1>Customers</h1>
 * <h2>Type</h2>
 * Constructor
 * This class is used to take in the customer information from the database and then convert it into something usable such as a customer object
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Customers {

    private int customer_id;
    private String customer_name;
    private String address;
    private String postal_code;
    private String phone_number;
    private String created_date;
    private String created_by;
    private String last_update;
    private String last_update_by;
    private int division_id;


    /**
     * This is a basic constructor for the customers class.
     * @param id
     * @param name
     * @param address
     * @param postal_code
     * @param phone_number
     * @param created_date
     * @param created_by
     * @param last_update
     * @param last_update_by
     * @param division_id
     */
    public Customers(int id, String name, String address, String postal_code, String phone_number, String created_date, String created_by, String last_update, String last_update_by, int division_id){
        this.customer_id = id;
        this.customer_name = name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone_number = phone_number;
        this.created_date = created_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_update_by = last_update_by;
        this.division_id = division_id;
    }

    /**
     * <h1>Get Customer ID</h1>
     * This is a getter method.
     * @return
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * <h1>Get Customer Name</h1>
     * This is a getter method.
     * @return
     */
    public String getCustomer_name() {
        return customer_name;
    }

    /**
     * <h1>Get Address</h1>
     * This is a getter method.
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * <h1>Get Postal Code</h1>
     * This is a getter method.
     * @return
     */
    public String getPostal_code() {
        return postal_code;
    }

    /**
     * <h1>Get Phone Number</h1>
     * This is a getter method.
     * @return
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * <h1>Get Created Date</h1>
     * This is a getter method.
     * @return
     */
    public String getCreated_date() {
        return created_date;
    }

    /**
     * <h1>Get Created bY</h1>
     * This is a getter method.
     * @return
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * <h1>Get Last Update</h1>
     * This is a getter method.
     * @return
     */
    public String getLast_update() {
        return last_update;
    }

    /**
     * <h1>Get Last Update By</h1>
     * This is a getter method.
     * @return
     */
    public String getLast_update_by() {
        return last_update_by;
    }

    /**
     * <h1>Get Division ID</h1>
     * This is a getter method.
     * @return
     */
    public int getDivision_id() {
        return division_id;
    }

    /**
     * <h1>Set Customer ID</h1>
     * This is a setter method.
     * @return
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * <h1>Set Customer Name</h1>
     * This is a setter method.
     * @return
     */
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    /**
     * <h1>Set Address</h1>
     * This is a setter method.
     * @return
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * <h1>Set Postal Code</h1>
     * This is a setter method.
     * @return
     */
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * <h1>Set Phone Number</h1>
     * This is a setter method.
     * @return
     */
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    /**
     * <h1>Set Created Date</h1>
     * This is a setter method.
     * @return
     */
    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    /**
     * <h1>Set Created By</h1>
     * This is a setter method.
     * @return
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * <h1>Set Last Update</h1>
     * This is a setter method.
     * @return
     */
    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    /**
     * <h1>Set Last Update By</h1>
     * This is a setter method.
     * @return
     */
    public void setLast_update_by(String last_update_by) {
        this.last_update_by = last_update_by;
    }

    /**
     * <h1>Set Division ID</h1>
     * This is a setter method.
     * @return
     */
    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }
}
