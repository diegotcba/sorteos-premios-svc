package ar.org.aveit.model;

import ar.org.aveit.rest.model.SorteoResource;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by DiegoT on 14/11/2015.
 */
@Data
public class Sorteo {
    private LocalDate fecha;
    private int emision;
    private int cuponPago;
    private List<Extracto> extractos;

    public static Sorteo valueOf(SorteoResource resource) {
        Sorteo sorteo = new Sorteo();

        sorteo.setFecha(LocalDate.parse(resource.getFecha(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        sorteo.setEmision(Integer.valueOf(resource.getEmision()));
        sorteo.setCuponPago(Integer.valueOf(resource.getCuponPago()));
        sorteo.setExtractos(resource.getExtractos());

        return sorteo;
    }
}
