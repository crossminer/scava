/*
 * This file was automatically generated by EvoSuite
 * Tue Oct 23 17:47:40 GMT 2018
 */

package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.File;
import org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.CloneUtils;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class CloneUtils_ESTest extends CloneUtils_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      String string0 = CloneUtils.createUniqueFolderForRepo("\u0000\u05BE\u05D0\u05F3\u0600\u0750\u0E00\u1E00\u2100\uFB50\uFE70\uFF61");
      assertEquals("\u05BE\u05D0\u05F3\u0600\u0750\u0E00\u1E00\u2100\uFB50\uFE70\uFF61-c4f877ef7874ae97b708ee171bdcbede08625e44", string0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      MockFile mockFile0 = new MockFile("cSsAxz:");
      mockFile0.mkdirs();
      mockFile0.setWritable(false, true);
      CloneUtils.removeRepoClones(mockFile0);
      assertFalse(mockFile0.isAbsolute());
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      String string0 = CloneUtils.extractGhRepoOwner("https://github.com/eclipse/epsilon");
      assertEquals("eclipse", string0);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      String string0 = CloneUtils.extractGhRepoName("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      String string0 = CloneUtils.cleanFileName("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      // Undeclared exception!
      try { 
        CloneUtils.removeRepoClones((File) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.CloneUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      File file0 = MockFile.createTempFile("vNecvm3G~L;yos$", (String) null);
      // Undeclared exception!
      try { 
        CloneUtils.removeRepoClones(file0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // /var/folders/yb/3xl78g7n3wg_96qmk9kf___c0000gp/T/vNecvm3G~L;yos$0.tmp is not a directory
         //
         verifyException("org.apache.commons.io.FileUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      // Undeclared exception!
      try { 
        CloneUtils.extractGhRepoOwner((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.CloneUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      // Undeclared exception!
      try { 
        CloneUtils.extractGhRepoName((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.CloneUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      // Undeclared exception!
      try { 
        CloneUtils.createUniqueFolderForRepo((String) null, "D~fjPJM");
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.CloneUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      // Undeclared exception!
      try { 
        CloneUtils.createUniqueFolderForRepo((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.CloneUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      // Undeclared exception!
      try { 
        CloneUtils.cleanFileName((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.CloneUtils", e);
      }
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      String string0 = CloneUtils.cleanFileName("Could not create BloomFilter of ");
      assertEquals("Could not create BloomFilter of", string0);
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      String string0 = CloneUtils.cleanFileName("https://github.com/eclipse/epsilon");
      assertEquals("httpsgithub.comeclipseepsilon", string0);
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      String string0 = CloneUtils.extractGhRepoName("f");
      assertEquals("f", string0);
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      // Undeclared exception!
      try { 
        CloneUtils.extractGhRepoOwner("cSsAxz:");
        fail("Expecting exception: StringIndexOutOfBoundsException");
      
      } catch(StringIndexOutOfBoundsException e) {
      }
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      MockFile mockFile0 = new MockFile("DRh4!ZUbxfI&jtz-5cae1a8217dc870a9502fn47<418`b3fd0 ");
      MockFile.createTempFile("DRh4!ZUbxfI&jtz-5cae1a8217dc870a9502fn47<418`b3fd0 ", "DRh4!ZUbxfI&jtz-5cae1a8217dc870a9502fn4774418`b3fd0-ef1329f139da49dea887de90602bf6a7cdc30742", (File) mockFile0);
      CloneUtils.removeRepoClones(mockFile0);
      assertFalse(mockFile0.isDirectory());
      assertFalse(mockFile0.canWrite());
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      MockFile mockFile0 = new MockFile("DRh4!ZUbxfI&jtz-5cae1a8217dc870a9502fn47<418`b3fd0 ");
      CloneUtils.removeRepoClones(mockFile0);
      assertFalse(mockFile0.canRead());
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      String string0 = CloneUtils.createUniqueFolderForRepo("DRh4!ZUbxfI&jtz-5cae1a8217dc870a9502fn47<418`b3fd0 ", "DRh4!ZUbxfI&jtz-5cae1a8217dc870a9502fn47<418`b3fd0 ");
      assertEquals("DRh4!ZUbxfI&jtz-5cae1a8217dc870a9502fn47418`b3fd0-ad58b96038cc794f18f79146c0ec0ec3c7a578ca", string0);
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      String string0 = CloneUtils.cleanFileName("DRh4!ZUbfI&jYz-5cae11S217dc870a95023n477 418`b3fd0.");
      assertEquals("DRh4!ZUbfI&jYz-5cae11S217dc870a95023n477 418`b3fd0", string0);
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      String string0 = CloneUtils.createUniqueFolderForRepo("ixK{?8DRR/!j", "");
      assertEquals("ixK{8DRR!j-be1bdec0aa74b4dcb079943e70528096cca985f8", string0);
  }

  @Test(timeout = 4000)
  public void test21()  throws Throwable  {
      CloneUtils cloneUtils0 = new CloneUtils();
  }

  @Test(timeout = 4000)
  public void test22()  throws Throwable  {
      String[] stringArray0 = new String[4];
      CloneUtils.main(stringArray0);
      assertEquals(4, stringArray0.length);
  }
}
