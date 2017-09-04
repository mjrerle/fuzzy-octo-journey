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

   public View(){
      list = new JSONArray();
      totalDistance =0;
   }
   @SuppressWarnings("unchecked")
   void readFile(String file) throws IOException{

      try{

         Map map = new LinkedHashMap<String,String>();
         Scanner in = new Scanner(new File(file));
         String line;
         while(in.hasNext()){
            line = in.nextLine();
            String[] array = line.split(",");
            if(array.length!=3){
                System.out.println("readFile detected bad input data");
                System.exit(1);
            }
            map.put("start", array[0]);
            map.put("end", array[1]);
            map.put("distance", array[2]);
            list.add(new JSONObject(map));
            totalDistance+= Double.parseDouble(array[2]);
         }
         in.close();
      }
      catch(IOException e){
         e.printStackTrace();
      }
   }

   @SuppressWarnings("unchecked")
   void writeFile() throws IOException{
      try{
         PrintWriter writer = new PrintWriter("data/out/itinerary.json");
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         writer.println(gson.toJson(list));
         writer.close();
      }
      catch (IOException e){
         e.printStackTrace();
      }
   }

   JSONArray getList(){
      return list;
   }
   void setTotalDistance(int distance)
   {
      totalDistance = distance;
   }

   int getTotalDistance()
   {
      return totalDistance;
   }
   void printList(){
       System.out.println(list.toString());
   }


}
