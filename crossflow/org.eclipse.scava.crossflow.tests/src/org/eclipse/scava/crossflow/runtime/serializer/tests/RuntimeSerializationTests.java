package org.eclipse.scava.crossflow.runtime.serializer.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	JsonSerializerTest.class,
	XstreamSerializerTest.class
})
public class RuntimeSerializationTests {

}
