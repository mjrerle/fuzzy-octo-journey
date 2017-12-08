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
    private Location location; // null pointer exception
    private ArrayList<Location> locations = new ArrayList<>();

    /** Creates 3 new location objects and adds them to the arrayList Locations. */
    @Before
    public void setUp(){
        info.put("id","abee");
        info.put("name","Two22 Brew");
        info.put("latitude","39°38'07\" N");
        info.put("longitude","104°45'32\" W");
        this.location = new Location(info);
        //Populate arraylist
        this.locations.add(location);
        info.put("id","abellend");
        info.put("name","Mad Jacks Mountain Brewery");
        info.put("latitude","39°24'05\" N");
        info.put("longitude","105°28'37\" W");
        this.location = new Location(info);
        //Populate arraylist
        this.locations.add(location);
        info.put("id","acwatson");
        info.put("name","Equinox Brewing");
        info.put("latitude","40°35'17\" N");
        info.put("longitude","105°4'26\" W");
        //Populate info with items from FullTest.csv
        //this.info = new HashMap<>();
        this.location = new Location(info);
        //Populate arraylist
        this.locations.add(location);
    }

    @Test
    public void testInstantiation() {
        assertNotNull(location);
        System.out.println("Instantiation Test Passed");
    }

    @Test
    public void testCorrectParsing() {
        String id = "abee";
        double latitude = 39.63527778;
        double longitude = -104.75888889;

        assertEquals(id, locations.get(0).getId());
        assertEquals(latitude, locations.get(0).getLatitude(), 1);
        assertEquals(longitude, locations.get(0).getLongitude(), 1);
    }

    @Test
    public void testHashMapReading(){
        assertEquals(locations.get(0).getId(),"abee");
        assertEquals(locations.get(1).getId(),"abellend");
        assertEquals(locations.get(2).getId(),"acwatson");

        assertEquals(locations.get(0).getColumnValue("name"),"Two22 Brew");
        assertEquals(locations.get(1).getColumnValue("name"),"Mad Jacks Mountain Brewery");
        assertEquals(locations.get(2).getColumnValue("name"),"Equinox Brewing");

        assertEquals(locations.get(0).getColumnValue("Test key not in map"),"Column not found");

    }

    @Test
    public void testKeySet(){
        Set<String> keys = new HashSet<>();
        keys.add("name");
        keys.add("id");

        keys.add("latitude");
        keys.add("longitude");

        assertEquals(locations.get(0).getColumnNames(),keys);

        keys.remove("name");

        assertNotEquals(locations.get(0).getColumnNames(),keys);
    }

}
