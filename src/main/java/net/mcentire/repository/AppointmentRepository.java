package net.mcentire.repository;

import net.mcentire.model.Appointment;
import net.mcentire.util.TimeConverter;

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
    Appointment createEntityFromResultSet(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment(
                rs.getInt("Appointment_ID"),
                rs.getString("Title"),
                rs.getString("Description"),
                rs.getString("Location"),
                rs.getString("Type"),
                TimeConverter.toLocalZoneDateTime(rs.getTimestamp("Start")),
                TimeConverter.toLocalZoneDateTime(rs.getTimestamp("End")),
                rs.getInt("Customer_ID"),
                rs.getInt("User_ID"),
                rs.getInt("Contact_ID")
        );
        return appointment;
    }

    @Override
    QueryParameter[] getEntityCreatorQueryParams(Appointment entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getTitle()),
                new QueryParameter(entity.getDescription()),
                new QueryParameter(entity.getLocation()),
                new QueryParameter(entity.getType()),
                new QueryParameter(TimeConverter.toUtcTimestamp(entity.getStart())),
                new QueryParameter(TimeConverter.toUtcTimestamp(entity.getEnd())),
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
                new QueryParameter(TimeConverter.toUtcTimestamp(entity.getStart())),
                new QueryParameter(TimeConverter.toUtcTimestamp(entity.getEnd())),
                new QueryParameter(entity.getCustomerId()),
                new QueryParameter(entity.getUserId()),
                new QueryParameter(entity.getContactId())
        };
        return params;
    }
}
