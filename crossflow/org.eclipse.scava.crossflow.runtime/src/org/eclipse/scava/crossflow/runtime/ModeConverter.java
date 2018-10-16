package org.eclipse.scava.crossflow.runtime;

import com.beust.jcommander.IStringConverter;

public class ModeConverter implements IStringConverter<Mode> {

		@Override
		public Mode convert(String s) {
			if ("master".equals(s)) return Mode.MASTER;
			else if ("worker".equals(s)) return Mode.WORKER;
			else throw new RuntimeException("Mode must be 'master' or 'worker' but was '" + s + "'");
		}
		
	}