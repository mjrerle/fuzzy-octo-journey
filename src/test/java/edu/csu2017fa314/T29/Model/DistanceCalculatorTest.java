package edu.csu2017fa314.T29.Model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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

    Location brewery1 = new Location("abee" , "Two22 Brew" , "Centennial ", "39°38'07\" N", "104°45'32\" W" ,"5872");
    Location brewery2 = new Location("abellend" ,"Mad Jacks Mountain Brewery","Bailey","39°24'05\" N", "105°28'37\" W","9580");
    Location brewery3 = new Location("acwatson","Equinox Brewing","Fort Collins","40°35'17\" N", "105°4'26\" W", "4988");

    @Test
    public void testInstantiation() {
        DistanceCalculator distanceCalculator = new DistanceCalculator(locations);

        assertNotNull(distanceCalculator);
        System.out.println("Instantiation Test Passed");

    }

    @Test
    public void testCalculateGreatCircleDistance() {
        LocationRecords testObject = new LocationRecords("first-5.csv"); // Only first-5.csv should work.

        ArrayList<Location> locations = testObject.getLocations();

        DistanceCalculator distanceCalculator = new DistanceCalculator(locations);
        int distance = distanceCalculator.calculateGreatCircleDistance(distanceCalculator.degreeToRadian(foCoLatitude),
                                                                        distanceCalculator.degreeToRadian(foCoLongitude),
                                                                        distanceCalculator.degreeToRadian(boraBoraLatitude),
                                                                        distanceCalculator.degreeToRadian(boraBoraLongitude));

        assertEquals(4949, distance, 2);
        System.out.println("Test Passed, calculated value was: " + Integer.toString(distance) + ", which is within a delta of 2 of 4949");
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
        String[][] infoArray = distanceCalculator.calculateDistances();

        String[][] expectedArray = new String[][]{
                {"abee", "abellend", "42"},
                {"abellend, acwatson", "85"}
        };

        System.out.println(Arrays.deepToString(infoArray));
        System.out.println(Arrays.deepToString(expectedArray));
        //assertTrue(Arrays.deepEquals(infoArray, expectedArray));
    }
}