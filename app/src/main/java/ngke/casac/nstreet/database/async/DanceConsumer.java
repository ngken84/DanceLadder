package ngke.casac.nstreet.database.async;

import android.support.v4.util.Consumer;

public abstract class DanceConsumer<T> implements Consumer<T> {

    private String error = null;

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public abstract void consume(T t);

    public abstract void handleError();

    @Override
    public void accept(T t) {
        if(error == null) {
            consume(t);
        } else {
            handleError();
        }

    }
}
