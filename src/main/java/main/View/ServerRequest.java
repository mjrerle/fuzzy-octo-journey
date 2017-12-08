package main.View;

import java.util.ArrayList;


/**
 * sent from client
 */
public class ServerRequest {
    private String request = "";
    private ArrayList<String> description;

    private String locationCode = "";

    public ServerRequest(String request,
                         ArrayList<String> description,
                         String optLevel,
                         String locationCode) {
        this.request = request;
        this.description = description;
        this.locationCode = locationCode;
        this.op_level = optLevel;
    }

    String getOp_level() {
        return op_level;
    }

    private String op_level ="None";

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

    String getLocationCode() {
        return locationCode;
    }

    public ServerRequest(String request, ArrayList<String> description, String op_level) {
        this.request = request;
        this.description = description; //description is either a search or an array of destinations
        this.op_level = op_level;
    }

    String getRequest() {
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
                + ", op_level='"+ op_level +'\''
                + '}';
    }

}