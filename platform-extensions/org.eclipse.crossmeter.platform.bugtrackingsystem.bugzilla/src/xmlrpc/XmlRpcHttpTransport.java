/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
package xmlrpc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.client.XmlRpcClientException;
import org.apache.xmlrpc.client.XmlRpcHttpClientConfig;
import org.apache.xmlrpc.util.HttpUtil;
import org.xml.sax.SAXException;

/** Abstract base implementation of an HTTP transport. Base class for the
 * concrete implementations, like {@link org.apache.xmlrpc.client.XmlRpcSunHttpTransport},
 * or {@link org.apache.xmlrpc.client.XmlRpcCommonsTransport}.
 */
public abstract class XmlRpcHttpTransport extends XmlRpcStreamTransport {
    protected class ByteArrayReqWriter implements ReqWriter {
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayReqWriter(XmlRpcRequest pRequest)
                throws XmlRpcException, IOException, SAXException {
            new ReqWriterImpl(pRequest).write(baos);
        }

        protected int getContentLength() {
            return baos.size();
        }

        public void write(OutputStream pStream) throws IOException {
            try {
                baos.writeTo(pStream);
                pStream.close();
                pStream = null;
            } finally {
                if (pStream != null) { try { pStream.close(); } catch (Throwable ignore) {} }
            }
        }
    }

	private String userAgent;

	/** The user agent string.
	 */
	public static final String USER_AGENT = "Apache XML RPC 3.0";

	protected XmlRpcHttpTransport(XmlRpcClient pClient, String pUserAgent) {
		super(pClient);
		userAgent = pUserAgent;
	}

	protected String getUserAgent() { return userAgent; }

	protected abstract void setRequestHeader(String pHeader, String pValue);

	protected void setCredentials(XmlRpcHttpClientConfig pConfig)
			throws XmlRpcClientException {
		String auth;
		try {
			auth = HttpUtil.encodeBasicAuthentication(pConfig.getBasicUserName(),
													  pConfig.getBasicPassword(),
													  pConfig.getBasicEncoding());
		} catch (UnsupportedEncodingException e) {
			throw new XmlRpcClientException("Unsupported encoding: " + pConfig.getBasicEncoding(), e);
		}
		if (auth != null) {
			setRequestHeader("Authorization", "Basic " + auth);
		}
	}

	protected void setContentLength(int pLength) {
		setRequestHeader("Content-Length", Integer.toString(pLength));
	}

	protected void setCompressionHeaders(XmlRpcHttpClientConfig pConfig) {
		if (pConfig.isGzipCompressing()) {
			setRequestHeader("Content-Encoding", "gzip");
		}
		if (pConfig.isGzipRequesting()) {
			setRequestHeader("Accept-Encoding", "gzip");
		}
	}

	protected void initHttpHeaders(XmlRpcRequest pRequest) throws XmlRpcClientException {
		XmlRpcHttpClientConfig config = (XmlRpcHttpClientConfig) pRequest.getConfig();
		setRequestHeader("Content-Type", "text/xml");
        if(config.getUserAgent() != null)
            setRequestHeader("User-Agent", config.getUserAgent());
        else
            setRequestHeader("User-Agent", getUserAgent());
		setCredentials(config);
		setCompressionHeaders(config);
	}

	public Object sendRequest(XmlRpcRequest pRequest) throws XmlRpcException {
		initHttpHeaders(pRequest);
		return super.sendRequest(pRequest);
	}

	protected boolean isUsingByteArrayOutput(XmlRpcHttpClientConfig pConfig) {
		return !pConfig.isEnabledForExtensions()
			|| !pConfig.isContentLengthOptional();
	}

	protected ReqWriter newReqWriter(XmlRpcRequest pRequest)
			throws XmlRpcException, IOException, SAXException {
		final XmlRpcHttpClientConfig config = (XmlRpcHttpClientConfig) pRequest.getConfig();
        if (isUsingByteArrayOutput(config)) {
            ByteArrayReqWriter reqWriter = new ByteArrayReqWriter(pRequest);
            setContentLength(reqWriter.getContentLength());
            if (isCompressingRequest(config)) {
                return new GzipReqWriter(reqWriter);
            }
            return reqWriter;
		} else {
			return super.newReqWriter(pRequest);
		}
	}
}
