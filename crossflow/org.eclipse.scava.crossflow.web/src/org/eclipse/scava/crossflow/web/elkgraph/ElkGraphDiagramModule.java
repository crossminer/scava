/**
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.scava.crossflow.web.elkgraph;

import org.eclipse.xtext.ide.server.ILanguageServerExtension;

import io.typefox.sprotty.api.IDiagramServer;
import io.typefox.sprotty.server.xtext.DefaultDiagramModule;
import io.typefox.sprotty.server.xtext.IDiagramGenerator;

/**
 * Guice bindings for the ELK diagram server.
 */
@SuppressWarnings("all")
public class ElkGraphDiagramModule extends DefaultDiagramModule {
  @Override
  public Class<? extends ILanguageServerExtension> bindILanguageServerExtension() {
    return ElkGraphLanguageServerExtension.class;
  }
  
  @Override
  public Class<? extends IDiagramServer.Provider> bindIDiagramServerProvider() {
    return ElkGraphLanguageServerExtension.class;
  }
  
  public Class<? extends IDiagramGenerator> bindIDiagramGenerator() {
    return ElkGraphDiagramGenerator.class;
  }
}
