/*
 * This file was automatically generated by EvoSuite
 * Tue Oct 23 17:17:45 GMT 2018
 */

package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import org.junit.Test;
import static org.junit.Assert.*;
import org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.EmptySink;
import org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.Result;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class EmptySink_ESTest extends EmptySink_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      EmptySink emptySink0 = new EmptySink();
      Result result0 = new Result();
      emptySink0.consumeResultsPublisher(result0);
      assertNull(result0.getTechnology());
  }
}
