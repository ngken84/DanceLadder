package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Lesson extends DanceObject {

    public static final String TYPE = "LESSON";

    private Location location;
    private Teacher teacher;
    private Date startTime;
    private Date endTime;


    // DATABASE FUNCTIONS

    public Lesson(SQLiteDatabase db, long id) throws DanceObjectException {
        if(id < 1) {
            throw new DanceObjectException(DanceObjectException.ERR_NOT_FOUND);
        }

        if(isReadableDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }
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
                null);

        if(cursor.moveToNext()) {
            this.id = id;
            name = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NAME));
            starred = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_STARRED)) == 1;
            long teacherId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_TEACHER_ID));
            if(teacherId > 0) {
                teacher = new Teacher(db, teacherId);
            }
            long locationId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_LOCATION_ID));
            if(locationId > 0) {
                location = new Location(db, locationId);
            }
            dateCreated = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_CREATED)));
            dateModified = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_MODIFIED)));
            long startTimeLong = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_START_TIME));
            if(startTimeLong > 0) {
                startTime = new Date(startTimeLong);
            }
            long endTimeLong = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_END_TIME));
            if(endTimeLong > 0) {
                endTime = new Date(endTimeLong);
            }
        } else {
            throw new DanceObjectException(DanceObjectException.ERR_NOT_FOUND);
        }
    }

    public void insertLesson(SQLiteDatabase db) throws DanceObjectException {
        if(isWriteDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }

        if(id > 0) {
            throw new DanceObjectException(DanceObjectException.ERR_ALREADY_EXISTS);
        }

        if(startTime == null || endTime == null) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }

        ContentValues cv = new ContentValues();
        cv.put(Contract.COL_NAME, name);
        if(teacher != null) {
            cv.put(Contract.COL_TEACHER_ID, teacher.getId());
        }
        if(location != null) {
            cv.put(Contract.COL_LOCATION_ID, location.getId());
        }
        long time = System.currentTimeMillis();
        cv.put(Contract.COL_DATE_CREATED, time);
        cv.put(Contract.COL_DATE_MODIFIED, time);
        cv.put(Contract.COL_START_TIME, startTime.getTime());
        cv.put(Contract.COL_END_TIME, endTime.getTime());

        id = db.insert(Contract.TABLE_NAME, null, cv);

    }

    public static class Contract extends ContractTemplate {

        public static final String TABLE_NAME = "LESSON_TBL";
        public static final String COL_LOCATION_ID = "LOC_ID";
        public static final String COL_TEACHER_ID = "TEACHER_ID";
        public static final String COL_START_TIME = "START_TIME";
        public static final String COL_END_TIME = "END_TIME";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_LOCATION_ID + " INTEGER, " +
                    COL_TEACHER_ID + " INTEGER, " + COL_START_TIME + " INTEGER, " +
                    COL_END_TIME + " INTEGER, " );
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }

        public static String[] getProjection() {
            String[] projection = {
                    _ID,
                    COL_LOCATION_ID,
                    COL_TEACHER_ID,
                    COL_NAME,
                    COL_START_TIME,
                    COL_END_TIME,
                    COL_DATE_CREATED,
                    COL_STARRED,
                    COL_DATE_MODIFIED
            };
            return projection;
        }
    }

    private List<DanceObject> contents = new LinkedList<>();

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getTableName() {
        return Contract.TABLE_NAME;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
