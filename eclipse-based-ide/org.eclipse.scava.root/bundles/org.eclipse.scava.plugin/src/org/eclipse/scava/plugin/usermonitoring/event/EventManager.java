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
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.usermonitoring.ErrorType;
import org.eclipse.scava.plugin.usermonitoring.UserMonitor;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.event.events.IEvent;
import org.eclipse.scava.plugin.usermonitoring.event.events.idle.IdleEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.events.scheduledUploadEvent.ScheduledUploadEvent;
import org.eclipse.scava.plugin.usermonitoring.gremlin.GremlinUtils;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;
import org.eclipse.swt.widgets.Display;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventManager {

	private boolean saveToDatabase = true;
	private final GremlinUtils gremlinUtils;
	private final UserMonitor userMonitor;
	private final IdleEventListener idleEventListener;
	private Map<Class<? extends IFlushableEvent<?>>, EventBuffer> buffers;
	private BlockingQueue<IEvent> eventQueue = new LinkedBlockingQueue<>();
	private Thread consumerThread;
	private final ListenerManager listenerManager;
	private boolean erase = Activator.getDefault().getPreferenceStore().getBoolean(Preferences.ERASRE_DATABASE_AFTER_METRIC_CALCULATION);

	public EventManager(GremlinUtils gremlinUtils, EventBus eventBus, UserMonitor userMonitor) {
		this.userMonitor = userMonitor;
		this.gremlinUtils = gremlinUtils;
		this.buffers = new HashMap<>();
		eventBus.register(this);
		idleEventListener = new IdleEventListener();
		listenerManager = new ListenerManager();
		startListeners();
		consumerThread();

	}

	private void consumerThread() {

		consumerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					EventManager.this.consumeEvent();
				} catch (InterruptedException | MetricException e) {
					e.printStackTrace();
					ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.EVENT_CONSUME_ERROR);
				}
			}
		});
		consumerThread.start();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Subscribe
	public void processEvent(IEvent event) {
		if (saveToDatabase) {

			idleEventListener.eventAboutToBeInserted(event);
			if (event instanceof IFlushableEvent) {
				EventBuffer eventBuffer = buffers.get(event.getClass());
				if (eventBuffer == null) {
					eventBuffer = new EventBuffer(this::produceEvent);
					buffers.put((Class<? extends IFlushableEvent<?>>) event.getClass(), eventBuffer);
				}
				eventBuffer.add((IFlushableEvent) event);
			} else {
				for (EventBuffer buffer : buffers.values()) {
					buffer.flush();
				}
				produceEvent(event);
			}

		}

	}

	private void produceEvent(IEvent event) {

		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			e.printStackTrace();
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.EVENT_CONSUME_ERROR);
		}

	}

	public void consumeEvent() throws InterruptedException, MetricException {
		while (true) {

			IEvent event = eventQueue.take();

			if (event instanceof ScheduledUploadEvent) {
				Job job = Job.create("Metric calculation", (ICoreRunnable) monitor -> {
					IEvent startMetricCalculation = userMonitor.startMetricCalculation(event);
					if (erase) {
						gremlinUtils.eraseEventsBefore(((Event) event).getTimestamp());
					}
					
					System.out.println("Returned event: "+startMetricCalculation);
					gremlinUtils.insertVertex(startMetricCalculation);
					consumerThread();
					
					
				});
				job.schedule();
				break;

			}
			
			System.out.println(event);
			gremlinUtils.insertVertex(event);

		}

	}

	public void startListeners() {
		listenerManager.enableListeners();
	}

}
