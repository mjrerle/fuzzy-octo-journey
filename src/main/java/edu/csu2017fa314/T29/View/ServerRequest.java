package edu.csu2017fa314.T29.View;

/**
 * Created by sswensen on 10/1/17.
 */

public class ServerRequest {
    private String query = "";
    private String op_level = "";

    public ServerRequest(String query, String op_level) {
        this.query = query;
        this.op_level = op_level;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getOpLevel() {
        return op_level;
    }

    public void setOpLevel(String id) {
        this.op_level = id;
    }

    @Override
    public String toString() {
        return "Request{" +
                "query='" + query + '\'' +
                ", id='" + op_level + '\'' +
                '}';
    }
}