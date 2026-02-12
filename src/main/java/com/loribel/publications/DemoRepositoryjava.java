package com.loribel.publications;

import com.loribel.publications.bo.*;
import com.loribel.publications.repository.PublicationFileRepository;

import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

public class DemoRepositoryjava {
	public static void main(String[] args) throws Exception {

		PublicationFileRepository repo = new PublicationFileRepository(
				Paths.get("src/main/resources/repository/publication"));

		// Création manuelle d’un exemple
		PublicationYoutubeVideo yt = new PublicationYoutubeVideo();
		yt.setTitle("Ma première vidéo");
		yt.setStatus("DRAFT");
		yt.setDatePub(new Date());
		yt.setVideoUri("https://youtube.com/watch?v=xxxx");
		yt.setDescription("Une description");
		yt.setDurationSec(777);

		repo.save(yt);

		// Relire tout
		List<Publication> pubs = repo.findAll();
		for (Publication p : pubs) {
			System.out.println(p.getUid() + " - " + p.getTitle() + " - " + p.getClass().getSimpleName());
		}
	}
}
