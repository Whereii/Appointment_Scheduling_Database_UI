package sample;

import java.time.LocalDateTime;

/**
 * <h1>Divisions</h1>
 * <h2>Type</h2>
 * Constructor
 * This class is used to take information from the database and convert it into a useful piece of information.
 *
 * @author Joshua McCausey
 * @version 1.0
 * @since 2023-05-03
 */
public class Division {

    private int division_id;
    private String division;
    private String create_date;
    private String created_by;
    private String last_update;
    private String last_updated_by;
    private int country_id;

    /**
     * This is a basic constructor for the division class.
     * @param division_id
     * @param division
     * @param create_date
     * @param created_by
     * @param last_update
     * @param last_updated_by
     * @param country_id
     */
    public Division(int division_id, String division, String create_date, String created_by, String last_update, String last_updated_by, int country_id) {
        this.division_id = division_id;
        this.division = division;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.country_id = country_id;
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
     * <h1>Set Division ID</h1>
     * This is a setter method.
     * @return
     */
    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }

    /**
     * <h1>Get Division</h1>
     * This is a getter method.
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * <h1>Set Division</h1>
     * This is a setter method.
     * @return
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * <h1>Get Create Date</h1>
     * This is a getter method.
     * @return
     */
    public String getCreate_date() {
        return create_date;
    }

    /**
     * <h1>Set Create Date</h1>
     * This is a setter method.
     * @return
     */
    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    /**
     * <h1>Get Created By</h1>
     * This is a getter method.
     * @return
     */
    public String getCreated_by() {
        return created_by;
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
     * <h1>Get Last Update</h1>
     * This is a getter method.
     * @return
     */
    public String getLast_update() {
        return last_update;
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
     * <h1>Get Last Updated By</h1>
     * This is a getter method.
     * @return
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * <h1>Set Last Updated By</h1>
     * This is a setter method.
     * @return
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    /**
     * <h1>Get Country ID</h1>
     * This is a getter method.
     * @return
     */
    public int getCountry_id() {
        return country_id;
    }

    /**
     * <h1>Set Country ID</h1>
     * This is a setter method.
     * @return
     */
    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

}
