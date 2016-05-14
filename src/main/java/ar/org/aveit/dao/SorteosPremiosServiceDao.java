package ar.org.aveit.dao;

import ar.org.aveit.model.Vendedor;

import java.util.List;

/**
 * Created by DiegoT on 14/11/2015.
 */
public interface SorteosPremiosServiceDao {
    public List<Vendedor> getGanadoresNCifras(int anio, int cuponPago, int cifras, int numero);
    public List<Vendedor> getGanadoresSorteo(int anio, int cuponPago, List<String> numeros);
    public String getDBStatus();
}
