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
* NNTPArticle holds information about an article.  
*
* @author 	Scott MacDiarmid
* @version 	0.1
* @since    0.1
*/
public class NNTPArticle
{
    // Define an invalid article ID for error checking
    static final public int INVALID_ARTICLE_ID = -1;

    // Member variables
    private int m_articleID = 0;
    private NNTPArticleHeader m_header;
    private NNTPArticleBody m_body;

    /**
    * empty constructor
    *
    * @since        0.1
    */
    public NNTPArticle()
    {
        m_header = new NNTPArticleHeader();
        m_body = new NNTPArticleBody();
    }

    /**
    * allows the articleID to be set
    *
    * @param articleID  articleID this object should have
    * @since            0.1
    */
    public NNTPArticle(int articleID)
    {
        m_articleID = articleID;
        m_header = new NNTPArticleHeader();
        m_body = new NNTPArticleBody();
    }

    /**
    * gets the article ID
    *
    * @return       the current articleID
    * @since        0.1
    */
    public int getArticleID()
    {
        return m_articleID;
    }

    /**
    * gets the body
    *
    * @return       the current body
    * @since        0.1
    */
    public NNTPArticleBody getBody()
    {
        return m_body;
    }

    /**
    * gets the article header
    *
    * @return       the current article header
    * @since        0.1
    */
    public NNTPArticleHeader getHeader()
    {
        return m_header;
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
    * sets the articleID 
    *
    * @since        0.1
    */
    public void setArticleID(int newArticleID)
    {
        m_articleID = newArticleID;
    }

    /**
    * set the article to have a new body
    *
    * @param newBody    new body
    * @return           none
    * @since            0.1
    */
    public void setBody(NNTPArticleBody newBody)
    {
        m_body = newBody;
    }

    /**
    * sets the article header
    *
    * @param newBody    new article header
    * @return           none
    * @since            0.1
    */
    public void setHeader(NNTPArticleHeader newItem)
    {
        m_header = newItem;
    }
}
