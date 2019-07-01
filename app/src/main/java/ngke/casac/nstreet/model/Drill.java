package ngke.casac.nstreet.model;

import java.util.Date;

import ngke.casac.nstreet.model.template.DanceSubItem;

public class Drill extends DanceSubItem {

    public static final String TYPE = "DRILL";

    public String instructions;
    public int completionCount;
    public int estimatedTime;
    public Date lastCompleted;


    @Override
    public String getType() {
        return TYPE;
    }
}
