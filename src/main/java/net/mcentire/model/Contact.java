package net.mcentire.model;

/**
 * The type Contact.
 */
public class Contact extends Entity implements Named {

    private String name;
    private String email;

    /**
     * Instantiates a new Contact.
     *
     * @param id    the id
     * @param name  the name
     * @param email the email
     */
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
     * Sets name.
     *
     * @param name the new contact name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the contact email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
