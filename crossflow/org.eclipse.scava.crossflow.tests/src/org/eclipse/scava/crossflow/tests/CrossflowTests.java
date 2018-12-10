package org.eclipse.scava.crossflow.tests;

import org.eclipse.scava.crossflow.tests.addition.AdditionWorkflowCommandLineTests;
import org.eclipse.scava.crossflow.tests.addition.AdditionWorkflowTests;
import org.eclipse.scava.crossflow.tests.cache.DirectoryCacheTests;
import org.eclipse.scava.crossflow.tests.crawler.CrawlerWorkflowTests;
import org.eclipse.scava.crossflow.tests.csvsourcesinkcase.CsvSourceSinkTestCase;
import org.eclipse.scava.crossflow.tests.opinionated.OccurencesWorkflowTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AdditionWorkflowTests.class,
	AdditionWorkflowCommandLineTests.class,
	CsvSourceSinkTestCase.class,
	DirectoryCacheTests.class,
	OccurencesWorkflowTests.class,
	CrawlerWorkflowTests.class
})
public class CrossflowTests {

}
