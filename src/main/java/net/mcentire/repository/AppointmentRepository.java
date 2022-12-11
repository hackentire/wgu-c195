package net.mcentire.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.mcentire.app.App;
import net.mcentire.model.Appointment;
import net.mcentire.util.Time;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles data operations for Appointments
 */
public class AppointmentRepository extends EntityRepository<Appointment> {

    /**
     * The constant tableName.
     */
    private static final String tableName = "appointments";
    /**
     * The constant tableIdentifier.
     */
    private static final String tableIdentifier = "Appointment_ID";
    /**
     * Fields to be set on creation
     */
    private static final String[] entityCreatorFields = {
            "Title",
            "Description",
            "Location",
            "Type",
            "Start",
            "End",
            "Customer_ID",
            "User_ID",
            "Contact_ID"
    };
    /**
     * Fields to be set on update
     */
    private static final String[] entityUpdaterFields = {
            "Title",
            "Description",
            "Location",
            "Type",
            "Start",
            "End",
            "Customer_ID",
            "User_ID",
            "Contact_ID"
    };

    @Override
    String getTableName() {
        return tableName;
    }

    @Override
    String getTableIdentifier() {
        return tableIdentifier;
    }

    @Override
    String[] getEntityCreatorFields() {
        return entityCreatorFields;
    }

    @Override
    String[] getEntityUpdaterFields() {
        return entityUpdaterFields;
    }

    /**
     * Provides a method to create an Appointment from a result set
     * @param rs the ResultSet from the query
     * @return
     */
    @Override
    Appointment createEntityFromResultSet(ResultSet rs) {
        try {
            Appointment appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    Time.toLocalDateTime(rs.getTimestamp("Start")),
                    Time.toLocalDateTime(rs.getTimestamp("End")),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            return appointment;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sets up which type of parameter to insert into the prepared statement
     * @param entity
     * @return
     */
    @Override
    QueryParameter[] getEntityCreatorQueryParams(Appointment entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getTitle()),
                new QueryParameter(entity.getDescription()),
                new QueryParameter(entity.getLocation()),
                new QueryParameter(entity.getType()),
                new QueryParameter(Time.toTimestamp(entity.getStart())),
                new QueryParameter(Time.toTimestamp(entity.getEnd())),
                new QueryParameter(entity.getCustomerId()),
                new QueryParameter(entity.getUserId()),
                new QueryParameter(entity.getContactId())
        };
        return params;
    }

    /**
     * Sets up which type of parameter to insert into the prepared statement
     * @param entity
     * @return
     */
    @Override
    QueryParameter[] getEntityUpdaterQueryParams(Appointment entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getTitle()),
                new QueryParameter(entity.getDescription()),
                new QueryParameter(entity.getLocation()),
                new QueryParameter(entity.getType()),
                new QueryParameter(Time.toTimestamp(entity.getStart())),
                new QueryParameter(Time.toTimestamp(entity.getEnd())),
                new QueryParameter(entity.getCustomerId()),
                new QueryParameter(entity.getUserId()),
                new QueryParameter(entity.getContactId())
        };
        return params;
    }

    /**
     * Get a list of appointments related to a given Customer ID
     *
     * @param id the customer id
     * @return customer appointments
     */
    public ObservableList<Appointment> getCustomerAppointments(int id) {
        ObservableList<Appointment> results = FXCollections.observableArrayList();

        try {
            String sql = String.format("SELECT * FROM %s WHERE Customer_ID = %d", getTableName(), id);
            Query.executeQuery(sql);
            ResultSet rs = Query.getResult();

            while (rs.next())
                results.add(createEntityFromResultSet(rs));

            return results;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return results;
    }
}
