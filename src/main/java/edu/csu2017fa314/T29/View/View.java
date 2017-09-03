package edu.csu2017fa314.T29.View;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
         JSONObject obj = new JSONObject();
         Scanner in = new Scanner(new File(file));
         String line;
         while(in.hasNext()){
            line = in.nextLine();
            String[] array = line.split(",");
            obj.put("start", array[0]);
            obj.put("end", array[1]);
            obj.put("distance", array[2]);
            list.add(obj);
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
         writer.print(list.toString());
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
       System.out.print(list.toString());
   }


}
