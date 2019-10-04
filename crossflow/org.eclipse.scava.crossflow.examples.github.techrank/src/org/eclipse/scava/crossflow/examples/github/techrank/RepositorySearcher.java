package org.eclipse.scava.crossflow.examples.github.techrank;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;

public class RepositorySearcher extends RepositorySearcherBase {
	
	@Override
	public RepositorySearchResult consumeRepositorySearches(RepositorySearch repositorySearch) throws Exception {
		
		System.out.println("Searching " + repositorySearch.getRepository());
		
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
			
			/* TODO: See why grep has stopped working (it returns 0 results even when the terminal says otherwise
			try {
				String grep = "grep -r -l --include=\"*." + technology.getExtension() + "\" \"" + 
						technology.getKeyword() + "\" " + clone.getAbsolutePath();
				
				System.out.println("Grepping: " + grep);
				
				Process process = Runtime.getRuntime().exec(grep);
				
				
				BufferedReader processInputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
				BufferedReader processErrorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				
				
				int files = 0;
				String s;
				while ((s = processInputStream.readLine()) != null) { 
					System.out.println("Found: " + s);
					files++; 
				}
				
				String e;
				while ((e = processErrorStream.readLine()) != null) { 
					System.out.println("Error: " + e);
				}
				
				RepositorySearchResult result = new RepositorySearchResult(technology.getName(), files, repositorySearch);
				sendToRepositorySearchResults(result);
				
			}
			catch (Exception ex) {
				System.out.println("Falling back to file-by-file searching because " + ex.getMessage());*/
				return new RepositorySearchResult(technology.getName(), countFiles(clone, technology), repositorySearch);
			//}
		}
		return null;
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