package ngke.casac.nstreet.model;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Location extends DanceObject {

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

    @Override
    public String getType() {
        return TYPE;
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
