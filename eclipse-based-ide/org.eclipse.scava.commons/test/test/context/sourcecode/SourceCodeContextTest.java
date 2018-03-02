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
package test.context.sourcecode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.eclipse.scava.commons.context.sourcecode.SourceCodeContext;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.LineInfo;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailKind;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailTypeName;
import org.junit.jupiter.api.Test;

class SourceCodeContextTest {
	private String sampleContent = "Lorem ipsum dolor sit amet,\nconsectetur adipiscing elit. Nunc tincidunt\npellentesque ligula et pellentesque.\nQuisque et lectus scelerisque,\nrutrum mi vitae, molestie metus.\nPhasellus eu sapien vitae velit scelerisque pulvinar\net vitae risus. Nullam quis tempor eros.\nFusce pulvinar lorem eget nisi\nmollis commodo. Cras vehicula\ntempus enim, ac aliquam arcu commodo\nin. Curabitur in velit libero.\nFusce eget quam sagittis,\nfringilla mauris at, rutrum metus.\nSed aliquam lacus vitae.";
	private SourceCodeContext coverageReferenceContext = new SourceCodeContext(sampleContent, "", "", 3, 3, 0);
	private SourceCodeContext fullyCoveredContext = new SourceCodeContext(sampleContent, "", "", 4, 1, 0);
	private SourceCodeContext partlyCoveredContextFromLeft = new SourceCodeContext(sampleContent, "", "", 2, 3, 0);
	private SourceCodeContext partlyCoveredContext2FromRight = new SourceCodeContext(sampleContent, "", "", 5, 3, 0);
	private SourceCodeContext fullySeparatedContext = new SourceCodeContext(sampleContent, "", "", 10, 1, 0);
	
	@Test
	void testCoverageWithFullyCoveredCase() {
		assertTrue(coverageReferenceContext.isCovering(fullyCoveredContext), "Context should cover the other.");
	}
	
	@Test
	void testCoverageWithPartlyCoveredCase() {
		assertTrue(coverageReferenceContext.isCovering(partlyCoveredContextFromLeft), "Context should cover the partly overlapping other.");
		assertTrue(coverageReferenceContext.isCovering(partlyCoveredContext2FromRight), "Context should cover the partly overlapping other.");
	}
	
	@Test
	void testCoverageWithSeparatedCase() {
		assertFalse(coverageReferenceContext.isCovering(fullySeparatedContext), "Context should not cover the fully separated other.");
	}
	
	@Test
	void testCoverageWithBorderlineCases() {
		SourceCodeContext notCoveredContextFromLeft = new SourceCodeContext(sampleContent, "", "", 0, 3, 0);
		SourceCodeContext justCoveredCotextFromLeft = new SourceCodeContext(sampleContent, "", "", 1, 3, 0);
		SourceCodeContext notCoveredContextFromRight = new SourceCodeContext(sampleContent, "", "", 6, 3, 0);
		SourceCodeContext justCoveredCotextFromRight = new SourceCodeContext(sampleContent, "", "", 5, 3, 0);

		assertFalse(coverageReferenceContext.isCovering(notCoveredContextFromLeft));
		assertTrue(coverageReferenceContext.isCovering(justCoveredCotextFromLeft));
		assertFalse(coverageReferenceContext.isCovering(notCoveredContextFromRight));
		assertTrue(coverageReferenceContext.isCovering(justCoveredCotextFromRight));
	}
	
	@Test
	void testDetailAdditionWithInvalidLineNumber() {
		SourceCodeContext context = new SourceCodeContext(sampleContent, "", "", 0, 42, 2);
		ASTDetailTypeName detail = new ASTDetailTypeName(0, 1, "a", ASTDetailKind.TYPE_INTERFACE);
		
		assertThrows(IllegalArgumentException.class, () -> context.addDetail(0, detail));
		assertThrows(IllegalArgumentException.class, () -> context.addDetail(999, detail));
	}
	
	@Test
	void testDetailAddition() {
		SourceCodeContext context = new SourceCodeContext(sampleContent, "", "", 0, sampleContent.length(), 2);
		ASTDetailTypeName detail1 = new ASTDetailTypeName(0, 1, "a", ASTDetailKind.TYPE_INTERFACE);
		ASTDetailTypeName detail2 = new ASTDetailTypeName(2, 3, "b", ASTDetailKind.TYPE_INTERFACE);
		ASTDetailTypeName detail3 = new ASTDetailTypeName(4, 5, "c", ASTDetailKind.TYPE_INTERFACE);
		
		context.addDetail(2, detail1);
		context.addDetail(3, detail2);
		context.addDetail(3, detail3);
		
		List<LineInfo> lines = context.getLines();
		assertSame(detail1, lines.get(0).getDetails().get(0), "Added detail should match the original.");
		assertSame(detail2, lines.get(1).getDetails().get(0), "Added detail should match the original.");
		assertSame(detail3, lines.get(1).getDetails().get(1), "Added detail should match the original.");
	}
}
