package com.loribel.publications;

import java.text.SimpleDateFormat;
import java.util.List;

import com.loribel.publications.interfaces.Publication;
import com.loribel.publications.mock.MockPublicationMonthRepository;

public class MainTestMockPublicationMonthRepository {

	public static void main(String[] args) {
		MockPublicationMonthRepository repository = new MockPublicationMonthRepository(3);

		repository.setCountForMonth(2026, 4, 8);
		repository.setCountForMonth(2026, 5, 2);

		testMonth(repository, 2026, 4);
		testMonth(repository, 2026, 5);
		testMonth(repository, 2026, 6);
	}

	private static void testMonth(MockPublicationMonthRepository repository, int annee, int mois) {
		List<Publication> publications = repository.getPublicationsMonth(annee, mois);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		System.out.println("======================================");
		System.out.println("Publications pour " + mois + "/" + annee);
		System.out.println("Nombre : " + publications.size());
		System.out.println("======================================");

		for (Publication publication : publications) {
			System.out.println("UID      : " + publication.getUid());
			System.out.println("Titre    : " + publication.getTitle());
			System.out.println("Statut   : " + publication.getStatus());
			System.out.println("Date     : " + dateFormat.format(publication.getDatePub()));
			System.out.println("Type     : " + publication.getTypeInfo());
			System.out.println("--------------------------------------");
		}

		System.out.println();
	}
}