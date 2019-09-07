package ngke.casac.nstreet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import ngke.casac.nstreet.database.DanceSQLHelper;
import ngke.casac.nstreet.model.DanceObjectException;
import ngke.casac.nstreet.model.Location;

import static ngke.casac.nstreet.DanceInstrumentedTest.compareDanceObjects;
import static org.junit.Assert.assertEquals;

public class LocationInstrumentedTest {

    @Test
    public void constructorWorks() {
        SQLiteDatabase db = getClearedDatabase();

        Location location = new Location("Spotlight Ballroom");
        try {
            location.dbInsert(db);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

        Location location2 = Location.getLocationById(db, location.getId());

        compareLocations(location, location2);

        location = new Location("Firehouse 5");
        location.setAddress("400 15th Street");
        location.setCity("Sacramento");
        location.setState("CA");
        location.setZip("95811");

        try {
            location.dbInsert(db);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

        location2 = Location.getLocationById(db, location.getId());

        compareLocations(location, location2);

        Location location3 = Location.getLocationByName(db, "Firehouse 5");
        compareLocations(location2, location3);
    }

    @Test
    public void modifyWorks() {
        SQLiteDatabase db = getClearedDatabase();

        Location location = new Location("Spotlight");
        try {
            location.dbInsert(db);
        } catch (DanceObjectException e) {
            assertEquals("modifyWorks : DB insert", "");
        }

        location.setName("Spotlight Ballroom");
        location.setAddress("1100 J Street");
        location.setCity("Sacramento");
        location.setState("CA");
        location.setZip("95811");
        try {
            location.dbUpdate(db, true);
        } catch (DanceObjectException e) {
            assertEquals("modifyWorks", "DB Update");
        }

        Location location2 = Location.getLocationById(db, location.getId());
        compareLocations(location, location2);
    }

    @Test
    public void modifyToExistingName() {
        SQLiteDatabase db = getClearedDatabase();

        Location l1 = new Location("Spotlight");
        try {
            l1.dbInsert(db);
        } catch (DanceObjectException e) {
            assertEquals("modifyToExistingName", "Insert");
        }

        Location l2 = new Location("Firehouse");
        try {
            l2.dbInsert(db);
        } catch (DanceObjectException e) {
            assertEquals("modifyToExistingName", "Insert");
        }

        l1.setName("Firehouse");
        try {
            l1.dbUpdate(db, false);
            assertEquals("modifyToExistingName", "Should not have gotten here");
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }
    }

    private SQLiteDatabase getClearedDatabase() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Location.deleteAllLocations(writeDB);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

        return writeDB;
    }

    public static void compareLocations(Location l1, Location l2) {
        if(l1 == null && l2 == null) {
            return;
        }

        if(l1 == null && l2 != null) {
            assertEquals("L1 is null", "");
            return;
        }
        if(l2 == null && l1 != null) {
            assertEquals("L2 is null", "");
            return;
        }
        assertEquals(l1.getAddress(), l2.getAddress());
        assertEquals(l1.getCity(), l2.getCity());
        assertEquals(l1.getState(), l2.getState());
        assertEquals(l1.getZip(), l2.getZip());


        compareDanceObjects(l1, l2);
    }


}
