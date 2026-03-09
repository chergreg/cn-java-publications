package com.loribel.publications.bo;

import java.util.Date;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PublicationYoutubeVideoBO.class, name = "YoutubeVideo"),
        @JsonSubTypes.Type(value = PublicationYoutubeShortBO.class, name = "nYoutubeShort"),
        @JsonSubTypes.Type(value = PublicationLinkedInTextBO.class, name = "LinkedInText"),
        @JsonSubTypes.Type(value = PublicationLinkedInImageBO.class, name = "LinkedInImage"),
        @JsonSubTypes.Type(value = PublicationLinkedInPdfBO.class, name = "LinkedInPdf"),
        @JsonSubTypes.Type(value = PublicationLinkedInVideoBO.class, name = "LinkedInVideo")
})
public abstract class PublicationBO {

    private UUID uid;
    private String title;
    private String status;
    private Date datePub;

    public PublicationBO() {
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
    
    public abstract String getType() ;
    
}
