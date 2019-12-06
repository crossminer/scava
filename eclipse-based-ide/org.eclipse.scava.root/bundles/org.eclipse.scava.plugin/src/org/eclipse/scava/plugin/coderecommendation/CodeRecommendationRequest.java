/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.plugin.collections.AutoSortedArrayList;

public class CodeRecommendationRequest
		implements Comparable<CodeRecommendationRequest>, IPreviewable, ICodeRecommendationElement {
	private final Date date;
	private final int startLine;
	private final int endLine;
	private final String text;

	private final CodeRecommendationTarget target;
	private final List<CodeRecommendation> codeRecommendations = new AutoSortedArrayList<>();

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public CodeRecommendationRequest(Date date, int startLine, int endLine, String text,
			CodeRecommendationTarget target) {
		super();
		this.date = date;
		this.startLine = startLine;
		this.endLine = endLine;
		this.text = text;
		this.target = target;
	}

	public String getText() {
		return text;
	}

	public CodeRecommendationTarget getTarget() {
		return target;
	}

	public List<CodeRecommendation> getCodeRecommendations() {
		return codeRecommendations;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(dateFormatter.format(date));
		sb.append(" Line ");
		sb.append(startLine);
		sb.append('-');
		sb.append(endLine);

		return sb.toString();
	}

	@Override
	public int compareTo(CodeRecommendationRequest o) {
		return toString().compareToIgnoreCase(o.toString());
	}

	@Override
	public String getPreviewTitle() {
		return toString();
	}

	@Override
	public String getPreviewContent() {
		return getText();
	}

	@Override
	public boolean canBeInserted() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + endLine;
		result = prime * result + startLine;
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodeRecommendationRequest other = (CodeRecommendationRequest) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (endLine != other.endLine)
			return false;
		if (startLine != other.startLine)
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
