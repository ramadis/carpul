package ar.edu.itba.paw.webapp.errors;

import ar.edu.itba.paw.webapp.DTO.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WildcardExceptionHandler implements ExceptionMapper<Exception> {
    private static final Logger console = LoggerFactory.getLogger(WildcardExceptionHandler.class);

    @Override
    public Response toResponse(Exception e) {
        console.error("Uncaught exception {}", e.toString());
        String error = e.toString();
        if (error.contains("404")) {
        	return Response.status(Status.NOT_FOUND)
                .entity(new ErrorDTO(Status.NOT_FOUND.getStatusCode(), "Endpoint non-existent", "Try accessing a valid endpoint"))
                .build();
        } else {
        	return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorDTO(Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Uncaught error in the server", "Something wrong happend on our end. We're sorry."))
                    .build();
        }
    }
}
