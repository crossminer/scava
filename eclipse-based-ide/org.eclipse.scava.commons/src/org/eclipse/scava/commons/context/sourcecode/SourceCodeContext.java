/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt J�nos Szamosv�lgyi
*    Endre Tam�s V�radi
*    Gerg� Balogh
**********************************************************************/
package org.eclipse.scava.commons.context.sourcecode;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.commons.context.DevelopmentContext;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.LineInfo;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetail;

/**
 * Provides a context-describer class for source codes.
 * This class stores informations related to a java source file
 * like the used java version, the raw source code,
 * the relative path of the file within the project, a range in the source code
 * or more particular informations for each line.
 *
 */
public class SourceCodeContext extends DevelopmentContext {
	private String targetFile;
	private String rawText;
	private String javaVersion;
	private List<LineInfo> lines;
	private int offset;
	private int length;
	private transient int startLine;
	
	public SourceCodeContext(String rawText, String targetFile, String javaVersion, int offset, int length,
			int startLine) {
		super();
		this.rawText = rawText;
		this.targetFile = targetFile;
		this.javaVersion = javaVersion;
		this.offset = offset;
		this.length = length;
		this.startLine = startLine;
		
		prepareLineInfos(rawText, offset, length);
	}
	
	private void prepareLineInfos(String rawText, int offset, int length) {
		this.lines = new ArrayList<>();
		String[] splittedLines = rawText.substring(offset, offset + length).split("\\r?\\n");
		for (String line : splittedLines) {
			lines.add(new LineInfo(line));
		}
	}
	
	@Override
	public String toString() {
		return "ContextInfo [javaVersion=" + javaVersion + ", fullText=" + rawText + ", filePath=" + targetFile
				+ ", astInfos=" + lines + ", offset=" + offset + ", length=" + length + ", startLine=" + startLine
				+ "]";
	}
	
	/**
	 * Adds the given detail to this context at the given line.
	 * @param line the number of the line related to the detail
	 * @param detail the {@link ASTDetail} to be added
	 * @throws IllegalArgumentException If an invalid line number has been given
	 */
	public void addDetail(int line, ASTDetail detail) {
		line -= startLine;
		if (line >= 0 && line < lines.size()) {
			lines.get(line).addDetail(detail);
		} else {
			throw new IllegalArgumentException("Invalid line number.");
		}
	}
	
	/**
	 * Returns whether the range of the given {@link SourceCodeContext} is covered by this. 
	 * @param contextInfo
	 * @return whether the range of the given {@link SourceCodeContext} is covered by this
	 */
	public boolean isCovering(SourceCodeContext contextInfo) {
		return targetFile.equals(contextInfo.targetFile) && rawText.equals(contextInfo.rawText)
				&& contextInfo.offset < this.offset + this.length && contextInfo.offset
						+ contextInfo.length > this.offset;
	}
	
	public String getJavaVersion() {
		return javaVersion;
	}
	
	public String getTargetFile() {
		return targetFile;
	}
	
	public String getRawText() {
		return rawText;
	}
	
	/**
	 * Returns a list of the informations stored for each line.
	 * @return list of {@link LineInfo}s stored for each line
	 */
	public List<LineInfo> getLines() {
		return lines;
	}
	
	/**
	 * Returns the start position of the described range.
	 * @return the start position of the described ranged
	 */
	public int getOffset() {
		return offset;
	}
	
	/**
	 * Returns the length of the described range.
	 * @return the length of the described range
	 */
	public int getLength() {
		return length;
	}
	
	public int getStartLine() {
		return startLine;
	}
	
}
