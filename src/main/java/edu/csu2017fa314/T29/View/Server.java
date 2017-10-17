package edu.csu2017fa314.T29.View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.csu2017fa314.T29.Model.DistanceCalculator;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.LinkedList;
import edu.csu2017fa314.T29.Model.Location;

import static spark.Spark.post;

/**
 * Created by sswensen on 10/1/17.
 */

public class Server {


    public void serve() {
        Gson g = new Gson();
        post("/testing", this::queryDatabase, g::toJson); //Create new listener
    }

    private Object queryDatabase(Request rec, Response res) {
        // Set the return headers
        setHeaders(res);

        // Init json parser
        JsonParser parser = new JsonParser();

        // Grab the json body from POST
        JsonElement elm = parser.parse(rec.body());

        // Create new Gson (a Google library for creating a JSON representation of a java class)
        Gson gson = new Gson();

        // Create new Object from received JsonElement elm
        ServerRequest sRec = gson.fromJson(elm, ServerRequest.class);

        // The object generated by the frontend should match whatever class you are reading into.
        // Notice how DataClass has name and ID and how the frontend is generating an object with name and ID.
        System.out.println("Got \"" + sRec.toString() + "\" from server.");
        // Client sends query under "name" field in received json:
        String searched = sRec.getQuery();
        // Get something from the server
        QueryBuilder q = new QueryBuilder("tstroup", "Abc123def456hij"); // Create new QueryBuilder instance and pass in credentials //TODO update credentials
        String queryString = String.format("SELECT * FROM airports WHERE municipality LIKE '%s' OR name LIKE '%s' OR type LIKE '%s' LIMIT 10", searched, searched, searched);
        ArrayList<Location> queryResults = q.query(queryString);

        DistanceCalculator dist = new DistanceCalculator(queryResults);
        LinkedList<Location> itinerary = dist.computeAllNearestNeighbors();

        SVG svg = new SVG(itinerary,"/data/Background.svg");

        // Create object with svg file path and array of matching database entries to return to server
        ServerResponse sRes = new ServerResponse("/Map.svg", itinerary);

        System.out.println("Sending \"" + sRes.toString() + "\" to server.");

        //Convert response to json
        Object ret = gson.toJson(sRes, ServerResponse.class);

        /* What to return to the server.
         * In this example, the "ServerResponse" object sRes is converted into a JSON representation using the GSON library.
         * If you'd like to see what this JSON looks like, it is logged to the console in the web client.
         */
        return ret;
    }
    private void setHeaders(Response res) {
        // Declares returning type json
        res.header("Content-Type", "application/json");

        // Ok for browser to call even if different host host
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "*");
    }
}