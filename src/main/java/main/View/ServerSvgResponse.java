package main.View;

import main.Model.Location;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * different response class
 */
public class ServerSvgResponse {
    private String response = "svg";
    private String contents;
    private final int width;
    private final int height;
    private ArrayList<Location> locations;
    private Object[] columns;

    /**
     * @param width     : svg.getWidth() = 1024
     * @param height    : svg.getHeight() = 512
     * @param contents  : the entire svg string
     * @param locations : an ordered location list (based on optimization level)
     */
    public ServerSvgResponse(int width, int height, String contents,
                             ArrayList<Location> locations, Object[] columns) {
        this.contents = contents;
        this.width = width;
        this.height = height;
        this.locations=locations;
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
                + ", height=" + height
                + ", width=" + width
                + '}';
    }
}
