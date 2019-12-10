package org.eclipse.scava.business.impl;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

@Service
@Qualifier("Focus")
public class FocusCodeSnippetRecommender implements IRecommendationProvider {
	@Autowired
	private FOCUSSimilarityCalculator fsc;
	@Autowired
	private FocusContexAwareRecommender fcar;
	@Autowired
	private ISimilarityManager simManger;
	@Value("${focus.code.snippet.numOfApiFunctionCalls}")	
	private static int _numOfApiFunctionCalls = 5;
	@Value("${focus.code.snippet.numOfCodeSnippetResult}")
	private static int _numOfCodeSnippetResult = 10;
	
	@Value("${focus.code.snippet.path}")	
	private String jarPath;
	Maracas maracas = new Maracas();
	@Value("${focus.extension}")	
	private String FOCUS_EXTENSION;

	private static final Logger log = LoggerFactory.getLogger(FocusCodeSnippetRecommender.class);

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
	boolean isVersionInSet(Set<String> set, String fullname) {
		String[] name = fullname.split("/");
		String withVers = name[name.length-1]
				.replace("g_","")
				.replace(".jar.txt", "");// /Users/juri/Desktop/focusImport/g_runtime-0.1.2-incubating.jar.txt
		String noVers = withVers.substring(0, withVers.lastIndexOf("-"));
		if(set.contains(noVers))
			return true;
		set.add(noVers);
		return false;
	}
	public Map<Artifact, Double> getTopNSimilarProjects(List<String> query, int n) {
		Map<Artifact, Double> v = getSimilarProjects(query);
		Set<String> already = Sets.newHashSet();
		return v.entrySet().stream()
				.sorted(Map.Entry.<Artifact, Double>comparingByValue().reversed())
				.filter(z -> !isVersionInSet(already, z.getKey().getFullName()))
				.limit(n)
				.collect(Collectors.toMap(Map.Entry::getKey, 
						Map.Entry::getValue, 
						(oldValue, newValue) -> oldValue,
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
		queryMIs.replaceAll(x -> cleanString(x));
		Map<Artifact, Double> simArts = getTopNSimilarProjects(queryMIs, _numOfCodeSnippetResult);
		Map<Artifact, String> clientMethodMap = Maps.newHashMap();
		log.debug("======================================");
		for (Entry<Artifact, Double> art_simEntry : simArts.entrySet()) {
			Artifact art = art_simEntry.getKey();
			String method0 = cleanString(getBestMethodDeclaration(art, queryMIs));
			if (!method0.endsWith("$initializer")) {
				clientMethodMap.put(art, method0);
				log.debug("Similarity log: {} - {} - {} ", art_simEntry.getValue(), art.getName(), method0);
			}
		}
		return clientMethodMap;
	}

	private String cleanString(String rascalString) {
		return rascalString
				.replace("|java+method:///", "")
				.replace("|java+constructor:///", "")
				.replace("|", "");
	}
	
	private List<String> getCodeSnippetFromRecommendation(Map<Artifact, String> clientMethodMap) {
		List<String> result = Lists.newArrayList();
		for (Entry<Artifact, String> ent : clientMethodMap.entrySet()) {
			try {
				String jarName = ent.getKey().getName().replace("g_", "").replace(FOCUS_EXTENSION, "").replace(".jar", "-sources.jar");
				String invocation = ent.getValue().replace("%5B%5D", "[]");
				log.debug("\tlooking for {} in {}", invocation, jarName);
				String methodQuery = "|java+method:///" + invocation + "|";
				String s = getCode(Paths.get(jarPath, jarName).toString(), methodQuery);
				if (s == null || s.isEmpty()) {
					methodQuery = "|java+constructor:///" + invocation + "|";
					s = getCode(Paths.get(jarPath, jarName).toString(), methodQuery);
				}
				log.debug("\t\t{} get snippet is not empty: {}", jarName, !s.isEmpty());
				if (s != null && !s.isEmpty())
					result.add(s);
			} catch (Exception e) {
				log.error("\t\tExtracting code error {}: {}", ent.getKey().getName(), e.getMessage());
			}
		}
		return result;
	}

	public List<String> focusCodeSmippetRecommender(Query query) throws Exception {
		Artifact a = new Artifact();
		a.setMethodDeclarations(query.getFocusInput().getMethodDeclarations());
		a.setName("INPUTPROJECT");
		List<Artifact> arts = simManger.appliableProjects(fsc);
		Map<String, Float> apiFunctionCalls = fcar.recommends(arts, a, query.getFocusInput().getActiveDeclaration());
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
			Map<Artifact, String> clientMethodMap = getBestMethodFromSimilarPriojects(queryMIs);
			List<String> result = getCodeSnippetFromRecommendation(clientMethodMap);
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
			log.info("\t\tLib1 store {} {}", coord, bLib1);
			if (!bLib1) {
				log.error("\t\terror computing {} m3 model", coord);
				throw new Exception();
			}
		}
		return libM3;
	}

	public String getCode(String jarFile, String location) throws Exception {
//		if(!jarPath.endsWith("sources.jar"))
//			jarFile = jarFile.replace(".jar", "-sources.jar");
		String sources = getSources(jarFile);
		String result = maracas.getCodeFromM3(sources, location);
		return result;
	}

	@Override
	public Recommendation getRecommendation(Query query) throws Exception {
		List<String> v = focusCodeSmippetRecommender(query);
		Recommendation r = new Recommendation();
		r.setRecommendationItems(Lists.newArrayList());
		log.info("Start recommender");
		for (String snippet : v) {
			RecommendationItem ri = new RecommendationItem();
			ri.setCodeSnippet(snippet);
			r.getRecommendationItems().add(ri);
		}
		log.info("#COMPUTED {} RECOMMENTADIONS", v.size());
		return r;
	}

}
