/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.rascal;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarInputStream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.logging.OssmeterLogger;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;
import org.rascalmpl.interpreter.control_exceptions.Throw;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.env.Pair;
import org.rascalmpl.interpreter.load.StandardLibraryContributor;
import org.rascalmpl.interpreter.load.URIContributor;
import org.rascalmpl.interpreter.result.AbstractFunction;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.StaticError;
import org.rascalmpl.interpreter.utils.RascalManifest;
import org.rascalmpl.uri.URIResolverRegistry;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.uri.jar.JarURIResolver;
import org.rascalmpl.values.ValueFactoryFactory;

import io.usethesource.vallang.IConstructor;
import io.usethesource.vallang.IMap;
import io.usethesource.vallang.ISourceLocation;
import io.usethesource.vallang.IString;
import io.usethesource.vallang.IValue;
import io.usethesource.vallang.IValueFactory;
import io.usethesource.vallang.exceptions.FactParseError;
import io.usethesource.vallang.exceptions.FactTypeUseException;
import io.usethesource.vallang.io.StandardTextReader;
import io.usethesource.vallang.type.Type;
import io.usethesource.vallang.type.TypeStore;

public class RascalManager {
	private static final String EXTRACTOR_TAG_AST = "ASTExtractor";
	private static final String EXTRACTOR_TAG_M3 = "M3Extractor";
	private static final String RASCAL_ECLIPSE_NAME = "rascal_eclipse";
	
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	
	private final Evaluator evaluator = createEvaluator();
	private final Set<Bundle> registeredBundles = new HashSet<>();


	// thread safe way of keeping a static instance
	private static class InstanceKeeper {
		public static final RascalManager sInstance = new RascalManager();
	}

	public class Extractor {
		public ModuleEnvironment module;
		public AbstractFunction function;
		public IValue language;

		public Extractor(AbstractFunction fun, ModuleEnvironment env, IValue lang) throws Exception {
			if (lang == null || (lang instanceof IString && ((IString)lang).length() == 0)) {
				throw new Exception("Invalid language");
			}

			function = fun;
			module = env;
			language = lang;

			if (!fun.hasTag("memo")) {
				// TODO enable once we can distinguish between tags and annotations
				//throw new IllegalArgumentException("Rascal M3 extractor functions should have an @memo tag.");
			}
		}

		public IValue call(ISourceLocation projectLocation, IConstructor delta, IMap workingCopyFolders, IMap scratchFolders) {
			try {
				Result<IValue> result = function.call(new NullRascalMonitor(),
						new Type[]{projectLocation.getType(), delta.getType(), workingCopyFolders.getType(), scratchFolders.getType()},
						new IValue[]{projectLocation, delta, workingCopyFolders, scratchFolders}, null);
				return result.getValue();
			}
			catch (Throw e) {
				Rasctivator.logException("Runtime error in model extractor", e);
				Rasctivator.printRascalTrace(e.getTrace());
			}
			catch (StaticError e) {
				Rasctivator.logException("Static error in model extractor", e);
			}
			catch (Throwable e) {
				Rasctivator.logException("Unexpected implementation error while extracting model", e);
			}

			return null;
		}

		@Override
		public String toString() {
			return function.getName();
		}
	}

	private final Set<Extractor> m3Extractors = new HashSet<>();
	private final Set<Extractor> astExtractors = new HashSet<>();

	public static final String MODULE = "org::eclipse::scava::metricprovider::Manager";
	private List<IMetricProvider> metricProviders;

	public void configureRascalMetricProviders(Set<Bundle> providers) {
		assert evaluator != null;

		for (Bundle provider : providers) {
			configureRascalPath(evaluator, provider);
		}
	}

	private Evaluator createEvaluator() {
		GlobalEnvironment heap = new GlobalEnvironment();
		ModuleEnvironment moduleRoot = new ModuleEnvironment("******scava******", heap);

		OssmeterLogger log = (OssmeterLogger) OssmeterLogger.getLogger("Rascal console");
		LoggerWriter stderr = new LoggerWriter(true, log);
		LoggerWriter stdout = new LoggerWriter(false, log);

		Evaluator eval = new Evaluator(VF, stderr, stdout, moduleRoot, heap);
		URIResolverRegistry registry = eval.getRascalResolver().getRegistry();

		eval.setMonitor(new RascalMonitor(log));
		registry.registerLogical(new BundleURIResolver(registry));
		eval.addRascalSearchPathContributor(StandardLibraryContributor.getInstance());
		Bundle currentBundle = Rasctivator.getContext().getBundle();
		List<String> roots = new RascalBundleManifest().getSourceRoots(currentBundle);

		for (String root : roots) {
			try {
				eval.addRascalSearchPath(VF.sourceLocation(currentBundle.getResource(root).toURI()));
			} 
			catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return eval;
	}

	public Evaluator getEvaluator() {
		return evaluator;
	}

	private void configureRascalPath(Evaluator eval, Bundle bundle) {
		Bundle rascalBundle = Platform.getBundle(RASCAL_ECLIPSE_NAME);
		if(!registeredBundles.contains(rascalBundle)) {
			eval.addClassLoader(new BundleClassLoader(rascalBundle));
			registeredBundles.add(rascalBundle);
		}
		
		if (registeredBundles.contains(bundle)) {
			return;
		}
		registeredBundles.add(bundle);

		try {
			// Adding dependendencies from mf.getRequiredBundles(bundle) was removed after 
			// using the most recent version of Rascal.
			RascalBundleManifest mf = new RascalBundleManifest();
			for (String root : mf.getSourceRoots(bundle)) {
				eval.addRascalSearchPath(VF.sourceLocation(bundle.getResource(root).toURI()));
			}

			List<String> requiredLibs = mf.getRequiredLibraries(bundle);
			if (requiredLibs != null) {
				for (String lib : requiredLibs) {
					URIResolverRegistry registry = eval.getRascalResolver().getRegistry();
					ISourceLocation libURL = registry.logicalToPhysical(VF.sourceLocation(bundle.getResource(lib).toURI()));
					JarURIResolver resolver = new JarURIResolver(registry);

					try {
						addJarToSearchPath(libURL, resolver, eval);
					} 
					catch (IOException e) {
						Rasctivator.logException("ignoring lib " + lib, e);
					}
				}
			}

			eval.addClassLoader(new BundleClassLoader(bundle));
			configureClassPath(bundle, eval);
		} 
		catch (Throwable e) {
			Rasctivator.logException("failed to configure metrics bundle "
					+ bundle, e);
		}
	}

	public static void addJarToSearchPath(ISourceLocation lib, JarURIResolver resolver, Evaluator eval) throws URISyntaxException, IOException {
		URIResolverRegistry registry = eval.getRascalResolver().getRegistry();
		try (JarInputStream jarStream = new JarInputStream(registry.getInputStream(lib))) {
			List<String> roots = new RascalManifest().getSourceRoots(jarStream);

			if (roots != null) {
				for (String root : roots) {
					eval.addRascalSearchPath(VF.sourceLocation(resolver.scheme(), "", "/" + root));
				}
			}
		}
	}

	private void configureClassPath(Bundle bundle, Evaluator eval) {
		List<URL> classPath = new LinkedList<URL>();
		List<String> compilerClassPath = new LinkedList<String>();
		collectClassPathForBundle(bundle, classPath, compilerClassPath);
		configureClassPath(eval, classPath, compilerClassPath);
	}

	private void configureClassPath(Evaluator eval, List<URL> classPath,
			List<String> compilerClassPath) {
		try {
			// this registers the run-time path:
			URL[] urls = new URL[classPath.size()];
			classPath.toArray(urls);
			URLClassLoader classPathLoader = new URLClassLoader(urls, RascalManager.class.getClassLoader());
			eval.addClassLoader(classPathLoader);
			
			Bundle rascalBundle = Platform.getBundle(RASCAL_ECLIPSE_NAME);
	    	URL entry = FileLocator.toFileURL(rascalBundle.getEntry("lib/rascal.jar"));
	        String ccp = new File(URIUtil.fromURL(entry)).getAbsolutePath();
			for (String elem : compilerClassPath) {
				ccp += File.pathSeparatorChar + elem;
			}
			eval.getConfiguration().setRascalJavaClassPathProperty(ccp);
		}
		catch(IOException e) {
			Rasctivator.logException("Error while locating Rascal lib: ", e);
		} 
		catch (URISyntaxException e) {
			Rasctivator.logException("Error while parsing Rascal URL: ", e);
		}
	}

	private void collectClassPathForBundle(Bundle bundle, List<URL> classPath,
			List<String> compilerClassPath) {
		try {
			File file = FileLocator.getBundleFile(bundle);
			URL url = file.toURI().toURL();

			if (classPath.contains(url)) {
				return; // kill infinite loop
			}
			classPath.add(0, url);
			compilerClassPath.add(0, file.getAbsolutePath());
			
			BundleWiring wiring = bundle.adapt(BundleWiring.class);
			for (BundleWire dep : wiring.getRequiredWires(null)) {
				collectClassPathForBundle(dep.getProviderWiring().getBundle(),
						classPath, compilerClassPath);
			}

			List<String> requiredLibs = new RascalBundleManifest().getRequiredLibraries(bundle);
			if (requiredLibs != null) {
				for (String lib : requiredLibs) {
					classPath.add(bundle.getResource(lib));
				}
			}

		} 
		catch (IOException e) {
			Rasctivator.logException("error while tracing dependencies", e);
		} 
	}

	public static RascalManager getInstance() {
		return InstanceKeeper.sInstance;
	}

	public void importModule(String module) {
		evaluator.doImport(new NullRascalMonitor(), module);
	}

	public static String makeRelative(String base, String extension) {
		StringBuilder result = new StringBuilder();
		List<String> baseSegments = Arrays.asList(base.split("/"));
		String[] extensionSegments = extension.split("/");
		for (String ext : extensionSegments) {
			if (!baseSegments.contains(ext)) {
				result.append(extension.substring(extension.indexOf(ext)));
				break;
			}
		}
		return result.toString();
	}

	private void addMetricProviders(Bundle bundle, List<IMetricProvider> providers, Set<IValue> extractedLanguages) {
		RascalBundleManifest mf = new RascalBundleManifest();
		String moduleName = mf.getMainModule(bundle);
		
		try {
			File bundleFile = FileLocator.getBundleFile(bundle).getCanonicalFile();
			List<String> moduleRoots = mf.getSourceRoots(bundle);
			for(String root : moduleRoots) {
				ISourceLocation moduleRoot = VF.sourceLocation("file", "", bundleFile.getAbsolutePath() + File.separator + root + File.separator);
				evaluator.addRascalSearchPathContributor(new URIContributor(moduleRoot));
			}
			
			if (!evaluator.getHeap().existsModule(moduleName)) {
				importModule(moduleName);
			}	
		}
		catch(IOException e) {
			Rasctivator.logException("Error while finding bundle " + bundle, e);
		}
		catch(URISyntaxException e) {
			Rasctivator.logException("Error creating source location of bundle source roots: " + bundle, e);
		}
		
		ModuleEnvironment module = evaluator.getHeap().getModule(moduleName);
		for (Pair<String, List<AbstractFunction>> func : module.getFunctions()) {
			final String funcName = func.getFirst();

			for (final AbstractFunction f : func.getSecond()) {
				// TODO: add some type checking on the arguments
				if (f.hasTag("metric")) {
					try {
						String metricName = getTag(f, "metric");
						String friendlyName = getTag(f, "friendlyName");
						String description = getTag(f, "doc");
						IValue language = f.getTag("appliesTo");
						Map<String,String> uses = getUses(f);

						if (!extractedLanguages.contains(language)) {
							evaluator.getStdOut().println("Warning: metric " + f + " not loaded, no extractors available for language " + language);
							continue;
						}

						if (f.getReturnType().toString().equals("Factoid")) {
							providers.add(new RascalFactoidProvider(bundle.getSymbolicName(), metricName, funcName, friendlyName, description, f, uses));
						}
						else { 
							RascalMetricProvider m = new RascalMetricProvider(bundle.getSymbolicName(), metricName, funcName, friendlyName, description, f, uses); 
							providers.add(m);
							if (f.hasTag("historic")) {
								providers.add(new RascalMetricHistoryWrapper(m));
							}
						}
					}
					catch (Exception e) {
						Rasctivator.logException("Error loading metric " + f, e);
					}
				}
			}
		}
	}

	private String getTag(final AbstractFunction f, String name) {
		IValue val = f.getTag(name);
		if (val instanceof IString) {
			return ((IString) val).getValue();
		} else {
			return val.toString();
		}
	}

	private Map<String,String> getUses(AbstractFunction f) {
		IValue tag = null;
		try {			
			if (f.hasTag("uses")) {
				tag = f.getTag("uses");
				IMap m = null;
				if (tag instanceof IMap) {
					m = (IMap) tag;
				} else if (tag instanceof IString) {
					m = (IMap) new StandardTextReader().read(VF, new StringReader(((IString) tag).getValue()));
				}

				if (m != null) {
					Map<String,String> map = new HashMap<>();

					for (IValue key : m) {
						map.put(((IString) key).getValue(), ((IString) m.get(key)).getValue());
					}

					return map;
				}
			}
		} catch (FactTypeUseException | IOException | FactParseError e) {
			Rasctivator.logException("could not parse uses tag" + (tag != null ? tag.toString() : ""), e);
		}

		return Collections.emptyMap();
	}

	public Set<Extractor> getM3Extractors() {
		return m3Extractors;
	}

	public Set<Extractor> getASTExtractors() {
		return astExtractors;
	}

	public TypeStore getExtractedTypes() {
		TypeStore store = new TypeStore();

		for (Extractor e : m3Extractors) {
			store.extendStore(e.function.getEnv().getStore());
		}
		for (Extractor e : astExtractors) {
			store.extendStore(e.function.getEnv().getStore());
		}

		return store; 
	}

	public synchronized List<IMetricProvider> getMetricProviders() {
		if (metricProviders != null) {
			return metricProviders;
		}

		metricProviders = new LinkedList<>();

		Set<Bundle> extractorBundles = new HashSet<>();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint("scava.rascal.extractor");
		if (extensionPoint != null) {
			for (IExtension element : extensionPoint.getExtensions()) {
				String name = element.getContributor().getName();
				Bundle bundle = Platform.getBundle(name);
				configureRascalPath(evaluator, bundle);
				extractorBundles.add(bundle);
			}
		}

		for (Bundle bundle : extractorBundles) {
			addExtractors(bundle);
		}

		Set<IValue> extractedLanguages = new HashSet<>();
		for (Extractor e : m3Extractors) {
			extractedLanguages.add(e.language);
		}
		for (Extractor e : astExtractors) {
			extractedLanguages.add(e.language);
		}

		Set<Bundle> metricBundles = new HashSet<>();
		extensionPoint = Platform.getExtensionRegistry().getExtensionPoint("scava.rascal.metricprovider");

		if (extensionPoint != null) {
			for (IExtension element : extensionPoint.getExtensions()) {
				String name = element.getContributor().getName();
				Bundle bundle = Platform.getBundle(name);
				configureRascalPath(evaluator, bundle);
				metricBundles.add(bundle);
			}
		}

		for (Bundle bundle : metricBundles) {
			addMetricProviders(bundle, metricProviders, extractedLanguages);
		}

		return metricProviders;
	}

	public void initialize(IValue... parameters) {
		evaluator.call("initialize", MODULE, null, parameters);
	}

	public void addExtractors(Bundle bundle) {
		configureRascalPath(evaluator, bundle);
		RascalBundleManifest mf = new RascalBundleManifest();
		String moduleName = mf.getMainModule(bundle);

		if (!evaluator.getHeap().existsModule(moduleName)) {
			importModule(moduleName);
		}

		ModuleEnvironment module = evaluator.getHeap().getModule(moduleName);

		for (Pair<String, List<AbstractFunction>> func : module.getFunctions()) {
			for (final AbstractFunction f : func.getSecond()) {
				try {
					if (f.hasTag(EXTRACTOR_TAG_M3)) {
						m3Extractors.add(new Extractor(f, module, f.getTag(EXTRACTOR_TAG_M3)));
					} 
					else if (f.hasTag(EXTRACTOR_TAG_AST)) {
						astExtractors.add(new Extractor(f, module, f.getTag(EXTRACTOR_TAG_AST)));								
					} 
				} 
				catch (Exception e) {
					evaluator.getStdErr().println("Error while loading extractor " + f.toString());
					e.printStackTrace(evaluator.getStdErr());
				}
			}
		}
	}
}
