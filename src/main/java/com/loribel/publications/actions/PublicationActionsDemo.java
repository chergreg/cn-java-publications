package com.loribel.publications.actions;

import java.time.LocalDate;

import com.loribel.publications.interfaces.Publication;
import com.loribel.publications.interfaces.PublicationActions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Window;

public class PublicationActionsDemo implements PublicationActions {

	private Window parentWindow;

	@Override
	public void changeViewDay(LocalDate date) {
		showDialog("Change View", "Day view : " + formatDate(date));
	}

	@Override
	public void changeViewList(LocalDate date) {
		showDialog("Change View", "List view : " + formatDate(date));
	}

	@Override
	public void changeViewMonth(LocalDate date) {
		showDialog("Change View", "Month view : " + formatDate(date));
	}

	@Override
	public void changeViewWeek(LocalDate date) {
		showDialog("Change View", "Week view : " + formatDate(date));
	}

	@Override
	public void createPublication(LocalDate date) {
		showDialog("Create Publication", "Date : " + formatDate(date));
	}

	@Override
	public void deletePublication(Publication publication) {
		showPublicationDialog("Delete Publication", publication);
	}

	@Override
	public void editPublication(Publication publication) {
		showPublicationDialog("Edit Publication", publication);
	}

	private String formatDate(LocalDate date) {
		return date == null ? "null" : date.toString();
	}

	private String safe(String value) {
		return value == null ? "" : value;
	}

	@Override
	public void setParentWindow(Window parentWindow) {
		this.parentWindow = parentWindow;
	}

	private void showDialog(String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("PublicationActionsDemo");
		alert.setHeaderText(header);
		alert.setContentText(content);

		if (parentWindow != null) {
			alert.initOwner(parentWindow);
		}

		alert.showAndWait();
	}

	private void showPublicationDialog(String action, Publication publication) {
		StringBuilder content = new StringBuilder();

		if (publication == null) {
			content.append("Publication : null");
		} else {
			content.append("Title : ").append(safe(publication.getTitle())).append("\n");
			content.append("Status : ").append(safe(publication.getStatus())).append("\n");
			content.append("Type : ").append(safe(publication.getTypeInfo())).append("\n");
			content.append("UID : ").append(publication.getUid());
		}

		showDialog(action, content.toString());
	}

	@Override
	public void viewPublication(Publication publication) {
		showPublicationDialog("View Publication", publication);
	}
}