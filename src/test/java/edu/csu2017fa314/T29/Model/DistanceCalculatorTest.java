package edu.csu2017fa314.T29.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
    Location brewery1;
    Location brewery2;
    Location brewery3;
    Location t1;Location t2;Location t3; Location t4; Location t5;Location t6; Location t7;
    Location t8; Location t9; Location t01; Location t02; Location t03;

    @Before
    public void setUp(){
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
        extraInfo.put("id","a");
        extraInfo.put("latitude","0");
        extraInfo.put("longitude","0");
        t1 = new Location(extraInfo);
        extraInfo.put("id","b");
        extraInfo.put("latitude","0");
        extraInfo.put("longitude","1");
        t2 = new Location(extraInfo);
        extraInfo.put("id","c");
        extraInfo.put("latitude","1");
        extraInfo.put("longitude","0");
        t3 = new Location(extraInfo);
        extraInfo.put("id","d");
        extraInfo.put("latitude","1");
        extraInfo.put("longitude","1");
        t4 = new Location(extraInfo);
        extraInfo.put("id","e");
        extraInfo.put("latitude","1");
        extraInfo.put("longitude","2");
        t5 = new Location(extraInfo);
        extraInfo.put("id","f");
        extraInfo.put("latitude","2");
        extraInfo.put("longitude","1");
        t6 = new Location(extraInfo);
        extraInfo.put("id","g");
        extraInfo.put("latitude","2");
        extraInfo.put("longitude","2");
        t7 = new Location(extraInfo);
        extraInfo.put("id","a1");
        extraInfo.put("latitude","0");
        extraInfo.put("longitude","0");
        t8 = new Location(extraInfo);
        extraInfo.put("id","b1");
        extraInfo.put("latitude","999");
        extraInfo.put("longitude","999");
        t9 = new Location(extraInfo);
        extraInfo.put("id","c1");
        extraInfo.put("latitude","-999");
        extraInfo.put("longitude","-999");
        t01 = new Location(extraInfo);
        extraInfo.put("id","d1");
        extraInfo.put("latitude","999");
        extraInfo.put("longitude","-999");
        t02 = new Location(extraInfo);
        extraInfo.put("id","e1");
        extraInfo.put("latitude","-999");
        extraInfo.put("longitude","999");
        t03 = new Location(extraInfo);


    }


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
    public void testComputeNearestNeighbor(){
        LocationRecords t = new LocationRecords("data/test/FullTest.csv");
        ArrayList<Location> locs = t.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        DistanceCalculator.Pair ll1 = dist.computeNearestNeighbor(locs,locs.get(0));
//        System.out.println();
//        System.out.println("TEST result: ");
//        for (int i=0;i<ll.getKey().size();i++){
//            System.out.print(ll.getKey().get(i).getId()+" ");
//            if(i%5==0){
//                System.out.println();
//            }
//        }
//        System.out.println();
//        System.out.println("Size: "+ll.getKey().size());
        assertNotNull(ll1);
        assertEquals(ll1.getValue(),(Integer)1696);
        assertEquals(ll1.getKey().getFirst(),locs.get(0));
        assertNotEquals(ll1.getKey().get(1),locs.get(0));

    }

    @Test
    public void testComputeNearestNeighborCO14ers() {
        LocationRecords t = new LocationRecords("data/test/CO14ers.csv");
        ArrayList<Location> locs = t.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        LinkedList<Location> ll = dist.computeAllNearestNeighbors(locs);
        int sum = 0;
        for (int i = 0; i < ll.size(); i++) {
            sum += ll.get(i).getDistance();
        }
        assertEquals(sum, 818);
    }

    @Test
    public void testComputeNearestNeighborCOrand75() {
        LocationRecords t = new LocationRecords("data/test/COrand75.csv");
        ArrayList<Location> locs = t.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        LinkedList<Location> ll = dist.computeAllNearestNeighbors(locs);
        int sum = 0;
        for (int i = 0; i < ll.size(); i++) {
            sum += ll.get(i).getDistance();
        }
        assertEquals(sum, 2454);
    }

    @Test
    public void testComputeNearestNeighborCOrand50() {
        LocationRecords t = new LocationRecords("data/test/COrand50.csv");
        ArrayList<Location> locs = t.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        LinkedList<Location> ll = dist.computeAllNearestNeighbors(locs);
        int sum = 0;
        for (int i = 0; i < ll.size(); i++) {
            sum += ll.get(i).getDistance();
        }
        assertEquals(sum, 1834);
    }


    @Test
    public void testComputeNearestNeighborSki() {
        LocationRecords t = new LocationRecords("data/test/ski.csv");
        ArrayList<Location> locs = t.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        LinkedList<Location> ll = dist.computeAllNearestNeighbors(locs);
        int sum = 0;
        for (int i = 0; i < ll.size(); i++) {
            sum += ll.get(i).getDistance();
        }
        assertEquals(sum, 668);
    }

    @Test
    public void testComputeNearestNeighborFullTest(){
        LocationRecords t = new LocationRecords("data/test/FullTest.csv");
        ArrayList<Location> locs = t.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        LinkedList<Location> ll = dist.computeAllNearestNeighbors(locs);
        int sum=0;
        for (int i = 0; i < ll.size(); i++) {
            sum+=ll.get(i).getDistance();
        }
        assertEquals(sum,1600);
//        for (int i = 0; i < ll.size(); i++) {
//            System.out.print(ll.get(i).getId()+" ");
//            if(i%5==0){
//                System.out.println();
//            }
//        }
//        System.out.println();
//        System.out.println("Size: "+ll.size());
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