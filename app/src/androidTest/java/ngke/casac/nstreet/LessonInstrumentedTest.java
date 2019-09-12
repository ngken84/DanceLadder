package ngke.casac.nstreet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import ngke.casac.nstreet.database.DanceSQLHelper;
import ngke.casac.nstreet.model.DanceObjectException;
import ngke.casac.nstreet.model.Lesson;
import ngke.casac.nstreet.model.Location;
import ngke.casac.nstreet.model.Teacher;

import static junit.framework.TestCase.assertEquals;
import static ngke.casac.nstreet.DanceInstrumentedTest.compareDanceObjects;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LessonInstrumentedTest {

    @Test
    public void constructorWorks() {
        SQLiteDatabase db = getClearedDatabase();




    }

    private SQLiteDatabase getClearedDatabase() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Location.deleteAllLocations(writeDB);
            Teacher.deleteAllTeachers(writeDB);
            Lesson.deleteAllLessons(writeDB);
        } catch (DanceObjectException ex) {

        }

        return writeDB;

    }

    public static void compareLessons(Lesson l1, Lesson l2) {
        if(l1 == null && l2 != null) {
            assertNull(l2);
            return;
        }

        if(l2 == null && l1 != null) {
            assertNotNull(l2);
            return;
        }

        if(l1 == null && l2 == null) {
            return;
        }

        assertEquals(l1.getDuration(), l2.getDuration());
        if(l1.getStartDateAndTime() != null && l2.getStartDateAndTime() != null) {
            assertEquals(l1.getStartDateAndTime().getDateInt(), l2.getStartDateAndTime().getDateInt());
            assertEquals(l1.getStartDateAndTime().getTimeInt(), l2.getStartDateAndTime().getTimeInt());
        } else if (!(l1.getStartDateAndTime() == null && l2.getStartDateAndTime() == null)) {
            assertEquals("Date and Time not equivalent", "");
        }
        compareDanceObjects(l1, l2);


    }


}
