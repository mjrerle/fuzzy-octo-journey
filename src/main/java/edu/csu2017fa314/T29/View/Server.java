package edu.csu2017fa314.T29.View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.csu2017fa314.T29.Model.DistanceCalculator;
import edu.csu2017fa314.T29.Model.Location;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.post;

/**
 * Created by sswensen on 10/1/17.
 */

/*class description:
*
* serve():: set up listener on /testing and /download
*   event listener on /testing sends a json response calling the testing method
*   event listener on /download sends RAW data response calling the download method
*
* download(Request, Response)::
*   parses the request json object sent from the client
*   make a gson object using the server request template
*   attach file to the header (to send back to the client)
*   write file calling writeFile... crazy!
*
* writeFile(Response, ArrayList<String>)::
*   make a json with the data sent from the server
*   this data will look like ():
*       {"title":"The Coolest Trip",
*           "destinations":["code1","code2","code3"]}
*   print to file
*
*testing(Request, Response)::
*   capture the request json and make a gson object using the server request template
*   look at the request(type) and determine whether to query, upload, or plan
*   query means call serveQuery(String query)
*   upload is serveUpload(ArrayList<String> description)
*   plan is more ambiguous with working with the current API: (will elaborate more in the method)
*       serveSVG(String opcode, ArrayList<String> locations)
*       to compensate with no extra fields in the response class and to make it easier for Trey...
*          to give me the data,
*          basically I assume if the response type is not query or upload, that I want to plan.
*       The problem arises with the way
*          we do the client/server interaction
*       Trey fetches only twice and expects all of the info
*           by the end of the second fetch.
*           This means I have to be creative and by creative I mean hack a solution
*       If Trey sets the request equal to the opcode,
*           then we can bypass nonlocal variables
*           (I would have to store the opcode when changed and
*           reflect the change in another serve method)
*       It also means that I'm given all of the information
*           I need when he sends me the location IDs
*
*   serveSvg(String, ArrayList<String>)::
*       most action occurs here
*       take the arraylist of codes
*       query the database with the codes
*           (accounting for the joins and whatnot)
*       now with all of the codes converted to locations,
*           we are able to make an itinerary
*       basically take the opcode (in the parameter list)
*           and apply an optimization level
*       now i have a list of locations in sorted(or non sorted)
 *          order and an svg. All i have to do is construct a response
*       return the constructed response
*       send:
*           {response:"svg", contents:String, width:int, height:int, locations:LinkedList<Location>}
*
*   buildWithCode(ArrayList<String>)::
*       construct a query with the codes given
*       have to account for multiple tables
*       utility method more or less because I have to call it in multiple methods
*
*   serveUpload(ArrayList<String>)::
*       construct a query with the arraylist with buildWithCode
*       set response type to upload
*       same response as serveQuery
*       send:
*           {response:"upload", locations:ArrayList<Location>, columns: Object[]}
*
*   serveQuery(String)::
*       construct a query with the string looking across all tables
*       respond with an arraylist of results
*       send:
*           {response:"query", locations:ArrayList<Location>, columns:new Object[1]}
*
*   setHeaders(Response)::
*       sets the post header (as normal, make sure its sending a json etc)
*       uses Response
*
*   setHeadersFile(Response)::
*       attach a file to the header
*       uses Response.raw()
*
* */

/**
 * web server just for fun
 */
public class Server {
    /**
     * set up event listeners
     */
    public void serve() {
        Gson g = new Gson();

        post("/testing", (rec, res) -> {
            return g.toJson(testing(rec, res));
        });
        post("/download", (rec, res) -> {
            // call the download function
            download(rec, res);
            // return the raw HttpServletResponse from the Response
            // Note that we do not send a JSON
            return rec.raw();
        });
    }

    /**
     * @param rec : request from the client, consists of a "request" : String,
     *            "description" : ArrayList key pairs
     * @param res : response from the server, consists of a RawHttpServletResponse
     * @return raw HttpServletResponse with attached file
     */
    private Object download(Request rec, Response res) {
        // As before, parse the request and convert it to a Java class with Gson:
        JsonParser parser = new JsonParser();
        JsonElement elm = parser.parse(rec.body()); //parse the rec
        Gson gson = new Gson();
        ServerRequest srec = gson.fromJson(elm, ServerRequest.class);
        //make a gson with the serverrequest.class template

        // Sending a file back requires different response headers
        setHeadersFile(res); //attach file to response
        // Write a file to the response
        writeFile(res, srec.getDescription()); //save to file

        return res;
    }

    /**
     * @param res       : passed from download,
     *                  will be a HttpServletResponse type with an attached file
     * @param locations : ArrayList of locations passed from the client
     */
    private void writeFile(Response res, ArrayList<String> locations) {
        try {
            // Write our file directly to the response rather than to a file
            PrintWriter fileWriter = new PrintWriter(res.raw().getOutputStream());
            // Ideally, the user will be able to name their own trips. We hard code it here:
            fileWriter.println("{ \"title\" : \"The Coolest Trip\",\n"
                    + "  \"destinations\" : [");
            for (int i = 0; i < locations.size(); i++) {
                if (i < locations.size() - 1) {
                    fileWriter.println("\"" + locations.get(i) + "\",");
                } else {
                    fileWriter.println("\"" + locations.get(i) + "\"]}");
                }
            }
            // Important: flush and close the writer or a blank file will be sent
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param rec : raw json passed from the client
     * @param res : template that will eventually be returned
     *            listen on a specific port and create a dynamic response based on the input
     * @return a serve: basically an event handler based on the "request"
     *              value passed from the client
     */
    private Object testing(Request rec, Response res) {
        setHeaders(res);

        // Init json parser
        JsonParser parser = new JsonParser();

        // Grab the json body from POST
        JsonElement elm = parser.parse(rec.body());

        // Create new Gson (a Google library for creating a JSON representation of a java class)
        Gson gson = new Gson();

        // Create new Object from received JsonElement elm
        // Note that both possible requests have the same format (see app.js)
        ServerRequest srec = gson.fromJson(elm, ServerRequest.class);

        // The object generated by the frontend should match whatever class you are reading into.
        // Notice how DataClass has name and ID and how the frontend is generating an object with name and ID.
        System.out.println("Got \"" + srec.toString() + "\" from server.");

        // Because both possible requests from the client have the same format,
        // we can check the "type" of request we've received: either "query" or "svg"
        if (srec.getRequest().equals("query")) {
            // Set the return headers
            return serveQuery(srec.getDescription().get(0));
            // if the user uploads a file
        } else if (srec.getRequest().equals("upload")) {
            return serveUpload(srec.getDescription());
            // assume that I am only getting an array of codes
            // assume if the request is not "query" it is "svg":
        } else {
            //serve the svg with the information in description (should be a bunch of destinations)
            return serveSvg(srec.getOp_level(), srec.getDescription());
            //0 should be the opcode, next is my dests
        }
    }

    // called by testing method if the client requests an svg

    /**
     * "description" is basically an arraylist of destinations,
     * in this case we expect it to be the destination's codes
     *
     * @param opcode : assumes that the "request" value is an opcode,
     *               that is, an optimization level (none, nearest neighbor, 2 opt, 3 opt)
     * @param locs   : the "description" value passed from the client.
     *               must be an array of locations
     * @return an SVGResponse which is a response consisting of an svg and an array of locations
     */
    private Object serveSvg(String opcode, ArrayList<String> locs) {
        Gson gson = new Gson();
        // Instead of writing the SVG to a file,
        // we send it in plaintext back to the client to be rendered inline
        // assumes that the user has input a query first
        QueryBuilder queryBuilder = new QueryBuilder("mjrerle", "829975763");
        String queryString = buildWithCode(locs);
        ArrayList<Location> temp = queryBuilder.query(queryString);
        DistanceCalculator distanceCalculator = new DistanceCalculator(temp);

        ArrayList<Location> locations; //will be changed to ArrayList
        if (opcode.equals("Nearest Neighbor")) { //opcode is dependent on trey's code
            //figure out which method to call... depends on Tim's code
            locations = distanceCalculator.shortestNearestNeighborTrip();
        } else if (opcode.equals("2-Opt")) {
            //what is this method?
            locations = distanceCalculator.shortestTwoOptTrip();
        } else if (opcode.equals("3-Opt")) {
            //what is this method?
            locations = new ArrayList<>();
        } else {
            //opcode is likely "none"
            // so I want the raw order->need a method for this in Distance Calculator
            locations = new ArrayList<>();
        }

        //
        SVG svg = new SVG(locations);
        String map = svg.getContents();
        int wid = (int) svg.getWidth();
        int hei = (int) svg.getHeight();
        ServerSvgResponse ssres = new ServerSvgResponse(wid, hei, map, locations);

        return gson.toJson(ssres, ServerSvgResponse.class);
    }

    /**
     * @param locations : "description" value passed from the client,
     *                  will consist of "code" for each
     *                  destination, we use it to build a query
     *                  string to pass to the sql server
     * @return the built query string (ex: select {code} where code = '{code[i]}')
     */
    private String buildWithCode(ArrayList<String> locations) {
        //makes locations given an arraylist of codes(ids like AXHS), quite useful

        String queryString = "SELECT airports.*, countries.*, regions.*, continents.* ";
        queryString += "FROM continents INNER JOIN countries ";
        queryString += "ON continents.code = countries.continent INNER JOIN regions ";
        queryString += "ON countries.code = regions.iso_country INNER JOIN airports ";
        queryString += "ON regions.code = airports.iso_region ";
        queryString += "WHERE ";
        for (int i = 0; i < locations.size(); i++) {
            if (i == locations.size() - 1) {
                queryString += "code = '" + locations.get(i) + "';";
            } else {
                queryString += "code = '" + locations.get(i) + "' OR ";
            }
        }
        return queryString;
    }

    /**
     * @param locations : "description" value passed from the client, consists of codes
     * @return a ServerResponse, similar to a query request
     */
    // if the user uploads a file
    private Object serveUpload(ArrayList<String> locations) {
        Gson gson = new Gson();
        QueryBuilder queryBuilder = new QueryBuilder("mjrerle", "829975763");

        // Build a query of every code in the destinations file:
        String queryString = buildWithCode(locations);

        // Query database with queryString
        ArrayList<Location> queryResults = queryBuilder.query(queryString);
        SVG svg = new SVG(queryResults);
        // Same response structure as the query request
        int wid = (int)svg.getWidth();
        int hei = (int)svg.getHeight();
        String contents = svg.getContents();
        ServerSvgResponse serverSvgResponse = new ServerSvgResponse(wid,hei,contents,queryResults);

        return gson.toJson(serverSvgResponse, ServerSvgResponse.class);
    }

    // called by testing method if client requests a search

    /**
     *
     * @param searched : input from event
     * @return appropriate query
     */

    private String makeQuery(String searched){
        String queryString = "SELECT airports.*, countries.*, regions.*, continents.* ";
        queryString += "FROM continents INNER JOIN countries ";
        queryString += "ON continents.code = countries.continent INNER JOIN regions ";
        queryString += "ON countries.code = regions.iso_country INNER JOIN airports ";
        queryString += "ON regions.code = airports.iso_region ";
        queryString += "WHERE municipality LIKE '%"
                + searched + "%' " + " OR airports.name LIKE '%"
                + searched + "%' " + " OR airports.type LIKE '%"
                + searched + "%'";
        return queryString;
    }

    /**
     * search the database for the input
     * The Object[] parameter is useful for Trey
     * because he needs the keys in order to show attributes, may as well give it to him here
     *
     * @param searched : input passed from the client (e.target.value)
     * @return a ServerResponse, but also give it the optional Object[] parameter
     */

    private Object serveQuery(String searched) {
        Gson gson = new Gson();
        QueryBuilder queryBuilder = new QueryBuilder("mjrerle", "829975763");
        // Create new QueryBuilder instance and pass in credentials
        String queryString = makeQuery(searched);
        ArrayList<Location> queryResults = queryBuilder.query(queryString);
        if (queryResults.size() == 0) {
            System.out.println("Size of query results = 0, try a better search");
            Object err = gson.toJson(new ServerResponse(null));
            //return an empty object json for error handling on the client side
            return err;
        }

        // Create object with svg file path and array of matching database entries to return to server
        HashMap<String, String> map = queryResults.get(0).getExtraInfo();
        Object columns[] = map.keySet().toArray();
        ServerResponse sres = new ServerResponse(queryResults, columns);
        sres.setResponseType("query");
        System.out.println("Sending \"" + sres.toString() + "\" to server.");

        //Convert response to json
        return gson.toJson(sres, ServerResponse.class);
    }

    /**
     * @param res : a HttpServletResponse
     *            configures the POST reply
     */
    private void setHeaders(Response res) {
        // Declares returning type json
        res.header("Content-Type", "application/json");

        // Ok for browser to call even if different host host
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "*");
    }

    /**
     * attach a file (selection.json) and do a force download on the client
     *
     * @param res : a HttpServletResponse, we want the raw form
     */
    private void setHeadersFile(Response res) {
        /* Unlike the other responses, the file request sends back an actual file. This means
        that we have to work with the raw HttpServletRequest that Spark's Response class is built
        on.
         */
        // First, add the same Access Control headers as before
        res.raw().addHeader("Access-Control-Allow-Origin", "*");
        res.raw().addHeader("Access-Control-Allow-Headers", "*");
        // Set the content type to "force-download." Basically, we "trick" the browser with
        // an unknown file type to make it download the file instead of opening it.
        res.raw().setContentType("application/force-download");
        res.raw().addHeader("Content-Disposition", "attachment; filename=\"selection.json\"");
    }
}