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
package org.eclipse.scava.commons.context;

import org.eclipse.scava.commons.context.sourcecode.SourceCodeContext;
import org.eclipse.scava.commons.context.useractivity.UserActivityMetric;

/**
 * Provides a base class for specific context-describer classes.
 * This class can be derived to create particular context-describer classes
 * like {@link SourceCodeContext} or {@link UserActivityMetric}.
 *
 */
public abstract class DevelopmentContext {
	
}
