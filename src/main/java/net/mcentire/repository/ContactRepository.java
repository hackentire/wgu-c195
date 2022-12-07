package net.mcentire.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.mcentire.model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRepository extends Repository {

    /**
     * Get a single contact by ID
     * @param id
     * @return the Contact if found
     * @throws SQLException
     */
    public static Contact get(int id) throws SQLException {
        Contact contact = null;

        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
        Query.executeQuery(sql, new QueryParameter(id));
        ResultSet rs = Query.getResult();

        if (rs.next())
        {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            contact = new Contact(contactId, contactName, contactEmail);
        }

        return contact;
    }

    /**
     * Fetches all Contacts
     * @return an ObservableList of Contacts
     * @throws SQLException
     */
    public static ObservableList<Contact> getAll() throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM contacts";
        Query.executeQuery(sql);
        ResultSet rs = Query.getResult();

        while (rs.next())
        {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactId, contactName, contactEmail);
            contacts.add(contact);
        }

        return contacts;
    }

    public boolean create(Contact contact) throws SQLException {
        String sql = "INSERT INTO contacts (Contact_Name, Email) values (?, ?)";
        Query.executeQuery(sql, new QueryParameter(contact.getName()), new QueryParameter(contact.getEmail()));

        return Query.getRowsAffected() > 0;
    }

    /**
     *
     * @param contact
     * @return true if update was successful
     * @throws SQLException
     */
    public boolean update(Contact contact) throws SQLException {
        String sql = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ?";
        Query.executeQuery(sql,
                new QueryParameter(contact.getName()),
                new QueryParameter(contact.getEmail()),
                new QueryParameter(contact.getId()));

        return Query.getRowsAffected() > 0;
    }

    /**
     * @param id
     * @return true if delete was successful
     * @throws SQLException
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM contacts WHERE Contact_ID = ?";
        Query.executeQuery(sql, new QueryParameter(id));

        return Query.getRowsAffected() > 0;
    }
}
