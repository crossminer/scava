/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.rascal.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.rascal.RascalFactoidProvider;
import org.eclipse.scava.metricprovider.rascal.RascalManager;
import org.eclipse.scava.metricprovider.rascal.RascalMetricHistoryWrapper;
import org.eclipse.scava.metricprovider.rascal.RascalMetricProvider;
import org.eclipse.scava.metricprovider.rascal.RascalProjectDeltas;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.platform.osgi.executors.MetricListExecutor;
import org.eclipse.scava.platform.osgi.executors.ProjectExecutor;
import org.eclipse.scava.repository.model.LocalStorage;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectExecutionInformation;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.io.BinaryValueReader;
import org.eclipse.imp.pdb.facts.io.BinaryValueWriter;
import org.eclipse.imp.pdb.facts.io.StandardTextReader;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.rascalmpl.interpreter.Evaluator;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class RascalTestCaseGenerator implements IApplication  {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		// create platform with mongo db
		Mongo mongo = new Mongo();
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		Platform platform = new Platform(mongo);
		OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("RascalTestCaseGenerator");
		
		// load rascal manager and metric providers
		RascalManager manager = RascalManager.getInstance();
		Evaluator eval = manager.getEvaluator();
		
		List<IMetricProvider> metricProviders = manager.getMetricProviders();
		
		// sort metric providers topologically
		metricProviders = new ProjectExecutor(platform, new Project()) {
			public List<IMetricProvider> order(List<IMetricProvider> metrics) {
				List<IMetricProvider> result = new ArrayList<>(metrics.size());
				for (List<IMetricProvider> branch : splitIntoBranches(metrics)) {
					branch.removeAll(result);
					//Collections.reverse(branch);
					result.addAll(branch);
				}
				return result;
			}
		}.order(metricProviders);

		System.out.println("Available metric providers:");
		for (IMetricProvider mp : metricProviders) {
			System.out.println(mp.getIdentifier());
		}
		
		TypeStore extractedTypes = manager.getExtractedTypes();
		TypeStore deltaTypes = new RascalProjectDeltas(eval).getStore();		
		
		// initialise test data dir and read project settings
		File testDataDir = new File(Platform.getInstance().getLocalStorageHomeDirectory().toFile(), "rascaltestdata");
		testDataDir.mkdirs();
				
		List<Project> projects = null;
		File settingsFile = new File(testDataDir, "projects.txt");
		if (settingsFile.exists()) {
			IValue projectSettings = readTextValue(settingsFile, eval);
			projects = loadProjects(projectSettings, platform);
		}
		
		if (projects == null || projects.size() == 0) {
			logger.error("Please specify projects to import test data from in " + settingsFile.getAbsolutePath());
			logger.error("Format: \"[<name, type, url>, ...]\" where type can be \"svn\" or \"git\".");
			return null;
		}

		final int MAX_DELTAS_PER_REPO = 25;
		Map<String, Integer> deltasProcessed = new HashMap<>();
		Map<String, Iterator<Date>> repositoryDates = new HashMap<>();
		
		// set up available dates per repository
		for (Project project : projects) {
			for (VcsRepository repo : project.getVcsRepositories()) {
				String firstRevision = platform.getVcsManager().getFirstRevision(repo);
				Date startDate = platform.getVcsManager().getDateForRevision(repo, firstRevision).addDays(-1);
				Date today = new Date();
				
				Date[] dates = Date.range(startDate.addDays(1), today.addDays(-1));
				repositoryDates.put(repo.getUrl(), Arrays.asList(dates).iterator());
				
				deltasProcessed.put(repo.getUrl(), 0);
			}
		}
		
		// generate test data
		boolean moreDatesAvailable;
		do {
			moreDatesAvailable = false;
			
			for (Project project : projects) {
				for (VcsRepository repo : project.getVcsRepositories()) {
					String repoURL = repo.getUrl();
					Iterator<Date> dateIterator = repositoryDates.get(repoURL);
					if (deltasProcessed.get(repoURL) < MAX_DELTAS_PER_REPO && dateIterator.hasNext()) {
						Date date = dateIterator.next();
						moreDatesAvailable |= dateIterator.hasNext();
						
						ProjectDelta delta = new ProjectDelta(project, date, platform);
						try {
							delta.create();	
							if (!delta.getVcsDelta().getRepoDeltas().isEmpty()) {
								System.out.println("\nProject: " + project.getShortName() + ", date: " + date);
								
								File dir = new File(testDataDir, project.getName() + "/" + encode(repoURL) + "/" + date.toString());
								dir.mkdirs();
								
								MetricListExecutor ex = new MetricListExecutor(project.getShortName(), delta, date);
								ex.setMetricList(metricProviders);
								ex.run();
								
								IValue rascalDelta = RascalMetricProvider.computeDelta(project, delta, manager, logger);
								IValue rascalASTs = RascalMetricProvider.computeAsts(project, delta, manager, logger);
								IValue rascalM3s = RascalMetricProvider.computeM3(project, delta, manager, logger);
								
								handleNewValue(new File(dir, "delta.bin"), rascalDelta, deltaTypes, eval, logger);
								handleNewValue(new File(dir, "asts.bin"), rascalASTs, extractedTypes, eval, logger);
								handleNewValue(new File(dir, "m3s.bin"), rascalM3s, extractedTypes, eval, logger);
								
								for (IMetricProvider mp : metricProviders) {
									IValue result = null;
									
									if (mp instanceof RascalMetricProvider) {
										result = ((RascalMetricProvider) mp).getMetricResult(project, mp, manager);
									}
									else if (mp instanceof RascalMetricHistoryWrapper) {
										// ignore because its automatically derived
									}
									else if (mp instanceof RascalFactoidProvider) {
										RascalFactoidProvider rfp = (RascalFactoidProvider) mp;
										result = rfp.getMeasuredFactoid(rfp.adapt(platform.getMetricsRepository(project).getDb()), eval.getValueFactory());								
									} 
									else {
										logger.warn("Unknown metric provider: " + mp.getIdentifier());
										continue;
									}
									
									System.out.println(mp.getFriendlyName());
									System.out.println("" + result);
									
									handleNewValue(new File(dir, mp.getIdentifier()), result, null, eval, logger);
								}
								
								deltasProcessed.put(repoURL, deltasProcessed.get(repoURL) + 1);
							}
						} catch (Exception e) {
							
						}
					}
				}
			}
		} while (moreDatesAvailable);
		
		return null;
	}

	private List<Project> loadProjects(IValue projectSettings, Platform platform) {
		List<Project> projects = new LinkedList<Project>();

		if (projectSettings instanceof IList) {
			for (IValue v : ((IList) projectSettings)) {
				if (v instanceof ITuple && ((ITuple) v).arity() == 3) {
					ITuple t = (ITuple) v;
					List<String> entries = new LinkedList<String>();
					for (IValue s : t) {
						if (s instanceof IString) {
							entries.add(((IString) s).getValue());
						}
					}
					if (entries.size() == 3) {
						String name = entries.get(0);
						String type = entries.get(1);
						String repo = entries.get(2);
						
						Project project = null;
						
						if (type.equals("svn")) {
							project = ProjectCreationUtil.createSvnProject(name, repo);
						} else if (type.equals("git")) {
							project = ProjectCreationUtil.createGitProject(name, repo);
						}

						if (project != null) {
							project.setShortName(name);		
							initialiseProjectLocalStorage(project, platform);
							projects.add(project);
						}
					}
				}
			}
		}
		return projects;
	}

	private void handleNewValue(File path, IValue value, TypeStore store, Evaluator eval, OssmeterLogger logger) throws IOException {
		if (store == null) {
			store = eval.getCurrentModuleEnvironment().getStore();
		}
		
		if (path.exists()) {
			// data already exists from previous run, test if current value is equal
			if (value == null) {
				logger.error("Null value for: " + path.toString());
				return;
			}
			
			IValue previous = readValue(path, value.getType(), store, eval);
			
			if (!value.isEqual(previous)) {
				logger.error("Different value in file: " + path.toString());
				
				File path2 = new File(path.getAbsolutePath() + ".conflict");
				writeValue(path2, value, eval);
			}
		} else if (value != null) {
			writeValue(path, value, eval);
		}
	}
	
	@Override
	public void stop() {
	}

	private static void initialiseProjectLocalStorage (Project project, Platform platform) {
		project.setExecutionInformation(new ProjectExecutionInformation());
		
		try{	
			Path projectLocalStoragePath = Paths.get(platform.getLocalStorageHomeDirectory().toString(), project.getName());		
			if (Files.notExists(projectLocalStoragePath)) {
				Files.createDirectory(projectLocalStoragePath);
			}
			LocalStorage projectLocalStorage = new LocalStorage();
			projectLocalStorage.setPath(projectLocalStoragePath.toString());
			project.getExecutionInformation().setStorage(projectLocalStorage);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void writeValue(File path, IValue value, Evaluator eval) throws IOException {
		try (OutputStream out = eval.getResolverRegistry().getOutputStream(path.toURI(), false)) {
			new BinaryValueWriter().write(value, out);
			//new StandardTextWriter().write(value, new OutputStreamWriter(out, "UTF8"));
		} catch (RuntimeException e) {
			// ignore
		}
	}
	
	private static IValue readValue(File path, Type type, TypeStore store, Evaluator eval) throws IOException {
		try (InputStream in = new BufferedInputStream(eval.getResolverRegistry().getInputStream(path.toURI()))) {		
			return new BinaryValueReader().read(eval.getValueFactory(), store, type, in);		
			//return new StandardTextReader().read(eval.getValueFactory(), store, type, new InputStreamReader(in, "UTF8"));
		}
	}

	private static IValue readTextValue(File path, Evaluator eval) throws IOException {
		InputStream in = eval.getResolverRegistry().getInputStream(path.toURI());		
		return new StandardTextReader().read(eval.getValueFactory(), new InputStreamReader(in));
	}
	
	private static String encode(String url) {
		StringBuilder b = new StringBuilder();

		for (char ch : url.toCharArray()) {
			if (Character.isLetterOrDigit(ch)) {
				b.append(ch);
			}
			else {
				b.append(String.format("_%x_", (int) ch));
			}
		}

		return b.toString();
	}
}
