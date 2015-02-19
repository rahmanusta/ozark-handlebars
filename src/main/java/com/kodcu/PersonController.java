package com.kodcu;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.View;
import javax.mvc.Viewable;
import javax.mvc.mapper.OnConstraintViolation;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.*;

/**
 * Created by usta on 12.02.2015.
 */
@Path("/")
@Controller
public class PersonController {

    @Inject
    private Models models;

    @GET
    @Path("/person")
    public String person() {

        Person person = new Person();
        person.setName("Hüseyin");
        person.setSurname("Akdoğan");
        person.setAge(35);

        models.put("person", person);

        return "person.hbs";
    }

}
