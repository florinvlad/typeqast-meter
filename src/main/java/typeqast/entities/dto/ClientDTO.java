package typeqast.entities.dto;

import typeqast.entities.Address;
import typeqast.entities.Meter;

import java.math.BigInteger;

public class ClientDTO {

    private BigInteger id;

    private String name;

    private Address address;

    private Meter meter;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }
}
