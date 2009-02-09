package com.neutralspace.alibi;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Utilities for Calendars and milliseconds-since-epoch date-times.
 */
public class DateUtils {

    /**
     * Returns whether the two times given exist within the same calendar day.
     * @param left: first time to compare
     * @param right: second time to compare
     * @return True if times are within same day
     */
    public static Boolean sameDay(final Calendar left, final Calendar right) {
        return left.get(Calendar.DAY_OF_YEAR) == right.get(Calendar.DAY_OF_YEAR) &&
               left.get(Calendar.YEAR) == right.get(Calendar.YEAR);
    }

    /**
     * Mutates given calendar to be right on the nearest minute.
     * @param cal: The calendar to round
     */
    public static void roundToMinutes(Calendar cal) {
        int ms = cal.get(Calendar.MILLISECOND);
        if (ms >= 500)
            cal.add(Calendar.MILLISECOND, 1000 - ms);
        else
            cal.set(Calendar.MILLISECOND, 0);

        int second = cal.get(Calendar.SECOND);
        if (second >= 30)
            cal.add(Calendar.SECOND, 60 - second);
        else
            cal.set(Calendar.SECOND, 0);
    }
    
    /**
     * Formats the time given (in milliseconds since epoch) in a "nice" way,
     * based on the current time. Example: "2:00 PM" if 'time' is during the
     * same day as today; if not, more contextual information is given.
     * @param time in milliseconds since epoch
     * @return String representing nicely formatted time
     */
    public static String getNiceTime(long time) {
        Calendar now = Calendar.getInstance();
        Calendar someTime = Calendar.getInstance();
        someTime.setTimeInMillis(time);
        
        roundToMinutes(now);
        roundToMinutes(someTime);
        DateFormat df;
        if (sameDay(someTime, now)) {
            df = DateFormat.getTimeInstance();
            return df.format(someTime.getTime());
        }
        df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        return df.format(someTime.getTime());
    }   
}
