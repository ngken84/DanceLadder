package ngke.casac.nstreet.model.template;

import java.util.Comparator;

public abstract class DanceSubItem extends DanceObject {

    int orderNumber;

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public static class DanceSubItemComparator implements Comparator<DanceSubItem> {

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
