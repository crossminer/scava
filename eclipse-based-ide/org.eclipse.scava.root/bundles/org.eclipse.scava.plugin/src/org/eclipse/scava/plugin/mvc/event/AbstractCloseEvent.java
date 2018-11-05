/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.event;

import org.eclipse.scava.plugin.mvc.IMvcComponent;

public abstract class AbstractCloseEvent<SenderComponentType extends IMvcComponent>
		extends AbstractEvent<SenderComponentType> {

	public AbstractCloseEvent(SenderComponentType sender) {
		super(sender);
	}

}
