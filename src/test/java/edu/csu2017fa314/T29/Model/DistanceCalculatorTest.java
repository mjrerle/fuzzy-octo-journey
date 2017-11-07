package edu.csu2017fa314.T29.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
    ArrayList<Location> breweries = new ArrayList<Location>();

    Map<String,String> extraInfo = new HashMap<String,String>();
    LocationRecords locationRecords;
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

        breweries.add(brewery1);
        breweries.add(brewery2);
        breweries.add(brewery3);
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



    public void utilityCompute(Location A, Location B){
        locationRecords = new LocationRecords("data/test/FullTest.csv");
        locations = locationRecords.getLocations();
        DistanceCalculator d = new DistanceCalculator(locations);
        int distance =d.calculateGreatCircleDistance(d.degreeToRadian(A.getLatitude()),d.degreeToRadian(A.getLongitude()), d.degreeToRadian(B.getLatitude()),d.degreeToRadian(B.getLongitude()));
        System.out.printf("%04d ",distance);
    }

    @Test
    public void testComputeNearestNeighbor(){
        locationRecords = new LocationRecords("data/test/FullTest.csv");
        ArrayList<Location> locs = locationRecords.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        DistanceCalculator.Pair ll1 = dist.computeNearestNeighbor(locs.get(0));
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
        assertEquals(ll1.getKey().get(0),locs.get(0));
        assertNotEquals(ll1.getKey().get(1),locs.get(0));

    }

    @Test
    public void testComputeNearestNeighborCO14ers() {
        locationRecords = new LocationRecords("data/test/CO14ers.csv");
        ArrayList<Location> locs = locationRecords.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        ArrayList<Location> ll = dist.computeAllNearestNeighbors();
        int sum = 0;
        for (int i = 0; i < ll.size(); i++) {
            sum += ll.get(i).getDistance();
        }
        assertEquals(sum, 818);
    }

    @Test
    public void testComputeNearestNeighborCOrand75() {
        locationRecords = new LocationRecords("data/test/COrand75.csv");
        ArrayList<Location> locs = locationRecords.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        ArrayList<Location> ll = dist.computeAllNearestNeighbors();
        int sum = 0;
        for (int i = 0; i < ll.size(); i++) {
            sum += ll.get(i).getDistance();
        }
        assertEquals(sum, 2454);
    }

    @Test
    public void testComputeNearestNeighborCOrand50() {
        locationRecords = new LocationRecords("data/test/COrand50.csv");
        ArrayList<Location> locs = locationRecords.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        ArrayList<Location> ll = dist.computeAllNearestNeighbors();
        int sum = 0;
        for (int i = 0; i < ll.size(); i++) {
            sum += ll.get(i).getDistance();
        }
        assertEquals(sum, 1834);
    }


    @Test
    public void testComputeNearestNeighborSki() {
        locationRecords = new LocationRecords("data/test/ski.csv");
        ArrayList<Location> locs = locationRecords.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        ArrayList<Location> ll = dist.computeAllNearestNeighbors();
        int sum = 0;
        for (int i = 0; i < ll.size(); i++) {
            sum += ll.get(i).getDistance();
        }
        assertEquals(sum, 668);
    }

    @Test
    public void testComputeNearestNeighborFullTest(){
        locationRecords = new LocationRecords("data/test/FullTest.csv");
        ArrayList<Location> locs = locationRecords.getLocations();
        DistanceCalculator dist = new DistanceCalculator(locs);
        ArrayList<Location> ll = dist.computeAllNearestNeighbors();

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

    @Ignore
    public void testCalculateAllDistances() {
        DistanceCalculator distanceCalculator = new DistanceCalculator(breweries);
        locationRecords = new LocationRecords("data/test/FullTest.csv");
        ArrayList<Location> locations = locationRecords.getLocations();
        DistanceCalculator distanceCalculator1 = new DistanceCalculator(locations);

        int[][] expectedArray = { {0, 42, 68}, {42, 0, 85}, {68, 85, 0} };

        String expectedString = Arrays.deepToString(expectedArray);
        String actualString = Arrays.deepToString(distanceCalculator.calculateAllDistances());

        Assert.assertEquals(expectedString, actualString);

        int [] expectedDistances = new int[locations.size()];
        for(int i = 0; i < locations.size(); i++) {
            expectedDistances[i] = distanceCalculator1.calculateGreatCircleDistance(locations.get(0), locations.get(i));
        }

        int [] actualDistances = distanceCalculator1.allDistances[0];
        String expectedDistString = Arrays.toString(expectedDistances);
        String actualDistString = Arrays.toString(actualDistances);

        Assert.assertEquals(expectedDistString, actualDistString);
    }

    @Ignore
    @Test
    public void testCalculateTrips() {
        locationRecords = new LocationRecords("data/test/FullTest.csv");
        ArrayList<Location> locations = locationRecords.getLocations();
        DistanceCalculator distanceCalculator = new DistanceCalculator(locations);

        DistanceCalculator.Pair treyPair = distanceCalculator.calculateTrips(locations.get(67), 67);
        DistanceCalculator.Pair mattPair = distanceCalculator.computeNearestNeighbor(locations.get(67));
       ArrayList<Location> treyArrayList = treyPair.getKey();
       ArrayList<Location> mattArrayList = mattPair.getKey();

        String treysTrip = distanceCalculator.toStringByID(treyArrayList);
        String mattsTrip = distanceCalculator.toStringByID(mattArrayList);
        int treysDistance = treyPair.getValue();
        int mattsDistance = mattPair.getValue();

        Assert.assertEquals(treysTrip, mattsTrip);
        Assert.assertEquals(treysDistance, mattsDistance);
    }

    @Test
    public void testShortestNearestNeighborTrip() {
        locationRecords = new LocationRecords("data/test/FullTest.csv");
        ArrayList<Location> locations = locationRecords.getLocations();
        DistanceCalculator distanceCalculator = new DistanceCalculator(locations);

       ArrayList<Location> shortestTrip = distanceCalculator.shortestNearestNeighborTrip();

        Assert.assertEquals("lyzhu", shortestTrip.get(0).getId());
    }

}