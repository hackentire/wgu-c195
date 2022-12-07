package net.mcentire.repository;

import net.mcentire.database.JDBC;

import java.sql.*;

public abstract class Repository {
    protected static Connection connection = JDBC.getConnection();

    /**
     * Data used to set a PreparedStatement's parameters
     */
    public static class QueryParameter {
        private QueryParameterType type;
        private Object value;

        public QueryParameterType getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }

        public QueryParameter(int value) {
            this.type = QueryParameterType.INT;
            this.value = value;
        }

        public QueryParameter(String value) {
            this.type = QueryParameterType.STRING;
            this.value = value;
        }

        public QueryParameter(Timestamp value) {
            this.type = QueryParameterType.TIMESTAMP;
            this.value = value;
        }
    }

    /**
     * Supported query parameter types
     */
    public enum QueryParameterType {
        INT,
        STRING,
        TIMESTAMP
    }

    /**
     * Primary interface for executing queries and receiving data from the DB connection
     */
    public class Query {
        private static String query;
        private static PreparedStatement statement;
        private static ResultSet result;
        private static int rowsAffected;

        /**
         * Generates and primes a PreparedStatement and handles its execution
         * @param sql a String with optional placeholders ("?") for parameter insertion
         * @param returnGeneratedKey whether the function will place the generated ID (in the case of insertions) in the ResultSet result
         * @param params Optional parameters to be inserted into the PreparedStatement
         * @throws SQLException
         */
        public static void executeQuery(String sql, boolean returnGeneratedKey, QueryParameter... params) throws SQLException {
            query = sql;
            statement = returnGeneratedKey ?
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) :
                    connection.prepareStatement(sql);

            // Set statement parameters and indices
            int count = 0;
            for (QueryParameter param : params) {
                switch (param.type)
                {
                    case INT: {
                        statement.setInt(++count, (int) param.value);
                        break;
                    }
                    case STRING: {
                        statement.setString(++count, (String) param.value);
                        break;
                    }
                    case TIMESTAMP: {
                        statement.setTimestamp(++count, (Timestamp) param.value);
                        break;
                    }
                    default: ++count;
                }
            }

            try {
                // Determine query execution
                if (query.toLowerCase().startsWith("select")) {
                    result = statement.executeQuery();
                    rowsAffected = 0;
                }
                if (query.toLowerCase().startsWith("insert")) {
                    rowsAffected = statement.executeUpdate();
                    result = statement.getGeneratedKeys();
                }
                if (query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("update")) {
                    result = null;
                    rowsAffected = statement.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void executeQuery(String sql, QueryParameter... params) throws SQLException {
            executeQuery(sql, false, params);
        }

        /**
         * @return the result of the last query
         */
        public static ResultSet getResult() {
            return result;
        }

        /**
         * @return the number of rows affected by the last query
         */
        public static int getRowsAffected() {
            return rowsAffected;
        }
    }
}
