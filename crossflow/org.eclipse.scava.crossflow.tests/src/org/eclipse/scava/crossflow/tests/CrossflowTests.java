package org.eclipse.scava.crossflow.tests;

import org.eclipse.scava.crossflow.tests.addition.AdditionWorkflowTests;
import org.eclipse.scava.crossflow.tests.cache.DirectoryCacheTests;
import org.eclipse.scava.crossflow.tests.csvsourcesinkcase.CsvSourceSinkTestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AdditionWorkflowTests.class,
	CsvSourceSinkTestCase.class,
	DirectoryCacheTests.class
})
public class CrossflowTests {

}
