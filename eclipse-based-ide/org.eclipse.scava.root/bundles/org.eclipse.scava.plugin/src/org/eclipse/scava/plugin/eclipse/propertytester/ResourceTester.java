/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.eclipse.propertytester;

import java.util.Collection;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;

public class ResourceTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		switch (property) {
		case "hasPom":
			if (receiver instanceof Collection) {
				Collection<Object> collection = (Collection<Object>) receiver;

				if (collection.size() != 1) {
					return false;
				}

				Object object = collection.iterator().next();

				if (object instanceof IResource) {
					IResource resource = (IResource) object;
					IProject project = resource.getProject();
					return (boolean) expectedValue == hasPom(project);
				} else if (object instanceof IJavaElement) {
					IJavaElement element = (IJavaElement) object;
					IJavaProject javaProject = element.getJavaProject();
					IProject project = javaProject.getProject();
					return (boolean) expectedValue == hasPom(project);
				} else if (object instanceof PackageFragmentRootContainer) {
					PackageFragmentRootContainer packageFragmentRootContainer = (PackageFragmentRootContainer) object;
					IJavaProject javaProject = packageFragmentRootContainer.getJavaProject();
					IProject project = javaProject.getProject();
					return (boolean) expectedValue == hasPom(project);
				}
			}
			return false;

		case "print":
			System.out.println("Tested object: " + receiver);
			System.out.println("Tested object's class: " + receiver.getClass());

			if (receiver instanceof Collection) {
				Collection collection = (Collection) receiver;

				for (Object object : collection) {
					System.out.println(" Collection's element: " + object);
					System.out.println(" Collection's element's class: " + object.getClass());
				}
			}

			return true;
		default:
			return false;
		}
	}

	private boolean hasPom(IProject project) {
		IFile file = project.getFile(new Path("pom.xml"));
		return file.exists();
	}
}
