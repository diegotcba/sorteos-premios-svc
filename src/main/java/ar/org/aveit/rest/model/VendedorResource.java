package ar.org.aveit.rest.model;

import ar.org.aveit.model.Vendedor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by DiegoT on 15/11/2015.
 */
@Data
public class VendedorResource {
    private String nroSocio;
    private String apellido;
    private String nombre;
    private String grupo;
    private List<Integer> numeros;
    private String telefono;

    public VendedorResource() {
        this.numeros = new ArrayList<>();
    }

    public static VendedorResource valueOf(Vendedor vendedor) {
        VendedorResource resource = new VendedorResource();

        resource.setApellido(Optional.ofNullable(vendedor.getApellido()).orElse(""));
        resource.setNombre(Optional.ofNullable(vendedor.getNombre()).orElse(""));
        resource.setNroSocio(vendedor.getNroSocio() > 0 ? String.valueOf(vendedor.getNroSocio()) : "");
        resource.setGrupo(vendedor.getGrupo() > 0 ? String.valueOf(vendedor.getGrupo()) : "");
        resource.setTelefono(Optional.ofNullable(vendedor.getTelefono()).orElse(""));
        resource.setNumeros(vendedor.getNumeros());

        return resource;
    }
}
