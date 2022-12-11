package net.mcentire.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.mcentire.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles data operations for Users
 */
public class UserRepository extends EntityRepository<User> {

    private static final String tableName = "users";
    private static final String tableIdentifier = "User_ID";
    /**
     * Fields to be set on creation
     */
    private static final String[] entityCreatorFields = {
            "User_Name",
            "Password"
    };
    /**
     * Fields to be set on update
     */
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

    /**
     * Provides a method to create a User from a result set
     * @param rs the ResultSet from the query
     * @return
     */
    @Override
    User createEntityFromResultSet(ResultSet rs) {
        try {
            User user = new User(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password")
            );
            return user;
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
    QueryParameter[] getEntityCreatorQueryParams(User entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName()),
                new QueryParameter(entity.getPassword())
        };
        return params;
    }

    /**
     * Sets up which type of parameter to insert into the prepared statement
     * @param entity
     * @return
     */
    @Override
    QueryParameter[] getEntityUpdaterQueryParams(User entity) {
        QueryParameter[] params = new QueryParameter[]{
                new QueryParameter(entity.getName()),
                new QueryParameter(entity.getPassword())
        };
        return params;
    }

    /**
     * @param username
     * @param password
     * @return the User if login was successful
     */
    public static User login(String username, String password) {
        String sql = "SELECT * FROM " + tableName + " WHERE User_Name = ? AND Password = ?";
        try {
            Query.executeQuery(sql, new QueryParameter(username), new QueryParameter(password));

            ResultSet rs = Query.getResult();
            if (rs.next()) {
                User activeUser = new User(
                        rs.getInt("User_ID"),
                        rs.getString("User_Name"),
                        rs.getString("Password"));

                return activeUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Overrides the parent method to conceal the user password
     * @return a list of Users with truncated passwords
     */
    @Override
    public ObservableList<User> getAll() {
        try {
            ObservableList<User> users = FXCollections.observableArrayList();

            String sql = String.format("SELECT User_ID, User_Name FROM %s", getTableName());
            Query.executeQuery(sql);
            ResultSet rs = Query.getResult();

            while (rs.next())
                users.add(
                        new User(
                                rs.getInt("User_ID"),
                                rs.getString("User_Name"),
                                "" // Hide password from inspection
                        )
                );

            return users;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
