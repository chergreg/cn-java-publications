package com.loribel.publications.bo;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.loribel.publications.interfaces.Publication;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = PublicationYoutubeVideoBO.class, name = "YoutubeVideo"),
		@JsonSubTypes.Type(value = PublicationYoutubeShortBO.class, name = "nYoutubeShort"),
		@JsonSubTypes.Type(value = PublicationLinkedInTextBO.class, name = "LinkedInText"),
		@JsonSubTypes.Type(value = PublicationLinkedInImageBO.class, name = "LinkedInImage"),
		@JsonSubTypes.Type(value = PublicationLinkedInPdfBO.class, name = "LinkedInPdf"),
		@JsonSubTypes.Type(value = PublicationLinkedInVideoBO.class, name = "LinkedInVideo") })
public abstract class PublicationBO implements Publication {

	private Date datePub;
	private String status;
	private String title;
	private UUID uid;

	public PublicationBO() {
	}

	public Date getDatePub() {
		return datePub;
	}

	public String getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}

	@JsonIgnore
	public abstract String getTypeInfo();

	public UUID getUid() {
		return uid;
	}

	public void setDatePub(Date datePub) {
		this.datePub = datePub;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

}
