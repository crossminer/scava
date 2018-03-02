/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
//////////////////////////////////////////////////////////////////////////////
//  This file is part of jnntpe, an NNTP client library.
//  Copyright (C) 2001 Scott MacDiarmid
//  
//  This program  is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//////////////////////////////////////////////////////////////////////////////
package org.eclipse.scava.repository.model.eclipse.importer.util.NNTP;

import java.net.*;
import java.io.*;

/**
* NNTPRaw is a class that communicates with a news(nntp) server. It handles 
* all the communication between the calling application and the news server.
* The functionality supplied is based off the commands from RFC 977 and RFC
* 2980. All specific information about what a command does can be found in
* those documents and will not be re-documented in the methods description. 
* And specific usage of parameters for the command methods can also be found
* there.  If there is a difference between what the RFC says and what the 
* takes it will be documented. Methods that do not use a command will have 
* a description attached and exact parameter usage.
*
* Look at nntp_responses.html for possible response strings or the RFC 
* documents.
* 
* @author 	Scott MacDiarmid
* @version 	0.1
* @since    0.1
*/
public class NNTPRaw implements NNTPError
{
    // Defines for 'list' command
    final static String LIST_NONE = "";
    final static String LIST_ACTIVE = "ACTIVE";
    final static String LIST_ACTIVE_TIMES = "ACTIVE.TIMES";
    final static String LIST_DISTRIBUTIONS = "DISTRIBUTIONS";
    final static String LIST_NEWSGROUPS = "NEWSGROUPS";
    final static String LIST_SUBSCRIPTIONS = "SUBSCRIPTIONS";
    final static String LIST_DISTRIBUTION_PATS = "DISTRIB.PATS";
    final static String LIST_OVERVIEW_FMT = "OVERVIEW.FMT";

    // Define the keys needed for 'authinfo'
    final static String USER_KEY = "USER";
    final static String PASS_KEY = "PASS";

    // Define constants for 'article' flags
    final static int BY_ARTICLEID = 1;
    final static int BY_ARTICLENUMBER = 2;

    /** Reads the incoming data off the socket */
    private BufferedReader m_inputStream;    		
    /** Writes to the socket connection */
    private PrintWriter m_outputStream;
    /** Socket connection */
    private Socket m_socket;

    /**
    * Empty constructor
    *
    * @since        0.1
    */
    public NNTPRaw()
    {
        m_socket = null;
    }

    /**
    * handles the 'authinfo' command. 
    *
    * @param key        should be 'USER' or 'PASS'
    * @param value      if key is 'USER' value is the username
    *                   if key is 'PASS' value is the password
    * @return           NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse authinfo(String key, String value)
    {
        String request;
        NNTPRawResponse response;

        try {
            // Validate incoming parameters
            if (key == null) {
                response = new NNTPRawResponse();
                response.set(NNTPError.INVALID_PARAMETER, "Key can not be null");
                return response;
            }
            if (key != USER_KEY && key != PASS_KEY) {
                response = new NNTPRawResponse();
                response.set(NNTPError.INVALID_PARAMETER, "Key must be USER_KEY or PASS_KEY");
                return response;
            }
            if (value == null) {
                response = new NNTPRawResponse();
                response.set(NNTPError.INVALID_PARAMETER, "Value can not be null");
                return response;
            }  
            if (key ==  PASS_KEY) 
               request = "AUTHINFO " + key + " " + value + "\r\n";
            else 
            	request = "AUTHINFO " + key + " " + value + "\r\n";
            
            

            response = sendCommand(request);
        }
        catch (Exception e) {
            response = new NNTPRawResponse();
            response.set(NNTPError.EXCEPTION, "Exception thrown in [authinfo]");
        }

        return response;
    }

    /**
    * handles the 'body' command. 
    *
    * @param articleID  articleID to retrieve the header for
    * @return           NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse body(int articleID)
    {
        String request;

        request = "BODY " + articleID + "\r\n";

        NNTPRawResponse response = sendCommand(request);

        return response;
    }

    /**
    * opens a connection to the server.
    *
    * @param hostname   hostname to connect to
    * @param port       port to connect to
    * @return           NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse connect(String hostname, int port)
    {
        NNTPRawResponse response = new NNTPRawResponse();
        String serverString = "";

       try {
           
            // If there is no hostname return an error
            if (hostname == null) {
                response.set(NNTPError.NOHOSTNAME, "");
                return response;
            }

            // If the port is zero or less than zero return an error
            if (port <= 0) {
                response.set(NNTPError.INVALIDPORT, "");
                return response;
            }

            // Attempt to open a connection to the news server
            m_socket = new Socket(hostname, port);

            // Setup the streams to read and write information
            // to the connection
		    m_inputStream = new BufferedReader(
                new InputStreamReader(m_socket.getInputStream()));
		    m_outputStream = new PrintWriter(m_socket.getOutputStream());
            

            // Read the initialize response the server sends
            serverString = m_inputStream.readLine();

            //
            response.set(NNTPError.OK, serverString);
        }
        catch (IOException e) {
            response.set(NNTPError.SOCKET_ERROR, "Unable to connect to " + hostname + " " + port);
        }
        catch (Exception e) {
            response.set(NNTPError.EXCEPTION, "Unable to connect to " + hostname + " " + port);
        }
        
        return response;
    }        

    /**
    * handles the 'group' command.
    *
    * @param groupName  full name of the newsgroup 
    * @return           NNTPRawResponse object
    * @since            0.1
    */
    public NNTPRawResponse group(String groupName)
    {
        String request;

        request = "GROUP " + groupName + "\r\n";
            
        NNTPRawResponse response = sendCommand(request);

        return response;
    }

    /**
    * handles the 'head' command. 
    *
    * @param articleID  articleID to retrieve the header for
    * @return           NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse head(int articleID)
    {
        String request;

        request = "HEAD " + articleID + "\r\n";

        NNTPRawResponse response = sendCommand(request);

        return response;
    }

    /**
    * handles the 'help' command.
    *
    * @param response   stores the response string from the server
    * @return           NNTPError object
    * @since            0.1
    */
    public NNTPRawResponse help()
    {
        String request = "HELP\r\n";

        NNTPRawResponse response = sendCommand(request);
        
        return response;
    }

    /**
    * handles the 'list' command.
    *
    * @param type       One of the LIST_ defines from above
    * @param wildmat    Pattern to match in the serach.  Can be null
    * @return           NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse list(String type, String wildmat)
    {
        String request;
        
        request = "LIST";

        // Check whether there is more
        if (type != LIST_NONE) {

            // Add the type to the request
            request += " " + type;

            // Check for a pattern to match
            if (wildmat != null) {
                request += " " + wildmat;
            }
        }

        // Terminate request line
        request += "\r\n";

        // Send the command in
        NNTPRawResponse response = sendCommand(request);
        
        return response;            
    }

    /**
    * handles the 'listgroup' command.
    *
    * @param groupName  fullname of the newsgroup
    * @return           NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse listgroup(String groupName)
    {
        String request;
        
        // Build the request
        request = "LISTGROUP";

        // IF there is no group name it will list the current group
        if (groupName != null) {
            request += " "  + groupName;
        }

        // end request
        request += "\r\n";

        NNTPRawResponse response = sendCommand(request);
        
        return response;
    }

    /**
    * handles the 'mode reader' command.
    *
    * @return           NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse mode_reader()
    {
        String request;

        request = "MODE READER\r\n";
        
        NNTPRawResponse response = sendCommand(request);
        
        return response;            
    }

    /**
    * handles the 'newgroups' command. 
    *
    * @param date               YYMMDD - last two digits of the year
    * @param time               HHMMSS based on the 24 hour clock
    * @param isGMT              signal whether the time sent in is GMT time
    * @param distributionList   ?
    * @return                   NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse newgroups(String date, String time, boolean isGMT, String distributionList)
    {
        // Build the string to send
        String request = "NEWGROUPS " + date + " " + time;

        // Add the GMT flag [optional]
        if (isGMT) {
            request += " " + "GMT";
        }

        // Add the distribution list to the request [optional]
        if (distributionList != null) {
            request += " " + distributionList;
        }

        // Add the termination characters
        request += "\r\n";

        NNTPRawResponse response = sendCommand(request);
        
        return response;            
    }

    /**
    * handles the 'newnews' command.
    *
    * @param groupName          fullname of the news group
    * @param date               YYMMDD - last two digits of the year
    * @param time               HHMMSS based on the 24 hour clock
    * @param isGMT              signal whether the time sent in is GMT time
    * @param distributionList   ?
    * @return                   NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse newnews(String groupName, String date, String time, boolean isGMT, String distributionList)
    {
        // Build the string to send
        String request = "NEWNEWS " + groupName + " " + date + " " + time;

        // Add the GMT flag [optional]
        if (isGMT) {
            request += " " + "GMT";
        }

        // Add the distribution list to the request [optional]
        if (distributionList != null) {
            request += " " + distributionList;
        }

        // Add the termination characters
        request += "\r\n";

        NNTPRawResponse response = sendCommand(request);
        
        return response;
    }

    /**
    * handles the 'quit' command.
    *
    * @return           NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse quit()
    {  
        String request;

        request = "QUIT\r\n";

        NNTPRawResponse response = sendCommand(request);
        
        return response;            
    }

    /**
    * gets the next response message from the server.  If a command returns
    * multiple rows of data this can be called until you reach the data
    * terminator line (".")
    *
    * @return           NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse readMore()
    {
        NNTPRawResponse response = new NNTPRawResponse();
        String serverString;

        try {
		    
            // Read the response
            serverString = m_inputStream.readLine();
            if (serverString == null) {
                response.set(NNTPError.SOCKET_ERROR, "Unable to read response");
            }
            else {
                response.set(NNTPError.OK, serverString);
            }
        }
        catch (IOException e) {
            response.set(NNTPError.SOCKET_ERROR, "Unable to read next response");
        }
        catch (Exception e) {
            response.set(NNTPError.EXCEPTION, "Exception thrown while reading next line");
        }

        return response;
    }

    /**
    * generic way to send commands to the server and read the initial response.
    *
    * @param command    command to send to the server
    * @return           NNTPRawResponse        
    * @since            0.1
    */
    public NNTPRawResponse sendCommand(String command)
    {
        NNTPRawResponse response = new NNTPRawResponse();
        String serverString = "";

        try {
            
            // Send the command over the socket
            m_outputStream.print(command);
            m_outputStream.flush();
		    
            // Read the response
            serverString  = m_inputStream.readLine();
            if (serverString == null) {
                // Socket probably dropped
                response.set(NNTPError.SOCKET_ERROR, "Unable to read response");
            }
            else {
                // Store the response string
                response.set(NNTPError.OK, serverString);
            }
        }
        catch (IOException e) {
            response.set(NNTPError.SOCKET_ERROR, "Unable to send request");
        }
        catch (Exception e) {
            response.set(NNTPError.EXCEPTION, "Exception thrown while sending request");
        }

        return response;
    }
    

    /**
    * handles the 'xgtitle' command.
    *
    * @param match              string to match in search. See pattern
    *                           matching in the RFC documents.
    * @return                   NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse xgtitle(String match)
    {
        String request;

        // Build the request
        request = "XGTITLE";

        // If there is no matching string skip it [optional]
        if (match != null) {
            request += " " + match;
        }

        // end request
        request += "\r\n";

        NNTPRawResponse response = sendCommand(request);
        
        return response;            
    }

    /**
    * handles the 'xhdr' command.
    *
    * @param headerline         item in the header to read out. 
    * @param articleRange       range of article ID's to get
    * @return                   NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse xhdr(String headerLine, String articleRange)
    {
        String request;

        // Build the request
        request = "XHDR" + headerLine;

        // articleRange [optional]
        if (articleRange != null) {
            request += " " + articleRange;
        }

        // end request
        request += "\r\n";

        NNTPRawResponse response = sendCommand(request);
        
        return response;            
    }

    /**
    * handles the 'xindex' command.
    *
    * @param groupName          fullname of the newsgroup
    * @return                   NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse xindex(String groupName)
    {                
        String request;

        request = "XINDEX " + groupName + "\r\n";

        NNTPRawResponse response = sendCommand(request);
        
        return response;            
    }

    /**
    * handles the 'xover' command.
    *
    * @param articleRange       range of article ID's
    * @return                   NNTPRawResponse
    * @since            0.1
    */
    public NNTPRawResponse xover(String articleRange)
    {                        
        String request = "XOVER";

        if (articleRange != null) {
            request += " " + articleRange;
        }

        request += "\r\n";

        NNTPRawResponse response = sendCommand(request);
        
        return response;
    }

    /**
    * handles the 'xpat' command.
    *
    * @param headerLine         item from the header to get
    * @param articleRange       range of articles to search
    * @param pattern            See RFC for details
    * @return                   NNTPRawResponse
    *                           returned
    * @since            0.1
    */
    public NNTPRawResponse xpat(String headerLine, String articleRange, String pattern)
    {
        String request;

        // Build the request
        request = "XPAT" + headerLine + " " + articleRange + " " + pattern + "\r\n";

        NNTPRawResponse response = sendCommand(request);

        return response;
    }
}
