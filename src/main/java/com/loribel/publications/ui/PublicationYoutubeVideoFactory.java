package com.loribel.publications.ui;

import com.loribel.publications.bo.PublicationYoutubeVideoBO;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PublicationYoutubeVideoFactory {

    public static PublicationYoutubeVideoBO random() {
        ThreadLocalRandom r = ThreadLocalRandom.current();

        PublicationYoutubeVideoBO bo = new PublicationYoutubeVideoBO();
        bo.setTitle("Video #" + r.nextInt(1, 10_000));
        bo.setStatus(r.nextBoolean() ? "DRAFT" : "PUBLISHED");
        bo.setYoutubeId("4xq6bVbS-Pw"); // ou random si tu veux
        bo.setPublishedDate("2026-02-" + String.format("%02d", r.nextInt(1, 29)));
        bo.setPublishedTime(String.format("%02d:%02d:%02d", r.nextInt(0,24), r.nextInt(0,60), r.nextInt(0,60)));
        bo.setDuration(r.nextInt(30, 1800));
        bo.setTags(List.of("java", "javafx", "poo"));

        bo.setChannelId("UC" + r.nextInt(100000, 999999));
        bo.setChannelTitle("Ma chaîne " + r.nextInt(1, 100));
        bo.setDefaultLanguage("fr");
        bo.setDefinition(r.nextBoolean() ? "hd" : "sd");
        bo.setThumbnailsUrl("https://example.com/thumb.jpg");
        bo.setDescription("Description random " + r.nextInt(1, 10_000));

        return bo;
    }
}