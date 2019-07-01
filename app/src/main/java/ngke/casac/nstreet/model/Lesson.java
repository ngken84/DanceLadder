package ngke.casac.nstreet.model;

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
}
