package com.loribel.publications.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.loribel.publications.bo.PublicationYoutubeVideoBO;

// --- JavaFX App ---
public class MainApp extends Application {

    // 1) L'objet qu'on édite
    private final PublicationYoutubeVideoBO bo = new PublicationYoutubeVideoBO();

    // 2) Champs UI
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

    @Override
    public void start(Stage stage) {
        // Valeurs initiales (pour voir que ça marche)
    	bo.setTitle("Ma vidéo");
    	bo.setStatus("DRAFT");
    	bo.setYoutubeId("4xq6bVbS-Pw");
    	bo.setPublishedDate("2026-02-23");
    	bo.setPublishedTime("10:30:00");
    	bo.setDuration(120);
    	bo.setTags(Arrays.asList("java", "javafx", "poo"));
    	bo.setChannelId("UC123");
    	bo.setChannelTitle("Ma chaîne");
    	bo.setDefaultLanguage("fr");
    	bo.setDefinition("hd");
    	bo.setThumbnailsUrl("https://example.com/thumb.jpg");
    	bo.setDescription("Description ici...");

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
        form.add(descriptionArea, 1, row);
        row++;

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button loadBtn = new Button("Load BO → Form");
        Button saveBtn = new Button("Save Form → BO");

        loadBtn.setOnAction(e -> loadToForm());
        saveBtn.setOnAction(e -> {
            String err = saveFromForm();
            errorLabel.setText(err == null ? "" : err);
            if (err == null) {
                System.out.println("✅ Saved BO:");
                printBO();
            }
        });

        HBox actions = new HBox(10, loadBtn, saveBtn);
        form.add(actions, 1, row++);
        form.add(errorLabel, 1, row++);

        // Chargement initial
        loadToForm();

        Scene scene = new Scene(form, 760, 520);
        stage.setTitle("Publication YouTube Editor (Beginner)");
        stage.setScene(scene);
        stage.show();
    }

    // --- Helpers UI ---
    private TextField addTextField(GridPane grid, int row, String label, String initial) {
        Label l = new Label(label);
        TextField tf = new TextField(initial);
        grid.add(l, 0, row);
        grid.add(tf, 1, row);
        return tf;
    }

    // --- 3) Copier BO → UI ---
    private void loadToForm() {
    	titleField.setText(nullSafe(bo.getTitle()));
    	statusField.setText(nullSafe(bo.getStatus()));
    	youtubeIdField.setText(nullSafe(bo.getYoutubeId()));
    	publishedDateField.setText(nullSafe(bo.getPublishedDate()));
    	publishedTimeField.setText(nullSafe(bo.getPublishedTime()));
    	durationField.setText(String.valueOf(bo.getDuration()));
    	tagsField.setText(String.join(", ", bo.getTags()));

    	channelIdField.setText(nullSafe(bo.getChannelId()));
    	channelTitleField.setText(nullSafe(bo.getChannelTitle()));
    	defaultLanguageField.setText(nullSafe(bo.getDefaultLanguage()));
    	definitionField.setText(nullSafe(bo.getDefinition()));
    	thumbnailsUrlField.setText(nullSafe(bo.getThumbnailsUrl()));

    	descriptionArea.setText(nullSafe(bo.getDescription()));
    }

    // --- 4) Copier UI → BO + validation simple ---
    // Retourne null si OK, sinon message d'erreur
    private String saveFromForm() {
        // Validation débutant (très simple)
        if (titleField.getText().trim().isEmpty()) return "Title is required.";
        if (youtubeIdField.getText().trim().isEmpty()) return "YouTube ID is required.";

        int duration;
        try {
            duration = Integer.parseInt(durationField.getText().trim());
            if (duration < 0) return "Duration must be >= 0.";
        } catch (NumberFormatException ex) {
            return "Duration must be a number (integer).";
        }

     // Copier dans l'objet (via setters)
        bo.setTitle(titleField.getText().trim());
        bo.setStatus(statusField.getText().trim());
        bo.setYoutubeId(youtubeIdField.getText().trim());
        bo.setPublishedDate(publishedDateField.getText().trim());
        bo.setPublishedTime(publishedTimeField.getText().trim());
        bo.setDuration(duration);

        bo.setTags(parseTags(tagsField.getText()));

        bo.setChannelId(channelIdField.getText().trim());
        bo.setChannelTitle(channelTitleField.getText().trim());
        bo.setDefaultLanguage(defaultLanguageField.getText().trim());
        bo.setDefinition(definitionField.getText().trim());
        bo.setThumbnailsUrl(thumbnailsUrlField.getText().trim());
        bo.setDescription(descriptionArea.getText().trim());

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

    private String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private void printBO() {
        System.out.println("title=" + bo.getTitle());
        System.out.println("status=" + bo.getStatus());
        System.out.println("youtubeId=" + bo.getYoutubeId());
        System.out.println("publishedDate=" + bo.getPublishedDate());
        System.out.println("publishedTime=" + bo.getPublishedTime());
        System.out.println("duration=" + bo.getDuration());
        System.out.println("tags=" + bo.getTags());
        System.out.println("channelId=" + bo.getChannelId());
        System.out.println("channelTitle=" + bo.getChannelTitle());
        System.out.println("defaultLanguage=" + bo.getDefaultLanguage());
        System.out.println("definition=" + bo.getDefinition());
        System.out.println("thumbnailsUrl=" + bo.getThumbnailsUrl());
        System.out.println("description=" + bo.getDescription());
    }

    public static void main(String[] args) {
        launch(args);
    }
}