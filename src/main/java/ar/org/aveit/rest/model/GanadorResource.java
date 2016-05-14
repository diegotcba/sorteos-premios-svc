package ar.org.aveit.rest.model;

import ar.org.aveit.model.Ganador;
import lombok.Data;

import java.util.Optional;

/**
 * Created by DiegoT on 15/11/2015.
 */
@Data
public class GanadorResource {
    private String apellido;
    private String nombre;
    private String dni;
    private String domicilio;

    public GanadorResource() {
        this.apellido = "";
        this.nombre = "";
        this.dni = "";
        this.domicilio = "";
    }

    public static GanadorResource valueOf(Ganador ganador) {
        GanadorResource resource = new GanadorResource();
        resource.setApellido(Optional.ofNullable(ganador.getApellido()).orElse(""));
        resource.setNombre(Optional.ofNullable(ganador.getNombre()).orElse(""));
        resource.setDni(ganador.getDni() > 0 ? String.valueOf(ganador.getDni()) : "");
        resource.setDomicilio(Optional.ofNullable(ganador.getDomicilio()).orElse(""));

        return resource;
    }
}
