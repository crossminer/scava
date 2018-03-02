/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.knowledgebase.access.requestsender;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RemoteRequestSender implements IRequestSender {
	private static final String SERVER_ADDRESS = "http://localhost:8080";
	
	@Override
	public String sendRequest(String address, String content) {
		
		try {
			return sendRequestToServer(address, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String sendRequestToServer(String address, String content) throws IOException, ProtocolException {
		byte[] postData = content.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		
		URL url = buildRequestURL(address);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("charset", StandardCharsets.UTF_8.name());
		conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
		conn.setUseCaches(false);
		
		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			wr.write(postData);
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
		
		StringBuilder sb = new StringBuilder();
		String output = "";
		while ((output = reader.readLine()) != null) {
			sb.append(output);
		}
		
		conn.disconnect();
		
		return sb.toString();
	}
	
	private URL buildRequestURL(String address) throws MalformedURLException, UnsupportedEncodingException {
		
		return new URL(SERVER_ADDRESS + encodeAddress(address));
	}
	
	private String encodeAddress(String address) throws UnsupportedEncodingException {
		String[] parts = address.split("/");
		
		for (int i = 0; i < parts.length; i++) {
			parts[i] = URLEncoder.encode(parts[i], StandardCharsets.UTF_8.name());
		}
		
		return String.join("/", parts);
	}
}
