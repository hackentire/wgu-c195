package net.mcentire.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.mcentire.app.App;
import net.mcentire.model.Appointment;
import net.mcentire.util.Time;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentRepository extends EntityRepository<Appointment> {

    private static final String tableName = "appointments";
    private static final String tableIdentifier = "Appointment_ID";
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
