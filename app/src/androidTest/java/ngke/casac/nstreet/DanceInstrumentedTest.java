package ngke.casac.nstreet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ngke.casac.nstreet.database.DanceSQLHelper;
import ngke.casac.nstreet.model.Category;
import ngke.casac.nstreet.model.Dance;
import ngke.casac.nstreet.model.DanceObjectException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class DanceInstrumentedTest {

    @Test
    public void constructorWorks() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Dance dance = new Dance("Tango");
            assertNotNull(dance);

            Dance.deleteAllDances(writeDB);
            Category.deleteAllCategories(writeDB);

            Category category = new Category("Ballroom");
            category.insertCategory(writeDB);

            dance.setCategory(category);

            dance.insertDance(writeDB);
            Dance dance2 = new Dance(writeDB, dance.getId());
            assertEquals(dance.getName(), dance2.getName());
            assertEquals(dance.getCategory().getName(), dance2.getCategory().getName());
        } catch (DanceObjectException e) {
            assertEquals("", e.getMessage());
        }
    }

}
