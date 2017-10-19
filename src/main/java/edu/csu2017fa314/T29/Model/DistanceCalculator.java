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
    protected int[][] allDistances; // This 2D array will store every distance of all of the locations
    protected ArrayList<Pair> permutations = new ArrayList<>();

    //////////////////////////////////////////////////////////
    // Constructor                                          //
    //////////////////////////////////////////////////////////
    public DistanceCalculator(ArrayList<Location> locations) {
        this.locations = locations;
        this.allDistances = calculateAllDistances();
        calculateAllNearestNeighbor();
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
    // This Calculate Trips method calculates the itinerary //
    // through a Dynamic Programming approach. We have a 2D //
    // array that will help us determine the next closest   //
    // neighbor (see calculateAllDistances for 2D array     //
    // info). Once a location is selected, we add it to the //
    // itinerary, and then search all of its distance and   //
    // find the smallest, non-zero, distance. We also check //
    // if the column index of that distance already exist in//
    // the itinerary. Once that location is compared with   //
    // every other location in our list and no smaller value//
    // exist, then we add that location to the itinerary and//
    // update the "current" location to that new location.  //
    //////////////////////////////////////////////////////////
    public Pair calculateTrips (Location startNode, int startIndex) {
        LinkedList<Location> itinerary = new LinkedList<Location>();
        Location currentLocation = startNode; // Starting Location
        Location nextLocation = null; // Declaration so IntelliJ stops yelling at me
        int totalDistance = 0;

        itinerary.add(startNode); // Starting location
        int currentIndex = startIndex;

        while(itinerary.size() < locations.size()) {
            int currentMin = Integer.MAX_VALUE;
            int tempDist;
            int tempIndex = 0;

            for (int i = 0; i < locations.size(); i++) {
                tempDist = allDistances[currentIndex][i];

                /* In the following comparison, we ignore currentIndex
                   because when i == currentIndex, the distance will be
                   to itself, or 0.
                 */
                if (tempDist < currentMin && i != currentIndex
                        && (!itinerary.contains(locations.get(i)))) {
                    tempIndex = i;
                    currentMin = tempDist;
                    nextLocation = locations.get(i);
                } else {
                    //Do nothing, keep parsing the list
                }
            }
            itinerary.add(nextLocation);
            int currentDistance = calculateGreatCircleDistance(currentLocation, nextLocation);
            nextLocation.setDistance(currentDistance);
            currentLocation = nextLocation;
            currentIndex = tempIndex;
            totalDistance += currentDistance;

        }

        // I will comment on this later, its make the trip round and completed by readding the starting node
        int finalLegDist = calculateGreatCircleDistance(itinerary.get(locations.size() - 1), startNode);
        totalDistance += finalLegDist;
        itinerary.add(startNode);
        itinerary.get(itinerary.size() - 1).setDistance(finalLegDist);
        return new Pair(itinerary, totalDistance);
    }

    //////////////////////////////////////////////////////////

    public void calculateAllNearestNeighbor() {

        for(int i = 0; i < locations.size(); i++) {
            permutations.add(calculateTrips(locations.get(i), i));
        }

    }
    //////////////////////////////////////////////////////////
    // This Shortest Trip calculates all of the possible    //
    // trips based on different starting locations, which   //
    // are the different "permutations" of the trip. We then//
    // find the shortest permutation and return it.         //
    //////////////////////////////////////////////////////////
    public LinkedList<Location> shortestNearestNeighborTrip() {
        int min = Integer.MAX_VALUE;
        int index = 0;

        for(int j = 0; j < locations.size(); j++) {
            if(permutations.get(j).getValue() < min) {
                min = permutations.get(j).getValue();
                index = j;
            }
        }

        return permutations.get(index).getKey();

    }

    public LinkedList<Location> shortestTwoOptTrip() {
        ArrayList<Pair> twoOptPermutations = new ArrayList<>();

        for(int i = 0; i < permutations.size(); i++) {
            twoOptPermutations.add(twoOpt(permutations.get(i).key));
        }

        int min = Integer.MAX_VALUE;
        int index = 0;

        for(int j = 0; j < twoOptPermutations.size(); j++) {
            if(twoOptPermutations.get(j).getValue() < min) {
                min = permutations.get(j).getValue();
                index = j;
            }
        }

        return twoOptPermutations.get(index).getKey();
    }
    //////////////////////////////////////////////////////////

    public void twoOptSwap(LinkedList<Location> trip, int startIndex, int endIndex) {
        while(startIndex < endIndex) {
            Location tempLocation = trip.get(startIndex);
            trip.set(startIndex, trip.get(endIndex));
            trip.set(endIndex, tempLocation);
            startIndex++;
            endIndex--;
        }
    }

    //////////////////////////////////////////////////////////
    // 2-Opt?                                               //
    //////////////////////////////////////////////////////////
    public Pair twoOpt(LinkedList<Location> trip) {

        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (int i = 0; i < trip.size() - 3; i++) {
                for (int j = i+2; j <= trip.size() - 2; j++) {
                    int delta = -calculateGreatCircleDistance(trip.get(i), trip.get(i + 1))
                                -calculateGreatCircleDistance(trip.get(j), trip.get(j + 1))
                                +calculateGreatCircleDistance(trip.get(i), trip.get(j))
                                +calculateGreatCircleDistance(trip.get(i + 1), trip.get(j + 1));

                    if(delta < 0) {
                        twoOptSwap(trip, i + 1, j);
                        improvement = true;
                    }
                }
            }
        }

        setLocationDistances(trip);
        System.out.println(getTotal(trip));
        return new Pair(trip, getTotal(trip));
    }

    public void setLocationDistances(LinkedList<Location> itinerary) {
        for(int i = 0; i < itinerary.size() - 1; i++) {
            itinerary.get(i + 1).setDistance(calculateGreatCircleDistance(itinerary.get(i), itinerary.get(i + 1)));
        }
    }
    //////////////////////////////////////////////////////////
    // toString method(s)                                   //
    //////////////////////////////////////////////////////////
    public String toStringByID (LinkedList<Location> itinerary) {
        String result = "";

        for (int i = 0; i < itinerary.size(); i++) {
            result += Integer.toString(i) + ": " + itinerary.get(i).getId() + "\n";

        }

        return result;
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

        //System.out.println("min: "+min + " index: "+index);
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


