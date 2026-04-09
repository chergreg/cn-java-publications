package com.loribel.publications.ui;

import java.util.List;

import com.loribel.publications.interfaces.Publication;
import com.loribel.publications.interfaces.PublicationMonthRepository;

public class PublicationMonthController {

	private final PublicationMonthModel model;
	private final PublicationMonthRepository repository;
	private final PublicationMonthView view;

	public PublicationMonthController(PublicationMonthRepository repository, PublicationMonthModel model,
			PublicationMonthView view) {
		this.repository = repository;
		this.model = model;
		this.view = view;

		this.view.setController(this);
	}

	public void init() {
		loadPublications();
		refreshView();
	}

	private void loadPublications() {
		List<Publication> publications = repository.getPublicationsMonth(model.getAnnee(), model.getMois());

		model.setPublications(publications);
	}

	public void nextMonth() {
		int mois = model.getMois() + 1;
		int annee = model.getAnnee();

		if (mois > 12) {
			mois = 1;
			annee++;
		}

		model.setMois(mois);
		model.setAnnee(annee);

		loadPublications();
		refreshView();
	}

	public void previousMonth() {
		int mois = model.getMois() - 1;
		int annee = model.getAnnee();

		if (mois < 1) {
			mois = 12;
			annee--;
		}

		model.setMois(mois);
		model.setAnnee(annee);

		loadPublications();
		refreshView();
	}

	private void refreshView() {
		view.displayMonth(model.getAnnee(), model.getMois(), model.getPublications());
	}
}