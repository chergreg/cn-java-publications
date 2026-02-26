package com.loribel.publications.ui;

import com.loribel.publications.bo.PublicationYoutubeVideoBO;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1) Créer un BO random
        PublicationYoutubeVideoBO bo = PublicationYoutubeVideoFactory.random();

        // 2) Afficher console
        System.out.println("=== BO AVANT ===");
        PublicationYoutubePrinter.print(bo);

        // 3) Ouvrir écran d'édition (OK / Cancel)
        PublicationYoutubeEditorDialog dialog = new PublicationYoutubeEditorDialog(primaryStage, bo);
        boolean ok = dialog.showAndWait();

        // 4) Ré-afficher console
        System.out.println("=== BO APRES (ok=" + ok + ") ===");
        PublicationYoutubePrinter.print(bo);

        // Fermer l'app après le dialog (optionnel, selon ton besoin)
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}