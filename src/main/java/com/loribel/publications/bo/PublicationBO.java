package com.loribel.publications.bo;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.loribel.publications.interfaces.Publication;
import com.loribel.publications.interfaces.SocialNetwork;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = PublicationYoutubeVideoBO.class, name = "YoutubeVideo"),
		@JsonSubTypes.Type(value = PublicationYoutubeShortBO.class, name = "nYoutubeShort"),
		@JsonSubTypes.Type(value = PublicationLinkedInTextBO.class, name = "LinkedInText"),
		@JsonSubTypes.Type(value = PublicationLinkedInImageBO.class, name = "LinkedInImage"),
		@JsonSubTypes.Type(value = PublicationLinkedInPdfBO.class, name = "LinkedInPdf"),
		@JsonSubTypes.Type(value = PublicationLinkedInVideoBO.class, name = "LinkedInVideo") })
public abstract class PublicationBO implements Publication {

	private Date datePub;

	private SocialNetwork socialNetwork;

	private String status;

	private String title;

	private UUID uid;

	public PublicationBO() {
	}

	@Override
	public Date getDatePub() {
		return datePub;
	}

	@Override
	public SocialNetwork getSocialNetwork() {
		return socialNetwork;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	@JsonIgnore
	public abstract String getTypeInfo();

	@Override
	public UUID getUid() {
		return uid;
	}

	@Override
	public void setDatePub(Date datePub) {
		this.datePub = datePub;
	}

	@Override
	public void setSocialNetwork(SocialNetwork socialNetwork) {
		this.socialNetwork = socialNetwork;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	public void setUid(UUID uid) {
		this.uid = uid;
	}

}
