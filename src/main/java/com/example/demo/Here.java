package com.example.demo;

import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Value;

/**
 * Here
 */
public class Here implements Parser {

	public String appId;
	public String appCode;


    @Override
    public String genURL(String lat, String lon) {
        return "https://reverse.geocoder.api.here.com/6.2/reversegeocode.json?"
        + "prox="+ lat +"%2C"+ lon +"%2C50&mode=retrieveAddresses&maxresults=1&gen=9"
        + "&app_id="+ appId +"&app_code=" + appCode;

    }

    @Override
    public String parse(JsonObject json) {

        String number ="0";
        String road ="SN";
        String out = "ERROR!";
        String matchType = "SN";

        try {
            JsonObject result = json.get("Response").getAsJsonObject()
            .get("View").getAsJsonArray().get(0).getAsJsonObject()
            .get("Result").getAsJsonArray().get(0).getAsJsonObject();

            JsonObject location = result.get("Location").getAsJsonObject();
            JsonObject address = location.get("Address").getAsJsonObject();

            if ( address.get("HouseNumber") != null){
                number = address.get("HouseNumber").getAsString();
            }
            
            if ( address.get("Street") != null){
                road = address.get("Street").getAsString();
            } else {
                road = address.get(address.keySet().toArray()[0].toString()).getAsString();
            }

            if (result.get("MatchType") != null){
                matchType= result.get("MatchType").getAsString();
            }
            out =  number + "," + road
            + "," + result.get("MatchLevel").getAsString()
            + "," + location.get("LocationType").getAsString()
            + "," + matchType;

        } catch  (Exception e) {
            System.out.println(e.getMessage());
        }
            
        return out;            }
    
}