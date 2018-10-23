/**
 * 
 */
package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

/**
 * @author Patrick Neubauer
 *
 */
public class CloneUtils {
	
	public static String createUniqueFolderForRepo(String repoUrl) {
		return createUniqueFolderForRepo(extractGhRepoName(repoUrl), repoUrl);
	}
	
	public static String createUniqueFolderForRepo(String name, String url) {

		//System.out.println("creating unique hash (SHA-1) for url: " + url);

		String ret = cleanFileName(name);

		// create unique id for the remote url
		MessageDigest md = null;
		try {

			md = MessageDigest.getInstance("SHA-1");

			md.update(url.getBytes());

			ret = ret + "-" + DigestUtils.sha1Hex(md.digest());

		} catch (NoSuchAlgorithmException e) {
			System.err.println(
					"createUniqueFolderForRepo() tried to create a SHA-1 digest but a NoSuchAlgorithmException was thrown, appending nothing");
		}

		return ret;
	}

	// illegal ascii characters in filenames for various operating systems
	// (mainly windows)
	final static int[] illegalChars = { 34, 60, 62, 124, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
			18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 58, 42, 63, 92, 47 };
	static {
		Arrays.sort(illegalChars);
	}

	public static String cleanFileName(String badFileName) {
		StringBuilder cleanName = new StringBuilder();
		for (int i = 0; i < badFileName.length(); i++) {
			int c = (int) badFileName.charAt(i);
			if (Arrays.binarySearch(illegalChars, c) < 0) {
				cleanName.append((char) c);
			}
		}
		String cleaned = cleanName.toString();
		// remove names ending with one or more "." / " " as this is illegal as
		// well in JGit.
		while (cleaned.endsWith(".") || cleaned.endsWith(" "))
			cleaned = cleaned.substring(0, cleaned.length() - 1);

		return cleaned;
	}
	
	/**
	 * 
	 * @param repoUrl in format "https://github.com/repoOwner/repoName"
	 * @return String repoName
	 */
	public static String extractGhRepoName(String repoUrl) {
		int repoNameStartIndex = repoUrl.indexOf("/", 19) + 1;
		return repoUrl.substring(repoNameStartIndex, repoUrl.length());			
	}
	
	/**
	 * 
	 * @param repoUrl in format "https://github.com/repoOwner/repoName"
	 * @return String repoOwner
	 */
	public static String extractGhRepoOwner(String repoUrl) {
		int repoNameStartIndex = repoUrl.indexOf("/", 19) + 1;	
		return repoUrl.substring(19, repoNameStartIndex - 1);
	}
	
	public static void main(String[] args) throws IOException {
		String url = "https://github.com/eclipse/epsilon";
		System.out.println(extractGhRepoOwner(url.toString()));
		System.out.println(extractGhRepoName(url.toString()));
	}
	
	public static void removeRepoClones(File repoLocation) {
		// clean local clone parent destination if it exists
		if (repoLocation.exists()) {
			try {
				FileUtils.deleteDirectory(repoLocation);
				System.out.println("Successfully cleaned repo clone parent: "
						+ repoLocation.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repoLocation.mkdir();
		}
	}
	

}
