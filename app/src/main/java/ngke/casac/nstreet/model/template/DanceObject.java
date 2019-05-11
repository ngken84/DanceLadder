package ngke.casac.nstreet.model.template;

import java.util.Date;

public abstract class DanceObject {

    protected int id;
    protected String name;
    protected boolean starred;
    protected Date dateCreated;
    protected Date dateModified;

    public DanceObject() {
        dateCreated = new Date();
        dateModified = new Date();
    }

    public DanceObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getStarred() { return starred; }

    public void setStarred(boolean value) { starred = value; }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
}
