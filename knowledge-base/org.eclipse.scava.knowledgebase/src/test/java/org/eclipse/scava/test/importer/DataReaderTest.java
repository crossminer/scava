package org.eclipse.scava.test.importer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

import org.eclipse.scava.business.impl.DataReader;
import org.eclipse.scava.business.model.Artifact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class DataReaderTest {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	@Autowired
	private DataReader dr;
        
    @Test(expected = IOException.class) 
    public void testReadArtifactFromFileException() throws IOException
    {
    	Resource resource = new ClassPathResource("FOCUS/asd.txt");
    	Artifact res = dr.readArtifactFromFile(resource.getFile().getAbsolutePath());
        res.getMethodDeclarations().forEach(x-> System.out.println(x));
    	assertNotNull(res);
    }

    @Test
    public void testReadArtifactFromFile() throws IOException
    {
    	Resource resource = new ClassPathResource("FOCUS/000a7d6989abec22bf0a8336d350d8a97ccda5fa.txt");
    	Artifact res = dr.readArtifactFromFile(resource.getFile().getAbsolutePath());
        res.getMethodDeclarations().forEach(x-> System.out.println(x));
    	assertNotNull(res);
    }
    
    @Test
    public void testReadArtifactFromPath() throws IOException
    {
    	Resource resource = new ClassPathResource("FOCUS/");
    	List<Artifact> res = dr.readArtifactsFromPath(resource.getFile().getAbsolutePath());
    	assertNotNull(res);
    }
}
