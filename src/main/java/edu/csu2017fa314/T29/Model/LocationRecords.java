package edu.csu2017fa314.T29.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// LocationRecords is an object with an ArrayList that holds all the Locations in a csv file.
public class LocationRecords {
    // Empty ArrayList to hold Location objects.
    ArrayList<Location> locations = new ArrayList<>();
    int indexID;
    int indexLatitude;
    int indexLongitude;

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
            line.concat(Double.toString(locations.get(i).getLatitude())+ ", ");
            line.concat(Double.toString(locations.get(i).getLongitude()) + ", ");
        }
        return line;
    }

    // Each line of the file is parsed into a Location Object that contains id(S), name(S), city(S), latitude(D), longitude(D), elevation(D)
    public void readFile(String file){
        try{
            File filename = new File(file);
            Scanner scan = new Scanner(filename);

            String orderString = scan.nextLine();
            orderString = orderString.replaceAll("\\t+|\\s+", "");
            String[] order = orderString.split(",");

            for(int i = 0; i < order.length; i++) {
                if(order[i].equalsIgnoreCase("ID")) {
                    indexID = i;
                }
                else if(order[i].equalsIgnoreCase("Latitude")) {
                    indexLatitude = i;
                }
                else if(order[i].equalsIgnoreCase("Longitude")) {
                    indexLongitude = i;
                }
            }

            while(scan.hasNextLine()){
                String[] line = scan.nextLine().split(",");

                if (line.length != 0) {
                    String id = line[indexID];
                    //String name = line[indexName];
                    //String city = line[indexCity];
                    String latitude = line[indexLatitude]; // Changed to double in Location.java
                    String longitude = line[indexLongitude]; // Changed to double in Location.java
                    //String elevation = line[indexElevation]; // Changed to double in Location.java
                    Location next = new Location(id, latitude, longitude);
                    locations.add(next);
                } else{
                    //Do Nothing
                }
            }
            scan.close();
        } catch(FileNotFoundException e){
            // Some kind of exception handling
            System.out.println("File not found! "+ file);
        }

    }
}
