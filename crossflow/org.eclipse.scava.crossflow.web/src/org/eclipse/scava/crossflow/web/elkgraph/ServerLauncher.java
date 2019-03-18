/**
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.scava.crossflow.web.elkgraph;

import java.net.InetSocketAddress;

import javax.websocket.Endpoint;

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
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Slf4jLog;
//import org.eclipse.jetty.webapp.WebAppContext;
//import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
//import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.eclipse.xtext.ide.server.ServerModule;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.util.Modules2;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

import io.typefox.sprotty.server.xtext.websocket.LanguageServerEndpoint;

/**
 * Main class for launching the ELK Graph server.
 */
@SuppressWarnings("all")
public class ServerLauncher {
//  public static void main(final String[] args) {
//    ServerModule _serverModule = new ServerModule();
//    final com.google.inject.Module _function = (Binder it) -> {
//      it.<Endpoint>bind(Endpoint.class).to(LanguageServerEndpoint.class);
//      it.<IResourceServiceProvider.Registry>bind(IResourceServiceProvider.Registry.class).toProvider(IResourceServiceProvider.Registry.RegistryProvider.class);
//    };
//    final Injector injector = Guice.createInjector(Modules2.mixin(_serverModule, _function));
//    final ServerLauncher launcher = injector.<ServerLauncher>getInstance(ServerLauncher.class);
//    launcher.initialize();
//    String _xifexpression = null;
//    int _length = args.length;
//    boolean _greaterEqualsThan = (_length >= 1);
//    if (_greaterEqualsThan) {
//      _xifexpression = args[0];
//    } else {
//      _xifexpression = "../..";
//    }
//    final String rootPath = _xifexpression;
//    launcher.start(rootPath);
//  }
//  
//  @Inject
//  private Provider<Endpoint> endpointProvider;
//  
//  public void initialize() {
//    LayoutMetaDataService _instance = LayoutMetaDataService.getInstance();
//    ForceMetaDataProvider _forceMetaDataProvider = new ForceMetaDataProvider();
//    LayeredMetaDataProvider _layeredMetaDataProvider = new LayeredMetaDataProvider();
//    MrTreeMetaDataProvider _mrTreeMetaDataProvider = new MrTreeMetaDataProvider();
//    RadialMetaDataProvider _radialMetaDataProvider = new RadialMetaDataProvider();
//    StressMetaDataProvider _stressMetaDataProvider = new StressMetaDataProvider();
//    PolyominoOptions _polyominoOptions = new PolyominoOptions();
//    DisCoMetaDataProvider _disCoMetaDataProvider = new DisCoMetaDataProvider();
//    SporeMetaDataProvider _sporeMetaDataProvider = new SporeMetaDataProvider();
//    _instance.registerLayoutMetaDataProviders(_forceMetaDataProvider, _layeredMetaDataProvider, _mrTreeMetaDataProvider, _radialMetaDataProvider, _stressMetaDataProvider, _polyominoOptions, _disCoMetaDataProvider, _sporeMetaDataProvider);
//    ElkGraphPackage.eINSTANCE.getNsURI();
//    new ElkGraphIdeSetup() {
//      @Override
//      public Injector createInjector() {
//        ElkGraphRuntimeModule _elkGraphRuntimeModule = new ElkGraphRuntimeModule();
//        ElkGraphIdeModule _elkGraphIdeModule = new ElkGraphIdeModule();
//        ElkGraphDiagramModule _elkGraphDiagramModule = new ElkGraphDiagramModule();
//        return Guice.createInjector(Modules2.mixin(_elkGraphRuntimeModule, _elkGraphIdeModule, _elkGraphDiagramModule));
//      }
//    }.createInjectorAndDoEMFRegistration();
//  }
//  
//  public void start(final String rootPath) {
//    try {
//      String _name = ServerLauncher.class.getName();
//      final Slf4jLog log = new Slf4jLog(_name);
//      InetSocketAddress _inetSocketAddress = new InetSocketAddress(9090);
//      final Server server = new Server(_inetSocketAddress);
//      WebAppContext _webAppContext = new WebAppContext();
//      final Procedure1<WebAppContext> _function = (WebAppContext it) -> {
//        it.setResourceBase((rootPath + "/client/app"));
//        String _resourceBase = it.getResourceBase();
//        String _plus = ("Serving client app from " + _resourceBase);
//        log.info(_plus);
//        it.setWelcomeFiles(new String[] { "index.html" });
//        it.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
//        it.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
//      };
//      
//      final WebAppContext webAppContext = ObjectExtensions.<WebAppContext>operator_doubleArrow(_webAppContext, _function);
//      server.setHandler(webAppContext);
//      final ServerContainer container = WebSocketServerContainerInitializer.configureContext(webAppContext);
//      final ServerEndpointConfig.Builder endpointConfigBuilder = ServerEndpointConfig.Builder.create(LanguageServerEndpoint.class, "/elkgraph");
//      endpointConfigBuilder.configurator(new ServerEndpointConfig.Configurator() {
//        @Override
//        public <T extends Object> T getEndpointInstance(final Class<T> endpointClass) throws InstantiationException {
//          Endpoint _get = ServerLauncher.this.endpointProvider.get();
//          return ((T) _get);
//        }
//      });
//      container.addEndpoint(endpointConfigBuilder.build());
//      
//      try {
//        server.start();
//        log.info("Press enter to stop the server...");
//        final Runnable _function_1 = () -> {
//          try {
//            final int key = System.in.read();
//            server.stop();
//            if ((key == (-1))) {
//              log.warn("The standard input stream is empty");
//            }
//          } catch (Throwable _e) {
//            throw Exceptions.sneakyThrow(_e);
//          }
//        };
//        new Thread(_function_1).start();
//        server.join();
//      } catch (final Throwable _t) {
//        if (_t instanceof Exception) {
//          final Exception exception = (Exception)_t;
//          log.warn("Shutting down due to exception", exception);
//          System.exit(1);
//        } else {
//          throw Exceptions.sneakyThrow(_t);
//        }
//      }
//    } catch (Throwable _e) {
//      throw Exceptions.sneakyThrow(_e);
//    }
//  }
}
