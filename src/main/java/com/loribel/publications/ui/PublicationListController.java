package com.loribel.publications.ui;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.loribel.publications.bo.PublicationBO;
import com.loribel.publications.bo.PublicationLinkedInTextBO;
import com.loribel.publications.bo.PublicationYoutubeVideoBO;
import com.loribel.publications.repository.PublicationFileRepository;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PublicationListController {

	@FXML
	private TableView<PublicationBO> tablePublications;

	@FXML
	private TableColumn<PublicationBO, UUID> colUid;

	@FXML
	private TableColumn<PublicationBO, String> colTypeInfo;

	@FXML
	private TableColumn<PublicationBO, String> colTitle;

	@FXML
	private TableColumn<PublicationBO, String> colStatus;

	@FXML
	private TableColumn<PublicationBO, Date> colDatePub;

	@FXML
	private void initialize() {

		colUid.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getUid()));

		colTypeInfo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTypeInfo()));

		colTitle.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTitle()));

		colStatus.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getStatus()));

		colDatePub.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getDatePub()));

		loadData();

	}

	private void loadData() {

		try {

			List<PublicationBO> list = PublicationFileRepository.getInstance().findAll();

			tablePublications.getItems().setAll(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onNew() {
	}

	@FXML
	private void onEdit() {

		PublicationBO p = getSelected();
		if (p == null)
			return;

		try {
			boolean ok = false;

			if (p instanceof PublicationYoutubeVideoBO yt) {
				ok = new PublicationYoutubeEditorDialog(tablePublications.getScene().getWindow(), yt).showAndWait();

			} else if (p instanceof PublicationLinkedInTextBO liText) {
				ok = new PublicationLinkedInTextEditorDialog(tablePublications.getScene().getWindow(), liText)
						.showAndWait();

			} else {
				Alert a = new Alert(Alert.AlertType.WARNING);
				a.setTitle("Edition");
				a.setHeaderText("Type non supporté");
				a.setContentText("Pas encore d'éditeur pour : " + p.getTypeInfo());
				a.showAndWait();
				return;
			}

			if (!ok)
				return;

			PublicationFileRepository.getInstance().save(p);
			loadData();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onDelete() {

		PublicationBO p = getSelected();

		if (p == null)
			return;

		try {

			PublicationFileRepository.getInstance().delete(p.getUid());

			loadData();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onView() {
	}

	private PublicationBO getSelected() {
		return tablePublications.getSelectionModel().getSelectedItem();
	}
}