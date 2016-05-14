package ar.org.aveit.model;

import lombok.Data;

/**
 * Created by DiegoT on 15/11/2015.
 */
@Data
public class InformacionGanador {
    private Extracto extracto;
    private Ganador ganador;
    private Vendedor vendedor;

    public InformacionGanador() {
        this.extracto = new Extracto();
        this.ganador = new Ganador();
        this.vendedor = new Vendedor();
    }
}
