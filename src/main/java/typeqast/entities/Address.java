package typeqast.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "address")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint unsigned")
    private BigInteger id;

    public Address(String country, String city, String street, Integer number) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "client_id", columnDefinition = "bigint unique")
    private Client client;

    @Column(name = "country", nullable = false, columnDefinition = "varchar(25)")
    private String country;
    @Column(name = "city", nullable = false, columnDefinition = "varchar(25)")
    private String city;
    @Column(name = "street", nullable = false, columnDefinition = "varchar(25)")
    private String street;
    @Column(name = "number", nullable = false, columnDefinition = "int default 0")
    private Integer number;

}
