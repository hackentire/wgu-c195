package net.mcentire.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.mcentire.model.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

/**
 * Base repository that provides generic CRUD for type T after Overriding certain methods as a way to set up DB schema information
 * and handle entity updates.
 * @param <T> an entity Class having a unique ID
 */
public abstract class EntityRepository<T extends Identifiable> extends Repository {
    /**
     * @return the name of the entity's table
     */
    abstract String getTableName(); // e.g. entities - likely lowercase and plural

    /**
     * @return the name of the entity's ID column
     */
    abstract String getTableIdentifier(); // e.g. Entity_ID - likely Proper case

    /**
     * @return an array of table fields set during entity creation
     */
    abstract String[] getEntityCreatorFields(); // e.g. Name, Description

    /**
     * @return an array of table fields settable during entity update
     */
    abstract String[] getEntityUpdaterFields(); // e.g. Name, Description, Date

    /**
     * @param rs the ResultSet from the query
     * @return an instance of the Entity created from data of the ResultSet.
     * IMPORTANT: This should assign an ID to the entity Instance
     */
    abstract T createEntityFromResultSet(ResultSet rs);

    /**
     * @param entity
     * @return an array of QueryParameters used to set parameters in the PreparedStatement for creating the entity
     */
    abstract QueryParameter[] getEntityCreatorQueryParams(T entity);

    /**
     * @param entity
     * @return an array of QueryParameters used to set parameters in the PreparedStatement for updating the entity
     */
    abstract QueryParameter[] getEntityUpdaterQueryParams(T entity);

    /**
     *
     * @param id the ID of the entity to be searched
     * @return an Entity T matching the provided ID
     */
    public T get(int id) {
        try {
            T entity = null;

            String sql = String.format("SELECT * FROM %s WHERE %s = %d", getTableName(), getTableIdentifier(), id);
            Query.executeQuery(sql);
            ResultSet rs = Query.getResult();

            if (rs.next())
                entity = createEntityFromResultSet(rs);

            return entity;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return an ObservableList of all entity records
     */
    public ObservableList<T> getAll() {
        try {
            ObservableList<T> entities = FXCollections.observableArrayList();

            String sql = String.format("SELECT * FROM %s", getTableName());
            Query.executeQuery(sql);
            ResultSet rs = Query.getResult();

            while (rs.next())
                entities.add(createEntityFromResultSet(rs));

            return entities;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param entity the entity to be inserted
     * @return the ID of the created entity (returns -1 on failure)
     */
    public int create(T entity) {
        // RUNTIME_ERROR: Fixed poorly generated string for the prepared statement (no comma between ? marks)
        try {
            String sql = String.format("INSERT INTO %s (%s) values (%s)", getTableName(),
                    String.join(", ", getEntityCreatorFields()),
//                String.join(",", "?".repeat(getCreateEntityFields().length))
                    // Build a string of placeholders for the prepared statement
                    String.join(", ", "?".repeat(getEntityCreatorFields().length).split("(?!^)"))
            );
            QueryParameter[] params = getEntityCreatorQueryParams(entity);
            Query.executeQuery(sql, true, params);

            if (Query.getRowsAffected() > 0 && Query.getResult().next())
            {
                // Return the generated key
                return Query.getResult().getInt(1);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     *
     * @param entity the updated entity
     * @return true if update was successful
     */
    public boolean update(T entity) {
        // Build fields string - e.g. "Field1 = ?, Field2 = ?"
        StringJoiner fields = new StringJoiner(",");
        for (String field : getEntityUpdaterFields())
            fields.add(field + " = ?");
        String sql = String.format("UPDATE %s SET %s WHERE %s = %d", getTableName(), fields.toString(), getTableIdentifier(), entity.getId());
        QueryParameter[] params = getEntityUpdaterQueryParams(entity);
        Query.executeQuery(sql, params);

        return Query.getRowsAffected() > 0;
    }

    /**
     *
     * @param id the ID of the entity to be deleted
     * @return true if removal was successful
     */
    public boolean delete(int id) {
        String sql = String.format("DELETE FROM %s WHERE %s = %d", getTableName(), getTableIdentifier(), id);
        Query.executeQuery(sql);

        return Query.getRowsAffected() > 0;
    }
}
