package net.mcentire.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
