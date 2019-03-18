package org.eclipse.scava.crossflow.web.elkgraph;

import java.util.Collections;
import java.util.List;

import org.eclipse.xtext.ide.server.LanguageServerImpl;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

import io.typefox.sprotty.api.IDiagramServer;
import io.typefox.sprotty.server.xtext.DiagramLanguageServerExtension;
import io.typefox.sprotty.server.xtext.ILanguageAwareDiagramServer;
import io.typefox.sprotty.server.xtext.LanguageAwareDiagramServer;
import io.typefox.sprotty.server.xtext.websocket.LanguageServerEndpoint;

/**
 * The language server extension is created by the {@link LanguageServerImpl}, which is in turn
 * created by the {@link LanguageServerEndpoint}. This extension takes care of generating diagrams
 * for the ELK graphs written in the text editor.
 */
@SuppressWarnings("all")
public class ElkGraphLanguageServerExtension extends DiagramLanguageServerExtension {
  private LanguageAwareDiagramServer languageAwareDiagramServer;
  
  @Override
  protected void initializeDiagramServer(final IDiagramServer server) {
    System.out.println("calling initializeDiagramServer");
    super.initializeDiagramServer(server);
    final LanguageAwareDiagramServer languageAware = ((LanguageAwareDiagramServer) server);
    languageAware.setNeedsClientLayout(false);
  }
  
  /**
   * The client identifier is hard-coded here so we can notify the client about a new diagram
   * although we never received a request for it. This is possible because there is always
   * exactly one client for each instance of this class.
   */
  @Override
  public List<? extends ILanguageAwareDiagramServer> findDiagramServersByUri(final String uri) {
    List<ILanguageAwareDiagramServer> _xblockexpression = null;
    {
      System.out.println(("calling findDiagramServersByUri with uri = " + uri));
      IDiagramServer _diagramServer = this.getDiagramServer("sprotty");
      this.languageAwareDiagramServer = ((LanguageAwareDiagramServer) _diagramServer);
      new ElkGraphDiagramUpdater(this.languageAwareDiagramServer, 2000, 3000);
      _xblockexpression = Collections.<ILanguageAwareDiagramServer>unmodifiableList(CollectionLiterals.<ILanguageAwareDiagramServer>newArrayList(this.languageAwareDiagramServer));
    }
    return _xblockexpression;
  }
}
