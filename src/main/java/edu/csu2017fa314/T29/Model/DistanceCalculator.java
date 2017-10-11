package edu.csu2017fa314.T29.Model;

import java.util.*;

/**
 * Created by Trey Yu on 9/2/2017.
 */
public class DistanceCalculator {
    private static final double EARTH_RADIUS = 6371.0088; //In Kilometers
    private static final double EARTH_RADIUS_MILES = 3958.7613; //In Kilometers

    private static final double KILOMETER_TO_MILES = EARTH_RADIUS_MILES/EARTH_RADIUS; //How many miles in one kilometer

    protected ArrayList<Location> locations = new ArrayList<>();
    protected int[][] allDistances; // This 2D array will store every distance of all of


    //////////////////////////////////////////////////////////
    // Constructor                                          //
    //////////////////////////////////////////////////////////
    public DistanceCalculator(ArrayList<Location> locations) {
        this.locations = locations;
    }


    //////////////////////////////////////////////////////////
    // Getters and Setters                                  //
    //////////////////////////////////////////////////////////
    public ArrayList<Location> getLocations () {return locations;}
    public void setLocations(ArrayList<Location> locations) {this.locations = locations;}


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
    // This creates a 2D array that contains all of the     //
    // distances from every locations to every other        //
    // locations. We list every location in every column and//
    // every row and calculate the distance of the location //
    // at the specific cross section. It should be noted    //
    // the diagonal from the top left to the bottom right   //
    // will be all 0's because the entries at those indices //
    // are the distance of a location to itself.            //
    //////////////////////////////////////////////////////////
    public int[][] calculateAllDistances() {
        int locSize = locations.size();
        allDistances = new int[locSize][locSize];

        for(int i = 0; i < locSize; i++) {
            for (int j = 0; j < locSize; j++) {
                allDistances[i][j] = calculateGreatCircleDistance(locations.get(i), locations.get(j));
            }
        }

        return allDistances;
    }
    //////////////////////////////////////////////////////////
    // Radian Conversion for Latitude and Longitude         //
    //////////////////////////////////////////////////////////
    public double degreeToRadian (double degree) {

        return Math.toRadians(degree);

    }

    //////////////////////////////////////////////////////////
    // Let's try this...                                    //
    //////////////////////////////////////////////////////////

    public LinkedList<Location> shortestTrip () {
        LinkedList<Location> itinerary = new LinkedList<Location>();
        Location nextLocation;

        itinerary.add(locations.get(0)); // Starting location
        int currentIndex = 0; // Starts at 0
        int min = Integer.MAX_VALUE;

        for(int i = 0; i < locations.size(); i++) {
            
        }

        return itinerary;
    }

    //////////////////////////////////////////////////////////
    // All of Matt's Code                                   //
    //////////////////////////////////////////////////////////
    public Pair computeNearestNeighbor(Location node){
        //this will return a Pair... a pair is key value pair: LinkedList<Location> key, Integer value
        ArrayList<Location> unvisited = new ArrayList<>(locations);
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

        return new Pair(visited,sum);
        //associate the visited linkedlist and the total sum!
    }

    public LinkedList<Location> computeAllNearestNeighbors(){
        ArrayList<Pair> permutations = new ArrayList<>();
        //list of lists
        LinkedList<Location> shortest;
        //result
        for (int i = 0; i < locations.size(); i++) {
            permutations.add(computeNearestNeighbor(locations.get(i)));
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



    public int getTotal(LinkedList<Location> path){
        int sum=0;
        for (int i = 0; i < path.size(); i++) {
            sum+=path.get(i).getDistance();
        }
        //this will get the distance total for a path (useful for testing purposes)
        return sum;
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


