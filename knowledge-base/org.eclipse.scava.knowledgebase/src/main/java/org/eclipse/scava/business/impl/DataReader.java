package org.eclipse.scava.business.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.MethodDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
@Service
public class DataReader {

	private static final Logger log = LoggerFactory.getLogger(DataReader.class);

	public List<Artifact> readArtifactsFromFile(List<String> projectsFileNames) {
		List<Artifact> results = new ArrayList<>();
		for (String fileName : projectsFileNames) {
			try {
				results.add(readArtifactFromFile(fileName));
			} catch (IOException e) {
				log.error("Couldn't read file " + fileName, e);
			}
		}
		return results;
	}

	public List<Artifact> readArtifactsFromPath(String projectsFileNames) throws IOException {
		Resource resource = new ClassPathResource(projectsFileNames);
		File folder = resource.getFile(); 
		File[] listOfFiles = folder.listFiles();
		List<Artifact> results = new ArrayList<>();
		for (int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].isFile() && !listOfFiles[i].getName().equals("List.txt"))
				results.add(readArtifactFromFile(listOfFiles[i].getAbsolutePath()));
		}
		return results;
	}

	public Artifact readArtifactFromFile(String filename) throws IOException {
		Resource resource2 = new ClassPathResource("./");
		filename = filename.replace(resource2.getFile().getAbsolutePath(),"");
		Resource resource = new ClassPathResource(filename);
		Artifact result = new Artifact();
		BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
		String line;
		String mdOld = "";
		MethodDeclaration methodDeclaration = new MethodDeclaration();
		result.setName(filename);
		result.setFullName(filename);
		while ((line = reader.readLine()) != null) {
			String[] parts = line.split("#");
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
		reader.close();

		return result;
	}

}
