package edu.csu2017fa314.T29;

import edu.csu2017fa314.T29.Model.DistanceCalculator;
import edu.csu2017fa314.T29.Model.Location;
import edu.csu2017fa314.T29.Model.LocationRecords;
import edu.csu2017fa314.T29.View.SVG;
import edu.csu2017fa314.T29.View.Server;
import edu.csu2017fa314.T29.View.View;
import sun.awt.image.ImageWatched;

import java.io.IOException;
import java.util.LinkedList;

public class TripCo {

    private String name = "";

    public String getName()
    {
       return name;
    }

    public String getMessage()
    {
       if (name == "")
       {
          return "Hello!";
       }
       else
       {
          return "Hello " + name + "!";
       }
    }

    public void setName(String name)
    {
       this.name = name;
    }

    public static void main(String[] args) {


        Server s = new Server();
        s.serve();
    }

}
