package com.robbiegay.randomwebcam.models;

public class Webcam {
    private final String url;
    private final String location;

    public Webcam(String url, String location) {
        this.url = url;
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public String getLocation() {
        return location;
    }
}
