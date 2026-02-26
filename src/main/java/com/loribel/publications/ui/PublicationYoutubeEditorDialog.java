package com.loribel.publications.ui;

import com.loribel.publications.bo.PublicationYoutubeVideoBO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PublicationYoutubeEditorDialog {

    private final Stage stage;
    private final PublicationYoutubeVideoBO target;     // BO original (à modifier seulement si OK)
    private final PublicationYoutubeVideoBO working;    // copie de travail
    private boolean ok = false;

    // Champs UI
    private TextField titleField;
    private TextField statusField;
    private TextField youtubeIdField;
    private TextField publishedDateField;
    private TextField publishedTimeField;
    private TextField durationField;
    private TextField tagsField;

    private TextField channelIdField;
    private TextField channelTitleField;
    private TextField defaultLanguageField;
    private TextField definitionField;
    private TextField thumbnailsUrlField;

    private TextArea descriptionArea;
    private Label errorLabel;

    public PublicationYoutubeEditorDialog(Window owner, PublicationYoutubeVideoBO target) {
        this.target = target;
        this.working = copyOf(target);

        this.stage = new Stage();
        this.stage.initOwner(owner);
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.setTitle("Publication YouTube Editor");
        this.stage.setResizable(false);

        Scene scene = new Scene(buildRoot(), 760, 520);
        this.stage.setScene(scene);

        loadToForm(); // working -> UI
    }

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
        youtubeIdField = addTextField(form, row++, "YouTube ID", "");
        publishedDateField = addTextField(form, row++, "Published Date (YYYY-MM-DD)", "");
        publishedTimeField = addTextField(form, row++, "Published Time (HH:mm:ss)", "");
        durationField = addTextField(form, row++, "Duration (seconds)", "");
        tagsField = addTextField(form, row++, "Tags (comma separated)", "");

        channelIdField = addTextField(form, row++, "Channel ID", "");
        channelTitleField = addTextField(form, row++, "Channel Title", "");
        defaultLanguageField = addTextField(form, row++, "Default Language", "");
        definitionField = addTextField(form, row++, "Definition (sd/hd)", "");
        thumbnailsUrlField = addTextField(form, row++, "Thumbnails URL", "");

        descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(4);
        form.add(new Label("Description"), 0, row);
        form.add(descriptionArea, 1, row++);
        
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

        // working -> target (commit)
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
        youtubeIdField.setText(nullSafe(working.getYoutubeId()));
        publishedDateField.setText(nullSafe(working.getPublishedDate()));
        publishedTimeField.setText(nullSafe(working.getPublishedTime()));
        durationField.setText(String.valueOf(working.getDuration()));
        tagsField.setText(String.join(", ", safeList(working.getTags())));

        channelIdField.setText(nullSafe(working.getChannelId()));
        channelTitleField.setText(nullSafe(working.getChannelTitle()));
        defaultLanguageField.setText(nullSafe(working.getDefaultLanguage()));
        definitionField.setText(nullSafe(working.getDefinition()));
        thumbnailsUrlField.setText(nullSafe(working.getThumbnailsUrl()));

        descriptionArea.setText(nullSafe(working.getDescription()));
    }

    // --- UI -> working + validation (retourne null si ok)
    private String saveFromForm() {
        if (titleField.getText().trim().isEmpty()) return "Title is required.";
        if (youtubeIdField.getText().trim().isEmpty()) return "YouTube ID is required.";

        int duration;
        try {
            duration = Integer.parseInt(durationField.getText().trim());
            if (duration < 0) return "Duration must be >= 0.";
        } catch (NumberFormatException ex) {
            return "Duration must be a number (integer).";
        }

        working.setTitle(titleField.getText().trim());
        working.setStatus(statusField.getText().trim());
        working.setYoutubeId(youtubeIdField.getText().trim());
        working.setPublishedDate(publishedDateField.getText().trim());
        working.setPublishedTime(publishedTimeField.getText().trim());
        working.setDuration(duration);

        working.setTags(parseTags(tagsField.getText()));

        working.setChannelId(channelIdField.getText().trim());
        working.setChannelTitle(channelTitleField.getText().trim());
        working.setDefaultLanguage(defaultLanguageField.getText().trim());
        working.setDefinition(definitionField.getText().trim());
        working.setThumbnailsUrl(thumbnailsUrlField.getText().trim());
        working.setDescription(descriptionArea.getText().trim());

        return null;
    }

    private List<String> parseTags(String input) {
        if (input == null || input.trim().isEmpty()) return new ArrayList<>();
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    private String nullSafe(String s) { return s == null ? "" : s; }
    private List<String> safeList(List<String> l) { return l == null ? List.of() : l; }

    // --- Copies (sans clone(), juste getters/setters)
    private PublicationYoutubeVideoBO copyOf(PublicationYoutubeVideoBO src) {
        PublicationYoutubeVideoBO dst = new PublicationYoutubeVideoBO();
        copyInto(src, dst);
        return dst;
    }

    private void copyInto(PublicationYoutubeVideoBO src, PublicationYoutubeVideoBO dst) {
        dst.setTitle(src.getTitle());
        dst.setStatus(src.getStatus());
        dst.setYoutubeId(src.getYoutubeId());
        dst.setPublishedDate(src.getPublishedDate());
        dst.setPublishedTime(src.getPublishedTime());
        dst.setDuration(src.getDuration());
        dst.setTags(src.getTags() == null ? new ArrayList<>() : new ArrayList<>(src.getTags()));

        dst.setChannelId(src.getChannelId());
        dst.setChannelTitle(src.getChannelTitle());
        dst.setDefaultLanguage(src.getDefaultLanguage());
        dst.setDefinition(src.getDefinition());
        dst.setThumbnailsUrl(src.getThumbnailsUrl());
        dst.setDescription(src.getDescription());
    }
}