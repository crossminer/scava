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

import java.util.StringTokenizer;
import java.util.*;

/**
* NNTPArticleHeader is used to handle the header data for an NNTPArticle.
* 
* 
* @author 	Scott MacDiarmid
* @version 	0.1
* @since    0.1
*/
public class NNTPArticleHeader implements NNTPError
{
    // Defines for valid header fields - required
    public final static String DATE                 = "Date";
    public final static String FROM                 = "From";
    public final static String SUBJECT              = "Subject";
    public final static String NEWSGROUPS           = "Newsgroups";
    public final static String MESSAGE_ID           = "Message-ID";
    public final static String PATH                 = "Path";
    // Defines for valid header fields - optional
    public final static String LINES                = "Lines";

    // Map of header fields to values
    private HashMap m_fieldMap;

    // The header response from the server without any changes
    private String m_uneditedHeader;


    /**
    * empty constructor
    *
    * @since        0.1
    */
    public NNTPArticleHeader()
    {
        m_fieldMap = new HashMap();
    }

    /**
    * adds a field to the header.  All values will be forced to be String's
    * in order to keep everything on a common ground. The client application
    * can convert the String to whatever data type is needed.
    *
    * @param fieldName      name of the field to be added
    * @param value          value of the field to be added
    * @return               NNTPError
    * @since                0.1
    */
    public int addField(String fieldName, String value)
    {
        int error = NNTPError.OK;
        Object existingRecord = null;

        try {

            // Attempt to add the field and value to the map, a null return 
            // means the add worked, if it is not null that means it replaced
            // an existing value, this may not be an error, it is up to the
            // client application to determine that.
            existingRecord = m_fieldMap.put(fieldName, value);
            if (existingRecord != null) {

                // A record already existed, let the calling app know
                error = NNTPError.HEADER_FIELD_CHANGED;
            }
        }
        catch (Exception e) {
            error = NNTPError.EXCEPTION;
        }

        return error;
    }

    /**
    * clears all data so it can be reset or used over again
    *
    * @since        0.1
    */
    public void clear()
    {
        // Clear the header string
        m_uneditedHeader = "";

        // Clear the map
        m_fieldMap.clear();
    }

    /**
    * returns a set of all field names currently held. To get the values out
    * use an Iterator and call getFieldValue on each field name.
    *
    * @return       Set of field names
    * @since        0.1
    */
    public Set getAllFieldNames()
    {
        Set retObject = null;

        try {
            // return the Set that relates to the Map, if null is returned
            // that signals an error
            retObject = m_fieldMap.keySet();
        }
        catch (Exception e) {
            retObject = null;
        }
        return retObject;
    }

    /**
    * returns the value for a specific field of the header.  If it returns null 
    * an error has occurred.
    *
    * @param fieldName  name of the field to return the value of
    * @return           value of the field if it existed, or null if it didn't
    * @since            0.1
    */
    public String getFieldValue(String fieldName)
    {
        String retObject = null;

        try {
            // Attempt to get the value out, if it is null
            // the key does not exist
            retObject = (String) m_fieldMap.get(fieldName);
        }
        catch (Exception e) {
            retObject = null;
        }

        return retObject;
    }

    /**
    * gets the unedited header string
    *
    * @return           unedited header data
    * @since            0.1
    */
    public String getUneditedHeader()
    {
        return m_uneditedHeader;
    }

    /**
    * will log a message to the screen.  
    *
    * @param msg            message to output
    * @return               none
    * @since                0.1
    */
    public void log(String msg)
    {
        System.out.println(msg);
    }

    /**
    * removes the field and associated value from the header.
    *
    * @param fieldName  name of the field to remove
    * @return           NNTPError
    * @since            0.1
    */
    public int removeField(String fieldName)
    {
        int error = NNTPError.OK;
        Object retObject = null;

        try {
            // Attempt to remove the field, if it returns null
            // that means the field did not exist in the map
            retObject = m_fieldMap.remove(fieldName);
            if (retObject == null) {
                error = NNTPError.HEADER_FIELD_DOESNOT_EXIST;
            }            
        }
        catch (Exception e) {
            error = NNTPError.EXCEPTION;
        }

        return error;
    }

    /**
    * set the header information from the head command response
    * All previous information will be cleared out
    *
    * @param response   complete server response string
    * @param clearold   true means all old values are cleared, false means
    *                   only an addition or update will take place
    * @return           NNTPError
    * @since            0.1
    */
    public int setFromHeadResponse(String response, boolean clearOld)
    {
        int error = NNTPError.OK;

        // clear old values
        if (clearOld) {
            clear();
        }

        // Store new unchanged string
        m_uneditedHeader = response;

        // Parse the string - break the string up into lines
        StringTokenizer lineToken = new StringTokenizer(response, "\n");
        String line = "xxx";
        String field = "";
        String tempValue;

        // Keep reading until the end is reached
        while (lineToken.hasMoreTokens()) {

            // Get the line from the response
            line = lineToken.nextToken();

            // If it is a single "." that is the signal of the end
            if (line.compareTo(".") == 0) {
                // we are done here
                break;
            }

            // Check to see if this is a continuation of the last line
            // It is signaled by starting with at least one space
            if (line.startsWith(" ") == true) {

                // Get the value from the last field
                tempValue = getFieldValue(field);
                if (tempValue != null) {

                    // FIXME: Need to trim all spaces from the line except one
                    tempValue += line;
                }
                else {
                    field = "";
                    line = "";
                    log("[Header] Parsing error! (multi-line)");
                }
            }
            else {

                // Parse the field name from the value
                // format should be field: value blah blah blah...
                StringTokenizer fieldToken = new StringTokenizer(line, ": ");

                // FIXME: Somehow error check to make sure field follows this
                // format, if nto at least 2 token error?
                field = fieldToken.nextToken();

                // Get the value out, we do this by substringing out the field
                // value.
                tempValue = line.substring(field.length() + 2, line.length());                
            }

            if (field != "") {
             
                // Add the field to the header
                error = addField(field, tempValue);
                if (error != NNTPError.OK) {

                    // Check to see if the field was just updated, if it was that is
                    // ok, it just means the field already had a value
                    if (error == NNTPError.HEADER_FIELD_CHANGED) {
                        log("[Header] updating '" + field + "'");
                        error = NNTPError.OK;
                    }
                    else {
                        log("[Header] Parsing error! (field line)");
                    }
                }
            }
        }

        return error;
    }
}
