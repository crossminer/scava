/*
 * This file was automatically generated by EvoSuite
 * Tue Oct 23 18:27:13 GMT 2018
 */

package org.eclipse.scava.crossflow.examples.opinionated;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.eclipse.scava.crossflow.examples.opinionated.OccurencesMonitor;
import org.eclipse.scava.crossflow.examples.opinionated.OpinionatedExample;
import org.eclipse.scava.crossflow.examples.opinionated.Word;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class OccurencesMonitorBase_ESTest extends OccurencesMonitorBase_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      OccurencesMonitor occurencesMonitor0 = new OccurencesMonitor();
      Word word0 = new Word();
      // Undeclared exception!
      try { 
        occurencesMonitor0.consumeWordsActual(word0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.opinionated.OccurencesMonitorBase", e);
      }
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      OccurencesMonitor occurencesMonitor0 = new OccurencesMonitor();
      Workflow workflow0 = occurencesMonitor0.getWorkflow();
      assertNull(workflow0);
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      OccurencesMonitor occurencesMonitor0 = new OccurencesMonitor();
      OpinionatedExample opinionatedExample0 = new OpinionatedExample();
      occurencesMonitor0.setWorkflow(opinionatedExample0);
      Workflow workflow0 = occurencesMonitor0.getWorkflow();
      assertEquals(Mode.MASTER, workflow0.getMode());
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      OccurencesMonitor occurencesMonitor0 = new OccurencesMonitor();
      // Undeclared exception!
      try { 
        occurencesMonitor0.taskUnblocked();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.opinionated.OccurencesMonitorBase", e);
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      OccurencesMonitor occurencesMonitor0 = new OccurencesMonitor();
      // Undeclared exception!
      try { 
        occurencesMonitor0.taskBlocked("tcp://localhost:61616");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.opinionated.OccurencesMonitorBase", e);
      }
  }
}
