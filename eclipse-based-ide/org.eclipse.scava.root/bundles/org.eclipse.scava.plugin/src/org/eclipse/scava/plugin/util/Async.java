package org.eclipse.scava.plugin.util;

import java.util.function.Consumer;

import org.eclipse.swt.widgets.Display;

public class Async {
	@SafeVarargs
	public static final <T> Consumer<T> asyncDefaultDisplay(Consumer<T>... consumers) {
		return (arg) -> {
			Display.getDefault().syncExec(() -> {
				for (Consumer<T> consumer : consumers) {
					consumer.accept(arg);
				}
			});
		};
	}
}
