package fr.ulille.iut.agile;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class DebugMapper implements ExceptionMapper<Throwable> {
    public DebugMapper() {
        System.out.println("###### Attention !! DebugMapper est actif !!!");
    }

    @Override
    public Response toResponse(Throwable ex) {
        ex.printStackTrace();
        return Response.serverError()
            .entity(ex.getMessage())
            .build();
    }
}
