package org.eclipse.scava.business.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
	@Value("${sorecommender.answerBoostValue}")
	private double answerBoostValue;
	@Value("${sorecommender.questionBoostValue}")
	private double questionBoostValue;

	@Value("${sorecommender.importerBoostLibrariesPath}")
	private String importerBoostValue;
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
	public Recommendation getRecommendation(Query query) throws Exception {
		String compUnit = "";
		if(query.getSoRecommendationSelection() == null || query.getSoRecommendationSelection().isEmpty()) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("ImportDeclaration", "AND");
			param.put("MethodDeclaration", "AND");
			param.put("MethodInvocation", "AND");
			param.put("VariableDeclaration", "AND");
			param.put("ClassInstance", "AND");
			param.put("VariableDeclarationType", "AND");
			compUnit = makeBoostedQuery(query.getCompilationUnit(), param);
		}
		else 
			compUnit = makeBoostedQuery(query.getCompilationUnit(), query.getSoRecommendationSelection());
		
		return null;
	}

	public String makeBoostedQuery(String snippet, Map<String,String> param) throws IOException {

		ArrayList<HashMap<String, String>> tokens = extractTokensFromCode(snippet);
		tokens = cleanDuplicates(tokens);
		HashMap<String, Double> entropies = getEntropy(snippet, tokens);
		ArrayList<String> imports = getImportList(tokens);
		String result = makeBoostedQuery(tokens, imports, entropies, param);
		return result;

	}
	public String makeBoostedQuery(String snippet) throws IOException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ImportDeclaration", "AND");
		param.put("MethodDeclaration", "AND");
		param.put("MethodInvocation", "AND");
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
	
	
	private String makeBoostedQuery(ArrayList<HashMap<String, String>> tokens, ArrayList<String> imports,
			HashMap<String, Double> entropies, Map<String, String> parameter) throws IOException {
		String query = "";
		ArrayList<String> varDeclTypeFix = getImportList(tokens);
		for (HashMap<String, String> token : tokens) {
			if(parameter.containsKey("ImportDeclaration")) {
				if (!query.isEmpty())
					query = query + " ";
				query = query + getTokenBoosted(token, "ImportDeclaration", entropies);
			}
			if(parameter.containsKey("MethodDeclaration")) {	
				if (!query.isEmpty())
					query = query + " ";
				query = query + getTokenBoosted(token, "MethodDeclaration", entropies);
			}
			if(parameter.containsKey("MethodInvocation")) {
				if (!query.isEmpty())
					query = query + " ";
				query = query + getTokenBoosted(token, "MethodInvocation", entropies);
			}
			if(parameter.containsKey("VariableDeclaration")) {
				if (!query.isEmpty())
					query = query + " ";
				query = query + getTokenBoosted(token, "VariableDeclaration", entropies);
			}
			if(parameter.containsKey("ClassInstance")) {
				if (!query.isEmpty())
					query = query + " ";
				query = query + getTokenBoosted(token, "ClassInstance", entropies);
			}
			if(parameter.containsKey("VariableDeclarationType")) {
				if (!query.isEmpty())
					query = query + " ";
				query = query + getVariableDeclarationTypeBoosted(token, varDeclTypeFix, entropies);
			}
		}
		ArrayList<String> vars = getBoostImport(tokens);
		query = getAnswer(vars, query, true);
		query = getQuestion(vars, query, true);
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
			File source= resource.getFile(); 
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
			query += String.format(" %s: %s^%f", tokenType, value, entropies.get(token.get(tokenType)));
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
						query = String.format("VariableDeclarationType: %s^%f", value,
								entropies.get(token.get("VariableDeclarationType")));
			}
		}
		return query;
	}

	private String getTitle(ArrayList<String> vars, String query, boolean overallGuard) {
		if (overallGuard == true)
			for (String var : vars)
				if (var.equalsIgnoreCase("Java") != true)
					query += String.format(" Title: %s^%f", var, titleBoostValue);
		return query;
	}

	private String getAnswer(ArrayList<String> vars, String query, boolean overallGuard) {
		if (overallGuard == true)
			for (String var : vars)
				if (var.equalsIgnoreCase("Java") != true)
					query += String.format(" Answer: %s^%f", var, answerBoostValue);
		return query;
	}

	private String getQuestion(ArrayList<String> vars, String query, boolean overallGuard) {
		if (overallGuard == true)
			for (String var : vars)
				if (var.equalsIgnoreCase("Java") != true)
					query += String.format(" Question: %s^%f", var, questionBoostValue);
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
