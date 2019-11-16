package org.eclipse.scava.platform.communicationchannel.eclipseforums.client.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.scava.platform.communicationchannel.eclipseforums.utils.EclipseForumUtils;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClientManager {

	// ECLIPSE FORUMS AUTH DOCUMENTATION = https://api.eclipse.org/docs/auth

	private static Map<String, ClientData> clientMap;
	protected static OssmeterLogger logger;

	static {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("eclipse_forums.token.manager");
		clientMap = ClientManagerSingleton.getInstance().getClientData();

	}

	public OkHttpClient getClient(EclipseForum eclipseForum) throws IOException {
		OkHttpClient client = null;

		if (clientMap.containsKey(eclipseForum.getOSSMeterId())) {
			checkForRefresh(eclipseForum);// ensures that the client is up-to-date
			client = clientMap.get(eclipseForum.getOSSMeterId()).getClient();
		}

		return client;
	}

	private void addNewClient(EclipseForum eclipseForum) {

		if (clientMap.containsKey(eclipseForum.getOSSMeterId())) {

			logger.info("A Client already exists for " + eclipseForum.getOSSMeterId());

		} else {

			ClientData data = new ClientData();
			data.setOssmeterID(eclipseForum.getOSSMeterId());

			if (eclipseForum.getClient_id()!=null && !eclipseForum.getClient_id().isEmpty()
					&& eclipseForum.getClient_secret()!=null && !eclipseForum.getClient_secret().isEmpty())
			{

				data.setClientId(eclipseForum.getClient_id());
				data.setClientSecret(eclipseForum.getClient_secret());

				try {

					data = generateOAuthToken(data);// this sets OauthToken, refresh token, expiresIn and generatedAt
					logger.info("An authenticated client has been added for " + eclipseForum.getOSSMeterId());

				} catch (IOException e) {

					logger.error(e);
				}

			} else {

				data.setClient(getUnAuthenticatedClient());
				logger.info("Unauthenticated client has been added for " + eclipseForum.getOSSMeterId());
			}
			clientMap.put(data.getOssmeterID(), data);
			logger.info("Client data has been added to Client Map " + eclipseForum.getOSSMeterId());
		}
	}

	private OkHttpClient getUnAuthenticatedClient() {

		return new OkHttpClient();
	}

	public void checkForRefresh(EclipseForum eclipseForum) throws IOException {

		if (clientMap.containsKey(eclipseForum.getOSSMeterId())) {

			ClientData td = clientMap.get(eclipseForum.getOSSMeterId());

			// only enters if the the user wants to use OAuth
			if (eclipseForum.getClient_id()!=null && !eclipseForum.getClient_id().isEmpty()
					&& eclipseForum.getClient_secret()!=null && !eclipseForum.getClient_secret().isEmpty())
			{

				if (checkTime(td.getGeneratedAt(), td.getExpiresIn()) == true) {
					// THIS SHOULD BE REFRESEH TOKEN BUT ECLIPSE DOESNT INCLUDE IT
					// IN THE BODY (EVEN THOUGH THEY SAY THEY DO IN THE DOCS)
					logger.info("Refreshing client for " + eclipseForum.getOSSMeterId());
					td = generateOAuthToken(td);

				}

			}

			clientMap.put(td.getOssmeterID(), td);

		} else {

			logger.info("No Client Found");

		}
	}

	private ClientData generateOAuthToken(ClientData clientData) throws JsonProcessingException, IOException {

		logger.info("Attempting to generate OAuth Token");
		OkHttpClient genClient = new OkHttpClient();

		FormBody.Builder formBodyBuilder = new FormBody.Builder();
		formBodyBuilder.add("grant_type", "client_credentials");
		formBodyBuilder.add("client_id", clientData.getClientId());
		formBodyBuilder.add("client_secret", clientData.getClientSecret());

		FormBody body = formBodyBuilder.build();

		// Used for a POST request
		Request.Builder builder = new Request.Builder();
		builder = builder.url("https://accounts.eclipse.org/oauth2/token");
		builder = builder.post(body);
		Request request = builder.build();
		Response response = genClient.newCall(request).execute();

		Map<String, Integer> headerData = checkHeader(response.headers());

		if (headerData.get("calls-remaining") == 0) {

			waitUntilCallReset(headerData.get("time-to-reset"));
		}

		JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());

		clientData.setoAuthToken(EclipseForumUtils.fixString(jsonNode.get("access_token").toString()));

		// This sets the number of milliseconds until expiration
		clientData.setExpiresIn(
				Integer.parseInt(EclipseForumUtils.fixString(jsonNode.get("expires_in").toString())) * 1000);

		clientData.setGeneratedAt(System.currentTimeMillis());

		response.close();

		logger.info("OAuth Token has been generated for " + clientData.getOssmeterID());
		logger.info("Creating authenticated client for " + clientData.getOssmeterID());

		OkHttpClient.Builder authClient = new OkHttpClient.Builder();
		authClient.addInterceptor(new Interceptor() {

			@Override
			public Response intercept(Chain chain) throws IOException {

				Request request = chain.request();
				Request.Builder newRequest = request.newBuilder().header("Authorization",
						" Bearer " + clientData.getoAuthToken());
				return chain.proceed(newRequest.build());
			}
		});

		clientData.setClient(authClient.build());

		logger.info("Authenticated client created for " + clientData.getOssmeterID());

		return clientData;
	}

	public ClientData getClientData(EclipseForum eclipseForum) {

		return clientMap.get(eclipseForum.getOSSMeterId());
	}

	public Boolean clientDataExists(EclipseForum eclipseForum) {

		if (clientMap.containsKey(eclipseForum.getOSSMeterId())) {

			return true;

		} else {

			addNewClient(eclipseForum);

			return true;
		}
	}

	// =========================================================
	// U T I L I T Y M E T H O D S
	// =========================================================

	private void waitUntilCallReset(int timeToReset) {

		try {
			logger.info("The rate limit has been reached. This thread will be suspended for " + timeToReset
					+ " seconds until the limit has been reset");
			Thread.sleep((timeToReset * 1000l) + 2);

		} catch (InterruptedException e) {

		}
	}

	private Map<String, Integer> checkHeader(Headers responseHeader) {

		Map<String, Integer> responseHeaderData = new HashMap<>();

		int rateLimit = Integer.parseInt(responseHeader.get("X-Rate-Limit-Limit"));
		int callsRemaning = Integer.parseInt(responseHeader.get("X-Rate-Limit-Remaining"));
		int timeToReset = Integer.parseInt(responseHeader.get("X-Rate-Limit-Reset"));

		responseHeaderData.put("rate-limit", rateLimit);
		responseHeaderData.put("calls-remaining", callsRemaning);
		responseHeaderData.put("time-to-reset", timeToReset);

		return responseHeaderData;
	}

	private Boolean checkTime(long generatedAt, long expiresIn) {

		long currentTime = System.currentTimeMillis();
		long diff = (currentTime - generatedAt);

		if (diff < expiresIn) {
			return false;
		}
		return true;
	}

}