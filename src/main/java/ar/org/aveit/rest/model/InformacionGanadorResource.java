package ar.org.aveit.rest.model;

import ar.org.aveit.model.Extracto;
import ar.org.aveit.model.InformacionGanador;
import lombok.Data;

/**
 * Created by DiegoT on 15/11/2015.
 */
@Data
public class InformacionGanadorResource {
    private ExtractoResource extracto;
    private GanadorResource ganador;
    private VendedorResource vendedor;

    public InformacionGanadorResource() {
        this.extracto = new ExtractoResource();
        this.ganador = new GanadorResource();
        this.vendedor = new VendedorResource();
    }

    public static InformacionGanadorResource valueOf(InformacionGanador info) {
        InformacionGanadorResource resource = new InformacionGanadorResource();

        resource.setExtracto(ExtractoResource.valueOf(info.getExtracto()));
        resource.setGanador(GanadorResource.valueOf(info.getGanador()));
        resource.setVendedor(VendedorResource.valueOf(info.getVendedor()));

        return resource;
    }

}
