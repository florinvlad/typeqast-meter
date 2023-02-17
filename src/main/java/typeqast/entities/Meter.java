package typeqast.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meter")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public Meter(BigInteger id) {
        this.id = id;
    }

    public Meter(String name) {
        this.name = name;
    }


    public void addReading(Reading reading) {
        if (readings == null) {
            readings = new ArrayList<>();
        }
        readings.add(reading);
    }
}
