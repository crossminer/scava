package org.eclipse.scava.business.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.ArtifactType;
import org.eclipse.scava.business.model.MethodDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataReader {

	private static final Logger log = LoggerFactory.getLogger(DataReader.class);

	@Autowired
	private ArtifactRepository artifactRepo;

	public List<Artifact> readArtifactsFromFile(List<String> projectsFileNames) {
		List<Artifact> results = new ArrayList<>();
		projectsFileNames.forEach(z -> results.add(readArtifactFromFile(z)));
		return results;
	}

	public List<Artifact> readArtifactsFromFolder(String focucFilesFolder) {
		List<Artifact> results = new ArrayList<>();

		try {
			Files.list(Paths.get(focucFilesFolder)).filter(Files::isRegularFile)
				.filter(z -> z.toString().endsWith(".m3.focus")).forEach(z -> {
					try {
							results.add(readArtifactFromFile(z.toString()));
						} catch(Exception e) {
							log.error("Fail importin {} data", z.toString());
						}
					});
		} catch (IOException e) {
			log.error("from path: {}", e.getMessage());
		}

		return results;
	}

	public Artifact readArtifactFromFile(String filePath) {
		Path path = Paths.get(filePath);
		Artifact result = new Artifact();
		ArtifactType atype = new ArtifactType();
		atype.setName("FOCUS");
		result.setType(atype);
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			String mdOld = "";
			MethodDeclaration methodDeclaration = new MethodDeclaration();
			result.setName(path.getFileName().toString());
			result.setFullName(filePath);
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("#");
				if (parts.length == 2) {
					String md = parts[0].replace("'", "").trim();
					String methodInvocation = parts[1].replace("'", "").trim();
					if (!mdOld.equals(md)) {
						methodDeclaration = new MethodDeclaration();
						methodDeclaration.setName(md);
						mdOld = md;
						result.getMethodDeclarations().add(methodDeclaration);
						methodDeclaration.getMethodInvocations().add(methodInvocation);
					}
					else
						methodDeclaration.getMethodInvocations().add(methodInvocation);
				}
			}
		} catch (Exception e) {
			log.error("from file: {} - {}", filePath, e.getMessage());
		}
		artifactRepo.save(result);
		return result;
	}

}
