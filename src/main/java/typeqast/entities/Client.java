package typeqast.entities;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint unsigned")
    private BigInteger id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "client")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
    private Meter meter;

    public Client(BigInteger id, String name) {
        this(name);
        this.setId(id);
    }

    public Client(String name) {
        this.name = name;
    }

    public Client(BigInteger id) {
        this.id = id;
    }

    public Client() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
