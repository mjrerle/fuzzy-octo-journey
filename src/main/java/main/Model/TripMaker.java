package main.Model;


import java.util.ArrayList;

/**
 * Created by Trey Yu on 9/2/2017.
 */
public class TripMaker {
    private static final double EARTH_RADIUS = 6371.0088; //In Kilometers
    private static final double EARTH_RADIUS_MILES = 3958.7613; //In Kilometers

    private static final double KILOMETER_TO_MILES = EARTH_RADIUS_MILES/EARTH_RADIUS; //How many miles in one kilometer

    protected ArrayList<Location> locations = new ArrayList<>();
    private int[][] allDistances; // This 2D array will store every distance of all of the locations
    private ArrayList<Pair> permutations = new ArrayList<>();

    /**
     * Constructor that creates a Distance Calculator
     * Object and computes all of the Nearest Neighbor trips
     * of the passed in ArrayList of the Location objects
     *
     * @param locations list of Location objects to create a trip from
     * */

    public TripMaker(ArrayList<Location> locations) {
        this.locations = locations;
        //this.allDistances = calculateAllDistances();
        calculateAllNearestNeighbor();
    }


    //////////////////////////////////////////////////////////
    // Getters and Setters                                  //
    //////////////////////////////////////////////////////////
    public ArrayList<Location> getLocations() {
        return locations;
    }
    public void setLocations(ArrayList<Location> locations) {this.locations = locations;}

    //////////////////////////////////////////////////////////
    // Great Circle Distance calculation                    //
    //////////////////////////////////////////////////////////
    private int calculateGreatCircleDistance(Location node1, Location node2) {
        return calculateGreatCircleDistance(degreeToRadian(node1.getLatitude()),degreeToRadian(node1.getLongitude()),degreeToRadian(node2.getLatitude()),degreeToRadian(node2.getLongitude()));
    }

    int calculateGreatCircleDistance(double startLat, double startLong, double endLat, double endLong) {
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



    /**
     * !!Not used currently because I'm pretty sure it doesn't work!!
     *<p></p>
     * This creates a 2D array that contains all of the
     * distances from every locations to every other
     * locations. We list every location in every column and
     * every row and calculate the distance of the location
     * at the specific cross section. It should be noted
     * the diagonal from the top left to the bottom right
     * will be all 0's because the entries at those indices
     * are the distance of a location to itself.
     *
     *  2D array that contains the distances between all locations
     * */

    private void calculateAllDistances() {
        int locSize = locations.size();
        allDistances = new int[locSize][locSize];

        for(int i = 0; i < locSize; i++) {
            for (int j = 0; j < locSize; j++) {
                allDistances[i][j] = calculateGreatCircleDistance(locations.get(i), locations.get(j));
            }
        }

    }

    /**
     * Radian Conversion for Latitude and Longitude
     *
     * @param degree number that is a degree format
     * @return radian of the degree that was passed in
     * */

    double degreeToRadian(double degree) {

        return Math.toRadians(degree);

    }


    /**
     * For calulateTrips to run without getting a null pointer error
     * , calculateAllDistances must be called prior.
     */
    Pair calculateTrips(Location startNode) {

        calculateAllDistances();
        ArrayList<Location> itinerary = new ArrayList<>();
        Location currentLocation = startNode; // Starting Location
        Location nextLocation = null; // Declaration so IntelliJ stops yelling at me
        int totalDistance = 0;

        itinerary.add(startNode); // Starting location
        int currentIndex = 11;

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
                }
            }
            itinerary.add(nextLocation);
            int currentDistance = calculateGreatCircleDistance(currentLocation, nextLocation);
            assert nextLocation != null;
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


    /**
     * A method that calculates all of the Nearest Neighbor
     * trips and them in an ArrayList called permutations.
     * Permutations is an ArrayList of Pairs, where the key
     * is the Linked List object of an itinerary, and the
     * value is the total distance of that itinerary.
     */

    private void calculateAllNearestNeighbor() {

        for (Location location : locations) {
            permutations.add(computeNearestNeighbor(location));
        }

    }

    /**
     * Shortest Nearest Neighbor Trip iterates through all
     * of permutations and finds the shortest trip.
     *
     * @return The shortest trip found of all Nearest
     *         Neighbor trips
     */

    public ArrayList<Location> shortestNearestNeighborTrip() {
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



    /**
     * Shortest Two Opt Trip iterates through permutations
     * and calls Two Opt on every Nearest Neighbor trip in
     * it. It then finds the shortest two opt trip.
     *
     * @return ArrayList of the shortest twoOpt trip that is found
     */

    public ArrayList<Location> shortestTwoOptTrip() {
        ArrayList<Pair> twoOptPermutations = new ArrayList<>();

        for (Pair permutation : permutations) {
            twoOptPermutations.add(twoOpt(permutation.getKey()));
        }

        int min = Integer.MAX_VALUE;
        int index = 0;

        for(int j = 0; j < twoOptPermutations.size(); j++) {
            if(twoOptPermutations.get(j).getValue() < min) {
                min = twoOptPermutations.get(j).getValue();
                index = j;
            }
        }

        return twoOptPermutations.get(index).getKey();
    }



    /**
     * Additional method required for two opt, just a method
     * that swaps two locations.
     * @param trip unswapped Arraylist of locations
     * @param startIndex the start index to swapping values
     * @param endIndex the end index to end swapping values
     */

    private void twoOptSwap(ArrayList<Location> trip, int startIndex, int endIndex) {
        while(startIndex < endIndex) {
            Location tempLocation = trip.get(startIndex);
            trip.set(startIndex, trip.get(endIndex));
            trip.set(endIndex, tempLocation);
            startIndex++;
            endIndex--;
        }
    }


    /**
     * This method performs TwoOpt on a nearest neighbor trip
     * that is passed in. It looks for pairs that can be swapped
     * that make the distance between those points shorter and the
     * overall distance shorter
     *@param trip The ArrayList Nearest Neighbor trip that twopt is
     *            performed on.
     *@return The Pair object that contains the twoOpt trip and distance of the trip
     */

    private Pair twoOpt(ArrayList<Location> trip) {

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
        return new Pair(trip, getTotal(trip));
    }

    //////////////////////////////////////////////////////////
    // A method for setting the distance between every      //
    // location in an itinerary, specifically for 2-Opt.    //
    // This is definitely a lot of extra work that needs to //
    // be optimized.                                        //
    //////////////////////////////////////////////////////////
    /**
     * A method for setting the distance between every
     * location in an itinerary, specifically for 2-Opt.
     * This is definitely a lot of extra work that needs to
     * be optimized.
     *
     * @param itinerary ArrayList of Location objects that the
     *                  distances will be computed with
     */

    private void setLocationDistances(ArrayList<Location> itinerary) {
        for (int i = 0; i < itinerary.size() - 1; i++) {
            itinerary.get(i + 1).setDistance(calculateGreatCircleDistance(itinerary.get(i), itinerary.get(i + 1)));
        }
    }

    /**
     * Prints location objects by the location ID
     *
     * @param itinerary ArrayList of locations in a trip
     * @return string of locations numbered by index and identified by ID
     */

    String toStringById(ArrayList<Location> itinerary) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < itinerary.size(); i++) {
            result.append(Integer.toString(i)).append(": ").append(itinerary.get(i).getId()).append("\n");

        }

        return result.toString();
    }

    //////////////////////////////////////////////////////////
    // All of Matt's Code                                   //
    //////////////////////////////////////////////////////////
    Pair computeNearestNeighbor(Location node) {
        //this will return a Pair...
        // a pair is key value pair: ArrayList<Location> key, Integer value
        ArrayList<Location> unvisited = new ArrayList<>(locations);
        //must have a local copy otherwise I will modify the given arraylist!
        ArrayList<Location> visited = new ArrayList<>();
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
            for (Location anUnvisited : unvisited) {

                int distance = calculateGreatCircleDistance(anUnvisited,
                        visited.get(visited.size() - 1));
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
        last.setDistance(calculateGreatCircleDistance(visited.get(visited.size() - 1), node));
        //set distance equal to last node added to visited and the given node
        visited.add(last);
        //add the last node to the ArrayList
        sum += last.getDistance();
        //add last distance

        return new Pair(visited, sum);
        //associate the visited ArrayList and the total sum!
    }
    /**
     * This method computes all the nearest Neighbor trips for
     * a given list and returns the shortest one
     *
     * @return ArrayList of the shortest Nearest Neighbor trip
     */

    public ArrayList<Location> computeAllNearestNeighbors() {
        ArrayList<Pair> permutations = new ArrayList<>();
        //list of lists
        ArrayList<Location> shortest;
        //result
        for (Location location : locations) {
            permutations.add(computeNearestNeighbor(location));
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


    /**
     * Computes the distance of the itinerary passed in
     * @param path ArrayList of Location objects
     * @return The integer of the total distance of the trip
     * */

    int getTotal(ArrayList<Location> path) {
        int sum = 0;
        for (Location aPath : path) {
            sum += aPath.getDistance();
        }
        //this will get the distance total for a path (useful for testing purposes)
        return sum;
    }

    public static class Pair{
        //this is for keeping track of the total distance
        private ArrayList<Location> key;
        private Integer value;

        Pair(ArrayList<Location> key, Integer value) {
            this.key=key;
            this.value=value;
        }

        public Pair() {
            key = new ArrayList<>();
            value = 0;
        }

        public ArrayList<Location> getKey() {
            return key;
        }

        public void setKey(ArrayList<Location> newkey) {
            this.key=newkey;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue( Integer newValue){
            this.value=newValue;
        }
    }

    /** Repeats a distance between the last two legs of each trip*/
    public ArrayList<Location> noOptimization(){
        ArrayList<Location> result = new ArrayList<>(locations);
        Location temp = new Location(locations.get(0).getExtraInfo());
        for(int i = 0; i < result.size()-1; i++){
            result.get(i).setDistance(calculateGreatCircleDistance(result.get(i), result.get(i+1)));
        }
        result.get(result.size()-1).setDistance(calculateGreatCircleDistance(result.get(result.size()-2), result.get(result.size()-1)));
        result.add(temp);
        System.out.println("Result -2 " + result.get(result.size()-2));
        result.get(result.size()-1).setDistance(calculateGreatCircleDistance(result.get(result.size()-2), temp));
        return result;
    }
}


