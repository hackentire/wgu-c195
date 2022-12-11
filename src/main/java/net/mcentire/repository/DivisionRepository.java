package net.mcentire.repository;

import javafx.collections.ObservableList;
import net.mcentire.model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles data operations for Divisions
 */
public class DivisionRepository extends EntityRepository<Division> {
    private static final String tableName = "first_level_divisions";
    private static final String tableIdentifier = "Division_ID";
    /**
     * Fields to be set on creation
     */
    private static final String[] entityCreatorFields = {
            "Division",
            "Country_ID"
    };
    /**
     * Fields to be set on update
     */
    private static final String[] entityUpdaterFields = {
            "Division",
            "Country_ID"
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
     * Provides a method to create a Division from a result set
     * @param rs the ResultSet from the query
     * @return
     */
    @Override
    Division createEntityFromResultSet(ResultSet rs) {
        try {
            Division division = new Division(
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID")
            );
            return division;
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
    QueryParameter[] getEntityCreatorQueryParams(Division entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName()),
                new QueryParameter(entity.getCountryId())
        };
        return params;
    }

    /**
     * Sets up which type of parameter to insert into the prepared statement
     * @param entity
     * @return
     */
    @Override
    QueryParameter[] getEntityUpdaterQueryParams(Division entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName()),
                new QueryParameter(entity.getCountryId())
        };
        return params;
    }
}
