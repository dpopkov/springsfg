package learn.sprb2g.restclient.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Address implements Serializable {
    private String street;
    private String suite;
    private String city;
    private String zipCode;
    private Geo geo;
}
