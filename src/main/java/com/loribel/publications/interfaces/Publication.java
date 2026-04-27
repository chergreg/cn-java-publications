package com.loribel.publications.interfaces;

import java.util.Date;
import java.util.UUID;

public interface Publication {

	Date getDatePub();

	SocialNetwork getSocialNetwork();

	String getStatus();

	String getTitle();

	String getTypeInfo();

	UUID getUid();

	void setDatePub(Date datePub);

	void setSocialNetwork(SocialNetwork socialNetwork);

	void setStatus(String status);

	void setTitle(String title);
}