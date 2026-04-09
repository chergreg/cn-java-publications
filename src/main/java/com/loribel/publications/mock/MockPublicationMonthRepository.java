package com.loribel.publications.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.loribel.publications.interfaces.Publication;
import com.loribel.publications.interfaces.PublicationMonthRepository;

public class MockPublicationMonthRepository implements PublicationMonthRepository {

	private final int defaultCount;
	private final Map<String, Integer> publicationsCountByMonth;

	public MockPublicationMonthRepository() {
		this(5);
	}

	public MockPublicationMonthRepository(int defaultCount) {
		this.defaultCount = defaultCount;
		this.publicationsCountByMonth = new HashMap<>();
	}

	private String buildKey(int annee, int mois) {
		return annee + "-" + mois;
	}

	@Override
	public List<Publication> getPublicationsMonth(int annee, int mois) {
		int count = publicationsCountByMonth.getOrDefault(buildKey(annee, mois), defaultCount);

		List<Publication> publications = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			publications.add(PublicationMock.createRandomPublication(annee, mois));
		}

		return publications;
	}

	public void setCountForMonth(int annee, int mois, int count) {
		publicationsCountByMonth.put(buildKey(annee, mois), count);
	}
}