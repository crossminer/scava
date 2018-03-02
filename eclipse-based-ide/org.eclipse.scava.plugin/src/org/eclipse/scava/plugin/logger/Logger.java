/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.logger;

import java.util.ArrayList;
import java.util.List;

public class Logger {
	private static List<ILoggerListener> listeners = new ArrayList<>();
	private static ILoggerListener standardOutputListener;
	
	static {
		standardOutputListener = new ILoggerListener() {
			@Override
			public void listen(String message, LoggerMessageKind messageKind) {
				System.out.println(message);
			}
		};
	};
	
	public static ILoggerListener registerListener(ILoggerListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
		return listener;
	}
	
	public static void unregisterListener(ILoggerListener listener) {
		while (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}
	
	public static void setPrintToConsole(boolean printToConsole) {
		if (printToConsole) {
			registerListener(standardOutputListener);
		} else {
			unregisterListener(standardOutputListener);
		}
	}
	
	public static void log(String message, LoggerMessageKind messageKind) {
		for (ILoggerListener iLoggerListener : listeners) {
			iLoggerListener.listen(message, messageKind);
		}
	}
}
