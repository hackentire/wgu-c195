package net.mcentire.app;

import net.mcentire.model.User;
import java.util.ResourceBundle;

/**
 * All cross-scene application data resides in this singleton object
 */
public final class AppContext {

    private static AppContext single_instance = null;
    private static ResourceBundle resourceBundle;
    private static User activeUser = null;

    private AppContext() {}

    /**
     * Only instantiate and use one AppDataSingleton instance across the application
     */
    public static AppContext getInstance() {
        if (single_instance == null)
        {
            single_instance = new AppContext();
        }
        return single_instance;
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User user) {
        AppContext.activeUser = user;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

}
