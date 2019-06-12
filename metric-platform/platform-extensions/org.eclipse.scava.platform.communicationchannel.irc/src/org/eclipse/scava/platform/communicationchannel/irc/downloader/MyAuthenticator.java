package org.eclipse.scava.platform.communicationchannel.irc.downloader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {  

	private static String username, password;

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication (MyAuthenticator.username, 
        								   MyAuthenticator.password.toCharArray());
    }

    public static void setPasswordAuthentication(String username, String password) {
        MyAuthenticator.username = username;
        MyAuthenticator.password = password;
    }
}