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
import java.util.Scanner;

public class View
{
   private int totalDistance;

   private JSONArray list;
   //provided by the JSON-simple library

   public View(){
      list = new JSONArray();
      //init list
      totalDistance =0;
      //housekeeping
   }
   @SuppressWarnings("unchecked")
   void readFile(String file){

      try{

         Map map = new LinkedHashMap<String,String>();
         //map for keeping json object ordered
         Scanner in = new Scanner(new File(file));
         String line;
         while(in.hasNext()){
             //expects a csv in the format <start>,<end>,<distance>
            line = in.nextLine();
            String[] array = line.split(",");
            //create array out of the line
            if(array.length!=3){
                //checks line format
                throw new Exception();
            }
            map.put("start", array[0]);
            map.put("end", array[1]);
            map.put("distance", array[2]);
            //add elements in ordered fashion
            list.add(new JSONObject(map));
            //the first element will look like [{"key":"value"}], thankfully the library will handle the heavy lifting
            //make a new JSONObject with the given map and put that into the JSONArray
            totalDistance+= Double.parseDouble(array[2]);
            //keep track of the distance
         }
         in.close();
         //no leaks please
      }
      catch(IOException e){
         e.printStackTrace();
      }
      catch (Exception e) {
          System.out.println("Line length is not equal to 3, this is bad data");
          e.printStackTrace();
      }
   }

   @SuppressWarnings("unchecked")
   void writeFile() throws IOException{

      try{
         if(list.isEmpty()){
            System.out.println();
            throw new Exception();
         }
         PrintWriter writer = new PrintWriter("data/out/itinerary.json");
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
