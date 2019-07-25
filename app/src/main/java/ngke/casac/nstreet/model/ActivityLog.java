package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.Date;

import ngke.casac.nstreet.model.template.BaseObject;
import ngke.casac.nstreet.model.template.DanceObject;

public class ActivityLog extends BaseObject {

    public ActivityLog(DanceObject object, String description) {
        date = new Date(System.currentTimeMillis());
        tableName = object.getTableName();
        refId = object.getId();
        activityDescription = description;

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



    public static class Contract implements BaseColumns {
        public static final String TABLE_NAME = "activity_log_tbl";
        public static final String COL_DATE = "date";
        public static final String COL_TABLE_NAME = "table";
        public static final String COL_REF_ID = "ref_id";
        public static final String COL_DESCRIPTION = "description";

        public String[] getProjection() {
            String[] projection = {
                    COL_DATE,
                    COL_TABLE_NAME,
                    COL_REF_ID,
                    COL_DESCRIPTION
            };
            return projection;
        }

        public String getInitSQL() {
            return "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COL_DATE + " INTEGER, " +
                    COL_TABLE_NAME + " TEXT, " +
                    COL_REF_ID + " INTEGER, " +
                    COL_DESCRIPTION + " TEXT) ";
        }

        public String getDestroySQL() {
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
