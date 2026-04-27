package com.loribel.publications.ui;

import java.util.List;

import com.loribel.publications.interfaces.Publication;
import com.loribel.publications.interfaces.PublicationActions;
import com.loribel.publications.interfaces.PublicationMonthRepository;

public class PublicationMonthController {

	private final PublicationActions actions;
	private final PublicationMonthModel model;
	private final PublicationMonthRepository repository;
	private final PublicationMonthView view;

	public PublicationMonthController(PublicationMonthRepository repository, PublicationMonthModel model,
			PublicationMonthView view, PublicationActions actions) {
		this.repository = repository;
		this.model = model;
		this.view = view;
		this.actions = actions;

		this.view.setController(this);
		this.view.setActions(actions);
	}

	public int getAnnee() {
		return model.getAnnee();
	}

	public int getMois() {
		return model.getMois();
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