package integration.endpoint;

import javax.ws.rs.*;

@Path("sales")
public class CustomerEndpoint {
    @POST
    @Path("customers")
    public void createCustomer() {
    }
}
