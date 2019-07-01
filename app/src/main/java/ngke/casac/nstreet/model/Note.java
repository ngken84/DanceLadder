package ngke.casac.nstreet.model;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceSubItem;

public class Note extends DanceSubItem {

    private String note;

    public static final String TYPE = "NOTE";

    @Override
    public String getType() {
        return TYPE;
    }

    public static class Contract extends ContractTemplate {

        public static final String TABLE_NAME = "note_table";
        public static final String COL_NOTE = "note";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_NOTE + " TEXT, ");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }

        public static String[] getProjection() {
            String[] projection = {
                    _ID,
                    COL_NAME,
                    COL_NOTE,
                    COL_DATE_CREATED,
                    COL_STARRED,
                    COL_DATE_MODIFIED
            };
            return projection;
        }

    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
