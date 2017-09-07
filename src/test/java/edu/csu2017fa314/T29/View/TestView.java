package edu.csu2017fa314.T29.View;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;


public class TestView
{
    private View v;
    private static String[][] array = {{"test1","test2","100"},{"test2","test3","100"}, {"test3","test4","100"}};
    private static void print(Object object){
        System.out.println(object);
    }

    public static void printArray(){
        for (String[] anArray : array) {
            for (String anAnArray : anArray) {
                print(anAnArray);
            }
        }
    }

    @Before
    public void setUp()
    {
        v = new View(array);
        assertTrue(v.getTotalDistance()==0);
    }

    @Test 
    public void testSetDistance() 
    {
        v.setTotalDistance(4);
        assertTrue(v.getTotalDistance() == 4);
        v.setTotalDistance(-4);
        assertTrue(v.getTotalDistance() == -4);
    }

    @Test
    public void testParseItinerary(){
        setUp();
        v.parseItinerary();
    }

    @Test
    public void testWriteFile(){
        setUp();
        v.parseItinerary();
        try {
            v.writeFile("data/out/itinerary.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTotalDistance() throws IOException{
        setUp();
        v.parseItinerary();
        v.writeFile("data/out/itinerary.json");
        print(v.getTotalDistance());
    }
}
