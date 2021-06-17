package com.robbiegay.randomwebcam.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class WebcamPlayer {
    private List<Map<String,Object>> webcams;
    private Map<String,Object> firstWebcam;
    private String resultsWebcamsId;
    private String resultsWebcamsStatus;
    private String resultsWebcamsTitle;
    private int resultsTotal;
    private Map<String,Object> url;


    @SuppressWarnings("unchecked")
    @JsonProperty("result")
    private void unpackNested(Map<String,Object> result) {
        this.resultsTotal = (int)result.get("total");
        this.webcams = (List<Map<String,Object>>)result.get("webcams");
        this.firstWebcam = webcams.get(0);

        this.resultsWebcamsId = (String)firstWebcam.get("id");
        this.resultsWebcamsStatus = (String)firstWebcam.get("status");
        this.resultsWebcamsTitle = (String)firstWebcam.get("title");

        //this.url = firstWebcam.get("url");

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

    /*public Object getUrl() {
        //return url;
    }*/
}

/*

firstWebcam:
{
    "id": "1565261762",
    "status": "active",
    "title": "Zelezna Ruda: Rezidence Javor - Železná Ruda centrum",
    "url":
    {
        "current": {
            "desktop": "https://www.windy.com/webcams/1565261762",
            "mobile": "https://www.windy.com/webcams/1565261762"
        },
        "edit": "https://www.windy.com/webcams/1565261762",
        "daylight": {
            "desktop": "https://www.windy.com/webcams/1565261762",
            "mobile": "https://www.windy.com/webcams/1565261762"
        }
    }
}
 */
