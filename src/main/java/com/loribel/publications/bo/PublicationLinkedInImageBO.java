package com.loribel.publications.bo;

public class PublicationLinkedInImageBO extends PublicationLinkedInBO {

    private String imageUri;

    public PublicationLinkedInImageBO() {
    	super();
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
