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
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
	
	private String logPath;

	public Logger() {
		Date d = new Date();
		
		
		logPath = "C:\\Users\\Szamos\\Desktop\\log"+d.getTime()+".txt";
	}

	public void log(String s) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(logPath, true));
		writer.append("\n");
		writer.append(s);
		writer.close();
	}

}
