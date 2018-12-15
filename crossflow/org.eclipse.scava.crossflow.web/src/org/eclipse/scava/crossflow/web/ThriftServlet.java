package org.eclipse.scava.crossflow.web;

import javax.servlet.annotation.WebServlet;

import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.server.TServlet;

@WebServlet("/thrift")
public class ThriftServlet extends TServlet {

	public ThriftServlet() {
		super(new Crossflow.Processor<>(new CrossflowHandler()), 
				new TJSONProtocol.Factory());
	}
	
}
