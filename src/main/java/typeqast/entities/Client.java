package typeqast.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "client")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}
