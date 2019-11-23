package org.eclipse.scava.platform.communicationchannel.eclipseforums.client.manager;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.OkHttpClient;

public class ClientData {

	private Integer callsRemaining;
	private Long timeToReset;
	private Integer rateLimit;
	private OkHttpClient client;
	private AtomicInteger zeroCounter;
	private Long timeUpdated;
	
	public ClientData() {
		callsRemaining=null;
		timeToReset=null;
		rateLimit=null;
		zeroCounter=new AtomicInteger(0);
		timeUpdated=null;
	}
	
	public synchronized Integer getCallsRemaining() {
		return callsRemaining;
	}
	
	public synchronized void setCallsRemaining(Integer callsRemaining) {
		if(callsRemaining>0 && zeroCounter.get()>0)
			zeroCounter.set(0);
		if(callsRemaining==0)
			zeroCounter.incrementAndGet();
		if(callsRemaining<0)
			zeroCounter.set(2);
		this.callsRemaining = callsRemaining;
	}
	
	public int getZeroCounter()
	{
		return zeroCounter.get();
	}
	
	public synchronized Long getTimeToReset() {
		if(timeUpdated!=null)
		{
			long timeElapsed = System.nanoTime()-timeUpdated;
			timeToReset-=TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS);
			timeToReset+=120; //We add 2 min to prevent a call exactly on the renewal
		}
		return timeToReset;
	}
	public synchronized void setTimeToReset(Long timeToReset) {
		this.timeToReset = timeToReset;
		this.timeUpdated=System.nanoTime();
	}
	public synchronized Integer getRateLimit() {
		return rateLimit;
	}
	public synchronized void setRateLimit(Integer rateLimit) {
		this.rateLimit = rateLimit;
	}

	public synchronized OkHttpClient getClient() {
		return client;
	}

	public synchronized void setClient(OkHttpClient client) {
		this.client = client;
	}
	
}
