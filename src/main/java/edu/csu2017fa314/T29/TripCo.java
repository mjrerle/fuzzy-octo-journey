package edu.csu2017fa314.T29;

import edu.csu2017fa314.T29.View.Server;

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
