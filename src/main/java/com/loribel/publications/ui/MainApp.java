package com.loribel.publications.ui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.loribel.publications.bo.PublicationBO;
import com.loribel.publications.bo.PublicationYoutubeVideoBO;
import com.loribel.publications.repository.PublicationFileRepository;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;


public class MainApp extends Application {

    private PublicationFileRepository repo;

    @Override
    public void start(Stage primaryStage) {
    	
        // IMPORTANT: un owner de Dialog doit avoir une Scene (sinon NPE en JavaFX 21)
    	if (primaryStage.getScene() == null) {
    	    primaryStage.setScene(new javafx.scene.Scene(new javafx.scene.layout.Pane(), 1, 1));
    	}
    	primaryStage.show();
    	primaryStage.hide();

            
        // Repo local (dossier où seront stockés les fichiers)
        repo = new PublicationFileRepository(
				Paths.get("src/main/resources/repository/publication"));
     
        PublicationYoutubeVideoBO bo = chooseOrCreate(primaryStage);
        if (bo == null) {
            primaryStage.close();
            return;
        }

        System.out.println("=== BO AVANT ===");
        PublicationYoutubePrinter.print(bo);

        // Ecran d’édition (OK/Cancel)
        PublicationYoutubeEditorDialog editor = new PublicationYoutubeEditorDialog(primaryStage, bo);
        boolean ok = editor.showAndWait();

        // Sur OK : Save dans le repo
        if (ok) {
            try {
                repo.save(bo);
                System.out.println("✅ SAVED in repo (uid=" + bo.getUid() + ")");
            } catch (IOException e) {
                showError("Erreur sauvegarde", e);
            }
        } else {
            System.out.println("❎ Cancel -> pas de save");
        }

        System.out.println("=== BO APRES (ok=" + ok + ") ===");
        PublicationYoutubePrinter.print(bo);

        primaryStage.close();
    }

    private PublicationYoutubeVideoBO chooseOrCreate(Window owner) {
        // 1) Choix : créer ou choisir dans repo
        ChoiceDialog<String> choice = new ChoiceDialog<>(
                "Créer une nouvelle publication",
                "Créer une nouvelle publication",
                "Choisir depuis le repo"
        );
        choice.initOwner(owner);
        choice.setTitle("Démarrage");
        choice.setHeaderText("Que veux-tu faire ?");
        choice.setContentText("Choix :");

        Optional<String> result = choice.showAndWait();
        if (!result.isPresent()) return null;

        // 2) Créer nouvelle
        if ("Créer une nouvelle publication".equals(result.get())) {
            return PublicationYoutubeVideoFactory.random(); // ou ton générateur / builder
        }

        // 3) Choisir depuis repo
        try {
            List<PublicationBO> all = repo.findAll();
            all.sort(Comparator.comparing(p -> p.getTitle() == null ? "" : p.getTitle()));

            if (all.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Repo vide : création d’une nouvelle publication.")
                        .showAndWait();
                return PublicationYoutubeVideoFactory.random();
            }

            Optional<PublicationBO> selected = pickFromRepo(owner, all);
            if (!selected.isPresent()) return null;

            PublicationBO base = selected.get();

            // Pour l’instant : cet écran édite uniquement des YoutubeVideoBO
            if (base instanceof PublicationYoutubeVideoBO) {
                return (PublicationYoutubeVideoBO) base;
            }

            new Alert(Alert.AlertType.WARNING,
                    "Type non supporté par cet écran : " + base.getClass().getSimpleName())
                    .showAndWait();

            return null;

        } catch (IOException e) {
            showError("Erreur lecture repo", e);
            return null;
        }
    }

    /**
     * Dialog de sélection compatible JavaFX 8 (ChoiceDialog n'a pas setConverter).
     */
    private Optional<PublicationBO> pickFromRepo(Window owner, List<PublicationBO> items) {
        Dialog<PublicationBO> dialog = new Dialog<>();
        dialog.initOwner(owner);
        dialog.setTitle("Repo");
        dialog.setHeaderText("Choisir une publication existante");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        ComboBox<PublicationBO> combo = new ComboBox<>();
        combo.getItems().addAll(items);
        combo.setMaxWidth(Double.MAX_VALUE);

        combo.setConverter(new StringConverter<PublicationBO>() {
            @Override
            public String toString(PublicationBO p) {
                if (p == null) return "";
                String title = (p.getTitle() == null || p.getTitle().trim().isEmpty())
                        ? "(sans titre)"
                        : p.getTitle();
                String uid = (p.getUid() == null)
                        ? "no-uid"
                        : p.getUid().toString();
                return title + "  [" + uid + "]";
            }

            @Override
            public PublicationBO fromString(String s) {
                return null; // pas utilisé
            }
        });

        combo.getSelectionModel().selectFirst();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Publication :"), 0, 0);
        grid.add(combo, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == okButtonType) {
                return combo.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        return dialog.showAndWait();
    }

    private void showError(String title, Exception e) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(e.getClass().getSimpleName() + ": " + e.getMessage());
        a.showAndWait();
        e.printStackTrace();
    }

    public static void main(String[] args) {
        launch(args);
    }
}