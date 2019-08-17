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
import ngke.casac.nstreet.model.template.DanceObject;
import ngke.casac.nstreet.model.template.DanceSubItem;
import ngke.casac.nstreet.model.template.SubItemContractTemplate;

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
            category.dbInsert(writeDB);

            dance.setCategory(category);

            dance.dbInsert(writeDB);
            Dance dance2 = Dance.getDanceById(writeDB,null, dance.getId());
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
            category.dbInsert(writeDB);

            Dance dance = new Dance("West Coast Swing");
            dance.dbInsert(writeDB);

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
            dance.dbInsert(writeDB);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

        try {
            dance.dbInsert(writeDB);
            assertTrue(false);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

        Dance dance2 = new Dance("West Coast Swing");
        try {
            dance2.dbInsert(writeDB);
            assertTrue(false);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getDanceByNameWorks() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Dance.deleteAllDances(writeDB);
            Category.deleteAllCategories(writeDB);
            ActivityLog.deleteAllActivity(writeDB);

            Category category = new Category("Swing");
            category.dbInsert(writeDB);

            Dance dance = new Dance("West Coast Swing");
            dance.setCategory(category);
            dance.dbInsert(writeDB);

            Dance dance2 = Dance.getDanceByName(writeDB, null, "WEST COAST SWING");
            compareDances(dance, dance2);

        } catch (DanceObjectException e) {
            e.printStackTrace();
        }

    }


    public static void compareDances(Dance d1, Dance d2) {
        compareDanceObjects(d1, d2);
        if(d1 == null) {
            return;
        }
        assertEquals(d1.getDescription(), d2.getDescription());
        if(d1.getCategory() != null) {
            assertEquals(d1.getCategory().getId(), d2.getCategory().getId());
        } else {
            assertNull(d2.getCategory());
        }
    }

    public static void compareDanceObjects(DanceObject d1, DanceObject d2) {
        if(d1 == null && d2 != null) {
            assertNull(d2);
            return;
        }

        if(d2 == null && d1 != null) {
            assertNotNull(d2);
            return;
        }

        if(d1 == null && d2 == null) {
            return;
        }

        assertEquals(d1.getId(), d2.getId());
        assertEquals(d1.getName(), d2.getName());
        assertEquals(d1.getStarred(), d2.getStarred());
        assertEquals(d1.getDateCreated(), d2.getDateCreated());
        assertEquals(d1.getDateModified(), d2.getDateModified());
    }

    public static void compareSubDanceObjects(DanceSubItem d1, DanceSubItem d2) {
        if(d1 == null && d2 != null) {
            assertNull(d2);
            return;
        }

        if(d2 == null && d1 != null) {
            assertNotNull(d2);
            return;
        }

        if(d1 == null && d2 == null) {
            return;
        }

        compareDanceObjects(d1, d2);
        assertEquals(d1.getOrderNumber(), d2.getOrderNumber());
        assertEquals(d1.getRating(), d2.getRating());
        Dance dance1 = d1.getDance();
        Dance dance2 = d2.getDance();

        int nullCount = 0;

        if(dance1 != null) {
            nullCount++;
        }

        if(dance2 != null) {
            nullCount++;
        }

        if(nullCount%2 != 0) {
            assertEquals("One dance is null!", "");
        } else if(nullCount == 0) {
            assertEquals(dance1.getId(), dance2.getId());
        }


    }
}
