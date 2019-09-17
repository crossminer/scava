package org.eclipse.scava.crossflow.runtime;

import com.beust.jcommander.IStringConverter;

public class ModeConverter implements IStringConverter<Mode> {

		@Override
		public Mode convert(String s) {
			return Mode.valueOf(s.toUpperCase());
		}
		
	}