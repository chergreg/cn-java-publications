package com.loribel.publications.interfaces;

import java.time.LocalDate;

import javafx.stage.Window;

public interface PublicationActions {

	void changeViewDay(LocalDate date);

	void changeViewList(LocalDate date);

	void changeViewMonth(LocalDate date);

	void changeViewWeek(LocalDate date);

	void createPublication(LocalDate date);

	void deletePublication(Publication publication);

	void editPublication(Publication publication);

	void setParentWindow(Window parentWindow);

	void viewPublication(Publication publication);
}