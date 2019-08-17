package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.Map;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceSubItem;
import ngke.casac.nstreet.model.template.SubItemContractTemplate;

public class Note extends DanceSubItem {

    public Note(SQLiteDatabase db, Map<Long, Dance> danceMap, Map<Long, Category> categoryMap, Cursor cursor) throws DanceObjectException {
        super(db, danceMap, categoryMap, cursor);

        note = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NOTE));
    }

    private String note;

    public static final String TYPE = "NOTE";

    // DATABASE FUNCTIONS

    public static Note getNoteById(SQLiteDatabase db, Map<Long, Dance> danceMap, Map<Long, Category> categoryMap, long id) {
        try {
            checkReadableDatabase(db);

            String[] projection = Contract.getProjection();
            String selection = Contract._ID + " = ?";
            String[] selectionArgs = {Long.toString(id)};

            Cursor cursor = db.query(Contract.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);

            try {
                if (cursor.moveToNext()) {
                    return new Note(db, danceMap, categoryMap, cursor);
                }
            } finally {
                cursor.close();
            }
        } catch (DanceObjectException ex) {

        }
        return null;
    }

    @Override
    protected void updateContentValuesForSubItem(ContentValues cv) {
        cv.put(Contract.COL_NOTE, note);
    }

    @Override
    protected void isInsertReady(SQLiteDatabase db) throws DanceObjectException {
        if(note == null || note.trim().isEmpty()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }
    }

    @Override
    public void isUpdateReady(SQLiteDatabase db) throws DanceObjectException {

    }

    public static class Contract extends SubItemContractTemplate {

        public static final String TABLE_NAME = "note_table";
        public static final String COL_NOTE = "note";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_NOTE + " TEXT, ");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }

        public static String[] getProjection() {
            String[] projection = {
                    _ID,
                    COL_NAME,
                    COL_NOTE,
                    COL_DATE_CREATED,
                    COL_STARRED,
                    COL_DATE_MODIFIED,
                    COL_ORDER_NO,
                    COL_RATING
            };
            return projection;
        }

    }

    // Boilerplate

    @Override
    public String getObjectName() {
        return "Note";
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getTableName() {
        return Contract.TABLE_NAME;
    }

    // Getters & Setters

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
