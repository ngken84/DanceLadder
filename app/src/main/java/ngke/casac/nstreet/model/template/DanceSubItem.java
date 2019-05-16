package ngke.casac.nstreet.model.template;

import java.util.Comparator;

import ngke.casac.nstreet.model.Dance;

public abstract class DanceSubItem extends DanceObject {

    private int orderNumber;
    private int rating;
    private Dance dance;

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Dance getDance() {
        return dance;
    }

    public void setDance(Dance dance) {
        this.dance = dance;
    }

    public static class DanceSubItemOrderComparator implements Comparator<DanceSubItem> {

        @Override
        public int compare(DanceSubItem t0, DanceSubItem t1) {
            return t0.getOrderNumber() - t1.getOrderNumber();
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}
