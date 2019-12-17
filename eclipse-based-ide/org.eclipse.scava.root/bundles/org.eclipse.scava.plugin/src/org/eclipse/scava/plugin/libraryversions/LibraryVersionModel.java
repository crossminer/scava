/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.eclipse.scava.plugin.properties.Properties;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.swagger.client.ApiException;
import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;
import io.swagger.client.model.RecommendationItem;
import io.swagger.client.model.RecommendedLibrary;

public class LibraryVersionModel extends Model {

	private final KnowledgeBaseAccess knowledgeBaseAccess;
	private final SimpleDateFormat recommendedLibraryReleaseDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

	public LibraryVersionModel() {
		knowledgeBaseAccess = new KnowledgeBaseAccess();
	}

	public Collection<Library> getUsedLibrariesFromPom(String pomLocation)
			throws FileNotFoundException, IOException, XmlPullParserException {
		MavenXpp3Reader reader = new MavenXpp3Reader();
		org.apache.maven.model.Model model = reader.read(new FileReader(pomLocation));
		List<Dependency> dependencies = model.getDependencies();

		Collection<Library> libraries = dependencies.stream().map(dependency -> {
			String groupId = Optional.ofNullable(dependency.getGroupId()).orElse("");
			String artifactId = Optional.ofNullable(dependency.getArtifactId()).orElse("");
			String version;
			if (dependency.getVersion().matches("\\$\\{[^\\}]+\\}")) {
				version = model.getProperties()
						.getProperty(dependency.getVersion().substring(2, dependency.getVersion().length() - 1), "");
			} else {
				version = Optional.ofNullable(dependency.getVersion()).orElse("");
			}
			return new Library(groupId, artifactId, version);
		}).collect(Collectors.toList());

		return libraries;
	}

	public Map<Library, List<Library>> getAvailableVersionsOfLibraries(Collection<Library> referenceLibraries,
			Collection<LibraryFilterRule> rules) throws ApiException {

		Query query = new Query();
		query.setProjectDependencies(
				referenceLibraries.stream().map(this::mapLibraryToDependency).collect(Collectors.toList()));

		RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess
				.getRecommenderRestController(Preferences.TIMEOUT_APIMIGRATION_LIBRARY_SEARCH);
		Recommendation results = recommenderRestController.getVersionsUsingPOST(query);

		List<RecommendationItem> recommendationItems = results.getRecommendationItems();

		Stream<Library> recommendedLibraries = recommendationItems.stream()
				.map(RecommendationItem::getRecommendedLibrary).map(this::mapRecommendedLibraryToLibrary);

		Map<Library, List<Library>> groupedLibraries = recommendedLibraries
				.collect(Collectors.groupingBy(library -> groupByGroupIdAndArtifactId(referenceLibraries, library)));

		groupedLibraries.entrySet().forEach(referenceLibRecommendedLibs -> {
			Library reference = referenceLibRecommendedLibs.getKey();
			Optional<Library> referenceAmongRecommended = referenceLibRecommendedLibs.getValue().stream()
					.filter(recommended -> reference.equals(recommended)).findFirst();
			if (referenceAmongRecommended.isPresent()) {
				reference.setReleaseDate(referenceAmongRecommended.get().getReleaseDate());
			} else {
				reference.setReleaseDate(null);
			}
		});

		Map<Library, List<Library>> filteredRecommendedLibraries = new HashMap<>();
		groupedLibraries.entrySet().forEach(referenceLibRecommendedLibs -> {
			Library reference = referenceLibRecommendedLibs.getKey();
			Stream<Library> libraries = referenceLibRecommendedLibs.getValue().stream();
			if (rules != null && !rules.isEmpty()) {
				libraries = libraries.filter(library -> rules.stream().anyMatch(rule -> rule.match(library)));
			}
			if (reference.getReleaseDate() != null) {
				libraries = libraries.filter(lib -> reference.getReleaseDate().before(lib.getReleaseDate()));
			}
			List<Library> asList = libraries.collect(Collectors.toList());
			if (!asList.isEmpty()) {
				filteredRecommendedLibraries.put(reference, asList);
			}
		});

		return filteredRecommendedLibraries;

	}

	private io.swagger.client.model.Dependency mapLibraryToDependency(Library lib) {
		io.swagger.client.model.Dependency dependency = new io.swagger.client.model.Dependency();
		dependency.setArtifactID(lib.getArtifactId());
		dependency.setGroupID(lib.getGroupId());
		return dependency;
	}

	private Library groupByGroupIdAndArtifactId(Collection<Library> referenceLibraries, Library library) {
		return referenceLibraries.stream().filter(reference -> reference.getGroupId().equals(library.getGroupId())
				&& reference.getArtifactId().equals(library.getArtifactId())).findFirst().orElse(library);
	}

	private Library mapRecommendedLibraryToLibrary(RecommendedLibrary recommendedLib) {
		String libraryName = recommendedLib.getLibraryName();
		String[] libraryNameParts = libraryName.split(":");
		String releaseDate = recommendedLib.getReleaseDate();
		try {
			return new Library(libraryNameParts[0], libraryNameParts[1], libraryNameParts[2],
					recommendedLibraryReleaseDateFormat.parse(releaseDate));
		} catch (Exception e) {
			return new Library(libraryNameParts[0], libraryNameParts[1], libraryNameParts[2]);
		}
	}

	public List<LibraryFilterRule> getRules(IResource resource) throws CoreException {
		String json = resource.getPersistentProperty(Properties.LIBRARY_UPDATE_VERSION_RULES);
		Type listType = new TypeToken<ArrayList<LibraryFilterRule>>() {
		}.getType();
		List<LibraryFilterRule> loadedRules = new Gson().fromJson(json, listType);
		return loadedRules;
	}
}
