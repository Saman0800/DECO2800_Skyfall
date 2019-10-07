package deco2800.skyfall.saving;

public class LoadException extends Exception {


    public LoadException() {
    }

    public LoadException(String s) {
        super(s);
    }
    public LoadException(String s, Exception e) {
        super(s, e);
    }
}