package org.eclipse.scava.crossflow.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/uploadExperiment")
@MultipartConfig
public class UploadExperiment extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
		// prepare target locations
		String experimentName = request.getParameter("inputName");
		Path experimentPath = Paths.get(getServletContext().getRealPath("/experiments/"), experimentName);
		Path experimentJarPath= Paths.get(getServletContext().getRealPath("/jars/"));
		
		try {
			Files.createDirectories(experimentPath);
			
			// serialize
		    serialize(request, "xmlFile", experimentPath + "/experiment.xml");
		    serialize(request, "runtimeModelFile", experimentPath + "/graph.abstract");
	    	serialize(request, "jarFile", experimentJarPath + "/" + experimentName + ".jar");
	    	serialize(request, "inputDataZipFile", experimentPath + "/in.zip");
	
		    // unzip  to "in"
		    unzip(Paths.get(experimentPath.toString(), "in.zip"), experimentPath);
		    Files.deleteIfExists(Paths.get(experimentPath + "in.zip"));
		    
		    // submit successful upload notification
		    String inputEmail = request.getParameter("inputEmail"); 
		    // TODO: submit successful upload notification
		    
		    
		} catch (IOException e) {
			System.err.println("Failed to upload experiment. Make sure no experiment with the same name has already been deployed.");
			System.err.println(e.getMessage());
		}
		
		response.sendRedirect("/org.eclipse.scava.crossflow.web/");
	}

	/**
	 * @param request
	 * @throws IOException
	 * @throws ServletException
	 * @throws FileNotFoundException
	 */
	private void serialize(HttpServletRequest request, String inputFieldName, String targetLocation) throws IOException, ServletException, FileNotFoundException {
		try { 
			Part filePart = request.getPart(inputFieldName); 
		 
			if (filePart == null) {
				System.err.println("Unable to process workflow upload (input specification missing).");
				return; // quit
			}
			
		    InputStream fileContent = filePart.getInputStream();
		    
		    byte[] buffer = new byte[fileContent.available()];
		    fileContent.read(buffer);
		 
		    File targetFile = new File(targetLocation);
		    
		    OutputStream outStream = new FileOutputStream(targetFile);
		    outStream.write(buffer);
		    
		    outStream.flush();
		    outStream.close();
		    
		} catch (FileNotFoundException e) {
			System.err.println("Unable to process workflow upload (specified file not found).");
			System.err.println(e.getMessage());
			
		} catch (IOException e) {
			System.err.println("Unable to process workflow upload (i/o exception occurred).");
			System.err.println(e.getMessage());
		}
	}
	
	protected static void unzip(final Path zipFilePath, final Path unzipLocation) throws IOException {

        if (!(Files.exists(unzipLocation))) {
            Files.createDirectories(unzipLocation);
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath.toString()))) {
            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null) {
                Path filePath = Paths.get(unzipLocation.toString(), entry.getName());
                if (!entry.isDirectory()) {
                    unzipFiles(zipInputStream, filePath);
                } else {
                    Files.createDirectories(filePath);
                }

                zipInputStream.closeEntry();
                entry = zipInputStream.getNextEntry();
            }
        }
        
        
    }
	
	protected static void unzipFiles(final ZipInputStream zipInputStream, final Path unzipFilePath) throws IOException {

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzipFilePath.toAbsolutePath().toString()))) {
            byte[] bytesIn = new byte[1024];
            int read = 0;
            while ((read = zipInputStream.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }

    }
	
}
