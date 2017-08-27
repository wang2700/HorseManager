package jw.horsemanager.Misc;

import java.text.DateFormat;

/**
 * Created by jerry on 8/26/2017.
 * Description:
 */

public class SystemSetting {
    private static boolean is24HourFormat;
    private static DateFormat dateFormat;

    public static DateFormat getDateFormat() {
        return dateFormat;
    }

    public static void setDateFormat(DateFormat dateFormat) {
        SystemSetting.dateFormat = dateFormat;
    }

    public static DateFormat getTimeFormat() {
        return timeFormat;
    }

    public static void setTimeFormat(DateFormat timeFormat) {
        SystemSetting.timeFormat = timeFormat;
    }

    private static DateFormat timeFormat;

    public static boolean is24HourFormat() {
        return is24HourFormat;
    }

    public static void setIs24HourFormat(boolean is24HourFormat) {
        SystemSetting.is24HourFormat = is24HourFormat;
    }
}
