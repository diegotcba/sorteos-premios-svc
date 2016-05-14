package ar.org.aveit.rest.model;

import ar.org.aveit.model.InformacionGanador;
import ar.org.aveit.model.PremiosGanadores;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DiegoT on 15/11/2015.
 */
@Data
public class PremiosGanadoresResource {
    private List<InformacionGanadorResource> premios;
    private List<InformacionGanadorResource> premios4Cifras;
    private List<InformacionGanadorResource> premios3Cifras;
    private List<InformacionGanadorResource> premios2Cifras;

    public PremiosGanadoresResource() {
        this.premios = new ArrayList<>();
        this.premios4Cifras = new ArrayList<>();
        this.premios3Cifras = new ArrayList<>();
        this.premios2Cifras = new ArrayList<>();
    }

    public static PremiosGanadoresResource valueOf(PremiosGanadores premios) {
        PremiosGanadoresResource resource = new PremiosGanadoresResource();

        List<InformacionGanadorResource> premiosResource = new ArrayList<>();
        List<InformacionGanadorResource> premios4CifrasResource = new ArrayList<>();
        List<InformacionGanadorResource> premios3CifrasResource = new ArrayList<>();
        List<InformacionGanadorResource> premios2CifrasResource = new ArrayList<>();

        premios.getPremios().stream().forEach(premio -> {
            premiosResource.add(InformacionGanadorResource.valueOf(premio));
        });

        premios.getPremios4Cifras().stream().forEach(premio -> {
            premios4CifrasResource.add(InformacionGanadorResource.valueOf(premio));
        });

        premios.getPremios3Cifras().stream().forEach(premio -> {
            premios3CifrasResource.add(InformacionGanadorResource.valueOf(premio));
        });

        premios.getPremios2Cifras().stream().forEach(premio -> {
            premios2CifrasResource.add(InformacionGanadorResource.valueOf(premio));
        });

        resource.setPremios(premiosResource);
        resource.setPremios4Cifras(premios4CifrasResource);
        resource.setPremios3Cifras(premios3CifrasResource);
        resource.setPremios2Cifras(premios2CifrasResource);

        return resource;
    }
}
