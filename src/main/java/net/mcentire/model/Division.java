package net.mcentire.model;

public class Division extends Entity {
    private String name;
    private int countryId;

    public Division(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    public Division(String name, int countryId) {
        this.name = name;
        this.countryId = countryId;
    }

    /**
     *
     * @return the Division name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the related Country
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     *
     * @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param id the id of the Country to be associated
     */
    public void setCountryId(int id) {
        this.countryId = id;
    }
}
