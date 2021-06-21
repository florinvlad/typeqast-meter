package typeqast.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "address")
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

    public Address(BigInteger id) {
        this.id = id;
    }

    public Address() {

    }

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "client_id", columnDefinition = "bigint unique")
    private Client client;

    @Column(name = "country", nullable = false, columnDefinition = "varchar(25) default unknown")
    private String country;
    @Column(name = "city", nullable = false, columnDefinition = "varchar(25) default unknown")
    private String city;
    @Column(name = "street", nullable = false, columnDefinition = "varchar(25) default unknown")
    private String street;
    @Column(name = "number", nullable = false, columnDefinition = "int default 0")
    private Integer number;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

}
