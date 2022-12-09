package net.mcentire.model;

/**
 * The type Customer.
 */
public class Customer extends Entity implements Named {

    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;

    /**
     * Instantiates a new Customer.
     *
     * @param name        the name
     * @param address     the address
     * @param postalCode  the postal code
     * @param phoneNumber the phone number
     * @param divisionId  the division id
     */
    public Customer(String name, String address, String postalCode, String phoneNumber, int divisionId) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id          the id
     * @param name        the name
     * @param address     the address
     * @param postalCode  the postal code
     * @param phoneNumber the phone number
     * @param divisionId  the division id
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    /**
     * Sets id.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     *
     * @return the Customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the new Customer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets address.
     *
     * @return the Customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the new Customer address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets postal code.
     *
     * @return the Customer postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the new Customer postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets phone number.
     *
     * @return the Customer phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the new Customer phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets division id.
     *
     * @return the id of the Division the Customer is associated with
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets division id.
     *
     * @param divisionId the id of the new Division the Customer is associated with
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
