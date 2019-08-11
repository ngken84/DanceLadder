package ngke.casac.nstreet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ngke.casac.nstreet.database.DanceSQLHelper;
import ngke.casac.nstreet.model.ActivityLog;
import ngke.casac.nstreet.model.Category;
import ngke.casac.nstreet.model.Dance;
import ngke.casac.nstreet.model.DanceObjectException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DanceInstrumentedTest {

    @Test
    public void constructorWorks() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Dance.deleteAllDances(writeDB);
            Category.deleteAllCategories(writeDB);

            Dance dance = new Dance("Tango");
            assertNotNull(dance);

            Category category = new Category("Ballroom");
            category.insertCategory(writeDB);

            dance.setCategory(category);

            dance.insertDance(writeDB);
            Dance dance2 = new Dance(writeDB,null, dance.getId());
            compareDances(dance, dance2);
        } catch (DanceObjectException e) {
            assertEquals("", e.getMessage());
        }
    }

    @Test
    public void doesCreateActivityLogWork() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Dance.deleteAllDances(writeDB);
            Category.deleteAllCategories(writeDB);
            ActivityLog.deleteAllActivity(writeDB);

            Category category = new Category("Swing");
            category.insertCategory(writeDB);

            Dance dance = new Dance("West Coast Swing");
            dance.insertDance(writeDB);

            List<ActivityLog> log = ActivityLog.getRecentActivity(writeDB);

            assertEquals(log.size(), 2);

            ActivityLog danceLog = log.get(0);
            compareDances(dance, (Dance) danceLog.getObject());

        } catch (DanceObjectException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void doubleInsertThrowsException() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Dance.deleteAllDances(writeDB);
            Category.deleteAllCategories(writeDB);
            ActivityLog.deleteAllActivity(writeDB);


        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

        Dance dance = new Dance("West Coast Swing");
        try {
            dance.insertDance(writeDB);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

        try {
            dance.insertDance(writeDB);
            assertTrue(false);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

        Dance dance2 = new Dance("West Coast Swing");
        try {
            dance2.insertDance(writeDB);
            assertTrue(false);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

    }

    public static void compareDances(Dance d1, Dance d2) {
        if(d1 == null) {
            assertEquals("Dance 1 is null", "");
            return;
        }
        if(d2 == null) {
            assertEquals("Dance 2 is null", "");
            return;
        }
        assertEquals(d1.getId(), d2.getId());
        assertEquals(d1.getName(), d2.getName());
        assertEquals(d1.getStarred(), d2.getStarred());
        assertEquals(d1.getDateCreated(), d2.getDateCreated());
        assertEquals(d1.getDateModified(), d2.getDateModified());
        assertEquals(d1.getDescription(), d2.getDescription());
        if(d1.getCategory() != null) {
            assertEquals(d1.getCategory().getId(), d2.getCategory().getId());
        } else {
            assertNull(d2.getCategory());
        }


    }
}
