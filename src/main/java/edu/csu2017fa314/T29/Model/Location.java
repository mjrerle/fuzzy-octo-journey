package edu.csu2017fa314.T29.Model;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Location {
    private double latitude;
    private double longitude;

    private int distance;

    private HashMap<String,String> extraInfo = new HashMap<>();


    public Location(Map<String,String> extraInfo){
        this.extraInfo.putAll(extraInfo); //copies values into this map
        this.latitude = coordinatesToDouble(extraInfo.get("latitude"));
        this.longitude = coordinatesToDouble(extraInfo.get("longitude"));
    }

    public void setDistance(int distance){ this.distance = distance;}

    public HashMap<String, String> getExtraInfo() {
        return extraInfo;
    }

    public String getId(){ return extraInfo.get("id");}

    public String getColumnValue(String columnName){
        columnName = columnName.toLowerCase();
        if(extraInfo.containsKey(columnName)){
            return extraInfo.get(columnName);
        }
        else{
            return "Column not found";
        }
    }

    public Set<String> getColumnNames(){
        return extraInfo.keySet();
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public int getDistance(){return this.distance;}


    // Takes a String of Latitude or Longitude and returns it in degrees as a double!
    public double coordinatesToDouble(String latLong){

        // This handles any Latitude and Longitude that are already in Decimal Degree form
        if(!(latLong.contains("N") || latLong.contains("E") || latLong.contains("S") || latLong.contains("W"))) {
            return Double.parseDouble(latLong);
        }
        String[] coordinate = coordinateParser(latLong);

        /* Since we do not know how many levels of precision we are given, there is no defined place in which
         *  the direction is within the coordinate array. However, we do know it is always the last element in
         *  a Latitude or Longitude
         */
        String direction = coordinate[coordinate.length - 1];

        double result = getCoordinatePrecision(coordinate);

        // Use direction to decide if pos. or neg.
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

    private double getCoordinatePrecision(String[] coordinate) {
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

        // Degrees should be the same number but as a double
        // Minutes and seconds will be divided by 60 and 3600 respectively, and concatenated together with degrees, for final double!
        // Decimal Degrees = degrees + (minutes/60) + (seconds/3600)
        double dDegrees = Double.parseDouble(degrees);
        double dMinutes = Double.parseDouble(minutes)/60.0;
        double dSeconds = Double.parseDouble(seconds)/3600.0;
        double dExtraPrecision = Double.parseDouble(extraPrecision)/1000*3600;
        // Concatenate everything as a double.
        return dDegrees + dMinutes + dSeconds + dExtraPrecision;
    }

    private String[] coordinateParser(String latLong) {
        // Parsing on °, ", ', " ", should provide degrees then minutes, then seconds, then direction.
        latLong = latLong.replaceAll("\\s+|\\t+", "");
        String delimiters = "°|\"|\'|”|’";
        return latLong.split(delimiters);
    }


}
