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

import java.net.*;
import java.util.*;
import java.io.*;


public class URLX {
    
    private String urlAddress;
    public static final int TIMEOUT_MILLIS=1000;
    
    /**
     * Class to get url content in String format
     * @param urlAddress
     */
    public URLX(String urlAddress) {
       this.urlAddress=urlAddress;
    }
    
    /**
     * Get url response text
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public String get() throws MalformedURLException, IOException {
        return get(null);
    }
    
    /**
     * Get text by sending a querystring
     * @param queryString
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public String get(String queryString) throws MalformedURLException, IOException {
        String addr=urlAddress;
        if (queryString==null ||  queryString.equals("")) {
            addr=addr;
        }else{
           // addr=addr+"?"+URLEncoder.encode(queryString, "UTF-8"); //cannot encode since request won't handle those encoded params
           addr=addr+"?"+queryString;
        }
        URL url=new URL(addr);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(TIMEOUT_MILLIS);
        return readConnection(con);
    }
    
    public String post(Map<String,String> params) throws MalformedURLException, IOException {
        URL url=new URL(urlAddress);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(TIMEOUT_MILLIS);
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        PrintStream outStream = new PrintStream(con.getOutputStream());
        StringBuilder sbout=new StringBuilder();
        for(String key:params.keySet()){
            String value=URLEncoder.encode(params.get(key), "UTF-8");   
            if (sbout.length()>0) sbout.append("&");
            sbout.append(key);sbout.append("=");sbout.append(value);
        }
        String data=sbout.toString();
        outStream.print(data);
        outStream.close();
        return readConnection(con);
        
    }
    
    private String readConnection(URLConnection con) throws IOException{
        DataInputStream dis = new DataInputStream(con.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(dis));
        String inputLine;
        StringBuilder sb=new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);sb.append(System.getProperty("line.separator"));
        }
        br.close();
        dis.close();
        return sb.toString();
    }
    
}
    
