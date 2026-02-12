package com.loribel.publications.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.loribel.publications.bo.Publication;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PublicationFileRepository {

	private final Path folder;
	private final ObjectMapper mapper;

	public PublicationFileRepository(Path folder) {
		this.folder = folder;
		this.mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	}

	public void save(Publication publication) throws IOException {
		if (publication.getUid() == null) {
			publication.setUid(UUID.randomUUID());
		}
		Files.createDirectories(folder);

		Path file = folder.resolve(publication.getUid().toString() + ".json");
		mapper.writeValue(file.toFile(), publication);
	}

	public Publication load(UUID uid) throws IOException {
		Path file = folder.resolve(uid.toString() + ".json");
		return mapper.readValue(file.toFile(), Publication.class);
	}

	public List<Publication> findAll() throws IOException {
		Files.createDirectories(folder);

		List<Publication> result = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder, "*.json")) {
			for (Path file : stream) {
				Publication pub = mapper.readValue(file.toFile(), Publication.class);
				result.add(pub);
			}
		}
		return result;
	}

	public void delete(UUID uid) throws IOException {
		Path file = folder.resolve(uid.toString() + ".json");
		Files.deleteIfExists(file);
	}
}
