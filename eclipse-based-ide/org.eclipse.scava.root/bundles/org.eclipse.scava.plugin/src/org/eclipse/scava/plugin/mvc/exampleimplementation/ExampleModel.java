/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.exampleimplementation;

import java.util.Date;

import org.eclipse.scava.plugin.mvc.model.Model;

public class ExampleModel extends Model {

	public String getCurrentTime() {
		return new Date().toString();
	}

}
