package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
/**
 * Created by sswensen on 10/1/17.
 */

public class ServerResponse {
    private String svg;
    private ArrayList<Location> locs;
    private Object columns[];
    private double width;
    private double height;

    /**
     * Creates a response to the client that contains all of the info
     * it needs to display a trip that was calculated
     *
     * @param svg The Background svg to display
     * @param width width of the svg display
     * @param height height of the svg display
     * @param locs ArrayList that is the trip
     * @param columns Name of each column that each location has
     *
     * */

    public ServerResponse(String svg, double width, double height, ArrayList<Location> locs, Object columns[]) {
        this.svg = svg;
        this.locs = locs;
        this.columns = columns;
        this.width = width;
        this.height=height;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "svg='" + svg + '\'' +
                ", columns= "+ Arrays.toString(columns) +
                '}';
    }
}
