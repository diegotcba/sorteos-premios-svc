package ar.org.aveit.rest.model;

import ar.org.aveit.model.Extracto;
import ar.org.aveit.model.Sorteo;
import lombok.Data;

import java.util.List;

/**
 * Created by DiegoT on 14/11/2015.
 */
@Data
public class SorteoResource {
    private String fecha;
    private String emision;
    private String cuponPago;
    private List<Extracto> extractos;

    public static SorteoResource valueOf(Sorteo sorteo) {
        SorteoResource resource = new SorteoResource();

        resource.setFecha(sorteo.getFecha().toString());
        resource.setEmision(String.valueOf(sorteo.getEmision()));
        resource.setCuponPago(String.valueOf(sorteo.getCuponPago()));
        resource.setExtractos(sorteo.getExtractos());

        return resource;
    }
}
