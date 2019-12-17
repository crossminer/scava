/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.preferences;

import java.util.HashSet;
import java.util.Set;

public class UserActivityDisablements {
	private Set<String> disabledElements;

	public UserActivityDisablements() {
		disabledElements = new HashSet<>();
	}

	public void disable(Class<?> elementClass) {
		String name = elementClass.getSimpleName();
		disabledElements.add(name);
	}

	public void enable(Class<?> elementClass) {
		String name = elementClass.getSimpleName();
		disabledElements.remove(name);
	}

	public boolean isDisabled(Class<?> elementClass) {
		String name = elementClass.getSimpleName();
		return disabledElements.contains(name);
	}


}
