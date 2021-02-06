package learn.sprb2g.restclient.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Geo implements Serializable {
    private String lat;
    private String lng;
}
