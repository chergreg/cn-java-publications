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
import com.loribel.publications.interfaces.PublicationActions;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PublicationMonthView extends BorderPane {

	private static final double DAY_CELL_MIN_HEIGHT = 120;
	private static final int MAX_VISIBLE_PUBLICATIONS_PER_DAY = 3;

	private PublicationActions actions;
	private final GridPane calendarGrid;
	private PublicationMonthController controller;
	private final Button newPublicationButton;
	private final Button nextButton;
	private final Button previousButton;
	private final Label titleLabel;

	public PublicationMonthView() {
		this.titleLabel = new Label();
		this.previousButton = new Button("Mois Précédent");
		this.nextButton = new Button("Mois Suivant");
		this.newPublicationButton = new Button("Nouvelle publication...");
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

		newPublicationButton.setOnAction(event -> {
			if (actions != null) {
				actions.createPublication(LocalDate.now());
			}
		});
	}

	private String buildAdditionalPublicationText(int remainingCount) {
		return "+" + remainingCount + " publication(s)...";
	}

	private void buildView() {
		this.setPadding(new Insets(20));

		titleLabel.setFont(Font.font(28));

		previousButton.setPrefWidth(180);
		nextButton.setPrefWidth(180);
		newPublicationButton.setPrefWidth(180);

		HBox navigationBox = new HBox(20, previousButton, nextButton, newPublicationButton);
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
			columnConstraints.setFillWidth(true);
			calendarGrid.getColumnConstraints().add(columnConstraints);
		}

		for (int row = 0; row < 7; row++) {
			RowConstraints rowConstraints = new RowConstraints();

			if (row == 0) {
				rowConstraints.setPrefHeight(30);
				rowConstraints.setMinHeight(30);
				rowConstraints.setMaxHeight(30);
			} else {
				rowConstraints.setPercentHeight(100.0 / 6.0);
				rowConstraints.setMinHeight(150);
				rowConstraints.setVgrow(Priority.ALWAYS);
				rowConstraints.setFillHeight(true);
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
		VBox cell = new VBox(3);
		cell.setPadding(new Insets(4));
		cell.setMinHeight(150);
		cell.setPrefHeight(150);
		cell.setMaxHeight(Double.MAX_VALUE);
		cell.setFillWidth(true);
		cell.setStyle("-fx-background-color: white; -fx-border-color: #D0D0D0;");

		HBox headerBox = new HBox();
		headerBox.setAlignment(Pos.CENTER_LEFT);

		Label dayLabel = new Label(String.valueOf(day));
		dayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

		Button addButton = new Button("+");
		addButton.setFocusTraversable(false);
		addButton.setMinWidth(24);
		addButton.setPrefWidth(24);
		addButton.setPrefHeight(24);
		addButton.setStyle("-fx-font-weight: bold; -fx-padding: 0;");

		LocalDate date = LocalDate.of(controller.getAnnee(), controller.getMois(), day);
		addButton.setOnAction(event -> {
			if (actions != null) {
				actions.createPublication(date);
			}
		});

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		headerBox.getChildren().addAll(dayLabel, spacer, addButton);
		cell.getChildren().add(headerBox);

		int visibleCount = Math.min(publications.size(), MAX_VISIBLE_PUBLICATIONS_PER_DAY);

		for (int i = 0; i < visibleCount; i++) {
			Publication publication = publications.get(i);
			cell.getChildren().add(createPublicationItem(publication));
		}

		if (publications.size() > MAX_VISIBLE_PUBLICATIONS_PER_DAY) {
			int remainingCount = publications.size() - MAX_VISIBLE_PUBLICATIONS_PER_DAY;

			Label moreLabel = new Label(buildAdditionalPublicationText(remainingCount));
			moreLabel.setWrapText(true);
			moreLabel.setStyle("-fx-font-size: 11;" + "-fx-text-fill: #666666;" + "-fx-font-style: italic;"
					+ "-fx-padding: 2 4 2 4;");
			moreLabel.setMinHeight(Region.USE_PREF_SIZE);
			moreLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);

			cell.getChildren().add(moreLabel);
		}

		return cell;
	}

	private HBox createPublicationItem(Publication publication) {
		HBox row = new HBox(6);
		row.setAlignment(Pos.CENTER_LEFT);
		row.setMaxWidth(Double.MAX_VALUE);
		row.setStyle("-fx-background-color: #D9ECFF;" + "-fx-border-color: #A8C8E8;" + "-fx-padding: 2 4 2 4;");

		Label iconLabel = createSocialNetworkIcon(publication);

		Label publicationTitleLabel = new Label(publication.getTitle());
		publicationTitleLabel.setWrapText(false);
		publicationTitleLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
		publicationTitleLabel.setMaxWidth(Double.MAX_VALUE);
		publicationTitleLabel.setStyle("-fx-font-size: 11;");
		publicationTitleLabel.setTooltip(new Tooltip(publication.getTitle()));

		HBox.setHgrow(publicationTitleLabel, Priority.ALWAYS);

		ContextMenu contextMenu = new ContextMenu();

		MenuItem editItem = new MenuItem("Éditer la publication...");
		editItem.setOnAction(event -> {
			if (actions != null) {
				actions.editPublication(publication);
			}
		});

		MenuItem viewItem = new MenuItem("Voir la publication...");
		viewItem.setOnAction(event -> {
			if (actions != null) {
				actions.viewPublication(publication);
			}
		});

		MenuItem deleteItem = new MenuItem("Supprimer la publication...");
		deleteItem.setOnAction(event -> {
			if (actions != null) {
				actions.deletePublication(publication);
			}
		});

		contextMenu.getItems().addAll(editItem, viewItem, deleteItem);

		row.setOnContextMenuRequested(event -> {
			contextMenu.show(row, event.getScreenX(), event.getScreenY());
		});

		row.setOnMousePressed(event -> {
			if (contextMenu.isShowing()) {
				contextMenu.hide();
			}
		});

		row.getChildren().addAll(iconLabel, publicationTitleLabel);
		return row;
	}

	private Label createSocialNetworkIcon(Publication publication) {
		Label iconLabel = new Label();
		iconLabel.setMinWidth(24);
		iconLabel.setPrefWidth(24);
		iconLabel.setAlignment(Pos.CENTER);
		iconLabel.setStyle("-fx-font-size: 10;" + "-fx-font-weight: bold;" + "-fx-background-radius: 4;"
				+ "-fx-padding: 2 4 2 4;");

		switch (publication.getSocialNetwork()) {
		case LINKEDIN -> {
			iconLabel.setText("in");
			iconLabel.setStyle(iconLabel.getStyle() + "-fx-background-color: #D6E9FF;" + "-fx-border-color: #7FB3E6;");
		}
		case YOUTUBE -> {
			iconLabel.setText("▶");
			iconLabel.setStyle(iconLabel.getStyle() + "-fx-background-color: #FFE0E0;" + "-fx-border-color: #E8A8A8;");
		}
		}

		return iconLabel;
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
			GridPane.setFillHeight(dayCell, true);
			GridPane.setFillWidth(dayCell, true);

			col++;
			if (col > 6) {
				col = 0;
				row++;
			}

			day++;
		}
	}

	public void setActions(PublicationActions actions) {
		this.actions = actions;
	}

	public void setController(PublicationMonthController controller) {
		this.controller = controller;
	}
}