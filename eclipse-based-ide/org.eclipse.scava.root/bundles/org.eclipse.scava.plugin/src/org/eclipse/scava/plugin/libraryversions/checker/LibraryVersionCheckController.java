/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.checker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.eclipse.decorator.LibraryUpdateAvailableDecorator;
import org.eclipse.scava.plugin.libraryversions.Library;
import org.eclipse.scava.plugin.libraryversions.LibraryFilterRule;
import org.eclipse.scava.plugin.libraryversions.LibraryVersionModel;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelController;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import io.swagger.client.ApiException;

public class LibraryVersionCheckController extends ModelController<LibraryVersionModel> {

	public LibraryVersionCheckController(Controller parent, LibraryVersionModel model) {
		super(parent, model);
	}

	public void markUpdatableLibraries(IProject project) throws FileNotFoundException {

		IFile pom = project.getFile("pom.xml");
		if (!pom.exists()) {
			throw new FileNotFoundException(pom.toString());
		}

		new Thread(() -> {
			try {
				Collection<Library> usedLibraries = getModel().getUsedLibrariesFromPom(pom.getLocation().toOSString());

				List<LibraryFilterRule> rules = getModel().getRules(project);

				Map<Library, List<Library>> availableVersionsOfLibraries = getModel()
						.getAvailableVersionsOfLibraries(usedLibraries, rules);

				boolean atLeastOneUpdatable = availableVersionsOfLibraries.entrySet().stream()
						.anyMatch(entry -> entry.getKey().getReleaseDate() != null);

				Display.getDefault().asyncExec(() -> {
					LibraryUpdateAvailableDecorator decorator = (LibraryUpdateAvailableDecorator) PlatformUI
							.getWorkbench().getDecoratorManager()
							.getBaseLabelProvider("org.eclipse.scava.plugin.decorators.libraryupdate.available");

					if (decorator != null) {
						decorator.setState(project, atLeastOneUpdatable);
					}
				});
			} catch (CoreException | ApiException | IOException | XmlPullParserException e) {
				Throwable cause = e.getCause();
				if (cause != null && cause instanceof ApiException) {
					ApiException apiException = (ApiException) cause;
					Throwable cause2 = apiException.getCause();
					if (cause2 != null && cause2 instanceof SocketTimeoutException) {
						return;
					}
				}
				ErrorHandler.logAndGetMessage(e);
			}
		}).start();
	}

	@Override
	protected void disposeController() {
		Display.getDefault().asyncExec(() -> {
			LibraryUpdateAvailableDecorator decorator = (LibraryUpdateAvailableDecorator) PlatformUI.getWorkbench()
					.getDecoratorManager()
					.getBaseLabelProvider("org.eclipse.scava.plugin.decorators.libraryupdate.available");

			if (decorator != null) {
				decorator.clear();
			}
		});

		super.disposeController();
	}

}
