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

import java.util.*;

/**
* NNTPSession is used as the developers API.  This class will hide the
* implemenation of the low level NNTP commands.  The raw NNTP data will 
* transformed into the correct objects so the developer never has to mess
* around with the raw nntp streams if they don't want to.
*
* 
* @author 	Scott MacDiarmid
* @version 	0.2
* @since    0.1
*/
public class NNTPSession implements NNTPError
{
    /** Holds the host information given back */
    private String m_hostInfo;
    /** Holds the hostname to connect to */
	private String m_hostname;
    /** Holds the last error message */
    private String m_lastServerErrorMessage;
    /** Holds the password to use when logging on */
	private String m_password;
    /** Stores the port to connect to */
    private int m_port;
    /** Holds the username to use when loggin on */
    private String m_username;
    /** Flag to show whether server allows posting */
    private boolean m_isPostingAllowed;
    /** This will be the communication piece to access the server */
    private NNTPRaw m_nntpAccess;

    /**
    * empty constructor
    *
    * @since        0.1
    */
    public NNTPSession()
    {
        // Set default information
        setHostname("news");
        setPort(119);
        m_nntpAccess = new NNTPRaw();
    }
    
    /**
    * allows the Session to be constructed with a hostname
    *
    * @since        0.2
    */
    public NNTPSession(String hostname)
    {
        // Set default information
        setHostname(hostname);
        setPort(119);
        m_nntpAccess = new NNTPRaw();
    }

    /**
    * allows the Session to be constructed with a hostname and port
    *
    * @since        0.2
    */
    public NNTPSession(String hostname, int port)
    {
        // Set default information
        setHostname(hostname);
        setPort(port);
        m_nntpAccess = new NNTPRaw();
    }

    /**
    * gets the all the articles for a group
    *
    * @param group      group object with at least the group name populated
    * @param articles   vector that will hold the article ID, must of already
    *                   been created
    * @return           NNTPError.OK if everything went ok, an NNTPError value
    *                   otherwise
    * @since            0.1
    */
    public int getAllArticleIDs(NNTPGroup group, Vector articles)
    {
        NNTPRawResponse response;
        String serverResponse = "";
        int error = NNTPError.OK;

        // Check the incoming parameters for null
        if (group == null || articles == null) {
            m_lastServerErrorMessage = "group or articles parameters can not be null";
            return NNTPError.INVALID_PARAMETER;

        }

        // Make the request
        response = m_nntpAccess.listgroup(group.getName());
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();
        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
            return error;
        }

        // Make sure the server accepted the request
        error = validateResponse("211", serverResponse);
        if (error != NNTPError.OK) {
            return error;                
        }

        // Read the next line of the response
        response = m_nntpAccess.readMore();
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();

        // If the record compares to a '.' that signals the end
        while (error == NNTPError.OK && (serverResponse.compareTo(".") != 0)) {

            Integer id = new Integer(serverResponse);

            // Add the article ID to the list
            articles.add(id);

            // Read the next line of the response
            response = m_nntpAccess.readMore();
            error = response.getErrorValue();
            serverResponse = response.getServerResponse();
        }

        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
        }

        return error;
    }

    /**
    * gets every group on the server
    *
    * @param groupList  will hold all the groups returned
    * @return           NNTPError.OK if everything went ok, an NNTPError value
    *                   otherwise
    * @since            0.1
    */
    public int getAllGroups(Vector groupList)
    {
        NNTPRawResponse response;
        String serverResponse = "";
        int error = NNTPError.OK;
        
        // clear the incoming list
        groupList.clear();

        // Request all groups from the server
        response  = m_nntpAccess.list(NNTPRaw.LIST_ACTIVE, null);
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();
        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
            return error;
        }

        // 
        error = validateResponse("215", serverResponse);
        if (error != NNTPError.OK) {
            return error;                
        }

        // Read the next line of the response
        response = m_nntpAccess.readMore();
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();

        // If the record starts with a '.' that signals the end
        while (error == NNTPError.OK && (serverResponse.compareTo(".") != 0)) {

            //FIXME: Need to validate response make sure it is not an error

            // Create a new group
            NNTPGroup newGroup = new NNTPGroup();            
            newGroup.buildFromNewGroupsResponse(serverResponse);

            // Add it to the list
            groupList.add(newGroup);

            // Read the next line of the response
            response = m_nntpAccess.readMore();
            error = response.getErrorValue();
            serverResponse = response.getServerResponse();
        }

        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
        }

        // Put the groups in alphabetical order
        Collections.sort(groupList);

        return error;
    }

    /**
    * gets the body information for an article.  The group the article is
    * in must have been set to the active group.
    *
    * @param article    Article object with at least the articleID populated 
    * @return           NNTPError.OK if everything went ok, an NNTPError value
    *                   otherwise
    * @since            0.1
    */
    public int getArticleBody(NNTPArticle article)
    {
        NNTPRawResponse response;
        String serverResponse = "";
        int error = NNTPError.OK;

        // Send the message to get the header
        response = m_nntpAccess.body(article.getArticleID());
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();
        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
            return error;
        }

        //     
        error = validateResponse("222", serverResponse);
        if (error != NNTPError.OK) {
            return error;
        }

        // Create a String to hold the response data
        // Thsi will be a multi-line string
        String bodyLines = new String("");

        // serverResponse and error should be intialized to items that
        // will allow it to drop into the loop the first time
        // serverResponse will be 222 something and error will be OK
        while (serverResponse.compareTo(".") != 0 && error == NNTPError.OK) {

            // Keep reading lines until the end is reached
            response = m_nntpAccess.readMore();
            error = response.getErrorValue();
            serverResponse = response.getServerResponse();

            // Add the response to the String with a null terminator since
            // the readMore command removes the null terminator when it 
            // reads a line out
            bodyLines += serverResponse + "\n";
        }

        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
        }
        else {
            // Update the body object in the article
            article.getBody().setFromBodyResponse(bodyLines);
        }

        return error;
    }
        
    /**
    * gets the header information for an article.  The group the article is
    * in must have been set to the active group.
    *
    * @param articleID  ID of the article 
    * @return           NNTPError.OK if everything went ok, an NNTPError value
    *                   otherwise
    * @since            0.1
    */
    public int getArticleHeader(NNTPArticle article)
    {
        NNTPRawResponse response;
        String serverResponse = "";
        int error = NNTPError.OK;

        // Send the message to get the header
        response = m_nntpAccess.head(article.getArticleID());
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();
        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
            return error;
        }

        // make sure the server accepted the request
        error = validateResponse("221", serverResponse);
        if (error != NNTPError.OK) {
            return error;
        }

        String headerLines = new String("");

        // serverResponse and error should be intialized to items that
        // will allow it to drop into the loop the first time
        // serverResponse will be 222 something and error will be OK
        while (serverResponse.compareTo(".") != 0 && error == NNTPError.OK) {

            // Read a line
            response = m_nntpAccess.readMore();
            error = response.getErrorValue();
            serverResponse = response.getServerResponse();

            // Add the response to the String with a null terminator since
            // the readMore command removes the null terminator when it 
            // reads a line out
            headerLines += serverResponse + "\n";            
        }

        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
        }
        else {
            // Update the article header
            article.getHeader().setFromHeadResponse(headerLines, true);
        }

        return error;
    }

    /**
    * gets information for a certain field in the header for an article
    * The article must be in the group that is currently set active.
    * This provides a way to only pull back fields your client is interested
    * in and not the whole header.
    *
    * @param article    ARticle object with articleID popualtes
    * @param fieldName  Field in the header to retrieve
    * @return           NNTPError.OK if everything went ok, an NNTPError value
    *                   otherwise
    * @since            0.1
    */
    public int getArticleHeaderField(NNTPArticle article, String fieldName)
    {
        NNTPRawResponse response;
        String serverResponse = "";
        int error = NNTPError.OK;

        // Send the message to get the header
        response = m_nntpAccess.xhdr(fieldName, Integer.toString(article.getArticleID()));
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();
        if (error != NNTPError.OK) {    
            m_lastServerErrorMessage = serverResponse;
            return error;
        }

        //     
        error = validateResponse("221", serverResponse);
        if (error != NNTPError.OK) {
            return error;
        }

        String headerLine = "";

        // Keep looping until end is reached
        while (serverResponse.compareTo(".") != 0 && error == NNTPError.OK) {
            
            // Get the next line
            response = m_nntpAccess.readMore();
            error = response.getErrorValue();
            serverResponse = response.getServerResponse();

            headerLine += serverResponse;
        }

        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
        }
        else {
            // Add the line into the vector
            article.getHeader().setFromHeadResponse(headerLine, false);
        }

        return error;
    }

    /**
    * returns the host information string
    *
    * @return       host information
    * @since        0.1
    */
    public String getHostInfo()
    {
        return m_hostInfo;
    }

    /**
    * returns the host name
    *
    * @return       hostname
    * @since        0.1
    */
    public String getHostname()
    {
        return m_hostname;
    }

    /**
    * gets the new groups since a certain time and date
    *
    * @param date       date in the format YYMMDD
    * @param time       time in the format HHMMSS
    * @param isGMTTime  true if the time is GMT, false otherwise
    * @param groupList  will hold all the groups returned
    * @return           NNTPError.OK if everything went ok, an NNTPError value
    *                   otherwise
    * @since            0.1
    */
    public int getNewGroups(String date, String time, boolean isGMTTime, Vector groupList)
    {
        NNTPRawResponse response;
        String serverResponse = "";
        int error = NNTPError.OK;
        
        // clear the incoming list
        groupList.clear();

        // Request new groups from the server
        response  = m_nntpAccess.newgroups(date, time, isGMTTime, null);
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();
        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
            return error;
        }

        // 231 means more data is coming
        error = validateResponse("231", serverResponse);
        if (error != NNTPError.OK) {
            return error;                
        }

        // Read the next line of the response
        response = m_nntpAccess.readMore();
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();

        // If the record starts with a '.' that signals the end
        while (error == NNTPError.OK && (serverResponse.compareTo(".") != 0)) {

            //FIXME: Need to validate response make sure it is not an error

            // Create a new group
            NNTPGroup newGroup = new NNTPGroup();            
            newGroup.buildFromNewGroupsResponse(serverResponse);

            // Add it to the list
            groupList.add(newGroup);

            // Read the next line of the response
            response = m_nntpAccess.readMore();
            error = response.getErrorValue();
            serverResponse = response.getServerResponse();
        }

        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
        }

        return error;
    }

    /**
    * gets the port that is current set
    *
    * @return           port number
    * @since            0.1
    */
    public int getPort()
    {
        return m_port;
    }

    /**
    * allows access to the last error message the server returned
    *
    * @return           the last message 
    * @since            0.1
    */
    public String lastServerErrorMessage()
    {
        return m_lastServerErrorMessage;
    }

    /**
    * authenticates with the news server.  This is for internal use only
    *
    * @param username   username
    * @param password   password
    * @return           NNTPError.OK if everything went ok, an NNTPError value
    *                   otherwise
    * @since            0.1
    */
    private int logon(String username, String password)
    {
        NNTPRawResponse response;
        String serverResponse = "";
        int error = NNTPError.OK;

        // Store the username and password for reconnection purposes
        m_username = username;
        m_password = password;

        // Send the username
        response = m_nntpAccess.authinfo(NNTPRaw.USER_KEY, m_username);
        
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();
        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
            return error;
        }
        response = m_nntpAccess.authinfo(NNTPRaw.PASS_KEY, m_password);
        // This should be 3xx - meaning it needs a password also
        error = validateResponse("3", serverResponse);
        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
            return error;                
        }
		
        // Send the password
        
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();
        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
            return error;
        }
		
        // This should be 2xx
        error = validateResponse("2", serverResponse);        
	
        return error;
    }

    /**
    * removes the first 4 characters of the response string
    *
    * @param response   response string from the server
    * @return           adjusted response string
    * @since            0.1
    */
    private String removeCodeFromResponse(String response)
    {
        String newString;

        // Use substring to remove the first 4 characters
        // It should be the 3 for the code and 1 for the space
        newString = response.substring(4, response.length());

        return newString;
    }

    /**
    * sets a newsgroup's to be the active newsgroup
    *
    * @param group  group object with at least the newsgroup name populated
    * @return       NNTPError.OK if everything went ok, an NNTPError value
    *               otherwise
    * @since        0.1
    */
    public int setActiveGroup(NNTPGroup group)
    {
        NNTPRawResponse response;
        String serverResponse;
        int error = NNTPError.OK;
                
        // 
        response = m_nntpAccess.group(group.getName());
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();
        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
            return error;
        }

        // Valid response should start with 211
        error = validateResponse("211", serverResponse);
        if (error != NNTPError.OK) {
            return error;                
        }

        // Populate the group object with the data that came back
        group.buildFromGroupResponse(serverResponse);        

        return NNTPError.OK;
    }

    /**
    * sets the hostname that is used for connecting
    *
    * @param hostname   name of the server to connect to(new.something.com)
    * @return           none
    * @since            0.1
    */
    public void setHostname(String hostname)
    {
        m_hostname = hostname;
    }

    /**
    * sets the port that is used for connecting
    *
    * @param port       port the news server is on
    * @return           none
    * @since            0.1
    */
    public void setPort(int port)
    {
        m_port = port;
    }

    /**
    * starts the session
    *
    * @return       NNTPError.OK if everything went ok, an NNTPError value
    *               otherwise
    * @since        0.1
    */
    public int start()
    {
        return start(null, null);
    }

    /**
    * starts the session with authentication
    *
    * @param username       username
    * @param password       password
    * @return               NNTPError.OK if everything went ok, an NNTPError value
    *                       otherwise
    * @since                0.1
    */
    public int start(String username, String password)
    {
        NNTPRawResponse response;
        int error;
        String serverResponse;

        // Attempt to open a connection to the server
        response = m_nntpAccess.connect(m_hostname, m_port);
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();
        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
            return error;
        }

        // Validate response 2xx is a valid response
        error = validateResponse("2", serverResponse);
        if (error != NNTPError.OK) {
            return error;
        }

        // Store the host info
        m_hostInfo = removeCodeFromResponse(serverResponse);

        // Based on the response code we can tell if posting is allowed
        // 201 means posting is not allowed, 200 means it is
        if (serverResponse.charAt(2) == '1') {
            m_isPostingAllowed = false;
        }
        else {
            m_isPostingAllowed = true;
        }            
        
        // If the username is null there is no logon needed
        // otherwise attempt to logon to the server
        if (username != null) {

            // Logon to the server
            error = logon(username, password);            
        }

        return error;
    }

    /**
    * stops the session
    *
    * @return       NNTPError.OK if everything went ok, an NNTPError value
    *               otherwise
    * @since        0.1
    */
    public int stop()
    {
        NNTPRawResponse response;
        String serverResponse = "";
        int error = NNTPError.OK;

        // Send a quit message
        response = m_nntpAccess.quit();
        error = response.getErrorValue();
        serverResponse = response.getServerResponse();
        if (error != NNTPError.OK) {
            m_lastServerErrorMessage = serverResponse;
            return error;
        }        
		
        // 205 means the server acknowledged the quit      
        error = validateResponse("205", serverResponse);
        if (error != NNTPError.OK) {
            return error;
        }        

        return error;
    }

    /**
    * validates the response code with a code that is passed in
    *
    * @param code       code that should be checked
    * @param response   response from the server
    * @return           true if it matched, false if it doesn't
    * @since            0.1
    */
    private int validateResponse(String code, String response)
    {
        // Check to see if the string starts with the correct code
        boolean result = response.startsWith(code);
        if (result == false) {
            
            // Store this so the client can display the server response if it needs to
            m_lastServerErrorMessage = response;

            // Connection closed
            if (response.startsWith("500") == true) {
                return NNTPError.CLOSEDCONNECTION;
            }
            if (response.startsWith("480") == true) {
                return NNTPError.AUTHENTICATION_REQUEST_480;
            }
            // No permission to do the request action
            if (response.startsWith("502") == true) {
                return NNTPError.ACCESSDENIED;
            }
        
            // Group does not exist
            if (response.startsWith("411") == true) {
                return NNTPError.GROUPDOESNOTEXIST;
            }

            // Unknown - not standard message
            return NNTPError.GENERALERROR;
        }

        return NNTPError.OK;
    }
}
