package ngke.casac.nstreet.model;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Teacher extends DanceObject {

    public final static String TYPE = "TEACHER";

    private String firstName;
    private Location location;
    private String email;
    private int phoneNumber;

    public static class Contract extends ContractTemplate {

        public static String TABLE_NAME = "teacher_tbl";
        public static String COL_LOCATION_ID = "location_id";
        public static String COL_FIRST_NAME = "first_name";
        public static String COL_EMAIL = "email";
        public static String COL_PHONE = "phone";

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
}
