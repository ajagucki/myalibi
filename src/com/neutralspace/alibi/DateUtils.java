package com.neutralspace.alibi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Utilities for Calendars and milliseconds-since-epoch date-times.
 */
public class DateUtils {

    /**
     * Returns whether the two times given exist within the same calendar day
     * in the given time zone.
     * @param left: first time to compare
     * @param right: second time to compare
     * @return True if times are within same day
     */
    public static Boolean sameDay(final TimeZone tz, final Calendar left, final Calendar right) {
        return left.get(Calendar.DAY_OF_YEAR) == right.get(Calendar.DAY_OF_YEAR) &&
               left.get(Calendar.YEAR) == right.get(Calendar.YEAR);
    }

    /**
     * Returns whether the two times given exist within one week of each other
     * and do not have the same day name, in the given time zone.
     * 
     * Purpose: See if displaying the date of "time" (assuming the current time
     * is "current") should specify date or could just display, e.g., "Sunday",
     * without it being confusing to the user.
     * 
     * If today is Sunday the 8th, and "time" is Sunday the 1st, we can't
     * just display "Sunday" as that would be confusing (so this returns
     * false). If "time" is Monday the 2nd, however, we can just display
     * "Monday" (and this method returns true).
     * 
     * @param time:    time to be examined in relation to current time
     * @param current: current time
     * @return True if times are not-confusingly within 7 days apart
     */
    public static Boolean withinWeek(final TimeZone tz, final Calendar time, final Calendar current) {
        assert time.compareTo(current) < 0; /* doesn't support arbitrary order */
        Calendar weekBefore = Calendar.getInstance(tz);
        weekBefore.setTimeInMillis(current.getTimeInMillis());
        weekBefore.set(Calendar.HOUR, 0);
        weekBefore.set(Calendar.MINUTE, 0);
        weekBefore.set(Calendar.SECOND, 0);
        weekBefore.set(Calendar.MILLISECOND, 0);
        weekBefore.add(Calendar.DAY_OF_YEAR, -6);        
        return time.compareTo(weekBefore) >= 0;
    }
    
    /**
     * Format time unambiguously with as little detail as possible, based on
     * the current time and current time zone.
     * 
     * The time will be formatted one of these three ways:
     *   - "2:00 PM" if "time" is during the same day as today;
     *   - "Wednesday, 2:00 PM" if time is within last 6 days;
     *   - "15 February, 2009, 2:00 PM" if time is on the same-named day of
     *     last week or earlier.
     *
     * @param time in milliseconds since epoch
     * @return String representing nicely formatted time
     */
    public static String getNiceTime(long time) {
        TimeZone tz = TimeZone.getDefault();
        Calendar now = Calendar.getInstance(tz);
        Calendar someTime = Calendar.getInstance(tz);
        someTime.setTimeInMillis(time);

        DateFormat df;
        if (sameDay(tz, someTime, now)) {
            df = DateFormat.getTimeInstance(DateFormat.SHORT);
            return df.format(someTime.getTime());
        }     
        if (withinWeek(tz, someTime, now)) {
            SimpleDateFormat sdfDay = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
            sdfDay.applyPattern("EEEE, ");
            df = DateFormat.getTimeInstance(DateFormat.SHORT);
            return sdfDay.format(someTime.getTime()) + df.format(someTime.getTime());
        }
        df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        return df.format(someTime.getTime());
    }   
}
