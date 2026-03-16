package com.loribel.publications.ui;

import com.loribel.publications.bo.PublicationLinkedInTextBO;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PublicationLinkedInTextEditorDialog {

    private final Stage stage;
    private final PublicationLinkedInTextBO target;   // BO original (modifié seulement si OK)
    private final PublicationLinkedInTextBO working;  // copie de travail
    private boolean ok = false;

    // Champs UI
    private TextField titleField;
    private TextField statusField;
    private TextArea contentArea;
    private Label errorLabel;

    public PublicationLinkedInTextEditorDialog(Window owner, PublicationLinkedInTextBO target) {
        this.target = target;
        this.working = copyOf(target);

        this.stage = new Stage();
        this.stage.initOwner(owner);
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.setTitle("Publication LinkedIn Text Editor");
        this.stage.setResizable(false);

        Scene scene = new Scene(buildRoot(), 700, 420);
        this.stage.setScene(scene);

        loadToForm(); // working -> UI
    }

    /** @return true si bouton OK */
    public boolean showAndWait() {
        stage.showAndWait();
        return ok;
    }

    private GridPane buildRoot() {
        GridPane form = new GridPane();
        form.setPadding(new Insets(12));
        form.setHgap(10);
        form.setVgap(8);

        int row = 0;

        titleField = addTextField(form, row++, "Title", "");
        statusField = addTextField(form, row++, "Status", "");

        contentArea = new TextArea();
        contentArea.setWrapText(true);
        contentArea.setPrefRowCount(10);
        form.add(new Label("Content"), 0, row);
        form.add(contentArea, 1, row++);
        
        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        form.add(errorLabel, 1, row++);

        Button okBtn = new Button("OK");
        Button cancelBtn = new Button("Cancel");

        okBtn.setDefaultButton(true);
        cancelBtn.setCancelButton(true);

        okBtn.setOnAction(e -> onOk());
        cancelBtn.setOnAction(e -> onCancel());

        HBox actions = new HBox(10, okBtn, cancelBtn);
        form.add(actions, 1, row++);

        return form;
    }

    private void onOk() {
        String err = saveFromForm(); // UI -> working + validation
        errorLabel.setText(err == null ? "" : err);
        if (err != null) return;

        // Commit working -> target
        copyInto(working, target);

        ok = true;
        stage.close();
    }

    private void onCancel() {
        ok = false;
        stage.close();
    }

    private TextField addTextField(GridPane grid, int row, String label, String initial) {
        Label l = new Label(label);
        TextField tf = new TextField(initial);
        grid.add(l, 0, row);
        grid.add(tf, 1, row);
        return tf;
    }

    // --- working -> UI
    private void loadToForm() {
        titleField.setText(nullSafe(working.getTitle()));
        statusField.setText(nullSafe(working.getStatus()));
        contentArea.setText(nullSafe(working.getContent()));
    }

    // --- UI -> working + validation
    private String saveFromForm() {
        if (titleField.getText().trim().isEmpty()) return "Title is required.";
        if (contentArea.getText().trim().isEmpty()) return "Content is required.";

        working.setTitle(titleField.getText().trim());
        working.setStatus(statusField.getText().trim());
        working.setContent(contentArea.getText().trim());

        return null;
    }

    private String nullSafe(String s) { return s == null ? "" : s; }

    private PublicationLinkedInTextBO copyOf(PublicationLinkedInTextBO src) {
        PublicationLinkedInTextBO dst = new PublicationLinkedInTextBO();
        copyInto(src, dst);
        return dst;
    }

    private void copyInto(PublicationLinkedInTextBO src, PublicationLinkedInTextBO dst) {
        dst.setTitle(src.getTitle());
        dst.setStatus(src.getStatus());
        dst.setContent(src.getContent());
        // uid/datePub : on ne touche pas ici (selon ta logique repo/BO)
    }
}