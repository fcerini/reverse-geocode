package com.example.demo;

import com.google.gson.JsonObject;

/**
 * Parser
 */
public interface Parser {

    public String genURL( String lat, String lon);
    public String parse( JsonObject json);
    
}