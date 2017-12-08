package main.View;

import main.Model.Location;
import main.Model.TripMaker;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class SVGTest {

    private TripMaker tripMaker;
    private LinkedList<Location> locationLinkedList;
    private ArrayList<Location> locations;
    private SVG svg;
    @Before
    public void setUp() {
        locations = new ArrayList<>();

        Map<String, String> extraInfo = new HashMap<>();
        Location brewery1;
        Location brewery2;
        Location brewery3;

        extraInfo.put("id","abee");
        extraInfo.put("name","Two22 Brew");
        extraInfo.put("latitude","39°38'07\" N");
        extraInfo.put("longitude","104°45'32\" W");
        brewery1 = new Location(extraInfo);
        extraInfo.put("id","abellend");
        extraInfo.put("name","Mad Jacks Mountain Brewery");
        extraInfo.put("latitude","39°24'05\" N");
        extraInfo.put("longitude","105°28'37\" W");
        brewery2 =new Location(extraInfo);
        extraInfo.put("id","acwatson");
        extraInfo.put("name","Equinox Brewing");
        extraInfo.put("latitude","40°35'17\" N");
        extraInfo.put("longitude","105°4'26\" W");
        brewery3 = new Location(extraInfo);
        locations.add(brewery1);
        locations.add(brewery2);
        locations.add(brewery3);

        tripMaker = new TripMaker(locations);
        locationLinkedList = new LinkedList<>();

        svg = new SVG(tripMaker.computeAllNearestNeighbors());

    }
    @Test
    public void testNull(){
        assertNotNull(svg);
    }

    @Test
    public void testGetMap(){
        assertNotNull(svg.getMap());
    }

    @Test
    public void testGetContents(){
        assertNotNull(svg.getContents());
    }

}