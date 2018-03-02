/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.event.informative;

import org.eclipse.scava.plugin.event.Event;
import org.eclipse.scava.plugin.event.IEvent;

public class InformativeEvent<ParameterType> extends Event<IInformativeEventListener<ParameterType>> implements
		IInformativeEvent<ParameterType>{
	public void invoke(ParameterType parameter) {
		getListeners().forEach( listener -> listener.handle(parameter) );
	}
}
