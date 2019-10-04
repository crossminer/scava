package rWorkflow;

import java.util.Timer;
import java.util.TimerTask;

public class Source extends SourceBase {

	@Override
	public void produce() throws Exception {

		RConfiguration stub1 = new RConfiguration();
		stub1.analysisType = "Species~.";
		sendToSoQ(stub1);

	}

}
