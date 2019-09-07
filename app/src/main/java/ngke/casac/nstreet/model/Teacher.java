package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.Map;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Teacher extends DanceObject {

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        name = lastName;
    }

    public Teacher(SQLiteDatabase db, Map<Long, Location> locationMap, Cursor cursor) {
        super(cursor);
        firstName = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_FIRST_NAME));
        email = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_EMAIL));
        phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_PHONE));
        long locationId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_LOCATION_ID));

        if(locationId > 0) {
            if (locationMap != null && locationMap.containsKey(locationId)) {
                location = locationMap.get(locationId);
            } else {
                Location l = Location.getLocationById(db, locationId);
                if (locationMap != null && l != null) {
                    locationMap.put(locationId, l);
                }
                location = l;
            }
        }
    }

    public final static String TYPE = "TEACHER";

    private String firstName;
    private Location location;
    private String email;
    private String phoneNumber;

    public boolean isTeacherValid() {
        if(!isNameValid()) {
            return false;
        }
        if(firstName == null || firstName.trim().length() == 0) {
            return false;
        }
        return true;
    }


    // DATABASE FUNCTIONS

    public static Teacher getTeacherById(SQLiteDatabase db, Map<Long, Location> locationMap, long id) {
        try {
            checkReadableDatabase(db);

            String[] projection = Contract.getProjection();
            String selection = Contract._ID + " = ? ";
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
                    return new Teacher(db, locationMap, cursor);
                }
            } finally {
                cursor.close();
            }
        } catch (DanceObjectException ex) {

        }
        return null;
    }

    public static Teacher getTeacherByName(SQLiteDatabase db, String firstName, String lastName) {
        try {
            checkReadableDatabase(db);

            String[] projection = Contract.getProjection();
            String selection = "UPPER(" + Contract.COL_FIRST_NAME + ") = UPPER(?) AND UPPER(" + Contract.COL_NAME + ") = UPPER(?)";
            String[] selectionArgs = {firstName, lastName};

            Cursor cursor = db.query(
                    Contract.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);

            if(cursor.moveToNext()) {
                return new Teacher(db, null, cursor);
            } else {
                return null;
            }
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean doesTeacherExist(SQLiteDatabase db) throws DanceObjectException {
        if(!isReadableDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }
        String[] projection = {Contract._ID};
        String selection = "UPPER(" + Contract.COL_FIRST_NAME + ") = UPPER(?) " +
                "AND UPPER("+ Contract.COL_NAME + ") = UPPER(?)";
        String[] selectionArgs = {firstName, name};

        Cursor cursor = db.query(Contract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if(cursor.moveToNext()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    @Override
    protected void isInsertReady(SQLiteDatabase db) throws DanceObjectException {
        if(!isTeacherValid()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }
        if(doesTeacherExist(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_ALREADY_EXISTS);
        }
    }

    @Override
    public void isUpdateReady(SQLiteDatabase db) throws DanceObjectException {

    }

    @Override
    protected void updateContentValues(ContentValues cv) {
        cv.put(Contract.COL_FIRST_NAME, firstName);
        cv.put(Contract.COL_EMAIL, email);
        cv.put(Contract.COL_PHONE, phoneNumber);
        if(location != null) {
            cv.put(Contract.COL_LOCATION_ID, location.getId());
        }
    }

    public static void deleteAllTeachers(SQLiteDatabase db) throws DanceObjectException {
        checkWriteDatabase(db);

        db.delete(Contract.TABLE_NAME, null, null);
    }

    public static class Contract extends ContractTemplate {

        public static String TABLE_NAME = "teacher_tbl";
        public static String COL_LOCATION_ID = "location_id";
        public static String COL_FIRST_NAME = "first_name";
        public static String COL_EMAIL = "email";
        public static String COL_PHONE = "phone";

        public static String[] getProjection() {
            String[] projection = {
                    _ID,
                    COL_NAME,
                    COL_LOCATION_ID,
                    COL_FIRST_NAME,
                    COL_EMAIL,
                    COL_PHONE,
                    COL_STARRED,
                    COL_DATE_CREATED,
                    COL_DATE_MODIFIED
            };
            return projection;
        }

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_LOCATION_ID + " INTEGER, " +
                    COL_FIRST_NAME + " TEXT, " + COL_EMAIL + " TEXT, " + COL_PHONE + " TEXT, ");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }


    }

    // BOILERPLATE

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
        return "Location";
    }

    // GETTERS & SETTERS

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phoneNumber = phonenumber;
    }


}
