package com.example.demo;

import com.google.gson.JsonObject;

/**
 * OSM
 */
public class OSM implements Parser {

    @Override
    public String genURL(String lat, String lon) {
        return "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat="
        + lat +"&lon=" + lon
        + "&limit=1&zoom=18&addressdetails=1";
    }

    @Override
    public String parse (JsonObject json) {
        JsonObject address = json.get("address").getAsJsonObject();

        String number ="0";
        String road ="SN";
        try {
            
            if ( address.get("house_number") != null){
                number = address.get("house_number").getAsString();
            }
            
            if ( address.get("road") != null){
                road = address.get("road").getAsString();
            } else {
                road = address.get(address.keySet().toArray()[0].toString()).getAsString();
            }

        } catch  (Exception e) {
            System.out.println(e.getMessage());
        }
            
        return number + "," + road
                    + "," + json.get("category").getAsString()
                    + "," + json.get("type").getAsString()
                    + "," + json.get("addresstype").getAsString();
            }

    
}