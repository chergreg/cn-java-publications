package com.loribel.publications.mock;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.loribel.publications.bo.PublicationLinkedInImageBO;
import com.loribel.publications.bo.PublicationLinkedInTextBO;
import com.loribel.publications.bo.PublicationLinkedInVideoBO;
import com.loribel.publications.bo.PublicationYoutubeShortBO;
import com.loribel.publications.bo.PublicationYoutubeVideoBO;
import com.loribel.publications.interfaces.Publication;

public final class PublicationMock {

	private static final Random RANDOM = new Random();

	private static final String[] STATUS = { "BROUILLON", "PLANIFIEE", "PUBLIEE" };

	private static final String[] TITLES = { "Lancement produit", "Retour client", "Annonce importante",
			"Tutoriel rapide", "Bilan mensuel", "Nouveau service", "Astuces communication", "Présentation équipe" };

	public static Publication createRandomPublication(int annee, int mois) {
		Publication publication = createRandomType();
		publication.setTitle(randomTitle());
		publication.setStatus(randomStatus());
		publication.setDatePub(randomDate(annee, mois));
		return publication;
	}

	private static Publication createRandomType() {
		int value = RANDOM.nextInt(5);

		return switch (value) {
		case 0 -> new PublicationLinkedInTextBO();
		case 1 -> new PublicationLinkedInImageBO();
		case 2 -> new PublicationLinkedInVideoBO();
		case 3 -> new PublicationYoutubeShortBO();
		default -> new PublicationYoutubeVideoBO();
		};
	}

	private static Date randomDate(int annee, int mois) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, annee);
		calendar.set(Calendar.MONTH, mois - 1);

		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int day = 1 + RANDOM.nextInt(maxDay);

		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, 8 + RANDOM.nextInt(10));
		calendar.set(Calendar.MINUTE, RANDOM.nextInt(60));
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	private static String randomStatus() {
		return STATUS[RANDOM.nextInt(STATUS.length)];
	}

	private static String randomTitle() {
		return TITLES[RANDOM.nextInt(TITLES.length)];
	}

	private PublicationMock() {
	}
}