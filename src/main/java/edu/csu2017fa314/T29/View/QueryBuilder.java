package edu.csu2017fa314.T29.View;

import edu.csu2017fa314.T29.Model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class QueryBuilder {
    private String user = "";
    private String pass = "";

    public QueryBuilder(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public ArrayList<Location> query(String query) { // Command line args contain username and password
        ArrayList<Location> locations = new ArrayList<>();
        String myDriver = "com.mysql.jdbc.Driver"; // Add dependencies in pom.xml
        String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314"; // Use this line if connecting inside CSU's network
        //String myUrl = "jdbc:mysql://localhost/cs314"; // Use this line if tunneling 3306 traffic through shell
        try { // Connect to the database
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, user, pass);
            try { // Create a statement
                Statement st = conn.createStatement();
                try { // Submit a query
                    ResultSet rs = st.executeQuery(query);
                    try { //create a hashmap from all the column names in the table
                        HashMap<String, String> info = new HashMap<>();

                        ResultSetMetaData md = rs.getMetaData();
                        int columnNumber = md.getColumnCount();
                        for(int i = 1; i <= columnNumber;i++){ //<= because the first column is 1
                            info.put(md.getColumnName(i),"");
                        }

                        while(rs.next()) {
                            for(String keys:info.keySet()){
                                info.put(keys,rs.getString(keys));//then assign the values to the column name keys
                            }
                            locations.add(new Location(info));
                        }
                    } finally {
                        rs.close();
                    }
                } finally {
                    st.close();
                }
            } finally {
                conn.close();
            }
        } catch(Exception e) { // Catches all exceptions in the nested try's
            System.err.printf("Exception: ");
            System.err.println(e.getMessage());
        }
        return locations;
    }

}