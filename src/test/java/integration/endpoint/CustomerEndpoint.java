package integration.endpoint;

import javax.ws.rs.*;

@Path("sales")
public class CustomerEndpoint {
    @POST
    @Path("customers")
    public void createCustomer() {
    }

    @DELETE
    @Path("customers/{customerId}")
    public void deleteCustomer() {
    }

    @GET
    @Path("customers/{customerId}")
    public void readCustomer() {
    }

    @PUT
    @Path("customers/{customerId}")
    public void updateCustomer() {
    }
}
