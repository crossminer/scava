package org.eclipse.scava.crossflow.elkgraph.web

import io.typefox.sprotty.api.IDiagramServer
import io.typefox.sprotty.server.xtext.DiagramLanguageServerExtension
import io.typefox.sprotty.server.xtext.LanguageAwareDiagramServer
import io.typefox.sprotty.server.xtext.websocket.LanguageServerEndpoint
import org.eclipse.xtext.ide.server.LanguageServerImpl

/**
 * The language server extension is created by the {@link LanguageServerImpl}, which is in turn
 * created by the {@link LanguageServerEndpoint}. This extension takes care of generating diagrams
 * for the ELK graphs written in the text editor.
 */
class ElkGraphLanguageServerExtension extends DiagramLanguageServerExtension {
	
	LanguageAwareDiagramServer languageAwareDiagramServer
			
	override protected initializeDiagramServer(IDiagramServer server) {
		super.initializeDiagramServer(server)
		val languageAware = server as LanguageAwareDiagramServer
		languageAware.needsClientLayout = false
	}
	
	/**
	 * The client identifier is hard-coded here so we can notify the client about a new diagram
	 * although we never received a request for it. This is possible because there is always
	 * exactly one client for each instance of this class.
	 */
	override findDiagramServersByUri(String uri) {
		System.out.println("calling findDiagramServersByUri with uri = " + uri)
		languageAwareDiagramServer = getDiagramServer('sprotty') as LanguageAwareDiagramServer
		new ElkGraphDiagramUpdater(languageAwareDiagramServer);
		#[languageAwareDiagramServer]
	}

}
