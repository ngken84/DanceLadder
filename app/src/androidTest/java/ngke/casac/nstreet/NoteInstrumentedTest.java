package ngke.casac.nstreet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import ngke.casac.nstreet.database.DanceSQLHelper;
import ngke.casac.nstreet.model.Dance;
import ngke.casac.nstreet.model.DanceObjectException;
import ngke.casac.nstreet.model.Note;
import ngke.casac.nstreet.model.subitemlists.DanceObjRelation;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
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

    @Test
    public void testPopulateDanceObjectRelationshipList() {
        SQLiteDatabase db = getClearedDatabase();

        Dance dance = new Dance("West Coast Swing");
        try {
            dance.dbInsert(db);
        } catch (DanceObjectException e) {
            assertEquals("testPopulateDanceObjectRelationshipList", "dance insert");
            return;
        }

        Note note = new Note("West Coast Swing is cool");
        try {
            note.dbInsert(db);
        } catch (DanceObjectException e) {
            assertEquals("testPopulateDanceObjectRelationshipList", "note insert");
            return;
        }

        Note n2 = new Note("West Coast Swing is just OK");
        try {
            n2.dbInsert(db);
        } catch (DanceObjectException e) {
            assertEquals("testPopulateDanceObjectRelationshipList", "note 2 insert");
            return;
        }

        DanceObjRelation rel = new DanceObjRelation(dance, note);
        try {
            rel.dbInsert(db);
        } catch (DanceObjectException e) {
            assertEquals("testPopulateDanceObjectRelationshipList", "rel insert");
            return;
        }

        DanceObjRelation rel2 = new DanceObjRelation(dance, n2);
        try {
            rel2.dbInsert(db);
        } catch (DanceObjectException e) {
            assertEquals("testPopulateDanceObjectRelationshipList", "rel2 insert");
            return;
        }

        List<DanceObjRelation> list = new LinkedList<>();
        list.add(rel);
        list.add(rel2);

        try {
            Note.populateDanceObjRelList(db, list);
        } catch (DanceObjectException e) {
            assertEquals("relation population", e.getMessage());
            return;
        }

        assertNotNull(rel.getObject());
        assertNotNull(rel2.getObject());

    }


    private SQLiteDatabase getClearedDatabase() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Note.deleteAllNotes(writeDB);
            Dance.deleteAllDances(writeDB);
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
