package com.example.demo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Google
 */
public class Google implements Parser {

    public String key;

    @Override
    public String genURL(String lat, String lon) {
        return "https://maps.googleapis.com/maps/api/geocode/json?"
        + "latlng="+ lat +","+ lon
        + "&key=" + key;
    }

    @Override
    public String parse(JsonObject json) {
        String number ="0";
        String road ="SN";
        String out = "ERROR!";
        
        //System.out.println(json.toString());

        try {
            JsonObject result = json.get("results").getAsJsonArray().get(0).getAsJsonObject();

            JsonArray address = result.get("address_components").getAsJsonArray();
            
            if ( address.get(0).getAsJsonObject().get("types").getAsJsonArray().get(0).getAsString()
                    .contains("street_number") ) {
                number = address.get(0).getAsJsonObject().get("long_name").getAsString();
                road = address.get(1).getAsJsonObject().get("long_name").getAsString();
            } else {
                road = address.get(0).getAsJsonObject().get("long_name").getAsString();
            }

            out =  number + "," + road
            + "," + result.get("geometry").getAsJsonObject().get("location_type").getAsString()
            + "," + result.get("types").getAsJsonArray().get(0).getAsString();

        } catch  (Exception e) {
            System.out.println(e.getMessage());
        }
            
        return out;
    }
}
