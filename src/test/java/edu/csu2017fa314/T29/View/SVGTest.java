package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.DistanceCalculator;
import edu.csu2017fa314.T29.Model.Location;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SVGTest {

    DistanceCalculator distanceCalculator;
    LinkedList<Location> locationLinkedList;
    ArrayList<Location> locations;
    SVG svg;
    @Before
    public void setUp() {
        locations = new ArrayList<Location>();

        Map<String,String> extraInfo = new HashMap<String,String>();
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

        distanceCalculator = new DistanceCalculator(locations);
        locationLinkedList = new LinkedList<Location>();

        svg = new SVG(distanceCalculator.computeAllNearestNeighbors());

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