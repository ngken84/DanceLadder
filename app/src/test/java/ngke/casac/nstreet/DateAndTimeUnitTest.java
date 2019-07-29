package ngke.casac.nstreet;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import ngke.casac.nstreet.model.DateAndTime;

import static org.junit.Assert.assertEquals;

public class DateAndTimeUnitTest {

    @Test
    public void constructor_works() {
        DateAndTime dateAndTime = new DateAndTime(new Date(System.currentTimeMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        int date = Integer.parseInt(sdf.format(new Date(System.currentTimeMillis()))) - 100;

        assertEquals(dateAndTime.getDateInt(), date);
    }

    @Test
    public void constructor_works_2() {
        DateAndTime dateAndTime = new DateAndTime(27,6, 2019, 12, 25);
        Date date = dateAndTime.getDate();

        DateAndTime dateAndTime2 = new DateAndTime(date);
        assertEquals(dateAndTime2.getDateInt(), dateAndTime.getDateInt());
        assertEquals(dateAndTime2.getTimeInt(), dateAndTime.getTimeInt());
    }
}
