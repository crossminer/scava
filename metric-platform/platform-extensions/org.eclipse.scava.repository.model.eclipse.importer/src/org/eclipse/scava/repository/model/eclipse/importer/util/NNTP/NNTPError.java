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
* NNTPError is an interface that supplies defines for error values. Anything
* that returns an error should return the definition and not the numerical
* value. 
*
* @author 	Scott MacDiarmid
* @version 	0.1
* @since    0.1
*/
public interface NNTPError
{
    static final public int OK                          = 0;
    static final public int NOHOSTNAME                  = 1;
    static final public int INVALIDPORT                 = 2;
    static final public int INVALIDRESPONSE             = 3;
    static final public int EXCEPTION                   = 4;
    static final public int GROUPDOESNOTEXIST           = 5;
    static final public int GENERALERROR                = 6;
    static final public int ACCESSDENIED                = 7;
    static final public int CLOSEDCONNECTION            = 8;
    static final public int HEADER_FIELD_CHANGED        = 9;
    static final public int HEADER_FIELD_DOESNOT_EXIST  = 10;
    static final public int SOCKET_ERROR                = 11;
    static final public int INVALID_PARAMETER           = 12;
    static final public int AUTHENTICATION_REQUEST_480           = 13;

}
    
