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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.client.XmlRpcClientException;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.common.XmlRpcStreamRequestConfig;
import org.apache.xmlrpc.parser.XmlRpcResponseParser;
import org.apache.xmlrpc.serializer.XmlRpcWriter;
import org.apache.xmlrpc.util.SAXParsers;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


/** Implementation of a transport class, which is based on an output
 * stream for sending the request and an input stream for receiving
 * the response,
 */
public abstract class XmlRpcStreamTransport extends XmlRpcTransportImpl {
    protected interface ReqWriter {
        /**
         * Writes the requests data to the given output stream.
         * The method ensures, that the target is being closed.
         */
        void write(OutputStream pStream) throws XmlRpcException, IOException, SAXException;
    }

    protected class ReqWriterImpl implements ReqWriter {
        private final XmlRpcRequest request;

        protected ReqWriterImpl(XmlRpcRequest pRequest) {
            request = pRequest;
        }

        /** Writes the requests uncompressed XML data to the given
         * output stream. Ensures, that the output stream is being
         * closed.
         */
        public void write(OutputStream pStream)
                throws XmlRpcException, IOException, SAXException {
            final XmlRpcStreamConfig config = (XmlRpcStreamConfig) request.getConfig();
            try {
                ContentHandler h = getClient().getXmlWriterFactory().getXmlWriter(config, pStream);
                XmlRpcWriter xw = new XmlRpcWriter(config, h, getClient().getTypeFactory());
                xw.write(request);
                pStream.close();
                pStream = null;
            } finally {
                if (pStream != null) { try { pStream.close(); } catch (Throwable ignore) {} }
            }
        }
    }

    protected class GzipReqWriter implements ReqWriter {
        private final ReqWriter reqWriter;
        protected GzipReqWriter(ReqWriter pReqWriter) {
            reqWriter = pReqWriter;
        }

        public void write(OutputStream pStream) throws XmlRpcException, IOException, SAXException {
            try {
                GZIPOutputStream gStream = new GZIPOutputStream(pStream);
                reqWriter.write(gStream);
                pStream.close();
                pStream = null;
            } catch (IOException e) {
                throw new XmlRpcException("Failed to write request: " + e.getMessage(), e);
            } finally {
                if (pStream != null) { try { pStream.close(); } catch (Throwable ignore) {} }
            }
        }
    }

	/** Creates a new instance on behalf of the given client.
	 */
	protected XmlRpcStreamTransport(XmlRpcClient pClient) {
		super(pClient);
	}

	/** Closes the connection and ensures, that all resources are being
	 * released.
	 */
	protected abstract void close() throws XmlRpcClientException;

	/** Returns, whether the response is gzip compressed.
	 * @param pConfig The clients configuration.
	 * @return Whether the response stream is gzip compressed.
	 */
	protected abstract boolean isResponseGzipCompressed(XmlRpcStreamRequestConfig pConfig);

	/** Returns the input stream, from which the response is
	 * being read.
	 */
	protected abstract InputStream getInputStream() throws XmlRpcException;

	protected boolean isCompressingRequest(XmlRpcStreamRequestConfig pConfig) {
		return pConfig.isEnabledForExtensions()
			&& pConfig.isGzipCompressing();
	}

    protected ReqWriter newReqWriter(XmlRpcRequest pRequest)
            throws XmlRpcException, IOException, SAXException {
        ReqWriter reqWriter = new ReqWriterImpl(pRequest);
        if (isCompressingRequest((XmlRpcStreamRequestConfig) pRequest.getConfig())) {
            reqWriter = new GzipReqWriter(reqWriter);
        }
        return reqWriter;
    }

    protected abstract void writeRequest(ReqWriter pWriter)
        throws XmlRpcException, IOException, SAXException;
    
	public Object sendRequest(XmlRpcRequest pRequest) throws XmlRpcException {
		XmlRpcStreamRequestConfig config = (XmlRpcStreamRequestConfig) pRequest.getConfig();
		boolean closed = false;
		try {
            ReqWriter reqWriter = newReqWriter(pRequest);
			writeRequest(reqWriter);
			InputStream istream = getInputStream();
			if (isResponseGzipCompressed(config)) {
				istream = new GZIPInputStream(istream);
			}
			Object result = readResponse(config, istream);
			closed = true;
			close();
			return result;
		} catch (IOException e) {
			throw new XmlRpcException("Failed to read server's response: "
					+ e.getMessage(), e);
        } catch (SAXException e) {
            Exception ex = e.getException();
            if (ex != null  &&  ex instanceof XmlRpcException) {
                throw (XmlRpcException) ex;
            }
            throw new XmlRpcException("Failed to generate request: "
                    + e.getMessage(), e);
		} finally {
			if (!closed) { try { close(); } catch (Throwable ignore) {} }
		}
	}

	protected XMLReader newXMLReader() throws XmlRpcException {
		return SAXParsers.newXMLReader();
	}

	// XML 1.0
	private static String xml10pattern = "[^"
	                    + "\u0009\r\n"
	                    + "\u0020-\uD7FF"
	                    + "\uE000-\uFFFD"
	                    + "\ud800\udc00-\udbff\udfff"
	                    + "]";

	// XML 1.1
//	private static String xml11pattern = "[^"
//	                    + "\u0001-\uD7FF"
//	                    + "\uE000-\uFFFD"
//	                    + "\ud800\udc00-\udbff\udfff"
//	                    + "]+";
	//

	private String cleanXMLchars(String input) {
//		System.err.println("CLEANING INPUT");
		return input.replaceAll(xml10pattern, "");
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
 
	protected Object readResponse(XmlRpcStreamRequestConfig pConfig, InputStream pStream) throws XmlRpcException {
//		InputSource isource = new InputSource(pStream);		
		String istring = convertStreamToString(pStream);
		String cleanString = cleanXMLchars(istring);
		InputSource isource = new InputSource( new StringReader( cleanString ) );
		
		XMLReader xr = newXMLReader();
		XmlRpcResponseParser xp;
		try {
			xp = new XmlRpcResponseParser(pConfig, getClient().getTypeFactory());
			xr.setContentHandler(xp);
            xr.parse(isource);
		} catch (SAXException e) {
			throw new XmlRpcClientException("Failed to parse server's response: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new XmlRpcClientException("Failed to read server's response: " + e.getMessage(), e);
		}
		if (xp.isSuccess()) {
			return xp.getResult();
		}
		Throwable t = xp.getErrorCause();
        if (t == null) {
            throw new XmlRpcException(xp.getErrorCode(), xp.getErrorMessage());
        }
        if (t instanceof XmlRpcException) {
            throw (XmlRpcException) t;
        }
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        throw new XmlRpcException(xp.getErrorCode(), xp.getErrorMessage(), t);
	}

}
