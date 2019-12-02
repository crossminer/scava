package org.eclipse.scava.platform.communicationchannel.eclipseforums.client.manager;

import java.io.IOException;
import java.util.Hashtable;
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

	static
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("communicationchannel.eclipseforums.client.manager");
		clientMap = new Hashtable<String, ClientData>();
	}
	
	public static ClientData getClient(EclipseForum eclipseForum)
	{
		ClientData clientData;
		if(clientMap.containsKey(eclipseForum.getClient_id()))
			clientData=verifyClientStatus(clientMap.get(eclipseForum.getClient_id()));
		else
			clientData=setClient(eclipseForum);
		return clientData;
	}
	
	private static ClientData verifyClientStatus(ClientData clientData)
	{
		needToWait(clientData, false);
		if(clientData instanceof ClientDataOAuth)
			refreshOAuth((ClientDataOAuth) clientData);
		return clientData;
	}
	
	private static void refreshOAuth(ClientDataOAuth clientDataOAuth)
	{
		//If the client will need to be refreshed in the following 60s
		if(clientDataOAuth.getExpiresIn()<60)
		{
			try {
				generateOAuthToken(clientDataOAuth);
			} catch (IOException | InterruptedException e) {
				logger.error("Error while creating an OAuth client due to: ", e);
				logger.info("Using a temporal non-OAuth client");
				setTemporalNoOAuth(clientDataOAuth);
			}
			clientMap.put(clientDataOAuth.getClientId(), clientDataOAuth);
		}
	}
	
	private static ClientData getNoOAuthClient()
	{
		ClientData clientData;
		if(!clientMap.containsKey("")) //NoOAuth client
			clientData=setClientNoOAuth();
		else
			clientData=clientMap.get("");
		return clientData;
	}
	
	private static void setTemporalNoOAuth(ClientDataOAuth clientDataOAuth)
	{
		ClientData clientData = getNoOAuthClient();
		
		clientDataOAuth.setCallsRemaining(clientData.getCallsRemaining());
		clientDataOAuth.setClient(clientData.getClient());
		clientDataOAuth.setTimeToReset(clientData.getTimeToReset());
		clientDataOAuth.setRateLimit(clientData.getRateLimit());
	}
	
	private static ClientData setClient(EclipseForum eclipseForum)
	{
		if(eclipseForum.getClient_id().isEmpty() || eclipseForum.getClient_secret().isEmpty())
			return setClientNoOAuth();
		else
			return setClientOAuth(eclipseForum);
	}
	
	private static ClientData setClientNoOAuth()
	{
		ClientData clientData = new ClientData();
		clientData.setClient(new OkHttpClient());
		clientMap.put("", clientData);
		logger.info("Creating a non-OAuth client");
		return clientData;
	}
	
	private static ClientData setClientOAuth(EclipseForum eclipseForum)
	{
		ClientDataOAuth clientDataOAuth = new ClientDataOAuth();
		clientDataOAuth.setClientId(eclipseForum.getClient_id());
		clientDataOAuth.setClientSecret(eclipseForum.getClient_secret());
		try {
			generateOAuthToken(clientDataOAuth);
			clientMap.put(eclipseForum.getClient_id(), clientDataOAuth);
			return clientDataOAuth;
		} catch (IOException | InterruptedException e) {
			logger.error("Error while creating an OAuth client for "+ eclipseForum.getOSSMeterId() +" due to: ", e);
			logger.info("Creating instead a non-OAuth client");
			return setClientNoOAuth();
		}
	}
	
	public static Response executeRequest(EclipseForum eclipseForum, Request request) throws IOException, InterruptedException
	{
		return executeRequest(getClient(eclipseForum), request);
	}
	
	private static Response executeRequest(ClientData clientData, Request request) throws IOException, InterruptedException
	{
		int counter=0;
		Response response;
		do
		{
			response = clientData.getClient().newCall(request).execute();
			checkHeader(response.headers(), clientData);
			counter++;
			if(counter==3)
			{
				throw new InterruptedException("The client manager has been trying 3 times in a row to get the response. This has been interrupted to prevent a ban.");
			}
		}
		while(needToWait(clientData, true));
		return response;
	}

	private static ClientData generateOAuthToken(ClientDataOAuth clientData) throws JsonProcessingException, IOException, InterruptedException {

		logger.info("Attempting to generate OAuth Token");

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
		
		Response response;
		if(clientData.getClient()==null)
		{	
			clientData.setClient(new OkHttpClient());
			response = executeRequest(clientData, request);
		}
		else
			response = executeRequest(clientData, request);
		
		JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());

		clientData.setoAuthToken(EclipseForumUtils.fixString(jsonNode.get("access_token").toString()));

		// This sets the number of milliseconds until expiration
		clientData.setExpiresIn(
				Long.parseLong(EclipseForumUtils.fixString(jsonNode.get("expires_in").toString())));

		clientData.setGeneratedAt(System.currentTimeMillis());

		response.close();

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

		return clientData;
	}

	// =========================================================
	// U T I L I T Y M E T H O D S
	// =========================================================

	private static void waitUntilCallReset(long timeToReset)
	{	
		if(timeToReset>0)
		{	
			try {
				logger.info("The rate limit has been reached. This thread will be suspended for " + timeToReset
						+ " seconds until the limit has been reset");
				Thread.sleep((timeToReset * 1000l));
	
			} catch (InterruptedException e) {
				logger.error("An error happened while waiting for the calls limit to renew", e);
			}
		}
	}
	
	private static boolean needToWait(ClientData clientData, boolean afterRequest)
	{
		if(clientData.getCallsRemaining()<=0 && clientData.getZeroCounter()>0)
		{
			if(afterRequest && clientData.getZeroCounter()==1)
				return false;
			waitUntilCallReset(clientData.getTimeToReset());
			return true;
		}
		return false;
	}
	
	private static void checkHeader(Headers responseHeader, ClientData clientData)
	{
		clientData.setCallsRemaining(Integer.parseInt(responseHeader.get("X-Rate-Limit-Remaining")));
		clientData.setRateLimit(Integer.parseInt(responseHeader.get("X-Rate-Limit-Limit")));
		clientData.setTimeToReset(Long.parseLong(responseHeader.get("X-Rate-Limit-Reset")));
	}

}