package org.eclipse.scava.plugin.ui.errorhandler;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.usermonitoring.ErrorType;
import org.eclipse.swt.widgets.Shell;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.swagger.client.ApiException;

public class ErrorHandler {

	public static void handle(Shell shell, ApiException e) {

		int errorCode = e.getCode();
			
		String responseBody = e.getResponseBody();
		Map<String, List<String>> responseHeaders = e.getResponseHeaders();
		StringBuilder userMessage = new StringBuilder();

		switch (errorCode) {
		case 0:
			Throwable cause = e.getCause();
			if (cause != null) {
				if (cause instanceof UnknownHostException) {
					userMessage.append("Uknonwn host: ");
					userMessage.append(cause.getMessage());
					userMessage.append('\n');
				}
				if (cause instanceof SocketTimeoutException) {
					userMessage.append("Connection timed out.\nProbably the host is unavailable or you have connection issues.\n");
				}
				userMessage.append("Please, check your settings in the preferences.\n");
				break;
			}
		case 400:
			userMessage.append("Bad Request: The server cannot or will not process the request due to an apparent client error.\n");
			break;
		case 404:
			userMessage.append("The requested resource could not be found.\n");
			userMessage.append("Please, check your settings in the preferences.\n");
			break;
		default:
			userMessage.append("Unexpected error occured.\n");
			break;
		}

		userMessage.append('\n');
		userMessage.append("For more details see the log file.");

		StringBuilder logMessage = new StringBuilder();
		logMessage.append("Swagger client exception details:\n");
		logMessage.append("Error code: ");
		logMessage.append(errorCode);
		logMessage.append('\n');
		if (responseHeaders != null && !responseHeaders.isEmpty()) {
			logMessage.append("Response headers:\n");
			responseHeaders.entrySet().forEach(entry -> {
				logMessage.append(" ");
				logMessage.append(entry.getKey());
				logMessage.append('\n');
				if (entry.getValue() != null && !entry.getValue().isEmpty()) {
					entry.getValue().forEach(element -> {
						logMessage.append("  ");
						logMessage.append(element);
						logMessage.append('\n');
					});
				}
			});
		} else {
			logMessage.append("Response header is empty\n");
		}

		if (responseBody != null && !responseBody.isEmpty()) {
			logMessage.append("Response body:\n");
			try {
				JsonParser jsonParser = new JsonParser();
				JsonObject parsedResponseBody = jsonParser.parse(responseBody).getAsJsonObject();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				logMessage.append(gson.toJson(parsedResponseBody));
			} catch (Throwable jsonException) {
				logMessage.append(responseBody);
			}
			logMessage.append('\n');
		} else {
			logMessage.append("Response body is empty\n");
		}

		log(Status.ERROR, logMessage, e);
		MessageDialog.openError(shell, "Error", userMessage.toString());
	}

	public static void handle(Shell shell, Exception exception, ErrorType errorType) {

		StringBuilder userMessage = new StringBuilder();
		userMessage.append(errorType.getErrorMessage());
		MessageDialog.openError(shell, "Error", userMessage.toString());
	}

	private static void log(int severity, StringBuilder logMessage, ApiException e) {
		ILog logger = Platform.getLog(Activator.getDefault().getBundle());
		logger.log(new Status(severity, Activator.PLUGIN_ID, Status.OK, logMessage.toString(), e));
	}
}
