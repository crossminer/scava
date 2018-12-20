package org.eclipse.scava.crossflow.examples.github.techrank;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;

public class RepositorySearcher extends RepositorySearcherBase {
	
	@Override
	public void consumeRepositorySearches(RepositorySearch repositorySearch) throws Exception {
		
		TechrankWorkflowContext context = new TechrankWorkflowContext(workflow);
		
		File clone = new File(context.getProperties().getProperty("clones") + "/" + UUID.nameUUIDFromBytes(repositorySearch.getRepository().getBytes()));
		
		if (!clone.exists()) {
				
			try {
				// Try the command-line option first as it supports --depth 1
				Process process = Runtime.getRuntime().exec("git clone --depth 1 " + "https://github.com/" + 
						repositorySearch.getRepository() + ".git " + clone.getAbsolutePath());
				process.waitFor();
			}
			catch (Exception ex) {
				System.out.println("Falling back to JGit because " + ex.getMessage());
				Git.cloneRepository()
					.setURI( "https://github.com/" + repositorySearch.getRepository() + ".git" )
					.setDirectory(clone)
					.call();
			}
		}
		
		for (Technology technology : repositorySearch.getTechnologies()) {
			
			try {
				Process process = Runtime.getRuntime().exec("grep -r -l --include=\"*." + technology.getExtension() + "\" \"" + 
						technology.getKeyword() + "\" " + clone.getAbsolutePath());
				
				BufferedReader processInputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
				
				int files = 0;
				while (processInputStream.readLine() != null) { files++; }
				
				RepositorySearchResult result = new RepositorySearchResult(technology.getName(), files, repositorySearch);
				sendToRepositorySearchResults(result);
				
			}
			catch (Exception ex) {
				System.out.println("Falling back to file-by-file searching because " + ex.getMessage());
				sendToRepositorySearchResults(new RepositorySearchResult(technology.getName(), countFiles(clone, technology), repositorySearch));
			}
		}
		
	}
	
	protected int countFiles(File directory, Technology technology) {
		if (directory.isDirectory()) {
			return Arrays.asList(directory.listFiles()).stream().filter(f -> 
				!f.isDirectory() && conforms(f, technology)).collect(Collectors.toList()).size() +
				Arrays.asList(directory.listFiles()).stream().filter(f -> f.isDirectory() && !f.getName().equals(".git")).
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