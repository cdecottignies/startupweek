package fr.ulille.iut.agile.client_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Response {
    private Response[] children;
    private String descFR;
    private String code;
    private boolean leaf;
    private double similarity;

    public Response(Response[] children, String descFR, String code, boolean leaf, double similarity) {
        this.children = children;
        this.descFR = descFR;
        this.code = code;
        this.leaf = leaf;
        this.similarity = similarity;
    }

    public Response(String id, Double similarity){
        this.code = id;
        this.similarity = similarity;
    }

    public Response(){
        this.code = "";
        this.similarity = 0.0;
    }

    public String getCode() {
        return code;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setCode(String id) {
        this.code = id;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public Response[] getChildren() {
        return children;
    }

    public void setChildren(Response[] children) {
        this.children = children;
    }

    public String getDescFR() {
        return descFR;
    }

    public void setDescFR(String descFR) {
        this.descFR = descFR;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public Response jsonToResponse(JSONObject obj) throws JSONException {
        return new Response(null, obj.getString("descFR"), obj.getString("code"), obj.getBoolean("leaf"), obj.getDouble("similarity"));
    }
}
