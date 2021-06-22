package typeqast.entities.response;

import org.springframework.http.HttpStatus;
import typeqast.entities.Address;

/**
 * Wrapper object for {@link Address}
 */
public class AddressResponse {

    private Address address;
    private HttpStatus status;

    public AddressResponse(Address address, HttpStatus status) {
        this.address = address;
        this.status = status;
    }

    public AddressResponse() {

    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
