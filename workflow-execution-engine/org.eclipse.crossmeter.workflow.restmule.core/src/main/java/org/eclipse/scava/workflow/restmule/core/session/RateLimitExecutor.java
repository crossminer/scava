package org.eclipse.scava.workflow.restmule.core.session;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 
 * {@link RateLimitExecutor}
 * <p>
 * @version 1.0.0
 *
 */
public class RateLimitExecutor extends ThreadPoolExecutor {

	private static final Logger LOG = LogManager.getLogger(RateLimitExecutor.class);
	private static final String COUNTER_DEBUG = "\n[{}] Total={}, Remaining={}, CacheCounter={}, Dispatched={}";

	private RateLimiter maxRequestsPerSecond;
	private AtomicInteger remainingRequestCounter;
	private AtomicInteger dispatchCounter;
	private AtomicBoolean awaiting;
	private Class<? extends AbstractSession> sessionClass;
	private String sessionId;

	private long jitter = 100;
	private String id;

	private Date lastReset = new Date();

	RateLimitExecutor(int maxRequestsPerSecond, Class<? extends AbstractSession> session, String sessionId,
			ThreadFactory factory) {
		super(1, 1, 0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>(), factory); // SINGLE
		setup(maxRequestsPerSecond, session, sessionId);
	}

	RateLimitExecutor(int maxRequestsPerSecond, Class<? extends AbstractSession> session, String sessionId) {
		super(1, 1, 0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory()); // SINGLE
																												// THREAD
		setup(maxRequestsPerSecond, session, sessionId);
	}

	private void setup(int maxRequestsPerSecond, Class<? extends AbstractSession> session, String sessionId) {
		this.id = UUID.randomUUID().toString();
		this.maxRequestsPerSecond = RateLimiter.create(maxRequestsPerSecond);
		this.dispatchCounter = new AtomicInteger(0);
		this.remainingRequestCounter = new AtomicInteger(1);
		this.awaiting = new AtomicBoolean(false);
		this.sessionClass = session;
		this.sessionId = sessionId;
	}

	public static RateLimitExecutor create(int maxRequestsPerSecond, Class<? extends AbstractSession> session,
			String sessionId) {
		if (maxRequestsPerSecond > 30) {
			maxRequestsPerSecond = 30;
		}
		return new RateLimitExecutor(maxRequestsPerSecond, session, sessionId);
	}

	public String getId() {
		return id;
	}

	private ISession getLimiter() {
		return (ISession) AbstractSession.getSession(sessionClass, sessionId);
	}

	@Override
	public void execute(Runnable command) {
		LOG.info("[" + sessionId + "] ENTERING EXECUTE");
		maxRequestsPerSecond.acquire();

		// Wait for first request to return
		if (dispatchCounter.get() == 1) {
			awaitToSet();
		}
		dispatchCounter.incrementAndGet();

		if (getLimiter().isSet().get()) {
			if (dispatchCounter.get() == 2) {
				LOG.info("ADJUSTING REQUEST COUNTER");
				remainingRequestCounter.set(getLimiter().getRateLimitRemaining().get());
				lastReset = getLimiter().getRateLimitReset();
			}
			if ((remainingRequestCounter.get() + getLimiter().cacheCounter().get()) <= 0) {
				long timeout = getLimiter().getRateLimitResetInMilliSeconds() - System.currentTimeMillis() + jitter;
				timeout = (timeout > 0) ? timeout : 1000;
				try {
					awaiting.set(true);
					LOG.info("SLEEPING for " + MILLISECONDS.toSeconds(timeout) + " s");
					MILLISECONDS.sleep(timeout);
				} catch (InterruptedException e) {
					LOG.error(e.getMessage());
				}
				//awaitToSet();
				awaiting.set(false);
				LOG.info("RESETING COUNTER");
				remainingRequestCounter.set(getLimiter().getRateLimitRemaining().get());
			}
			if (getLimiter().cacheCounter().get() > 0) {
				LOG.info("USED CACHE");
				getLimiter().cacheCounter().decrementAndGet();
				// Because we're removing the value from the cache and later on decreasing the remaining value
				remainingRequestCounter.incrementAndGet(); 
			}
			if (lastReset.before(getLimiter().getRateLimitReset())) {
				LOG.info("UPDATE RESET TIME + FILL UP REMAINING");
				lastReset = getLimiter().getRateLimitReset();
				remainingRequestCounter.set(getLimiter().getRateLimitRemaining().get());
			}
		} else {
			LOG.info("LIMITER HAS NOT YET BEEN SET");
		}
		remainingRequestCounter.decrementAndGet();
		logCounterDebug("beforeExec");
		super.execute(command);
	}

	private void awaitToSet() {
		while (!getLimiter().isSet().get()) {
			try {
				LOG.info("AWAITING (" + MILLISECONDS.toSeconds(1000) + " s) FOR SESSION ("+sessionId+") TO BE SET");
				MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	private void logCounterDebug(String step) {
		LOG.debug(COUNTER_DEBUG, sessionId, getLimiter().getRateLimit(), remainingRequestCounter.get(),
				getLimiter().cacheCounter().get(), dispatchCounter.get());
	}
}
