package ngke.casac.nstreet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import ngke.casac.nstreet.database.DanceSQLHelper;
import ngke.casac.nstreet.model.DanceObjectException;
import ngke.casac.nstreet.model.Note;

import static junit.framework.TestCase.assertEquals;
import static ngke.casac.nstreet.DanceInstrumentedTest.compareDanceObjects;

public class NoteInstrumentedTest {

    @Test
    public void constructorWorks() {
        SQLiteDatabase db = getClearedDatabase();

        Note note = new Note("This is a cool note");

        try {
            note.dbInsert(db);
        } catch (DanceObjectException e) {
            assertEquals("constructorWorks", e.getMessage());
        }

        Note n2 = Note.getNoteById(db, note.getId());
        compareNotes(note, n2);

    }

    private SQLiteDatabase getClearedDatabase() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Note.deleteAllNotes(writeDB);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }
        return writeDB;
    }

    public static void compareNotes(Note n1, Note n2) {
        if(n1 == null && n2 == null) {
            return;
        }

        if(n1 == null && n2 != null) {
            Assert.assertEquals("L1 is null", "");
            return;
        }
        if(n2 == null && n1 != null) {
            Assert.assertEquals("L2 is null", "");
            return;
        }


        assertEquals(n1.getNote(), n2.getNote());
        compareDanceObjects(n1, n2);

    }




}
