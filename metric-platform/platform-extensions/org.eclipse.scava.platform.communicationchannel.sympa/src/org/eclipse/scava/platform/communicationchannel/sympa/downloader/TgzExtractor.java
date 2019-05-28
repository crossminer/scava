package org.eclipse.scava.platform.communicationchannel.sympa.downloader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.eclipse.scava.platform.communicationchannel.sympa.utils.SympaUtils;


public class TgzExtractor {

	private final static int BUFFER_SIZE = 2048;

public static Path extract(InputStream inputStream, String ext) throws IOException {
        
	
		String rootCreated="";
        File file = createTempFile(inputStream, SympaUtils.checkExtension(ext));
        
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        GzipCompressorInputStream gzipIn = null;
        try {
            gzipIn = new GzipCompressorInputStream(in);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
       
        try (TarArchiveInputStream tarIn = new TarArchiveInputStream(gzipIn)) {
            TarArchiveEntry entry;

            HashMap<String, String> mappingFolders = new HashMap<String, String> ();
            
            Path path;
            String pathUntilNow;
            
            while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {
                /** If the entry is a directory, create the directory. **/
                if (!entry.isDirectory() && !entry.getName().contains("/.")) {
                    List<String> parsedPath = pathParser(entry.getName());
                    pathUntilNow="";
                    for(int i=0; i<parsedPath.size()-1; i++)
                    {
                   	
                        if(parsedPath.get(i).isEmpty())
                            continue;
                        if(mappingFolders.containsKey(parsedPath.get(i)))
                        {
                            pathUntilNow =mappingFolders.get(parsedPath.get(i));
                        }
                        else
                        {
                            if(rootCreated.isEmpty())
                            {
                                path=Files.createTempDirectory(parsedPath.get(i) + "_");
                                rootCreated=path.toString();
                            }
                            else
                            {
                                path=Files.createTempDirectory(Paths.get(pathUntilNow), parsedPath.get(i));                                
                            }
                            mappingFolders.put(parsedPath.get(i), path.toString());
                            pathUntilNow =path.toString();
                        }
                    }
                    path = createTempFileInTemporalDirectory(parsedPath.get(parsedPath.size()-1), pathUntilNow);
                    
                    int count;
                    byte data[] = new byte[BUFFER_SIZE];
                    FileOutputStream fos = new FileOutputStream(path.toFile(), false);
                    try (BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER_SIZE)) {
                        while ((count = tarIn.read(data, 0, BUFFER_SIZE)) != -1) {
                            dest.write(data, 0, count);
                        }
                    }
                }
            }
       } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return Paths.get(rootCreated);

    }


private static File createTempFile(InputStream inputStream, String extension) throws IOException {
    File tmpFile = File.createTempFile("mailingListArchive", extension);
    try {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(tmpFile));
        IOUtils.copy(inputStream, bufferedOutputStream);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        // IOUtils.closeQuietly(bufferedOutputStream);
        return tmpFile;
    } catch (IOException e) {
if (tmpFile != null) {
            tmpFile.delete();
        }
        throw e;
    }
}



private static List<String> pathParser(String path)
{
    List<String> pathParsed = Arrays.asList(path.split("/"));
    return pathParsed;
}

private static Path createTempFileInTemporalDirectory(String fileName, String pathUntilNow) throws IOException
{
	
    String[] fileNameParsed = fileName.split("\\.");
    String name = fileNameParsed[0] + "_";
    String extension="";
    for(int i=1; i<fileNameParsed.length; i++)
        extension+=fileNameParsed[i];
    
    if(!(extension.isEmpty()))
    	extension = "."+ extension;
    	
    return Files.createTempFile(Paths.get(pathUntilNow), name, extension);
}

}
