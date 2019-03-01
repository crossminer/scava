package org.eclipse.scava.workflow.restmule.core.util;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.workflow.restmule.core.data.IDataSet;
import org.eclipse.scava.workflow.restmule.core.data.Status;

public class LoggerUtil extends Thread {

	private static final Logger LOG = LogManager.getLogger(LoggerUtil.class);

	private IDataSet<?> dataset;

	public LoggerUtil(IDataSet<?> dataset) {
		this.dataset = dataset;
	}

	public LoggerUtil() { }

	public void setDataSet(IDataSet<?> dataset) {
		this.dataset = dataset;
	}

	@Override
	public void run() {
		while (!dataset.status().equals(Status.COMPLETED)){
			try {
				log();
				TimeUnit.SECONDS.sleep(3);
			} catch (Exception e) {}
		}
		log();
	}

	private void log() {
		LOG.info( dataset.id() + " - " + dataset.status() 
				+ (dataset.status().equals(Status.COMPLETED) ? "" : ("(Got " + dataset.count() + " of "+ dataset.total() + ")")));
	}

	public boolean isEmpty() {
		return dataset == null;
	}

}
