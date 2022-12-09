package net.mcentire.repository;

import net.mcentire.model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRepository extends EntityRepository<Country> {

    private static final String tableName = "countries";
    private static final String tableIdentifier = "Country_ID";
    /**
     * Fields to be set on creation
     */
    private static final String[] entityCreatorFields = {
            "Country_ID"
    };
    /**
     * Fields to be set on update
     */
    private static final String[] entityUpdaterFields = {
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
     * Provides a method to create a Country from a result set
     * @param rs the ResultSet from the query
     * @return
     */
    @Override
    Country createEntityFromResultSet(ResultSet rs) {
        try {
            Country country = new Country(
                    rs.getInt("Country_ID"),
                    rs.getString("Country")
            );
            return country;
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
    QueryParameter[] getEntityCreatorQueryParams(Country entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName())
        };
        return params;
    }

    /**
     * Sets up which type of parameter to insert into the prepared statement
     * @param entity
     * @return
     */
    @Override
    QueryParameter[] getEntityUpdaterQueryParams(Country entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName())
        };
        return params;
    }
}
