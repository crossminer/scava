/*
 * This file was automatically generated by EvoSuite
 * Tue Oct 23 17:10:21 GMT 2018
 */

package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.jms.JMSException;
import org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.MdeTechnologyExample;
import org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.MdeTechnologyRepoOwnerPopularityCountEntries;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class MdeTechnologyRepoOwnerPopularityCountEntries_ESTest extends MdeTechnologyRepoOwnerPopularityCountEntries_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
    Future<?> future = executor.submit(new Runnable(){ 
            @Override public void run() { 
        try {
          MdeTechnologyExample mdeTechnologyExample0 = new MdeTechnologyExample();
          MdeTechnologyRepoOwnerPopularityCountEntries mdeTechnologyRepoOwnerPopularityCountEntries0 = null;
          try {
            mdeTechnologyRepoOwnerPopularityCountEntries0 = new MdeTechnologyRepoOwnerPopularityCountEntries(mdeTechnologyExample0);
            fail("Expecting exception: JMSException");
          
          } catch(Throwable e) {
             //
             // Could not connect to broker URL: tcp://localhost:61616. Reason: java.lang.SecurityException: Security manager blocks (\"java.net.SocketPermission\" \"200.42.42.0:61616\" \"connect,resolve\")
             // java.lang.Thread.getStackTrace(Thread.java:1559)
             // org.evosuite.runtime.sandbox.MSecurityManager.checkPermission(MSecurityManager.java:434)
             // java.lang.SecurityManager.checkConnect(SecurityManager.java:1051)
             // java.net.Socket.connect(Socket.java:584)
             // org.apache.activemq.transport.tcp.TcpTransport.connect(TcpTransport.java:525)
             // org.apache.activemq.transport.tcp.TcpTransport.doStart(TcpTransport.java:488)
             // org.apache.activemq.util.ServiceSupport.start(ServiceSupport.java:55)
             // org.apache.activemq.transport.AbstractInactivityMonitor.start(AbstractInactivityMonitor.java:169)
             // org.apache.activemq.transport.InactivityMonitor.start(InactivityMonitor.java:52)
             // org.apache.activemq.transport.TransportFilter.start(TransportFilter.java:64)
             // org.apache.activemq.transport.WireFormatNegotiator.start(WireFormatNegotiator.java:72)
             // org.apache.activemq.transport.TransportFilter.start(TransportFilter.java:64)
             // org.apache.activemq.transport.TransportFilter.start(TransportFilter.java:64)
             // org.apache.activemq.ActiveMQConnectionFactory.createActiveMQConnection(ActiveMQConnectionFactory.java:354)
             // org.apache.activemq.ActiveMQConnectionFactory.createActiveMQConnection(ActiveMQConnectionFactory.java:304)
             // org.apache.activemq.ActiveMQConnectionFactory.createConnection(ActiveMQConnectionFactory.java:244)
             // org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.MdeTechnologyRepoOwnerPopularityCountEntries.<init>(MdeTechnologyRepoOwnerPopularityCountEntries.java:31)
             // sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
             // sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
             // sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
             // java.lang.reflect.Constructor.newInstance(Constructor.java:423)
             // org.evosuite.testcase.statements.ConstructorStatement$1.execute(ConstructorStatement.java:233)
             // org.evosuite.testcase.statements.AbstractStatement.exceptionHandler(AbstractStatement.java:169)
             // org.evosuite.testcase.statements.ConstructorStatement.execute(ConstructorStatement.java:188)
             // org.evosuite.testcase.execution.TestRunnable.executeStatements(TestRunnable.java:307)
             // org.evosuite.testcase.execution.TestRunnable.call(TestRunnable.java:213)
             // org.evosuite.testcase.execution.TestRunnable.call(TestRunnable.java:55)
             // java.util.concurrent.FutureTask.run(FutureTask.java:266)
             // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
             // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
             // java.lang.Thread.run(Thread.java:748)
             //
             verifyException("org.apache.activemq.util.JMSExceptionSupport", e);
          }
        } catch(Throwable t) {
            // Need to catch declared exceptions
        }
      } 
    });
    future.get(4000, TimeUnit.MILLISECONDS);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      MdeTechnologyRepoOwnerPopularityCountEntries mdeTechnologyRepoOwnerPopularityCountEntries0 = null;
      try {
        mdeTechnologyRepoOwnerPopularityCountEntries0 = new MdeTechnologyRepoOwnerPopularityCountEntries((Workflow) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.MdeTechnologyRepoOwnerPopularityCountEntries", e);
      }
  }
}
