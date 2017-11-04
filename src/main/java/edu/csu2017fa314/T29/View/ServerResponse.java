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
    private String response;
    private ArrayList<Location> locations;
    private Object[] columns; //location keys

    public ServerResponse(ArrayList<Location> locations, Object[] columns) {
        this.locations=locations;
        this.columns=columns;
        System.out.println(this.toString());
    }
    public ServerResponse(ArrayList<Location> locations){
        //this happens when i request an upload
        this.locations=locations;
        this.columns=new Object[1];
    }
    // set to query for query request
    // set to upload for upload request
    //
    public void setResponseType(String response){
        this.response=response;
    }
    @Override
    public String toString() {
        return "ServerResponse{" +
                "response='" + response + '\'' +
                ", locations=" + locations +
                '}';
    }
}
