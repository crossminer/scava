package org.eclipse.scava.crossflow.standalone;
/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Jonathan Co - initial API and implementation
 ******************************************************************************/

import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.scava.crossflow.GenerateBaseClasses;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import crossflow.CrossflowPackage;
import crossflow.Language;

/**
 * Standalone runner for Crossflow Stub generation
 */
public class Standalone {

	@Parameter(names = { "-project", "-p", "-o" }, description = "Root output folder for generation", required = true)
	private String projectLoc;

	@Parameter(names = { "-model", "-i" }, description = "Workflow Definiton model to use", required = true)
	private String modelLoc;

	@Parameter(names = { "-runtime",
			"-r" }, description = "Whether to copy the Java runtime to generated classes. Defaults to false")
	private boolean copyRuntime = false;

	GenerateBaseClasses generator = null;

	public static void main(String[] args) throws Exception {
		Standalone standalone = new Standalone();
		JCommander.newBuilder().addObject(standalone).build().parse(args);
		standalone.run();
	}

	public void run() throws Exception {	
		getGenerator().run(projectLoc, modelLoc);
		if (copyRuntime) {
			doCopyRuntime();
		}
	}

	public void doCopyRuntime() throws Exception {
		
		// Get the output folder for generated code to copy runtime to
		final EmfModel model = getGenerator().getModel();
		final Optional<String> genOutputFolder = model.getAllOfType("Language").stream()
			.map(l -> (Language) l)
			.filter(l -> l.getName().equalsIgnoreCase("java"))
			.map(l -> l.getGenOutputFolder())
			.findFirst();
		
		// Return if folder is not specified
		if (!genOutputFolder.isPresent()) return;
		
		// Retrieve location of runtime Java source and pre-process final output paths
		final ImmutableSet<ClassInfo> classInfo = ClassPath.from(Standalone.class.getClassLoader())
				.getTopLevelClassesRecursive("org.eclipse.scava.crossflow.runtime");
		final Map<String, File> javaFiles = classInfo.stream()
			.map(ci -> ci.url().toString())
			.map(url -> url.replaceFirst("/bin/", "/src/"))
			.map(url -> url.replaceFirst("\\.class", ".java"))
			.map(url -> URI.create(url))
			.map(uri -> new File(uri))
			.collect(Collectors.toMap(
					f -> {
						String absPath = f.getAbsolutePath();
						return absPath.substring((absPath.lastIndexOf("/src/") + 5));
					}, 
					t -> t));
		
		// Do the copying
		for (Map.Entry<String, File> e : javaFiles.entrySet()) {
			File newFile = new File(projectLoc + "/" + genOutputFolder.get() + "/" + e.getKey());
			FileUtils.copyFile(e.getValue(), newFile);
		}
	}

	public String getProjectLoc() {
		return projectLoc;
	}

	public void setProjectLoc(String projectLoc) {
		this.projectLoc = projectLoc;
	}

	public String getModelLoc() {
		return modelLoc;
	}

	public void setModelLoc(String modelLoc) {
		this.modelLoc = modelLoc;
	}

	public boolean isCopyRuntime() {
		return copyRuntime;
	}

	public void setCopyRuntime(boolean copyRuntime) {
		this.copyRuntime = copyRuntime;
	}

	protected GenerateBaseClasses getGenerator() {
		if (generator == null) {
			CrossflowPackage crossflowPackage = CrossflowPackage.eINSTANCE;
			EPackage.Registry.INSTANCE.put(crossflowPackage.getNsURI(), crossflowPackage);
			generator = new GenerateBaseClasses();
		}
		return generator;
	}

}
