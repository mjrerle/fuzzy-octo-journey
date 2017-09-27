package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.util.LinkedList;
import java.io.File;
import java.util.Scanner;

public class SVG {

    private LinkedList<Location> locations;

    private final int svgWidth = 900;
    private final int svgHeight = 600;

    private final int x1 = 100;
    private final int y1 = 100;

    private final int x2 = 800;
    private final int y2 = 100;

    private final int x3 = 800;
    private final int y3 = 500;

    private final int x4 = 100;
    private final int y5 = 500;

    public SVG(LinkedList<Location> locations){
        this.locations = locations;
    }

    private double latitudeToSVG(double latitude){

        return 0;
    }

    private double longitudeToSVG(double longitude){
        return 0;
    }

    public void writeSVG(){
        File svg = new File("/data/map.svg");



    }
}
