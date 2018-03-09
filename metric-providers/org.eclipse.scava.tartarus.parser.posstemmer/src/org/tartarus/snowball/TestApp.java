/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.tartarus.snowball;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class TestApp {
	
    private static void usage() {
        System.err.println("Usage: TestApp <algorithm> <input file> [-o <output file>]");
    }

    public static void main(String[] args) throws Throwable {
    	if (args.length < 2) {
    		usage();
            return;
        }

    	Class<?> stemClass = Class.forName("org.tartarus.snowball.ext." + args[0] + "Stemmer");
    	SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();

    	@SuppressWarnings("resource")
		Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[1])));

    	StringBuffer input = new StringBuffer();

    	OutputStream outstream = getOutputStream(args);
     	Writer writer = new BufferedWriter(new OutputStreamWriter(outstream));

    	int repeat = 1;
    	if (args.length > 4) 
    		repeat = Integer.parseInt(args[4]);

//    	Object [] emptyArgs = new Object[0];
    	int character;
    	while ((character = reader.read()) != -1) {
    		char ch = (char) character;
    		if (Character.isWhitespace((char) ch)) {
    			if (input.length() > 0) {
    				stemmer.setCurrent(input.toString());
    				for (int i = repeat; i != 0; i--)
    					stemmer.stem();
    				writer.write(stemmer.getCurrent());
    				writer.write('\n');
    				input.delete(0, input.length());
    			}
    		} else {
    			input.append(Character.toLowerCase(ch));
    		}
    	}
    	writer.flush();
    	writer.close();
    	
    }
    
    private static OutputStream getOutputStream(String[] args) {
    	if (args.length > 2) {
    		if (args.length >= 4 && args[2].equals("-o")) {
    			try {
					return new FileOutputStream(args[3]);
				} catch (FileNotFoundException e) {
					System.err.println("Cannot open file " + args[3]);
					e.printStackTrace();
				}
    		} else {
    			usage();
    			System.exit(-1);
    		}
    	} else {
    		return System.out;
    	}
		return null;
    }


	
	
}
