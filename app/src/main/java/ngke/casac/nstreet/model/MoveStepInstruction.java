package ngke.casac.nstreet.model;

public class MoveStepInstruction {

    private int startingFoot;
    private int endingFreeFoot;
    private String stepDescription;

    public int getStartingFoot() {
        return startingFoot;
    }

    public void setStartingFoot(int startingFoot) {
        this.startingFoot = startingFoot;
    }

    public int getEndingFreeFoot() {
        return endingFreeFoot;
    }

    public void setEndingFreeFoot(int endingFreeFoot) {
        this.endingFreeFoot = endingFreeFoot;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }
}
