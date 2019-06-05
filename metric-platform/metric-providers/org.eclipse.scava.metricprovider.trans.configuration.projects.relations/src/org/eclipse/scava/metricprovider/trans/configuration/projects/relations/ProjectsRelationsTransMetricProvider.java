package org.eclipse.scava.metricprovider.trans.configuration.projects.relations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependencies;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependency;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.model.DockerDependencyCollection;
import org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.DockerDependenciesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.configuration.projects.relations.model.ProjectRelation;
import org.eclipse.scava.metricprovider.trans.configuration.projects.relations.model.ProjectRelations;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.PuppetDependenciesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependencies;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependency;
import org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model.PuppetDependencyCollection;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mongodb.DB;

public class ProjectsRelationsTransMetricProvider implements ITransientMetricProvider<ProjectRelations> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private final OssmeterLogger logger;
	
	public ProjectsRelationsTransMetricProvider() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("metricprovider.trans.configuration.projects.relations.ProjectsRelationsTransMetricProvider");
	}
    
    @Override
    public String getIdentifier() {
    return ProjectsRelationsTransMetricProvider.class.getCanonicalName();
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
    	return Arrays.asList(new String[]{DockerDependenciesTransMetricProvider.class.getCanonicalName(), PuppetDependenciesTransMetricProvider.class.getCanonicalName()});
    }
    
    public void setMetricProviderContext(MetricProviderContext context) {
    	this.context = context;
    }
    
    @Override
    public ProjectRelations adapt(DB db) {
    	return new ProjectRelations(db);
    }
    
    @Override
	public void measure(Project project, ProjectDelta projectDelta, ProjectRelations db) {
		
    	URL url;
    	BufferedReader in = null;
		try {
			List<VcsRepositoryDelta> repoDeltas = projectDelta.getVcsDelta().getRepoDeltas();
			
			// don't continue if there isn't anything to do
			if (repoDeltas.isEmpty()) {
				return;
			}
			
			db.getRelations().getDbCollection().drop();
			db.sync();
			
			String projectName = project.getName();
			String projectShortName = project.getShortName();
			
			
			url = new URL("http://localhost:8182/projects/");
			
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
	            String thirdName = jsonArray.get(i).getAsJsonObject().get("name").getAsString();
	            String thirdShortName = jsonArray.get(i).getAsJsonObject().get("shortName").getAsString();
	            
	            if(!thirdShortName.equals(projectShortName)) {
		            if(testMaven(projectName, thirdShortName)) {
		            	ProjectRelation pr = new ProjectRelation();
		            	
		            	pr.setRelationName(thirdName);
		            	pr.setDependencyType("maven");
	
						db.getRelations().add(pr);
						db.sync();
		            }
		            
		            if(testOsgi(projectName, thirdShortName)) {
		            	ProjectRelation pr = new ProjectRelation();
		            	
		            	pr.setRelationName(thirdName);
		            	pr.setDependencyType("osgi");
	
						db.getRelations().add(pr);
						db.sync();
		            }
		            
		            if(testDocker(projectName, thirdShortName)) {
		            	ProjectRelation pr = new ProjectRelation();
		            	
		            	pr.setRelationName(thirdName);
		            	pr.setDependencyType("docker");
	
						db.getRelations().add(pr);
						db.sync();
		            }
		            
		            if(testPuppet(projectName, thirdShortName)) {
		            	ProjectRelation pr = new ProjectRelation();
		            	
		            	pr.setRelationName(thirdName);
		            	pr.setDependencyType("puppet");
	
						db.getRelations().add(pr);
						db.sync();
		            }
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
    
    public boolean testMaven(String projectName, String thirdShortName){
    	BufferedReader in = null;
        try {
            	
        	URL url = new URL("http://localhost:8182/raw/projects/p/" + thirdShortName + "/m/trans.rascal.dependency.maven.allMavenDependencies");
            
        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");


            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            
            JsonArray jsonArray = new JsonParser().parse(content.toString()).getAsJsonArray();
	        
	        for (int i = 0; i < jsonArray.size(); i++) {
	            String value = jsonArray.get(i).getAsJsonObject().get("value").getAsString();
	            String[] valueParts = value.split("bundle://maven/")[1].split("/");
	            
	            if(valueParts[1].equals(projectName))
	            	return true;
	        }
	        
	        return false;

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
    
    public boolean testOsgi(String projectName, String thirdShortName){
    	BufferedReader in = null;
        try {
            	
        	URL url = new URL("http://localhost:8182/raw/projects/p/" + thirdShortName + "/m/trans.rascal.dependency.osgi.allOsgiDependencies");
            
        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");


            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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
	            
	            if(valueParts[1].equals(projectName))
	            	return true;
	        }
	        
	        return false;

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
    
    public boolean testPuppet(String projectName, String thirdShortName){
    	BufferedReader in = null;
        try {
            	
        	URL url = new URL("http://localhost:8182/raw/projects/p/" + thirdShortName + "/m/org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.PuppetDependenciesTransMetricProvider");
            
        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");


            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            
            JsonArray jsonArray = new JsonParser().parse(content.toString()).getAsJsonArray();
	        
	        for (int i = 0; i < jsonArray.size(); i++) {
	        	String name = jsonArray.get(i).getAsJsonObject().get("dependencyName").getAsString();
	            
	            if(name.equals(projectName))
	            	return true;
	        }
	        
	        return false;

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
    
    public boolean testDocker(String projectName, String thirdShortName){
    	BufferedReader in = null;
        try {
            	
        	URL url = new URL("http://localhost:8182/raw/projects/p/" + thirdShortName + "/m/org.eclipse.scava.metricprovider.trans.configuration.docker.dependencies.DockerDependenciesTransMetricProvider");
            
        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");


            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            
            JsonArray jsonArray = new JsonParser().parse(content.toString()).getAsJsonArray();
	        
	        for (int i = 0; i < jsonArray.size(); i++) {
	        	String name = jsonArray.get(i).getAsJsonObject().get("dependencyName").getAsString();
	            
	            if(name.equals(projectName))
	            	return true;
	        }
	        
	        return false;

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
}