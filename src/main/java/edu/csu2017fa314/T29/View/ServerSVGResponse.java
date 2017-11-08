package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.util.ArrayList;
import java.util.LinkedList;

public class ServerSVGResponse {
    private String response = "svg";
    private String contents;
    private int width;
    private int height;
    private LinkedList<Location> locations;

    public ServerSVGResponse(int width, int height, String contents, LinkedList<Location> locations) {
        this.contents = contents;
        this.width = width;
        this.height = height;
        this.locations=locations;
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "response='" + response + '\'' +
                ", contents=" + contents +
                '}';
    }
}
