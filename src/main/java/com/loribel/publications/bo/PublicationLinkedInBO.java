package com.loribel.publications.bo;

import com.loribel.publications.interfaces.SocialNetwork;

public abstract class PublicationLinkedInBO extends PublicationBO {

	private String content;

	public PublicationLinkedInBO() {
		setSocialNetwork(SocialNetwork.LINKEDIN);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
