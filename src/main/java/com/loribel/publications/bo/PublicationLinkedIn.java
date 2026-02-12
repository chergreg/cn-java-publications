package com.loribel.publications.bo;

public abstract class PublicationLinkedIn extends Publication {

    private String content;

    public PublicationLinkedIn() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
