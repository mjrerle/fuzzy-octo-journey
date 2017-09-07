package edu.csu2017fa314.T29;

import edu.csu2017fa314.T29.Model.DistanceCalculator;
import edu.csu2017fa314.T29.Model.LocationRecords;
import edu.csu2017fa314.T29.View.View;

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
      LocationRecords records = new LocationRecords("");
      DistanceCalculator transform = new DistanceCalculator(records.getLocations());
   }

}
