package org.eclipse.scava.crossflow.dt;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;

public class UploadZipAndRun extends UploadZip {

	@Override
	public void run(IAction action) {
		super.run(action);

		try {
			//TODO connect to appropriate hook on the server for starting the experiment
			//beginExperiment();
		} catch (Exception e) {
			ExceptionHandler.handle(e, shell);
		}

	}

	protected void beginExperiment() throws Exception {

		HttpClient httpclient = new DefaultHttpClient();
		
		HttpPost httppost = new HttpPost(
				"http://" + serverIp + "/" + webAppProjectName + "/experiment.jsp?id=" + experimentName + ".zip");

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addPart("autoStart", new StringBody("true"));

		HttpEntity entity = builder.build();

		httppost.setEntity(entity);

		HttpResponse response = httpclient.execute(httppost);

		System.out.println(response.toString());

	}

}
