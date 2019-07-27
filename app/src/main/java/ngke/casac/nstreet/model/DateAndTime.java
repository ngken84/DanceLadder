package ngke.casac.nstreet.model;

import java.util.Calendar;
import java.util.Date;

public class DateAndTime {

    private int dateInt;
    private int timeInt;

    public DateAndTime(int dateInt, int timeInt) {
        this.dateInt = dateInt;
        this.timeInt = timeInt;
    }

    public DateAndTime(int day, int month, int year) {
        dateInt = (day * 1000000) + (month * 10000) + year;
    }

    /**
     *
     * @param day
     * @param month January is 0!
     * @param year
     * @param hour
     * @param minute
     */
    public DateAndTime(int day, int month, int year, int hour, int minute) {
        dateInt = (day * 1000000) + (month * 10000) + year;
        timeInt = hour * 100 + minute;
    }

    public DateAndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        dateInt = (day * 1000000) + (month * 10000) + year;
        timeInt = hour * 100 + minute;
    }

    public Date getDate() {
        Calendar calendar = Calendar.getInstance();

        int hour = timeInt/ 100;
        int minute = timeInt % 100;

        int year = dateInt % 10000;
        int month = (dateInt / 10000) % 100;
        int day = dateInt / 1000000;

        calendar.set(year, month, day, hour, minute);
        return calendar.getTime();
    }

    public int getDateInt() {
        return dateInt;
    }

    public void setDateInt(int dateInt) {
        this.dateInt = dateInt;
    }

    public int getTimeInt() {
        return timeInt;
    }

    public void setTimeInt(int timeInt) {
        this.timeInt = timeInt;
    }
}
