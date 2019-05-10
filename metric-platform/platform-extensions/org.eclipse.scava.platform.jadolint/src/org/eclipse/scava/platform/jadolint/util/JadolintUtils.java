/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.util;

/**
 *
 * @author blue
 */
public class JadolintUtils {
    
    public static String getInstruction(String line) {
        if (line.startsWith("ADD")) {
            return "ADD";
        } else if (line.startsWith("ARG")) {
            return "ARG";
        } else if (line.startsWith("CMD")) {
            return "CMD";
        } else if (line.startsWith("COPY")) {
            return "COPY";
        } else if (line.startsWith("ENTRYPOINT")) {
            return "ENTRYPOINT";
        } else if (line.startsWith("ENV")) {
            return "ENV";
        } else if (line.startsWith("EXPOSE")) {
            return "EXPOSE";
        } else if (line.startsWith("FROM")) {
            return "FROM";
        } else if (line.startsWith("HEALTHCHECK")) {
            return "HEALTHCHECK";
        } else if (line.startsWith("LABEL")) {
            return "LABEL";
        } else if (line.startsWith("MAINTAINER")) {
            return "MAINTAINER";
        } else if (line.startsWith("ONBUILD")) {
            return "ONBUILD";
        } else if (line.startsWith("RUN")) {
            return "RUN";
        } else if (line.startsWith("SHELL")) {
            return "SHELL";
        } else if (line.startsWith("STOPSIGNAL")) {
            return "STOPSIGNAL";
        } else if (line.startsWith("USER")) {
            return "USER";
        } else if (line.startsWith("VOLUME")) {
            return "VOLUME";
        } else if (line.startsWith("WORKDIR")) {
            return "WORKDIR";
        } else {
            return "";
        }
    }
    
}
