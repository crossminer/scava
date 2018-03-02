/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.demoserver;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.scava.commons.buildsystem.BuildSystemType;
import org.eclipse.scava.commons.context.sourcecode.SourceCodeContext;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.LineInfo;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetail;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailMethod;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailTypeName;
import org.eclipse.scava.commons.json.Json;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.commons.library.ReleaseType;
import org.eclipse.scava.commons.library.Version;
import org.eclipse.scava.commons.libraryapi.LibraryAPIElement;
import org.eclipse.scava.commons.libraryapi.LibraryAPIMethodSignature;
import org.eclipse.scava.commons.libraryapi.LibraryAPISimpleName;
import org.eclipse.scava.commons.recommendation.Recommendation;
import org.eclipse.scava.commons.recommendation.RecommendationSet;
import org.eclipse.scava.commons.recommendation.library.AddSimpleLibraryRecommendation;
import org.eclipse.scava.commons.recommendation.library.RemoveLibraryRecommendation;
import org.eclipse.scava.commons.recommendation.source.SourceReplaceRecommendation;
import org.eclipse.scava.commons.transaction.APIChanges;
import org.eclipse.scava.commons.transaction.APIUsageInContext;
import org.eclipse.scava.commons.transaction.Description;
import org.eclipse.scava.commons.transaction.Libraries;
import org.eclipse.scava.commons.transaction.ParsedSourceCodeContext;
import org.eclipse.scava.commons.transaction.Recommendations;
import org.eclipse.scava.commons.transaction.UpdatableAPIUsage;
/**
 * This class is for testing purpose only!
 * The final plugin will not contain this class.
 * 
 *
 */
public class DemoServer {
	public static String receive(String address, String json) {
		String[] addressParts = address.split("/");
		
		switch (addressParts[addressParts.length - 1]) {
			// "/api/get/library/CrossMinerExample/1.0.0.0/description"
			case "description":
				return getDescription(addressParts[4].replaceAll("\\+","%20"), addressParts[5]);
			
			// "/api/get/library/CrossMinerExample/1.0.0.0/alternatives"
			case "alternatives":
				System.out.println("\n\n\n"+addressParts[4]+"\n\n\n");
				return getAlternatives(addressParts[4].replaceAll("\\+","%20"), addressParts[5]);
			
			// "/api/get/library/CrossMinerExample/1.0.0.0/2.0.0.0/apichanges"
			case "apichanges":
				return getAPIChanges(addressParts[4].replaceAll("\\+","%20"), addressParts[5], addressParts[6]);
			
			// "/api/get/source/updateapiusage"
			case "updateapiusage":
				return getRecommendationsToUpdateAPIUsage(json);
			
			// "/api/get/source/generalimprovements"
			case "generalimprovements":
				return getGeneralImprovements(json);
			
			default:
				return "Error: Unexpected request.";
		}
	}
	
	public static String getRecommendationsToUpdateAPIUsage(String json) {
		UpdatableAPIUsage updatableAPIUsage = Json.fromJson(json, UpdatableAPIUsage.class);
		
		List<Recommendation> recommendationList = new LinkedList<>();
		
		
		
		
		
		if( updatableAPIUsage.getUsedLibrary().getId().equals("Caroline DB Access") )
		{
			//Library recommendations
			recommendationList.add(new RemoveLibraryRecommendation("remove-old-library", new Date(),
					BuildSystemType.NONE, updatableAPIUsage.getUsedLibrary()));
			recommendationList.add(new AddSimpleLibraryRecommendation("add-new-library", new Date(),
					updatableAPIUsage.getTargetLibrary(), "http://localhost:8080/files/balloon.jar"));
			
			
			//Source recommendations
			String file = "src/Demo.java";
			recommendationList.add( new SourceReplaceRecommendation("import-fix", new Date(), file,
					7, 7+24-1, "balloon.DataFetcher"));
			recommendationList.add( new SourceReplaceRecommendation("import-fix", new Date(), file,
					41, 41+19-1, "balloon.FetchedData"));
			recommendationList.add( new SourceReplaceRecommendation("import-fix", new Date(), file,
					70, 70+17-1, "balloon.RequestMethod"));
			recommendationList.add( new SourceReplaceRecommendation("import-fix", new Date(), file,
					90, 90+0-1, "import balloon.URLFetcher;\n"));
			recommendationList.add( new SourceReplaceRecommendation("AccessManager-to-DataFetcher", new Date(), file,
					269, 269+13-1, "DataFetcher"));
			recommendationList.add( new SourceReplaceRecommendation("DBAccess-to-URLFetcher", new Date(), file,
					296, 296+10-1, "URLFetcher(ACCESS_URL, RequestMethod.TCP)"));
			recommendationList.add( new SourceReplaceRecommendation("remove-AccessManager.Connect", new Date(), file,
					311, 311+27-1, ""));
			recommendationList.add( new SourceReplaceRecommendation("DBInfo-to-FetchedData", new Date(), file,
					360, 360+6-1, "FetchedData"));
			recommendationList.add( new SourceReplaceRecommendation("AccessManager.readData-to-DataFetcher.fetch", new Date(), file,
					384, 384+8-1, "fetch"));
			recommendationList.add( new SourceReplaceRecommendation("DBInfo.asArray-to-FetchedData.toArray", new Date(), file,
					420, 420+7-1, "toArray"));
		}
		
		RecommendationSet recommendationSet = new RecommendationSet(recommendationList);
		Recommendations recommendations = new Recommendations(recommendationSet);
		return Json.toJson(recommendations);
	}
	
	public static String getAPIChanges(String id, String oldVersion, String newVersion) {
		
		List<LibraryAPIElement> changes = new LinkedList<>();
		
		if (id.equals("Balloon Data Fetcher")) {
			changes.add(new LibraryAPIMethodSignature("carolinedb.DBAccess.DBAccess()V"));
			changes.add(new LibraryAPIMethodSignature("carolinedb.AccessManager.connect(Qjava.lang.String;)V"));
			changes.add(new LibraryAPIMethodSignature("carolinedb.AccessManager.readData()Qcarolinedb.DBInfo;"));
			changes.add(new LibraryAPIMethodSignature("carolinedb.DBInfo.asArray()[I"));
		}
		
		APIChanges apiChanges = new APIChanges(changes);
		return Json.toJson(apiChanges);
	}
	
	public static String getAlternatives(String id, String version) {
		List<Library> libraries = new LinkedList<>();
		
		if (id.equals("Caroline DB Access")) {
			libraries.add(new Library("Balloon Data Fetcher", new Version("1.2.3.0"), ReleaseType.STABLE,
					"www.balloondb.org"));
			libraries.add(new Library("Spring DataBase", new Version("3.2.3.0"), ReleaseType.STABLE,
					"www.balloondb.org"));
			libraries.add(new Library("CrossDataMiner", new Version("2.4.3.0"), ReleaseType.STABLE,
					"www.balloondb.org"));
			libraries.add(new Library("OpenConn", new Version("3.5.4.0"), ReleaseType.STABLE,
					"www.balloondb.org"));
			libraries.add(new Library("SQLite", new Version("1.1.1.1"), ReleaseType.STABLE,
					"www.balloondb.org"));
			libraries.add(new Library("HotWire", new Version("6.2.3.0"), ReleaseType.STABLE,
					"www.balloondb.org"));
		}
		
		if (id.equals("Thunder Data")) {
			libraries.add(new Library("Zeppelin Data Fetcher", new Version("1.2.3.0"), ReleaseType.STABLE,
					"www.balloondb.org"));
			libraries.add(new Library("Data Doctor", new Version("5.2.2.0"), ReleaseType.STABLE,
					"www.balloondb.org"));
			libraries.add(new Library("Data O' Mine", new Version("6.2.2.0"), ReleaseType.STABLE,
					"www.balloondb.org"));
			libraries.add(new Library("Star Data", new Version("6.3.1.0"), ReleaseType.STABLE,
					"www.balloondb.org"));
			libraries.add(new Library("Cloud Data", new Version("1.2.6.0"), ReleaseType.STABLE,
					"www.balloondb.org"));
			libraries.add(new Library("Syncronized", new Version("6.1.1.1"), ReleaseType.STABLE,
					"www.balloondb.org"));
			
		}
		
		Libraries alternatives = new Libraries(libraries);
		return Json.toJson(alternatives);
	}
	
	public static String getDescription(String id, String version) {
		Description description = new Description("This is the description of " + id + " v" + version + " library.");
		
		if (id.equals("Caroline DB Access")) {
			description = new Description(
					"Caroline DB Access is a widely used database connection manager. It provides an easy to use interface for every developer\nfrom the beginners to the advanced professionals.\nFor more information, please visit www.carolinedba.com");
		}
		
		if (id.equals("Balloon Data Fetcher")) {
			description = new Description(
					"Balloon Data Fetcher is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("SQL Bean")) {
			description = new Description(
					"SQL Bean is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("Spring DataBase")) {
			description = new Description(
					"Spring DataBase Fetcher is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("CrossDataMiner")) {
			description = new Description(
					"CrossDataMiner Fetcher is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("OpenConn")) {
			description = new Description(
					"OpenConn is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("SQLite")) {
			description = new Description(
					"SQLite is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("HotWire")) {
			description = new Description(
					"HotWire is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("Zeppelin Data Fetcher")) {
			description = new Description(
					"Zeppelin Data Fetcher is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("Data Doctor")) {
			description = new Description(
					"Data Doctor is a brand new library for accessing data via the web nIt is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("Data O' Mine")) {
			description = new Description(
					"Data O' Mine is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("Star Data")) {
			description = new Description(
					"Star Data is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("Cloud Data")) {
			description = new Description(
					"Cloud Data is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}

		if (id.equals("Syncronized")) {
			description = new Description(
					"Syncronized is a brand new library for accessing data via the web. It is one of the most powerful tools currently available for the average user.\nFor more information, please visit www.balloondb.org");
		}
		
		return Json.toJson(description);
	}
	
	public static String getGeneralImprovements(String json) {
		
		ParsedSourceCodeContext parsedContext = Json.fromJson(json, ParsedSourceCodeContext.class);
		
		List<Recommendation> recommendationList = new LinkedList<>();
		
		
		if( parsedContext.getContext().getTargetFile().equals("src/Sorter.java") )
		{
			recommendationList.add(new SourceReplaceRecommendation("sort-algorithm", new Date(), parsedContext.getContext().getTargetFile(),
					73, 73+198-1, 
					"for (int i = 1; i < input.length; i++) {\n"+
					"			for (int j = i; j > 0; j--) {\n"+
					"				if (input[j] < input[j - 1]) {\n"+
					"					swapNumbers(j, j - 1, input);\n"+
					"				}\n"+
					"			}\n"+
					"		}"
					
					));
		}
		
		RecommendationSet recommendationSet = new RecommendationSet(recommendationList);
		Recommendations recommendations = new Recommendations(recommendationSet);
		return Json.toJson(recommendations);
	}
}
