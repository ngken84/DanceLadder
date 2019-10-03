package ngke.casac.nstreet.model;

public class MoveStepInstruction {

    public MoveStepInstruction(int startingFoot, int endingFreeFoot, String stepDescription) {
        this.startingFoot = startingFoot;
        this.endingFreeFoot = endingFreeFoot;
        this.stepDescription = stepDescription;
    }

    private int startingFoot;
    private int endingFreeFoot;
    private String stepDescription;

    // GETTERS & SETTERS

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
