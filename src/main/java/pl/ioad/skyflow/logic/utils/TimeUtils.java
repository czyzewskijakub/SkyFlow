package pl.ioad.skyflow.logic.utils;

import static java.time.ZoneOffset.UTC;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class TimeUtils {

    public static String toUnixTime(String dateTime) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        var localDateTime = LocalDateTime.parse(dateTime, formatter);
        return String.valueOf(localDateTime.toEpochSecond(UTC));
    }

    public static String toDateTime(String unixTime) {
        var date = new Date(Long.parseLong(unixTime) * 1000);
        var sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public static String addDateTime(String dateTime) {
        return (Integer.parseInt(dateTime.substring(0, 4)) + 1) + "-" + dateTime.substring(5);
    }

    public static String subtractDateTime(String dateTime) {
        return (Integer.parseInt(dateTime.substring(0, 4)) - 1) + "-" + dateTime.substring(5);
    }

}
