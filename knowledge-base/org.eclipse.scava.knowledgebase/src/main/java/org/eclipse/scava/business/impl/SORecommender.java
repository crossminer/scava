package org.eclipse.scava.business.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationItem;
import org.eclipse.scava.business.integration.LibBoostRepository;
import org.eclipse.scava.business.model.LibBoost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Qualifier("SORecommender")
public class SORecommender implements IRecommendationProvider {
	@Autowired
	private LibBoostRepository libBoostRepo;

	@Value("${sorecommender.titleBoostValue}")
	private double titleBoostValue;
	@Value("${sorecommender.hitsPerPage}")
	private int hitsPerPage;
	@Value("${sorecommender.bm25}")
	private boolean isBm25;
	@Value("${sorecommender.answerBoostValue}")
	private double answerBoostValue;
	@Value("${sorecommender.questionBoostValue}")
	private double questionBoostValue;
	@Value("${sorecommender.luceneTreshold}")
	private double luceneTreshold;

	@Value("${sorecommender.INDEX_DIRECTORY}")
	private String INDEX_DIRECTORY;
	private static final Logger logger = LoggerFactory.getLogger(SORecommender.class);

	private HashMap<String, HashSet<String>> extractTokensFromCode(String soSnippet) throws IOException {
		HashMap<String, HashSet<String>> postTokens = new HashMap<String, HashSet<String>>();
		postTokens = parseKCUnit(soSnippet);
		if (postTokens.size() < 1)
			postTokens = parseKStatements(soSnippet);
		if (postTokens.size() < 1) {
			soSnippet = classBugFixer(soSnippet);
			postTokens = parseKCUnit(soSnippet);
			if (postTokens.size() < 1)
				postTokens = parseKStatements(soSnippet);
		}
		return postTokens;
	}

	private HashMap<String, HashSet<String>> parseKCUnit(String snippet) throws IOException {
		HashMap<String, HashSet<String>> tokens = new HashMap<String, HashSet<String>>();
		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(snippet.toCharArray());
		parser.setResolveBindings(true);
		Hashtable<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_DOC_COMMENT_SUPPORT, JavaCore.ENABLED);
		parser.setCompilerOptions(options);
		try {
			CompilationUnit cu = (CompilationUnit) parser.createAST(null);
			MyASTVisitor myVisitor = new MyASTVisitor();
			cu.accept(myVisitor);
			tokens = myVisitor.getTokens();
		} catch (Exception exc) {
			logger.error("JDT parsing error");
		}
		return tokens;
	}

	private HashMap<String, HashSet<String>> parseKStatements(String snippet) throws IOException {
		HashMap<String, HashSet<String>> tokens = new HashMap<String, HashSet<String>>();
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_STATEMENTS);
		parser.setBindingsRecovery(true);
		Map<String, String> options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
		parser.setUnitName("test");
		String src = snippet;
		String[] sources = {};
		String[] classpath = { "/usr/lib/jvm/java-8-openjdk-amd64" };
		parser.setEnvironment(classpath, sources, new String[] {}, true);
		parser.setSource(src.toCharArray());
		try {
			Block block = (Block) parser.createAST(null);
			MyASTVisitor myVisitor = new MyASTVisitor();
			block.accept(myVisitor);
			tokens = myVisitor.getTokens();
		} catch (Exception exc) {
			logger.error("JDT parsing error");
		}
		return tokens;
	}

	private String classBugFixer(String snippet) {
		String fix = "public class fix{ ";
		fix += snippet + "}";
		return fix;
	}

	@Override
	public Recommendation getRecommendation(Query rec_query) {
		Recommendation rec = new Recommendation();
		try {

			String compUnit = "";
			if (rec_query.getSoRecommendationSelection() == null
					|| rec_query.getSoRecommendationSelection().isEmpty()) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("ImportDeclaration", "OR");
				param.put("MethodDeclaration", "OR");
				param.put("MethodInvocation", "OR");
				param.put("VariableDeclaration", "OR");
				param.put("ClassInstance", "OR");
				param.put("VariableDeclarationType", "OR");
				compUnit = makeBoostedQuery(rec_query.getCompilationUnit(), param);
			} else
				compUnit = makeBoostedQuery(rec_query.getCompilationUnit(), rec_query.getSoRecommendationSelection());

			File indexDirectory = new File(INDEX_DIRECTORY);
			Directory indexDir = FSDirectory.open(Paths.get(indexDirectory.getAbsolutePath()));
			IndexReader reader = DirectoryReader.open(indexDir);
			IndexSearcher searcher = new IndexSearcher(reader);
			List<String> fields2 = getAllIndexTags(INDEX_DIRECTORY);
			String[] fields = new String[fields2.size()];
			fields = fields2.toArray(fields);
			Analyzer analzer = new StandardAnalyzer();
			MultiFieldQueryParser qp = new MultiFieldQueryParser(fields, analzer);
			org.apache.lucene.search.Query q = qp.parse(compUnit);
			TopDocs results = executeQuery(q);
			if (results != null) {
				int counter = 0;
				ArrayList<Explanation> expls = new ArrayList<Explanation>();
				ArrayList<String> Ids = new ArrayList<String>();
				for (ScoreDoc result : results.scoreDocs) {
					if (counter < luceneTreshold) {
						RecommendationItem ri = new RecommendationItem();
						org.apache.lucene.document.Document d = searcher.doc(result.doc);
						ri.setApiDocumentationLink(d.get("ID_POST"));
						expls.add(searcher.explain(q, result.doc));
						ri.setSignificance(result.score);
						Ids.add(d.get("ID_POST"));
						counter += 1;
						rec.getRecommendationItems().add(ri);
					}
				}
			}
		} catch (IOException | ParseException e) {
			logger.error(e.getMessage());
		}
		return rec;
	}

	public String makeBoostedQuery(String snippet, Map<String, String> param) throws IOException {

		HashMap<String, HashSet<String>> tokens = extractTokensFromCode(snippet);
		HashMap<String, Double> entropies = getEntropy(snippet, tokens);
		ArrayList<String> imports = getImportList(tokens);
		String result = makeBoostedQuery(tokens, imports, entropies, param);
		return result;

	}

	public Recommendation getVersionsSORecommendations(String v1, String v2) {
		v1 = v1.replace(":", " ");
		v2 = v2.replace(":", " ");
		StringBuilder sb = new StringBuilder(500);
		sb.append(String.format(" Title:%s OR", v1));
		sb.append(String.format(" Answer:%s OR", v1));
		sb.append(String.format(" Question:%s OR", v1));
		sb.append(String.format(" Title:%s OR", v2));
		sb.append(String.format(" Answer:%s OR", v2));
		sb.append(String.format(" Question:%s", v2));
		Recommendation rec = new Recommendation();
		try {
			File indexDirectory = new File(INDEX_DIRECTORY);
			Directory indexDir;
			indexDir = FSDirectory.open(Paths.get(indexDirectory.getAbsolutePath()));
			IndexReader reader = DirectoryReader.open(indexDir);
			IndexSearcher searcher = new IndexSearcher(reader);
			List<String> fields2 = getAllIndexTags(INDEX_DIRECTORY);
			String[] fields = new String[fields2.size()];
			fields = fields2.toArray(fields);
			Analyzer analzer = new StandardAnalyzer();
			MultiFieldQueryParser qp = new MultiFieldQueryParser(fields, analzer);
			org.apache.lucene.search.Query q = qp.parse(sb.toString());
			TopDocs results = executeQuery(q);
			if (results != null) {
				int counter = 0;
				ArrayList<Explanation> expls = new ArrayList<Explanation>();
				ArrayList<String> Ids = new ArrayList<String>();
				for (ScoreDoc result : results.scoreDocs) {
					if (counter < luceneTreshold) {
						RecommendationItem ri = new RecommendationItem();
						org.apache.lucene.document.Document d = searcher.doc(result.doc);
						ri.setApiDocumentationLink(d.get("ID_POST"));
						expls.add(searcher.explain(q, result.doc));
						ri.setSignificance(result.score);
						Ids.add(d.get("ID_POST"));
						counter += 1;
						rec.getRecommendationItems().add(ri);
					}
				}
			}
		} catch (IOException e) {
			logger.error("IO error on {} {}", INDEX_DIRECTORY, e.getMessage());
		} catch (ParseException e) {
			logger.error("Error {} {} parsing query {}", v1, v2, e.getMessage());
		}
		return rec;
	}

	private List<String> getAllIndexTags(String INDEX_DIRECTORY) {
		Collection<String> result = new HashSet<String>();
		try {
			IndexReader luceneIndexReader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
			result = MultiFields.getIndexedFields(luceneIndexReader);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		List<String> sortedList = new ArrayList<String>(result);
		Collections.sort(sortedList);

		return sortedList;
	}

	public TopDocs executeQuery(org.apache.lucene.search.Query query) throws IOException, ParseException {
		Directory indexDir = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
		try {
			IndexReader reader = DirectoryReader.open(indexDir);
			IndexSearcher searcher = new IndexSearcher(reader);
			if (isBm25 == false) {
				ClassicSimilarity CS = new ClassicSimilarity();
				searcher.setSimilarity(CS);
			}
			TopDocs docs = searcher.search(query, hitsPerPage);
			return docs;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	private String makeBoostedQuery(HashMap<String, HashSet<String>> tokens, ArrayList<String> imports,
			HashMap<String, Double> entropies, Map<String, String> parameter) throws IOException {
		String query = "";
		ArrayList<String> varDeclTypeFix = getImportList(tokens);
		for (Entry<String, HashSet<String>> token : tokens.entrySet()) {
			if (parameter.containsKey("ImportDeclaration") && token.getKey().equals("ImportDeclaration")) {
				String value = getTokenBoosted(token.getValue(), "ImportDeclaration", entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR ";
				query = query + getTokenBoosted(token.getValue(), "ImportDeclaration", entropies);
			}
			if (parameter.containsKey("MethodDeclaration") && token.getKey().equals("MethodDeclaration")) {
				String value = getTokenBoosted(token.getValue(), "MethodDeclaration", entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR ";
				query = query + getTokenBoosted(token.getValue(), "MethodDeclaration", entropies);
			}
			if (parameter.containsKey("MethodInvocation") && token.getKey().equals("MethodInvocation")) {
				String value = getTokenBoosted(token.getValue(), "MethodInvocation", entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR ";
				query = query + value;
			}
			if (parameter.containsKey("VariableDeclaration") && token.getKey().equals("VariableDeclaration")) {
				String value = getTokenBoosted(token.getValue(), "VariableDeclaration", entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR ";
				query = query + value;
			}
			if (parameter.containsKey("ClassInstance") && token.getKey().equals("ClassInstance")) {
				String value = getTokenBoosted(token.getValue(), "ClassInstance", entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR ";
				query = query + value;
			}
			if (parameter.containsKey("VariableDeclarationType") && token.getKey().equals("VariableDeclarationType")) {
				String value = getVariableDeclarationTypeBoosted(token, varDeclTypeFix, entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR";
				query = query + value;
			}
		}
		query = query.substring(0, query.length() - 3);
		ArrayList<String> vars = getBoostImport(tokens);
		query = query.isEmpty() ? "" : query + " OR ";
		query = getAnswer(vars, query, true);
		query = query.isEmpty() ? "" : query + " OR ";
		query = getQuestion(vars, query, true);
		query = query.isEmpty() ? "" : query + " OR ";
		query = getTitle(vars, query, true);
		return query;
	}

	private ArrayList<String> getImportList(HashMap<String, HashSet<String>> tokens) {
		/*
		 * vardecltype fix
		 */
		ArrayList<String> imports = new ArrayList<String>();
		if (tokens.containsKey("ImportDeclaration"))
			for (String token : tokens.get("ImportDeclaration")) {
				String imp = token.substring(token.lastIndexOf(".") + 1);
				imports.add(imp);
			}
		return imports;
	}


	private ArrayList<String> getBoostImport(HashMap<String, HashSet<String>> tokens) {
		ArrayList<String> importedTokens = new ArrayList<String>();
		for (LibBoost boost : libBoostRepo.findAll()) {
			String text = boost.getName();
			if (text.length() >= 5) {
				text = text.substring(0, text.length() - 1);
				if (tokens.containsKey("ImportDeclaration"))
					for (String txt : tokens.get("ImportDeclaration"))
						if (txt.toLowerCase().contains(text.toLowerCase()))
							importedTokens.add(txt);
			}
		}
		Set<String> set = new HashSet<String>();
		set.addAll(importedTokens);
		importedTokens.clear();
		importedTokens.addAll(set);
		return importedTokens;

	}

	private String getTokenBoosted(HashSet<String> tokens, String tokenType, HashMap<String, Double> entropies) {
		String query = "";
		for (String token : tokens) {
			if (token != null && (!token.isEmpty()) && entropies.containsKey(token))
				query += String.format(Locale.US, " %s:%s^%.2f", tokenType, token, entropies.get(token));

		}
		return query;

	}

	private String getVariableDeclarationTypeBoosted(Map.Entry<String, HashSet<String>> token,
			ArrayList<String> imports, HashMap<String, Double> entropies) {
		String query = "";
		String value = (String) token.getKey();
		if (token.getValue().contains("VariableDeclarationType")) {
			String imp = "";
			for (int i = 0; i < imports.size(); i++) {
				imp = imports.get(i);
				if ((imp != null && imp.contains(value)) || (imp != null && value.contains(imp)))
					if (entropies.get(value) != null)
						query = String.format("VariableDeclarationType:%s^%.2f", value, entropies.get(value));
			}
		}
		return query;
	}

	private String getTitle(ArrayList<String> vars, String query, boolean overallGuard) {
		if (overallGuard == true)
			for (String var : vars)
				if (var.equalsIgnoreCase("Java") != true)
					query += String.format(Locale.US, " Title:%s^%.2f OR", var, titleBoostValue);
		query = query.substring(0, query.length() - 3);
		return query;
	}

	private String getAnswer(ArrayList<String> vars, String query, boolean overallGuard) {
		if (overallGuard == true)
			for (String var : vars)
				if (var.equalsIgnoreCase("Java") != true)
					query += String.format(Locale.US, " Answer:%s^%.2f OR", var, answerBoostValue);
		query = query.substring(0, query.length() - 3);
		return query;
	}

	private String getQuestion(ArrayList<String> vars, String query, boolean overallGuard) {
		if (overallGuard == true)
			for (String var : vars)
				if (var.equalsIgnoreCase("Java") != true)
					query += String.format(Locale.US, " Question:%s^%.2f OR", var, questionBoostValue);
		query = query.substring(0, query.length() - 3);
		return query;
	}

	private HashMap<String, Double> getEntropy(String snippet, HashMap<String, HashSet<String>> tokens)
			throws IOException {
		HashMap<String, Double> entropies = new HashMap<String, Double>();
		// TODO check it!
		int tokenSize = tokens.values().stream().mapToInt(i -> i.size()).sum();
		logger.info(tokenSize + "");
		for (Entry<String, HashSet<String>> token : tokens.entrySet()) {
			HashSet<String> values = token.getValue();
			for (String coll : values)
				if (coll.length() > 1)
					entropies = getEntropy(coll, snippet, entropies, true, tokenSize);
		}
		return entropies;
	}

	private static HashMap<String, Double> getEntropy(String target, String snippet, HashMap<String, Double> entropies,
			Boolean normalization, int tokens) throws IOException {
		int tot = tokens;
		double np;
		double count = StringUtils.countMatches(snippet, target);
		double p = count / (double) tot;
		np = 1 - p;
		if (normalization == false) {
			double ent = -p * (Math.log(p) / Math.log(2)) - np * (Math.log(np) / Math.log(2));
			entropies.put(target, ent);
		} else {
			double ent = -p * (Math.log(p) / Math.log(2)) - np * (Math.log(np) / Math.log(2));
			if (ent > 0 && ent < 0.25)
				ent = 1.0;
			if (ent > 0.25 && ent < 0.4)
				ent = 2.0;
			if (ent > 0.4 && ent < 0.6)
				ent = 3.0;
			if (ent > 0.6 && ent < 0.75)
				ent = 4.0;
			if (ent > 0.75 && ent < 1.0)
				ent = 5.0;
			if (Double.isNaN(ent))
				ent = 1.0;
			entropies.put(target, ent);
		}
		return entropies;
	}
}

class MyASTVisitor extends ASTVisitor {
	private HashMap<String, HashSet<String>> tokens = new HashMap<String, HashSet<String>>();

	public boolean visit(final LineComment commentNode) {
		return false;
	}

	public boolean visit(ImportDeclaration node) {
		if (tokens.containsKey("ImportDeclaration")) {
			HashSet<String> i = tokens.get("ImportDeclaration");
			i.add(node.getName().toString());
			tokens.put("ImportDeclaration", i);
		} else {
			HashSet<String> i = new HashSet<String>();
			i.add(node.getName().toString());
			tokens.put("ImportDeclaration", i);
		}
		return true;
	}

	public boolean visit(MethodDeclaration node) {
		if (tokens.containsKey("MethodDeclaration")) {
			HashSet<String> i = tokens.get("MethodDeclaration");
			i.add(node.getName().toString());
			tokens.put("MethodDeclaration", i);
		} else {
			HashSet<String> i = new HashSet<String>();
			i.add(node.getName().toString());
			tokens.put("MethodDeclaration", i);
		}
		return true;
	}

	public boolean visit(MethodInvocation node) {
		if (tokens.containsKey("MethodInvocation")) {
			HashSet<String> i = tokens.get("MethodInvocation");
			i.add(node.getName().toString());
			tokens.put("MethodInvocation", i);
		} else {
			HashSet<String> i = new HashSet<String>();
			i.add(node.getName().toString());
			tokens.put("MethodInvocation", i);
		}
		return true;
	}

	public boolean visit(VariableDeclarationStatement node) {
		if (tokens.containsKey("VariableDeclarationType")) {
			HashSet<String> i = tokens.get("VariableDeclarationType");
			i.add(node.getType().toString());
			tokens.put("VariableDeclarationType", i);
		} else {
			HashSet<String> i = new HashSet<String>();
			i.add(node.getType().toString());
			tokens.put("VariableDeclarationType", i);
		}
		return true;
	}

	public boolean visit(VariableDeclarationFragment node) {
		if (tokens.containsKey("VariableDeclaration")) {
			HashSet<String> i = tokens.get("VariableDeclaration");
			i.add(node.getName().toString());
			tokens.put("VariableDeclaration", i);
		} else {
			HashSet<String> i = new HashSet<String>();
			i.add(node.getName().toString());
			tokens.put("VariableDeclaration", i);
		}
		return true;
	}

	public boolean visit(ClassInstanceCreation node) {

		if (tokens.containsKey("VariableDeclarationType")) {
			HashSet<String> i = tokens.get("VariableDeclarationType");
			i.add(node.getType().toString());
			tokens.put("VariableDeclarationType", i);
		} else {
			HashSet<String> i = new HashSet<String>();
			i.add(node.getType().toString());
			tokens.put("VariableDeclarationType", i);
		}
		return true;
	}

	public HashMap<String, HashSet<String>> getTokens() {
		return tokens;
	}

	public void setTokens(HashMap<String, HashSet<String>> tokens) {
		this.tokens = tokens;
	}
}