package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.util.ArrayList;

/**
 * Created by sswensen on 10/1/17.
 */

public class ServerResponse {
    private String response;
    private ArrayList<Location> locations;
    private Object[] columns; //location keys

    /**
     * @param locations : no order locations list, this is for the initial query
     * @param columns   : keys to the locations list, Trey needs this for the attribute selection
     */
    public ServerResponse(ArrayList<Location> locations, Object[] columns) {
        this.locations = locations;
        this.columns = columns;
        System.out.println(this.toString());
    }

    /**
     * called when i request an upload
     *
     * @param locations : no order locations list
     */
    public ServerResponse(ArrayList<Location> locations) {
        //this happens when i request an upload
        this.locations = locations;
        this.columns = new Object[1];
        System.out.println(this.toString());

    }
    // set to query for query request
    // set to upload for upload request
    //

    /**
     * @param response : either "upload" or "query"
     */
    public void setResponseType(String response) {
        this.response = response;
    }

    /**
     * @return json formatted string
     */
    @Override
    public String toString() {
        return "ServerResponse{" +
                "response='" + response + '\'' +
                ", locations=" + locations +
                '}';
    }
}
