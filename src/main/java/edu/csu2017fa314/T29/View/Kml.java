package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.util.ArrayList;

class Kml {
    private ArrayList<Location> locations;
    private String contents = "";
    private String map = "";


    /**
     * does all of the hard labor for me
     *
     * @param locations : an ordered list
     */
    Kml(ArrayList<Location> locations) {
        this.locations = locations;
        writeContents();
    }


    String getContents() {
        return contents;
    }

    /**
     * contents is the outer layer
     * map is the background layer
     * add the svg header for contents before hand to signify that it is the outer layer
     * scan the map, ignoring the first line and add everything to contents
     * then, draw the lines given the arraylist of locations
     * for each line, convert the coordinate to an svg coordinate and draw
     */

    private String header() {
        String line = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
        line += "<kml xmlns=\"http://earth.google.com/kml/2.1\">";
        line += "<Document>";
        line += "<name>\"overlay\"</name>";
        line += "<description>\"my trip\"</description>";
        return line;
    }

    private String footer() {
        return "</coordinates></LineString></Placemark></Document></kml>";
    }

    private String makeLine() {
        String line = "<Style id=\"blueLine\">";
        line += "<LineStyle><color>\"ffff0000\"</color><width>\"4\"</width></LineStyle>";
        line += "</Style>";
        return line;
    }

    private String makePlacemark() {
        String line = "<Placemark>";
        line += "<name>\"Trip route\"</name>";
        line += "<styleUrl>\"#blueLine\"</styleUrl>";
        line += "<LineString>";
        line += "<altitudeMode>\"relative\"</altitudeMode>";
        line += "<coordinates>\n";
        return line;
    }

    private void writeContents() {
        contents += header();
        contents += makeLine();
        contents += makePlacemark();
        for (int i = 0; i < locations.size(); i++) {
            drawLine(i);
        }
        contents += footer();
    }

    private void drawLine(int index) {
        String latitude = locations.get(index).getColumnValue("latitude");
        String longitude = locations.get(index).getColumnValue("longitude");
        contents += latitude + "," + longitude + "\n";

    }

}
