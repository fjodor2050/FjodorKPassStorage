/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoncreator;

import entity.Book;
import entity.User;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author user
 */
public class UserJsonBuilder {
    public JsonObject createJsonObject(User user){
        CustomerJsonBuilder cjb = new CustomerJsonBuilder();
        
        JsonObjectBuilder job = Json.createObjectBuilder();
          job.add("id",user.getId())
            .add("login",user.getLogin())
            .add("customer",cjb.createJsonObject(user.getCustomer()));
            
        return job.build();
                    
    }
}
