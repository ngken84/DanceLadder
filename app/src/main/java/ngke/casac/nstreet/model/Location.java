package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Location extends DanceObject {

    public Location(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(Contract._ID));
        name = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NAME));
        address = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_ADDRESS));
        city = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_CITY));
        state = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_STATE));
        zip = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_ZIP));
        dateCreated = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_CREATED)));
        dateModified = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_MODIFIED)));
        starred = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_STARRED)) == 1;
    }

    public static final String TYPE = "LOCATION";

    private String address;
    private String city;
    private String state;
    private String zip;

    public static class Contract extends ContractTemplate {

        public static String TABLE_NAME = "location_tbl";
        public static String COL_ADDRESS = "address";
        public static String COL_CITY = "city";
        public static String COL_STATE = "state";
        public static String COL_ZIP = "zip";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_ADDRESS + " TEXT, " + COL_CITY + " TEXT, "
            + COL_STATE + " TEXT, " + COL_ZIP + " TEXT, ");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }

        public static String[] getProjection() {
            String[] projection = {
                    _ID,
                    COL_NAME,
                    COL_ADDRESS,
                    COL_CITY,
                    COL_STATE,
                    COL_ZIP,
                    COL_DATE_CREATED,
                    COL_STARRED,
                    COL_DATE_MODIFIED
            };
            return projection;
        }
    }

    // Database Functions

    public Location(SQLiteDatabase db, long id) throws DanceObjectException {
        if(!isReadableDatabase(db)) {
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
            address = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_ADDRESS));
            city = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_CITY));
            state = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_STATE));
            zip = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_ZIP));
            cursor.close();
        } else {
            cursor.close();
            throw new DanceObjectException(DanceObjectException.ERR_NOT_FOUND);
        }

    }

    public boolean doesLocationExistInDB(SQLiteDatabase db, String locationName) throws DanceObjectException {
        if(!isReadableDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }

        String[] projection = {Contract._ID};
        String selection = "UPPER(" + Contract.COL_NAME + ") = UPPER(?)";
        String[] selectionArgs = {locationName};

        Cursor cursor = db.query(Contract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);

        if(cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;

    }

    public void insertLocation(SQLiteDatabase db) throws DanceObjectException {
        if(!isWriteDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }

        if(!isNameValid()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }

        if(doesLocationExistInDB(db, name)) {
            throw new DanceObjectException(DanceObjectException.ERR_ALREADY_EXISTS);
        }

        long dateCreated = System.currentTimeMillis();
        setDateCreated(new Date(dateCreated));
        setDateModified(new Date(dateCreated));

        ContentValues cv = new ContentValues();
        cv.put(Contract.COL_NAME, name);
        cv.put(Contract.COL_ADDRESS, address);
        cv.put(Contract.COL_CITY, city);
        cv.put(Contract.COL_STATE, state);
        cv.put(Contract.COL_ZIP, zip);

        id = db.insert(Contract.TABLE_NAME, null, cv);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getTableName() {
        return Contract.TABLE_NAME;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
