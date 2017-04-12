/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author Joon
 */
@Path("users")
public class UsersResource {
    
    @GET
    public JsonArray users() {
        return Json.createArrayBuilder()
                .add(user("joon", "ho"))
                .add(user("gustav", "staprans"))
                .build();
    }
    
    public JsonObject user(String firstName, String lastName) {
        return Json.createObjectBuilder()
                .add("firstName", firstName)
                .add("lastName", lastName)
                .build();
    }
    
}
