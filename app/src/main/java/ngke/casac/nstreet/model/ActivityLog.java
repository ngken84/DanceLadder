package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ngke.casac.nstreet.model.template.BaseObject;
import ngke.casac.nstreet.model.template.DanceObject;

public class ActivityLog extends BaseObject {

    public enum ActivityTag {
        CREATED, MODIFIED, STARRED
    }

    public ActivityLog(DanceObject object, String description) {
        date = new Date(System.currentTimeMillis());
        tableName = object.getTableName();
        refId = object.getId();
        activityDescription = description;

        this.object = object;
    }

    public ActivityLog(DanceObject object, ActivityTag tag) {
        date = new Date(System.currentTimeMillis());
        tableName = object.getTableName();
        refId = object.getId();
        this.object = object;
        switch(tag) {
            case CREATED:
                activityDescription = object.getType() + " " + object.getName() + " created.";
                break;
        }
    }

    public ActivityLog (SQLiteDatabase db, Map<Long, Dance> danceMap, Map<Long, Category> categoryMap, Cursor cursor) throws DanceObjectException {
        checkReadableDatabase(db);
        date = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE)));
        activityDescription = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_DESCRIPTION));
        refId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_REF_ID));
        tableName = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_TABLE_NAME));

        switch(tableName) {
            case Dance.Contract.TABLE_NAME:
                object = new Dance(db, categoryMap, refId);
                break;
            case Category.Contract.TABLE_NAME:
                object = new Category(db, refId);
                break;
        }


    }

    private Date date;
    private String tableName;
    private long refId;
    private String activityDescription;

    private DanceObject object;

    public void insertActivity(SQLiteDatabase db) throws DanceObjectException {
        if(!isWriteDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }

        if(!isValid()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }

        ContentValues cv = new ContentValues();
        cv.put(Contract.COL_DATE, date.getTime());
        cv.put(Contract.COL_TABLE_NAME, tableName);
        cv.put(Contract.COL_REF_ID, object.getId());
        cv.put(Contract.COL_DESCRIPTION, activityDescription);

        db.insert(Contract.TABLE_NAME, null, cv);
    }

    public static List<ActivityLog> getRecentActivity(SQLiteDatabase db) throws DanceObjectException {
        if(!isReadableDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }

        List<ActivityLog> retList = new LinkedList<>();
        String[] projection = Contract.getProjection();

        String orderBy = Contract.COL_DATE + " DESC";
        Cursor cursor = db.query(Contract.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                orderBy,
                "20");

        Map<Long, Dance> danceMap = new HashMap<>();
        Map<Long, Category> categoryMap = new HashMap<>();

        while(cursor.moveToNext()) {
            retList.add(new ActivityLog(db, danceMap, categoryMap, cursor));
        }
        cursor.close();
        return retList;
    }

    public static void deleteAllActivity(SQLiteDatabase db) throws DanceObjectException {
        if(!isWriteDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }

        db.delete(Contract.TABLE_NAME, null, null);
    }

    public static class Contract implements BaseColumns {
        public static final String TABLE_NAME = "activity_log_tbl";
        public static final String COL_DATE = "activity_date";
        public static final String COL_TABLE_NAME = "table_name";
        public static final String COL_REF_ID = "ref_id";
        public static final String COL_DESCRIPTION = "description";

        public static String[] getProjection() {
            String[] projection = {
                    COL_DATE,
                    COL_TABLE_NAME,
                    COL_REF_ID,
                    COL_DESCRIPTION
            };
            return projection;
        }

        public static String getInitSQL() {
            return "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COL_DATE + " INTEGER, " +
                    COL_TABLE_NAME + " TEXT, " +
                    COL_REF_ID + " INTEGER, " +
                    COL_DESCRIPTION + " TEXT) ";
        }

        public static String getDestroySQL() {
                return "DROP TABLE IF EXISTS " + TABLE_NAME;
            }
        }

    private boolean isValid() {
        if(date == null) {
            return false;
        }
        if(activityDescription == null || activityDescription.trim().length() == 0) {
            return false;
        }
        if(tableName == null || tableName.trim().length() == 0) {
            return false;
        }
        if(refId == 0) {
            return false;
        }
        return true;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public DanceObject getObject() {
        return object;
    }

    public void setObject(DanceObject object) {
        this.object = object;
    }
}
