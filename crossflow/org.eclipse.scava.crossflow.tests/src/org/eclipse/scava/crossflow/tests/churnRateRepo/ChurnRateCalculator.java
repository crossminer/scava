package org.eclipse.scava.crossflow.tests.churnRateRepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class ChurnRateCalculator extends ChurnRateCalculatorBase {

	// it contains the remote branches that are cloned
	private ArrayList<String> remoteBranches = new ArrayList<>();

	// each key-value contains each branch (key) and all its commit list values
	private HashMap<String, List<String>> remoteCommits = new HashMap<>();

	// key represents the branch, hashmap value represents another hashmap with key
	// of the path of the file with changes and integer the number of changes of
	// this specific file in this specific branch
	private HashMap<String, HashMap<String, Integer>> changesPerBranch = new HashMap<>();

	// stores the cloned repository
	private Git repo;

	private String url;
	// local path of repository
	private File localPath;

	private FileRepository myrepo;

	@Override
	public void consumeURLs(URL uRL) throws Exception {

		// TODO: Add implementation that instantiates, sets, and submits result objects
		// (example below)
		ChurnRate churnRate1 = new ChurnRate();
		// churnRate1.setChurnRate( String );
		sendToChurnRates(churnRate1);

		this.createLocalPath();
		this.myrepo = new FileRepository(localPath + "/.git");
		url=uRL.getUrl();

		try (Git result = Git.cloneRepository()

				.setURI(uRL.getUrl())

				.setDirectory(localPath)

				.setCloneAllBranches(true)

				.call()) {

			// Note: the call() returns an opened repository already which needs to be
			// closed to avoid file handle leaks!

			repo = result;

			System.out.println("Having repository: " + getRepositoryDir());
			System.out.println("Cloning from " + uRL.getUrl() + " to " + localPath);
			System.out.println("Head is: " + getHead());
			setRemoteBranches();
			this.getRemoteBranches();
			this.setRemoreCommits();

			System.out.println("get the last commit of the branch example "
					+ this.getLastCommitofBranch(this.remoteBranches.get(0)));

			// getting the tree of the last commit of a specific branch
			System.out.print(
					"get a tree example " + this.getTree(this.getLastCommitofBranch(this.remoteBranches.get(0))));

			this.setDiffsAll();
			// this.setDiffsToBranch(this.remoteBranches.get(1));
			this.obtainChurnRates();
		}

	}//consumeURLs

	public void createLocalPath() throws IOException {
		// prepare a new folder
		File path = File.createTempFile("TestGitRepository", "");
		if (!path.delete()) {
			throw new IOException("Could not delete temporary file " + path);
		}
		localPath = path;
	}

	public File getRepositoryDir() {
		return repo.getRepository().getDirectory();
	}

	public ObjectId getHead()
			throws RevisionSyntaxException, AmbiguousObjectException, IncorrectObjectTypeException, IOException {

		return repo.getRepository().resolve(Constants.HEAD);

	}

	public void setRemoteBranches() throws GitAPIException {

		List<Ref> call = repo.branchList().setListMode(ListMode.REMOTE).call();
		for (Ref ref : call) {

			System.out.println("Remote Branches: " + ref + " " + ref.getName() + " "
					+ ref.getName().substring(ref.getName().lastIndexOf("/") + 1));
			remoteBranches.add(ref.getName());

		}
		System.out.println("Total remote branches" + " " + remoteBranches.size());

	}

	public void getRemoteBranches() {
		System.out.println(remoteBranches);
	}

	// method for setting the local commits to the appropriate instance variable
	// it checks each branch and stores the commits
	public void setRemoreCommits() throws IOException, RevisionSyntaxException, NoHeadException, GitAPIException {
		System.out.println(localPath + "/.git");

		/////////////////////// this should be move to an instance variable

		// stores into appropriate instance variables (HashMap localCommits) the commits
		// of each branch
		for (int i = 0; i < remoteBranches.size(); i++) {

			// for each branch create a list of lists with the total commits
			// the next for loop stores the list of all commits of each branch
			// to allCommitsSpecificBranchRemote list and then add that list to allCommits
			// list
			List<String> allCommitsSpecificBranchRemote = new ArrayList<>();

			// loop for each commit of all the commits of each branch
			for (RevCommit commit : repo.log().add(myrepo.resolve(remoteBranches.get(i))).call()) {

				allCommitsSpecificBranchRemote.add(commit.getName());

			}

			// after each loop it adds allCommitsSpecificBranchRemote List to allCommits
			// list of lists
			// allCommits.add(allCommitsSpecificBranchRemote);

			// stores all commits lists with key value of a specific branch to hashmap
			// remoteCommits
			remoteCommits.put(remoteBranches.get(i), allCommitsSpecificBranchRemote);
			// remoteCommits.put(remoteBranches.get(i), allCommits.get(i));

		}

		this.getCommits(remoteCommits);

		// myrepo.close();

	}

	public void getCommits(HashMap<String, List<String>> tempHashMap) {

		Set<Entry<String, List<String>>> setMap = tempHashMap.entrySet();

		Iterator<Entry<String, List<String>>> iteratorMap = setMap.iterator();

		while (iteratorMap.hasNext()) {
			Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) iteratorMap.next();

			System.out.print("key is: " + entry.getKey() + " & Value is: ");
			System.out.println(entry.getValue());
		}
	}

	/*
	 * public void getSpecificCommit(HashMap<String, List<String>> tempHashMap) {
	 * 
	 * Set<Entry<String, List<String>>> setMap = tempHashMap.entrySet();
	 * Iterator<Entry<String, List<String>>> iteratorMap = setMap.iterator();
	 * 
	 * while (iteratorMap.hasNext()) { Map.Entry<String, List<String>> entry =
	 * (Map.Entry<String, List<String>>) iteratorMap.next();
	 * 
	 * System.out.print("key is: " + entry.getKey() + " & Value is: ");
	 * System.out.println(entry.getValue()); } }
	 */
	public String getLastCommitofBranch(String branch) {

		return remoteCommits.get(branch).get(0);
	}

	public RevTree getTree(String commit) throws MissingObjectException, IncorrectObjectTypeException, IOException {

		// convert SHA into ObjectId
		ObjectId commitId = ObjectId.fromString(commit);

		try (RevWalk revWalk = new RevWalk(myrepo)) {
			RevCommit mycommit = revWalk.parseCommit(commitId);
			System.out.println("got the tree" + mycommit.getTree());
			return mycommit.getTree();
		}
	}

	// this method sets diffs to all branches

	public void setDiffsAll() throws RevisionSyntaxException, AmbiguousObjectException, IncorrectObjectTypeException,
			IOException, GitAPIException {

		for (int i = 0; i < this.remoteBranches.size(); i++) {

			this.setDiffsToBranch(this.remoteBranches.get(i));

		}

	}

	// this method checks differences of every commit + previous commit set
	// of a specific branch and calculates the average number of changes in all
	// changed files in this specific branch
	public void setDiffsToBranch(String branch) throws RevisionSyntaxException, AmbiguousObjectException,
			IncorrectObjectTypeException, IOException, GitAPIException {
		System.out.println(this.remoteCommits.get(branch).size());

		// counting the differences for this branch
		// int countDifferencesOfBranch = 0;

		// checking each branch for differences between commit and previous commit sets
		for (int i = 0; i < this.remoteCommits.get(branch).size() - 1; i++) {

			// this list is used in order to store all changes for this specific branch
			// it lists SystemFile objects that store changes
			// List<SystemFile> allChangesSpecificBranch = new ArrayList<>();

			System.out.println("\n" + i + "\n");

			// convert SHA of the commit and previous commit of branch into ObjectId of tree
			// of each commit
			ObjectId commitTree = this.getTree(this.remoteCommits.get(branch).get(i));
			ObjectId previousCommitTree = this.getTree(this.remoteCommits.get(branch).get(i + 1));
			System.out.println("test committree " + i + " " + this.remoteCommits.get(branch).get(i));
			System.out.println("test previouscommittree " + this.remoteCommits.get(branch).get(i + 1));

			// reading data from Git
			ObjectReader reader = myrepo.newObjectReader();

			// tree iterators
			CanonicalTreeParser oldTreeIterator = new CanonicalTreeParser();
			CanonicalTreeParser newTreeIterator = new CanonicalTreeParser();
			newTreeIterator.reset(reader, commitTree);
			// System.out.println("testing loop " +i);
			oldTreeIterator.reset(reader, previousCommitTree);
			// System.out.println("testing loop " +i);

			List<DiffEntry> diff = repo.diff().setOldTree(oldTreeIterator).setNewTree(newTreeIterator).call();

			// looping all the differences of the specific branch
			for (DiffEntry entry : diff) {

				// System.out.println(countDifferencesOfBranch + " " + "Entry: " + entry);

				// check if a file is deleted and in that case return
				// the old path as newpath is null for deleted files

				/// IMPORTANT!!!
				// in case a a file is renamed then we have a ChangeType of DELETE and ADD at
				/// the same time.
				// That means that the program will walk both if an else statements and this is
				/// correct

				if (entry.getChangeType() == ChangeType.DELETE) {

					// allChanges.setChangesPerBranch(branch, entry.getOldPath());
					// System.out.println(allChanges.);
					System.out.println("testing diff " + entry.getChangeType() + " " + entry.getOldPath());
					this.setChangesPerBranch(branch, entry.getOldPath());
					System.out.println("CHANGES TEST number of changes from getChangesPerBranch "
							+ this.getChangesPerBranch(branch, entry.getOldPath()));

					// System.out.println("count changes in branch: "+ branch+ " changes: "
					// +myFile.getChangesPerBranch(branch));

					// System.out.println("getting path from systemfile: " +
					// allChanges.getChangesPerBranch(branch)get(countDifferencesOfBranch).getPath());
					// System.out.println("getting changes from allChanges object for path "
					// +entry.getOldPath()+ allChanges.getChangesPerBranch(branch,
					// entry.getOldPath()));
					// countDifferencesOfBranch++;
				}

				// in case there is no deleted file
				else {
					this.setChangesPerBranch(branch, entry.getNewPath());
					// allChanges.setChangesPerBranch(branch, entry.getNewPath());
					// System.out.println(allChanges);
					System.out.println(
							"testing diff chnagetype + getpath " + entry.getChangeType() + " " + entry.getNewPath());
					System.out.println("CHANGES TEST number of changes from getChangesPerBranch "
							+ this.getChangesPerBranch(branch, entry.getNewPath()));
					// stores the SystemFile object to the appropriate list

					// SystemFile temp = new SystemFile(branch, entry.getNewPath());
					// allChangesSpecificBranch.add(temp);
					// System.out.println("count of diffs is: " + countDifferencesOfBranch);
					// System.out.println("getting path from systemfile: " +
					// allChangesSpecificBranch.get(countDifferencesOfBranch).getPath());
					// System.out.println("getting changes from allChanges object for path "
					// +entry.getNewPath()+ allChanges.getChangesPerBranch(branch,
					// entry.getNewPath()));
					// countDifferencesOfBranch++;
					// System.out.println("count changes in branch: "+ branch+ " changes: "
					// +myFile.getChangesPerBranch(branch));

					// SystemFile myFile = new SystemFile(branch, entry.getNewPath());
					// allFiles.add(myFile);

				}

			} // for loop that checked all diffs of this specific branch

			// at this part we are going to store all changes of this specific branch to

			// after each loop it adds allChangesSpecificBranch List to allChanges list of
			// lists

			// allChanges.add(allChangesSpecificBranch);

			// stores all commits lists with key value of a specific branch to hashmap
			// remoteCommits
			/////////////////////////////////////////////////////////////////////////////////
			// remoteChanges.put(branch, allChanges.get(0));

			System.out.print(this.changesPerBranch +"\n");

			// for each difference found:

		}
	}

	

	public void setChangesPerBranch(String branch, String path) {

		// checks if changesPerBranch hashmap instance variable contains
		// key value named branch. That means that the file has changes
		// in the specific branch
		if (this.changesPerBranch.containsKey(branch)) {

			// there is another if for checking if file is already added (has already been
			// changed)
			if (this.changesPerBranch.get(branch).containsKey(path)) {

				// HashMap<String, Integer> temp = new HashMap<String, Integer>();
				// temp.put(path, );

				// adds changes to appropriate value of inner HashMap
				// HashMap<String,Integer> temp = new HashMap<String,Integer>();
				HashMap<String, Integer> temp = new HashMap<>();
				temp.put(path, this.getChangesPerBranch(branch, path) + 1);

				// temp.put(path, getChangesPerBranch(branch,path)+1);

				changesPerBranch.get(branch).put(path, this.getChangesPerBranch(branch, path) + 1);
				System.out.println("Inside 1 " + temp + "\n");

				// changesPerBranch.get(branch).containsKey(path);

			}

			// this means that we have changes in the specific branch but not in the same
			// file
			else {
				changesPerBranch.get(branch).put(path, 1);
				System.out.println("Inside 2 ");
			}

		}

		// or if it does not contain branch or path
		else {

			HashMap<String, Integer> temp = new HashMap<>();
			temp.put(path, 1);
			this.changesPerBranch.put(branch, temp);
			System.out.println("Inside 3 " + temp + "\n");

			// System.out.print(this.changesPerBranch);

		}

	}

	public int getChangesPerBranch(String branch, String path) {
		return changesPerBranch.get(branch).get(path);
	}

	// returns the churnRate for every branch of the project
	public void obtainChurnRates () {
		
		//for every branch
		for (int i=0; i<this.remoteBranches.size();i++) {
			
						
			int temp=0;
			
			//for loop for every key that represent a file that changed
			for (String key: changesPerBranch.get(this.remoteBranches.get(i)).keySet()){
				temp=temp+this.changesPerBranch.get(this.remoteBranches.get(i)).get(key);
				//System.out.println("temp = "+temp);
			}
			
			double numberofChanges= this.changesPerBranch.get(this.remoteBranches.get(i)).size();
			double churnRate=temp/numberofChanges;
			System.out.println("The churn rate of branch " +this.remoteBranches.get(i)+ " = " + churnRate);
			
			//sending job to the next queue 
			sendToChurnRates(new ChurnRate(url,this.remoteBranches.get(i),churnRate));
				
			}
			
		}
	
}//class
