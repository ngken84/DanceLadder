package ngke.casac.nstreet.model;

import java.util.List;

import ngke.casac.nstreet.model.template.DanceSubItem;

public class DanceMove extends DanceSubItem {

    private String description;
    private DanceMove parentMove;

    private List<DanceSubItem> subItems;
    private List<DanceMoveStep> moveSteps;

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
