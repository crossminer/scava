package org.eclipse.scava.crossflow.restmule.client.stackexchange.callback;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_INFO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.core.callback.AbstractCallback;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.data.StackExchangeDataSet;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.page.StackExchangePagination;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.util.StackExchangePropertiesUtil;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Response;

public class StackExchangeCallback<D> extends AbstractCallback<D, List<D>, StackExchangeDataSet<D>> {

	private static final Logger LOG = LogManager.getLogger(StackExchangeCallback.class);

	private static StackExchangePagination paginationPolicy = StackExchangePagination.get();

	public StackExchangeCallback() {
		super(new StackExchangeDataSet<D>());
	}

	// FIXME move these methods to super abstract class <--

	@Override
	public void handleResponse(Response<List<D>> response) {
		System.out.println("StackExchangeCallback.handleResponse from response.headers().toString()="+response.headers().toString());
		System.out.println("StackExchangeCallback.handleResponse from response.body()="+response.body());
		System.out.println("StackExchangeCallback.handleResponse from response.raw().body()="+response.raw().body());
		
		this.dataset.addElements(response.body());
	}

	@Override
	public void handleTotal(Response<List<D>> response) {
		System.out.println("handleTotal from response="+response);
		// Ignore for the moment
	}

	@Override
	public void handleError(Call<List<D>> call, Throwable t) {
		System.out.println("handleError from call="+call);
		LOG.error(t.getMessage());
		LOG.error(call.request().url()); // TODO RETRY
	}
	

	// --->

	@Override
	public Integer totalIterations(Response<List<D>> response) { // FIXME
		System.out.println("totalIterations from response="+response);
		Integer totalIterations = 100;//null;//4;

		// PN: totalIterations = total / pagesize
		
		Headers headers = response.headers();
		String pagination = StackExchangePropertiesUtil.get(PAGE_INFO);
		String headerValue;
		if ((headerValue = headers.get(pagination)) != null){
			HashMap<String, String> links = getLinks(headerValue);
			return getPageFromURL(links.get("LAST"));
		}
		return totalIterations;
//		 return null; // FIXME!! Return pp.start()
	}

	public static final HashMap<String, String> getLinks(String headerValue) { // FIXME
		System.out.println("getLinks from headerValue="+headerValue); // TODO PN: return HashMap of api request links, containing all page numbers of
										// the request?

		HashMap<String, String> result = new HashMap<>();
		if (headerValue != null) {
			Iterator<String> iterator = Arrays.asList(headerValue.split(", ")).iterator();
			while (iterator.hasNext()) {
				String[] split = iterator.next().split(">; rel=\"");
				result.put(split[1].substring(0, split[1].length() - 1).toUpperCase(), split[0].substring(1));
			}
		}
		return result;
	}

	public static Integer getPageFromURL(String url) { // FIXME
		System.out.println("getPageFromURL="+url);
		String regex = "page=(\\d*)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			return Integer.valueOf(matcher.group(1));
		} else {
			return null;
		}
	}

}