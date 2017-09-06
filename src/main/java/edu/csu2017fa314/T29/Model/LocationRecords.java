package edu.csu2017fa314.T29.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LocationRecords {
    ArrayList<Location> locations = new ArrayList<>();


    public LocationRecords(String filename){
        readFile(filename);
    }

    public void readFile(String file){
        try{
            File filename = new File(file);
            Scanner scan = new Scanner(filename);
            while(scan.hasNextLine()){
                String[] line = scan.nextLine().split(",");
                String id = line[0];
                String name = line[1];
                String city = line[2];
                String latitude = line[3]; // Will be changed to double
                String longitude = line[4];
                String elevation = line[5];
                Location next = new Location(id, name, city, latitude, longitude, elevation);
                locations.add(next);
            }
        } catch(FileNotFoundException e){
            // Some kind of exception handling

        }

    }
}
