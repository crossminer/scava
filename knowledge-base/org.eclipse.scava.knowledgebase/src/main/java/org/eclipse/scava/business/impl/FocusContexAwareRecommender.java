package org.eclipse.scava.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.MethodDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FocusContexAwareRecommender {
	@Autowired
	private FOCUSSimilarityCalculator fsc;
	private static int NUM_OF_NEIGHBOURS = 2;
	private static final Logger log = LoggerFactory.getLogger(FocusContexAwareRecommender.class);
	public FocusContexAwareRecommender() {
	}

	public Map<String, Float> recommends(List<Artifact> trainingProjects, Artifact testingProject, String activeDeclaration)
			throws ActiveDeclarationNotFoundException {
		log.info(String.format("FOCUS is computing recomendation for %s project with %s as active declaraion", testingProject.getName(), activeDeclaration));
		Map<String, Float> recommendations = new HashMap<>();
		List<Artifact> listOfPRs = new ArrayList<>();
		List<String> listOfMIs = new ArrayList<>();
		Map<Artifact, Float> simScores = fsc.computeSimilarity(testingProject, trainingProjects);
		byte matrix[][][] = buildUserItemContextMatrix(testingProject, listOfPRs, listOfMIs, simScores,
				activeDeclaration);
		Map<String, Float> mdSimScores = new HashMap<String, Float>();
		// Compute the jaccard similarity between the testingMethod and every other
		// method
		// and store the results in mdSimScores
		byte[] testingMethodVector = matrix[matrix.length - 1][matrix[0].length - 1];
		for (int i = 0; i < matrix.length - 1; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				byte[] otherMethodVector = matrix[i][j];
				float sim = fsc.computeJaccardSimilarity(testingMethodVector, otherMethodVector);
				String key = Integer.toString(i) + "#" + Integer.toString(j);
				mdSimScores.put(key, sim);
			}
		}
		// Sort the results
		StringComparator bvc = new StringComparator(mdSimScores);
		TreeMap<String, Float> simSortedMap = new TreeMap<>(bvc);
		simSortedMap.putAll(mdSimScores);

		// Compute the top-3 most similar methods
		Map<String, Float> top3Sim = new HashMap<>();
		int count = 0;
		for (String key : simSortedMap.keySet()) {
			top3Sim.put(key, mdSimScores.get(key));
			count++;
			if (count > 3)
				break;
		}
		float[] ratings = new float[matrix[0][0].length - 1];
		// For every '?' cell (-1.0), compute a rating
		for (int k = 0; k < matrix[0][0].length; k++) {
			if (matrix[matrix.length - 1][matrix[0].length - 1][k] == -1) {
				double totalSim = 0;
				// Iterate over the top-3 most similar methods
				for (String key : top3Sim.keySet()) {
					String line = key.trim();

					String parts[] = line.split("#");
					int slice = Integer.parseInt(parts[0]);
					int row = Integer.parseInt(parts[1]);

					// Compute the average rating of the method declaration
					double avgMDRating = 0;
					for (int m = 0; m < matrix[0][0].length; m++)
						avgMDRating += matrix[slice][row][m];
					avgMDRating /= matrix[0][0].length;

					Artifact project = listOfPRs.get(slice);
					double projectSim = simScores.get(project);
					double val = projectSim * matrix[slice][row][k];
					double methodSim = top3Sim.get(key);
					totalSim += methodSim;
					ratings[k] += (val - avgMDRating) * methodSim;
				}
				if (totalSim != 0)
					ratings[k] /= totalSim;
				double activeMDrating = 0.8;
				ratings[k] += activeMDrating;
				String methodInvocation = listOfMIs.get(k);
				recommendations.put(methodInvocation, ratings[k]);
			}
		}

		StringComparator bvc2 = new StringComparator(recommendations);
		TreeMap<String, Float> recSortedMap = new TreeMap<>(bvc2);
		recSortedMap.putAll(recommendations);
		log.info(String.format("FOCUS computed recomendation for %s project with %s as active declaraion", testingProject.getName(), activeDeclaration));
		return recSortedMap;
	}

	public byte[][][] buildUserItemContextMatrix(Artifact testingProject, List<Artifact> listOfTrainingProjects,
			List<String> listOfMethodInvocations, Map<Artifact, Float> simScores, String activeDeclaration)
			throws ActiveDeclarationNotFoundException {
		log.info("Computing buildUserItemContextMatrix");
		if (!getActiveDeclarationMIs(testingProject, activeDeclaration))
			throw new ActiveDeclarationNotFoundException();
		List<Artifact> simProjects = getTopNSimilarProjects(simScores, NUM_OF_NEIGHBOURS);
		Map<Artifact, Map<String, Set<String>>> allProjects = new HashMap<>();
		List<Artifact> listOfPRs = new ArrayList<>();
		Set<String> allMDs = new HashSet<>();
		Set<String> allMIs = new HashSet<>();
		for (Artifact artifact : simProjects) {
			Map<String, Set<String>> tmpMIs = getMDsMIs(artifact);
			allMDs.addAll(tmpMIs.keySet());
			for (Set<String> mis : tmpMIs.values())
				allMIs.addAll(mis);
			allProjects.put(artifact, tmpMIs);
			listOfPRs.add(artifact);
		}
		// The slice for the testing project is located at the end of the matrix
		listOfPRs.add(testingProject);
		Map<String, Set<String>> testingMIs = getMDsMIs(testingProject);
		allMDs.addAll(testingMIs.keySet());
		for (Set<String> s : testingMIs.values())
			allMIs.addAll(s);
		allProjects.put(testingProject, testingMIs);

		// Convert to an ordered list of all method declarations to make sure
		// that the testing method declaration locates at the end of the list
		List<String> listOfMDs = new ArrayList<>(allMDs);
		if (listOfMDs.contains(activeDeclaration))
			listOfMDs.remove(listOfMDs.indexOf(activeDeclaration));
		listOfMDs.add(activeDeclaration);

		// Convert to an ordered list of all method invocations to make sure
		// that all testing method invocations locate at the end of the list
		List<String> listOfMIs = new ArrayList<>(allMIs);
		for (String testingMI : testingMIs.get(activeDeclaration))
			if (listOfMIs.contains(testingMI))
				listOfMIs.remove(listOfMIs.indexOf(testingMI));
		for (String testingMI : testingMIs.get(activeDeclaration))
			listOfMIs.add(testingMI);
		int numOfSlices = listOfPRs.size();
		int numOfRows = listOfMDs.size();
		int numOfCols = listOfMIs.size();
		byte[][][] matrix = new byte[numOfSlices][numOfRows][numOfCols];
		// Populate all cells in the user-item-context ratings matrix using 1s and 0s
		for (int i = 0; i < numOfSlices - 1; i++) {
			Artifact currentPro = listOfPRs.get(i);
			Map<String, Set<String>> myMDs = allProjects.get(currentPro);
			for (int j = 0; j < numOfRows; j++) {
				String currentMD = listOfMDs.get(j);
				if (myMDs.containsKey(currentMD)) {
					Set<String> myMIs = myMDs.get(currentMD);
					for (int k = 0; k < numOfCols; k++) {
						String currentMI = listOfMIs.get(k);
						if (myMIs.contains(currentMI))
							matrix[i][j][k] = (byte) 1;
					}
				}
			}
		}

		// This is the testing project, ie. the last slice of the 3-D matrix
		Map<String, Set<String>> myMDs = allProjects.get(testingProject);
		for (int j = 0; j < numOfRows - 1; j++) {
			String currentMD = listOfMDs.get(j);
			if (myMDs.containsKey(currentMD)) {
				Set<String> myMIs = myMDs.get(currentMD);
				for (int k = 0; k < numOfCols; k++) {
					String currentMI = listOfMIs.get(k);
					if (myMIs.contains(currentMI))
						matrix[numOfSlices - 1][j][k] = (byte) 1;
				}
			}
		}

		String currentMD = listOfMDs.get(numOfRows - 1);
		Set<String> myMIs = myMDs.get(currentMD);
		for (int k = 0; k < numOfCols; k++)
			if (myMIs.contains(listOfMIs.get(k)))
				matrix[numOfSlices - 1][numOfRows - 1][k] = (byte) 1;
			else
				matrix[numOfSlices - 1][numOfRows - 1][k] = (byte) -1;

		for (Artifact l : listOfPRs)
			listOfTrainingProjects.add(l);

		for (String l : listOfMIs)
			listOfMethodInvocations.add(l);
		log.info("Computed buildUserItemContextMatrix");
		return matrix;
	}

	private boolean getActiveDeclarationMIs(Artifact testingProject, String activeDeclaration)
			throws ActiveDeclarationNotFoundException {
		for (MethodDeclaration md : testingProject.getMethodDeclarations())
			if (md.getName().equals(activeDeclaration))
				return true;
		return false;

	}

	private Map<String, Set<String>> getMDsMIs(Artifact artifact) {
		Map<String, Set<String>> result = new HashMap<>();
		for (MethodDeclaration iterable_element : artifact.getMethodDeclarations()) {
			result.put(iterable_element.getName(), new HashSet<String>(iterable_element.getMethodInvocations()));
		}
		return result;
	}

	private List<Artifact> getTopNSimilarProjects(Map<Artifact, Float> simScores, int numOfNeighbours2) {
		int count = 0;
		List<Artifact> results = new ArrayList<>();
		for (Map.Entry<Artifact, Float> iterable_element : simScores.entrySet()) {
			if (!(count < numOfNeighbours2))
				return results;
			results.add(iterable_element.getKey());
			count++;
		}
		return results;
	}

}
