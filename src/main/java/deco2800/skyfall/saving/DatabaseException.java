package deco2800.skyfall.saving;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String s, Exception e){
        super(s, e);
    }
}
