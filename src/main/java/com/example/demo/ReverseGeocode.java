package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ReverseGeocode
	implements CommandLineRunner {

	public static void main(String[] args) {
		System.out.println("Basic Java Spring command line utility to reverse geocode a input.csv file using openstreetmap (nominatim)");		
		SpringApplication.run(ReverseGeocode.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {

			File file = new File("input.csv");

			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
			Date now = new Date();
			String strDate = sdfDate.format(now);
			PrintWriter writer = new PrintWriter("output"+ strDate +".csv");

			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				// System.out.println(line);
				
				String[] coord = line.split(",");

				String sURL = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat="
								+ coord[1].trim()+"&lon=" + coord[0].trim()
								+ "&limit=1&zoom=18&addressdetails=1";

				// Connect to the URL using java's native library
				URL url = new URL(sURL);
				URLConnection request = url.openConnection();
				request.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
				request.connect();

				// Convert to a JSON object to print data
				JsonParser jp = new JsonParser(); //from gson
				JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
				JsonObject rootobj = root.getAsJsonObject();

				JsonObject address = rootobj.get("address").getAsJsonObject();
				
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
					
				String dir = number + "," + road
							+ "," + rootobj.get("category").getAsString()
							+ "," + rootobj.get("type").getAsString()
							+ "," + rootobj.get("addresstype").getAsString();

				System.out.println(line + "," + dir);

				writer.println( line + "," + dir);
			}
			fileReader.close();
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}		
	}
}
