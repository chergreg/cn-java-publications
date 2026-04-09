package com.loribel.publications.interfaces;

import java.util.Date;
import java.util.UUID;

public interface Publication {

	Date getDatePub();

	String getStatus();

	String getTitle();

	String getTypeInfo();

	UUID getUid();

	void setDatePub(Date datePub);

	void setStatus(String status);

	void setTitle(String title);
}