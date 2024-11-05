package ec.fin.baustro;

import ec.fin.baustro.dtos.requests.ServerRequest;
import ec.fin.baustro.isoutils.Server;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Slf4j
@Path("/api")
public class ExampleResource {


    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }


    @POST
    @Path("/Inicio")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean Iniciar(
            @RequestBody(
                    description = "Parámetros para iniciar el servidor",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ServerRequest.class, type = SchemaType.OBJECT),
                            examples = {
                                    @ExampleObject(
                                            name = "pos.xml",
                                            summary = "Puerto 8900 con definición 'pos.xml'",
                                            value = "{\"port\": 8900, \"definicion\": \"pos.xml\"}"
                                    )
                                    ,@ExampleObject(
                                            name = "1-alignet.xml",
                                            summary = "Puerto 8901 con definición '1-alignet.xml'",
                                            value = "{\"port\": 8901, \"definicion\": \"1-alignet.xml\"}"
                                    ),
                                    @ExampleObject(
                                            name = "2-fullcarga.xml",
                                            summary = "Puerto 8902 con definición '2-fullcarga.xml'",
                                            value = "{\"port\": 8902, \"definicion\": \"2-fullcarga.xml\"}"
                                    ),
                                    @ExampleObject(
                                            name = "3-IATA.xml",
                                            summary = "Puerto 8903 con definición '3-IATA.xml'",
                                            value = "{\"port\": 8903, \"definicion\": \"3-IATA.xml\"}"
                                    ),
                                    @ExampleObject(
                                            name = "4-visa-Franquicia-ba.xml",
                                            summary = "Puerto 8904 con definición '4-visa-Franquicia-ba.xml'",
                                            value = "{\"port\": 8904, \"definicion\": \"4-visa-Franquicia-ba.xml\"}"
                                    ),
                                    @ExampleObject(
                                            name = "5-Mastercard-Franquicia-ba.xml",
                                            summary = "Puerto 8905 con definición '5-Mastercard-Franquicia-ba.xml'",
                                            value = "{\"port\": 8905, \"definicion\": \"5-Mastercard-Franquicia-ba.xml\"}"
                                    )
                            }
                    )
            )
            ServerRequest request) {

        try {
            Server server = new Server(request.getPort()    , request.getDefinicion());
            log.info("inicio de servicio");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
