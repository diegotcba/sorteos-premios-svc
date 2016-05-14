package ar.org.aveit.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DiegoT on 14/11/2015.
 */
@Data
public class Vendedor {
    private int nroSocio;
    private String apellido;
    private String nombre;
    private int grupo;
    private List<Integer> numeros;
    private String telefono;

    public Vendedor() {
        this.numeros = new ArrayList<>();
    }
}
