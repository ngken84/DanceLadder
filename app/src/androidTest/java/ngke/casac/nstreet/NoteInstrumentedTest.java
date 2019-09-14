package ngke.casac.nstreet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import ngke.casac.nstreet.database.DanceSQLHelper;
import ngke.casac.nstreet.model.DanceObjectException;
import ngke.casac.nstreet.model.Note;

import static junit.framework.TestCase.assertEquals;

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






}
