package ar.org.aveit.service;

import ar.org.aveit.model.PremiosGanadores;
import ar.org.aveit.model.Sorteo;

/**
 * Created by DiegoT on 14/11/2015.
 */
public interface SorteosPremiosService {
    public String getEcho(String input);
    public String getStatus();
    public PremiosGanadores getGanadoresSorteo(String fecha, Sorteo sorteo);
}
