package com.loribel.publications.ui;

import com.loribel.publications.bo.PublicationYoutubeVideoBO;

public class PublicationYoutubePrinter {

    public static void print(PublicationYoutubeVideoBO bo) {
        System.out.println("title=" + bo.getTitle());
        System.out.println("status=" + bo.getStatus());
        System.out.println("youtubeId=" + bo.getYoutubeId());
        System.out.println("publishedDate=" + bo.getPublishedDate());
        System.out.println("publishedTime=" + bo.getPublishedTime());
        System.out.println("duration=" + bo.getDuration());
        System.out.println("tags=" + bo.getTags());
        System.out.println("channelId=" + bo.getChannelId());
        System.out.println("channelTitle=" + bo.getChannelTitle());
        System.out.println("defaultLanguage=" + bo.getDefaultLanguage());
        System.out.println("definition=" + bo.getDefinition());
        System.out.println("thumbnailsUrl=" + bo.getThumbnailsUrl());
        System.out.println("description=" + bo.getDescription());
    }
}