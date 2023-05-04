package sample;

/**
 * <h1>Users</h1>
 * <h2>Type</h2>
 * Constructor
 * This class is used to take in data from the database and then conver it into something usable. In this case a user object.
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Users {

    private final int id;
    private final String name;
    private final String password;
    private final String create_date;
    private final String created_by;
    private final String last_updated;
    private final String last_updated_by;

    /**
     * This method is a simple constructor for the users class.
     * @param id
     * @param name
     * @param password
     * @param create_date
     * @param created_by
     * @param last_updated
     * @param last_updated_by
     */
    public Users(int id, String name, String password, String create_date, String created_by, String last_updated, String last_updated_by) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_updated = last_updated;
        this.last_updated_by = last_updated_by;
    }

    /**
     * <h1>Get ID</h1>
     * This method is a getter.
     * @return
     */
    public int get_id(){
        return this.id;
    }


    /**
     * <h1>Get Name</h1>
     * This method is a getter.
     * @return
     */
    public String get_name(){
        return this.name;
    }

    /**
     * <h1>Get Password</h1>
     * This method is a getter.
     * @return
     */
    public String get_password(){
        return this.password;
    }

    /**
     * <h1>Get Create Date</h1>
     * This method is a getter.
     * @return
     */
    public String get_create_date(){
        return create_date;
    }

    /**
     * <h1>Get Created By</h1>
     * This method is a getter.
     * @return
     */
    public String get_created_by(){
        return created_by;
    }

    /**
     * <h1>Get Last Updated</h1>
     * This method is a getter.
     * @return
     */
    public String get_last_updated(){
        return last_updated;
    }

    /**
     * <h1>Get Updated By</h1>
     * This method is a getter.
     * @return
     */
    public String get_updated_by(){
        return last_updated_by;
    }
}
