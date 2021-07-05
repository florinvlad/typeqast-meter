package typeqast.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import typeqast.entities.Client;
import typeqast.entities.Reading;

import java.math.BigInteger;
import java.util.List;

public class MeterDTO {

    private BigInteger id;

    @JsonIgnore
    private Client client;

    private List<Reading> readings;

    private String name;

    public MeterDTO(String name) {
        this.name = name;
    }

    public MeterDTO() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Reading> getReadings() {
        return readings;
    }

    public void setReadings(List<Reading> readings) {
        this.readings = readings;
    }
}
