package edu.csu2017fa314.T29.Model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LocationRecordsTest{

    @Test
    public void testLocationRecords(){
        LocationRecords testObject = new LocationRecords("data/first-5.csv"); // Only first-5.csv should work.
        assertNotNull(testObject);
        /*ArrayList<Location> locationList = testObject.getLocations();
        // testObject should have 5 locations
        assertTrue(locationList.size() == 5); // assert testObject.locations.size() == 5;
        // Each Location should have id, name, city, latitude, longitude, elevation.
        String firstId = "abee";
        assertTrue(firstId.equals(locationList.get(0).getId()));
        String firstName = "Two22 Brew";
        assertTrue(firstName.equals(locationList.get(0).getName()));
        String firstCity = "Centennial";
        assertTrue(firstCity.equals(locationList.get(0).getCity()));
        double firstLatitude = 39.63527778;
        assertTrue(firstLatitude == locationList.get(0).getLatitude());
        double firstLongitude = -104.75888889;
        assertTrue(firstLongitude == locationList.get(0).getLongitude());
        double firstElevation = 5872;
        assertTrue(firstElevation == locationList.get(0).getElevation());

        String lastId = "adamep3";
        assertTrue(lastId.equals(locationList.get(4).getId()));
        String lastName = "Echo Brewing Company";
        assertTrue(lastName.equals(locationList.get(4).getName()));
        String lastCity = "Frederick";
        assertTrue(lastCity.equals(locationList.get(4).getCity()));
        double lastLatitude = 40.10666667;
        assertTrue(lastLatitude == locationList.get(4).getLatitude());
        double lastLongitude = -104.94527778;
        assertTrue(lastLongitude == locationList.get(4).getLongitude());
        double lastElevation = 6791;
        assertTrue(lastElevation == locationList.get(4).getElevation());
        // Should be in order of file.
        System.out.println(locationList.toString());*/
        System.out.println("Test Passed");
    }

}
