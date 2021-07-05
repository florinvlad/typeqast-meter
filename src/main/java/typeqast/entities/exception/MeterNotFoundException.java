package typeqast.entities.exception;

public class MeterNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Meter id not found";
    }

}
