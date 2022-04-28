package test.jaxrs;

import javax.ws.rs.*;

@Path("endpoint")
public class Endpoints {
    @POST
    @Path("a")
    public void receivesAPostRequest() {
    }

    @GET
    @Path("b")
    public void receivesAGetRequest() {
    }

    @HEAD
    @Path("c")
    public void receivesAHeadRequest() {
    }

    @OPTIONS
    @Path("d")
    public void receivesAOptionsRequest() {
    }

    @PUT
    @Path("e")
    public void receivesAPutRequest() {
    }

    @PATCH
    @Path("f")
    public void receivesAPatchRequest() {
    }

    @DELETE
    @Path("g")
    public void receivesADeleteRequest() {
    }
}
