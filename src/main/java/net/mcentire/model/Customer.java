package net.mcentire.model;

public class Customer extends Entity {
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;

    public Customer(String name, String address, String postalCode, String phoneNumber, int divisionId) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    public Customer(int id, String name, String address, String postalCode, String phoneNumber, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }


    /**
     *
     * @return the Customer name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the new Customer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the Customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address the new Customer address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return the Customer postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode the new Customer postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return the Customer phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber the new Customer phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return the id of the Division the Customer is associated with
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     *
     * @param divisionId the id of the new Division the Customer is associated with
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
