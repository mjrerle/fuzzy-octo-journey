package edu.csu2017fa314.T29.Model;

import java.util.Scanner;
import java.util.ArrayList;

public class Brewery {
    String id;
    String name;
    String city;
    Double latitude;
    Double longitude;
    Double elevation;

    public Brewery(String id, String name, String city, Double latitude, Double longitude, Double elevation){
        this.id = id;
        this.name = name;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    public static void main(String [] args){
        Scanner scan = new Scanner("~\\Documents\\Github\\class\\sprint1"); // Local filepath to csv file
        ArrayList<Brewery> breweries = new ArrayList<Brewery>();
        while(scan.hasNextLine()){
            String [] line = scan.nextLine().split(",");
            String id = line[0];
            String name = line[1];
            String city = line[2];
            double latitude = Double.parseDouble(line[3]); //Decimal Degrees = Degrees + minutes/60 + seconds/3600
            double longitude = Double.parseDouble(line[4]);//Decimal Degrees = Degrees + minutes/60 + seconds/3600
            double elevation = Double.parseDouble(line[5]);
            Brewery nextBrewery = new Brewery(id, name, city, latitude, longitude, elevation);
            breweries.add(nextBrewery);
        }
        for(int i = 0; i < breweries.size(); i++){
            System.out.println(breweries.get(i));
        }
    }

}
