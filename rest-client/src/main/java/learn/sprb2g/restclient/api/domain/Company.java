package learn.sprb2g.restclient.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Company implements Serializable {
    private String name;
    private String catchPhrase;
    private String bs;
}
