package net.mcentire.repository;

import net.mcentire.model.Customer;
import net.mcentire.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends EntityRepository<User> {

    private static final String tableName = "users";
    private static final String tableIdentifier = "User_ID";
    private static final String[] entityCreatorFields = {
            "User_Name",
            "Password"
    };
    private static final String[] entityUpdaterFields = {
            "User_Name",
            "Password"
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
    User createEntityFromResultSet(ResultSet rs) throws SQLException {
        User user = new User(
                rs.getInt("User_ID"),
                rs.getString("User_Name"),
                rs.getString("Password")
        );
        return user;
    }

    @Override
    QueryParameter[] getEntityCreatorQueryParams(User entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName()),
                new QueryParameter(entity.getPassword())
        };
        return params;
    }

    @Override
    QueryParameter[] getEntityUpdaterQueryParams(User entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName()),
                new QueryParameter(entity.getPassword())
        };
        return params;
    }
}
