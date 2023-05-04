package sample;

/**
 * <h1>Contact</h1>
 * <h2>Type</h2>
 * Constructor
 * This class is used to take in the contact data from the database and then store it in a usable format such as individual contacts
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Contact {

    private int contact_id;
    private String contact_name;
    private String contact_email;

    /**
     * This is a simple consturctor method for the contacts class
     * @param contact_id
     * @param contact_name
     * @param contact_email
     */
    public Contact(int contact_id, String contact_name, String contact_email) {
        this.contact_id = contact_id;
        this.contact_name = contact_name;
        this.contact_email = contact_email;
    }

    /**
     * <h1>Get Contact ID</h1>
     * This is a getter method.
     * @return
     */
    public int getContact_id() {
        return contact_id;
    }

    /**
     * <h1>Set Contact ID</h1>
     * This is a setter method.
     * @return
     */
    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    /**
     * <h1>Get Contact Name</h1>
     * This is a getter method.
     * @return
     */
    public String getContact_name() {
        return contact_name;
    }

    /**
     * <h1>Set Contact Name</h1>
     * This is a setter method.
     * @return
     */
    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    /**
     * <h1>Get Contact Email</h1>
     * This is a getter method.
     * @return
     */
    public String getContact_email() {
        return contact_email;
    }

    /**
     * <h1>Set Contact Email</h1>
     * This is a setter method
     * @param contact_email
     */
    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

}
