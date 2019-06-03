package org.eclipse.scava.platform.communicationchannel.irc.utils;

public class IrcUtils {
	
public static String checkExtension(String ext) {
		
		String e ="";
		
		if (ext.startsWith(".") == false) {
			
			e = e +"." + ext;
		}else {
			e = ext;
		}
		
		
		return e.toLowerCase();
		
	}
	
	public static String checkDateValue(int dateVal) {
		
		String val = " ";
		
		if(Integer.toString(dateVal).length() < 2) {
			
			val = "0"+ Integer.toString(dateVal);
		}else {
			
			val = Integer.toString(dateVal);
		}
		
		return val;
	}

}
