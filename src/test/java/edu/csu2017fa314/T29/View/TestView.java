package edu.csu2017fa314.T29.View;
import static org.junit.Assert.*;

import edu.csu2017fa314.T29.Model.DistanceCalculator;
import edu.csu2017fa314.T29.Model.Location;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.*;


public class TestView
{

    DistanceCalculator distanceCalculator;
    View view;
    Map<String,String> extraInfo = new HashMap<String,String>();
    ArrayList<Location> locationArrayList = new ArrayList<Location>();
    LinkedList<Location> linkedList;
    Location brewery1;
    Location brewery2;
    Location brewery3;

    @Before
    public void setUp()
    {
        extraInfo.put("id","abee");
        extraInfo.put("name","Two22 Brew");
        extraInfo.put("latitude","39°38'07\" N");
        extraInfo.put("longitude","104°45'32\" W");
        brewery1 = new Location(extraInfo);
        brewery1.setLatitude("39°38'07\" N");
        brewery1.setLongitude("104°45'32\" W");
        extraInfo.put("id","abellend");
        extraInfo.put("name","Mad Jacks Mountain Brewery");
        extraInfo.put("latitude","39°24'05\" N");
        extraInfo.put("longitude","105°28'37\" W");
        brewery2 =new Location(extraInfo);
        brewery2.setLatitude("39°24'05\" N");
        brewery2.setLongitude("105°28'37\" W");
        extraInfo.put("id","acwatson");
        extraInfo.put("name","Equinox Brewing");
        extraInfo.put("latitude","40°35'17\" N");
        extraInfo.put("longitude","105°4'26\" W");
        brewery3 = new Location(extraInfo);
        brewery3.setLatitude("40°35'17\" N");
        brewery3.setLongitude("105°4'26\" W");

        locationArrayList.add(brewery1);
        locationArrayList.add(brewery2);
        locationArrayList.add(brewery3);

        distanceCalculator = new DistanceCalculator(locationArrayList);
        view = new View(distanceCalculator.computeAllNearestNeighbors(distanceCalculator.getLocations()));
    }

    @Ignore
    @Test 
    public void testSetDistance() 
    {
        view.setTotalDistance(4);
        assertTrue(view.getTotalDistance() == 4);
        view.setTotalDistance(-4);
        assertTrue(view.getTotalDistance() == -4);
    }

    @Ignore
    @Test
    public void testParseItinerary(){
        view.createItinerary();

    }

    @Test
    public void testWriteFile(){
        view.createItinerary();
        view.writeFile("itinerary.json");
    }

    @Ignore
    @Test
    public void testTotalDistance() throws IOException{
        view.createItinerary();
        view.writeFile("itinerary.json");
    }
}
