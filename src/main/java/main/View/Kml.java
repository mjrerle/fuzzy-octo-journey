package main.View;

import main.Model.Location;

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

    /**
     * @return contents
     */
    String getContents() {
        return contents;
    }

    /**
     *
     * @return proper header info
     */
    private String header() {
        String line = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
        line += "<kml xmlns=\"http://earth.google.com/kml/2.1\">";
        line += "<Document>";
        line += "<name>\"overlay\"</name>";
        line += "<description>\"my trip\"</description>";
        return line;
    }

    /**
     *
     * @return proper footer info
     */
    private String footer() {
        return "</coordinates></LineString></Placemark></Document></kml>";
    }

    /**
     *
     * @return a blue line
     */
    private String makeLine() {
        String line = "<Style id=\"blueLine\">";
        line += "<LineStyle><color>\"ffff0000\"</color><width>\"4\"</width></LineStyle>";
        line += "</Style>";
        return line;
    }

    /**
     *
     * @return placemark wrapper
     */
    private String makePlacemark() {
        String line = "<Placemark>";
        line += "<name>\"Trip route\"</name>";
        line += "<styleUrl>\"#blueLine\"</styleUrl>";
        line += "<LineString>";
        line += "<altitudeMode>\"relative\"</altitudeMode>";
        line += "<coordinates>\n";
        return line;
    }

    /**
     * write to contents
     */
    private void writeContents() {
        contents += header();
        contents += makeLine();
        contents += makePlacemark();
        for (int i = 0; i < locations.size(); i++) {
            drawLine(i);
        }
        contents += footer();
    }

    /**
     * create (lat,long) string and write to contents
     * @param index will be accessing locations
     */
    private void drawLine(int index) {
        String latitude = locations.get(index).getColumnValue("latitude");
        String longitude = locations.get(index).getColumnValue("longitude");
        contents += latitude + "," + longitude + "\n";

    }

}
