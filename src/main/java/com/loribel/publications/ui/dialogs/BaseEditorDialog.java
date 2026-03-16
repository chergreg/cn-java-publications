package com.loribel.publications.ui.dialogs;

import com.loribel.publications.bo.PublicationBO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public abstract class BaseEditorDialog<T extends PublicationBO> {

    protected final Stage stage;
    protected final T target;
    protected final T working;

    private boolean ok = false;

    // Bloc bas commun
    protected final Label errorLabel = new Label();

    // Champs communs
    protected TextField titleField;
    protected TextField statusField;

    protected BaseEditorDialog(Window owner, String title, T target, T working) {
        this.target = target;
        this.working = working;

        stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(title);
        stage.setResizable(false);

        errorLabel.setStyle("-fx-text-fill: red;");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));

        // 1) Bloc commun entête
        root.setTop(buildHeaderBlock());

        // 2) Bloc custom
        root.setCenter(buildCustomBlock());

        // 3) Bloc commun bas (validation + boutons)
        root.setBottom(buildFooterBlock());

        stage.setScene(new Scene(root, getWidth(), getHeight()));

        loadCommonToForm();
        loadCustomToForm();
    }

    public boolean showAndWait() {
        stage.showAndWait();
        return ok;
    }

    // ---------- BLOC 1 : ENTETE COMMUN ----------
    private Pane buildHeaderBlock() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(0, 0, 10, 0));

        int r = 0;

        // (optionnel) infos read-only
        grid.add(new Label("Type"), 0, r);
        grid.add(new Label(n(working.getTypeInfo())), 1, r++);

        // Champs communs éditables
        grid.add(new Label("Title"), 0, r);
        titleField = new TextField();
        grid.add(titleField, 1, r++);

        grid.add(new Label("Status"), 0, r);
        statusField = new TextField();
        grid.add(statusField, 1, r++);

        Separator sep = new Separator();
        VBox box = new VBox(10, grid, sep);
        return box;
    }

    private void loadCommonToForm() {
        titleField.setText(n(working.getTitle()));
        statusField.setText(n(working.getStatus()));
    }

    protected String saveCommonFromForm() {
        String title = titleField.getText().trim();
        if (title.isEmpty()) return "Title is required.";

        working.setTitle(title);
        working.setStatus(statusField.getText().trim());

        return null;
    }

    protected void commitCommonToTarget() {
        target.setTitle(working.getTitle());
        target.setStatus(working.getStatus());
    }

    // ---------- BLOC 2 : CUSTOM ----------
    protected abstract Pane buildCustomBlock();
    protected abstract void loadCustomToForm();
    protected abstract String saveCustomFromForm(); // null si ok
    protected abstract void commitCustomToTarget();

    // ---------- BLOC 3 : VALIDATION + BOUTONS ----------
    private Pane buildFooterBlock() {
        Button okBtn = new Button("OK");
        Button cancelBtn = new Button("Cancel");

        okBtn.setDefaultButton(true);
        cancelBtn.setCancelButton(true);

        okBtn.setOnAction(e -> onOk());
        cancelBtn.setOnAction(e -> onCancel());

        HBox actions = new HBox(10, okBtn, cancelBtn);

        VBox footer = new VBox(8, errorLabel, actions);
        footer.setPadding(new Insets(10, 0, 0, 0));

        return footer;
    }

    private void onOk() {
        errorLabel.setText("");

        String err = saveCommonFromForm();
        if (err == null) err = saveCustomFromForm();

        if (err != null) {
            errorLabel.setText(err);
            return;
        }

        commitCommonToTarget();
        commitCustomToTarget();

        ok = true;
        stage.close();
    }

    private void onCancel() {
        ok = false;
        stage.close();
    }

    // ---------- Utils ----------
    protected String n(String s) { return s == null ? "" : s; }
    protected double getWidth()  { return 760; }
    protected double getHeight() { return 520; }
}