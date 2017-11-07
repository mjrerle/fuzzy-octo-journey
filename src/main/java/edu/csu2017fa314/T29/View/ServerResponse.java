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
