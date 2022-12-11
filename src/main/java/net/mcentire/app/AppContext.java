package net.mcentire.app;

import javafx.collections.ObservableList;
import net.mcentire.model.*;

import java.util.ResourceBundle;

/**
 * All cross-scene application data resides in this singleton object
 */
public final class AppContext {

    private static AppContext single_instance = null;
    private static ResourceBundle resourceBundle;
    private static User activeUser = null;

    private static AppData data = new AppData();

    private AppContext() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AppContext getInstance() {
        if (single_instance == null) {
            single_instance = new AppContext();
        }
        return single_instance;
    }


    /**
     * Gets data.
     *
     * @return the data
     */
    public static AppData getData() {
        return data;
    }

    /**
     * Gets active user.
     *
     * @return the active user
     */
    public static User getActiveUser() {
        return activeUser;
    }

    /**
     * Sets active user.
     *
     * @param user the user
     */
    public static void setActiveUser(User user) {
        AppContext.activeUser = user;
    }

    /**
     * Gets resource bundle.
     *
     * @return the resource bundle
     */
    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * Sets resource bundle.
     *
     * @param resourceBundle the resource bundle
     */
    public static void setResourceBundle(ResourceBundle resourceBundle) {
        AppContext.resourceBundle = resourceBundle;
    }

    public static class AppData {
        private ObservableList<Customer> customers;
        private ObservableList<Appointment> appointments;
        private ObservableList<Country> countries;
        private ObservableList<Division> divisions;
        private ObservableList<Contact> contacts;
        private ObservableList<User> users;

        /**
         * Gets customers.
         *
         * @return the customers
         */
        public ObservableList<Customer> getCustomers() {
            return customers;
        }

        /**
         * Sets customers.
         *
         * @param customers the customers
         */
        public void setCustomers(ObservableList<Customer> customers) {
            this.customers = customers;
        }

        /**
         * Gets appointments.
         *
         * @return the appointments
         */
        public ObservableList<Appointment> getAppointments() {
            return appointments;
        }

        /**
         * Sets appointments.
         *
         * @param appointments the appointments
         */
        public void setAppointments(ObservableList<Appointment> appointments) {
            this.appointments = appointments;
        }

        /**
         * Gets countries.
         *
         * @return the countries
         */
        public ObservableList<Country> getCountries() {
            return countries;
        }

        /**
         * Sets countries.
         *
         * @param countries the countries
         */
        public void setCountries(ObservableList<Country> countries) {
            this.countries = countries;
        }

        /**
         * Gets divisions.
         *
         * @return the divisions
         */
        public ObservableList<Division> getDivisions() {
            return divisions;
        }

        /**
         * Sets divisions.
         *
         * @param divisions the divisions
         */
        public void setDivisions(ObservableList<Division> divisions) {
            this.divisions = divisions;
        }

        /**
         * Gets contacts.
         *
         * @return the contacts
         */
        public ObservableList<Contact> getContacts() {
            return contacts;
        }

        /**
         * Sets contacts.
         *
         * @param contacts the contacts
         */
        public void setContacts(ObservableList<Contact> contacts) {
            this.contacts = contacts;
        }

        /**
         * Gets users.
         *
         * @return the users
         */
        public ObservableList<User> getUsers() {
            return users;
        }

        /**
         * Sets users.
         *
         * @param users the users
         */
        public void setUsers(ObservableList<User> users) {
            this.users = users;
        }
    }
}
