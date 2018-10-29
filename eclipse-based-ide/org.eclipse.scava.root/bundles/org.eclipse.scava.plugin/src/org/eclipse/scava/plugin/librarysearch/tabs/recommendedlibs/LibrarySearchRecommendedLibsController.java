package org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModelViewController;

import com.google.common.eventbus.Subscribe;

import io.swagger.client.model.RecommendedLibrary;

public class LibrarySearchRecommendedLibsController
		extends AbstractModelViewController<ILibrarySearchRecommendedLibsModel, ILibrarySearchRecommendedLibsView>
		implements ILibrarySearchRecommendedLibsController {

	public LibrarySearchRecommendedLibsController(IController parent, ILibrarySearchRecommendedLibsModel model,
			ILibrarySearchRecommendedLibsView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		getView().init();
		
		List<RecommendedLibrary> recommendedLibraries = getModel().getRecommendedLibraries();

		getView().showRecommendedLibraries(recommendedLibraries);
	}

	@Subscribe
	public void onInstallLibrary(ILibrarySearchRecommendedLibsView.InstallLibrary e)
			throws FileNotFoundException, IOException, XmlPullParserException {
		if (e.getSender() == getView()) {
			String[] libraryData = e.getLibrary().split(":");
			String groupId = libraryData[0];
			String artifactId = libraryData[1];
			String version = libraryData[2];

			MavenXpp3Reader reader = new MavenXpp3Reader();
			Model model = reader.read(new FileReader(getModel().getPom()));

			Dependency dependency = new Dependency();
			dependency.setGroupId(groupId);
			dependency.setArtifactId(artifactId);
			dependency.setVersion(version);

			model.addDependency(dependency);

			MavenXpp3Writer writer = new MavenXpp3Writer();
			writer.write(new FileWriter(getModel().getPom()), model);
			
			getView().showInstalledMessage(e.getLibrary());
		}
	}
}
