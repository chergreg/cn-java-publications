package com.loribel.publications;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.loribel.publications.bo.PublicationBO;
import com.loribel.publications.bo.PublicationLinkedInTextBO;
import com.loribel.publications.bo.PublicationYoutubeVideoBO;
import com.loribel.publications.repository.PublicationFileRepository;

public class DemoRepository {

	private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE; // YYYY-MM-DD
	private static final DateTimeFormatter ISO_TIME = DateTimeFormatter.ISO_LOCAL_TIME; // HH:mm:ss (ou avec nanos si
																						// non contrôlé)
	private static final DateTimeFormatter TIME_HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static void main(String[] args) throws Exception {
		PublicationFileRepository repo = PublicationFileRepository.getInstance();

		// Création manuelle d’un exemple
		PublicationYoutubeVideoBO yt = randomYoutubeVideo();
		repo.save(yt);

		// Création manuelle d’un exemple
		PublicationLinkedInTextBO pubLn = randomLinkedInTextBO();
		repo.save(pubLn);

		// Relire tout
		List<PublicationBO> pubs = repo.findAll();
		for (PublicationBO p : pubs) {
			System.out.println(p.getUid() + " - " + p.getTitle() + " - " + p.getClass().getSimpleName());
		}
	}

	public static PublicationYoutubeVideoBO randomYoutubeVideo() {

		PublicationYoutubeVideoBO pub = new PublicationYoutubeVideoBO();

		pub.setTitle("Video " + randomString(8));
		pub.setStatus(randomStatus());
		pub.setDatePub(new java.util.Date());

		pub.setDuration(ThreadLocalRandom.current().nextInt(30, 1800));
		pub.setTags(List.of("java", "youtube", randomString(5)));

		LocalDateTime ldt = LocalDateTime.now().minusDays(ThreadLocalRandom.current().nextInt(0, 365))
				.withHour(ThreadLocalRandom.current().nextInt(0, 24))
				.withMinute(ThreadLocalRandom.current().nextInt(0, 60))
				.withSecond(ThreadLocalRandom.current().nextInt(0, 60)).withNano(0);

		pub.setPublishedDate(ldt.toLocalDate().format(ISO_DATE)); // YYYY-MM-DD
		pub.setPublishedTime(ldt.toLocalTime().format(TIME_HH_MM_SS)); // HH:mm:ss

		pub.setChannelId("UC" + randomString(20));
		pub.setChannelTitle("Channel_" + randomString(6));
		pub.setDefaultLanguage("fr");
		pub.setDefinition(ThreadLocalRandom.current().nextBoolean() ? "hd" : "sd");
		pub.setDescription("Description " + randomString(20));
		String youtubeId = randomString(11);
		pub.setYoutubeId(youtubeId);
		pub.setThumbnailsUrl("https://img.youtube.com/vi/" + youtubeId + "/0.jpg");

		return pub;
	}

	public static PublicationLinkedInTextBO randomLinkedInTextBO() {

		PublicationLinkedInTextBO pub = new PublicationLinkedInTextBO();

		pub.setTitle("LinkedIn " + randomString(8));
		pub.setStatus(randomStatus());
		pub.setDatePub(new java.util.Date());

		pub.setContent(randomString(50));

		return pub;
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
