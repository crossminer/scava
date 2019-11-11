package org.eclipse.scava.business.impl;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.ISimilarityManager;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationItem;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.MethodDeclaration;
import org.maracas.Maracas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

@Service
@Qualifier("Focus")
public class FocusContexAwareRecommender implements IRecommendationProvider {
	private static int NUM_OF_NEIGHBOURS = 2;
	@Autowired
	private FOCUSSimilarityCalculator fsc;
	@Autowired
	private ISimilarityManager simManger;

	private static final Logger log = LoggerFactory.getLogger(FocusContexAwareRecommender.class);

	public FocusContexAwareRecommender() {
	}

	public Map<String, Float> recommends(List<Artifact> trainingProjects, Artifact testingProject,
			String activeDeclaration) throws ActiveDeclarationNotFoundException {
		log.info(String.format("FOCUS is computing recomendation for {} project with {} as active declaraion",
				testingProject.getName(), activeDeclaration));
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
		log.info(String.format("FOCUS computed recomendation for {} project with {} as active declaraion",
				testingProject.getName(), activeDeclaration));
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

	@Override
	public Recommendation getRecommendation(Query query) throws Exception {
		Artifact a = new Artifact();
		a.setMethodDeclarations(query.getFocusInput().getMethodDeclarations());
		a.setName("INPUTPROJECT");
		List<Artifact> arts = simManger.appliableProjects(fsc);
		Map<String, Float> ret = recommends(arts, a, query.getFocusInput().getActiveDeclaration());
		Recommendation rec = new Recommendation();
		rec.setRecommendationItems(new ArrayList<RecommendationItem>());
		RecommendationItem recItm = new RecommendationItem();
		recItm.setApiFunctionCallFOCUS(ret);
		recItm.setRecommendationType("FOCUS");
		rec.getRecommendationItems().add(recItm);
		return rec;
	}

	public double getJaccardSmilarity(List<String> methodInvolcationsQuery, List<String> methodInvocationProject) {
		int leftLength = methodInvolcationsQuery.size();
		int rightLength = methodInvocationProject.size();
		if (leftLength == 0 || rightLength == 0)
			return 0d;
		Set<String> querySet = Sets.newHashSet(methodInvolcationsQuery);
		Set<String> projectSet = Sets.newHashSet(methodInvocationProject);
		SetView<String> intersect = Sets.intersection(querySet, projectSet);
		SetView<String> union = Sets.union(querySet, projectSet);
		return (2.0 * intersect.size()) / union.size();
	}

	public Map<Artifact, Double> getTopNSimilarProjects(List<String> query, int n) {
		Map<Artifact, Double> v = getSimilarProjects(query);
		return v.entrySet().stream().sorted(Map.Entry.<Artifact, Double>comparingByValue().reversed()).limit(n)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));

	}

	public Map<Artifact, Double> getSimilarProjects(List<String> methodInvolcationsQuery) {
		List<Artifact> arts = simManger.appliableProjects(fsc);
		Map<Artifact, Double> result = Maps.newHashMap();
		for (Artifact artifact : arts) {
			double res = 0d;
			for (MethodDeclaration mDs : artifact.getMethodDeclarations()) {
				if (!mDs.getName().endsWith("$initializer")) {
					double sim = getJaccardSmilarity(methodInvolcationsQuery, mDs.getMethodInvocations());
					if (sim > res)
						res = sim;
				}
			}
			result.put(artifact, res);
		}
		return result;
	}

	public String getBestMethodDeclaration(Artifact art, List<String> query) {
		String md = "";
		double res = 0d;
		for (MethodDeclaration mDs : art.getMethodDeclarations()) {
			double sim = getJaccardSmilarity(query, mDs.getMethodInvocations());
			if (sim > res) {
				res = sim;
				md = mDs.getName();
			}
		}
		return md;
	}

	/**
		 *  GET SIMILAR PROJECT AND MAP THE BEST METHOD
		 */
	private Map<Artifact, String> getBestMethodFromSimilarPriojects(List<String> queryMIs) {
		
		Map<Artifact, Double> simArts = getTopNSimilarProjects(queryMIs, _numOfCodeSnippetResult);
		Map<Artifact, String> clientMethodMap = Maps.newHashMap();
		log.info("======================================");
		for (Entry<Artifact, Double> art_simEntry : simArts.entrySet()) {
			Artifact art = art_simEntry.getKey();
			String method0 = getBestMethodDeclaration(art, queryMIs);
			if (!method0.endsWith("$initializer")) {
				clientMethodMap.put(art, method0);
				log.info("{} - {} - {} ", art_simEntry.getValue(), art.getName(), method0);
			}
		}
		return clientMethodMap;
	}

	private List<String> getCodeSnippetFromRecommendation(Map<Artifact, String> clientMethodMap) {
		List<String> result = Lists.newArrayList();
		log.info("======================================");
		for (Entry<Artifact, String> ent : clientMethodMap.entrySet()) {
			try {
				String name = ent.getKey().getName().replace("g_", "").replace(".focus", "").replace(".jar",
						"-sources.jar");
				log.info("___________{}_____________", name);
				String value = ent.getValue().replace("%5B%5D", "[]");
				String methodQuery = "|java+method:///" + value + "|";
				log.info("looking for {}", methodQuery);
				String s = getCode(Paths.get(jarPath, name).toString(), methodQuery);
				if (s == null || s.isEmpty()) {
					methodQuery = "|java+constructor:///" + value + "|";
					log.info("looking for {}", methodQuery);
					s = getCode(Paths.get(jarPath, name).toString(), methodQuery);
				}
				log.info("{} {}", methodQuery, !s.isEmpty());
				if (s != null && !s.isEmpty())
					result.add(s);
			} catch (Exception e) {
				log.error("Extracting code error {}: {}", ent.getKey().getName(), e.getMessage());
			}
		}
		return result;
	}

	private static int _numOfApiFunctionCalls = 25;
	private static int _numOfCodeSnippetResult = 10;
	private final String jarPath = "/Users/juri/Desktop/intiJars";
	Maracas maracas = new Maracas();

	public List<String> focusCodeSmippetRecommender(Query query) throws Exception {
		Artifact a = new Artifact();
		a.setMethodDeclarations(query.getFocusInput().getMethodDeclarations());
		a.setName("INPUTPROJECT");
		List<Artifact> arts = simManger.appliableProjects(fsc);
		Map<String, Float> apiFunctionCalls = recommends(arts, a, query.getFocusInput().getActiveDeclaration());
		Optional<MethodDeclaration> myActiveMethod = query.getFocusInput().getMethodDeclarations().stream()
				.filter(z -> z.getName().equals(query.getFocusInput().getActiveDeclaration())).findFirst();
		if (myActiveMethod.isPresent()) {
			int i = 0;

			// CREATE METHODO API FUNCTION CALLS TO SEARCH
			List<String> queryMIs = Lists.newArrayList();
			queryMIs.addAll(myActiveMethod.get().getMethodInvocations());
			for (Entry<String, Float> artifact : apiFunctionCalls.entrySet())
				if (i < _numOfApiFunctionCalls) {
					queryMIs.add(artifact.getKey());
					i++;
				} else
					break;

			// GET SIMILAR PROJECT AND MAP THE BEST METHOD
			Map<Artifact, String> clientMethodMap = getBestMethodFromSimilarPriojects(queryMIs);
			// EXTRACT CODE FROM
			List<String> result = getCodeSnippetFromRecommendation(clientMethodMap);
			result.forEach(z -> log.info(z));
			return result;
		}
		return Lists.newArrayList();
	}

	private String getSources(String coord) throws Exception {
		String libM3 = coord + "_src.m3";
		if (!Files.exists(Paths.get(libM3), new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
			String jarDir = libM3 + "_dir";
			maracas.unzipJar(coord, jarDir);
			boolean bLib1 = maracas.storeM3FromDir(jarDir, libM3);
			log.info("Lib1 store: " + bLib1);
			if (!bLib1) {
				log.error("error computing {} m3 model", coord);
				throw new Exception();
			}
		}
		return libM3;
	}

	public String getCode(String jarFile, String location) throws Exception {
		String sources = getSources(jarFile);
		String result = maracas.getCodeFromM3(sources, location);
		return result;
	}

}
