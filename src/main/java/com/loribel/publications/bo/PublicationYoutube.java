package com.loribel.publications.bo;

public abstract class PublicationYoutube extends Publication {

    private String videoUri;
    private String description;
    private int durationSec;

    public PublicationYoutube() {
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(int durationSec) {
        this.durationSec = durationSec;
    }
}
