package net.mcentire.model;

/**
 * The type Country.
 */
public class Country extends Entity {
    private String name;

    /**
     * Instantiates a new Country.
     *
     * @param id   the id
     * @param name the name
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name of the Country
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the new name of the Country
     */
    public void setName(String name) { this.name = name; }
}
