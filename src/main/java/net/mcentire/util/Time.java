package net.mcentire.util;

import java.sql.Timestamp;
import java.time.*;

public class Time {

    /**
     *
     * @return the current time in UTC
     */
    public static LocalDateTime getCurrentUtcTime() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    /**
     *
     * @param utcTimestamp a DB originated UTC timestamp
     * @return the local time representation of the timestamp
     */
    public static LocalDateTime toLocalDateTime(Timestamp utcTimestamp) {
        return utcTimestamp.toInstant().atZone(Clock.systemDefaultZone().getZone()).toLocalDateTime();
    }

    /**
     *
     * @param localDateTime the local time to convert into a UTC timestamp
     * @return a UTC timestamp representation of the datetime
     */
    public static Timestamp toUtcTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime.atZone(Clock.systemDefaultZone().getZone())
                .withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    /**
     *
     * @param localDateTime the local time to convert into a timestamp
     * @return a timestamp representation of the datetime
     */
    public static Timestamp toTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    /**
     *
     * @param localDateTime the local time to convert into a UTC timestamp
     * @return a UTC LocalDateTime representation of the datetime
     */
    public static LocalDateTime toUtcLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(Clock.systemDefaultZone().getZone())
                .withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    /**
     * Converts a local time to ET (EST/EDT)
     * @param localDateTime
     * @return
     */
    public static LocalDateTime toEstLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(Clock.systemDefaultZone().getZone())
                .withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
    }
}
