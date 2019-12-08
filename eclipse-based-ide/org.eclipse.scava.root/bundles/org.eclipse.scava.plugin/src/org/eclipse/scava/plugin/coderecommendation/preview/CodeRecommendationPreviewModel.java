/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation.preview;

import org.eclipse.scava.plugin.coderecommendation.IPreviewable;
import org.eclipse.scava.plugin.mvc.model.Model;

public class CodeRecommendationPreviewModel extends Model {
	private final IPreviewable previewable;

	public CodeRecommendationPreviewModel(IPreviewable previewable) {
		super();
		this.previewable = previewable;
	}

	public IPreviewable getPreviewable() {
		return previewable;
	}

}
