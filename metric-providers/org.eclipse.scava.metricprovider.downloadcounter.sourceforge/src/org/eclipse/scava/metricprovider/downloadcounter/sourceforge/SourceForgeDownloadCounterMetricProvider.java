/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.downloadcounter.sourceforge;

import java.text.SimpleDateFormat;

import org.eclipse.scava.metricprovider.downloadcounter.model.Download;
import org.eclipse.scava.metricprovider.downloadcounter.model.DownloadCounter;
import org.eclipse.scava.platform.AbstractTransientMetricProvider;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.sourceforge.SourceForgeProject;

import com.jayway.jsonpath.JsonPath;
import com.mongodb.DB;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class SourceForgeDownloadCounterMetricProvider extends AbstractTransientMetricProvider<DownloadCounter> {


	@Override
	public boolean appliesTo(Project project) {
		return project instanceof SourceForgeProject;
 	}

	@Override
	public DownloadCounter adapt(DB db) {
		return new DownloadCounter(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, DownloadCounter db) {
		
		
		String deltaDate = toString(delta.getDate());
		String previousDeltaDate = toString(delta.getDate().addDays(-1));
			
		try {
			Client client = Client.create(); 
			
			String restRequest = "http://sourceforge.net/projects/" +  project.getName() + "/files/stats/json?start_date=" + previousDeltaDate + "&end_date=" + deltaDate;
			
			WebResource webResource = client.resource(restRequest);
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			String output = response.getEntity(String.class);
			
			int counter = JsonPath.read(output, "$.summaries.time.downloads");

			Download download = new Download();
			download.setCounter(counter);
			download.setDate(deltaDate);
			
			db.getDownloads().add(download);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 
		db.sync();
		
	}
	
	public String toString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date.toJavaDate());
	}

	@Override
	public String getShortIdentifier() {
		return "sfdc";
	}

	@Override
	public String getFriendlyName() {
		return "Download counter";
	}

	@Override
	public String getSummaryInformation() {
		return "Lorum ipsum";
	}
	
}
