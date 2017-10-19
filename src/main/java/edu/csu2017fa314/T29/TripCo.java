package edu.csu2017fa314.T29;

import edu.csu2017fa314.T29.Model.DistanceCalculator;
import edu.csu2017fa314.T29.Model.Location;
import edu.csu2017fa314.T29.Model.LocationRecords;
import edu.csu2017fa314.T29.View.SVG;
import edu.csu2017fa314.T29.View.View;
import sun.awt.image.ImageWatched;

import java.io.IOException;
import java.util.LinkedList;

public class TripCo {

    private String name = "";

    public String getName()
    {
       return name;
    }

    public String getMessage()
    {
       if (name == "")
       {
          return "Hello!";
       }
       else
       {
          return "Hello " + name + "!";
       }
    }

    public void setName(String name)
    {
       this.name = name;
    }

    public static void main(String[] args) {

        System.out.println("Welcome to TripCo");
        String from = "";
        String to = "";
        String svgPath = "";

        if(args.length == 3) {
           from = args[0];
           to = args[1];
           svgPath = args[2];
        } else {
            System.out.println("3 arguments required: CSV, JSON Output Name, and SVG Path");
            System.exit(1);
        }
        //handles command arguments (this is useful for jar executables because of the command syntax $ java -jar *.jar <command 1> <command 2>)
        //quick tip: PrintWriter does not like to create directories for you so only give known directory paths if you want to write to a folder

        LocationRecords records = new LocationRecords(from);

        DistanceCalculator transform = new DistanceCalculator(records.getLocations());
        LinkedList<Location> locationLinkedList = transform.computeAllNearestNeighbors();
        System.out.println("I've calculated all nearest neighbor trip");
        LinkedList<Location> twoOptLinkedList = transform.shortestTwoOptTrip();
        System.out.println("I've calculated all shortest Two Opt Trip");
        SVG twoOpt = new SVG(twoOptLinkedList, svgPath);
        SVG svg = new SVG(locationLinkedList, svgPath);

        View json = new View(twoOptLinkedList); // Create View Object90
        json.createItinerary(); // Create Itinerary with info from Linked List
        json.writeFile(to);

        System.out.println("Printed JSON file to data/"+to+" in main");
    }

}
