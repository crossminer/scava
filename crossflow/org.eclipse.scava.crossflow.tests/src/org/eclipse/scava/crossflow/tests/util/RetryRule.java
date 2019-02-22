package org.eclipse.scava.crossflow.tests.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RetryRule implements TestRule {

	private AtomicInteger retryCount;

	public RetryRule(int retries) {
		super();
		this.retryCount = new AtomicInteger(retries);
	}

	@Override
	public Statement apply(final Statement base, final Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				Throwable caughtThrowable = null;

				while (retryCount.getAndDecrement() > 0) {
					try {
						base.evaluate();
						return;
					} catch (Throwable t) {
						if (retryCount.get() > 0 && description.getAnnotation(Retry.class) != null) {
							caughtThrowable = t;
							System.err.println(description.getDisplayName() + ": Failed, " + retryCount.toString()
									+ " retries remain");
						} else {
							throw caughtThrowable == null ? new Throwable("null throwable") : caughtThrowable;
						}
					}
				}
			}
		};
	}
}