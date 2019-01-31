/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
* 
* Contributors:
* 	- Thomas Degueule (CWI)
**********************************************************************/

package org.eclipse.scava.plugin.rascal;

import java.io.PrintWriter;

import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.load.StandardLibraryContributor;
import org.rascalmpl.library.lang.java.m3.internal.EclipseJavaCompiler;
import org.rascalmpl.values.ValueFactoryFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import io.usethesource.vallang.IConstructor;
import io.usethesource.vallang.ISet;
import io.usethesource.vallang.ISourceLocation;
import io.usethesource.vallang.ITuple;
import io.usethesource.vallang.IValue;
import io.usethesource.vallang.IValueFactory;

public class M3Builder {
	public Multimap<String, String> extractMethodInvocations(String jar) {
		Multimap<String, String> mis = ArrayListMultimap.create();
		IValueFactory vf = ValueFactoryFactory.getValueFactory();
		EclipseJavaCompiler ejc = new EclipseJavaCompiler(vf);
		Evaluator eval = createRascalEvaluator(vf);

		eval.doImport(null, "lang::java::m3::Core");
		eval.doImport(null, "lang::java::m3::AST");
		eval.doImport(null, "lang::java::m3::TypeSymbol");

		IValue v = ejc.createM3FromJarFile(vf.sourceLocation(jar), eval);
		ISet rel = ((ISet) ((IConstructor) v).asWithKeywordParameters().getParameter("methodInvocation"));

		rel.forEach(e -> {
			ITuple t = (ITuple) e;
			ISourceLocation md = (ISourceLocation) t.get(0);
			ISourceLocation mi = (ISourceLocation) t.get(1);
			mis.put(md.toString(), mi.toString());
		});

		return mis;
	}

	private Evaluator createRascalEvaluator(IValueFactory vf) {
		GlobalEnvironment heap = new GlobalEnvironment();
		ModuleEnvironment module = new ModuleEnvironment("$m3builer$", heap);
		PrintWriter stderr = new PrintWriter(System.err);
		PrintWriter stdout = new PrintWriter(System.out);
		Evaluator eval = new Evaluator(vf, stderr, stdout, module, heap);

		eval.addRascalSearchPathContributor(StandardLibraryContributor.getInstance());

		return eval;
	}

//	public static void main(String[] args) {
//		new M3Builder().extractMethodInvocations("/tmp/sonar-plugin-api-6.7.jar");
//	}
}
