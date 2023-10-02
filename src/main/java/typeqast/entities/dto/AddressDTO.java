package typeqast.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import typeqast.entities.Client;

import java.math.BigInteger;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
