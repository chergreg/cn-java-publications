package com.loribel.publications.ui.dialogs;

import com.loribel.publications.bo.PublicationYoutubeBO;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PublicationYoutubeEditorDialog extends BaseEditorDialog<PublicationYoutubeBO> {

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

    public PublicationYoutubeEditorDialog(Window owner, PublicationYoutubeBO target) {
        super(owner, "YouTube", target, copyOfSameConcreteType(target));
    }

    @Override
    protected Pane buildCustomBlock() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(10, 0, 0, 0));

        int r = 0;

        grid.add(new Label("YouTube ID"), 0, r);
        youtubeIdField = new TextField();
        grid.add(youtubeIdField, 1, r++);

        grid.add(new Label("Published Date (YYYY-MM-DD)"), 0, r);
        publishedDateField = new TextField();
        grid.add(publishedDateField, 1, r++);

        grid.add(new Label("Published Time (HH:mm:ss)"), 0, r);
        publishedTimeField = new TextField();
        grid.add(publishedTimeField, 1, r++);

        grid.add(new Label("Duration (seconds)"), 0, r);
        durationField = new TextField();
        grid.add(durationField, 1, r++);

        grid.add(new Label("Tags (comma separated)"), 0, r);
        tagsField = new TextField();
        grid.add(tagsField, 1, r++);

        grid.add(new Label("Channel ID"), 0, r);
        channelIdField = new TextField();
        grid.add(channelIdField, 1, r++);

        grid.add(new Label("Channel Title"), 0, r);
        channelTitleField = new TextField();
        grid.add(channelTitleField, 1, r++);

        grid.add(new Label("Default Language"), 0, r);
        defaultLanguageField = new TextField();
        grid.add(defaultLanguageField, 1, r++);

        grid.add(new Label("Definition (sd/hd)"), 0, r);
        definitionField = new TextField();
        grid.add(definitionField, 1, r++);

        grid.add(new Label("Thumbnails URL"), 0, r);
        thumbnailsUrlField = new TextField();
        grid.add(thumbnailsUrlField, 1, r++);

        grid.add(new Label("Description"), 0, r);
        descriptionArea = new TextArea();
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefRowCount(4);
        grid.add(descriptionArea, 1, r++);

        return grid;
    }

    @Override
    protected void loadCustomToForm() {
        youtubeIdField.setText(n(working.getYoutubeId()));
        publishedDateField.setText(n(working.getPublishedDate()));
        publishedTimeField.setText(n(working.getPublishedTime()));
        durationField.setText(String.valueOf(working.getDuration()));
        tagsField.setText(String.join(", ", safeList(working.getTags())));

        channelIdField.setText(n(working.getChannelId()));
        channelTitleField.setText(n(working.getChannelTitle()));
        defaultLanguageField.setText(n(working.getDefaultLanguage()));
        definitionField.setText(n(working.getDefinition()));
        thumbnailsUrlField.setText(n(working.getThumbnailsUrl()));

        descriptionArea.setText(n(working.getDescription()));
    }

    @Override
    protected String saveCustomFromForm() {
        String youtubeId = youtubeIdField.getText().trim();
        if (youtubeId.isEmpty()) return "YouTube ID is required.";

        int duration;
        try {
            String d = durationField.getText().trim();
            duration = d.isEmpty() ? 0 : Integer.parseInt(d);
            if (duration < 0) return "Duration must be >= 0.";
        } catch (NumberFormatException ex) {
            return "Duration must be an integer number.";
        }

        working.setYoutubeId(youtubeId);
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

    @Override
    protected void commitCustomToTarget() {
        target.setYoutubeId(working.getYoutubeId());
        target.setPublishedDate(working.getPublishedDate());
        target.setPublishedTime(working.getPublishedTime());
        target.setDuration(working.getDuration());
        target.setTags(working.getTags() == null ? new ArrayList<>() : new ArrayList<>(working.getTags()));

        target.setChannelId(working.getChannelId());
        target.setChannelTitle(working.getChannelTitle());
        target.setDefaultLanguage(working.getDefaultLanguage());
        target.setDefinition(working.getDefinition());
        target.setThumbnailsUrl(working.getThumbnailsUrl());
        target.setDescription(working.getDescription());
    }

    @Override
    protected double getWidth() {
        return 820;
    }

    @Override
    protected double getHeight() {
        return 620;
    }

    // ---------------- helpers ----------------

    private List<String> parseTags(String input) {
        if (input == null || input.trim().isEmpty()) return new ArrayList<>();
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    private List<String> safeList(List<String> l) {
        return l == null ? List.of() : l;
    }

    private static PublicationYoutubeBO copyOfSameConcreteType(PublicationYoutubeBO src) {
        try {
            PublicationYoutubeBO dst = src.getClass().getDeclaredConstructor().newInstance();

            // copie champs communs (gérés par BaseEditorDialog, mais on met tout dans working)
            dst.setTitle(src.getTitle());
            dst.setStatus(src.getStatus());

            // copie champs youtube
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

            return dst;
        } catch (Exception e) {
            // Important : PublicationYoutubeBO est abstract -> on DOIT pouvoir instancier le type concret
            throw new IllegalStateException(
                    "Le type " + src.getClass().getSimpleName() +
                    " doit avoir un constructeur vide (public) pour être édité.", e
            );
        }
    }
}