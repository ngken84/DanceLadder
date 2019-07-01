package ngke.casac.nstreet.model;

import android.provider.BaseColumns;

import java.util.List;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceSubItem;
import ngke.casac.nstreet.model.template.SubItemContractTemplate;

public class DanceMove extends DanceSubItem {

    public static final String TYPE = "DANCE_MOVE";

    private String description;
    private DanceMove parentMove;

    private List<DanceSubItem> subItems;
    private List<DanceMoveStep> moveSteps;

    @Override
    public String getType() {
        return TYPE;
    }

    public static class Contract extends SubItemContractTemplate {

        public static final String TABLE_NAME = "dance_move_template";
        public static final String COL_PARENT_MOVE_ID = "parent_move_id";
        public static final String COL_DESC = "description";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_PARENT_MOVE_ID + " INTEGER, " +
                    COL_DESC + " TEXT, ");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }

        public static String[] getProjection() {
            String[] projection = {
                    _ID,
                    COL_DANCE_ID,
                    COL_ORDER_NO,
                    COL_RATING,
                    COL_NAME,
                    COL_PARENT_MOVE_ID,
                    COL_DESC,
                    COL_DATE_CREATED,
                    COL_STARRED,
                    COL_DATE_MODIFIED
            };
            return projection;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DanceMove getParentMove() {
        return parentMove;
    }

    public void setParentMove(DanceMove parentMove) {
        this.parentMove = parentMove;
    }
}
