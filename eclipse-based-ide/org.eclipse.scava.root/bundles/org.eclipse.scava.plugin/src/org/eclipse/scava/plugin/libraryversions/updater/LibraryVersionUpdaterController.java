/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.updater;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.scava.plugin.libraryversions.Library;
import org.eclipse.scava.plugin.libraryversions.LibraryFilterRule;
import org.eclipse.scava.plugin.libraryversions.checker.RefreshAvailableLibraryUpdates;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;

import io.swagger.client.ApiException;

public class LibraryVersionUpdaterController
		extends ModelViewController<LibraryVersionUpdaterModel, LibraryVersionUpdaterView>
		implements ILibraryVersionUpdaterViewEventListener {

	public LibraryVersionUpdaterController(Controller parent, LibraryVersionUpdaterModel model,
			LibraryVersionUpdaterView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		try {
			List<LibraryFilterRule> rules = getModel().getRules(getModel().getProject());
			Collection<Library> usedLibrariesFromPom = getModel().getUsedLibrariesFromPom(getModel().getPomLocation());

			if (usedLibrariesFromPom.isEmpty()) {
				MessageDialog.openInformation(getView().getShell(), "No libraries",
						"There are no libraries used in the selected project.");
				dispose();
				return;
			}

			Map<Library, List<Library>> availableVersionsOfLibraries = getModel()
					.getAvailableVersionsOfLibraries(usedLibrariesFromPom, rules);

			if (availableVersionsOfLibraries.isEmpty()) {
				MessageDialog.openInformation(getView().getShell(), "No updates found",
						"Could not found any updates for the used libraries.");
				dispose();
				return;
			} else {
				availableVersionsOfLibraries.entrySet().forEach(entry -> {
					getView().showLibrary(entry.getKey(), entry.getValue());
				});
			}

			String lastJarPath = getModel().getLastJarPath();
			if (lastJarPath != null) {
				getView().showLastJarPath(lastJarPath);
			}

		} catch (ApiException | CoreException | IOException | XmlPullParserException e) {
			ErrorHandler.logAndShowErrorMessage(getView().getShell(), e);
			if (e instanceof ApiException) {
				ApiException apiException = (ApiException) e;
				Throwable cause = apiException.getCause();
				if (cause != null && cause instanceof SocketTimeoutException) {
					dispose();
					return;
				}
			}
		}
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void onInstall(Map<Library, Library> libraries, String jarPath) {

		if (libraries.size() == 0) {
			MessageDialog.openError(getView().getShell(), "Error", "You did not select any libraries to be updated.");
			return;
		}

		File jar = null;
		if (!jarPath.isEmpty()) {
			jar = new File(jarPath);
			if (!jar.exists()) {
				MessageDialog.openError(getView().getShell(), "Error", "The JAR at the given location does not exist.");
				return;
			}
		}

		if (!MessageDialog.openConfirm(getView().getShell(), "Confirmation",
				"The following libraries will be updated to the selected versions:\n" + libraries
						.entrySet().stream().map(entry -> entry.getKey().toMavenCoordWithoutVersion() + " "
								+ entry.getKey().getVersion() + " -> " + entry.getValue().getVersion() + "\n")
						.collect(Collectors.joining()))) {
			return;
		}
		if (!MessageDialog.openConfirm(getView().getShell(), "Confirmation",
				"All custom informations from the project's pom.xml file will be erased.\nAre you sure you want to continue?")) {
			return;
		}

		try {
			getModel().setLastJarPath(jarPath);
			getModel().install(libraries);
			MessageDialog.openInformation(getView().getShell(), "Install",
					"The installation of the selected versions has been finished successfully.");

			if (jar != null && jar.exists()) {
				routeEventToParentController(new LibraryVersionUpdateApiMigrationRequestEvent(this,
						getModel().getProject(), jar, libraries));
			}

			dispose();
		} catch (IOException | XmlPullParserException | CoreException e) {
			e.printStackTrace();
			MessageDialog.openError(getView().getShell(), "Error", "Could not install the selected versions.\n" + e);
		}
	}

	@Override
	protected void disposeController() {
		routeEventToParentController(new RefreshAvailableLibraryUpdates(this));

		super.disposeController();
	}

}
