package ar.org.aveit.model;

import lombok.Data;

import java.util.List;

/**
 * Created by DiegoT on 14/11/2015.
 */
@Data
public class PremiosGanadores {
    private List<InformacionGanador> premios;
    private List<InformacionGanador> premios4Cifras;
    private List<InformacionGanador> premios3Cifras;
    private List<InformacionGanador> premios2Cifras;
}
