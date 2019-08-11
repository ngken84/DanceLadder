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
        checkReadableDatabase(db);

        id = cursor.getLong(cursor.getColumnIndexOrThrow(Contract._ID));
        name = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NAME));
        int dateInt = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_DATE));
        int timeInt = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_TIME));
        if(dateInt > 0) {
            dateAndTime = new DateAndTime(dateInt, timeInt);
        }
        refId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_REF_ID));
        if(refId > 0) {
            refTableName = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_TABLE_NAME));
        }
        completed = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_COMPLETED)) == 1;
        if(completed) {
            completedDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_COMPLETED_DATE)));
        }
        dateIsDueDate = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_IS_DUE_DATE)) == 1;

        long danceId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DANCE_ID));
        if(danceId > 0) {
            if(danceMap.containsKey(danceId)) {
                dance = danceMap.get(danceId);
            } else {
                dance = new Dance(db, categoryMap, danceId);
                danceMap.put(dance.getId(), dance);
            }
        }
        dateCreated = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_CREATED)));
        dateModified = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_MODIFIED)));
        starred = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_STARRED)) == 1;
        rating = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_RATING));
        orderNumber = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_ORDER_NO));
    }

    public static final String TYPE = "dance_task";

    private DateAndTime dateAndTime;
    private boolean completed;
    private Date completedDate;
    private String refTableName;
    private long refId;
    private boolean dateIsDueDate;

    private DanceObject object;

    // DATABASE FUNCTIONS

    public DanceTask(SQLiteDatabase db, Map<Long, Dance> danceMap, Map<Long, Category> categoryMap, long id) throws DanceObjectException {
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

        if(cursor.moveToNext()) {
            this.id = id;
            name = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NAME));
            starred = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_STARRED)) == 1;
            dateCreated = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_CREATED)));
            dateModified = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_MODIFIED)));
            int dateInt = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_DATE));
            int timeInt = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_TIME));

            dateAndTime = new DateAndTime(dateInt, timeInt);
            completed = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_COMPLETED)) == 1;
            if(completed) {
                completedDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_COMPLETED_DATE)));
            }
            refTableName = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_TABLE_NAME));
            refId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_REF_ID));
            dateIsDueDate = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_IS_DUE_DATE)) == 1;

            long danceId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DANCE_ID));
            if(danceId > 0) {
                if(danceMap != null && danceMap.containsKey(danceId)) {
                    dance = danceMap.get(danceId);
                } else {
                    Dance nDance = new Dance(db, categoryMap, danceId);
                    if(danceMap != null & nDance != null) {
                        danceMap.put(danceId, nDance);
                    }
                    dance = nDance;
                }
            }

            rating = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_RATING));
            orderNumber = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_ORDER_NO));

            if(refId > 0 && refTableName != null && !refTableName.isEmpty()) {
                switch(refTableName) {
                    case Drill.Contract.TABLE_NAME:
                        object = new Drill(db, danceMap, categoryMap, refId);
                        break;
                }
            }
        }

    }

    public void insertDanceTask(SQLiteDatabase db) throws DanceObjectException {
        checkWriteDatabase(db);

        if(id < 1) {
            throw new DanceObjectException(DanceObjectException.ERR_ALREADY_EXISTS);
        }

        if(!isValid()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }

        ContentValues cv = new ContentValues();

        long created = System.currentTimeMillis();

        dateCreated = new Date(created);
        dateModified = new Date(created);

        cv.put(Contract.COL_DATE, dateAndTime.getDateInt());
        cv.put(Contract.COL_TIME, dateAndTime.getTimeInt());
        cv.put(Contract.COL_COMPLETED, completed ? 1 : 0);
        if(completed && completedDate != null) {
            cv.put(Contract.COL_COMPLETED_DATE, completedDate.getTime());
        }
        if(refId > 0 && !refTableName.isEmpty()) {
            cv.put(Contract.COL_REF_ID, refId);
            cv.put(Contract.COL_TABLE_NAME, refTableName);
        }
        cv.put(Contract.COL_NAME, name);
        cv.put(Contract.COL_DATE_CREATED, created);
        cv.put(Contract.COL_DATE_MODIFIED, created);
        if(dance != null) {
            cv.put(Contract.COL_DANCE_ID, dance.getId());
        }
        cv.put(Contract.COL_RATING, rating);
        cv.put(Contract.COL_STARRED, starred ? 1 : 0);
        cv.put(Contract.COL_ORDER_NO, orderNumber);
        cv.put(Contract.COL_IS_DUE_DATE, dateIsDueDate ? 1 : 0);

        id = db.insert(Contract.TABLE_NAME, null, cv);
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

    private boolean isValid() {

        if(isNameValid()) {
            return true;
        }

        if(refId < 1) {
            return false;
        }

        if(refTableName == null || refTableName.isEmpty()) {
            return false;
        }
        return true;
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

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
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
