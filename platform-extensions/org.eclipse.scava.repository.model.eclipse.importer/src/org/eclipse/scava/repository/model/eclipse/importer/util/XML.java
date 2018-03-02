/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.eclipse.importer.util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class XML {

    private Document doc;
    private XPath xpath;

    
    public XML(URL url) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
        URLConnection con = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        StringBuilder sb=new StringBuilder();
        while ((line = rd.readLine()) != null)  {
           sb.append(line+"\n");
        }
        rd.close(); 
        String text=sb.toString();
        init(text);
    }
    public XML(String text) throws ParserConfigurationException, SAXException, IOException{
    	init(text);
    }
    private void init(String text) throws ParserConfigurationException, SAXException, IOException {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         //if html
         if (text.indexOf("<html")>-1 ||text.indexOf("<HTML")>-1 ){
               
            //Logger.getLogger(this.getClass().getName()).warning("The text to be parsed seems to be in html format...html is not xml");
            //Logger.getLogger(this.getClass().getName()).warning("Sanitizing html...");
            //replace any entity
            text=text.replaceAll("&nbsp;","_");text=text.replaceAll("&uacute;","");
            text=text.replaceAll("&","_AND_SYMBOL_");
            //remove w3c DTDs since they are slow
            int index=text.indexOf("<head");if(index==-1) index=text.indexOf("<HEAD");
            text="<html>"+text.substring(index);
            //Actually remove all the head tag
            text=removeHead(text);
            //solve img tags not closed
            text=sanitizeTag("img",text);
            text=sanitizeTag("input",text);
            
         }
         DocumentBuilder db = dbf.newDocumentBuilder(); 
         InputStream is=new ByteArrayInputStream(text.getBytes("UTF-8"));
         doc = db.parse(is);
         doc.getDocumentElement().normalize();
         XPathFactory xFactory = XPathFactory.newInstance();
         xpath = xFactory.newXPath();
     }
    
    private String removeHead(String text) {
		String result="";
		result=text.substring(0,text.indexOf("<head "));
		result+=text.substring(text.indexOf("</head>") + "</head>".length());
		return result;
	}
	private String sanitizeTag(String tag,String text){
    	int offset=0;
    	int index1=0;
    	while (index1>-1){
    		index1=text.indexOf("<"+tag,offset);
    		if (index1==-1)break;
    		int index2=text.indexOf(">",index1);
    		offset=index2;
    		if (text.charAt(index2-1)!='/')text=text.substring(0,index2)+'/'+text.substring(index2);
    		//text=text.substring(0,index1)+text.substring(index2+1);
    	}
    	return text;
    }
    
    /**
     * @see XML#get(String selector,Element parentElement)
     * @param selector
     * @return
     * @throws Exception
     */
    public List<Element> get(String selector)throws Exception{
       return  get(selector,null);
    }
    
    public List<Element> get(String selector,Element parentElement) throws Exception{
        /* ************************************************************************************
         *                              1-transform selector to xpath format
         * ************************************************************************************/
        //use inner XPATH syntax for searching elements  http://www.w3schools.com/xpath/xpath_syntax.asp
        StringBuilder sb=new StringBuilder(".//");//relative to parentNode
        int index=0;
        String value=null;
        String  lastOperator="";
        while(index<selector.length()){
            char c=selector.charAt(index);
            if (c=='['){
                //attribute
                int close=selector.indexOf(']',index+1);
                String atrKeyValue=selector.substring(index+1,close);
                String[] keyValue=atrKeyValue.split("=");
                String attributeKey=keyValue[0];
                sb.append("[@"+attributeKey);
                if (keyValue.length>1){
                    String attributeValue=keyValue[1];
                    sb.append("="+"'"+attributeValue+"'");
                }
                sb.append("]");
                index=close+1;
            }else if (c=='='){
                //value
                int end=selector.length();
                value=selector.substring(index,end);
                //finding by value is not implemented in xpath->do our own implementation
                index=end+1;
            }else if (c==' '){
                //space->all inner childs
                lastOperator="//";
                index++;
            }else if ( c=='>'){
                //arrow>only first childs
                lastOperator="/";
                index++;
            }else if ( c=='#'){
                //identifier (id attribute)
                int end= getEndSeparatorIndex(selector,index+1);
                if(end<=index+1) throw new Exception("# must contain an identifier");
                String key="id";
                String val=selector.substring(index+1,end);
                sb.append("*[@"+key+"='"+val+"']");
                index=end;
            }else{
                int end=selector.indexOf(' ',index+1);
                if (end==-1) end=selector.indexOf('>',index+1);
                if (end==-1) end=selector.length();
                String tag=selector.substring(index,end);
               if (end==selector.length()) index=end+1;else index=end;
                sb.append(lastOperator+tag);
                lastOperator="";
            }
        }
        String xPathText=sb.toString();       
        /* ************************************************************************************
         *                              1-perform the query
         * ************************************************************************************/
        if ( parentElement==null) parentElement=doc.getDocumentElement();
        //perform the xpath query
        if  (xPathText.equals("") ||  xPathText.equals("./") || (xPathText.equals("//")) )xPathText=xPathText+"*";//if only search for value, get all nodes
        XPathExpression expr = xpath.compile(xPathText);
        Object result = expr.evaluate(parentElement, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        ArrayList<Element> elements=new ArrayList<Element>();
        for (int i=0; i<nodes.getLength();i++){
            Node node=nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element element=(Element) node;
            elements.add(element);
        }
        //intersect with value
        ArrayList<Element> newElements=new ArrayList<Element>();
        if (value!=null){
            for(Element element:elements){
                if (element.getFirstChild().getNodeValue().equals(value)==true) newElements.add(element);
            }
            elements=newElements;
        }
            
            
        return elements;
    }
    
    private int getEndSeparatorIndex(String selector, int indexToStart){
        String[] separators=new String[]{" ","#","[",">"};
        int lastIndex=selector.length();
        if (lastIndex<=0) return 0;
        for(String separator:separators){
                int index=selector.indexOf(separator,indexToStart);
                if(index>-1 && index<lastIndex) lastIndex=index;
        }
        return lastIndex;
    }
    
    public Document getDOM(){
        return doc;
    }
    

    public String saveToString() throws Exception {
        Source domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(domSource, result);
        String text=writer.toString();
        int index=text.indexOf(">");
        if (text.substring(index+1,index+2).equals("\n")==false){
            text=text.substring(0,index+1)+"\n"+text.substring(index+1);
        }
        return text;
    }
    
    public void saveToFile(File file) throws Exception{
        Source domSource = new DOMSource(doc);
        FileWriter writer = new FileWriter(file);
        Result result = new StreamResult(writer);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(domSource, result);
        writer.flush();writer.close();
    }
    
    public static List<Element> getChildrenElements(Element parentElement){
    	List<Element> children=new ArrayList<Element>();
		NodeList nodes=parentElement.getChildNodes();
        for (int i=0; i<nodes.getLength();i++){
            Node node=nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element element=(Element) node;
            children.add(element);
        }
        return children;
    }
    
    
}

