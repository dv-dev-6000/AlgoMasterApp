package com.example.algomasterapp;

import java.util.Calendar;
import java.util.Date;

public class TimeHelper {

    public TimeHelper() {

    }

    public long GetCurrentUnixTime(){

        return System.currentTimeMillis() / 1000L;
    }

    public Date UnixTimeToDate(long unixTime){

        return new Date(unixTime * 1000L);
    }

    public boolean IsNextDay(Date currentTime, Date prevTime){

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(prevTime);
        cal2.setTime(currentTime);

        // Clear out the hours, minutes, seconds and milliseconds
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);

        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);

        // Add one day to the first date
        cal1.add(Calendar.DAY_OF_MONTH, 1);

        // return the result of comparison (true if it's the same as the second date)
        return cal1.equals(cal2);
    }

}
