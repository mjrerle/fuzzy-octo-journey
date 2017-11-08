package edu.csu2017fa314.T29.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.csu2017fa314.T29.Model.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map;

public class View
{
   private JSONArray list; // This is the overarching structure of the JSON
   private ArrayList<Location> iti; // A LinkedList of Locations in the shortest order

    /////////////////////////////////
    // Constructor                 //
    /////////////////////////////////
    public View(ArrayList<Location> iti) {
       this.iti = iti;
       list = new JSONArray();
    }


    /////////////////////////////////
    // Some Getters and Setters    //
    /////////////////////////////////
    public ArrayList<Location> getIti() {
        return iti;
    }

    public void setIti(ArrayList<Location> linkedList) {
       this.iti = linkedList;
    }

    /////////////////////////////////////////////
    // Modularize work from create itinerary.  //
    // Simply creates a JSON Object such as a  //
    // "Start Location" or a "End Location"    //
    /////////////////////////////////////////////
    public JSONObject createLocationInfo(Location location) {
        Location firstLocation = iti.get(0);
        Map information = new LinkedHashMap<String,String>(); // A Map is necessary for ordering
        Set<String> columnNames = firstLocation.getColumnNames(); // Get a Set of all possible information
                                                                  // IE: "Elevation" or "Latitude"

        for(String columns : columnNames) {
            information.put(columns, location.getColumnValue(columns));
            information.put("distance", location.getDistance());
        }

        JSONObject obj = new JSONObject(information);

        return obj;
    }

    /////////////////////////////////////////////
    // This updates the class variable "list"  //
    // by adding an Object for every location. //
    /////////////////////////////////////////////

    public void createItinerary(){

       JSONObject start;

       for (int i = 0; i < iti.size(); i++) {

           start = createLocationInfo(iti.get(i));

           list.add(start);
       }
   }


   public void writeFile(String file){
      try{
         if(list.isEmpty() || list == null){
            System.out.println();
            throw new Exception();
         }
         PrintWriter writer = new PrintWriter(new File(file));
         //writes to a specific filepath
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         //gson allows for pretty printing (not available with just json-simple)
          //the gson object will transform the json string or array
         writer.println(gson.toJson(list));
         //write to the file
         writer.close();
         //no leaks...
      }
      catch (IOException e){
         e.printStackTrace();
      } catch (Exception e) {
          System.out.println("Please read a non-empty file before calling writeFile");
          e.printStackTrace();
      }
   }

   public JSONArray getList() {
       return list;
   }
}
