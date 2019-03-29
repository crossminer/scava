package org.eclipse.scava.business.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Qualifier("SO")
public class SORecommender implements IRecommendationProvider {

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
	
	@Value("${sorecommender.importerBoostLibrariesPath}")
	private String importerBoostValue;
	@Value("${sorecommender.INDEX_DIRECTORY}")
	private String INDEX_DIRECTORY;
	private static final Logger logger = LoggerFactory.getLogger(SORecommender.class);

	private ArrayList<HashMap<String, String>> extractTokensFromCode(String soSnippet) throws IOException {
		ArrayList<HashMap<String, String>> postTokens = new ArrayList<HashMap<String, String>>();
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

	private ArrayList<HashMap<String, String>> parseKCUnit(String snippet) throws IOException {
		final ArrayList<HashMap<String, String>> tokens = new ArrayList<HashMap<String, String>>();
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
			cu.accept(new ASTVisitor() {
				public boolean visit(final LineComment commentNode) {
					return false;
				}

				public boolean visit(ImportDeclaration node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("ImportDeclaration", node.getName().toString());
					tokens.add(token);
					return true;
				}

				public boolean visit(MethodDeclaration node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("MethodDeclaration", node.getName().toString());
					tokens.add(token);
					return true;
				}

				public boolean visit(MethodInvocation node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("MethodInvocation", node.getName().toString());
					tokens.add(token);
					return true;
				}

				public boolean visit(VariableDeclarationStatement node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("VariableDeclarationType", node.getType().toString());
					tokens.add(token);
					return true;
				}

				public boolean visit(VariableDeclarationFragment node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("VariableDeclaration", node.getName().toString());
					tokens.add(token);
					return true;
				}

				public boolean visit(ClassInstanceCreation node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("ClassInstance", node.getType().toString());
					tokens.add(token);
					return true;
				}
			});
		} catch (Exception exc) {
			System.out.println("JDT parsing error");
		}
		return tokens;
	}

	private ArrayList<HashMap<String, String>> parseKStatements(String snippet) throws IOException {
		final ArrayList<HashMap<String, String>> tokens = new ArrayList<HashMap<String, String>>();
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

			final Block block = (Block) parser.createAST(null);

			block.accept(new ASTVisitor() {
				public boolean visit(final LineComment commentNode) {

					return false;
				}

				public boolean visit(ImportDeclaration node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("ImportDeclaration", node.getName().toString());
					tokens.add(token);
					return true;
				}

				public boolean visit(MethodDeclaration node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("MethodDeclaration", node.getName().toString());
					tokens.add(token);
					return true;
				}

				public boolean visit(MethodInvocation node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("MethodInvocation", node.getName().toString());
					tokens.add(token);
					return true;
				}

				public boolean visit(VariableDeclarationStatement node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("VariableDeclarationType", node.getType().toString());
					tokens.add(token);
					return true;
				}

				public boolean visit(VariableDeclarationFragment node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("VariableDeclaration", node.getName().toString());
					tokens.add(token);
					return true;
				}

				public boolean visit(ClassInstanceCreation node) {
					final HashMap<String, String> token = new HashMap<String, String>();
					token.put("ClassInstance", node.getType().toString());
					tokens.add(token);
					return true;
				}

			});
		}

		catch (Exception exc) {
			System.out.println("JDT parsing error");
		}

		return tokens;

	}

	private ArrayList<HashMap<String, String>> cleanDuplicates(ArrayList<HashMap<String, String>> tokens) {
		Set<HashMap<String, String>> set = new HashSet<HashMap<String, String>>();
		set.addAll(tokens);
		tokens.clear();
		tokens.addAll(set);
		return tokens;
	}

	private String classBugFixer(String snippet) {
		String fix = "public class fix{ ";
		fix += snippet + "}";
		return fix;
	}

	@Override
	public Recommendation getRecommendation(Query rec_query)  {
		Recommendation rec = new Recommendation();
		try {
			
		
		String compUnit = "";
		if (rec_query.getSoRecommendationSelection() == null || rec_query.getSoRecommendationSelection().isEmpty()) {
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
		logger.info(compUnit);
		File indexDirectory = new File(INDEX_DIRECTORY);
		Directory indexDir = FSDirectory.open(Paths.get(indexDirectory.getAbsolutePath()));
		IndexReader reader = DirectoryReader.open(indexDir);
    	IndexSearcher searcher = new IndexSearcher(reader);
    	List<String> fields2 = getAllIndexTags(INDEX_DIRECTORY);
		String[] fields = new String[fields2.size()];
		int i = 0;
		for (String string : fields2) 
		{
			fields[i] = string;
			i++;
		}
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
		}catch (IOException | ParseException e) {
			logger.error(e.getMessage());
		}
		return rec;
	}

	public String makeBoostedQuery(String snippet, Map<String, String> param) throws IOException {

		ArrayList<HashMap<String, String>> tokens = extractTokensFromCode(snippet);
		tokens = cleanDuplicates(tokens);
		HashMap<String, Double> entropies = getEntropy(snippet, tokens);
		ArrayList<String> imports = getImportList(tokens);
		String result = makeBoostedQuery(tokens, imports, entropies, param);
		return result;

	}

	public String makeBoostedQuery(String snippet) throws IOException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ImportDeclaration", "OR");
		param.put("MethodDeclaration", "OR");
		param.put("MethodInvocation", "OR");
		param.put("VariableDeclaration", "AND");
		param.put("ClassInstance", "AND");
		param.put("VariableDeclarationType", "AND");
		ArrayList<HashMap<String, String>> tokens = extractTokensFromCode(snippet);
		tokens = cleanDuplicates(tokens);
		HashMap<String, Double> entropies = getEntropy(snippet, tokens);
		ArrayList<String> imports = getImportList(tokens);
		String result = makeBoostedQuery(tokens, imports, entropies, param);
		return result;

	}

	private List<String> getAllIndexTags(String INDEX_DIRECTORY) {
		Collection<String> result = new HashSet<String>();
		try {
			IndexReader luceneIndexReader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
			result = MultiFields.getIndexedFields(luceneIndexReader);
		} catch (IOException e) {
			System.out.println();
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

	private String makeBoostedQuery(ArrayList<HashMap<String, String>> tokens, ArrayList<String> imports,
			HashMap<String, Double> entropies, Map<String, String> parameter) throws IOException {
		String query = "";
		ArrayList<String> varDeclTypeFix = getImportList(tokens);
		for (HashMap<String, String> token : tokens) {
			if (parameter.containsKey("ImportDeclaration")) {
				String value = getTokenBoosted(token, "ImportDeclaration", entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR ";
				query = query + getTokenBoosted(token, "ImportDeclaration", entropies);
			}
			if (parameter.containsKey("MethodDeclaration")) {
				String value = getTokenBoosted(token, "MethodDeclaration", entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR ";
				query = query + getTokenBoosted(token, "MethodDeclaration", entropies);
			}
			if (parameter.containsKey("MethodInvocation")) {
				String value = getTokenBoosted(token, "MethodInvocation", entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR ";
				query = query + value;
			}
			if (parameter.containsKey("VariableDeclaration")) {
				String value = getTokenBoosted(token, "VariableDeclaration", entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR ";
				query = query + value;
			}
			if (parameter.containsKey("ClassInstance")) {
				String value = getTokenBoosted(token, "ClassInstance", entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR ";
				query = query + value;
			}
			if (parameter.containsKey("VariableDeclarationType")) {
				String value = getVariableDeclarationTypeBoosted(token, varDeclTypeFix, entropies);
				if (!query.isEmpty() && !value.isEmpty())
					query = query + " OR";
				query = query + value;
			}
		}
		query = query.substring(0,query.length()  -3);
		ArrayList<String> vars = getBoostImport(tokens);
		query = query.isEmpty()?"":query + " OR ";
		query = getAnswer(vars, query, true);
		query = query.isEmpty()?"":query + " OR ";
		query = getQuestion(vars, query, true);
		query = query.isEmpty()?"":query + " OR ";
		query = getTitle(vars, query, true);
		return query;
	}

	private ArrayList<String> getImportList(ArrayList<HashMap<String, String>> tokens) {
		/*
		 * vardecltype fix
		 */
		ArrayList<String> imports = new ArrayList<String>();
		for (HashMap<String, String> token : tokens) {
			if (token.get("ImportDeclaration") != null) {
				String imp = (String) token.get("ImportDeclaration");
				imp = imp.substring(imp.lastIndexOf(".") + 1);
				imports.add(imp);
			}
		}

		return imports;
	}

	private ArrayList<String> getBoostImport(ArrayList<HashMap<String, String>> tokens) {
		Resource resource = new ClassPathResource(importerBoostValue);
		ArrayList<String> importedTokens = new ArrayList<String>();
		try {
			File source = resource.getFile();
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(source));
			String text = null;

			while ((text = reader.readLine()) != null)
				if (text.length() >= 5) {
					text = text.substring(0, text.length() - 1);
					for (HashMap<String, String> token : tokens) {
						String t = token.get("ImportDeclaration");
						if (t != null && t.toLowerCase().contains(text.toLowerCase()))
							importedTokens.add(text);
					}
				}

			Set<String> set = new HashSet<String>();
			set.addAll(importedTokens);
			importedTokens.clear();
			importedTokens.addAll(set);
			reader.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return importedTokens;

	}

	private String getTokenBoosted(HashMap<String, String> token, String tokenType, HashMap<String, Double> entropies) {
		String query = "";
		String value = (String) token.get(tokenType);
		if (value != null && entropies.get(token.get(tokenType)) != null)
			query += String.format(Locale.US, " %s:%s^%.2f", tokenType, value, entropies.get(token.get(tokenType)));
		return query;

	}

	private String getVariableDeclarationTypeBoosted(HashMap<String, String> token, ArrayList<String> imports,
			HashMap<String, Double> entropies) {
		String query = "";
		String value = (String) token.get("VariableDeclarationType");

		if (value != null) {
			String imp = "";
			for (int i = 0; i < imports.size(); i++) {
				imp = imports.get(i);
				if ((imp != null && imp.contains(value)) || (imp != null && value.contains(imp)))
					if (entropies.get(token.get("VariableDeclarationType")) != null)
						query = String.format("VariableDeclarationType:%s^%.2f", value,
								entropies.get(token.get("VariableDeclarationType")));
			}
		}
		return query;
	}

	private String getTitle(ArrayList<String> vars, String query, boolean overallGuard) {
		if (overallGuard == true)
			for (String var : vars)
				if (var.equalsIgnoreCase("Java") != true)
					query += String.format(Locale.US, " Title:%s^%.2f OR", var, titleBoostValue);
		query = query.substring(0,query.length()-3);
		return query;
	}

	private String getAnswer(ArrayList<String> vars, String query, boolean overallGuard) {
		if (overallGuard == true)
			for (String var : vars)
				if (var.equalsIgnoreCase("Java") != true)
					query += String.format(Locale.US, " Answer:%s^%.2f OR", var, answerBoostValue);
		query = query.substring(0,query.length()-3);
		return query;
	}

	private String getQuestion(ArrayList<String> vars, String query, boolean overallGuard) {
		if (overallGuard == true)
			for (String var : vars)
				if (var.equalsIgnoreCase("Java") != true)
					query += String.format(Locale.US, " Question:%s^%.2f OR", var, questionBoostValue);
		query = query.substring(0,query.length()-3);
		return query;
	}

	private HashMap<String, Double> getEntropy(String snippet, ArrayList<HashMap<String, String>> tokens)
			throws IOException {
		HashMap<String, Double> entropies = new HashMap<String, Double>();
		for (HashMap<String, String> token : tokens) {
			Collection<String> coll = token.values();
			if (coll.toArray()[0].toString().length() > 1)
				entropies = getEntropy(coll.toArray()[0].toString(), snippet, entropies, true, tokens.size());
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
