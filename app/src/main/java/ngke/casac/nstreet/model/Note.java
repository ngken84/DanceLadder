package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceSubItem;
import ngke.casac.nstreet.model.template.SubItemContractTemplate;

public class Note extends DanceSubItem {

    private String note;

    public static final String TYPE = "NOTE";

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
