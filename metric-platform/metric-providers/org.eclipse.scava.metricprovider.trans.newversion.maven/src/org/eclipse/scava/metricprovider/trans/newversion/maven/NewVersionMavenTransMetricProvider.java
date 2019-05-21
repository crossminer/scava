package org.eclipse.scava.metricprovider.trans.newversion.maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.metricprovider.trans.newversion.maven.model.NewMavenVersion;
import org.eclipse.scava.metricprovider.trans.newversion.maven.model.NewMavenVersions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DB;

public class NewVersionMavenTransMetricProvider implements ITransientMetricProvider<NewMavenVersions> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private final OssmeterLogger logger;
	
	public NewVersionMavenTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.newversion.maven.NewVersionMavenTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return NewVersionMavenTransMetricProvider.class.getCanonicalName();
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
    public NewMavenVersions adapt(DB db) {
    	return new NewMavenVersions(db);
    }
    
    @Override
	public void measure(Project project, ProjectDelta projectDelta, NewMavenVersions db) {
    	
    	String projectName = project.getName();
		
    	URL url;
		try {
			List<VcsRepositoryDelta> repoDeltas = projectDelta.getVcsDelta().getRepoDeltas();
			
			// don't continue if there isn't anything to do
			if (repoDeltas.isEmpty()) {
				return;
			}
			
			db.getNewVersions().getDbCollection().drop();
			db.sync();
			
			
			url = new URL("http://localhost:8182/raw/projects/p/" + projectName + "/m/trans.rascal.dependency.maven.allMavenDependencies");
			
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        con.setRequestMethod("GET");
	        
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer content = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	            content.append(inputLine);
	        }
	        in.close();
	        
	        System.out.println(content);
	        
	        JsonArray jsonArray = new JsonParser().parse(content.toString()).getAsJsonArray();
	        
	        for (int i = 0; i < jsonArray.size(); i++) {
	            String value = jsonArray.get(i).getAsJsonObject().get("value").getAsString();
	            String[] valueParts = value.split("bundle://maven/")[1].split("/");
	            
	            System.out.println("1 " + value);
	            
	            if(value.contains("none") || value.contains("$"))
	            	continue;
	            
	            String newVersion = testMaven(valueParts[0], valueParts[1]);
	            
	            System.out.println("3 " + newVersion);
	            
	            if(newVersion == null)
	            		continue;
	            
	            if(testNewerVersion(valueParts[2], newVersion)) {
	            	NewMavenVersion mv = new NewMavenVersion();
	            	
	            	mv.setPackageName(valueParts[1]);
	            	mv.setOldVersion(valueParts[2]);
	            	mv.setNewVersion(newVersion);
	            	
	            	System.out.println("2 " + valueParts[1] + " " + valueParts[2]);

					db.getNewVersions().add(mv);
					db.sync();
	            }
	        }
        
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    public String getShortIdentifier() {
    	return "newVersionMaven";
    }
    
    @Override
    public String getFriendlyName() {
    	return "NewVersionMaven";
    }
    
    @Override
    public String getSummaryInformation() {
    	return "TODO";
    }
    
    public String testMaven(String group, String artifact){
        try {
            	
            String url = "https://search.maven.org/solrsearch/select";
            String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
            String param1 = "g:" + group + "+AND+a:" + artifact;
            String param2 = "gav";
            String param3 = "20";
            String param4 = "json";

            String query = String.format("q=%s&core=%s&rows=%s&wt=%s", param1, param2, param3, param4);
            
            HttpsURLConnection connection = (HttpsURLConnection)new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestMethod("GET");


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
        }
    }
    
    public boolean testNewerVersion(String oldVersion, String newVersion){
        oldVersion = oldVersion.split("-")[0];
        newVersion = newVersion.split("-")[0];
        
        String[] oldVersionParts = oldVersion.split("\\.");
        String[] newVersionParts = newVersion.split("\\.");
        
        for(int i = 0; i < oldVersionParts.length; i++){
            if(Integer.parseInt(newVersionParts[i]) > Integer.parseInt(oldVersionParts[i]))
                return true;
        }
        
        return false;
    }

}
