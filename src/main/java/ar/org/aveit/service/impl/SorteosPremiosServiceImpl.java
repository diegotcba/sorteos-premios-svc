package ar.org.aveit.service.impl;

import ar.org.aveit.dao.SorteosPremiosServiceDao;
import ar.org.aveit.model.*;
import ar.org.aveit.service.SorteosPremiosService;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DiegoT on 14/11/2015.
 */
public class SorteosPremiosServiceImpl implements SorteosPremiosService {
    private SorteosPremiosServiceDao daoService;

    @Override
    public String getEcho(String input) {
        return input;
    }

    @Override
    public String getStatus() {
        return daoService.getDBStatus();
    }

    @Override
    public PremiosGanadores getGanadoresSorteo(String fecha, Sorteo sorteo) {
        int cuponPago = sorteo.getCuponPago();
        int anio = sorteo.getFecha().getYear();
        int numero = sorteo.getExtractos().get(0).getNumero();

        List<String> numeros = new ArrayList<String>();

        sorteo.getExtractos().stream().forEach(extracto -> {
            numeros.add(String.valueOf(extracto.getNumero()));
        });

        List<Vendedor> premios = daoService.getGanadoresSorteo(anio, cuponPago, numeros);
        List<Vendedor> premios4Cifras = getGanadores4Cifras(anio, cuponPago, numero);
        List<Vendedor> premios3Cifras = getGanadores3Cifras(anio, cuponPago, numero);
        List<Vendedor> premios2Cifras = getGanadores2Cifras(anio, cuponPago, numero);

        return procesarPremios(sorteo, premios, premios4Cifras, premios3Cifras, premios2Cifras);
    }

    private PremiosGanadores procesarPremios(Sorteo sorteo, List<Vendedor> premios, List<Vendedor> premios4Cifras,
                                             List<Vendedor> premios3Cifras, List<Vendedor> premios2Cifras) {
        PremiosGanadores premiosGanadores = new PremiosGanadores();

        List<InformacionGanador> premiosOrdenados = new ArrayList<>();
        InformacionGanador info;
        final String numeroString = new String(String.valueOf(sorteo.getExtractos().get(0).getNumero()));
        final Integer numero4Cifras = Integer.parseInt(numeroString.substring((numeroString.length() - 4)));
        //numeroString = String.valueOf(sorteo.getExtractos().get(0).getNumero());
        final Integer numero3Cifras = Integer.parseInt(numeroString.substring((numeroString.length() - 3)));
        //numeroString = String.valueOf(sorteo.getExtractos().get(0).getNumero());
        final Integer numero2Cifras = Integer.parseInt(numeroString.substring((numeroString.length() - 2)));

        for (int i = 0; i < sorteo.getExtractos().size(); i++) {
            info = new InformacionGanador();
            info.setExtracto(sorteo.getExtractos().get(i));
            info.setGanador(new Ganador());
            info.setVendedor(new Vendedor());

//            premios.stream().forEach(vendedor -> {
//                if (vendedor.getNumeros().contains(Integer.valueOf(info.getExtracto().getNumero()))) {
//                    info.setVendedor(vendedor);
//                }
//            });

            for (Vendedor v : premios) {
                if (v.getNumeros().contains(Integer.valueOf(info.getExtracto().getNumero()))) {
                    info.setVendedor(v);
                }
            }

            premiosOrdenados.add(info);
        }

        List<InformacionGanador> premios4CifrasOrdenados = new ArrayList<>();

        premios4Cifras.stream().forEach(vendedor -> {
            InformacionGanador aux = new InformacionGanador();
            aux.setExtracto(sorteo.getExtractos().get(0));
            //aux.getExtracto().setNumero(numero4Cifras);
            aux.setVendedor(vendedor);
            aux.setGanador(new Ganador());

            premios4CifrasOrdenados.add(aux);
        });

        List<InformacionGanador> premios3CifrasOrdenados = new ArrayList<>();

        premios3Cifras.stream().forEach(vendedor -> {
            InformacionGanador aux2 = new InformacionGanador();
            aux2.setExtracto(sorteo.getExtractos().get(0));
            //aux2.getExtracto().setNumero(numero3Cifras);
            aux2.setVendedor(vendedor);
            aux2.setGanador(new Ganador());

            premios3CifrasOrdenados.add(aux2);
        });

        List<InformacionGanador> premios2CifrasOrdenados = new ArrayList<>();

        premios2Cifras.stream().forEach(vendedor -> {
            InformacionGanador aux3 = new InformacionGanador();
            aux3.setExtracto(sorteo.getExtractos().get(0));
            //aux3.getExtracto().setNumero(numero2Cifras);
            aux3.setVendedor(vendedor);
            aux3.setGanador(new Ganador());

            premios2CifrasOrdenados.add(aux3);
        });


        premiosGanadores.setPremios(premiosOrdenados);
        premiosGanadores.setPremios4Cifras(premios4CifrasOrdenados);
        premiosGanadores.setPremios3Cifras(premios3CifrasOrdenados);
        premiosGanadores.setPremios2Cifras(premios2CifrasOrdenados);

        return premiosGanadores;
    }

    private List<Vendedor> getGanadores2Cifras(int anio, int cuponPago, int numero) {
        return daoService.getGanadoresNCifras(anio, cuponPago, 2, numero);
    }

    private List<Vendedor> getGanadores3Cifras(int anio, int cuponPago, int numero) {
        return daoService.getGanadoresNCifras(anio, cuponPago, 3, numero);
    }

    private List<Vendedor> getGanadores4Cifras(int anio, int cuponPago, int numero) {
        return daoService.getGanadoresNCifras(anio, cuponPago, 4, numero);
    }

    @Required
    public void setDaoService(SorteosPremiosServiceDao daoService) {
        this.daoService = daoService;
    }
}
