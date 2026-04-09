package com.loribel.publications.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.loribel.publications.interfaces.Publication;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PublicationMonthView extends BorderPane {

	private final GridPane calendarGrid;
	private PublicationMonthController controller;
	private final Button nextButton;
	private final Button previousButton;

	private final Label titleLabel;

	public PublicationMonthView() {
		this.titleLabel = new Label();
		this.previousButton = new Button("Mois Précédent");
		this.nextButton = new Button("Mois Suivant");
		this.calendarGrid = new GridPane();

		buildView();
		bindActions();
	}

	private void addDayHeaders() {
		String[] dayNames = { "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi" };

		for (int col = 0; col < dayNames.length; col++) {
			Label label = new Label(dayNames[col]);
			label.setMaxWidth(Double.MAX_VALUE);
			label.setAlignment(Pos.CENTER);
			label.setStyle("-fx-font-weight: bold; -fx-padding: 8; -fx-background-color: #EAEAEA;");
			GridPane.setHgrow(label, Priority.ALWAYS);
			GridPane.setFillWidth(label, true);
			GridPane.setHalignment(label, HPos.CENTER);

			calendarGrid.add(label, col, 0);
		}
	}

	private void bindActions() {
		previousButton.setOnAction(event -> {
			if (controller != null) {
				controller.previousMonth();
			}
		});

		nextButton.setOnAction(event -> {
			if (controller != null) {
				controller.nextMonth();
			}
		});
	}

	private void buildView() {
		this.setPadding(new Insets(20));

		titleLabel.setFont(Font.font(28));

		previousButton.setPrefWidth(180);
		nextButton.setPrefWidth(180);

		HBox navigationBox = new HBox(20, previousButton, nextButton);
		navigationBox.setAlignment(Pos.CENTER);

		VBox topBox = new VBox(20, titleLabel, navigationBox);
		topBox.setPadding(new Insets(10, 10, 20, 10));

		calendarGrid.setHgap(2);
		calendarGrid.setVgap(2);
		calendarGrid.setGridLinesVisible(true);

		for (int col = 0; col < 7; col++) {
			ColumnConstraints columnConstraints = new ColumnConstraints();
			columnConstraints.setPercentWidth(100.0 / 7.0);
			columnConstraints.setHgrow(Priority.ALWAYS);
			calendarGrid.getColumnConstraints().add(columnConstraints);
		}

		for (int row = 0; row < 7; row++) {
			RowConstraints rowConstraints = new RowConstraints();
			if (row == 0) {
				rowConstraints.setPrefHeight(30);
			} else {
				rowConstraints.setVgrow(Priority.ALWAYS);
			}
			calendarGrid.getRowConstraints().add(rowConstraints);
		}

		this.setTop(topBox);
		this.setCenter(calendarGrid);
	}

	private int convertDayOfWeekToColumn(DayOfWeek dayOfWeek) {
		return switch (dayOfWeek) {
		case SUNDAY -> 0;
		case MONDAY -> 1;
		case TUESDAY -> 2;
		case WEDNESDAY -> 3;
		case THURSDAY -> 4;
		case FRIDAY -> 5;
		case SATURDAY -> 6;
		};
	}

	private VBox createDayCell(int day, List<Publication> publications) {
		VBox cell = new VBox(5);
		cell.setPadding(new Insets(6));
		cell.setMinHeight(100);
		cell.setStyle("-fx-background-color: white; -fx-border-color: #D0D0D0;");

		Label dayLabel = new Label(String.valueOf(day));
		dayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

		cell.getChildren().add(dayLabel);

		for (Publication publication : publications) {
			Label publicationLabel = new Label(publication.getTitle());
			publicationLabel.setWrapText(true);
			publicationLabel.setMaxWidth(Double.MAX_VALUE);
			publicationLabel.setStyle("-fx-background-color: #D9ECFF;" + "-fx-border-color: #A8C8E8;"
					+ "-fx-padding: 4;" + "-fx-font-size: 11;");
			cell.getChildren().add(publicationLabel);
		}

		VBox.setVgrow(cell, Priority.ALWAYS);
		return cell;
	}

	public void displayMonth(int annee, int mois, List<Publication> publications) {
		titleLabel.setText("PUBLICATIONS DU MOIS : " + getMonthLabel(mois) + " " + annee);
		rebuildCalendar(annee, mois, publications);
	}

	private String getMonthLabel(int mois) {
		return switch (mois) {
		case 1 -> "JANVIER";
		case 2 -> "FÉVRIER";
		case 3 -> "MARS";
		case 4 -> "AVRIL";
		case 5 -> "MAI";
		case 6 -> "JUIN";
		case 7 -> "JUILLET";
		case 8 -> "AOÛT";
		case 9 -> "SEPTEMBRE";
		case 10 -> "OCTOBRE";
		case 11 -> "NOVEMBRE";
		case 12 -> "DÉCEMBRE";
		default -> "";
		};
	}

	private Map<Integer, List<Publication>> groupPublicationsByDay(List<Publication> publications) {
		Map<Integer, List<Publication>> result = new HashMap<>();

		for (Publication publication : publications) {
			if (publication.getDatePub() == null) {
				continue;
			}

			LocalDate localDate = publication.getDatePub().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			int day = localDate.getDayOfMonth();

			result.computeIfAbsent(day, key -> new ArrayList<>()).add(publication);
		}

		return result;
	}

	private void rebuildCalendar(int annee, int mois, List<Publication> publications) {
		calendarGrid.getChildren().clear();

		addDayHeaders();

		YearMonth yearMonth = YearMonth.of(annee, mois);
		LocalDate firstDayOfMonth = yearMonth.atDay(1);
		int daysInMonth = yearMonth.lengthOfMonth();

		int startColumn = convertDayOfWeekToColumn(firstDayOfMonth.getDayOfWeek());

		Map<Integer, List<Publication>> publicationsByDay = groupPublicationsByDay(publications);

		int day = 1;
		int row = 1;
		int col = startColumn;

		while (day <= daysInMonth) {
			List<Publication> dayPublications = publicationsByDay.getOrDefault(day, new ArrayList<>());
			VBox dayCell = createDayCell(day, dayPublications);

			calendarGrid.add(dayCell, col, row);

			col++;
			if (col > 6) {
				col = 0;
				row++;
			}

			day++;
		}
	}

	public void setController(PublicationMonthController controller) {
		this.controller = controller;
	}
}