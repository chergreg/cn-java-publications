package com.loribel.publications.ui;

import java.util.ArrayList;
import java.util.List;

import com.loribel.publications.interfaces.Publication;

public class PublicationMonthModel {

	private int annee;
	private int mois;
	private List<Publication> publications;

	public PublicationMonthModel(int annee, int mois) {
		this.annee = annee;
		this.mois = mois;
		this.publications = new ArrayList<>();
	}

	public int getAnnee() {
		return annee;
	}

	public int getMois() {
		return mois;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}
}