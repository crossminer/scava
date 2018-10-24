/*
 * This file was automatically generated by EvoSuite
 * Tue Oct 23 17:08:27 GMT 2018
 */

package org.eclipse.scava.crossflow.examples.firstcommitment;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Locale;
import java.util.Set;
import org.eclipse.scava.crossflow.examples.firstcommitment.Animal;
import org.eclipse.scava.crossflow.examples.firstcommitment.AnimalCounter;
import org.eclipse.scava.crossflow.examples.firstcommitment.FirstCommitmentExample;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class AnimalCounter_ESTest extends AnimalCounter_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      AnimalCounter animalCounter0 = new AnimalCounter();
      animalCounter0.favouriteAnimal = "";
      Animal animal0 = new Animal();
      FirstCommitmentExample firstCommitmentExample0 = new FirstCommitmentExample();
      animalCounter0.workflow = firstCommitmentExample0;
      animal0.name = "";
      animalCounter0.consumeAnimals(animal0);
      assertFalse(animal0.isCached());
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      AnimalCounter animalCounter0 = new AnimalCounter();
      Locale locale0 = Locale.JAPAN;
      Set<String> set0 = locale0.getUnicodeLocaleKeys();
      animalCounter0.alreadySeenJobs = set0;
      Animal animal0 = new Animal();
      // Undeclared exception!
      try { 
        animalCounter0.consumeAnimals(animal0);
        fail("Expecting exception: UnsupportedOperationException");
      
      } catch(UnsupportedOperationException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.util.AbstractCollection", e);
      }
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      AnimalCounter animalCounter0 = new AnimalCounter();
      animalCounter0.favouriteAnimal = "";
      Animal animal0 = new Animal();
      animal0.name = "";
      // Undeclared exception!
      try { 
        animalCounter0.consumeAnimals(animal0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.firstcommitment.AnimalCounter", e);
      }
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      AnimalCounter animalCounter0 = new AnimalCounter();
      Animal animal0 = new Animal();
      animalCounter0.favouriteAnimal = "I";
      animal0.name = ",_LiMSbR";
      // Undeclared exception!
      try { 
        animalCounter0.consumeAnimals(animal0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.firstcommitment.AnimalCounter", e);
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      AnimalCounter animalCounter0 = new AnimalCounter();
      Animal animal0 = new Animal();
      // Undeclared exception!
      try { 
        animalCounter0.consumeAnimals(animal0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.firstcommitment.AnimalCounter", e);
      }
  }
}
