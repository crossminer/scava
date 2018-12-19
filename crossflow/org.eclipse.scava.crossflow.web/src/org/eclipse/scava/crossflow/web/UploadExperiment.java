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
		String experimentPath = getServletContext().getRealPath("/experiments/") + experimentName + "/";
		String experimentJarPath= getServletContext().getRealPath("/WEB-INF/lib/");
		
		
		Files.createDirectories(Paths.get(experimentPath));
		
		// serialize
	    serialize(request, "xmlFile", experimentPath + "experiment.xml");
    	serialize(request, "jarFile", experimentJarPath + experimentName + ".jar");
    	serialize(request, "inputDataZipFile", experimentPath + "in.zip");

	    // unzip  to "in"
	    unzip(experimentPath + "in.zip", experimentPath);
	    Files.deleteIfExists(Paths.get(experimentPath + "in.zip"));
	    
	    // submit successful upload notification
	    String inputEmail = request.getParameter("inputEmail"); 
	    // TODO: submit successful upload notification
	    
	    response.sendRedirect("/org.eclipse.scava.crossflow.web/");
	    
	}

	/**
	 * @param request
	 * @throws IOException
	 * @throws ServletException
	 * @throws FileNotFoundException
	 */
	private void serialize(HttpServletRequest request, String inputFieldName, String targetLocation) throws IOException, ServletException, FileNotFoundException {
		Part filePart = request.getPart(inputFieldName); 
	 
	    InputStream fileContent = filePart.getInputStream();
	    
	    byte[] buffer = new byte[fileContent.available()];
	    fileContent.read(buffer);
	 
	    File targetFile = new File(targetLocation);
	    
	    OutputStream outStream = new FileOutputStream(targetFile);
	    outStream.write(buffer);
	    
	    outStream.flush();
	    outStream.close();
	    
	    //System.out.println("targetFile="+targetFile.getAbsolutePath());
	}
	
	protected static void unzip(final String zipFilePath, final String unzipLocation) throws IOException {

        if (!(Files.exists(Paths.get(unzipLocation)))) {
            Files.createDirectories(Paths.get(unzipLocation));
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null) {
                Path filePath = Paths.get(unzipLocation, entry.getName());
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
