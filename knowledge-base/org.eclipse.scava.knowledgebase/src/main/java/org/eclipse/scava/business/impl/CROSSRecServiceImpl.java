package org.eclipse.scava.business.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.scava.business.dto.Dependency;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationItem;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.ArtifactTypeRepository;
import org.eclipse.scava.business.integration.CROSSRecGraphRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.ArtifactType;
import org.eclipse.scava.business.model.CROSSRecGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

class ValueComparator implements Comparator<String> {

	Map<String, Double> base;

	public ValueComparator(Map<String, Double> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with equals.
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}

@Service
public class CROSSRecServiceImpl {
	@Value("${crossrec.numberOfNeighbours}")
	private int numberOfNeighbours;
	
	@Value("${crossrec.numberOfRecommendedLibs}")
	private int numberOfRecommendedLibs;
	
	@Autowired
	private ArtifactTypeRepository artifactTypeRepository;
	
	@Autowired
	private ArtifactRepository artifactRepository;
	
	@Autowired
	private CROSSRecGraphRepository crossRecGraphRepository;
	
	private static final Logger logger = Logger.getLogger(ApiRecommendationProvider.class);
	
	public CROSSRecGraph createGraphFromArtifact(Artifact art) {
		CROSSRecGraph graph = new CROSSRecGraph();
		// Populate dictionary
		Map<Integer, String> backgroundDictionary = new HashMap<Integer, String>();
		Map<String, Integer> backgroundDictionarySwitch = new HashMap<String, Integer>();
		backgroundDictionary.put(1, art.getId());
		backgroundDictionarySwitch.put(art.getId(), 1);
		int count = 2;
		for (String dep : art.getDependencies()) {
			backgroundDictionary.put(count, "#DEP#" + dep.replace(".", "_"));
			backgroundDictionarySwitch.put("#DEP#" + dep.replace(".", "_"), count);
			count++;
		}
		count --;
		// Populate graph
		Set<Integer> outlinks = new HashSet<Integer>();
		Set<Integer> nodes = new HashSet<Integer>();
		for (int i = 1; i <= count; i++) {
			nodes.add(i);
		}
		for (int i = 2; i <= count; i++) {
			outlinks.add(i);
		}
		graph.getOutLinks().put(1, outlinks);
		graph.setNodeCount(nodes.size());
		graph.setDictionary(backgroundDictionarySwitch);
		graph.setDictionarySwitch(backgroundDictionary);
		return graph;
	}

	public CROSSRecGraph createGraphFromListDependecies(List<Dependency> dependencies) {
		Artifact art = new Artifact();
		art.setName("currentProject");
		art.setId("currentProject");
		for (Dependency dependency : dependencies) {
			art.getDependencies().add(dependency.getName());
		}
		return createGraphFromArtifact(art);
	}

	public Recommendation run(List<Dependency> dependencies) throws Exception {
		Recommendation result = new Recommendation();
		
		Map<String, Double> sim = computeWeightCosineSimilarity(dependencies);
		Map<String, Double> res = userBasedRecommendation(dependencies, sim);
		ValueComparator bvc =  new ValueComparator(res);        
		TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);
		sorted_map.putAll(res);
		int guard = 0;
		for(Map.Entry<String,Double> entry : sorted_map.entrySet()) {
			if (guard < numberOfRecommendedLibs) {
				Double value = entry.getValue();
				String lib = entry.getKey();
				RecommendationItem ri = new RecommendationItem();
				Artifact art = new Artifact();
				
				ArtifactType artType = artifactTypeRepository.findOneByName("Library");
				if(artType == null) {
					artType = new ArtifactType();
					artType.setName("Library");
				}
				art.setType(artType);
				art.setName(lib);
				ri.setArtifact(art);
				ri.setSignificance(value);
				ri.setRecommendationType("RecommendedLibrary");
				result.getRecommendationItems().add(ri);
				guard++;
			}
			else break;
		}
		return result;
	}
	
	private Set<String> extractDepsfromGraph(CROSSRecGraph graph) {
		Set<String> result = null;
		result = graph.getDictionary().keySet().stream().filter(z->z.startsWith("#DEP#")).collect(Collectors.toSet());
		return result;
	}

	private Set<String> extractProjectsfromGraph(CROSSRecGraph graph) {
		Set<String> result = null;
		result = graph.getDictionary().keySet().stream().filter(z->!z.startsWith("#DEP#")).collect(Collectors.toSet());
		return result;
	}
	
	public Map<String, Double> computeWeightCosineSimilarity(List<Dependency> dependencies) throws Exception{
		CROSSRecGraph bigGraph;
		List<CROSSRecGraph> tempGraphs = crossRecGraphRepository.findAll();
		if(tempGraphs.size() == 0) {
			bigGraph = createCROSSRecGraph();
		}
		else bigGraph = tempGraphs.get(0);

		Set<String> allLibs = extractDepsfromGraph(bigGraph);
		/*add all libraries from the training set*/
		CROSSRecGraph projectGraph = createGraphFromListDependecies(dependencies);		
		Map<String, Double> sim = new HashMap<String, Double>();
		Set<String> queryLibs = extractDepsfromGraph(projectGraph);
		
		allLibs.addAll(queryLibs);
		CROSSRecGraph globalGraph = combine(bigGraph, projectGraph);
		
		Map<Integer, Set<Integer>> graphEdges = globalGraph.getOutLinks();
		Set<Integer> keySet = graphEdges.keySet();
								
		Map<Integer, Double> libWeight = new HashMap<Integer, Double>();
		double freq = 0;
		for(Integer startNode:keySet){					
			Set<Integer> outlinks = graphEdges.get(startNode);					
			for(Integer endNode:outlinks){						
				if(libWeight.containsKey(endNode)) {
					freq = libWeight.get(endNode)+1;												
				} else freq = 1;
				libWeight.put(endNode, freq);					
			}				
		}
		/*get the number of projects in the whole graph*/
		int numberOfProjects = keySet.size();				
		keySet = libWeight.keySet();				
		double weight = 0, idf = 0;								
		for(Integer libID:keySet){
			freq = libWeight.get(libID);
			weight = (double)numberOfProjects/freq;
			idf = Math.log(weight);
			libWeight.put(libID, idf);									
		}			
		
		
		for (String artifactName : extractProjectsfromGraph(bigGraph)) {
			Artifact artifact = artifactRepository.findOne(artifactName);
			if(artifact != null) {
				Set<String> specificLibs = new HashSet<String>();
				for (String string : artifact.getDependencies()) {
					specificLibs.add(("#DEP#" + string).replace(".", "_")); 
				}
				List<String> libSet = new ArrayList<>(Sets.union(specificLibs, queryLibs));
				int size = libSet.size();
							
				double vector1[] = new double[size];
				double vector2[] = new double[size];
				double val=0;
									
				for(int i=0;i<size;i++) {	
					String lib = libSet.get(i);
					if(queryLibs.contains(lib)) {
						int libID = globalGraph.getDictionary().get(lib);
						vector1[i]=libWeight.get(libID);
					}
					else vector1[i]=0;
					
					if(specificLibs.contains(lib)) {
						int libID = globalGraph.getDictionary().get(lib);
						vector2[i]=libWeight.get(libID);
					}
					else vector2[i]=0;					
				}
				/*Using Cosine Similarity*/
				
				val = cosineSimilarity(vector1,vector2);					
				sim.put(artifact.getId(), val);		
			}
		}												
				
		return sim;
				
	}

	public CROSSRecGraph combine(CROSSRecGraph bigGraph, CROSSRecGraph graph) {
		if(bigGraph == null)
			return graph;
		if(graph == null)
			return bigGraph;
		Set<Integer> outlinks = new HashSet<Integer>();
		Set<Integer> mainOutlinks = new HashSet<Integer>();
		Set<Integer> key = graph.getOutLinks().keySet();
		String artifact = "";
		int idEndNode = 0, idStartNode = 0;
		for (Integer startNode : key) {
			outlinks = graph.getOutLinks().get(startNode);
			artifact = graph.getDictionarySwitch().get(startNode);
			idStartNode = extractKey(bigGraph, artifact);

			for (Integer endNode : outlinks) {
				artifact = graph.getDictionarySwitch().get(endNode);
				idEndNode = extractKey(bigGraph, artifact);
				if (bigGraph.getOutLinks().containsKey(idStartNode)) {
					mainOutlinks = bigGraph.getOutLinks().get(idStartNode);
				} else {
					mainOutlinks = new HashSet<Integer>();
				}
				mainOutlinks.add(idEndNode);
				bigGraph.getOutLinks().put(idStartNode, mainOutlinks);
			}
		}
		Set<Integer> nodes = new HashSet<Integer>();
		key = bigGraph.getOutLinks().keySet();
		
		for (Integer startNode : key) {
			nodes.add(startNode);
			mainOutlinks = bigGraph.getOutLinks().get(startNode);
			for (Integer endNode : mainOutlinks) {
				nodes.add(endNode);
			}
		}
		bigGraph.setNodeCount(nodes.size());
		return bigGraph;
	}
//
	private int extractKey(CROSSRecGraph g, String s) {
		if (g.getDictionary().containsKey(s))
			return g.getDictionary().get(s);
		else {
			int c = g.getDictionary().size() + 1;
			g.getDictionarySwitch().put(c, s);
			g.getDictionary().put(s, c);
			return c;
		}
	}
//
//	/* Compute the cosine similarity between two vectors */
//
	private double cosineSimilarity(double[] vector1, double[] vector2) {
		double sclar = 0, norm1 = 0, norm2 = 0;
		int length = vector1.length;
		for (int i = 0; i < length; i++)
			sclar += vector1[i] * vector2[i];
		for (int i = 0; i < length; i++)
			norm1 += vector1[i] * vector1[i];
		for (int i = 0; i < length; i++)
			norm2 += vector2[i] * vector2[i];
		double ret = 0;
		double norm = norm1 * norm2;
		ret = (double) sclar / Math.sqrt(norm);
		return ret;
	}
	
	/*Recommends libraries to an input project using the user-based collaborative-filtering technique*/
	public Map<String, Double> userBasedRecommendation(List<Dependency> dependencies, Map<String, Double> sim){
		Set<String> depsStringSet = dependencies.stream().map(z -> z.getName()).collect(Collectors.toSet());
		
		Map<String, Set<String>> allNeighbourLibs = new HashMap<String, Set<String>>();
		List<String> libSet = new ArrayList<String>();			
						
									
		Map<String, Double> recommendations = new HashMap<String, Double>();
		Map<Integer, Double> simMatrix = new HashMap<Integer, Double>();
					
		ValueComparator bvc =  new ValueComparator(sim);        
		TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);
		sorted_map.putAll(sim);
		
		List<String> keySet; 
			if(numberOfNeighbours -1 < sorted_map.keySet().size()-1)
				keySet =  new ArrayList<String>(sorted_map.keySet()).subList(0, numberOfNeighbours -1);
			else
				keySet = new ArrayList<String>(sorted_map.keySet());
		Set<String> libraries = new HashSet<String>();
		//Prendere le libreria per ogni progetto nel set									
		for(String key: keySet) {				
			Artifact art = artifactRepository.findOne(key);
			allNeighbourLibs.put(key, new HashSet<String>(art.getDependencies()));
			libraries.addAll(art.getDependencies());
		}
		allNeighbourLibs.put("currentProject", depsStringSet);
		/*The list of all libraries from the training projects and the testing project*/
		libraries.addAll(dependencies.stream().map(z -> z.getName()).collect(Collectors.toList()));
		/*change the set to an ordered list*/
		libraries.forEach(z-> libSet.add(z));
		
					
		/*Number of projects, including the test project*/
		int M = numberOfNeighbours + 1;
		/*Number of libraries*/
		int N = libraries.size();
					
		double userItemMatrix[][] = new double[M][N];
		
		int ind = 0;
		for(String key : allNeighbourLibs.keySet()){				
			Set<String> tmpLibs = allNeighbourLibs.get(key);
			for(int j=0;j<N;j++) {					
				if(tmpLibs.contains(libSet.get(j))) {											
					userItemMatrix[ind][j]=1.0;						
				}
				else userItemMatrix[ind][j]=0;
			}
			ind++;
		}					
		/*Here is the test project and it needs recommendation. It is located at the end of the list.*/
		for(int j=0;j<N;j++) {
			String str = libSet.get(j);
			if(depsStringSet.contains(str))userItemMatrix[numberOfNeighbours][j]=1.0;
			else {
				userItemMatrix[numberOfNeighbours][j]=-1.0;					
			}
		}
		/*Calculate the missing ratings using the item-based collaborative-filtering recommendation technique*/
		double tmpUserItemMatrix[][] = new double[M][N];
		/*copy the matrix*/			
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				tmpUserItemMatrix[i][j]=userItemMatrix[i][j];					
			}				
		}
		double val1 = 0;		
		/*average rating is computed for the projects that include a library in the library set, so it is 1*/
		double avgRating = 1.0, tmpRating = 0.0;
		int guard = 0;
		for(Map.Entry<String,Double> entry : sorted_map.entrySet()) {
			if (guard<numberOfNeighbours) {
			  Double value = entry.getValue();
			  val1 += value;
			  simMatrix.put(guard, value);
			  guard++;
			}
			else break;
		} 
			
		for(int j=0;j<N;j++) {				
			if(userItemMatrix[numberOfNeighbours][j]==-1) {					
				double val2=0;					
				for(int k=0;k<numberOfNeighbours;k++) {
					if(k >= artifactRepository.count())
						break;
					tmpRating = 0;
					for(int l=0;l<N;l++)tmpRating+=userItemMatrix[k][l];
					tmpRating = (double)tmpRating/N;						
					val2+=(userItemMatrix[k][j]-tmpRating)*simMatrix.get(k);									
				}					
				userItemMatrix[numberOfNeighbours][j] = avgRating + val2/val1;				
				recommendations.put(libSet.get(j), userItemMatrix[numberOfNeighbours][j]);				
			}				
		}								
		return recommendations;
	}
	
	public CROSSRecGraph createCROSSRecGraph() {
		List<Artifact> arts = artifactRepository.findAll();
		CROSSRecGraph graph = null;
		for (Artifact artifact : arts) {
			CROSSRecGraph graph1 = createGraphFromArtifact(artifact);
			graph = combine(graph, graph1);
		}
		crossRecGraphRepository.deleteAll();
		crossRecGraphRepository.save(graph);
		return graph;
	}
	
}
