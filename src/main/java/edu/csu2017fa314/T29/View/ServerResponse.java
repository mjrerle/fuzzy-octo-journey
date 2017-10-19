package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.util.LinkedList;
import java.util.Arrays;
/**
 * Created by sswensen on 10/1/17.
 */

public class ServerResponse {
    private String svg = "";
    private LinkedList<Location> locs;
    private Object columns[];

    public ServerResponse(String svg,LinkedList<Location> locs,Object columns[]) {
        this.svg = svg;
        this.locs = locs;
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "svg='" + svg + '\'' +
                ", columns= "+ Arrays.toString(columns) +
                '}';
    }
}
