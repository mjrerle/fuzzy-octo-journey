package edu.csu2017fa314.T29.Model;
public class Location {
    String id;
    String name;
    String city;
    double latitude;
    double longitude;
    double elevation;

    public Location(String id, String name, String city, String latitude, String longitude, String elevation){
        setId(id); // this.id = id;
        setName(name); // this.name = name;
        setCity(city); // this.city = city;
        setLatitude(latitude); // this.latitude = coordinatesToDouble(latitude);
        setLongitude(longitude); // this.longitude = coordinatesToDouble(longitude);
        setElevation(elevation); // this.elevation = Double.parseDouble(elevation);
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setLatitude(String latitude){
        this.latitude = coordinatesToDouble(latitude);
    }

    public void setLongitude(String longitude){
        this.longitude = coordinatesToDouble(longitude);
    }

    public void setElevation(String elevation){
        this.elevation = Double.parseDouble(elevation);
    }
    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getCity(){
        return this.city;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double getElevation(){
        return this.elevation;
    }

    // Takes a String of Latitude or Longitude and returns it in degrees as a double!
    public double coordinatesToDouble(String latLong){
        // Parsing on °, ", ', " ", should provide degrees then minutes, then seconds, then direction.
        latLong.replaceAll("\\s+", "");
        String delimiters = "°|\"|\'|\\s+ ";
        String[] coordinate = latLong.split(delimiters);
        String degrees = coordinate[0];
        String minutes = coordinate[1];
        String seconds = coordinate[2];
        String direction = coordinate[3];
        // Use direction to decide if pos. or neg.
        // Degrees should be the same number but as a double
        // Minutes and seconds will be divided by 60 and 3600 respectively, and concatenated together with degrees, for final double!
        // Decimal Degrees = degrees + (minutes/60) + (seconds/3600)
        double dDegrees = Double.parseDouble(degrees);
        double dMinutes = Double.parseDouble(minutes)/60.0;
        double dSeconds = Double.parseDouble(seconds)/3600.0;
        // Concatenate everything as a double.
        double result = dDegrees + dMinutes + dSeconds;
        // If South or West make negative!
        if(direction.equalsIgnoreCase(" S") || direction.equalsIgnoreCase(" W")){
            result  *= -1.0;
        } else if(direction.equalsIgnoreCase(" N") || direction.equalsIgnoreCase(" E")){
            // Do nothing, keep positive!
        } else {
            System.out.println("Direction not in correct format!");
        }
        return result;
    }


}
