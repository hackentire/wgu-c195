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

    public static AppContext getInstance() {
        if (single_instance == null) {
            single_instance = new AppContext();
        }
        return single_instance;
    }

    public static AppData getData() {
        return data;
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User user) {
        AppContext.activeUser = user;
    }

    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

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

        public ObservableList<Customer> getCustomers() {
            return customers;
        }

        public void setCustomers(ObservableList<Customer> customers) {
            this.customers = customers;
        }

        public ObservableList<Appointment> getAppointments() {
            return appointments;
        }

        public void setAppointments(ObservableList<Appointment> appointments) {
            this.appointments = appointments;
        }

        public ObservableList<Country> getCountries() {
            return countries;
        }

        public void setCountries(ObservableList<Country> countries) {
            this.countries = countries;
        }

        public ObservableList<Division> getDivisions() {
            return divisions;
        }

        public void setDivisions(ObservableList<Division> divisions) {
            this.divisions = divisions;
        }

        public ObservableList<Contact> getContacts() {
            return contacts;
        }

        public void setContacts(ObservableList<Contact> contacts) {
            this.contacts = contacts;
        }

        public ObservableList<User> getUsers() {
            return users;
        }

        public void setUsers(ObservableList<User> users) {
            this.users = users;
        }
    }
}
