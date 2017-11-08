package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.util.ArrayList;

/**
 * different response class
 */
public class ServerSvgResponse {
    private String response = "svg";
    private String contents;
    private final int WIDTH;
    private final int HEIGHT;
    private ArrayList<Location> locations;

    /**
     * @param WIDTH     : svg.getWIDTH() = 1024
     * @param HEIGHT    : svg.getHEIGHT() = 512
     * @param contents  : the entire svg string
     * @param locations : an ordered location list (based on optimization level)
     */
    public ServerSvgResponse(int WIDTH, int HEIGHT, String contents,
                             ArrayList<Location> locations) {
        this.contents = contents;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.locations=locations;
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
                + '}';
    }
}
