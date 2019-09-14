package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Lesson extends DanceObject {

    public Lesson(Date date, int duration) {
        startDateAndTime = new DateAndTime(date);
        this.duration = duration;
    }

    public Lesson(SQLiteDatabase db, Map<Long, Teacher> teacherMap, Map<Long, Location> locationMap, Cursor cursor) throws DanceObjectException {
        super(cursor);

        long teacherId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_TEACHER_ID));
        long locationId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_LOCATION_ID));
        if(teacherId != 0 || locationId != 0) {
            checkReadableDatabase(db);

            if(locationMap != null && locationMap.containsKey(locationId)) {
                location = locationMap.get(locationId);
            } else {
                Location l = Location.getLocationById(db, locationId);
                if(locationMap != null && l != null) {
                    locationMap.put(locationId, l);
                }
                location = l;
            }


            if(teacherMap != null && teacherMap.containsKey(teacherId)) {
                teacher = teacherMap.get(teacherId);
            } else {
                Teacher t = Teacher.getTeacherById(db, locationMap, teacherId);
                if(teacherMap != null && t != null) {
                    teacherMap.put(teacherId, t);
                }
                teacher = t;
            }
        }

        duration = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_DURATION));
        startDateAndTime = new DateAndTime(
                cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_START_DATE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_START_TIME)));

    }

    public static final String TYPE = "LESSON";

    private Location location;
    private Teacher teacher;
    private DateAndTime startDateAndTime;
    private int duration;


    // DATABASE FUNCTIONS

    public static Lesson getLessonById(SQLiteDatabase db, long id) {
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

            if(cursor.moveToNext()) {
                return new Lesson(db, null, null, cursor);
            } else {
                return null;
            }
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void isInsertReady(SQLiteDatabase db) throws DanceObjectException {
        if(teacher != null && teacher.getId() == 0) {
            teacher.dbInsert(db);
        }

        if(location != null && location.getId() == 0) {
            location.dbInsert(db);
        }

        if(startDateAndTime == null || duration == 0) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }
        if(startDateAndTime.getDateInt() == 0 || startDateAndTime.getTimeInt() == 0) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }
    }

    @Override
    public void isUpdateReady(SQLiteDatabase db) throws DanceObjectException {

    }

    @Override
    protected void updateContentValues(ContentValues cv) {
        if(startDateAndTime != null) {
            cv.put(Contract.COL_START_DATE, startDateAndTime.getDateInt());
            cv.put(Contract.COL_START_TIME, startDateAndTime.getTimeInt());
        }
        cv.put(Contract.COL_DURATION, duration);
        if(location != null) {
            cv.put(Contract.COL_LOCATION_ID, location.getId());
        }
        if(teacher != null) {
            cv.put(Contract.COL_TEACHER_ID, teacher.getId());
        }

    }

    public static void deleteAllLessons(SQLiteDatabase db) throws DanceObjectException {
        checkWriteDatabase(db);
        db.delete(Contract.TABLE_NAME, null,null);
    }


    public static class Contract extends ContractTemplate {

        public static final String TABLE_NAME = "LESSON_TBL";
        public static final String COL_LOCATION_ID = "LOC_ID";
        public static final String COL_TEACHER_ID = "TEACHER_ID";
        public static final String COL_START_TIME = "START_TIME";
        public static final String COL_START_DATE = "START_DATE";
        public static final String COL_DURATION = "DURATION";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_LOCATION_ID + " INTEGER, " +
                    COL_TEACHER_ID + " INTEGER, " + COL_START_TIME + " INTEGER, " +
                    COL_START_DATE + " INTEGER, " + COL_DURATION + " INTEGER, ");
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
                    COL_START_DATE,
                    COL_DURATION,
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
    public String getObjectName() {
        return "Lesson";
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

    public DateAndTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStartDateAndTime(DateAndTime startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }
}
