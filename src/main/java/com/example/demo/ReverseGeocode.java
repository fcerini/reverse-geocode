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
		System.out.println("Basic Java Spring command line utility to reverse geocode a input.csv file using openstreetmap (nominatim), here or google.");		
		System.out.println("Available input parameters -osm -here -google");		
		SpringApplication.run(ReverseGeocode.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Parser parser;
			String option = "osm";
			if (args.length > 0) {
				option = args[0];
				System.out.println("Default: -osm ");
			}

			if (option.contains("osm")){
				parser = new OSM();
			} else if (option.contains("here")){
				parser = new Here();
			} else if (option.contains("google")){
				parser = new Google();
			} else {
				parser = null;
				System.out.println("Parameter " + args[0] + " not available.");
				System.exit(0);
			}

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

				String sURL = parser.genURL(coord[1].trim(), coord[0].trim());

				URL url = new URL(sURL);
				URLConnection request = url.openConnection();
				request.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
				request.connect();

				JsonParser jp = new JsonParser();
				JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
				JsonObject rootobj = root.getAsJsonObject();

				String output = parser.parse( rootobj );

				System.out.println(line + "," + output);

				writer.println( line + "," + output);
			}
			fileReader.close();
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}		
	}
}
