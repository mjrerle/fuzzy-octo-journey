package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class QueryBuilder {
    private String user = "";
    private String pass = "";
    private String myDriver = "com.mysql.jdbc.Driver";
    // Add dependencies in pom.xml
    private String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314";
    // Use this line if connecting inside CSU's network

    QueryBuilder(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    /**
     * @param query a simple search like "denver" to be examined by the database
     * @return an arraylist of locations (raw order, no distances calculated)
     */
    public ArrayList<Location> query(String query) {
        // Command line args contain username and password
        ArrayList<Location> locations = new ArrayList<>();

        //String myUrl = "jdbc:mysql://localhost/cs314";
        // Use this line if tunneling 3306 traffic through shell
        try { // Connect to the database
            Class.forName(myDriver);
            connect(query, locations);
        } catch (Exception e) { // Catches all exceptions in the nested try's
            System.err.printf("Exception: ");
            System.err.println(e.getMessage());
        }
        return locations;
    }

    private void connect(String query, ArrayList<Location> locations) throws SQLException {
        try (Connection conn = DriverManager.getConnection(myUrl, user, pass)) {
            // Create a statement
            try (Statement st = conn.createStatement()) {
                // Submit a query
                submitAQuery(query, locations, st);
            }
        }
    }

    private void submitAQuery(String query,
                              ArrayList<Location> locations,
                              Statement st) throws SQLException {
        try (ResultSet rs = st.executeQuery(query)) {
            //create a hashmap from all the column names in the table
            HashMap<String, String> info = new HashMap<>();

            ResultSetMetaData md = rs.getMetaData();
            modifyHashMap(info, md);

            makeLocations(locations, rs, info);
        }
    }

    private void makeLocations(ArrayList<Location> locations,
                               ResultSet rs,
                               HashMap<String, String> info) throws SQLException {
        while (rs.next()) {
            for (String keys : info.keySet()) {
                info.put(keys, rs.getString(keys));
                //then assign the values to the column name keys
            }
            locations.add(new Location(info));
        }
    }

    private void modifyHashMap(HashMap<String, String> info,
                               ResultSetMetaData md) throws SQLException {
        int columnNumber = md.getColumnCount();
        for (int i = 1; i <= columnNumber; i++) {
            //<= because the first column is 1
            info.put(md.getColumnName(i), "");
        }
    }

}