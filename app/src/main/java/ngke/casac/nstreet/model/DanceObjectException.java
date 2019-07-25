package ngke.casac.nstreet.model;

public class DanceObjectException extends Exception {

    private String message;

    public DanceObjectException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static final String ERR_INVALID_DB = "Database is usable";
    public static final String ERR_ALREADY_EXISTS = "Name is taken";
    public static final String ERR_INVALID_OBJECT = "Object is invalid";
    public static final String ERR_NOT_FOUND = "NO OBJECT FOUND";
}
