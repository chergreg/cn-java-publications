package com.loribel.publications.interfaces;

import java.util.List;

public interface PublicationMonthRepository {

	List<Publication> getPublicationsMonth(int annee, int mois);
}