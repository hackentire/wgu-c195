package net.mcentire.util;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

/**
 * Writes log data to the filesystem
 */
public class Logger {
    /**
     * Path and filename of the log file to generate
     */
    private static final Path path = Paths.get("login_activity.txt");

    /**
     * Appends/creates a log entry for a login event at the predefined path.
     * @param username the username attempted
     * @param authenticated whether the event was successful or not
     */
    public static void log (String username, boolean authenticated) {
        String message = Time.getCurrentUtcTime().toString()
            + " UTC | Event: Login attempt | Username: "
            + username + " | Result: "
            + (authenticated ? "Success" : "Failure") + System.lineSeparator();
        try {
            Files.writeString(path, message, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}