package org.eclipse.scava.crossflow.web.elkgraph;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.eclipse.elk.alg.common.compaction.options.PolyominoOptions;
import org.eclipse.elk.alg.disco.options.DisCoMetaDataProvider;
import org.eclipse.elk.alg.force.options.ForceMetaDataProvider;
import org.eclipse.elk.alg.force.options.StressMetaDataProvider;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.alg.mrtree.options.MrTreeMetaDataProvider;
import org.eclipse.elk.alg.radial.options.RadialMetaDataProvider;
import org.eclipse.elk.alg.spore.options.SporeMetaDataProvider;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.elk.graph.ElkGraphPackage;
import org.eclipse.elk.graph.text.ElkGraphRuntimeModule;
import org.eclipse.elk.graph.text.ide.ElkGraphIdeModule;
import org.eclipse.elk.graph.text.ide.ElkGraphIdeSetup;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;


@WebServlet("/elkgraph")
public class ElkgraphServiceServlet extends WebSocketServlet {

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


//  @Override
//  protected void doPost(HttpServletRequest request, HttpServletResponse response)
//      throws ServletException, IOException {
//
//    TTransport inTransport = null;
//    TTransport outTransport = null;
//
//    try {
//      response.setContentType("application/x-thrift");
//
//      if (null != this.customHeaders) {
//        for (Map.Entry<String, String> header : this.customHeaders) {
//          response.addHeader(header.getKey(), header.getValue());
//        }
//      }
//      InputStream in = request.getInputStream();
//      // printout input stream to what comes here and submit elk graph layout computed to output stream
//      
//      OutputStream out = response.getOutputStream();
//
//      TTransport transport = new TIOStreamTransport(in, out);
//      inTransport = transport;
//      outTransport = transport;
//
//      TProtocol inProtocol = protocolFactory.getProtocol(inTransport);
//      TProtocol outProtocol = protocolFactory.getProtocol(outTransport);
//
//      System.out.println("initialContent="+request.getParameter("initialContent"));
//      
//      processor.process(inProtocol, outProtocol);
//      out.flush();
//    } catch (TException te) {
//      throw new ServletException(te);
//    }
//  }
//
//  protected void doGet(HttpServletRequest request, HttpServletResponse response)
//      throws ServletException, IOException {
//    doPost(request, response);
//  }
//
//  public void addCustomHeader(final String key, final String value) {
//    this.customHeaders.add(new Map.Entry<String, String>() {
//      public String getKey() {
//        return key;
//      }
//
//      public String getValue() {
//        return value;
//      }
//
//      public String setValue(String value) {
//        return null;
//      }
//    });
//  }
//
//  public void setCustomHeaders(Collection<Map.Entry<String, String>> headers) {
//    this.customHeaders.clear();
//    this.customHeaders.addAll(headers);
//  }

  public void initialize() {
    LayoutMetaDataService _instance = LayoutMetaDataService.getInstance();
    ForceMetaDataProvider _forceMetaDataProvider = new ForceMetaDataProvider();
    LayeredMetaDataProvider _layeredMetaDataProvider = new LayeredMetaDataProvider();
    MrTreeMetaDataProvider _mrTreeMetaDataProvider = new MrTreeMetaDataProvider();
    RadialMetaDataProvider _radialMetaDataProvider = new RadialMetaDataProvider();
    StressMetaDataProvider _stressMetaDataProvider = new StressMetaDataProvider();
    PolyominoOptions _polyominoOptions = new PolyominoOptions();
    DisCoMetaDataProvider _disCoMetaDataProvider = new DisCoMetaDataProvider();
    SporeMetaDataProvider _sporeMetaDataProvider = new SporeMetaDataProvider();
    _instance.registerLayoutMetaDataProviders(_forceMetaDataProvider, _layeredMetaDataProvider, _mrTreeMetaDataProvider, _radialMetaDataProvider, _stressMetaDataProvider, _polyominoOptions, _disCoMetaDataProvider, _sporeMetaDataProvider);
    ElkGraphPackage.eINSTANCE.getNsURI();
    new ElkGraphIdeSetup() {
      @Override
      public Injector createInjector() {
        ElkGraphRuntimeModule _elkGraphRuntimeModule = new ElkGraphRuntimeModule();
        ElkGraphIdeModule _elkGraphIdeModule = new ElkGraphIdeModule();
        ElkGraphDiagramModule _elkGraphDiagramModule = new ElkGraphDiagramModule();
        return Guice.createInjector(Modules2.mixin(_elkGraphRuntimeModule, _elkGraphIdeModule, _elkGraphDiagramModule));
      }
    }.createInjectorAndDoEMFRegistration();
  }

@Override
protected StreamInbound createWebSocketInbound(String arg0, HttpServletRequest arg1) {
	System.out.println("createWebSocketInbound String arg0="+arg0);
	System.out.println("createWebSocketInbound HttpServletRequest arg1="+arg1);
	
	// initialize here?
	initialize();
	
	
	/*
     * arg1.getRemoteAddr() return the ip address (v4 or v6) of the client
     */
    return new WebSocket(arg1.getRemoteAddr());
}

/*
 * The WebSocket class!!! :D
 * protected* is for testing with JUnit
 */
protected class WebSocket extends MessageInbound{

/*
 * Client stream reference.
 */
private WsOutbound outbound;
/*
 * Client IP reference.
 */
    private String ip;

/*
 * Costructor
 * @param ipAddress
 *  Client ip (v4 or v6).
 */
protected WebSocket(String ipAddress){
	System.out.println("WebSocket ipAddress: " +ipAddress);
    ip = ipAddress;
}

/*
 * Message sender.
 * @param m
 *  message to client.
 */
private void sendMessage(String m){
    try{
        outbound.writeTextMessage(CharBuffer.wrap((m).toCharArray()));
    }
    catch(IOException ioException){
        System.out.println("error opening websocket");
    }
}

/*
 * Client open channel.
 * @param o
 *  Client stream reference.
 */
@Override
public void onOpen(WsOutbound o){
    this.outbound = o;
    System.out.println("socket opened!");
}

/**
 * Client send a char stream to server.
 * @param buffer
 *  Message buffer.
 */
@Override
public void onTextMessage(CharBuffer buffer) throws IOException{
/**
 * What do you do when client send a message to server.
 */
	System.out.println("onTextMessage: CharBuffer buffer="+buffer);
}

/**
 * Client send a byte stream to server.
 * @param buffer
 *  Byte buffer.
 */
@Override
public void onBinaryMessage(ByteBuffer buffer) throws IOException {
/**
 * What do you do when client send a byte stream to server.
 */
	System.out.println("onBinaryMessage ByteBuffer buffer="+buffer);
}

} //WebSocket


}