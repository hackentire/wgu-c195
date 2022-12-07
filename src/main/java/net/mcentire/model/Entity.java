package net.mcentire.model;

//import java.time.ZonedDateTime;

public abstract class Entity implements Identifiable {
    protected int id;
    // FUTURE ENHANCEMENT: Implement auditing contexts
//    protected ZonedDateTime createdOn;
//    protected String createdBy;
//    protected ZonedDateTime updatedOn;
//    protected String updatedBy;

    /**
     * @return the entity ID
     */
    public int getId() {
        return id;
    }
}
