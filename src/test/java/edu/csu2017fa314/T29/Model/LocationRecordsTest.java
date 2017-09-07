package edu.csu2017fa314.T29.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LocationRecordsTest{

    @Test
    public void testLocationRecords(){
        LocationRecords testObject = new LocationRecords("test.csv");
        assertNotNull(testObject);
        System.out.println("Test Passed");
    }

}