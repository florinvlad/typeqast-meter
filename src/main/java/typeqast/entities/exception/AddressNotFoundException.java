package typeqast.entities.exception;

public class AddressNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Address id not found";
    }

}