package org.eclipse.scava.crossflow.web.elkgraph;

import java.util.Timer;
import java.util.TimerTask;

import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.server.xtext.LanguageAwareDiagramServer;

public class ElkGraphDiagramUpdater {

	Timer timer;
	private LanguageAwareDiagramServer languageAwareDiagramServer;

    public ElkGraphDiagramUpdater(LanguageAwareDiagramServer languageAwareDiagramServer, int delay, int interval) {
    	this.languageAwareDiagramServer = languageAwareDiagramServer;
    	timer = new Timer();
        timer.scheduleAtFixedRate(new CrossflowDiagramUpdaterTask(), delay, interval);
	}

    class CrossflowDiagramUpdaterTask extends TimerTask {
        public void run() {
            SModelRoot sModelRoot = languageAwareDiagramServer.getModel();
            
            // TODO: replace label text with data from Crossflow monitoring queue
//            SNode sNode = (SNode) sModelRoot.getChildren().get(0);
//            SLabel sLabel = (SLabel) sNode.getChildren().get(0);
//            sLabel.setText(String.valueOf(Math.random()));

            languageAwareDiagramServer.updateModel(sModelRoot);
        }
    }
    
}
