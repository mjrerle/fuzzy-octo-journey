package edu.csu2017fa314.T29.Model;
import java.util.Map;
import java.util.HashMap;

public class Location {
    String id;
    String name;
    double latitude;
    double longitude;

    Map<String,String> extraInfo = new HashMap<String,String>();


    public Location(String id,String name, String latitude, String longitude, Map<String,String> extraInfo){
        setId(id); // this.id = id;
        setName(name); // this.name = name;
        //setCity(city); // this.city = city;
        setLatitude(latitude); // this.latitude = coordinatesToDouble(latitude);
        setLongitude(longitude); // this.longitude = coordinatesToDouble(longitude);
        //setElevation(elevation); // this.elevation = Double.parseDouble(elevation);
        setExtraInfo(extraInfo); //this.extraInfo.putAll(extraInfo) copies values into this map
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(String latitude){this.latitude = coordinatesToDouble(latitude); }

    public void setLongitude(String longitude){
        this.longitude = coordinatesToDouble(longitude);
    }

    public void setExtraInfo(Map<String,String> extraInfo){ this.extraInfo.putAll(extraInfo);}

    public String getId(){
        return this.id;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    // Takes a String of Latitude or Longitude and returns it in degrees as a double!
    public double coordinatesToDouble(String latLong){
        // Parsing on °, ", ', " ", should provide degrees then minutes, then seconds, then direction.
        latLong = latLong.replaceAll("\\s+|\\t+", "");

        // This handles any Latitude and Longitude that are already in Decimal Degree form
        if(!(latLong.contains("N") || latLong.contains("E") || latLong.contains("S") || latLong.contains("W"))) {
            return Double.parseDouble(latLong);
        }

        String delimiters = "°|\"|\'|”|’";
        String[] coordinate = latLong.split(delimiters);

        String degrees = "0";
        String minutes = "0";
        String seconds = "0";
        String extraPrecision = "0";

        // Base on how many levels of precision provided, we provide that many decimal places
        switch(coordinate.length) {
            case 5:
                extraPrecision = coordinate[3];
            case 4:
                seconds = coordinate[2];
            case 3:
                minutes = coordinate[1];
            case 2:
                degrees = coordinate[0];
                break;
            default:
                System.out.println("Da fuq?!?!?!");
                break;
        }

        /* Since we do not know how many levels of precision we are given, there is no defined place in which
        *  the direction is within the coordinate array. However, we do know it is always the last element in
        *  a Latitude or Longitude
        */
        String direction = coordinate[coordinate.length - 1];

        // Use direction to decide if pos. or neg.
        // Degrees should be the same number but as a double
        // Minutes and seconds will be divided by 60 and 3600 respectively, and concatenated together with degrees, for final double!
        // Decimal Degrees = degrees + (minutes/60) + (seconds/3600)
        double dDegrees = Double.parseDouble(degrees);
        double dMinutes = Double.parseDouble(minutes)/60.0;
        double dSeconds = Double.parseDouble(seconds)/3600.0;
        double dExtraPrecision = Double.parseDouble(extraPrecision)/1000*3600;
        // Concatenate everything as a double.
        double result = dDegrees + dMinutes + dSeconds + dExtraPrecision;
        // If South or West make negative!
        if(direction.equalsIgnoreCase("S") || direction.equalsIgnoreCase("W")){
            result  *= -1.0;
        } else if(direction.equalsIgnoreCase("N") || direction.equalsIgnoreCase("E")){
            // Do nothing, keep positive!
        } else {
            System.out.println("Direction not in correct format!");
        }
        return result;
    }



}
