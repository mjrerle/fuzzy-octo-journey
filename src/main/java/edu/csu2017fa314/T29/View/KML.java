package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.util.ArrayList;

class KML {
    private ArrayList<Location> locations;
    private String contents = "";
    private String map = "";


    //initial thoughts: svg coordinates are at the left corner of the map corresponding the 90,-180 on the geographic scale
    //to get to my geographic point i will have to perform a conversion by scaling
    //test points: [41,-109], [0,0], [90,-180], [-90, 180], [90, 180], [-90, -180]
    // 0,0 -> this should transform into svg(512,256) 512 is height/2 and 256 is width/2
    // perform longitude + 512, latitude + 256... easy
    // 90, -180 -> should return svg(0,0) the origin
    // perform longitude - 90, latitude + 180
    // -90, 180 -> should return svg(1024,512)
    // perform longitude + 90 + 1024, latitude - 180 + 512 (don't want to perform that math rn)
    // 90, 180 -> svg(1024,0)
    // perform longitude - 90 + 1024, latitude - 180
    // -90, -180 -> svg(0,512)
    // perform longitude + 90, latitude + 180
    // 41, -109 -> should return svg(???)
    // from what I see...
    // if long/lat < 0
    // hypothesis:
    // svg(x) = (width - width*(longitude/-180))/2
    // svg(y) = (height - height*(latitude/90))/2
    // svg(201.955,139.377)
    // preliminary testing in python interpreter!!:
    //    carson-city:~$ ./convertToSVG 0 0
    //            [512.0, 256.0]
    //    carson-city:~$ ./convertToSVG -180 90
    //            [0.0, 0.0]
    //    carson-city:~$ ./convertToSVG -180 -90
    //            [0.0, 512.0]
    //    carson-city:~$ ./convertToSVG 180 90
    //            [1024.0, 0.0]
    //    carson-city:~$ ./convertToSVG 180 -90
    //            [1024.0, 512.0]
    //    carson-city:~$ ./convertToSVG -109 41
    //            [201.95555555555558, 139.3777777777778]

    /**
     * does all of the hard labor for me
     *
     * @param locations : an ordered list
     */
    KML(ArrayList<Location> locations) {
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
