package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.util.LinkedList;
/**
 * Created by sswensen on 10/1/17.
 */

public class ServerResponse {
    private String svg = "";
    private LinkedList<Location> locs;

    public ServerResponse(String svg,LinkedList<Location> locs) {
        this.svg = svg;
        this.locs = locs;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "svg='" + svg + '\'' +
                ", aList="+
                '}';
    }
}
