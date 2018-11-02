package org.eclipse.scava.crossflow.runtime;

import com.beust.jcommander.IStringConverter;

public class ModeConverter implements IStringConverter<Mode> {

		@Override
		public Mode convert(String s) {
			if ("master".equals(s)) return Mode.MASTER;
			else if ("worker".equals(s)) return Mode.WORKER;
			else if ("master_bare".equals(s)) return Mode.MASTER_BARE;
			else throw new RuntimeException("Mode must be 'master_bare', 'master' or 'worker' but was '" + s + "'");
		}
		
	}