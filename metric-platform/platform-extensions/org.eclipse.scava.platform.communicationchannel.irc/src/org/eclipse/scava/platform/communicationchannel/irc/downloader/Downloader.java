package org.eclipse.scava.platform.communicationchannel.irc.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Downloader{

	static void download(String URL, String username, String password, String localfilePath) {
        URL url = null;
        MyAuthenticator.setPasswordAuthentication(username, password);
        Authenticator.setDefault (new MyAuthenticator ());

        try {
			url = new URL(URL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
        	InputStream is = url.openStream();
			Files.copy(is, Paths.get(localfilePath), StandardCopyOption.REPLACE_EXISTING);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("File downloaded successfully!");
	}

    public static void main(String[] args) {
    	
    	String URL = "https://mail-archive.ow2.org/archdump/lemonldap-ng-dev@ow2.org.tgz",
    		   username = "crossminer",
    		   password = "U4QRh7H9SFy4AZxa",
    		   localfilePath = "local.tgz";
    	
    	download(URL, username, password, localfilePath);
   	
    }

}