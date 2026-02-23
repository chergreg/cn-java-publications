package com.loribel.publications.bo;

public abstract class PublicationLinkedInBO extends PublicationBO {

    private String content;

    public PublicationLinkedInBO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
