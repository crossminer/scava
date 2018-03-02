/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.context.sourcecodestatus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.scava.commons.libraryapi.LibraryAPIElement;
import org.eclipse.scava.commons.transaction.APIUsageInContext;
import org.eclipse.scava.plugin.context.sourcecodestatus.astmanipulating.APIUsageFinderASTVisitor;

public class APIUsageDetector {
	
	public static List<APIUsageInContext> findUsagesIn(IJavaProject project, List<LibraryAPIElement> apiElements) throws SourceCodeStatusException
	{
		List<APIUsageInContext> apiUsages = new LinkedList<>();
		
		try {
			IPackageFragment[] packageFragments = project.getPackageFragments();
			for (IPackageFragment iPackageFragment : packageFragments) {
				ICompilationUnit[] compilationUnits = iPackageFragment.getCompilationUnits();
				for (ICompilationUnit iCompilationUnit : compilationUnits) {
					List<APIUsageInContext> apiUsagesInCompilationUnit = findUsagesIn(iCompilationUnit, apiElements);
					apiUsages.addAll(apiUsagesInCompilationUnit);
				}
			}
		} catch (JavaModelException e) {
			throw new SourceCodeStatusException(e);
		}
		
		return apiUsages;
	}
	
	public static List<APIUsageInContext> findUsagesIn(ICompilationUnit compilationUnit, List<LibraryAPIElement> apiElements)
	{
		ASTNode node = SourceCodeParser.parseASTFrom(compilationUnit);
		return findUsagesIn(node, apiElements);
	}
	
	public static List<APIUsageInContext> findUsagesIn(ASTNode node, List<LibraryAPIElement> apiElements)
	{
		APIUsageFinderASTVisitor visitor = new APIUsageFinderASTVisitor(apiElements);
		node.accept(visitor);
		List<APIUsageInContext> apiUsages = visitor.getApiUsages();
		List<APIUsageInContext> mergedAPIUsages = mergeOverlappingUsages(apiUsages);
		return mergedAPIUsages;
	}
	
	private static List<APIUsageInContext> mergeOverlappingUsages(List<APIUsageInContext> usages) {
		
		List<APIUsageInContext> mergedUsages = new LinkedList<>();
		
		outerLoop: for (APIUsageInContext usage : usages) {
			
			for (Iterator<APIUsageInContext> mergedUsagesIterator = mergedUsages.iterator(); mergedUsagesIterator.hasNext();) {
				APIUsageInContext mergedOccurence = mergedUsagesIterator.next();
				
				if (usage.getContext().isCovering(mergedOccurence.getContext())) {
					
					for (LibraryAPIElement apiElement : mergedOccurence.getUsedAPIElements()) {
						usage.addUsedAPIElement(apiElement);
					}
					
					mergedUsagesIterator.remove();// The order is important! first
											// remove, only then add a new one
					mergedUsages.add(usage);
					
					continue outerLoop;
				} else if (mergedOccurence.getContext().isCovering(usage.getContext())) {
					
					for (LibraryAPIElement apiElement : usage.getUsedAPIElements()) {
						mergedOccurence.addUsedAPIElement(apiElement);
					}
					
					continue outerLoop;
				}
			}
			
			mergedUsages.add(usage);
		}
		
		return mergedUsages;
	}
}
