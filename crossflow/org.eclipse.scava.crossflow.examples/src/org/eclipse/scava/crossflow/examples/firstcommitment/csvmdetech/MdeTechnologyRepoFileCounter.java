package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVRecord;

public class MdeTechnologyRepoFileCounter extends MdeTechnologyRepoFileCounterBase {
	
protected final int MAX_NUMBER_OF_COMMITMENTS = 128;
	
	protected Set<String> alreadySeenJobs = new HashSet<String>();
	protected MdeTechnologyCsvSource mdeTechnologyCsvSource = new MdeTechnologyCsvSource();
	
	// < repository-url, number-of-repository-occurrence >
	protected Map<String, Integer> committedRepoMap = new HashMap<String, Integer>(); 
	
	
	final static File CLONE_PARENT_DESTINATION = new File(
			// level: same as this (scava) repo
			".." + File.separator + ".." + File.separator + ".." + File.separator + "CLONED-REPOS");
	
	public MdeTechnologyRepoFileCounter() {
		// do nothing
	}

	@Override
	public void consumeMdeTechnologyClonedRepoEntriesForFileCounter(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {
		if ( committedRepoMap.size() == MAX_NUMBER_OF_COMMITMENTS ) {
			// do not commit to any more repositories - sending back
			workflow.getMdeTechnologyClonedRepoEntriesForFileCounter().send( extensionKeywordStargazersRemoteRepoUrlTuple, this.getClass().getName() );
		
		} else {
			// We still have space left for repositories to commit to - considering it
			if ( alreadySeenJobs.contains( extensionKeywordStargazersRemoteRepoUrlTuple.getId() ) ) { 
				// We've seen this job before - assume no-one else wants it
				committedRepoMap.put( extensionKeywordStargazersRemoteRepoUrlTuple.getField1(), 0 );
			
			} else {
				// We haven't seen this job before
				// Record it and send it back
				alreadySeenJobs.add( extensionKeywordStargazersRemoteRepoUrlTuple.getId() );
				workflow.getMdeTechnologyClonedRepoEntriesForFileCounter().send( extensionKeywordStargazersRemoteRepoUrlTuple, this.getClass().getName() );
			}
			
			if ( committedRepoMap.containsKey( extensionKeywordStargazersRemoteRepoUrlTuple.getField1() ) ) {
				
				committedRepoMap.replace( extensionKeywordStargazersRemoteRepoUrlTuple.getField1(), committedRepoMap.get( extensionKeywordStargazersRemoteRepoUrlTuple.getField1()) + 1 );
				
				int fileCount = count(extensionKeywordStargazersRemoteRepoUrlTuple.getField3());
				
				ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple = new ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple();
				extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.setField0(extensionKeywordStargazersRemoteRepoUrlTuple.field0); // file extension
				extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.setField1(extensionKeywordStargazersRemoteRepoUrlTuple.field1); // repository remote URL
				extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.setField2(extensionKeywordStargazersRemoteRepoUrlTuple.field2); // repository number of stars
				extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.setField3(extensionKeywordStargazersRemoteRepoUrlTuple.field3); // cloned repository local path
				extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple.setField4(fileCount); // repository file count
				
				sendToMdeTechnologyRepoFileCountEntries(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
			}
			
		}
		
	}

	private int count(String repoLocation) {	
		List<Path> acceptedFilesList = new LinkedList<Path>();
		
		try {
			Stream<Path> paths = Files.walk(Paths.get(repoLocation)).filter(Files::isRegularFile);
			
			paths.filter(p -> p.getFileName() != null).filter(p -> p.getFileName().toString().contains(".")).forEach(new Consumer<Path>() {
				@Override
				public void accept(Path p) {

					String filename = p.getFileName().toString();
					
					boolean ret = inCollection(mdeTechnologyCsvSource.getRecords(), filename.substring(filename.lastIndexOf(".") + 1, filename.length()), 0);
					if (ret == true) {
						acceptedFilesList.add(p);
					}
				}

			});

		} catch (IOException e) {
			System.err.println("\n" + "[" + workflow.getName() + "] " + "Failed to count files of repository located at " + repoLocation);
			e.printStackTrace();
		}
		
		return acceptedFilesList.size();
	}

	/**
	 * @return the alreadySeenJobs
	 */
	public Set<String> getAlreadySeenJobs() {
		return alreadySeenJobs;
	}

	/**
	 * @return the committedRepoMap
	 */
	public Map<String, Integer> getCommittedRepoMap() {
		return committedRepoMap;
	}
	
	/**
	 * Determines if the provided String {@param s} occurs within {@param column}.
	 * 
	 * @param s String to look for in CSV column
	 * @param column column number in CSV file
	 * @return true if provided String {@param s} occurs within {@param column} and false otherwise
	 */
	protected boolean inCollection(Iterable<CSVRecord> records, String s, int column) {
		try {
			for (CSVRecord record : records) {
				if (record.get(column).equals(s)) {
					return true;
				}
			}
		} catch (Exception e) {
			if (e instanceof IOException && e.getMessage().contains("failed to parse")) {
				// skip
			}
		}
		return false;
	}
	public static void main(String args[]) throws IOException {
		MdeTechnologyRepoFileCounter counter = new MdeTechnologyRepoFileCounter();
		String repoLocation = "../../.git";
		System.out.println(new File(repoLocation).getCanonicalPath());
		int count = counter.count(repoLocation);
		System.out.println("COUNT: " + count);
	}

}