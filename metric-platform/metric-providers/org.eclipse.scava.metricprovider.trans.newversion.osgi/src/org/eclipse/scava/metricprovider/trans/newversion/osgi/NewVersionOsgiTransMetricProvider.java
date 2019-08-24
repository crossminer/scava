package org.eclipse.scava.metricprovider.trans.newversion.osgi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.eclipse.scava.metricprovider.trans.newversion.osgi.model.NewOsgiVersion;
import org.eclipse.scava.metricprovider.trans.newversion.osgi.model.NewOsgiVersions;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DB;

public class NewVersionOsgiTransMetricProvider implements ITransientMetricProvider<NewOsgiVersions> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private final OssmeterLogger logger;
	
	public NewVersionOsgiTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.newversion.osgi.NewVersionOsgiTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return NewVersionOsgiTransMetricProvider.class.getCanonicalName();
    }
    
    @Override
    public boolean appliesTo(Project project) {
    	return project.getVcsRepositories().size() > 0;
    }
    
    @Override
    public void setUses(List<IMetricProvider> uses) {
    	this.uses = uses;
    }
        
    @Override
    public List<String> getIdentifiersOfUses() {
    	return Collections.emptyList();
    }
    
    public void setMetricProviderContext(MetricProviderContext context) {
    	this.context = context;
    }
    
    @Override
    public NewOsgiVersions adapt(DB db) {
    	return new NewOsgiVersions(db);
    }
    
    @Override
	public void measure(Project project, ProjectDelta projectDelta, NewOsgiVersions db) {
    	
    	String projectName = project.getShortName();
		
    	URL url;
    	BufferedReader in = null;
		try {
			List<VcsRepositoryDelta> repoDeltas = projectDelta.getVcsDelta().getRepoDeltas();
			
			// don't continue if there isn't anything to do
			if (repoDeltas.isEmpty()) {
				return;
			}
			
			db.getNewVersions().getDbCollection().drop();
			db.sync();
			
			
			url = new URL("http://localhost:8182/raw/projects/p/" + projectName + "/m/trans.rascal.dependency.osgi.allOsgiDependencies");
			
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        con.setRequestMethod("GET");
	        
	        in = new BufferedReader(
	                new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer content = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	            content.append(inputLine);
	        }
	        in.close();
	        
	        JsonArray jsonArray = new JsonParser().parse(content.toString()).getAsJsonArray();
	        
	        for (int i = 0; i < jsonArray.size(); i++) {
	            String value = jsonArray.get(i).getAsJsonObject().get("value").getAsString();
	            String[] valueParts = value.split("bundle://")[1].split("/");
	            
	            if(value.contains("none") || value.contains("%") || value.contains("("))
	            	continue;
	            
	            String newVersion = testMaven(valueParts[1]);
	            
	            if(newVersion == null)
	            		continue;
	            
	            if(testNewerVersion(valueParts[2], newVersion)) {
	            	NewOsgiVersion mv = new NewOsgiVersion();
	            	
	            	mv.setPackageName(valueParts[1]);
	            	mv.setOldVersion(valueParts[2]);
	            	mv.setNewVersion(newVersion);

					db.getNewVersions().add(mv);
					db.sync();
	            }
	        }
        
	    } catch (IOException e) {
	    	logger.error("unexpected IO exception while measuring", e);
			throw new RuntimeException(e);
		} finally {
			try {
				if(in != null)
					in.close();
			} catch (IOException e) {
				logger.error("unexpected IO exception while measuring", e);
				throw new RuntimeException(e);
			}
		}
    }
    
    @Override
    public String getShortIdentifier() {
    	return "newVersionOsgi";
    }
    
    @Override
    public String getFriendlyName() {
    	return "New Versions of Osgi Bundles";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "This is the transient version of the metric that returns the number of the OSGi bundles that have a new version";
    }
    
    public String testMaven(String artifact){
    	BufferedReader in = null;
        try {
            	
            String url = "https://search.maven.org/solrsearch/select";
            String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
            String param1 = "a:" + artifact;
            String param2 = "gav";
            String param3 = "20";
            String param4 = "json";

            String query = String.format("q=%s&core=%s&rows=%s&wt=%s", param1, param2, param3, param4);
            
            HttpsURLConnection connection = (HttpsURLConnection)new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestMethod("GET");


            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            
            JsonObject obj = new JsonParser().parse(content.toString()).getAsJsonObject();
            
            JsonArray array = obj.getAsJsonObject("response").getAsJsonArray("docs");
            
            if(array.size() > 0){
            
	            JsonObject obj2 = array.get(0).getAsJsonObject();
	            
	            String version = obj2.get("v").getAsString();
	            
	            return version;
            } else
            	return null;

        } catch (IOException e) {
        	logger.error("unexpected exception while measuring", e);
			throw new RuntimeException(e);
        } finally {
			try {
				if(in != null)
					in.close();
			} catch (IOException e) {
				logger.error("unexpected IO exception while measuring", e);
				throw new RuntimeException(e);
			}
		}
    }
    
    public boolean testNewerVersion(String oldVersion, String newVersion){
        oldVersion = oldVersion.split("-")[0];
        newVersion = newVersion.split("-")[0];
        
        oldVersion = oldVersion.split("_")[0];
        newVersion = newVersion.split("_")[0];
        
        String[] oldVersionParts = oldVersion.split("\\.");
        String[] newVersionParts = newVersion.split("\\.");
        
        int length;
        
        if(oldVersionParts.length < newVersionParts.length)
            length = oldVersionParts.length;
        else
            length = newVersionParts.length;
        
        for(int i = 0; i < length; i++){
        	if(Integer.parseInt(newVersionParts[i]) > Integer.parseInt(oldVersionParts[i]))
                return true;
            else if(Integer.parseInt(newVersionParts[i]) == Integer.parseInt(oldVersionParts[i]))
                continue;
            else
                return false;
        }
        
        return false;
    }

}
