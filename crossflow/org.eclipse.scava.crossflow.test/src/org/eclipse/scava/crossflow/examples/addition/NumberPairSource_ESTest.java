/*
 * This file was automatically generated by EvoSuite
 * Tue Oct 23 18:56:58 GMT 2018
 */

package org.eclipse.scava.crossflow.examples.addition;

import org.junit.Test;
import static org.junit.Assert.*;
import org.eclipse.scava.crossflow.examples.addition.NumberPairSource;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class NumberPairSource_ESTest extends NumberPairSource_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      NumberPairSource numberPairSource0 = new NumberPairSource();
      numberPairSource0.produce();
  }
}
