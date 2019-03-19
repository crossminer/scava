package org.eclipse.scava.crossflow.web.elkgraph;

import java.util.HashMap;

import org.apache.thrift.TException;
import org.eclipse.elk.alg.common.compaction.options.PolyominoOptions;
import org.eclipse.elk.alg.disco.options.DisCoMetaDataProvider;
import org.eclipse.elk.alg.force.options.ForceMetaDataProvider;
import org.eclipse.elk.alg.force.options.StressMetaDataProvider;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.alg.mrtree.options.MrTreeMetaDataProvider;
import org.eclipse.elk.alg.radial.options.RadialMetaDataProvider;
import org.eclipse.elk.alg.spore.options.SporeMetaDataProvider;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.elk.graph.ElkGraphPackage;
import org.eclipse.elk.graph.text.ElkGraphRuntimeModule;
import org.eclipse.elk.graph.text.ide.ElkGraphIdeModule;
import org.eclipse.elk.graph.text.ide.ElkGraphIdeSetup;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.xtext.util.Modules2;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ElkgraphServiceHandler implements ElkgraphService.Iface {
	
	protected HashMap<String, Workflow> workflows = new HashMap<>();
	protected ElkgraphServiceServlet servlet;
	
	public ElkgraphServiceHandler(ElkgraphServiceServlet servlet) {
		System.out.println("ElkgraphServiceHandler");
		this.servlet = servlet;
		
		initialize();
		
	}
	
	public void initialize() {
	    LayoutMetaDataService _instance = LayoutMetaDataService.getInstance();
	    ForceMetaDataProvider _forceMetaDataProvider = new ForceMetaDataProvider();
	    LayeredMetaDataProvider _layeredMetaDataProvider = new LayeredMetaDataProvider();
	    MrTreeMetaDataProvider _mrTreeMetaDataProvider = new MrTreeMetaDataProvider();
	    RadialMetaDataProvider _radialMetaDataProvider = new RadialMetaDataProvider();
	    StressMetaDataProvider _stressMetaDataProvider = new StressMetaDataProvider();
	    PolyominoOptions _polyominoOptions = new PolyominoOptions();
	    DisCoMetaDataProvider _disCoMetaDataProvider = new DisCoMetaDataProvider();
	    SporeMetaDataProvider _sporeMetaDataProvider = new SporeMetaDataProvider();
	    _instance.registerLayoutMetaDataProviders(_forceMetaDataProvider, _layeredMetaDataProvider, _mrTreeMetaDataProvider, _radialMetaDataProvider, _stressMetaDataProvider, _polyominoOptions, _disCoMetaDataProvider, _sporeMetaDataProvider);
	    ElkGraphPackage.eINSTANCE.getNsURI();
	    new ElkGraphIdeSetup() {
	      @Override
	      public Injector createInjector() {
	        ElkGraphRuntimeModule _elkGraphRuntimeModule = new ElkGraphRuntimeModule();
	        ElkGraphIdeModule _elkGraphIdeModule = new ElkGraphIdeModule();
	        ElkGraphDiagramModule _elkGraphDiagramModule = new ElkGraphDiagramModule();
	        return Guice.createInjector(Modules2.mixin(_elkGraphRuntimeModule, _elkGraphIdeModule, _elkGraphDiagramModule));
	      }
	    }.createInjectorAndDoEMFRegistration();
	    System.out.println("ElkgraphServiceHandler.initialize() completed.");
	  }
	
	protected ClassLoader getClassLoader() throws Exception {
		return Thread.currentThread().getContextClassLoader();
	}
	
	@Override
	public ElkgraphServiceDiagnostics getDiagnostics() throws TException {
		ElkgraphServiceDiagnostics diagnostics = new ElkgraphServiceDiagnostics();
		diagnostics.setLanguageServerRunning(isLanguageServerRunning());
		return diagnostics;
	}

	@Override
	public void startLanguageServer() throws TException {
		System.out.println("startLanguageServer()");
		
	} 

	@Override
	public void stopLanguageServer() throws TException {
		System.out.println("stopLanguageServer()");
		
	}

	@Override
	public boolean isLanguageServerRunning() throws TException {
		System.out.println("isLanguageServerRunning()");

		return false;
	}
	
}
