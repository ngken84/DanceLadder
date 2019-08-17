package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ngke.casac.nstreet.model.template.DanceObject;
import ngke.casac.nstreet.model.template.DanceSubItem;
import ngke.casac.nstreet.model.template.SubItemContractTemplate;

public class DanceTask extends DanceSubItem {

    public DanceTask(SQLiteDatabase db, Map<Long, Dance> danceMap, Map<Long, Category> categoryMap, Cursor cursor) throws DanceObjectException {
        super(db, danceMap, categoryMap, cursor);

        int dateInt = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_DATE));
        int timeInt = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_TIME));
        if(dateInt > 0) {
            dateAndTime = new DateAndTime(dateInt, timeInt);
        }
        long refId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_REF_ID));
        if(refId > 0) {
            refTableName = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_TABLE_NAME));
        }
        completed = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_COMPLETED)) == 1;
        if(completed) {
            completedDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_COMPLETED_DATE)));
        }
        dateIsDueDate = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_IS_DUE_DATE)) == 1;
    }

    public static final String TYPE = "dance_task";

    private DateAndTime dateAndTime;
    private boolean completed;
    private Date completedDate;
    private boolean dateIsDueDate;
    private String refTableName;


    private DanceObject object;

    // DATABASE FUNCTIONS

    public static DanceTask getDanceTaskById(SQLiteDatabase db, Map<Long, Dance> danceMap, Map<Long, Category> categoryMap, long id) throws DanceObjectException {
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
                    return new DanceTask(db, danceMap, categoryMap, cursor);
                }
            } finally {
                cursor.close();
            }

        } catch (DanceObjectException ex) {

        }
        return null;

    }

    public List<DanceTask> getDanceTasksForDate(SQLiteDatabase db, int dateInt) throws DanceObjectException {
        checkReadableDatabase(db);

        String[] projection = Contract.getProjection();
        String selection = Contract.COL_DATE + " = ?";
        String[] selectionArgs = {Integer.toString(dateInt)};

        Cursor cursor = db.query(Contract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                Contract.COL_TIME);

        List<DanceTask> retList = new LinkedList<>();
        Map<Long, Dance> danceMap = new HashMap<>();
        Map<Long, Category> catMap = new HashMap<>();
        while (cursor.moveToNext()) {
            retList.add(new DanceTask(db, danceMap, catMap, cursor));
        }
        cursor.close();
        return retList;
    }

    @Override
    protected void updateContentValuesForSubItem(ContentValues cv) {
        cv.put(Contract.COL_DATE, dateAndTime.getDateInt());
        cv.put(Contract.COL_TIME, dateAndTime.getTimeInt());
        cv.put(Contract.COL_IS_DUE_DATE, dateIsDueDate ? 1 : 0);
        if(object != null) {
            cv.put(Contract.COL_REF_ID, object.getId());
            cv.put(Contract.COL_TABLE_NAME, object.getTableName());
        }
        cv.put(Contract.COL_COMPLETED, completed ? 1 : 0);
        if(completed && completedDate != null) {
            cv.put(Contract.COL_COMPLETED_DATE, completedDate.getTime());
        }
    }

    @Override
    protected void isInsertReady(SQLiteDatabase db) throws DanceObjectException {
        if(dateAndTime == null) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }
        if(!dateAndTime.hasDate()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }
        if(!(isNameValid() || object != null)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }
    }

    @Override
    public void isUpdateReady(SQLiteDatabase db) throws DanceObjectException {

    }

    public static class Contract extends SubItemContractTemplate {

        public static final String TABLE_NAME = "dance_task_tbl";

        public static final String COL_DATE = "task_date";
        public static final String COL_TIME = "task_time";
        public static final String COL_COMPLETED = "completed_flg";
        public static final String COL_COMPLETED_DATE = "completed_date";
        public static final String COL_TABLE_NAME = "table_name";
        public static final String COL_REF_ID = "ref_id";
        public static final String COL_IS_DUE_DATE = "is_due_date";

        public static String[] getProjection() {
            String[] retval =  {_ID,
                    COL_NAME,
                    COL_STARRED,
                    COL_DATE_CREATED,
                    COL_DATE_MODIFIED,
                    COL_DATE,
                    COL_TIME,
                    COL_IS_DUE_DATE,
                    COL_COMPLETED,
                    COL_COMPLETED_DATE,
                    COL_TABLE_NAME,
                    COL_REF_ID };
            return retval;
        }

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME,
                    COL_DATE + " INTEGER, " +
                    COL_TIME + " INTEGER, " +
                    COL_IS_DUE_DATE + "INTEGER, " +
                    COL_COMPLETED + " INTEGER, " +
                    COL_COMPLETED_DATE + " INTEGER, " +
                    COL_TABLE_NAME + " TEXT, " +
                    COL_REF_ID + " INTEGER, ");
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
        return "Task";
    }

    public DateAndTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(DateAndTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public DanceObject getObject() {
        return object;
    }

    public void setObject(DanceObject object) {
        this.object = object;
    }

    public String getRefTableName() {
        return refTableName;
    }

    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }
}
