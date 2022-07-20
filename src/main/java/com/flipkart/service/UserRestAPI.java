package com.flipkart.service;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

@Path("/User")

public class UserRestAPI {
    @GET
    @Path("/helloworld")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printingHelloWorld( ){
       /* try {
            return Response.status(200).entity(adminOpObj.viewPendingAdmissions()).build();
        } catch (Exception e) {
            return Response.status(409).entity(e.getMessage()).build();
        }*/
        return Response.status(200).entity("hello world").build();

    }

    @POST
    @Path("/orderPlace")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response orderPlace(
            @NotNull
            @QueryParam("userid") int UserId,
            @NotNull
            @QueryParam("name") String name,
            @NotNull
            @QueryParam("number") String number,
            @NotNull
            @QueryParam("price") int price
    )  {


                User U = new User(UserId, name, number, price);
                Date date = new Date();
                int hr = date.getHours();
                if (hr < 8 || hr >= 22)      //will change it later to hr>22||hr<8
                {
                    // System.out.println("User will be given option of link for 15 mins\n");
                    U.inwtschedq = true;
                    DB.wtschedq.addFirst(U);
                } else {
                    if (U.price > 1000) {
                        DB.hvq.addFirst(U);
                    } else {
                        DB.rtoq1.addFirst(U);
                    }
                }

                return Response.status(200).entity("dear User " + U.name + " your order has been placed\n you will get a call within 15 minutes").build();





    }

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewStatusUser(
            @NotNull
            @QueryParam("id") int UserId
    ){

        try {
            return Response.status(200).entity(DB.orderDetails.get(UserId)).build();
        } catch (Exception e) {
            return Response.status(409).entity(e.getMessage()).build();
        }

    }

    @GET
    @Path("/showavailableslots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showavailableslots(
    ){

        try {
            Vector<Integer> A= threadClass.showAvailableSlot();

            String items[]=new String[A.size()];
            for(int k=0;k<A.size();k++)
            {
                int i=A.get(k);
                if(i>12)
                {   int j=i-12;
                    items[k]=j+" pm - "+(j+1)+" pm";
                }
                else if(i==12)
                {
                    items[k]=i+" noon - "+(i+1-12)+" pm";
                }
                else if(i==11)
                {
                    items[k]=i+" am - "+(i+1)+" noon";
                }
                else
                {
                    items[k]=i+" am - "+(i+1)+" am";
                }

                if(i>24) {
                    i = i - 24;
                    if (i > 12) {
                        int j = i - 12;
                        items[k]="next_day " + j + " pm - " + (j + 1) + " pm";
                    } else if (i == 12) {
                        items[k]="next_day " + i + " noon - " + (i + 1 - 12) + " pm";
                    } else if (i == 11) {
                        items[k]="next_day " + i + " am - " + (i + 1) + " noon";
                    } else {
                        items[k]="next_day " + i + " am - " + (i + 1) + " am";
                    }
                }
            }
            String M="";
            for(int i=0;i<A.size();i++)
            {
                M=M+items[i]+"\n";
            }
            return Response.status(200).entity(M).build();
        } catch (Exception e) {
            return Response.status(409).entity(e.getMessage()).build();
        }

    }

    @POST
    @Path("/addslot")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addslot(
            @NotNull
            @QueryParam("User") User U,
            @NotNull
            @QueryParam("hour") int hr

    ) throws IOException {


        threadClass.addSlot(U,hr);
        if(hr>24) {
            hr = hr - 24;
            return Response.status(200).entity("dear User " + U.name + " you will get a call next day between " + hr + " to " + hr + 1).build();
        }
        else
        {
            return Response.status(200).entity("dear User " + U.name + " you will get a call next day between " + hr + " to " + hr + 1).build();
        }





    }

    @GET
    @Path("/callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response callback(
            @NotNull
            @QueryParam("User") User U

    ) throws IOException {

            return Response.status(200).entity("dear User " + U.name + " you will get a call within 5 minutes " ).build();
    }

    @PUT
    @Path("/editslot")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editslot(
            @NotNull
            @QueryParam("User") User U,
            @NotNull
            @QueryParam("currenthr") int currenthr,
            @NotNull
            @QueryParam("hour") int hr
    ) {
        threadClass.deleteSlot(U,hr);


        return Response.status(200).entity("dear User " + U.name + " you will get a call within 5 minutes " ).build();
    }











    }
