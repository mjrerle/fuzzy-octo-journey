package edu.csu2017fa314.T29.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class View
{
   private int totalDistance;

   private JSONArray list;
   //provided by the JSON-simple library
   private String[][] iti;

   public View(String[][] iti){
      list = new JSONArray();
      //init list
      this.iti = iti;
      //set up 2d array
      totalDistance =0;
      //housekeeping
   }
   @SuppressWarnings("unchecked")
   void parseItinerary(){

      try{
         Map map = new LinkedHashMap<String,String>();
         //map for keeping json object ordered

         for (String[] anIti : iti) {
            for (int j = 0; j < anIti.length; j++) {
               map.put("start", anIti[0]);
               map.put("end", anIti[1]);
               map.put("distance", anIti[2]);
               //add elements in ordered fashion
            }
            list.add(new JSONObject(map));
            //the first element will look like [{"key":"value"}], thankfully the library will handle the heavy lifting
            //make a new JSONObject with the given map and put that into the JSONArray
            totalDistance += Double.parseDouble(anIti[2]);
            //keep track of the distance
         }
      }

      catch (Exception e) {
          e.printStackTrace();
      }
   }

   @SuppressWarnings("unchecked")
   void writeFile(String file) throws IOException{

      try{
         if(list.isEmpty()){
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

   JSONArray getList(){
       //helper method for debugging
      return list;
   }
   void setTotalDistance(int distance)
   {
       //trivial
      totalDistance = distance;
   }

   int getTotalDistance()
   {
       //trivial
      return totalDistance;
   }
//   void printList(){
//       //debugging method (deprecated)
//       System.out.println(list.toString());
//   }


}
