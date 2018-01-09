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

import org.apache.xmlrpc.client.XmlRpcTransportFactory;

/** Abstract base implementation of an {@link XmlRpcTransportFactory}.
 */
public abstract class XmlRpcTransportFactoryImpl implements XmlRpcTransportFactory {
	private final XmlRpcClient client;

	/** Creates a new instance.
	 * @param pClient The client, which will invoke the factory.
	 */
	protected XmlRpcTransportFactoryImpl(XmlRpcClient pClient) {
		client = pClient;
	}

	/** Returns the client operating this factory.
	 * @return The client.
	 */
	public XmlRpcClient getClient() { return client; }
}
