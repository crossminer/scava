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
package test.context.sourcecode.lineinfo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.eclipse.scava.commons.context.sourcecode.lineinfo.LineInfo;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetail;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailKind;
import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetailTypeName;
import org.junit.jupiter.api.Test;

class LineInfoTest {
	
	@Test
	void testDetailAddition() {
		LineInfo lineInfo = new LineInfo("foo");
		ASTDetailTypeName detail1 = new ASTDetailTypeName(0, 3, "bar", ASTDetailKind.TYPE_INTERFACE);
		ASTDetailTypeName detail2 = new ASTDetailTypeName(5, 7, "asdaaaaa", ASTDetailKind.TYPE_INTERFACE);

		lineInfo.addDetail(detail1);
		lineInfo.addDetail(detail2);
		
		List<ASTDetail> details = lineInfo.getDetails();
		assertSame(detail1, details.get(0));
		assertSame(detail2, details.get(1));
	}
	
}
