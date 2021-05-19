package web.rest.resources;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.Persona;
import service.MyServiceFactory;
import service.abitante.PersonaService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Path("/persona")
public class PersonaResource {

    ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOGGER = Logger.getLogger(PersonaResource.class.getName());

    @Context
    HttpServletRequest request;

    private PersonaService personaService = MyServiceFactory.getPersonaServiceInstance();

    public PersonaResource() {
    }

    private String convertDate(Persona persona) throws JsonProcessingException {
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = objectMapper.writeValueAsString(persona);
        return json;
    }

    private String convertDateList(List<Persona> persona) throws JsonProcessingException {
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = objectMapper.writeValueAsString(persona);
        return json;
    }

    @GET
    @Path("{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAbitante(@PathParam("id") Long id) throws Exception {
        Persona persona = personaService.caricaSingoloElemento(id);
        return Response.status(200).entity(convertDate(persona)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserisciNuovoAbitante(Persona personaInput) throws Exception {
        personaService.inserisciNuovo(personaInput);
        return Response.status(200).entity(convertDate(personaInput)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() throws Exception {
        List<Persona> result = personaService.listAllElements();
        return Response.status(200).entity(convertDateList(result)).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAutomobile(@QueryParam("nome") String nome, @QueryParam("cognome") String cognome, @QueryParam("dataDiNascita") Date dataDiNascita) throws Exception {
        Persona persona = new Persona(nome, cognome, dataDiNascita);
        List<Persona> result = personaService.findByExample(persona);
        return Response.status(200).entity(convertDateList(result)).build();
    }

    @DELETE
    @Path("{id : \\d+}")
    public Response deleteAutomobile(@PathParam("id") Long id) throws Exception {
        Persona persona = personaService.caricaSingoloElemento(id);

        try {
            personaService.rimuovi(persona);
            return Response.status(200).entity("Rimossa Automobile con id: "+id).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("not found").build();
        }

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response aggiornaAutomobile(Persona personaInput) throws Exception {
        LOGGER.info("Verbo Http.........................." + request.getMethod());
        personaService.aggiorna(personaInput);
        return Response.status(200).entity(convertDate(personaInput)).build();
    }

}
