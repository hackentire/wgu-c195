package net.mcentire.model;

public class Country extends Entity {
    private String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return the name of the Country
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the new name of the Country
     */
    public void setName(String name) { this.name = name; }
}
