package com.loribel.publications.bo;

public abstract class PublicationYoutube extends PublicationBO {

    private String videoUri;
    private String description;
    private int durationSecond;

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

	public int getDurationSecond() {
		return durationSecond;
	}

	public void setDurationSecond(int durationSecond) {
		this.durationSecond = durationSecond;
	}

    
}
