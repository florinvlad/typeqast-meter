package typeqast.entities.response;

import org.springframework.http.HttpStatus;
import typeqast.entities.Client;

/**
 * Wrapper object for {@link Client}
 */
public class ClientResponse {

    private Client client;
    private HttpStatus status;

    public ClientResponse(Client client, HttpStatus status) {
        this.client = client;
        this.status = status;
    }

    public ClientResponse() {

    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
