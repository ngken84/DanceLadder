package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.Map;

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

    public Drill(SQLiteDatabase db, Map<Long, Dance> danceMap, Map<Long, Category> categoryMap, Cursor cursor) throws DanceObjectException {
        super(db, danceMap, categoryMap, cursor);
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

    public static Drill getDrillById(SQLiteDatabase db, Map<Long, Dance> danceMap, Map<Long, Category> categoryMap, long id)  {
        try {
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
            try {
                if (cursor.moveToNext()) {
                    return new Drill(db, danceMap, categoryMap, cursor);
                } else {
                    throw new DanceObjectException(DanceObjectException.ERR_NOT_FOUND);
                }
            } finally {
                cursor.close();
            }
        } catch (DanceObjectException ex)  {

        }
        return null;
    }

    @Override
    protected void updateContentValuesForSubInsert(ContentValues cv) {
        cv.put(Contract.COL_INSTRUCTIONS, instructions);
        cv.put(Contract.COL_COMPLETION_CNT, completionCount);
        cv.put(Contract.COL_DANCER_COUNT, dancersRequired);
        if(lastCompleted != null) {
            cv.put(Contract.COL_LAST_COMPLETED, lastCompleted.getTime());
        }
        cv.put(Contract.COL_ESTIMATED_TIME, estimatedTime);
    }

    @Override
    protected void isInsertReady(SQLiteDatabase db) throws DanceObjectException {
        if(!isNameValid()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }

        if(!isStringValid(instructions)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }
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

    @Override
    public String getObjectName() {
        return "Drill";
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
