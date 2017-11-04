package edu.csu2017fa314.T29.View;

import java.util.ArrayList;

/**
 * Created by sswensen on 10/1/17.
 */

public class ServerRequest {
    private String request="";

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description= new ArrayList<>();
        this.description.addAll(description);
    }

    private ArrayList<String> description;

    public ServerRequest(String request, ArrayList<String> description) {
        this.request=request;
        this.description=description; //description is either a search or an array of destinations
    }

    public String getRequest(){
        return request;
    }
    public void setRequest(String request){
        this.request=request;
    }
    @Override
    public String toString() {
        return "Request{" +
                "request='" + request + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}