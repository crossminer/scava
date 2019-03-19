package org.eclipse.scava.crossflow.web.elkgraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TTransport;

@WebServlet("/elkgraph")
public class ElkgraphServiceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5033980590462135997L;
	private final TProcessor processor;
	private final Collection<Map.Entry<String, String>> customHeaders;
	protected TProtocolFactory protocolFactory;

	public ElkgraphServiceServlet() {
		super();

		this.protocolFactory = new TJSONProtocol.Factory();
		this.processor = new ElkgraphService.Processor<>(new ElkgraphServiceHandler(this));
		this.customHeaders = new ArrayList<Map.Entry<String, String>>();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TTransport inTransport = null;
		TTransport outTransport = null;

		System.out.println("initialContent=" + request.getParameter("initialContent"));

		// Pass 'initialContent' to ELK for layout assignment and return result to
		// client

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}