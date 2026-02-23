package com.loribel.publications.bo;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public abstract class PublicationYoutubeBO extends PublicationBO {

	// Identifiant unique de la chaîne YouTube
	private String channelId;

	// Nom de la chaîne YouTube
	private String channelTitle;

	// Langue par défaut de la vidéo (code ISO 639-1, ex: "en", "fr")
	private String defaultLanguage;

	// Définition (qualité) de la vidéo : "sd" ou "hd" (contentDetails.definition)
	private String definition;

	// Description textuelle de la vidéo
	private String description;

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getPublishedTime() {
		return publishedTime;
	}

	public void setPublishedTime(String publishedTime) {
		this.publishedTime = publishedTime;
	}

	// Durée de la vidéo en s
	private int duration;

	private String publishedDate; // YYYY-MM-DD
	private String publishedTime; // HH:mm:ss

	// Liste des tags associés à la vidéo
	private List<String> tags;

	// URL principale de la miniature (thumbnail) de la vidéo
	private String thumbnailsUrl;
	
	// Identifiant unique de la vidéo YouTube
	private String youtubeId;

	public PublicationYoutubeBO() {
	}

	public String getChannelId() {
		return channelId;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public String getDefinition() {
		return definition;
	}

	public String getDescription() {
		return description;
	}


	public int getDuration() {
		return duration;
	}

	public List<String> getTags() {
		return tags;
	}

	public String getThumbnailsUrl() {
		return thumbnailsUrl;
	}

	public String getYoutubeId() {
		return youtubeId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}



	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void setThumbnailsUrl(String thumbnailsUrl) {
		this.thumbnailsUrl = thumbnailsUrl;
	}

	public void setYoutubeId(String youtubeId) {
		this.youtubeId = youtubeId;
	}

}
