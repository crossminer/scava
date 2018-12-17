package org.eclipse.scava.crossflow.tests.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LineSource extends LineSourceBase {
	
	@Override
	public void produce() {
		try {
			for (File file : workflow.getInputDirectory().listFiles()) {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = reader.readLine()) != null) {
					sendToLines(new Line(line));
				}
				reader.close();
			}
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}