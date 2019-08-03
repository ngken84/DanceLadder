package ngke.casac.nstreet.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import ngke.casac.nstreet.model.template.DanceObject;
import ngke.casac.nstreet.model.template.DanceSubItem;
import ngke.casac.nstreet.model.template.SubItemContractTemplate;

public class DanceTask extends DanceSubItem {

    public static final String TYPE = "dance_task";

    private DateAndTime dateAndTime;
    private boolean completed;
    private Date completedDate;
    private String refTableName;
    private long refId;

    private DanceObject object;

    // DATABASE FUNCTIONS

    public DanceTask(SQLiteDatabase db, long id) throws DanceObjectException {
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

            if(refId > 0 && refTableName != null && !refTableName.isEmpty()) {
                switch(refTableName) {
                    case Drill.Contract.TABLE_NAME:
                        object = new Drill(db, refId);
                        break;
                }
            }
        }

    }

    public static class Contract extends SubItemContractTemplate {

        public static final String TABLE_NAME = "dance_task_tbl";

        public static final String COL_DATE = "task_date";
        public static final String COL_TIME = "task_time";
        public static final String COL_COMPLETED = "completed_flg";
        public static final String COL_COMPLETED_DATE = "completed_date";
        public static final String COL_TABLE_NAME = "table_name";
        public static final String COL_REF_ID = "ref_id";

        public static String[] getProjection() {
            String[] retval =  {_ID,
                    COL_NAME,
                    COL_STARRED,
                    COL_DATE_CREATED,
                    COL_DATE_MODIFIED,
                    COL_DATE,
                    COL_TIME,
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
