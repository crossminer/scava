package org.eclipse.scava.platform.client.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectError;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.QueryBuilder;

public class ProjectDeleteResource extends ServerResource {
	
	private static final String WORKING_COPY_DIRECTORY = "workingCopies";
	private static final String MODEL_DIRECTORY = "workingModels";
	
	@Delete
	public Representation deleteAnalysisTask() {
		Mongo mongo = null;
		Platform platform = null;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
			platform = new Platform(mongo);				
			AnalysisTaskService service = platform.getAnalysisRepositoryManager().getTaskService();
			
			String projectId = (String) getRequest().getAttributes().get("projectid");

			List<AnalysisTask> analysisTasks = service.getAnalysisTasksByProject(projectId);
			for (AnalysisTask analysisTask : analysisTasks) {
				service.deleteAnalysisTask(analysisTask.getAnalysisTaskId());
			}
			
			ProjectRepository projectRepository = platform.getProjectRepositoryManager().getProjectRepository();
			Iterable<Project> projectToDelete = projectRepository.getProjects().findByShortName(projectId);
			for (Project project : projectToDelete) {
				if (project.getShortName().equals(projectId)) {
					// Drop project analysis database
					int db = mongo.getDatabaseNames().indexOf(project.getName());
					if (db != -1) {
						platform.getMetricsRepository(project).getDb().dropDatabase();
					}
					// Remove project's source and models
					Path pathStorage = Paths.get(platform.getLocalStorageHomeDirectory().toString(), project.getShortName());
					if (pathStorage.toString() != null) {
						File storage = new File(pathStorage.toString());
					    File wc = new File(storage, WORKING_COPY_DIRECTORY);
					    if (wc.exists()) {
					    	deleteDirectoryRecursion(wc.toPath());
					    }
					    File md = new File(storage, MODEL_DIRECTORY);
					    if (md.exists()) {
					    	deleteDirectoryRecursion(md.toPath());
					    }
					}
					// Erase project's stack traces
					for (ProjectError projectError : projectRepository.getErrors()) {
						if (projectError.getProjectId().equals(project.getShortName())) {
							projectRepository.getErrors().remove(projectError);
						}
					}
					projectRepository.getProjects().remove(project);
				}
			}
			projectRepository.sync();

			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.SUCCESS_OK);
			return rep;

		} catch (IOException e) {
			e.printStackTrace();
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		} finally {
			if (mongo != null) mongo.close();
			platform = null;
		}

	}
	
	void deleteDirectoryRecursion(Path path) throws IOException {
		if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
			try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
				for (Path entry : entries) {
					deleteDirectoryRecursion(entry);
				}
			}
		}
		Files.delete(path);
	}
}
