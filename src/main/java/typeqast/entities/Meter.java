package typeqast.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meter")
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint unsigned")
    private BigInteger id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "client_id", columnDefinition = "bigint unique")
    private Client client;

    @OneToMany(mappedBy = "meter")
    private List<Reading> readings;

    private String name;

    public Meter() {

    }

    public Meter(BigInteger id) {
        this.id = id;
    }

    public Meter(String name) {
        this.name = name;
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

    public List<Reading> getReadings() {
        return readings;
    }

    public void addReading(Reading reading) {
        if (readings == null) {
            readings = new ArrayList<>();
        }
        readings.add(reading);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReadings(List<Reading> readings) {
        this.readings = readings;
    }
}
