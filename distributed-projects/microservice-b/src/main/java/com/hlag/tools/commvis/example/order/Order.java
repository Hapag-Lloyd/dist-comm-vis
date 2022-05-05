package com.hlag.tools.commvis.example.order;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

public class Order {
    @POST
    @Path("orders")
    public void createOrder() {
    }

    @DELETE
    @Path("orders/{orderId}")
    public void deleteOrder() {
    }

    @GET
    @Path("orders/{orderId}")
    public void readOrder() {
    }
}