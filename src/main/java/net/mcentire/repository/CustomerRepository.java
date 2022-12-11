package net.mcentire.repository;

import net.mcentire.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles data operations for Customers
 */
public class CustomerRepository extends EntityRepository<Customer> {

    private static final String tableName = "customers";
    private static final String tableIdentifier = "Customer_ID";
    /**
     * Fields to be set on creation
     */
    private static final String[] entityCreatorFields = {
            "Customer_Name",
            "Address",
            "Postal_Code",
            "Phone",
            "Division_ID"
    };
    /**
     * Fields to be set on update
     */
    private static final String[] entityUpdaterFields = {
            "Customer_Name",
            "Address",
            "Postal_Code",
            "Phone",
            "Division_ID"
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
     * Provides a method to create a Customer from a result set
     * @param rs the ResultSet from the query
     * @return
     */
    @Override
    Customer createEntityFromResultSet(ResultSet rs) {
        try {
            Customer customer = new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getInt("Division_ID")
            );
            return customer;
        } catch (SQLException e)
        {
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
    QueryParameter[] getEntityCreatorQueryParams(Customer entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName()),
                new QueryParameter(entity.getAddress()),
                new QueryParameter(entity.getPostalCode()),
                new QueryParameter(entity.getPhoneNumber()),
                new QueryParameter(entity.getDivisionId()),
        };
        return params;
    }

    /**
     * Sets up which type of parameter to insert into the prepared statement
     * @param entity
     * @return
     */
    @Override
    QueryParameter[] getEntityUpdaterQueryParams(Customer entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName()),
                new QueryParameter(entity.getAddress()),
                new QueryParameter(entity.getPostalCode()),
                new QueryParameter(entity.getPhoneNumber()),
                new QueryParameter(entity.getDivisionId()),
        };
        return params;
    }
}
