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

    @Override
    protected void updateContentValuesForSubInsert(ContentValues cv) {
        cv.put(Contract.COL_NOTE, note);
    }

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

    private String note;

    public static final String TYPE = "NOTE";

    @Override
    protected void isInsertReady(SQLiteDatabase db) throws DanceObjectException {
        if(note == null || note.trim().isEmpty()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }
    }

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

    // DATABASE FUNCTIONS

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

    public void insertNote(SQLiteDatabase db) throws DanceObjectException {
        if(!isWriteDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }

        if(note == null || note.trim().length() == 0 || dance == null) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }

        if(id > 0) {
            throw new DanceObjectException(DanceObjectException.ERR_ALREADY_EXISTS);
        }

        long time = System.currentTimeMillis();

        ContentValues cv = new ContentValues();
        cv.put(Contract.COL_NAME, name);
        cv.put(Contract.COL_NOTE, note);
        cv.put(Contract.COL_DANCE_ID, dance.getId());
        cv.put(Contract.COL_DATE_CREATED, time);
        cv.put(Contract.COL_DATE_MODIFIED, time);
        cv.put(Contract.COL_STARRED, starred ? 1 : 0);
        cv.put(Contract.COL_ORDER_NO, orderNumber);
        cv.put(Contract.COL_RATING, rating);

        id = db.insert(Contract.TABLE_NAME, null, cv);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
