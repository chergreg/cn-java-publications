package com.loribel.publications.bo;

import java.util.Date;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PublicationYoutubeVideo.class, name = "YOUTUBE_VIDEO"),
        @JsonSubTypes.Type(value = PublicationYoutubeShort.class, name = "YOUTUBE_SHORT"),
        @JsonSubTypes.Type(value = PublicationLinkedInText.class, name = "LINKEDIN_TEXT"),
        @JsonSubTypes.Type(value = PublicationLinkedInImage.class, name = "LINKEDIN_IMAGE"),
        @JsonSubTypes.Type(value = PublicationLinkedInPdf.class, name = "LINKEDIN_PDF"),
        @JsonSubTypes.Type(value = PublicationLinkedInVideo.class, name = "LINKEDIN_VIDEO")
})
public abstract class Publication {

    private UUID uid;
    private String title;
    private String status;
    private Date datePub;

    public Publication() {
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatePub() {
        return datePub;
    }

    public void setDatePub(Date datePub) {
        this.datePub = datePub;
    }
}
