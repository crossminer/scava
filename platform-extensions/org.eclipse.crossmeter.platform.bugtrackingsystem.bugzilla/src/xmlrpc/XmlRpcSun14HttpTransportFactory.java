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

import javax.net.ssl.SSLSocketFactory;

import org.apache.xmlrpc.client.XmlRpcTransport;

/**
 * Default implementation of an HTTP transport factory in Java 1.4, based
 * on the {@link java.net.HttpURLConnection} class.
 */
public class XmlRpcSun14HttpTransportFactory extends XmlRpcTransportFactoryImpl {
    private SSLSocketFactory sslSocketFactory;

    /**
     * Creates a new factory, which creates transports for the given client.
     * @param pClient The client, which is operating the factory.
     */
    public XmlRpcSun14HttpTransportFactory(XmlRpcClient pClient) {
        super(pClient);
    }

    /**
     * Sets the SSLSocketFactory to be used by transports.
     * @param pSocketFactory The SSLSocketFactory to use.
     */
    public void setSSLSocketFactory(SSLSocketFactory pSocketFactory) {
        sslSocketFactory = pSocketFactory;
    }

    /**
     * Returns the SSLSocketFactory to be used by transports.
     */
    public SSLSocketFactory getSSLSocketFactory() {
        return sslSocketFactory;
    }

    public XmlRpcTransport getTransport() {
        XmlRpcSun14HttpTransport transport = new XmlRpcSun14HttpTransport(getClient());
        transport.setSSLSocketFactory(sslSocketFactory);
        return transport;
    }
}
