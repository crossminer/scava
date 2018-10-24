/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Tue Oct 23 17:11:34 GMT 2018
 */

package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class Result_ESTest_scaffolding {

  @org.junit.Rule 
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass 
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.Result"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    org.evosuite.runtime.RuntimeSettings.sandboxMode = org.evosuite.runtime.sandbox.Sandbox.SandboxMode.RECOMMENDED; 
    org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.init();
    setSystemProperties();
    initializeClasses();
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @AfterClass 
  public static void clearEvoSuiteFramework(){ 
    Sandbox.resetDefaultSecurityManager(); 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
  } 

  @Before 
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    setSystemProperties(); 
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @After 
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.reset(); 
    resetClasses(); 
    org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public static void setSystemProperties() {
 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
    java.lang.System.setProperty("user.dir", "/Users/blizzfire/REPOS/CROSSMINER-REPOS/CROSSMINER-PUBLIC/scava-crossflow/crossflow/org.eclipse.scava.crossflow.test"); 
    java.lang.System.setProperty("java.io.tmpdir", "/var/folders/yb/3xl78g7n3wg_96qmk9kf___c0000gp/T/"); 
  }

  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(Result_ESTest_scaffolding.class.getClassLoader() ,
      "com.thoughtworks.xstream.XStream",
      "com.thoughtworks.xstream.converters.extended.StackTraceElementFactory",
      "com.thoughtworks.xstream.core.util.OrderRetainingMap$1",
      "com.thoughtworks.xstream.converters.basic.StringBuilderConverter",
      "com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter$ArraysList",
      "com.thoughtworks.xstream.XStream$InitializationException",
      "com.thoughtworks.xstream.mapper.SystemAttributeAliasingMapper",
      "com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter",
      "com.thoughtworks.xstream.converters.ErrorReporter",
      "com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder$1IntPairList",
      "com.thoughtworks.xstream.core.util.PresortedMap$ArraySetComparator",
      "com.thoughtworks.xstream.converters.extended.CurrencyConverter",
      "com.thoughtworks.xstream.converters.Converter",
      "com.thoughtworks.xstream.core.util.Base64Encoder",
      "com.thoughtworks.xstream.core.util.Cloneables",
      "com.thoughtworks.xstream.security.TypePermission",
      "com.thoughtworks.xstream.converters.basic.BigDecimalConverter",
      "com.thoughtworks.xstream.converters.SingleValueConverter",
      "com.thoughtworks.xstream.converters.reflection.ReflectionProvider$Visitor",
      "com.thoughtworks.xstream.converters.MarshallingContext",
      "org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.Result",
      "com.thoughtworks.xstream.mapper.OuterClassMapper",
      "com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder$IntPair",
      "com.thoughtworks.xstream.converters.reflection.SerializableConverter$UnserializableParentsReflectionProvider",
      "com.thoughtworks.xstream.io.HierarchicalStreamReader",
      "com.thoughtworks.xstream.core.util.CompositeClassLoader",
      "com.thoughtworks.xstream.converters.ConverterLookup",
      "com.thoughtworks.xstream.mapper.ClassAliasingMapper",
      "com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder",
      "com.thoughtworks.xstream.mapper.CannotResolveClassException",
      "com.thoughtworks.xstream.converters.reflection.FieldDictionary",
      "com.thoughtworks.xstream.io.naming.NameCoder",
      "com.thoughtworks.xstream.mapper.EnumMapper",
      "com.thoughtworks.xstream.mapper.AnnotationConfiguration",
      "com.thoughtworks.xstream.io.xml.XmlFriendlyWriter",
      "com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider$1",
      "com.thoughtworks.xstream.core.util.FastStack",
      "com.thoughtworks.xstream.converters.ConversionException",
      "com.thoughtworks.xstream.core.TreeMarshaller$CircularReferenceException",
      "com.thoughtworks.xstream.mapper.MapperWrapper",
      "com.thoughtworks.xstream.core.AbstractReferenceMarshaller$Id",
      "com.thoughtworks.xstream.mapper.Mapper",
      "com.thoughtworks.xstream.converters.extended.FileConverter",
      "com.thoughtworks.xstream.core.util.PrioritizedList$PrioritizedItem",
      "com.thoughtworks.xstream.core.util.ObjectIdDictionary",
      "com.thoughtworks.xstream.converters.extended.SqlTimestampConverter",
      "com.thoughtworks.xstream.converters.UnmarshallingContext",
      "com.thoughtworks.xstream.core.util.SelfStreamingInstanceChecker",
      "com.thoughtworks.xstream.mapper.SecurityMapper",
      "com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter$UnknownFieldException",
      "com.thoughtworks.xstream.converters.extended.ColorConverter",
      "com.thoughtworks.xstream.converters.extended.DurationConverter",
      "com.thoughtworks.xstream.converters.basic.DoubleConverter",
      "com.thoughtworks.xstream.converters.collections.CharArrayConverter",
      "com.thoughtworks.xstream.converters.extended.LookAndFeelConverter",
      "com.thoughtworks.xstream.converters.collections.ArrayConverter",
      "com.thoughtworks.xstream.core.util.TypedNull",
      "com.thoughtworks.xstream.converters.extended.JavaMethodConverter",
      "com.thoughtworks.xstream.converters.enums.EnumConverter",
      "com.thoughtworks.xstream.converters.collections.MapConverter",
      "com.thoughtworks.xstream.converters.collections.TreeSetConverter$1",
      "com.thoughtworks.xstream.core.util.Primitives",
      "com.thoughtworks.xstream.security.NoTypePermission",
      "com.thoughtworks.xstream.io.HierarchicalStreamWriter",
      "com.thoughtworks.xstream.converters.extended.DynamicProxyConverter$1",
      "com.thoughtworks.xstream.core.util.WeakCache",
      "com.thoughtworks.xstream.core.util.WeakCache$Visitor",
      "com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter",
      "com.thoughtworks.xstream.core.AbstractReferenceUnmarshaller",
      "com.thoughtworks.xstream.core.AbstractReferenceMarshaller$1",
      "com.thoughtworks.xstream.core.JVM$Test",
      "com.thoughtworks.xstream.converters.reflection.SunLimitedUnsafeReflectionProvider",
      "com.thoughtworks.xstream.core.util.CustomObjectOutputStream",
      "com.thoughtworks.xstream.core.util.OrderRetainingMap",
      "com.thoughtworks.xstream.mapper.DynamicProxyMapper",
      "com.thoughtworks.xstream.converters.enums.EnumMapConverter",
      "com.thoughtworks.xstream.XStream$2",
      "com.thoughtworks.xstream.XStream$1",
      "com.thoughtworks.xstream.converters.basic.ByteConverter",
      "com.thoughtworks.xstream.converters.collections.TreeSetConverter",
      "com.thoughtworks.xstream.converters.extended.LocaleConverter",
      "com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper",
      "com.thoughtworks.xstream.core.BaseException",
      "com.thoughtworks.xstream.converters.collections.PropertiesConverter",
      "com.thoughtworks.xstream.core.TreeMarshaller",
      "com.thoughtworks.xstream.converters.ConverterRegistry",
      "com.thoughtworks.xstream.converters.collections.TreeMapConverter$1",
      "com.thoughtworks.xstream.core.TreeUnmarshaller",
      "com.thoughtworks.xstream.core.ReferenceByXPathMarshaller",
      "com.thoughtworks.xstream.mapper.AnnotationMapper",
      "com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter$MappingList",
      "com.thoughtworks.xstream.converters.extended.JavaFieldConverter",
      "com.thoughtworks.xstream.converters.basic.UUIDConverter",
      "com.thoughtworks.xstream.InitializationException",
      "com.thoughtworks.xstream.converters.extended.SubjectConverter",
      "com.thoughtworks.xstream.mapper.DefaultMapper",
      "com.thoughtworks.xstream.core.util.PresortedMap",
      "com.thoughtworks.xstream.mapper.PackageAliasingMapper$1",
      "com.thoughtworks.xstream.converters.basic.StringConverter",
      "com.thoughtworks.xstream.mapper.ImmutableTypesMapper",
      "com.thoughtworks.xstream.core.AbstractReferenceMarshaller$ReferencedImplicitElementException",
      "com.thoughtworks.xstream.converters.collections.BitSetConverter",
      "com.thoughtworks.xstream.core.util.CustomObjectInputStream$CustomGetField",
      "com.thoughtworks.xstream.converters.extended.ThrowableConverter",
      "com.thoughtworks.xstream.converters.reflection.ObjectAccessException",
      "com.thoughtworks.xstream.converters.extended.SqlTimeConverter",
      "com.thoughtworks.xstream.converters.extended.DurationConverter$1",
      "com.thoughtworks.xstream.mapper.AttributeMapper",
      "com.thoughtworks.xstream.security.AnyTypePermission",
      "com.thoughtworks.xstream.mapper.AttributeAliasingMapper",
      "com.thoughtworks.xstream.mapper.ImplicitCollectionMapper$ImplicitCollectionMapperForClass",
      "com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider",
      "com.thoughtworks.xstream.security.ForbiddenClassException",
      "com.thoughtworks.xstream.io.WriterWrapper",
      "com.thoughtworks.xstream.mapper.CachingMapper",
      "com.thoughtworks.xstream.converters.extended.RegexPatternConverter",
      "com.thoughtworks.xstream.core.util.ObjectIdDictionary$WeakIdWrapper",
      "com.thoughtworks.xstream.MarshallingStrategy",
      "com.thoughtworks.xstream.core.util.OrderRetainingMap$ArraySet",
      "com.thoughtworks.xstream.converters.reflection.SerializableConverter",
      "com.thoughtworks.xstream.mapper.Mapper$ImplicitCollectionMapping",
      "com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter$FieldInfo",
      "com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter$2",
      "com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter$1",
      "com.thoughtworks.xstream.converters.basic.CharConverter",
      "com.thoughtworks.xstream.core.util.DependencyInjectionFactory",
      "com.thoughtworks.xstream.core.util.PrioritizedList$PrioritizedItemIterator",
      "com.thoughtworks.xstream.converters.reflection.SerializationMethodInvoker$1",
      "com.thoughtworks.xstream.io.xml.AbstractXmlWriter",
      "com.thoughtworks.xstream.converters.reflection.FieldKey",
      "com.thoughtworks.xstream.core.DefaultConverterLookup",
      "com.thoughtworks.xstream.core.JVM$1",
      "com.thoughtworks.xstream.core.AbstractReferenceMarshaller",
      "com.thoughtworks.xstream.converters.reflection.SerializationMethodInvoker",
      "com.thoughtworks.xstream.converters.reflection.MissingFieldException",
      "com.thoughtworks.xstream.core.util.CustomObjectOutputStream$CustomPutField",
      "com.thoughtworks.xstream.converters.basic.ShortConverter",
      "com.thoughtworks.xstream.converters.basic.URIConverter",
      "com.thoughtworks.xstream.core.JVM",
      "com.thoughtworks.xstream.XStreamException",
      "com.thoughtworks.xstream.io.xml.AbstractXmlDriver",
      "com.thoughtworks.xstream.converters.basic.BigIntegerConverter",
      "com.thoughtworks.xstream.converters.ConverterMatcher",
      "com.thoughtworks.xstream.converters.basic.StringBufferConverter",
      "com.thoughtworks.xstream.mapper.AbstractAttributeAliasingMapper",
      "com.thoughtworks.xstream.io.AbstractDriver",
      "com.thoughtworks.xstream.core.ReferencingMarshallingContext",
      "com.thoughtworks.xstream.core.util.FastField",
      "com.thoughtworks.xstream.core.util.CustomObjectOutputStream$StreamCallback",
      "com.thoughtworks.xstream.core.util.ThreadSafeSimpleDateFormat$1",
      "com.thoughtworks.xstream.converters.reflection.AbstractAttributedCharacterIteratorAttributeConverter",
      "com.thoughtworks.xstream.mapper.FieldAliasingMapper",
      "com.thoughtworks.xstream.converters.basic.IntConverter",
      "com.thoughtworks.xstream.converters.extended.StackTraceElementConverter",
      "com.thoughtworks.xstream.core.util.Pool$Factory",
      "com.thoughtworks.xstream.converters.extended.JavaClassConverter",
      "com.thoughtworks.xstream.core.AbstractTreeMarshallingStrategy",
      "com.thoughtworks.xstream.io.AbstractWriter",
      "com.thoughtworks.xstream.converters.basic.DateConverter",
      "com.thoughtworks.xstream.core.util.CustomObjectInputStream$StreamCallback",
      "com.thoughtworks.xstream.converters.SingleValueConverterWrapper",
      "com.thoughtworks.xstream.converters.reflection.ReflectionConverter",
      "com.thoughtworks.xstream.io.StreamException",
      "com.thoughtworks.xstream.converters.basic.LongConverter",
      "com.thoughtworks.xstream.converters.extended.GregorianCalendarConverter",
      "com.thoughtworks.xstream.converters.extended.TextAttributeConverter",
      "com.thoughtworks.xstream.mapper.ImplicitCollectionMapper$ImplicitCollectionMappingImpl",
      "com.thoughtworks.xstream.core.util.PresortedMap$ArraySet",
      "com.thoughtworks.xstream.converters.extended.CharsetConverter",
      "com.thoughtworks.xstream.io.path.Path",
      "com.thoughtworks.xstream.converters.collections.TreeMapConverter$NullComparator",
      "com.thoughtworks.xstream.core.util.ObjectIdDictionary$IdWrapper",
      "com.thoughtworks.xstream.converters.reflection.ImmutableFieldKeySorter",
      "com.thoughtworks.xstream.converters.enums.EnumSetConverter",
      "com.thoughtworks.xstream.converters.collections.SingletonCollectionConverter",
      "com.thoughtworks.xstream.mapper.AbstractXmlFriendlyMapper",
      "com.thoughtworks.xstream.mapper.ImplicitCollectionMapper$NamedItemType",
      "com.thoughtworks.xstream.core.util.Fields",
      "com.thoughtworks.xstream.mapper.Mapper$Null",
      "com.thoughtworks.xstream.annotations.XStreamConverter",
      "com.thoughtworks.xstream.core.util.ThreadSafeSimpleDateFormat",
      "com.thoughtworks.xstream.core.ReferenceByXPathUnmarshaller",
      "com.thoughtworks.xstream.converters.reflection.ExternalizableConverter",
      "com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter",
      "com.thoughtworks.xstream.mapper.ImplicitCollectionMapper",
      "com.thoughtworks.xstream.converters.DataHolder",
      "com.thoughtworks.xstream.core.util.CustomObjectInputStream",
      "com.thoughtworks.xstream.core.Caching",
      "com.thoughtworks.xstream.converters.extended.DynamicProxyConverter",
      "com.thoughtworks.xstream.core.util.QuickWriter",
      "com.thoughtworks.xstream.mapper.ArrayMapper",
      "com.thoughtworks.xstream.converters.extended.StackTraceElementFactory15",
      "com.thoughtworks.xstream.converters.basic.NullConverter",
      "com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer",
      "com.thoughtworks.xstream.converters.basic.FloatConverter",
      "com.thoughtworks.xstream.converters.extended.SqlDateConverter",
      "com.thoughtworks.xstream.converters.extended.FontConverter",
      "com.thoughtworks.xstream.core.ClassLoaderReference",
      "com.thoughtworks.xstream.converters.collections.TreeMapConverter",
      "com.thoughtworks.xstream.core.util.ObjectIdDictionary$Wrapper",
      "com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriter",
      "com.thoughtworks.xstream.core.util.PresortedSet",
      "com.thoughtworks.xstream.core.ReferenceByXPathMarshallingStrategy",
      "com.thoughtworks.xstream.mapper.DefaultImplementationsMapper",
      "com.thoughtworks.xstream.core.util.PresortedMap$1",
      "com.thoughtworks.xstream.converters.reflection.SunUnsafeReflectionProvider",
      "com.thoughtworks.xstream.mapper.PackageAliasingMapper",
      "com.thoughtworks.xstream.converters.reflection.ReflectionProvider",
      "com.thoughtworks.xstream.converters.reflection.FieldKeySorter",
      "com.thoughtworks.xstream.mapper.LocalConversionMapper",
      "com.thoughtworks.xstream.converters.collections.SingletonMapConverter",
      "org.eclipse.scava.crossflow.runtime.Job",
      "com.thoughtworks.xstream.io.HierarchicalStreamDriver",
      "com.thoughtworks.xstream.io.path.PathTrackingWriter",
      "com.thoughtworks.xstream.converters.reflection.ReflectionProviderWrapper",
      "com.thoughtworks.xstream.core.util.Pool",
      "com.thoughtworks.xstream.core.util.ArrayIterator",
      "com.thoughtworks.xstream.converters.ErrorWriter",
      "com.thoughtworks.xstream.core.util.CompositeClassLoader$1",
      "com.thoughtworks.xstream.mapper.XStream11XmlFriendlyMapper",
      "com.thoughtworks.xstream.converters.basic.URLConverter",
      "com.thoughtworks.xstream.io.xml.PrettyPrintWriter",
      "com.thoughtworks.xstream.core.util.PrioritizedList",
      "com.thoughtworks.xstream.io.path.PathTracker",
      "com.thoughtworks.xstream.io.xml.DomDriver",
      "com.thoughtworks.xstream.converters.collections.CollectionConverter",
      "com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter",
      "com.thoughtworks.xstream.converters.basic.BooleanConverter"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(Result_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "org.eclipse.scava.crossflow.runtime.Job",
      "org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.Result",
      "com.thoughtworks.xstream.XStream",
      "com.thoughtworks.xstream.io.AbstractDriver",
      "com.thoughtworks.xstream.io.xml.AbstractXmlDriver",
      "com.thoughtworks.xstream.io.xml.DomDriver",
      "com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder$1IntPairList",
      "com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder$IntPair",
      "com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder",
      "com.thoughtworks.xstream.core.JVM$Test",
      "com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider",
      "com.thoughtworks.xstream.converters.reflection.SunLimitedUnsafeReflectionProvider",
      "com.thoughtworks.xstream.converters.reflection.SunUnsafeReflectionProvider",
      "com.thoughtworks.xstream.core.util.DependencyInjectionFactory",
      "com.thoughtworks.xstream.converters.reflection.FieldDictionary",
      "com.thoughtworks.xstream.converters.reflection.ImmutableFieldKeySorter",
      "com.thoughtworks.xstream.core.util.OrderRetainingMap",
      "com.thoughtworks.xstream.core.util.OrderRetainingMap$ArraySet",
      "com.thoughtworks.xstream.converters.reflection.FieldKey",
      "com.thoughtworks.xstream.core.JVM$1",
      "com.thoughtworks.xstream.core.util.PresortedMap",
      "com.thoughtworks.xstream.core.util.PresortedMap$ArraySet",
      "com.thoughtworks.xstream.core.util.PresortedMap$1",
      "com.thoughtworks.xstream.core.util.PresortedSet",
      "com.thoughtworks.xstream.core.util.CustomObjectOutputStream",
      "com.thoughtworks.xstream.core.util.FastStack",
      "com.thoughtworks.xstream.core.JVM",
      "com.thoughtworks.xstream.core.util.CompositeClassLoader",
      "com.thoughtworks.xstream.core.ClassLoaderReference",
      "com.thoughtworks.xstream.core.DefaultConverterLookup",
      "com.thoughtworks.xstream.core.util.PrioritizedList",
      "com.thoughtworks.xstream.XStream$1",
      "com.thoughtworks.xstream.XStream$2",
      "com.thoughtworks.xstream.mapper.DefaultMapper",
      "com.thoughtworks.xstream.mapper.MapperWrapper",
      "com.thoughtworks.xstream.mapper.DynamicProxyMapper",
      "com.thoughtworks.xstream.mapper.PackageAliasingMapper$1",
      "com.thoughtworks.xstream.mapper.PackageAliasingMapper",
      "com.thoughtworks.xstream.mapper.ClassAliasingMapper",
      "com.thoughtworks.xstream.mapper.FieldAliasingMapper",
      "com.thoughtworks.xstream.mapper.AbstractAttributeAliasingMapper",
      "com.thoughtworks.xstream.mapper.AttributeAliasingMapper",
      "com.thoughtworks.xstream.mapper.SystemAttributeAliasingMapper",
      "com.thoughtworks.xstream.mapper.ImplicitCollectionMapper",
      "com.thoughtworks.xstream.mapper.OuterClassMapper",
      "com.thoughtworks.xstream.mapper.ArrayMapper",
      "com.thoughtworks.xstream.mapper.DefaultImplementationsMapper",
      "com.thoughtworks.xstream.mapper.AttributeMapper",
      "com.thoughtworks.xstream.core.util.CompositeClassLoader$1",
      "com.thoughtworks.xstream.mapper.EnumMapper",
      "com.thoughtworks.xstream.mapper.LocalConversionMapper",
      "com.thoughtworks.xstream.mapper.ImmutableTypesMapper",
      "com.thoughtworks.xstream.mapper.SecurityMapper",
      "com.thoughtworks.xstream.mapper.AnnotationMapper",
      "com.thoughtworks.xstream.mapper.CachingMapper",
      "com.thoughtworks.xstream.security.AnyTypePermission",
      "com.thoughtworks.xstream.security.NoTypePermission",
      "com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter",
      "com.thoughtworks.xstream.converters.reflection.ReflectionConverter",
      "com.thoughtworks.xstream.converters.reflection.SerializationMethodInvoker$1",
      "com.thoughtworks.xstream.core.util.FastField",
      "com.thoughtworks.xstream.converters.reflection.SerializationMethodInvoker",
      "com.thoughtworks.xstream.core.util.PrioritizedList$PrioritizedItem",
      "com.thoughtworks.xstream.converters.reflection.SerializableConverter",
      "com.thoughtworks.xstream.converters.reflection.ReflectionProviderWrapper",
      "com.thoughtworks.xstream.converters.reflection.SerializableConverter$UnserializableParentsReflectionProvider",
      "com.thoughtworks.xstream.converters.reflection.ExternalizableConverter",
      "com.thoughtworks.xstream.converters.basic.NullConverter",
      "com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter",
      "com.thoughtworks.xstream.converters.basic.IntConverter",
      "com.thoughtworks.xstream.converters.SingleValueConverterWrapper",
      "com.thoughtworks.xstream.converters.basic.FloatConverter",
      "com.thoughtworks.xstream.converters.basic.DoubleConverter",
      "com.thoughtworks.xstream.converters.basic.LongConverter",
      "com.thoughtworks.xstream.converters.basic.ShortConverter",
      "com.thoughtworks.xstream.converters.basic.CharConverter",
      "com.thoughtworks.xstream.converters.basic.BooleanConverter",
      "com.thoughtworks.xstream.converters.basic.ByteConverter",
      "com.thoughtworks.xstream.converters.basic.StringConverter",
      "com.thoughtworks.xstream.core.util.WeakCache",
      "com.thoughtworks.xstream.converters.basic.StringBufferConverter",
      "com.thoughtworks.xstream.converters.basic.DateConverter",
      "com.thoughtworks.xstream.core.util.ThreadSafeSimpleDateFormat",
      "com.thoughtworks.xstream.core.util.Pool",
      "com.thoughtworks.xstream.core.util.ThreadSafeSimpleDateFormat$1",
      "com.thoughtworks.xstream.converters.collections.BitSetConverter",
      "com.thoughtworks.xstream.converters.basic.URIConverter",
      "com.thoughtworks.xstream.converters.basic.URLConverter",
      "com.thoughtworks.xstream.converters.basic.BigIntegerConverter",
      "com.thoughtworks.xstream.converters.basic.BigDecimalConverter",
      "com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter",
      "com.thoughtworks.xstream.converters.collections.ArrayConverter",
      "com.thoughtworks.xstream.converters.collections.CharArrayConverter",
      "com.thoughtworks.xstream.converters.collections.CollectionConverter",
      "com.thoughtworks.xstream.converters.collections.MapConverter",
      "com.thoughtworks.xstream.mapper.Mapper$Null",
      "com.thoughtworks.xstream.converters.collections.TreeMapConverter$NullComparator",
      "com.thoughtworks.xstream.core.util.Fields",
      "com.thoughtworks.xstream.converters.collections.TreeMapConverter",
      "com.thoughtworks.xstream.converters.collections.TreeSetConverter",
      "com.thoughtworks.xstream.converters.collections.TreeSetConverter$1",
      "com.thoughtworks.xstream.converters.collections.SingletonCollectionConverter",
      "com.thoughtworks.xstream.converters.collections.SingletonMapConverter",
      "com.thoughtworks.xstream.converters.collections.PropertiesConverter",
      "com.thoughtworks.xstream.core.util.Base64Encoder",
      "com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter",
      "com.thoughtworks.xstream.converters.extended.FileConverter",
      "com.thoughtworks.xstream.converters.extended.SqlTimestampConverter",
      "com.thoughtworks.xstream.converters.extended.SqlTimeConverter",
      "com.thoughtworks.xstream.converters.extended.SqlDateConverter",
      "com.thoughtworks.xstream.converters.extended.DynamicProxyConverter$1",
      "com.thoughtworks.xstream.converters.extended.DynamicProxyConverter",
      "com.thoughtworks.xstream.converters.extended.JavaClassConverter",
      "com.thoughtworks.xstream.converters.extended.JavaMethodConverter",
      "com.thoughtworks.xstream.converters.extended.JavaFieldConverter",
      "com.thoughtworks.xstream.converters.extended.FontConverter",
      "com.thoughtworks.xstream.converters.reflection.AbstractAttributedCharacterIteratorAttributeConverter",
      "com.thoughtworks.xstream.converters.extended.TextAttributeConverter",
      "com.thoughtworks.xstream.converters.extended.ColorConverter",
      "com.thoughtworks.xstream.converters.extended.LookAndFeelConverter",
      "com.thoughtworks.xstream.converters.extended.LocaleConverter",
      "com.thoughtworks.xstream.converters.extended.GregorianCalendarConverter",
      "com.thoughtworks.xstream.converters.extended.SubjectConverter",
      "com.thoughtworks.xstream.converters.extended.ThrowableConverter",
      "com.thoughtworks.xstream.converters.extended.StackTraceElementFactory",
      "com.thoughtworks.xstream.converters.extended.StackTraceElementFactory15",
      "com.thoughtworks.xstream.converters.extended.StackTraceElementConverter",
      "com.thoughtworks.xstream.converters.extended.CurrencyConverter",
      "com.thoughtworks.xstream.converters.extended.RegexPatternConverter",
      "com.thoughtworks.xstream.converters.extended.CharsetConverter",
      "com.thoughtworks.xstream.converters.extended.DurationConverter",
      "com.thoughtworks.xstream.converters.extended.DurationConverter$1",
      "com.thoughtworks.xstream.converters.enums.EnumConverter",
      "com.thoughtworks.xstream.converters.enums.EnumSetConverter",
      "com.thoughtworks.xstream.converters.enums.EnumMapConverter",
      "com.thoughtworks.xstream.converters.basic.StringBuilderConverter",
      "com.thoughtworks.xstream.converters.basic.UUIDConverter",
      "com.thoughtworks.xstream.core.util.SelfStreamingInstanceChecker",
      "com.thoughtworks.xstream.core.AbstractTreeMarshallingStrategy",
      "com.thoughtworks.xstream.core.ReferenceByXPathMarshallingStrategy",
      "com.thoughtworks.xstream.io.AbstractWriter",
      "com.thoughtworks.xstream.io.xml.AbstractXmlWriter",
      "com.thoughtworks.xstream.io.xml.PrettyPrintWriter",
      "com.thoughtworks.xstream.core.util.Cloneables",
      "com.thoughtworks.xstream.core.util.QuickWriter",
      "com.thoughtworks.xstream.core.TreeMarshaller",
      "com.thoughtworks.xstream.core.AbstractReferenceMarshaller",
      "com.thoughtworks.xstream.core.ReferenceByXPathMarshaller",
      "com.thoughtworks.xstream.core.util.ObjectIdDictionary",
      "com.thoughtworks.xstream.io.path.PathTracker",
      "com.thoughtworks.xstream.io.WriterWrapper",
      "com.thoughtworks.xstream.io.path.PathTrackingWriter",
      "com.thoughtworks.xstream.core.util.Primitives",
      "com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper",
      "com.thoughtworks.xstream.core.util.PrioritizedList$PrioritizedItemIterator",
      "com.thoughtworks.xstream.io.path.Path",
      "com.thoughtworks.xstream.core.util.ObjectIdDictionary$IdWrapper",
      "com.thoughtworks.xstream.core.AbstractReferenceMarshaller$Id",
      "com.thoughtworks.xstream.core.util.ObjectIdDictionary$WeakIdWrapper",
      "com.thoughtworks.xstream.core.AbstractReferenceMarshaller$1",
      "com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter$1",
      "com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter$FieldInfo",
      "com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter$2"
    );
  }
}
