package edu.csu2017fa314.T29.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Set;

import java.util.HashSet;
import static org.junit.Assert.*;

public class LocationTest{

    private HashMap<String,String> info = new HashMap<>();
    private Location location = new Location(info);
    private ArrayList<Location> locations = new ArrayList<>();

    @Before
    public void setUp(){
        //Populate info with items from FullTest.csv
        this.info = new HashMap<>();
        this.location = new Location(info);
        //Populate arraylist
        this.locations = new ArrayList<>();
    }

    @Test
    public void testInstantiation() {
        assertNotNull(location);
        System.out.println("Instantiation Test Passed");
    }

    @Test
    public void testCorrectParsing() {
        String ID = "abee";
        double latitude = 39.63527778;
        double longitude = -104.75888889;

        assertEquals(ID, location.getId());
        assertEquals(latitude, location.getLatitude(), 1);
        assertEquals(longitude, location.getLongitude(), 1);
    }

    @Test
    public void testHashMapReading(){
        assertEquals(locations.get(0).getId(),"abee");
        assertEquals(locations.get(1).getId(),"abellend");
        assertEquals(locations.get(2).getId(),"acwatson");
        assertEquals(locations.get(3).getId(),"acyaeger");
        assertEquals(locations.get(4).getId(),"adamep3");

        assertEquals(locations.get(0).getColumnValue("name"),"Two22 Brew");
        assertEquals(locations.get(1).getColumnValue("name"),"Mad Jacks Mountain Brewery");
        assertEquals(locations.get(2).getColumnValue("name"),"Equinox Brewing");
        assertEquals(locations.get(3).getColumnValue("name"),"Elevation Beer Company");
        assertEquals(locations.get(4).getColumnValue("name"),"Echo Brewing Company");

        assertEquals(locations.get(0).getColumnValue("elevation"),"5872");
        assertEquals(locations.get(1).getColumnValue("elevaTion"),"9580");
        assertEquals(locations.get(2).getColumnValue("eLevation"),"4988");
        assertEquals(locations.get(3).getColumnValue("ELEVATION"),"9317");
        assertEquals(locations.get(4).getColumnValue("elevation"),"6791");

        assertEquals(locations.get(0).getColumnValue("Test key not in map"),"Column not found");

    }

    @Test
    public void testKeySet(){
        Set<String> keys = new HashSet<>();
        keys.add("name");
        keys.add("id");
        keys.add("city");
        keys.add("latitude");
        keys.add("longitude");
        keys.add("elevation");
        assertEquals(locations.get(0).getColumnNames(),keys);

        keys.remove("name");

        assertNotEquals(locations.get(0).getColumnNames(),keys);
    }

}
