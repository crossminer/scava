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

/**
* NNTPGroup holds information about a newsgroup.  
* 
* @author 	Scott MacDiarmid
* @version 	0.1
* @since    0.1
*/
public class NNTPGroup implements Comparable<NNTPGroup>
{
    // fullname of the newsgroup
    private String m_name;
    // ID of the last message
    private int m_lastMessageID;
    // ID of the first message
    private int m_firstMessageID;
    // whether posting to this group is allowed 
    private boolean m_postingAllowed;
    // estimation of the number of messages in the group
    private int m_estimatedCount;

    /**
    * Empty constructor
    *
    * @since        0.1
    */
    public NNTPGroup()
    {
        // Initialize member variables to default values
        m_name = "none";
        m_lastMessageID = -1;
        m_firstMessageID = -1;
        m_postingAllowed = false;
        m_estimatedCount = -1;
    }

    /**
    * populates variables based on a 'group' command response.  This response
    * should be in the following format: see RFC for more details
    * xxx newsgroup 0000001 0000002 0000003
    *   xxx: code that was returned
    *   newsgroup: name of the newsgroup
    *   0000001: estimated count of the number of messages, can be any number
    *   0000002: ID of the first message
    *   0000003: ID of the last message, if this is larger than the ID of the
    *            ID fo the first message there are no messages
    * @return       none
    * @since        0.1
    */
    public void buildFromGroupResponse(String response)
    {
        // items are seperated by a space
        StringTokenizer strToken = new StringTokenizer(response, " ");

        // get the code
        String code = strToken.nextToken();

        // get the estimated count
        m_estimatedCount = Integer.parseInt(strToken.nextToken());

        // get the first message ID
        m_firstMessageID  = Integer.parseInt(strToken.nextToken());

        // get the last message ID
        m_lastMessageID = Integer.parseInt(strToken.nextToken());
    }

    /**
    * populates variable based on a 'newgroups' command response.
    *
    * @return       none
    * @since        0.1
    */
    public void buildFromNewGroupsResponse(String response)
    {
        StringTokenizer strToken = new StringTokenizer(response, " ");
        m_name = strToken.nextToken();

        m_lastMessageID = Integer.parseInt(strToken.nextToken());
        m_firstMessageID  = Integer.parseInt(strToken.nextToken());

        // If the last messageID is less then the first messageID then
        // the group is empty
        if (m_lastMessageID < m_firstMessageID) {
            m_lastMessageID = -1;
            m_firstMessageID = -1;
        }

        if (strToken.nextToken() == "y") {
            m_postingAllowed = true;
        }
        else {
            m_postingAllowed = false;
        }
    }

    /**
    * used for sorting
    *
    * @return       true if posting is allowed, false if not
    * @since        0.1
    */
    public int compareTo(NNTPGroup obj)
    {
        int result = m_name.compareTo(((NNTPGroup)obj).m_name);
        return result;
    }

    /**
    * returns the estimatedCount variable
    *
    * @return       estimated number of message for group
    * @since        0.1
    */
    public int getEstimatedCount()
    {
        return m_estimatedCount;
    }

    /**
    * returns the firstMessageID variable currently held
    *
    * @return       first message ID of the group
    * @since        0.1
    */
    public int getFirstMessageID()
    {
        return m_firstMessageID;
    }

    /**
    * returns the lastMessageID variable currently held
    *
    * @return       last message ID for the group
    * @since        0.1
    */
    public int getLastMessageID()
    {
        return m_lastMessageID;
    }

    /**
    * returns the name variable currently held
    *
    * @return       newsgroup name
    * @since        0.1
    */
    public String getName()
    {
        return m_name;
    }

    /**
    * returns whether posting is allowed to this group
    *
    * @return       true if posting is allowed, false if not
    * @since        0.1
    */
    public boolean getPostingAllowed()
    {
        return m_postingAllowed;
    }

    /**
    * sets the posting allowed flag
    *
    * @param on     if true then posting is allowed, otherwise it is not
    * @return       none
    * @since        0.1
    */
    public void setAllowPosting(boolean on)
    {
        m_postingAllowed = on;
    }

    /**
    * stores the estimated count
    *
    * @param count  estimated count
    * @return       none
    * @since        0.1
    */
    public void setEstimatedCount(int count)
    {
        m_estimatedCount = count;
    }

    /**
    * stores the first message ID
    *
    * @param id     ID of the first message
    * @return       none
    * @since        0.1
    */
    public void setFirstMessageID(int id)
    {
        m_firstMessageID = id;
    }

    /**
    * stores the last message ID
    *
    * @param id     ID of the last message
    * @return       none
    * @since        0.1
    */
    public void setLastMessageID(int id)
    {
        m_lastMessageID = id;
    }

    /**
    * stores a new group name
    *
    * @param name   newsgroup name
    * @return       none
    * @since        0.1
    */
    public void setName(String name)
    {
        m_name = name;
    }

    /**
    * overrides the toString to return the newsgroup name
    *
    * @return       newsgroup name
    * @since        0.1
    */
    public String toString()
    {
        return m_name;
    }

};
