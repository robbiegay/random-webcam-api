package com.robbiegay.randomwebcam.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class WebcamResponse {
    private List<Map<String,String>> webcams;
    private Map<String,String> firstWebcam;
    private String resultsWebcamsId;
    private String resultsWebcamsStatus;
    private String resultsWebcamsTitle;
    private int resultsTotal;


    @SuppressWarnings("unchecked")
    @JsonProperty("result")
    private void unpackNested(Map<String,Object> result) {
        this.resultsTotal = (int)result.get("total");
        this.webcams = (List<Map<String,String>>)result.get("webcams");
        this.firstWebcam = webcams.get(0);

        this.resultsWebcamsId = firstWebcam.get("id");
        this.resultsWebcamsStatus = firstWebcam.get("status");
        this.resultsWebcamsTitle = firstWebcam.get("title");
    }

    public String getId() {
        return resultsWebcamsId;
    }

    public String getStatus() {
        return resultsWebcamsStatus;
    }

    public String getTitle() {
        return resultsWebcamsTitle;
    }
}
