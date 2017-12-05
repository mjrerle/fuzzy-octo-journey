package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.util.ArrayList;
import java.util.Arrays;

public class ServerKmlResponse {
    private ArrayList<Location> locations;
    private Object[] columns;
    private String contents;
    private String response = "kml";

    /**
     * @param contents  : the entire svg string
     * @param locations : an ordered location list (based on optimization level)
     */
    ServerKmlResponse(String contents,
                      ArrayList<Location> locations,
                      Object[] columns) {
        this.contents = contents;
        this.locations = locations;
        this.columns = columns;
        System.out.println(this.toString());
    }

    /**
     * @return json formatted string
     */
    @Override
    public String toString() {
        return "ServerResponse{"
                + "response='" + response + '\''
                + ", contents=" + contents
                + ", columns=" + Arrays.toString(columns)
                + '}';
    }
}
