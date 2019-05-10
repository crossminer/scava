/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.scava.platform.jadolint.violations;

/**
 *
 * @author blue
 */
public class Violation {
    
    private String code;
    private String message;
    private String fileName;
    private int lineNumber;
    
    public Violation(String code, String message, String fileName, int lineNumber){
        this.code = code;
        this.message = message;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}
