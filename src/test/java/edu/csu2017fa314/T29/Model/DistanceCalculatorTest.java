package edu.csu2017fa314.T29.Model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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

    ArrayList<Location> locations = new ArrayList<Location>();

    Map<String,String> extraInfo = new HashMap<String,String>();


    Location brewery1 = new Location("abee", "Two22 Brew","39°38'07\" N", "104°45'32\" W", extraInfo);
    Location brewery2 = new Location("abellend" ,"Mad Jacks Mountain Brewery","39°24'05\" N", "105°28'37\" W",extraInfo);
    Location brewery3 = new Location("acwatson","Equinox Brewing","40°35'17\" N", "105°4'26\" W",extraInfo);
    Location loc1 = new Location("a","","40.854","-104.371",extraInfo);
    Location loc2 = new Location("b","","40.972","-104.376",extraInfo);

    @Test
    public void testInstantiation() {
        DistanceCalculator distanceCalculator = new DistanceCalculator(locations);

        assertNotNull(distanceCalculator);
        System.out.println("Instantiation Test Passed");

    }

    @Test
    public void testCalculateGreatCircleDistance() {

        DistanceCalculator distanceCalculator = new DistanceCalculator(locations);
        int distance = distanceCalculator.calculateGreatCircleDistance(distanceCalculator.degreeToRadian(foCoLatitude),
                                                                        distanceCalculator.degreeToRadian(foCoLongitude),
                                                                        distanceCalculator.degreeToRadian(boraBoraLatitude),
                                                                        distanceCalculator.degreeToRadian(boraBoraLongitude));

        assertEquals(4949, distance, 2);
        System.out.println("Great Circle Distance Test Passed, calculated value was: " + Integer.toString(distance) + ", which is within a delta of 2 of 4949");
    }

    @Test
    public void testTotalDistance() {
        LocationRecords testObject = new LocationRecords("data/test/FullTest.csv"); // Only first-5.csv should work.
        locations = testObject.getLocations();
        DistanceCalculator distanceCalculator = new DistanceCalculator(locations);

        String[][] infoArray = distanceCalculator.calculateDistances(locations);

        int totalDistance = distanceCalculator.totalDistance(infoArray);

        assertEquals(9319, totalDistance);
        System.out.println("Total Distance Test Passed, calculated value was: " + totalDistance + ", and expected value was 4283 (RAND25 TEST)");
    }

    public void utilityCompute(Location A, Location B){
        LocationRecords t = new LocationRecords("data/test/FullTest.csv");
        locations= t.getLocations();
        DistanceCalculator d = new DistanceCalculator(locations);
        int distance =d.calculateGreatCircleDistance(d.degreeToRadian(A.getLatitude()),d.degreeToRadian(A.getLongitude()), d.degreeToRadian(B.getLatitude()),d.degreeToRadian(B.getLongitude()));
        System.out.printf("%04d ",distance);
    }

    @Test
    public void testComputeNearestNeighborNN(){
        LocationRecords t = new LocationRecords("data/test/nn.csv");
        ArrayList<Location> locs = t.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        Location a = dist.computeNearestNeighbor(new Location("a","","0","0",extraInfo),locs);
        Location b = dist.computeNearestNeighbor(new Location("b","","0","1",extraInfo),locs);
        Location c = dist.computeNearestNeighbor(new Location("c","","1","0",extraInfo),locs);
        Location d = dist.computeNearestNeighbor(new Location("d","","1","1",extraInfo),locs);
        Location e = dist.computeNearestNeighbor(new Location("e","","1","2",extraInfo),locs);
        Location f = dist.computeNearestNeighbor(new Location("f","","2","1",extraInfo),locs);
        Location g = dist.computeNearestNeighbor(new Location("g","","2","2",extraInfo),locs);
        System.out.println("  a\t   b \tc  \t d    e    f    g");
        System.out.print("a ");utilityCompute(a,a);        utilityCompute(a,b);        utilityCompute(a,c);        utilityCompute(a,d);        utilityCompute(a,e);        utilityCompute(a,f);        utilityCompute(a,g);
        System.out.println();
        System.out.print("b ");utilityCompute(b,a);        utilityCompute(b,b);        utilityCompute(b,c);        utilityCompute(b,d);        utilityCompute(b,e);        utilityCompute(b,f);        utilityCompute(b,g);
        System.out.println();
        System.out.print("c ");utilityCompute(c,a);        utilityCompute(c,b);        utilityCompute(c,c);        utilityCompute(c,d);        utilityCompute(c,e);        utilityCompute(c,f);        utilityCompute(c,g);
        System.out.println();
        System.out.print("d ");utilityCompute(d,a);        utilityCompute(d,b);        utilityCompute(d,c);        utilityCompute(d,d);        utilityCompute(d,e);        utilityCompute(d,f);        utilityCompute(d,g);
        System.out.println();
        System.out.print("e ");utilityCompute(e,a);        utilityCompute(e,b);        utilityCompute(e,c);        utilityCompute(e,d);        utilityCompute(e,e);        utilityCompute(e,f);        utilityCompute(e,g);
        System.out.println();
        System.out.print("f ");utilityCompute(f,a);        utilityCompute(f,b);        utilityCompute(f,c);        utilityCompute(f,d);        utilityCompute(f,e);        utilityCompute(f,f);        utilityCompute(f,g);
        System.out.println();
        System.out.print("g ");utilityCompute(g,a);        utilityCompute(g,b);        utilityCompute(g,c);        utilityCompute(g,d);        utilityCompute(g,e);        utilityCompute(g,f);        utilityCompute(g,g);
        System.out.println();


        assertEquals(a.getId(),"b");
        assertEquals(b.getId(), "a");
        assertEquals(c.getId(),"a");
        assertEquals(d.getId(),"b");
        assertEquals(e.getId(),"d");
        assertEquals(f.getId(),"d");
        assertEquals(g.getId(),"e");

    }

    @Test
    public void testComputeNearestNeighborFullTest(){
        LocationRecords t = new LocationRecords("data/test/FullTest.csv");
        ArrayList<Location> locs = t.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);

        Location a = dist.computeNearestNeighbor(new Location("a","","0","0",extraInfo),locs);
        Location b = dist.computeNearestNeighbor(new Location("b","","999","999",extraInfo),locs);
        Location c = dist.computeNearestNeighbor(new Location("c","","-999","-999",extraInfo),locs);
        Location d = dist.computeNearestNeighbor(new Location("d","","999","-999",extraInfo),locs);
        Location e = dist.computeNearestNeighbor(new Location("e","","-999","999",extraInfo),locs);
        assertEquals(a.getId(),"alnolte");
        assertEquals(b.getId(), "alnolte");
        assertEquals(c.getId(),"rcox");
        assertEquals(d.getId(),"retzlaff");
        assertEquals(e.getId(),"rcox");
    }
    /* THIS DOES NOT ACTUALLY HAVE A TEST BECAUSE THERE IS NO ASSERTION TEST FOR 2D ARRAYS!!
     * We will have to come back to this and make our on assertion test that iterates through the 2d array have
     * have it call equals() on each element of the array */
    @Test
    public void testCalculateDistance(){
        locations.add(brewery1);
        locations.add(brewery2);
        locations.add(brewery3);

        DistanceCalculator distanceCalculator = new DistanceCalculator(locations);
        String[][] infoArray = distanceCalculator.calculateDistances(locations);

        String[][] expectedArray = new String[][]{
                {"abee", "abellend", "42"},
                {"abellend, acwatson", "85"}
        };

        System.out.println(Arrays.deepToString(infoArray));
        System.out.println(Arrays.deepToString(expectedArray));
        //assertTrue(Arrays.deepEquals(infoArray, expectedArray));
    }
}