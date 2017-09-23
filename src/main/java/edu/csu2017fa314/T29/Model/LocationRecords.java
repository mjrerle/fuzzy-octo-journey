package edu.csu2017fa314.T29.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

// LocationRecords is an object with an ArrayList that holds all the Locations in a csv file.
public class LocationRecords {
    // Empty ArrayList to hold Location objects.
    ArrayList<Location> locations = new ArrayList<>();

    Map<String,String> columnIndex = new HashMap<>();

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
            line.concat(locations.get(i).getId());
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
                    columnIndex.put(order[i].toLowerCase(),Integer.toString(i));
            }

            while(scan.hasNextLine()){
                String[] line = scan.nextLine().split(",");

                if (line.length != 0) {
                    Map<String,String> extraInfo = new HashMap<String,String>();
                    for(String key: columnIndex.keySet()){
                        extraInfo.put(key,line[Integer.parseInt(columnIndex.get(key))]);
                    }
                    Location next = new Location(extraInfo);
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
