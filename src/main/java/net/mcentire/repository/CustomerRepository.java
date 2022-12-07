package net.mcentire.repository;

import net.mcentire.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository extends EntityRepository<Customer> {

    private static final String tableName = "customers";
    private static final String tableIdentifier = "Customer_ID";
    private static final String[] entityCreatorFields = {
            "Customer_Name",
            "Address",
            "Postal_Code",
            "Phone",
            "Division_ID"
    };
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

    @Override
    Customer createEntityFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer(
                rs.getInt("Customer_ID"),
                rs.getString("Customer_Name"),
                rs.getString("Address"),
                rs.getString("Postal_Code"),
                rs.getString("Phone"),
                rs.getInt("Division_ID")
        );
        return customer;
    }

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
