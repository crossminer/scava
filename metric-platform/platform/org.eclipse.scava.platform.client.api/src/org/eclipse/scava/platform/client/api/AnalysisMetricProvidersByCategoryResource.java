package org.eclipse.scava.platform.client.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.osgi.framework.BundleContext;
import org.restlet.representation.Representation;

public class AnalysisMetricProvidersByCategoryResource extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
		
        JSONParser jsonParser = new JSONParser();
        JSONObject mps = new JSONObject();
        
        BundleContext bundler = Activator.getContext();        
        try (InputStream url = FileLocator.openStream(bundler.getBundle(), new Path("src/resources/metricsprovider.json"), false))
        {
        	Reader reader = new InputStreamReader(url);
        	mps = (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        
		return Util.createJsonRepresentation(mps.toString());
	}
}
