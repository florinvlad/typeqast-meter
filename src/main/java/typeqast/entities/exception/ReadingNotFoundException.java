package typeqast.entities.exception;

public class ReadingNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Reading id not found";
    }

}