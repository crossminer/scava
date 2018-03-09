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

/**
* NNTPArticleBody is used to handle the body element of an article.  It will
* do the translatation from server response to meaningful data.
* 
* @author 	Scott MacDiarmid
* @version 	0.1
* @since    0.1
*/
public class NNTPArticleBody
{
    // The unedited body text
    private String m_uneditedBody;

    /**
    * empty constructor
    *
    * @since            0.1
    */
    public NNTPArticleBody()
    {
        clear();
    }

    /**
    * clears the current body from memory.  This can be used to lower memory
    * usage or to reuse the object
    *
    * @since            0.1
    */
    public void clear()
    {
        m_uneditedBody = "";
    }

    /**
    * gets the unedited body text.  This can be used if an application wants
    * to translate the information itself.
    *
    * @return           unedited body text
    * @since            0.1
    */
    public String getUneditedBody()
    {
        return m_uneditedBody;
    }

    /**
    * sets the body data from the 'body' command response.  It will also clear
    * any old data from the body.
    *
    * @param response   complete server response string
    * @return           NNTPError value
    * @since            0.1
    */
    public int setFromBodyResponse(String response)
    {
        int error = NNTPError.OK;

        // Clear the old data
        clear();

        // Store the string unchanged
        m_uneditedBody = response;

        // Translate the string into something useful
        // FIXME: Still trying to figure out how ot make it useful

        return error;
    }
}
