package org.eclipse.scava.business;

import org.eclipse.scava.business.dto.CodeCloneResult;

import com.harukizaemon.simian.Options;

public interface ICodeCloneDetector {
	CodeCloneResult checkClone(String left, String right, Options option);


}
