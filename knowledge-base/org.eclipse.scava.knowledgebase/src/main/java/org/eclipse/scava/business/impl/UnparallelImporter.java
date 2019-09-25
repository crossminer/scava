package org.eclipse.scava.business.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UnparallelImporter{
	
	@Autowired
	private ArtifactRepository projectRepository;

	private static final Logger logger = LoggerFactory.getLogger(UnparallelImporter.class);
	
	public void importProjects(String projects) throws IOException {
		projectRepository.deleteAll();
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(projects))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        records.add(Arrays.asList(values));
		    }
		}
		for (List<String> list : records) {
			Artifact art = new Artifact();
			int count = 0;
			for (String value : list) {
				if(count == 0) {
					art.setName(value);
					art.setFullName(value);
					art.setShortName(value);
				}
				else 
					art.getDependencies().add("no_maven:" + value);
				count ++;
			}
			logger.info("Storing artifacts {}", art.getShortName());
			projectRepository.save(art);
			logger.info("Stored artifacts {}", art.getShortName());
		}
	}


}
