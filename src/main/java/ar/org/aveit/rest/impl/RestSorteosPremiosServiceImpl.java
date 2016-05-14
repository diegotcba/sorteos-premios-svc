package ar.org.aveit.rest.impl;

import ar.org.aveit.model.PremiosGanadores;
import ar.org.aveit.model.Sorteo;
import ar.org.aveit.rest.RestSorteosPremiosService;
import ar.org.aveit.rest.model.InformacionGanadorResource;
import ar.org.aveit.rest.model.LinkResource;
import ar.org.aveit.rest.model.PremiosGanadoresResource;
import ar.org.aveit.rest.model.SorteoResource;
import ar.org.aveit.service.SorteosPremiosService;
import ar.org.aveit.util.CSVFileWriter;
import ar.org.aveit.util.XLSFileWriter;
import org.springframework.beans.factory.annotation.Required;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DiegoT on 14/11/2015.
 */
public class RestSorteosPremiosServiceImpl implements RestSorteosPremiosService {
    private SorteosPremiosService service;

    @Override
    public Response getGanadoresSorteo(SorteoResource request, UriInfo uriInfo) {
//        return getGanadoresSorteoXLS(request, uriInfo);
        Sorteo sorteo = Sorteo.valueOf(request);

        PremiosGanadores premios = service.getGanadoresSorteo("", sorteo);

        PremiosGanadoresResource premiosGanadoresResource = PremiosGanadoresResource.valueOf(premios);

        return Response.status(Response.Status.OK).entity(premiosGanadoresResource).build();
    }

//    @Override
    public Response getGanadoresSorteoCSV(SorteoResource request, UriInfo uriInfo) {
        final String CSV_FILENAME = "resultados.csv";

        Sorteo sorteo = Sorteo.valueOf(request);

        PremiosGanadores premios = service.getGanadoresSorteo("", sorteo);

        PremiosGanadoresResource premiosGanadoresResource = PremiosGanadoresResource.valueOf(premios);

        String resultFileName = createGanadoresSorteoCSVFile(CSV_FILENAME, premiosGanadoresResource);

        URI uri = uriInfo.getBaseUriBuilder().path("/csvfiles/" + resultFileName).build();

        LinkResource link = new LinkResource();
        link.setSelf(uriInfo.getAbsolutePathBuilder().path("").build().toString());
        link.setResource(uri.toString());

//        Response.ResponseBuilder response = Response.status(Response.Status.CREATED).link(uri, "file");
        Response.ResponseBuilder response = Response.status(Response.Status.CREATED).entity(link);

        return response.build();
    }

    @Override
    public Response getGanadoresSorteoXLS(SorteoResource request, UriInfo uriInfo) {
        LocalDateTime fecha = LocalDateTime.now();
        final String CSV_FILENAME = "resultados" + fecha.format(DateTimeFormatter.ofPattern("yyyymmdd-hhmm")) + ".xls";

        Sorteo sorteo = Sorteo.valueOf(request);

        PremiosGanadores premios = service.getGanadoresSorteo("", sorteo);

        PremiosGanadoresResource premiosGanadoresResource = PremiosGanadoresResource.valueOf(premios);

        String resultFileName = createGanadoresSorteoXLSFile(CSV_FILENAME, premiosGanadoresResource);

        URI uri = uriInfo.getBaseUriBuilder().path("/xlsfiles/" + resultFileName).build();

        LinkResource link = new LinkResource();
        link.setSelf(uriInfo.getAbsolutePathBuilder().path("").build().toString());
        link.setResource(uri.toString());

//        Response.ResponseBuilder response = Response.status(Response.Status.CREATED).link(uri, "file");
        Response.ResponseBuilder response = Response.status(Response.Status.CREATED).entity(link);

        return response.build();
    }

    @Override
    public Response getAllXLSFiles(UriInfo uriInfo) {
        List<LinkResource> links = new ArrayList<>();
        URI uri;
        LinkResource link;

        File f = null;
        File[] paths;

        try{
            // create new file
            f = new File(".");

            // returns pathnames for files and directory
            paths = f.listFiles();

            // for each pathname in pathname array
            for(File path:paths)
            {
                // prints file and directory paths
                if (path.isFile() && path.getName().contains(".xls")) {
                    uri = uriInfo.getBaseUriBuilder().path("/xlsfiles/" + path.getName()).build();
                    link = new LinkResource();
                    link.setSelf(uriInfo.getAbsolutePathBuilder().path("").build().toString());
                    link.setResource(uri.toString());
                    links.add(link);
                }
            }
        }catch(Exception e){
            // if any error occurs
            e.printStackTrace();
        }

        Response.ResponseBuilder response = Response.status(Response.Status.OK).entity(links);

        return response.build();
    }

    //@Override
    public Response getAllCSVFiles() {
        return null;
    }

    //@Override
    public Response getCSVFile(String fileName) {
        File file = new File(fileName);
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        return response.build();
    }

    @Override
    public Response getXLSFile(String fileName) {
        File file = new File(fileName);
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        return response.build();
    }

    private String createGanadoresSorteoCSVFile(String fileName, PremiosGanadoresResource premios) {
        List<InformacionGanadorResource> aux = new ArrayList<>();

        aux.addAll(premios.getPremios());
//        aux.add(InformacionGanadorResource.valueOf(new InformacionGanador()));
        aux.addAll(premios.getPremios4Cifras());
//        aux.add(InformacionGanadorResource.valueOf(new InformacionGanador()));
        aux.addAll(premios.getPremios3Cifras());
//        aux.add(InformacionGanadorResource.valueOf(new InformacionGanador()));
        aux.addAll(premios.getPremios2Cifras());

//        return CSVFileWriter.writeCSVFile(fileName, aux);
        return CSVFileWriter.writeCsvFileACCSV(fileName, aux);
    }

    private String createGanadoresSorteoXLSFile(String fileName, PremiosGanadoresResource premios) {
        List<InformacionGanadorResource> aux = new ArrayList<>();

        aux.addAll(premios.getPremios());
//        aux.add(InformacionGanadorResource.valueOf(new InformacionGanador()));
        aux.addAll(premios.getPremios4Cifras());
//        aux.add(InformacionGanadorResource.valueOf(new InformacionGanador()));
        aux.addAll(premios.getPremios3Cifras());
//        aux.add(InformacionGanadorResource.valueOf(new InformacionGanador()));
        aux.addAll(premios.getPremios2Cifras());

        return XLSFileWriter.writeXLSFile(fileName, premios);
//        return XLSFileWriter.writeXLSFile(fileName, aux);
    }

    @Override
    public String getServiceStatus() {
        return service.getStatus();
    }

    @Override
    public Response getEcho(String input) {
        return Response.status(200).entity(service.getEcho(input)).build();
    }

    @Required
    public void setService(SorteosPremiosService service) {
        this.service = service;
    }
}
