package net.mcentire.model;

/**
 * The type User
 */
public class User extends Entity implements Named {
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    /**
     *
     * @return the name of the User
     */
    public String getName() { return name; }

    /**
     *
     * @param name the new name of the User
     */
    public void setName(String name) { this.name = name; }

    /**
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password the new password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
