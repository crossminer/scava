/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.librarysuggestion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.scava.plugin.feedback.FeedbackResource;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.librarysuggestion.library.Library;
import org.eclipse.scava.plugin.mvc.model.Model;
import org.eclipse.scava.plugin.preferences.Preferences;

import com.squareup.okhttp.Call;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiException;
import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;
import io.swagger.client.model.RecommendationItem;
import io.swagger.client.model.RecommendedLibrary;

public class LibrarySuggestionModel extends Model {
	private final String pomLocation;
	private final KnowledgeBaseAccess knowledgeBaseAccess;

	private final Collection<Library> usedLibraries = new HashSet<>();
	private final Collection<Library> baseLibrariesForSearch = new HashSet<>();
	private final Collection<Library> pickedLibraries = new HashSet<>();

	public LibrarySuggestionModel(String pomLocation, KnowledgeBaseAccess knowledgeBaseAccess) {
		super();
		this.pomLocation = pomLocation;
		this.knowledgeBaseAccess = knowledgeBaseAccess;
	}

	@Override
	public void init() {
		super.init();

		usedLibraries.addAll(readUsedLibrariesFromProject());
	}

	private Collection<Library> readUsedLibrariesFromProject() {
		Collection<Library> usedLibraries;

		try {
			MavenXpp3Reader reader = new MavenXpp3Reader();
			org.apache.maven.model.Model model = reader.read(new FileReader(pomLocation));
			List<Dependency> dependencies = model.getDependencies();

			usedLibraries = dependencies.stream()
					.map(dependency -> new Library(Optional.ofNullable(dependency.getGroupId()).orElse(""),
							Optional.ofNullable(dependency.getArtifactId()).orElse(""),
							Optional.ofNullable(dependency.getVersion()).orElse(""), null, null))
					.collect(Collectors.toList());

		} catch (IOException | XmlPullParserException e1) {
			usedLibraries = new ArrayList<>();
		}

		return usedLibraries;
	}

	public void installPickedLibraries() throws FileNotFoundException, IOException, XmlPullParserException {
		MavenXpp3Reader reader = new MavenXpp3Reader();
		org.apache.maven.model.Model model = reader.read(new FileReader(pomLocation));

		pickedLibraries.forEach(library -> {
			Dependency dependency = new Dependency();
			dependency.setGroupId(library.getGroupId());
			dependency.setArtifactId(library.getArtifactId());
			dependency.setVersion(library.getVersion());

			model.addDependency(dependency);
		});

		MavenXpp3Writer writer = new MavenXpp3Writer();
		writer.write(new FileWriter(pomLocation), model);
	}

	public Collection<Library> getUsedLibraries() {
		return new HashSet<>(usedLibraries);
	}

	public Collection<Library> getBaseLibrariesForSearch() {
		return new HashSet<>(baseLibrariesForSearch);
	}

	public Collection<Library> getPickedLibraries() {
		return new HashSet<>(pickedLibraries);
	}

	public Call getSuggestedLibrariesAsync(Consumer<Collection<Library>> resultsConsumer,
			Consumer<ApiException> failConsumer) {
		Call call = null;

		try {
			List<io.swagger.client.model.Dependency> dependencies = baseLibrariesForSearch.stream()
					.map(this::parseLibraryModelToDependency).collect(Collectors.toList());

			Query query = new Query();
			query.setProjectDependencies(dependencies);

			RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess
					.getRecommenderRestController(Preferences.TIMEOUT_LIBRARYSEARCH);
			call = recommenderRestController.getRecommendedLibrariesUsingPOSTAsync(query,
					new ApiCallback<Recommendation>() {

						@Override
						public void onUploadProgress(long arg0, long arg1, boolean arg2) {

						}

						@Override
						public void onSuccess(Recommendation arg0, int arg1, Map<String, List<String>> arg2) {
							List<RecommendationItem> recommendationItems = arg0.getRecommendationItems();

							List<Library> suggestions = recommendationItems.stream()
									.map(item -> parseRecommendationItemToLibrary(item, query))
									.collect(Collectors.toList());

							resultsConsumer.accept(suggestions);
						}

						@Override
						public void onFailure(ApiException arg0, int arg1, Map<String, List<String>> arg2) {

							failConsumer.accept(arg0);
						}

						@Override
						public void onDownloadProgress(long arg0, long arg1, boolean arg2) {

						}
					});

		} catch (ApiException e) {
			e.printStackTrace();
		}

		return call;
	}

	public void setLibraryBaseOfSearch(Library library) {
		baseLibrariesForSearch.add(library);
	}

	public void unsetLibraryBaseOfSearch(Library library) {
		baseLibrariesForSearch.remove(library);
	}

	public void setLibraryPicked(Library library) {
		pickedLibraries.add(library);
	}

	public void unsetLibraryPicked(Library library) {
		pickedLibraries.remove(library);
	}

	private io.swagger.client.model.Dependency parseLibraryModelToDependency(Library base) {
		io.swagger.client.model.Dependency dependency = new io.swagger.client.model.Dependency();

		dependency.setGroupID(base.getGroupId());
		dependency.setArtifactID(base.getArtifactId());
		dependency.setVersion(base.getVersion());

		return dependency;
	}

	private Library parseRecommendationItemToLibrary(RecommendationItem recommendationItem, Query query) {
		RecommendedLibrary recommendedLibrary = recommendationItem.getRecommendedLibrary();

		String[] libraryInfo = recommendedLibrary.getLibraryName().split(":");
		String groupId = libraryInfo.length > 0 ? libraryInfo[0] : "";
		String artifactId = libraryInfo.length > 1 ? libraryInfo[1] : "";
		String version = libraryInfo.length > 2 ? libraryInfo[2] : "";
		String url = recommendedLibrary.getUrl();

		return new Library(groupId, artifactId, version, url, new FeedbackResource(query, recommendationItem));
	}

}
