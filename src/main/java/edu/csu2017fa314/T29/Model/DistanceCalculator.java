package edu.csu2017fa314.T29.Model;

import sun.awt.image.ImageWatched;

import java.util.*;

/**
 * Created by Trey Yu on 9/2/2017.
 */
public class DistanceCalculator {
    private static final double EARTH_RADIUS = 6371.0088; //In Kilometers
    private static final double EARTH_RADIUS_MILES = 3958.7613; //In Kilometers

    private static final double KILOMETER_TO_MILES = EARTH_RADIUS_MILES/EARTH_RADIUS; //How many miles in one kilometer

    protected ArrayList<Location> locations = new ArrayList<>();
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

    public String[][] getCalculatedDistances() {return calculateDistances(locations);}
    public void setCalculatedDistances(String[][] calculatedDistances) {this.calculatedDistances = calculatedDistances;}

    //////////////////////////////////////////////////////////
    // Create 2D array that has start location, end location//
    // and distance between them. This is processed in the  //
    // order in which the original CSV file is given in.    //
    //////////////////////////////////////////////////////////

    public String[][] calculateDistances(ArrayList<Location> locs) {
        String[][] arrayOfInfo = new String[locs.size() - 1][3];

        for(int i = 0; i < locs.size() - 1; i++) { //We go to size() - 1 because say we have 3 locations, there
                                                        //are only 2 trips between three locations

            arrayOfInfo[i][0] = locs.get(i).getId(); //The 0 column contains the start id, thus only the first and
                                                          //second id are populated here

            arrayOfInfo[i][1] = locs.get(i + 1).getId(); //The 1 column contains the end id, which is the next
                                                              //location after the start id

            //Its a shit show, I know, we'll have to see if we can make this prettier later
            double distance = calculateGreatCircleDistance(degreeToRadian(locs.get(i).getLatitude()),
                                                           degreeToRadian(locs.get(i).getLongitude()),
                                                           degreeToRadian(locs.get(i + 1).getLatitude()),
                                                           degreeToRadian(locs.get(i + 1).getLongitude()));

            arrayOfInfo[i][2] = Integer.toString((int)distance);
        }

        return arrayOfInfo;
    }

    public Pair computeNearestNeighbor(ArrayList<Location> locs, Location node){
        //this will return a Pair... a pair is key value pair: LinkedList<Location> key, Integer value
        ArrayList<Location> unvisited = (ArrayList<Location>) locs.clone();
        //must have a local copy otherwise I will modify the given arraylist!
        LinkedList<Location> visited = new LinkedList<>();
        //fill this list
        node.setDistance(0);
        //set distance of first node equal to 0
        visited.add(node);
        //add first
        unvisited.remove(node);
        //remove node
        int k=0;
        int sum=0;
        int size = unvisited.size();
        while(k<size) {
            //go through all nodes
            ArrayList<Integer> distances = new ArrayList<>();
            //keeps track of distances between nodes
            for (int i = 0; i < unvisited.size(); i++) {

                int distance = calculateGreatCircleDistance(unvisited.get(i),visited.getLast());
                distances.add(distance);
                //populate distances arraylist
            }
            //find edge lengths
            int min = Integer.MAX_VALUE;
            int index = 0;
            for (int i = 0; i < distances.size(); i++) {
                if (distances.get(i) < min) {
                    //compare each intermediate distance to min
                    min = distances.get(i);
                    //set min equal to lowest distance
                    index = i;
                    //keep track of the index
                }
            }
            //find min
            sum+=min;
            Location temp = new Location(unvisited.get(index).getExtraInfo());
            //i make a new location so that I can play with a copy of the unvisited node... this seems to work
            temp.setDistance(min);
            //set distance of temp

            visited.add(temp);
            //add temp node to visited

            unvisited.remove(unvisited.get(index));
            //remove node from unvisited, i remove the actual node!

            k++;
        }
        Location last = new Location(node.getExtraInfo());
        //create last node to complete round trip
        last.setDistance(calculateGreatCircleDistance(visited.getLast(),node));
        //set distance equal to last node added to visited and the given node
        visited.add(last);
        //add the last node to the linkedlist
        sum+=last.getDistance();
        //add last distance
//        System.out.println("Index: "+k + " Size: "+locs.size()+" Sum: "+sum);
//        System.out.print("Visited: ");
//        for (int i = 0; i < visited.size(); i++) {
//            System.out.print("("+visited.get(i).getId()+", "+visited.get(i).getDistance()+") ");
//            if(i%5==0){
//                System.out.println();
//            }
//        }
//        System.out.println();
//        System.out.print("Unvisited: ");
//        for (int i = 0; i < unvisited.size(); i++) {
//            System.out.print(unvisited.get(i).getId());
//            if(i%5==0){
//                System.out.println();
//            }
//        }
//        System.out.println();
//        System.out.println();

        return new Pair(visited,sum);
        //associate the visited linkedlist and the total sum!
    }

    public LinkedList<Location> computeAllNearestNeighbors(ArrayList<Location> locs){
        ArrayList<Pair> permutations = new ArrayList<>();
        //list of lists
        LinkedList<Location> shortest = new LinkedList<>();
        //result
        for (int i = 0; i < locs.size(); i++) {
            permutations.add(computeNearestNeighbor(locs,locs.get(i)));
            //add all possible starting points
        }
        int min = Integer.MAX_VALUE;
        int index=0;
        for (int i = 0; i < permutations.size(); i++) {
            //find lowest cumulative distance
            if(permutations.get(i).getValue()<min){
                //finding the minimum
                min=permutations.get(i).getValue();
                index=i;
                //keep track of the index
            }
        }

//        System.out.println("min: "+min + " index: "+index);
        shortest=permutations.get(index).getKey();
        //set shortest equal to the path with lowest total distance

        return shortest;
    }


    //////////////////////////////////////////////////////////
    // Great Circle Distance calculation                    //
    //////////////////////////////////////////////////////////
    public int calculateGreatCircleDistance(Location node1, Location node2){
        return calculateGreatCircleDistance(degreeToRadian(node1.getLatitude()),degreeToRadian(node1.getLongitude()),degreeToRadian(node2.getLatitude()),degreeToRadian(node2.getLongitude()));
    }

    public int calculateGreatCircleDistance(double startLat, double startLong, double endLat, double endLong) {
        double deltaLambda = Math.abs(startLong - endLong);

        double numerator = Math.pow((( Math.pow(Math.cos(endLat) * Math.sin(deltaLambda), 2)) + (Math.pow((Math.cos(startLat) * Math.sin(endLat)) -
                (Math.sin(startLat) * Math.cos(endLat) * Math.cos(deltaLambda)), 2))),0.5);
        double denominator = (Math.sin(startLat) * Math.sin(endLat)) +
                             (Math.cos(startLat) * Math.cos(endLat) * Math.cos(deltaLambda));

        double deltaSigma = Math.atan2(numerator, denominator);

        return Math.round((float)(deltaSigma * EARTH_RADIUS * KILOMETER_TO_MILES)); //This calculation is done with Kilometers.
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
    public static class Pair{
        //this is for keeping track of the total distance
        private LinkedList<Location> key;
        private Integer value;
        public Pair(LinkedList<Location> key,Integer value){
            this.key=key;
            this.value=value;
        }
        public Pair(){
            key=new LinkedList<>();
            value=0;
        }

        public LinkedList<Location> getKey() {
            return key;
        }
        public void setKey(LinkedList<Location> newkey){
            this.key=newkey;
        }
        public Integer getValue() {
            return value;
        }
        public void setValue( Integer newValue){
            this.value=newValue;
        }
    }
}


