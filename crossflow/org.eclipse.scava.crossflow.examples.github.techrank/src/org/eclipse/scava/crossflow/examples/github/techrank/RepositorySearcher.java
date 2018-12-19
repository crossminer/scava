package org.eclipse.scava.crossflow.examples.github.techrank;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;

public class RepositorySearcher extends RepositorySearcherBase {
	
	protected Set<String> rejected = new HashSet<>();
	
	@Override
	public void consumeRepositorySearches(RepositorySearch repositorySearch) throws Exception {
		
		File clone = new File("cloned-repos/" + UUID.nameUUIDFromBytes(repositorySearch.getRepository().getBytes()));
		
		if (!clone.exists()) {
			if (rejected.contains(repositorySearch.getRepository())) {
				// System.out.println("Cloning " + repositorySearch.getRepository());
				Git git = Git.cloneRepository()
						.setURI( "https://github.com/" + repositorySearch.getRepository() + ".git" )
						.setDirectory(clone)
						.call();
			}
			else {
				// System.out.println("Rejecting " + repositorySearch.getRepository());
				rejected.add(repositorySearch.getRepository());
				workflow.getRepositorySearches().send(repositorySearch, "RepositorySearcher");
				return;
			}
		}
		
		for (Technology technology : repositorySearch.getTechnologies()) {
			sendToRepositorySearchResults(new RepositorySearchResult(technology.getName(), countFiles(clone, technology), repositorySearch));
		}
		
	}
	
	protected int countFiles(File directory, Technology technology) {
		if (directory.isDirectory()) {
			return Arrays.asList(directory.listFiles()).stream().filter(f -> 
				!f.isDirectory() && conforms(f, technology)).collect(Collectors.toList()).size() +
				Arrays.asList(directory.listFiles()).stream().filter(f -> f.isDirectory()).
					mapToInt(f -> countFiles(f, technology)).sum();	
		}
		else return 0;
	}
	
	protected boolean conforms(File file, Technology technology) {
		try {
			return file.getName().endsWith(technology.getExtension()) && new String(Files.readAllBytes(Paths.get(file.toURI()))).indexOf(technology.getKeyword()) > -1;
		} catch (IOException e) {
			workflow.reportInternalException(e);
			return false;
		}
	}

}