package edu.csu2017fa314.T29.Model;

import java.util.ArrayList;

/**
 * Created by Trey Yu on 9/2/2017.
 */
public class DistanceCalculator {
    private static final double EARTH_RADIUS = 6371.0088; //In Kilometers
    private static final double KILOMETER_TO_MILES = 0.621371; //How many miles in one kilometer

    protected ArrayList<Location> locations = new ArrayList<Location>();
    protected String[][] calculatedDistances;

    //////////////////////////////////////////////////////////
    // Constructor                                          //
    //////////////////////////////////////////////////////////

    public DistanceCalculator(ArrayList<Location> locations) {
        this.locations = locations;
    }

    //////////////////////////////////////////////////////////
    // Some Getter and Setter for shits and giggles         //
    //////////////////////////////////////////////////////////

    public ArrayList<Location> getLocations () {return locations;}
    public void setLocations(ArrayList<Location> locations) {this.locations = locations;}

    public String[][] getCalculatedDistances() {return calculateDistances();}
    public void setCalculatedDistances(String[][] calculatedDistances) {this.calculatedDistances = calculatedDistances;}

    //////////////////////////////////////////////////////////
    // Create 2D array that has start location, end location//
    // and distance between them. This is processed in the  //
    // order in which the original CSV file is given in.    //
    //////////////////////////////////////////////////////////

    public String[][] calculateDistances() {
        String[][] arrayOfInfo = new String[locations.size() - 1][3];

        for(int i = 0; i < locations.size() - 1; i++) { //We go to size() - 1 because say we have 3 locations, there
                                                        //are only 2 trips between three locations

            arrayOfInfo[i][0] = locations.get(i).getId(); //The 0 column contains the start id, thus only the first and
                                                          //second id are populated here

            arrayOfInfo[i][1] = locations.get(i + 1).getId(); //The 1 column contains the end id, which is the next
                                                              //location after the start id

            //Its a shit show, I know, we'll have to see if we can make this prettier later
            double distance = calculateGreatCircleDistance(degreeToRadian(locations.get(i).getLatitude()),
                                                           degreeToRadian(locations.get(i).getLongitude()),
                                                           degreeToRadian(locations.get(i + 1).getLatitude()),
                                                           degreeToRadian(locations.get(i + 1).getLongitude()));

            arrayOfInfo[i][2] = Integer.toString((int)distance);
        }

        return arrayOfInfo;
    }

    //////////////////////////////////////////////////////////
    // Great Circle Distance calculation                    //
    //////////////////////////////////////////////////////////

    public int calculateGreatCircleDistance(double startLat, double startLong, double endLat, double endLong) {
        double deltaLambda = Math.abs(startLong - endLong);

        double arg1 = Math.pow(Math.cos(endLat) * Math.sin(deltaLambda), 2);
        double arg2 = Math.pow((Math.cos(startLat) * Math.sin(endLat)) -
                                (Math.sin(startLat) * Math.cos(endLat) * Math.cos(deltaLambda)), 2);

        double numerator = Math.sqrt(arg1 + arg2);
        double denominator = (Math.sin(startLat) * Math.sin(endLat)) +
                             (Math.cos(startLat) * Math.cos(endLat) * Math.cos(deltaLambda));

        double deltaSigma = Math.atan2(numerator, denominator);

        return (int)Math.round(deltaSigma * EARTH_RADIUS * KILOMETER_TO_MILES); //This calculation is done with Kilometers.
                                                                           //then multiplied to be converted into miles
                                                                           //and rounds it to the nearest whole number
    }

    //////////////////////////////////////////////////////////
    // Radian Conversion for Latitude and Longitude         //
    //////////////////////////////////////////////////////////

    public double degreeToRadian (double degree) {

        return Math.toRadians(degree);

    }

    //////////////////////////////////////////////////////////
    // Adds all distances in the 3rd column of the 2D array //
    // that we create so we know the total distance of an   //
    // Itinerary                                            //
    //////////////////////////////////////////////////////////

    public int totalDistance(String[][] array) {
        int totalDistance = 0;
        for(int i = 0; i < array.length; i++) {
            totalDistance += Integer.parseInt(array[i][2]);
        }
        return totalDistance;
    }
}


