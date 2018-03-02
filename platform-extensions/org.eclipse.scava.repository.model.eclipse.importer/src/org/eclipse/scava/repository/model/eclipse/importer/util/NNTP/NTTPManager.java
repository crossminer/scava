/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.eclipse.importer.util.NNTP;

import java.util.ArrayList;
import java.util.Vector;

public class NTTPManager {

	/**
	 * @param args
	 */
	public ArrayList<String> GetListNNTPGroups() {
		// TODO Auto-generated method stub
		//NNTPRaw raw = new NNTPRaw();
		//raw.connect(hostname, port)
		NNTPSession a = new NNTPSession("news.eclipse.org");
		
		a.start("exquisitus","flinder1f7");
		Vector<NNTPGroup> vg = new Vector<NNTPGroup>();
		a.getAllGroups(vg);
		int i=0;
		ArrayList<String> result = new ArrayList<>();
		for (NNTPGroup nntpGroup : vg) {
			
			result.add(nntpGroup.getName());
		}
		return result;
		//NNTPError.ACCESSDENIED

	}
	
	private static void printError(int err)
	{
		switch (err) {
		case NNTPError.ACCESSDENIED:
			System.out.println("ACCESSDENIED");
			break;
		case NNTPError.CLOSEDCONNECTION:
			System.out.println("CLOSEDCONNECTION");
			break;
			
		case NNTPError.EXCEPTION:
			System.out.println("EXCEPTION");
			break;
			
		case NNTPError.GENERALERROR:
			System.out.println("GENERALERROR");
			break;
			
		case NNTPError.GROUPDOESNOTEXIST:
			System.out.println("GROUPDOESNOTEXIST");
			break;	
		
		case NNTPError.HEADER_FIELD_CHANGED:
			System.out.println("HEADER_FIELD_CHANGED");
			break;	
		
		case NNTPError.HEADER_FIELD_DOESNOT_EXIST:
			System.out.println("HEADER_FIELD_DOESNOT_EXIST");
			break;	
			
		case NNTPError.INVALID_PARAMETER:
			System.out.println("INVALID_PARAMETER");
			break;	
			
		case NNTPError.INVALIDPORT:
			System.out.println("INVALIDPORT");
			break;	
			
		case NNTPError.INVALIDRESPONSE:
			System.out.println("INVALIDRESPONSE");
			break;	
			
		case NNTPError.NOHOSTNAME:
			System.out.println("NOHOSTNAME");
			break;	
			
		case NNTPError.OK:
			System.out.println("OK");
			break;	
			
		case NNTPError.SOCKET_ERROR:
			System.out.println("SOCKET_ERROR");
			break;	
		case NNTPError.AUTHENTICATION_REQUEST_480:
			System.out.println("AUTHENTICATION_REQUEST_480");
			break;		
		default:
			break;
		}
	}

}
