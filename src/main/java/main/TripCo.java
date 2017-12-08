package main;

import main.View.Server;

import java.util.Objects;

public class TripCo {

    private String name = "";

    public String getName()
    {
       return name;
    }

    public String getMessage()
    {
        if (Objects.equals(name, ""))
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
