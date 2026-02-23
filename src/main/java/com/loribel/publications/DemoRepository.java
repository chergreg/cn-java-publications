package com.loribel.publications;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.loribel.publications.bo.PublicationBO;
import com.loribel.publications.bo.PublicationYoutubeVideoBO;
import com.loribel.publications.repository.PublicationFileRepository;

public class DemoRepository {

	private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE; // YYYY-MM-DD
	private static final DateTimeFormatter ISO_TIME = DateTimeFormatter.ISO_LOCAL_TIME; // HH:mm:ss (ou avec nanos si
																						// non contrôlé)
	private static final DateTimeFormatter TIME_HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static void main(String[] args) throws Exception {
		PublicationFileRepository repo = new PublicationFileRepository(
				Paths.get("src/main/resources/repository/publication"));

		// Création manuelle d’un exemple
		PublicationYoutubeVideoBO yt = randomYoutubeVideo();
		repo.save(yt);

		// Relire tout
		List<PublicationBO> pubs = repo.findAll();
		for (PublicationBO p : pubs) {
			System.out.println(p.getUid() + " - " + p.getTitle() + " - " + p.getClass().getSimpleName());
		}
	}

	public static PublicationYoutubeVideoBO randomYoutubeVideo() {

		PublicationYoutubeVideoBO yt = new PublicationYoutubeVideoBO();

		yt.setTitle("Video " + randomString(8));
		yt.setStatus(randomStatus());
		yt.setDatePub(new java.util.Date());

		yt.setDuration(ThreadLocalRandom.current().nextInt(30, 1800));
		yt.setTags(List.of("java", "youtube", randomString(5)));

		LocalDateTime ldt = LocalDateTime.now().minusDays(ThreadLocalRandom.current().nextInt(0, 365))
				.withHour(ThreadLocalRandom.current().nextInt(0, 24))
				.withMinute(ThreadLocalRandom.current().nextInt(0, 60))
				.withSecond(ThreadLocalRandom.current().nextInt(0, 60)).withNano(0);

		yt.setPublishedDate(ldt.toLocalDate().format(ISO_DATE)); // YYYY-MM-DD
		yt.setPublishedTime(ldt.toLocalTime().format(TIME_HH_MM_SS)); // HH:mm:ss

		yt.setChannelId("UC" + randomString(20));
		yt.setChannelTitle("Channel_" + randomString(6));
		yt.setDefaultLanguage("fr");
		yt.setDefinition(ThreadLocalRandom.current().nextBoolean() ? "hd" : "sd");
		yt.setDescription("Description " + randomString(20));
		String youtubeId = randomString(11);
		yt.setYoutubeId(youtubeId);
		yt.setThumbnailsUrl("https://img.youtube.com/vi/" + youtubeId + "/0.jpg");

		return yt;
	}

	private static String randomString(int length) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(ThreadLocalRandom.current().nextInt(chars.length())));
		}
		return sb.toString();
	}

	private static String randomStatus() {
		String[] statuses = { "DRAFT", "PUBLISHED", "ARCHIVED" };
		return statuses[ThreadLocalRandom.current().nextInt(statuses.length)];
	}

}
