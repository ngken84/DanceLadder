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

        Teacher t3 = Teacher.getTeacherByName(db, "Christina", "Musser");
        compareTeachers(t2, t3);
    }

    @Test
    public void constructorAllParameters() {
        SQLiteDatabase db = getClearedDatabase();

        Teacher t1 = new Teacher("Christina", "Musser");

        Location l1 = new Location("Spotlight Ballroom");
        l1.setAddress("100 J Street");
        l1.setCity("Sacramento");
        l1.setState("CA");
        l1.setZip("95814");

        t1.setEmail("ChristinaMusser@gmail.com");
        t1.setLocation(l1);
        t1.setPhoneNumber("1-502-283-1983");

        try {
            t1.dbInsert(db);
        } catch (DanceObjectException e) {
            assertEquals("constructorAllParameters", "Insert");
        }

        Location l2 = Location.getLocationById(db, l1.getId());
        compareLocations(l1, l2);

        Teacher t2 = Teacher.getTeacherById(db, null, t1.getId());
        compareTeachers(t1, t2);
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


