package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.util.LinkedList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;


public class SVG {

    private LinkedList<Location> locations;

    private final double x1 = 34.74561;
    private final double y1 = 34.74561;

    private final double x3 = 1027.738;
    private final double y3 = 744.63525;

    private String filePath;


    public SVG(LinkedList<Location> locations, String filePath){
        this.locations = locations;
        this.filePath = filePath;
        writeSVG();
    }

    private double latitudeToSVG(double latitude){
        return ((41 - latitude)/4 * (y3 - y1)) + y1;
    }

    private double longitudeToSVG(double longitude){
        return ((-109 - longitude)/-7 * (x3 - x1)) + x1;
    }

    private void writeSVG(){
            try(FileWriter fw = new FileWriter("Map.svg");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            File map = new File(filePath);
            Scanner scan = new Scanner(map);
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");

            out.printf("<svg xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\"" +
                    " version=\"1.0\" width=\"1066.6073\" height=\"783.0824\" id=\"svgUno\">");
            scan.nextLine();
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                out.println(line);
            }
            for(int i = 0 ; i < locations.size();i++){
                if(i == locations.size() -1){
                    out.printf("<line id=\"%d\" x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" stroke-width=\"1.5\" stroke=\"#0000FF\"/>\n"
                            ,i
                            ,longitudeToSVG(locations.get(i).getLongitude()), latitudeToSVG(locations.get(i).getLatitude())
                            ,longitudeToSVG(locations.get(0).getLongitude()),latitudeToSVG(locations.get(0).getLatitude()));
                }
                else {
                    out.printf("<line id=\"%d\" x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" stroke-width=\"1.5\" stroke=\"#0000FF\"/>\n"
                            , i
                            , longitudeToSVG(locations.get(i).getLongitude()), latitudeToSVG(locations.get(i).getLatitude())
                            , longitudeToSVG(locations.get(i + 1).getLongitude()), latitudeToSVG(locations.get(i + 1).getLatitude()));
                }

            }
            out.println("</svg>");
            out.close();
            bw.close();
            fw.close();
            scan.close();

        } catch (IOException e) {
                System.out.println(e);
        }


    }
}
