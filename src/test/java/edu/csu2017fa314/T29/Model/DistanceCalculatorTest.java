package edu.csu2017fa314.T29.Model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by treyy on 9/2/2017.
 */
public class DistanceCalculatorTest {

    @Test
    public void testExistence() {
        DistanceCalculator testObject = new DistanceCalculator();
        assertNotNull(testObject);
        System.out.println("Existence Test Passed");
    }

    @Test
    public void testGlobalString() {
        DistanceCalculator testObject = new DistanceCalculator();
        String globalString = testObject.globalStringTest;
        assertEquals("Testing Objects", globalString);
        System.out.println("Global String Test Passed");
    }

}