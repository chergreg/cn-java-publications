package com.loribel.publications.ui;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.loribel.publications.bo.PublicationBO;
import com.loribel.publications.bo.PublicationLinkedInTextBO;
import com.loribel.publications.bo.PublicationYoutubeShortBO;
import com.loribel.publications.bo.PublicationYoutubeVideoBO;
import com.loribel.publications.repository.PublicationFileRepository;
import com.loribel.publications.ui.dialogs.BaseEditorDialog;
import com.loribel.publications.ui.dialogs.PublicationLinkedInTextEditorDialog;
import com.loribel.publications.ui.dialogs.PublicationYoutubeEditorDialog;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Window;

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

	private enum PubType {
		YT_VIDEO("YouTube Video"), YT_SHORT("YouTube Short"), LI_TEXT("LinkedIn Text");

		final String label;

		PubType(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}
	}

	private PubType chooseType() {
		ChoiceDialog<PubType> dialog = new ChoiceDialog<>(PubType.LI_TEXT, PubType.values());
		dialog.setTitle("Nouvelle publication");
		dialog.setHeaderText("Choisir le type de publication");
		dialog.setContentText("Type :");

		return dialog.showAndWait().orElse(null);
	}

	@FXML
	private void onNew() {

	    PubType type = chooseType();
	    if (type == null) return; // Annuler

	    try {
	        PublicationBO bo;

	        switch (type) {
	            case YT_VIDEO -> bo = new PublicationYoutubeVideoBO();
	            case YT_SHORT -> bo = new PublicationYoutubeShortBO();
	            case LI_TEXT  -> bo = new PublicationLinkedInTextBO();
	            default -> throw new IllegalStateException("Type inconnu: " + type);
	        }

	        boolean ok = openEditor(bo);
	        if (!ok) return;

	        PublicationFileRepository.getInstance().save(bo);
	        loadData();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@FXML
	private void onEdit() {

		PublicationBO p = getSelected();
		if (p == null)
			return;

		try {
			boolean ok = openEditor(p);
			if (!ok)
				return;

			PublicationFileRepository.getInstance().save(p);
			loadData();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean openEditor(PublicationBO p) {

		Window owner = tablePublications.getScene().getWindow();

		
		// p = mon BO
		//BaseEditorDialog<PublicationBO> dialog = p.newDialog(owner);
		//return dialog.showAndWait();
		
		
		if (p instanceof PublicationYoutubeVideoBO yt) {
			return new PublicationYoutubeEditorDialog(owner, yt).showAndWait();
		}

		if (p instanceof PublicationLinkedInTextBO liText) {
			return new PublicationLinkedInTextEditorDialog(owner, liText).showAndWait();
		}

		Alert a = new Alert(Alert.AlertType.WARNING);
		a.setTitle("Edition");
		a.setHeaderText("Type non supporté");
		a.setContentText("Pas encore d'éditeur pour : " + p.getTypeInfo());
		a.showAndWait();

		return false;
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