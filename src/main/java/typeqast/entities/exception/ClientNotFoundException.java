package typeqast.entities.exception;

public class ClientNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Client id not found";
    }

}
