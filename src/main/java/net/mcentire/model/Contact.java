package net.mcentire.model;

public class Contact extends Entity implements Named {
    private String name;
    private String email;

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * @return more apparent string representation for testing purposes
     */
    @Override
    public String toString() {
        return id + ": " + name + " - " + email;
    }

    /**
     * @return the contact name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the new contact name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the contact email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
