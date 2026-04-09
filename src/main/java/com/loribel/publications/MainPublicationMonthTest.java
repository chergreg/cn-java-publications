package com.loribel.publications;

import com.loribel.publications.mock.MockPublicationMonthRepository;
import com.loribel.publications.ui.PublicationMonthController;
import com.loribel.publications.ui.PublicationMonthModel;
import com.loribel.publications.ui.PublicationMonthView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPublicationMonthTest extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		MockPublicationMonthRepository repository = new MockPublicationMonthRepository(3);
		repository.setCountForMonth(2026, 4, 80);
		repository.setCountForMonth(2026, 5, 4);
		repository.setCountForMonth(2026, 6, 10);

		PublicationMonthModel model = new PublicationMonthModel(2026, 4);
		PublicationMonthView view = new PublicationMonthView();

		PublicationMonthController controller = new PublicationMonthController(repository, model, view);

		controller.init();

		Scene scene = new Scene(view, 1200, 800);

		stage.setTitle("Test UI - Publications du mois");
		stage.setScene(scene);
		stage.show();
	}
}