package ngke.casac.nstreet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ngke.casac.nstreet.database.DanceSQLHelper;
import ngke.casac.nstreet.model.DanceObjectException;
import ngke.casac.nstreet.model.Drill;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DrillInstrumentedTest {

    @Test
    public void constructorNoDanceWorks() {

        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Drill.deleteAllDrills(writeDB);
        } catch (DanceObjectException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        String name = "Pivot Turns";
        String instructions = "Pivot turns over and over again.";

        Drill drill = new Drill(name, instructions);

        try {
            drill.insertDrill(writeDB);
        } catch (DanceObjectException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        try {
            Drill drill2 = new Drill(writeDB, drill.getId());
            compareDrills(drill, drill2);


        } catch (DanceObjectException e) {
            e.printStackTrace();
            assertTrue(false);
        }


    }

    public static void compareDrills(Drill d1, Drill d2) {
        if(d1 == null) {
            assertEquals("Drill 1 is null", "");
            return;
        }
        if(d2 == null) {
            assertEquals("Drill 2 is null", "");
            return;
        }

        assertEquals(d1.getId(), d2.getId());
        assertEquals(d1.getName(), d2.getName());
        assertEquals(d1.getInstructions(), d2.getInstructions());
        assertEquals(d1.getStarred(), d2.getStarred());
        assertEquals(d1.getDateCreated(), d2.getDateCreated());
        assertEquals(d1.getDateModified(), d2.getDateModified());
        assertEquals(d1.getDancersRequired(), d2.getDancersRequired());
        assertEquals(d1.getCompletionCount(), d2.getCompletionCount());
        assertEquals(d1.getEstimatedTime(), d2.getEstimatedTime());
        if(d1.getDance() != null) {
            assertEquals(d1.getDance().getId(), d2.getDance().getId());
        } else {
            assertNull(d2.getDance());
        }
        assertEquals(d1.getLastCompleted(), d2.getLastCompleted());

    }


}
