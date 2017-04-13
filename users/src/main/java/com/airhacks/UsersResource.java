package com.airhacks;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Date;
import java.util.List;
import javax.json.JsonArrayBuilder;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("users")
public class UsersResource {
    
    @PersistenceUnit
    EntityManagerFactory emf;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonArray newUser(String json) {
        JsonObject object;
        try (JsonReader jsonReader = Json.createReader(new StringReader(json))) {
            object = jsonReader.readObject();
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Users newUser = new Users();
        newUser.setFirstName(object.getString("first"));
        newUser.setLastName(object.getString("last"));
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
        Date dateOfBirth = new Date();
        try {
            dateOfBirth = df.parse(object.getString("dob"));
        } catch (Exception e) {
        }
        newUser.setDateOfBirth(dateOfBirth);
        em.persist(newUser); 
        em.flush();
        em.getTransaction().commit();
        return this.users("");
    }
    
    // get all users
    @GET
    public JsonArray users(@DefaultValue("") @QueryParam("searchstring") String searchString) {
        EntityManager em = emf.createEntityManager();
        List<Users> usersList;
        if (searchString.equals("")) {
            usersList = em.createNamedQuery("Users.findAll").getResultList();
        } else {
            usersList = em.createNamedQuery("Users.findByName")
                .setParameter("firstName", searchString)
                .setParameter("lastName", searchString)
                .getResultList();
        }
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        usersList.forEach((u) -> {
            jsonArray.add(user(u.getFirstName(), u.getLastName(), u.getDateOfBirth()));
        });
        return jsonArray.build();
    }
    
    public JsonObject user(String firstName, String lastName, Date dateOfBirth) {
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
        String dateString = "";
        try {
            dateString = sdfr.format(dateOfBirth);
        } catch (Exception e) {
        }
        return Json.createObjectBuilder()
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("dateOfBirth", dateString)
                .build();
    }
}
