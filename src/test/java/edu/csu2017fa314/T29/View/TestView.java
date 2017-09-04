package edu.csu2017fa314.T29.View;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class TestView
{
    private View v;
    public static void print(Object object){
        System.out.println(object);
    }
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
        v.readFile("data/test/test.csv");
        assertTrue(v.getList()!=null);
    }

    @Test
    public void testWriteFile() throws IOException{
        assertTrue(v.getList()!=null);
        v.readFile("data/test/test.csv");
        v.writeFile();
    }

    @Test
    public void testBadFile() throws IOException{
        v.readFile("data/test/test2.csv");
    }

    @Test
    public void testMisuse1() throws IOException{
        v.writeFile();
        v.readFile("data/test/test.csv");
    }

    @Test
    public void testMisuse2() throws IOException{
        v.readFile("data/test/evil.csv");
        v.writeFile();
    }

    @Test
    public void testOverflow() throws IOException{
        v.setTotalDistance(999999999);
        v.readFile("data/test/overflow.csv");
        v.writeFile();
        System.out.println(v.getTotalDistance());
    }


    @Test
    public void testUnderflow() throws IOException{
        v.setTotalDistance(-999999999);
        v.readFile("data/test/underflow.csv");
        v.writeFile();
        System.out.println(v.getTotalDistance());
    }

    @Test
    public void testTotalDistance() throws IOException{
        v.readFile("data/test/test.csv");
        v.writeFile();
        print(v.getTotalDistance());
    }
}
