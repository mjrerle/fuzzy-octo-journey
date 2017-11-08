package edu.csu2017fa314.T29.View;

import java.util.ArrayList;

/**
 * Created by sswensen on 10/1/17.
 */

public class ServerRequest {
    private String request = "";
    private ArrayList<String> description;

    public ArrayList<String> getDescription() {
        return description;
    }

    /**
     * @param request     : either "query" or "upload"
     * @param description : a list of strings consisting of Destination Codes (AXHS,COBE,ERAW)
     */
    public ServerRequest(String request, ArrayList<String> description) {
        this.request = request;
        this.description = description; //description is either a search or an array of destinations
    }

    public String getRequest() {
        return request;
    }

    /**
     * @return json formatted string
     */
    @Override
    public String toString() {
        return "Request{"
                + "request='" + request + '\''
                + ", description='" + description + '\''
                + '}';
    }

}