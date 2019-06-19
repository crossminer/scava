/*******************************************************************************
 * Copyright (c) 2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributor(s):
 *      Patrick Neubauer - initial API and implementation
 ******************************************************************************/
package org.eclipse.scava.crossflow.examples.techanalysis;

import java.io.File;

public class TechAnalysisProperties {

	final static File CLONE_PARENT_DESTINATION = new File(
	"tmp" + File.separator + "clones");
	
	protected final static int MAX_NUMBER_OF_COMMITMENTS = 128;

}
