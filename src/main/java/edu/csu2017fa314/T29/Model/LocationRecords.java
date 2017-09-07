package edu.csu2017fa314.T29.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// LocationRecords is an object with an ArrayList that holds all the Locations in a csv file.
public class LocationRecords {
    // Empty ArrayList to hold Location objects.
    ArrayList<Location> locations = new ArrayList<>();

    // Constructor simply reads the csv file.
    public LocationRecords(String filename){
        readFile(filename);
    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    public String toString(){
        String line = "";
        for(int i = 0; i < locations.size(); i ++){
            line.concat(locations.get(i).getId() + ", ");
            line.concat(locations.get(i).getName() + ", ");
            line.concat(locations.get(i).getCity() + ", ");
            line.concat(Double.toString(locations.get(i).getLatitude())+ ", ");
            line.concat(Double.toString(locations.get(i).getLongitude()) + ", ");
            line.concat(Double.toString(locations.get(i).getElevation()) + "\n");
        }
        return line;
    }

    // Each line of the file is parsed into a Location Object that contains id(S), name(S), city(S), latitude(D), longitude(D), elevation(D)
    public void readFile(String file){
        try{
            File filename = new File(file);
            Scanner scan = new Scanner(filename);
            while(scan.hasNextLine()){
                String[] line = scan.nextLine().split(",");
                String id = line[0];
                String name = line[1];
                String city = line[2];
                String latitude = line[3]; // Changed to double in Location.java
                String longitude = line[4]; // Changed to double in Location.java
                String elevation = line[5]; // Changed to double in Location.java
                Location next = new Location(id, name, city, latitude, longitude, elevation);
                locations.add(next);
            }
        } catch(FileNotFoundException e){
            // Some kind of exception handling

        }

    }
}
