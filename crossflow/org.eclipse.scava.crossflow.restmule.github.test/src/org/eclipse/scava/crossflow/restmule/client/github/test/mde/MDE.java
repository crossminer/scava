package org.eclipse.scava.crossflow.restmule.client.github.test.mde;

import org.eclipse.scava.crossflow.restmule.client.github.test.query.CodeSearchQuery;

public enum MDE {

	ATL("atl", "rule"), 
	QVTo("qvto", "transformation"),
	Emfatic("emf", "class" ), 
	Acceleo(" mtl", "template"),
	EGL("egl", "var"), 
	IncQuery("eiq", "pattern"),
	Eugenia("ecore", "\"gmf.diagram\""),
	GMF("gmfgraph", "figure"),
	EOL("eol", "var"), 
	Xtext("xtext", "grammar"),
	ETL("etl", "transform"), 
	Ecore ("ecore", "EClass"),
	EVL("evl", "context"), 
	OCL("ocl", "context"),
	Sirius("odesign", "node"),
	Henshin("henshin", "rule"),
	MOFScript("m2t", "texttransformation"), 
	Kermeta("kmt", "class"),
	Xcore("xcore", "class"), 
	JET ("javajet", "jet"),
	EMFText("cs", "syntaxdef"), 
	Xpand ("for", "xpt");
	
	private String extension;
	private String keyword;

	MDE(String extension, String keyword) {
		this.extension = extension;
		this.keyword = keyword;
	}

	/** Getters */
	
	public String getExtension() {
		return extension;
	}

	public String getKeyword() {
		return keyword;
	}

	/** Special Queries */
	
	/**
	 *  Will return all the files that match the extension and the keyword, 
	 *  but will sometimes be repeated or the same repo
	 * @return
	 */
	public String query(){
		return new CodeSearchQuery()
				.create(keyword)
				.extension(extension)
				.inFile()
				.build()
				.getQuery();
	}
	
	/**
	 *  Will return the code snippets for that particular repository
	 * @param repository
	 * @return
	 */
	public String query(String repository){
		return new CodeSearchQuery()
				.create(keyword)
				.extension(extension)
				.inFile()
				.repo(repository)
				.build()
				.getQuery();
	}
	
		
}
