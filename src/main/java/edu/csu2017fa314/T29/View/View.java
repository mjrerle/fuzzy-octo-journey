package edu.csu2017fa314.T29.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.csu2017fa314.T29.Model.Location;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class View
{
   private int totalDistance;

   private JSONArray list;
   private LinkedList<Location> iti;
   public View(LinkedList<Location> iti) {
       this.iti = iti;
       list = new JSONArray();
   }


   public LinkedList<Location> getIti () {return iti;}
   public void setIti (LinkedList<Location> linkedList) {
       this.iti = linkedList;
   }


   public void createItinerary(){
       Location firstLocation = iti.getFirst();
       Map start = new LinkedHashMap<String,String>();
       Map end = new LinkedHashMap<String, String>();
       Set<String> columnNames = firstLocation.getColumnNames();
       JSONObject startLocation = new JSONObject();
       JSONObject endLocation = new JSONObject();
       JSONArray pairLocation = new JSONArray();

       for (int i = 0; i < iti.size() - 1; i++) {
           for (String columns : columnNames) {
               start.put(columns, iti.get(i).getColumnValue(columns));
               end.put(columns, iti.get(i + 1).getColumnValue(columns));
           }
           JSONObject startInfo = new JSONObject(start);
           JSONObject endInfo = new JSONObject(end);

           startLocation.put("Start: ", startInfo);
           endLocation.put("End: ", endInfo);
           pairLocation.add(startLocation);
           pairLocation.add(endLocation);
           list.add("ADDING");
           list.add(pairLocation);
       }

   }


   public void writeFile(String file){
      String folder = "data/";
      try{
         if(list.isEmpty() || list == null){
            System.out.println();
            throw new Exception();
         }
         PrintWriter writer = new PrintWriter(new File(folder+file));
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

   void setTotalDistance(int distance)
   {
       //trivial
      totalDistance = distance;
   }

   int getTotalDistance() {
       //trivial
       return totalDistance;
   }
}
