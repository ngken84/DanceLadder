package ngke.casac.nstreet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ngke.casac.nstreet.database.DanceSQLHelper;
import ngke.casac.nstreet.model.DanceObjectException;
import ngke.casac.nstreet.model.Location;
import ngke.casac.nstreet.model.Teacher;

import static ngke.casac.nstreet.DanceInstrumentedTest.compareDanceObjects;
import static ngke.casac.nstreet.LocationInstrumentedTest.compareLocations;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TeacherInstrumentedTest {

    @Test
    public void constructorWorks() {
        SQLiteDatabase db = getClearedDatabase();

        Teacher teacher = new Teacher("Christina", "Musser");
        try {
            teacher.dbInsert(db);
        } catch (DanceObjectException e) {
            assertEquals("constructorWorks", "Insert");
        }

        Teacher t2 = Teacher.getTeacherById(db, null, teacher.getId());
        compareTeachers(teacher, t2);


    }


    private SQLiteDatabase getClearedDatabase() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Location.deleteAllLocations(writeDB);
            Teacher.deleteAllTeachers(writeDB);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

        return writeDB;
    }

    private void compareTeachers(Teacher t1, Teacher t2) {
        if(t1 == null && t2 == null) {
            return;
        }

        if(t1 == null && t2 != null) {
            assertEquals("L1 is null", "");
            return;
        }
        if(t2 == null && t1 != null) {
            assertEquals("L2 is null", "");
            return;
        }

        assertEquals(t1.getEmail(), t2.getEmail());
        assertEquals(t1.getFirstName(), t2.getFirstName());
        compareLocations(t1.getLocation(), t2.getLocation());
        assertEquals(t1.getPhoneNumber(), t2.getPhoneNumber());

        compareDanceObjects(t1, t2);
    }




}


