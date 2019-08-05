package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import ngke.casac.nstreet.model.template.DanceSubItem;
import ngke.casac.nstreet.model.template.SubItemContractTemplate;

public class Drill extends DanceSubItem {

    public Drill(String name, String instructions) {
        this.name = name;
        this.instructions = instructions;
        starred = false;
        dancersRequired = 1;
        completionCount = 0;
    }

    public Drill(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(Contract._ID));
        name = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NAME));
        starred = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_STARRED)) == 1;
        dateCreated = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_CREATED)));
        dateModified = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_MODIFIED)));
        instructions = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_INSTRUCTIONS));
        completionCount = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_COMPLETION_CNT));
        dancersRequired = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_DANCER_COUNT));
        estimatedTime = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_ESTIMATED_TIME));
        lastCompleted = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_LAST_COMPLETED)));
    }

    public static final String TYPE = "DRILL";

    public String instructions;
    public int completionCount;
    public int estimatedTime;
    public Date lastCompleted;
    public int dancersRequired;

    // DATABASE

    public Drill(SQLiteDatabase db, long id) throws DanceObjectException {
        checkReadableDatabase(db);

        String[] projection = Contract.getProjection();
        String selection = Contract._ID + " = ?";
        String[] selectionArgs = {Long.toString(id)};

        Cursor cursor = db.query(
                Contract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToNext()) {
            this.id = id;
            name = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NAME));
            starred = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_STARRED)) == 1;
            dateCreated = getDateFromLong(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_CREATED)));
            dateModified = getDateFromLong(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_MODIFIED)));
            instructions = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_INSTRUCTIONS));
            completionCount = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_COMPLETION_CNT));
            dancersRequired = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_DANCER_COUNT));
            estimatedTime = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_ESTIMATED_TIME));
            lastCompleted = getDateFromLong(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_LAST_COMPLETED)));
        } else {
            throw new DanceObjectException(DanceObjectException.ERR_NOT_FOUND);
        }

    }

    public void insertDrill(SQLiteDatabase db) throws DanceObjectException {
        checkWriteDatabase(db);

        if(id != 0) {
            throw new DanceObjectException(DanceObjectException.ERR_ALREADY_EXISTS);
        }

        if(!isNameValid()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }

        long created = System.currentTimeMillis();
        dateCreated = new Date(created);
        dateModified = new Date(created);

        ContentValues cv = new ContentValues();
        cv.put(Contract.COL_NAME, name);
        cv.put(Contract.COL_INSTRUCTIONS, instructions);
        cv.put(Contract.COL_COMPLETION_CNT, completionCount);
        cv.put(Contract.COL_DATE_CREATED, created);
        cv.put(Contract.COL_DATE_MODIFIED, created);
        cv.put(Contract.COL_DANCER_COUNT, dancersRequired);
        if(dance != null) {
            cv.put(Contract.COL_DANCE_ID, dance.getId());
        }
        cv.put(Contract.COL_RATING, rating);
        cv.put(Contract.COL_ESTIMATED_TIME, estimatedTime);
        cv.put(Contract.COL_ORDER_NO, orderNumber);
        if(lastCompleted != null) {
            cv.put(Contract.COL_LAST_COMPLETED, lastCompleted.getTime());
        }

        id = db.insert(Contract.TABLE_NAME, null, cv);

        ActivityLog log = new ActivityLog(this, ActivityLog.ActivityTag.CREATED);
        log.insertActivity(db);
    }

    public static void  deleteAllDrills(SQLiteDatabase db) throws DanceObjectException {
        checkWriteDatabase(db);

        db.delete(Contract.TABLE_NAME,
                null,
                null);
    }

    public static class Contract extends SubItemContractTemplate {
        public static final String TABLE_NAME = "drill_table";
        public static final String COL_INSTRUCTIONS = "instructions";
        public static final String COL_COMPLETION_CNT = "completions";
        public static final String COL_ESTIMATED_TIME = "estimatedtime";
        public static final String COL_LAST_COMPLETED = "lastcomplete";
        public static final String COL_DANCER_COUNT = "dancercount";

        public static String[] getProjection() {
            String[] retval = {_ID,
                    COL_NAME,
                    COL_STARRED,
                    COL_DATE_CREATED,
                    COL_DATE_MODIFIED,
                    COL_INSTRUCTIONS,
                    COL_COMPLETION_CNT,
                    COL_ESTIMATED_TIME,
                    COL_LAST_COMPLETED,
                    COL_DANCER_COUNT
            };
            return retval;
        }

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_INSTRUCTIONS + " TEXT, " +
                    COL_COMPLETION_CNT + " INTEGER, " +
                    COL_ESTIMATED_TIME + " INTEGER, " +
                    COL_LAST_COMPLETED + " INTEGER, " +
                    COL_DANCER_COUNT + " INTEGER, ");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }

    }


    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getTableName() {
        return Contract.TABLE_NAME;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getCompletionCount() {
        return completionCount;
    }

    public void setCompletionCount(int completionCount) {
        this.completionCount = completionCount;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Date getLastCompleted() {
        return lastCompleted;
    }

    public void setLastCompleted(Date lastCompleted) {
        this.lastCompleted = lastCompleted;
    }

    public int getDancersRequired() {
        return dancersRequired;
    }

    public void setDancersRequired(int dancersRequired) {
        this.dancersRequired = dancersRequired;
    }
}
