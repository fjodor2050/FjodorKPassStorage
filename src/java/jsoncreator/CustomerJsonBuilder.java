/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoncreator;

import entity.Address;
import entity.Customer;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author user
 */
public class CustomerJsonBuilder {
    public JsonObject createJsonObject(Customer customer){
       JsonObjectBuilder job = Json.createObjectBuilder();
          job.add("id",customer.getId())
            .add("firstname",customer.getFirstname())
            .add("lastname",customer.getLastname())

            .add("address", createJsonArray(customer.getAddress()));
            return job.build();
                    
    }
    public JsonArray createJsonArray(List<Address>listAddress){
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Address a : listAddress){
            jab.add(createJsonAddress(a));
        }
        return jab.build();
    }
    public JsonObject createJsonAddress(Address address){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", address.getId())
            .add("cantry",address.getCantry());
        return job.build();
    }
}
