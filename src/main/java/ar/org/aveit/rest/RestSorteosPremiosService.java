package ar.org.aveit.rest;

import ar.org.aveit.rest.model.SorteoResource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by DiegoT on 14/11/2015.
 */

@Path("/")
@Consumes("application/json")
@Produces("application/json")
public interface RestSorteosPremiosService {

    @GET
    @Path("/status/")
    @Produces("text/plain")
    public String getServiceStatus();

    @GET
    @Path("/echo/{input}")
    @Produces("text/plain")
    public Response getEcho(@PathParam("input") String input);

    @POST
    @Path("/ganadores/")
    public Response getGanadoresSorteo(SorteoResource request, @Context UriInfo uriInfo);

//    @POST
//    @Path("/ganadores/csv/")
//    public Response getGanadoresSorteoCSV(SorteoResource request, @Context UriInfo uriInfo);

    @POST
    @Path("/ganadores/xls/")
    public Response getGanadoresSorteoXLS(SorteoResource request, @Context UriInfo uriInfo);

    @GET
    @Path("/xlsfiles/")
    public Response getAllXLSFiles(@Context UriInfo uriInfo);
//
//    @GET
//    @Path("/csvfiles/")
//    public Response getAllCSVFiles();

//    @GET
//    @Path("/csvfiles/{filename}")
//    @Produces("application/vnd.ms-excel")
//    public Response getCSVFile(@PathParam("filename") String fileName);

    @GET
    @Path("/xlsfiles/{filename}")
    @Produces("application/vnd.ms-excel")
    public Response getXLSFile(@PathParam("filename") String fileName);
}
