/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package vasttext.datasets.file;

import java.io.IOException;
import java.nio.file.Path;

public class NoLabelFileDataSet extends FileDataSet
{

	public NoLabelFileDataSet(Path textFilePath, Path extraFilePath) throws IOException
	{
		super(textFilePath, extraFilePath);
		int entriesText;
		int entriesNumeric;
		super.multilabel=false;
		super.labelled=false;
		super.numericFeatures=true;
		entriesText=checkFiles(textFilePath, false);
		entriesNumeric=checkFiles(extraFilePath, false);
		if(entriesText!=entriesNumeric)
			throw new UnsupportedOperationException("Number of text entries ["+ entriesText +"] and numeric features entries ["+ entriesNumeric +"] do not match.");
	}
	
	public NoLabelFileDataSet(Path textFilePath) throws IOException
	{
		super(textFilePath);
		super.multilabel=false;
		super.labelled=false;
		super.numericFeatures=false;
		checkFiles(textFilePath, false);
	}

}
