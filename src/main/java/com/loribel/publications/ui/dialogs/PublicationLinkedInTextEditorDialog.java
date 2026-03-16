package com.loribel.publications.ui.dialogs;

import com.loribel.publications.bo.PublicationLinkedInTextBO;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

public class PublicationLinkedInTextEditorDialog extends BaseEditorDialog<PublicationLinkedInTextBO> {

    private TextArea contentArea;

    public PublicationLinkedInTextEditorDialog(Window owner, PublicationLinkedInTextBO target) {
        super(owner, "LinkedIn Text", target, copyOf(target));
    }

    @Override
    protected Pane buildCustomBlock() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(10, 0, 0, 0));

        contentArea = new TextArea();
        contentArea.setWrapText(true);
        contentArea.setPrefRowCount(10);

        grid.add(new Label("Content"), 0, 0);
        grid.add(contentArea, 1, 0);

        return grid;
    }

    @Override
    protected void loadCustomToForm() {
        contentArea.setText(n(working.getContent()));
    }

    @Override
    protected String saveCustomFromForm() {
        String content = contentArea.getText().trim();
        if (content.isEmpty()) return "Content is required.";
        working.setContent(content);
        return null;
    }

    @Override
    protected void commitCustomToTarget() {
        target.setContent(working.getContent());
    }

    private static PublicationLinkedInTextBO copyOf(PublicationLinkedInTextBO src) {
        PublicationLinkedInTextBO dst = new PublicationLinkedInTextBO();
        dst.setTitle(src.getTitle());
        dst.setStatus(src.getStatus());
        dst.setContent(src.getContent());
        return dst;
    }
}