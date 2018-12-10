package org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.eclipse.scava.plugin.usermonitoring.event.IEvent;

@Retention(RUNTIME)
@Target(TYPE)
public @interface BasedOn {
	Class<? extends IEvent>[] value();
}
