package edu.csu2017fa314.T29.View;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class TestView
{
    private View v;

    @Before
    public void setUp() throws Exception 
    {
        v = new View();
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
    public void testReadFile() throws IOException{
        v.readFile("C:\\Users\\matte\\workspace\\cs314\\T29\\src\\test\\java\\edu\\csu2017fa314\\T29\\View\\test.csv");
        assertTrue(v.getList()!=null);
    }

    @Test
    public void testWriteFile() throws IOException{
        assertTrue(v.getList()!=null);
        v.readFile("C:\\Users\\matte\\workspace\\cs314\\T29\\data\\test\\test.csv");
        v.writeFile();
    }

    @Test
    public void testTotalDistance() throws IOException{
        v.readFile("C:\\Users\\matte\\workspace\\cs314\\T29\\data\\test\\test.csv");
        v.writeFile();
        System.out.println(v.getTotalDistance());
    }
}
