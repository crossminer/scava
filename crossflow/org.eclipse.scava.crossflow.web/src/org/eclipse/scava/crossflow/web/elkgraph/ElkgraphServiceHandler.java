package org.eclipse.scava.crossflow.web.elkgraph;

import java.util.HashMap;

import javax.websocket.Endpoint;

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
import org.eclipse.xtext.ide.server.ServerModule;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

import io.typefox.sprotty.server.xtext.websocket.LanguageServerEndpoint;

public class ElkgraphServiceHandler implements ElkgraphService.Iface {
	
	 @Inject
	  private Provider<Endpoint> endpointProvider;

	protected HashMap<String, Workflow> workflows = new HashMap<>();
	protected ElkgraphServiceServlet servlet;
	
	public ElkgraphServiceHandler(ElkgraphServiceServlet servlet) {
		this.servlet = servlet;
		
		// transcribed from ServerLauncher.main(..)
//	    ServerModule _serverModule = new ServerModule();
//	    final com.google.inject.Module _function = (Binder it) -> {
//	      it.<Endpoint>bind(Endpoint.class).to(LanguageServerEndpoint.class);
//	      it.<IResourceServiceProvider.Registry>bind(IResourceServiceProvider.Registry.class).toProvider(IResourceServiceProvider.Registry.RegistryProvider.class);
//	    };
//	    final Injector injector = Guice.createInjector(Modules2.mixin(_serverModule, _function));
//	    final ServerLauncher launcher = injector.<ServerLauncher>getInstance(ServerLauncher.class);
////	    launcher.initialize();
//	    String _xifexpression = null;
//	    int _length = 0;//args.length;
//	    boolean _greaterEqualsThan = (_length >= 1);
//	    if (_greaterEqualsThan) {
//	      _xifexpression = "";//args[0];
//	    } else {
//	      _xifexpression = "../..";
//	    }
//	    final String rootPath = _xifexpression;
//	    launcher.start(rootPath);
		
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
	  }
	
	protected ClassLoader getClassLoader() throws Exception {
		return Thread.currentThread().getContextClassLoader();
	}
	
	@Override
	public ElkgraphServiceDiagnostics getDiagnostics() throws TException {
		ElkgraphServiceDiagnostics diagnostics = new ElkgraphServiceDiagnostics();
		diagnostics.setLanguageServerRunning(isLanguageServerRunning());
		diagnostics.setRootDirectory(servlet.getServletContext().getRealPath(""));
		return diagnostics;
	}

	@Override
	public void startLanguageServer() throws TException {
		
//	      final WebAppContext webAppContext = ObjectExtensions.<WebAppContext>operator_doubleArrow(_webAppContext, _function);
	      //server.setHandler(webAppContext);
//	      final ServerContainer container = WebSocketServerContainerInitializer.configureContext(webAppContext);
//	      final ServerEndpointConfig.Builder endpointConfigBuilder = ServerEndpointConfig.Builder.create(LanguageServerEndpoint.class, "/elkgraph");
//	      endpointConfigBuilder.configurator(new ServerEndpointConfig.Configurator() {
//	        @Override
//	        public <T extends Object> T getEndpointInstance(final Class<T> endpointClass) throws InstantiationException {
//	          Endpoint _get = ElkgraphServiceHandler.this.endpointProvider.get();
//	          return ((T) _get);
//	        }
//	      });
//	      container.addEndpoint(endpointConfigBuilder.build());
	} 

	@Override
	public void stopLanguageServer() throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLanguageServerRunning() throws TException {
		// TODO Auto-generated method stub
		return false;
	}
	

}
