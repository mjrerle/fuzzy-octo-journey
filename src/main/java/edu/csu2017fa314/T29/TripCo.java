package edu.csu2017fa314.T29;

import edu.csu2017fa314.T29.Model.DistanceCalculator;
import edu.csu2017fa314.T29.Model.LocationRecords;
import edu.csu2017fa314.T29.View.View;

import java.io.IOException;

public class TripCo
{

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
      String from;
      String to;

      if(args.length==2) {
         from = args[0];
         to = args[1];
      }
      else if(args.length==1){
         from = args[0];
         to = "itinerary.json"; //default value
      }
      else{
         from = "data/test/first-5.csv"; //default value
         to = "itinerary.json"; //default value
      }
      //handles command arguments (this is useful for jar executables because of the command syntax $ java -jar *.jar <command 1> <command 2>)
      //quick tip: PrintWriter does not like to create directories for you so only give known directory paths if you want to write to a folder
      LocationRecords records = new LocationRecords(from);
      //load records
      DistanceCalculator transform = new DistanceCalculator(records.getLocations());
      //transform data
      View json = new View(transform.getCalculatedDistances());
      //create view object
      json.parseItinerary();
      //read transformed data
      json.writeFile(to);
      //write to specified file (this will always be in the data directory)
      System.out.println("Printed JSON file to data/"+to+" in main");
   }

}
