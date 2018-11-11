package org.eclipse.scava.crossflow.restmule.client.stackexchange.callback;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_INFO;

import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.core.callback.AbstractWrappedCallback;
import org.eclipse.scava.crossflow.restmule.core.page.IWrap;
import org.mortbay.log.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import org.eclipse.scava.crossflow.restmule.client.stackexchange.data.StackExchangeDataSet;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.page.StackExchangePagination;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.util.StackExchangePropertiesUtil;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Response;

public class StackExchangeWrappedCallback<D,R extends IWrap<D>> extends AbstractWrappedCallback<D,R, StackExchangeDataSet<D>>  {

	private static final Logger LOG = LogManager.getLogger(StackExchangeWrappedCallback.class);
	
	private static StackExchangePagination paginationPolicy = StackExchangePagination.get();
	private static int maxCount = StackExchangePagination.getMaxResults(); // TODO: add to generator
	
	public StackExchangeWrappedCallback() {
		super(new StackExchangeDataSet<D>());
	}

	// FIXME move these methods to super abstract class <--
	
	@Override
	public void handleResponse(Response<R> response) {
		System.out.println("StackExchangeWrappedCallback.handleResponse from response.body().getItems()="+response.body().getItems());
		this.dataset.addElements(response.body().getItems());
	}

	@Override
	public void handleTotal(Response<R> response) {
		System.out.println("handleTotal from response="+response);
		Integer totalCount = response.body().getTotalCount();
		if (totalCount > maxCount) {
			Log.info("Please note that for this request, totalCount (" + totalCount + ") > maxCount (" + maxCount
					+ "), as such, only " + maxCount + " elements will be retrieved.");
			this.dataset.setTotal(maxCount);
		} else
			this.dataset.setTotal(totalCount);
	}// TODO: add update to generator

	@Override
	public void handleError(Call<R> call, Throwable t) {
		System.out.println("handleError from call="+call);
		LOG.error(t.getMessage());
		LOG.error(call.request().url()); // TODO RETRY
	}
	
	// --->
	
	@Override
	public Integer totalIterations(Response<R> response) { // FIXME
		Integer totalIterations = null;
		System.out.println("totalIterations from response="+response);
		Headers headers = response.headers();
		String pagination = StackExchangePropertiesUtil.get(PAGE_INFO);
		String headerValue;
		if ((headerValue = headers.get(pagination)) != null){
			HashMap<String, String> links = getLinks(headerValue);
			totalIterations = getPageFromURL(links.get("LAST"));
		} else {
			// TODO PN: parse response as JsonElement/JsonObject and extract "total" as in AbstractInterceptor
			
			
			
//			Gson gson = new Gson();
//			JsonReader jsonReader = new JsonReader(new StringReader(bodyString));
//			jsonReader.setLenient(true);
//			jsonResponse = gson.fromJson(new JsonReader(new StringReader(bodyString)), JsonElement.class);
//			
			// totalIterations = ( see above )
//			totalIterations = 4;
		}
		return totalIterations; // FIXME!! Return pp.start()
	}
	
	public static final HashMap<String, String> getLinks(String headerValue) { // FIXME
		System.out.println("getLinks from headerValue="+headerValue); // TODO PN: return HashMap of api request links, containing all page numbers of
		HashMap<String, String> result = new HashMap<>();
		if (headerValue!=null){
			Iterator<String> iterator = Arrays.asList(headerValue.split(", ")).iterator();
			while(iterator.hasNext()){
				String[] split= iterator.next().split(">; rel=\"");
				result.put(split[1].substring(0,split[1].length()-1).toUpperCase(), split[0].substring(1));	
			}
		} else {
			// TODO PN: get links by constructing them from the given link by increasing the "page" number until "total" DIV "pagesize" 
			// result.put( see above )
		}
		return result;
	}

	public static Integer getPageFromURL(String url){ // FIXME
		System.out.println("getPageFromURL="+url);
		String regex = "page=(\\d*)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()){
			return Integer.valueOf(matcher.group(1));
		} else {
			return null;
		}
	}
	
}
