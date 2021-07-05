package typeqast.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import typeqast.entities.Client;

import java.math.BigInteger;

public class AddressDTO {

    private BigInteger id;

    private String country;

    private String city;

    private String street;

    private Integer number;

    @JsonIgnore
    private Client client;

    public AddressDTO(String country, String city, String street, Integer number) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public AddressDTO() {

    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

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
}
