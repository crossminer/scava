package org.eclipse.scava.platform.communicationchannel.mbox.utils;

public class MboxUtils {
	
	public static String checkExtension(String ext) {

		if (!ext.startsWith("."))
			ext="."+ext;
		return ext.toLowerCase();
	}
}
