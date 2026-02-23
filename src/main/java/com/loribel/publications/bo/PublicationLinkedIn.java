package com.loribel.publications.bo;

public abstract class PublicationLinkedIn extends PublicationBO {

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
