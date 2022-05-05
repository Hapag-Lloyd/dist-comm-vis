package com.hlag.tools.commvis.example.customer;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

public class Customers {
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
}