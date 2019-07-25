package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Teacher extends DanceObject {

    public final static String TYPE = "TEACHER";

    private String firstName;
    private Location location;
    private String email;
    private int phoneNumber;

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

    public Teacher(SQLiteDatabase db, long id) throws DanceObjectException {
        if(!isReadableDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }

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

        if(cursor.moveToNext()) {
            this.id = id;
            name = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NAME));
            firstName = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_FIRST_NAME));
            long locationId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_LOCATION_ID));
            if(locationId > 0) {
                location = new Location(db, locationId);
            }
            email = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_EMAIL));
            phoneNumber = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_PHONE));
            dateCreated = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_CREATED)));
            dateModified = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_MODIFIED)));
            starred = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_STARRED)) == 1;
            cursor.close();
        } else {
            cursor.close();
            throw new DanceObjectException(DanceObjectException.ERR_NOT_FOUND);
        }

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

    public void insertTeacher(SQLiteDatabase db) throws DanceObjectException {
        if(!isWriteDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }
        if(doesTeacherExist(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_ALREADY_EXISTS);
        }
        if(!isTeacherValid()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }
        ContentValues cv = new ContentValues();
        cv.put(Contract.COL_NAME, name);
        cv.put(Contract.COL_FIRST_NAME, firstName);
        cv.put(Contract.COL_EMAIL, email);
        if(location != null) {
            cv.put(Contract.COL_LOCATION_ID, location.getId());
        }
        cv.put(Contract.COL_PHONE, phoneNumber);
        id = db.insert(Contract.TABLE_NAME, null, cv);
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
                    COL_FIRST_NAME + " TEXT, " + COL_EMAIL + " TEXT, " + COL_PHONE + "INTEGER, ");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }


    }

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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phonenumber) {
        this.phoneNumber = phonenumber;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getTableName() {
        return Contract.TABLE_NAME;
    }
}
