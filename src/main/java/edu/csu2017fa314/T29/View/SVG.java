package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.util.LinkedList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class SVG {

    private LinkedList<Location> locations;

    private final double x1 = 34.74561;
    private final double y1 = 34.74561;

    private final double x3 = 1027.738;
    private final double y3 = 744.63525;


    public SVG(LinkedList<Location> locations){
        this.locations = locations;
        writeSVG();
    }

    private double latitudeToSVG(double latitude){
        return (41 - latitude)/4 * (y3 - y1);
    }

    private double longitudeToSVG(double longitude){
        return (-109 - longitude)/7 * (x3 - x1);
    }

    public void writeSVG(){
            try(FileWriter fw = new FileWriter("/data/Map.svg", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.printf("</svg>\n");
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
}
