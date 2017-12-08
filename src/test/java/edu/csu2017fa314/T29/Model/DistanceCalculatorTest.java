package edu.csu2017fa314.T29.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by treyy on 9/2/2017.
 */
public class DistanceCalculatorTest {

    double foCoLatitude = 40.585258;
    double foCoLongitude = -105.084419;
    double boraBoraLatitude = -16.5004126;
    double boraBoraLongitude = -151.74149039999998;

    ArrayList<Location> tLocations = new ArrayList<Location>();
    ArrayList<Location> breweries = new ArrayList<Location>();

    Map<String,String> extraInfo = new HashMap<String,String>();
    Location brewery1;
    Location brewery2;
    Location brewery3;
    Location t1;Location t2;Location t3; Location t4; Location t5;Location t6; Location t7;
    Location t8; Location t9; Location t01; Location t02; Location t03;

    DistanceCalculator brewDistanceCalculator;
    DistanceCalculator distanceCalculator;

    /** Needs to be refactored from 66 lines to 25. */
    @Before
    public void setUp(){
        brewery1 = locationBuilder("abee", "Two22 Brew", "39°38'07\" N", "104°45'32\" W");
        brewery2 = locationBuilder("abellend", "Mad Jacks Mountain Brewery", "39°24'05\" N", "105°28'37\" W");
        brewery3 = locationBuilder("acwatson", "Equinox Brewing", "40°35'17\" N", "105°4'26\" W");
        t1 = locationBuilder("a", "0", "0");
        t2 = locationBuilder("b", "0", "1");
        t3 = locationBuilder("c", "1", "0");
        t4 = locationBuilder("d", "1", "1");
        t5 = locationBuilder("e", "1", "2");
        t6 = locationBuilder("f", "2", "1");
        t7 = locationBuilder("g", "2", "2");
        t8 = locationBuilder("a1", "0", "0");
        t9 = locationBuilder("b1", "999", "999");
        t01 = locationBuilder("c1", "-999", "-999");
        t02 = locationBuilder("d1", "999", "-999");
        t03 = locationBuilder("e1", "-999", "999");
        brewDistanceCalculator = new DistanceCalculator(breweries);
        distanceCalculator = new DistanceCalculator(tLocations);
    }

    /**For the tLocations ArrayList.*/
    public Location locationBuilder(String id, String name, String latitude, String longitude) {
        extraInfo.put("id",id);
        extraInfo.put("name",name);
        extraInfo.put("latitude",latitude);
        extraInfo.put("longitude",longitude);
        Location brewery = new Location(extraInfo);
        breweries.add(brewery);
        return brewery;
    }
    public Location locationBuilder(String id, String latitude, String longitude) {
        extraInfo.put("id",id);
        extraInfo.put("latitude",latitude);
        extraInfo.put("longitude",longitude);
        Location location = new Location(extraInfo);
        tLocations.add(location);
        return location;
    }

    @Test
    public void testDegreeToRadian(){
        assertTrue(brewDistanceCalculator.degreeToRadian(0.0) == 0.0);
        assertTrue(brewDistanceCalculator.degreeToRadian(90.0) == 1.5707963267948966);
        assertTrue(brewDistanceCalculator.degreeToRadian(180.0) ==3.141592653589793);
        assertTrue(brewDistanceCalculator.degreeToRadian(360.0) == 6.283185307179586);
    }

    @Test
    public void testToStringById(){
        System.out.println(brewDistanceCalculator.toStringById(breweries));
    }

    @Test
    public void testShortestTwoOptTrip(){
        assertTrue(distanceCalculator.shortestNearestNeighborTrip().get(12).getDistance()
                == distanceCalculator.shortestTwoOptTrip().get(12).getDistance());
        assertTrue(brewDistanceCalculator.shortestNearestNeighborTrip().get(3).getDistance()
                == brewDistanceCalculator.shortestTwoOptTrip().get(3).getDistance());
    }


    @Test
    public void testInstantiation() {
        assertNotNull(distanceCalculator);
        System.out.println("Instantiation Test Passed");
    }

    @Test
    public void testCalculateGreatCircleDistance() {
        int distance = distanceCalculator.calculateGreatCircleDistance(distanceCalculator.degreeToRadian(foCoLatitude),
                                                                        distanceCalculator.degreeToRadian(foCoLongitude),
                                                                        distanceCalculator.degreeToRadian(boraBoraLatitude),
                                                                        distanceCalculator.degreeToRadian(boraBoraLongitude));

        assertEquals(4949, distance, 2);
        System.out.println("Great Circle Distance Test Passed, calculated value was: " + Integer.toString(distance) + ", which is within a delta of 2 of 4949");
    }


    @Test
    public void testComputeNearestNeighbor(){
        DistanceCalculator.Pair ll1 = brewDistanceCalculator.computeNearestNeighbor(breweries.get(0));
        assertNotNull(ll1);
        assertEquals(ll1.getValue(),(Integer)195);
        assertEquals(ll1.getKey().get(0), breweries.get(0));
        assertNotEquals(ll1.getKey().get(1),breweries.get(0));

    }

    @Test
    public void testGetTotal(){
        ArrayList<Location> trip = brewDistanceCalculator.shortestNearestNeighborTrip();
        int sum = 0;
        for(int i=0; i<trip.size(); i++){
            sum += brewDistanceCalculator.shortestNearestNeighborTrip().get(i).getDistance();
        }
        assertTrue(sum == brewDistanceCalculator.getTotal(trip));
    }


    @Test
    public void testCalculateTrips() {
        DistanceCalculator.Pair treyPair = distanceCalculator.calculateTrips(tLocations.get(11), 11);
        DistanceCalculator.Pair mattPair = distanceCalculator.computeNearestNeighbor(tLocations.get(11));
        ArrayList<Location> treyArrayList = treyPair.getKey();
        ArrayList<Location> mattArrayList = mattPair.getKey();

        String treysTrip = distanceCalculator.toStringById(treyArrayList);
        String mattsTrip = distanceCalculator.toStringById(mattArrayList);
        int treysDistance = treyPair.getValue();
        int mattsDistance = mattPair.getValue();

        Assert.assertEquals(treysTrip, mattsTrip);
        Assert.assertEquals(treysDistance, mattsDistance);
    }

    @Test
    public void testShortestNearestNeighborTrip() {
        ArrayList<Location> shortestTrip = distanceCalculator.shortestNearestNeighborTrip();
        Assert.assertEquals("b", shortestTrip.get(0).getId());
    }

    /** Function repeats the distance between the last locations.*/
    @Test
    public void testNoOptimization() {
        ArrayList<Location> trip = distanceCalculator.noOptimization();

        Assert.assertNotNull(trip);
        Assert.assertTrue(trip.get(0).getDistance() != 0);
        Assert.assertTrue(trip.get(trip.size()-1).getDistance() != 0);
        System.out.println("Distance from last node: " + trip.get(trip.size()-1).getDistance());
        System.out.println("Total: " + distanceCalculator.getTotal(trip));
    }

}