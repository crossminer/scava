package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

public enum MDE {

	ATL("atl", "rule"),  // ~4.5k results
	QVTo("qvto", "transformation"), // ~5.7k results
	Emfatic("emf", "class" ),  // ~1.5k results
	Acceleo(" mtl", "template"), // ~20.5k results
	EGL("egl", "var"),  // ~3.5k results
	IncQuery("eiq", "pattern"), // ~343 results
	Eugenia("ecore", "gmf"), // ~1.8k repos //Eugenia("ecore", "\"gmf.diagram\""),
	GMF("gmfgraph", "figure"), // ~1.7k results
	EOL("eol", "var"),  // ~1.6k results
	Xtext("xtext", "grammar"), // ~42k results
	ETL("etl", "transform"), // ~1.4k results
	Ecore ("ecore", "EClass"), // ~91.2k results
	EVL("evl", "context"), // ~1.3k results
	OCL("ocl", "context"), // ~6.8k results
	Sirius("odesign", "node"), // ~2.4k results
	Henshin("henshin", "rule"), // ~26k results
	MOFScript("m2t", "texttransformation"), // ~107 results
	Kermeta("kmt", "class"), // ~764 results
	Xcore("xcore", "class"), // ~758 results
	JET ("javajet", "jet"), // ~9.7k results
	EMFText("cs", "syntaxdef"), // ~465 results
	Xpand ("xpt", "for"); // ~16.4k results
	
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
	
	/**
	 *  Will return the code snippets for that particular keyword and extension
	 * @param extension
	 * @param keyword
	 * @return String query to be supplied to provider API
	 */
	public static String query(String extension, String keyword){
		return new CodeSearchQuery()
				.create(keyword)
				.extension(extension)
				//.inFile()
				.build()
				.getQuery();
	}
	
	public static void main(String args[]) {
		System.out.println(MDE.query("kmt", "class"));
	}
	
	
}