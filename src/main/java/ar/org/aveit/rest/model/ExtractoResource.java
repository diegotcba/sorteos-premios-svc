package ar.org.aveit.rest.model;

import ar.org.aveit.model.Extracto;
import lombok.Data;

/**
 * Created by DiegoT on 18/11/2015.
 */
@Data
public class ExtractoResource {
    private String premio;
    private String numero;

    public ExtractoResource() {
        this.premio = "";
        this.numero = "";
    }

    public static ExtractoResource valueOf(Extracto extracto) {
        ExtractoResource resource = new ExtractoResource();

        resource.setPremio(extracto.getPremio() > 0 ? String.valueOf(extracto.getPremio()) : "");
        resource.setPremio(extracto.getNumero() > 0 ? String.valueOf(extracto.getNumero()) : "");

        return resource;
    }
}
