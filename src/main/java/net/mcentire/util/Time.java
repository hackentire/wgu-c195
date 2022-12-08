package net.mcentire.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Time {

    /**
     *
     * @param timestamp the timestamp value to be converted
     * @return a LocalDateTime using the system's Zone
     */
    public static LocalDateTime toLocalZoneDateTime(Timestamp timestamp) {
//        return timestamp.toLocalDateTime().atZone(ZoneId.systemDefault());
        return LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
    }

    /**
     *
     * @param localTime the LocalDateTime to be converted
     * @return a UTC Timestamp representation of the provided LocalDateTime
     */
    public static Timestamp toUtcTimestamp(LocalDateTime localTime) {
        return Timestamp.valueOf(localTime);
    }

    public static String getCurrentUtcTime() {
        return Instant.now().toString();
    }
}
