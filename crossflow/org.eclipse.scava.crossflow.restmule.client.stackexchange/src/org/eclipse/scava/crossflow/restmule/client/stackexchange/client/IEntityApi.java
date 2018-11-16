package org.eclipse.scava.crossflow.restmule.client.stackexchange.client;

import org.eclipse.scava.crossflow.restmule.core.data.IData;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.*;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Error;

public interface IEntityApi {
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/reputation 
	 */		
	IDataSet<ReputationResponse> getMeReputationReputationResponse(String filter, String callback, String site);
	
	/**
	 * null
	 * @param inname null
	 * @param order null
	 * @param max sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param min sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /tags 
	 */		
	IDataSet<Tags> getTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param tags String list (semicolon delimited).
	 * @param order null
	 * @param max sort = creation => date
	              sort = applied => number
	              sort = activity => date
	 * @param min sort = creation => date
	              sort = applied => number
	              sort = activity => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /tags/{tags}/synonyms 
	 */		
	IDataSet<TagSynonyms> getTagsSynonymsTagSynonyms(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = reputation => number
	              sort = creation => date
	              sort = name => string
	              sort = modified => date
	 * @param min sort = reputation => number
	              sort = creation => date
	              sort = name => string
	              sort = modified => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me 
	 */		
	IData<User> getMeUser(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/questions/unaccepted 
	 */		
	IDataSet<QuestionsResponse> getUsersQuestionsUnacceptedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /me/associated 
	 */		
	IDataSet<NetworkUsers> getMeAssociatedNetworkUsers(String filter, String callback);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/inbox 
	 */		
	IDataSet<InboxItems> getMeInboxInboxItems(String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @param since Unix date.
	 * @return OK
	 * @path /me/inbox/unread 
	 */		
	IDataSet<InboxItems> getMeInboxUnreadInboxItems(String filter, String callback, String site, Integer since);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/questions/featured 
	 */		
	IDataSet<QuestionsResponse> getMeQuestionsFeaturedQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/privileges 
	 */		
	IDataSet<Privileges> getMePrivileges(String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/answers 
	 */		
	IDataSet<Answers> getUsersAnswers(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /posts/{ids} 
	 */		
	IDataSet<Posts> getPostsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/questions/unanswered 
	 */		
	IDataSet<QuestionsResponse> getUsersQuestionsUnansweredQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param inname null
	 * @param order null
	 * @param max sort = rank => string
	              sort = name => string
	              sort = type => string
	 * @param min sort = rank => string
	              sort = name => string
	              sort = type => string
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /badges 
	 */		
	IDataSet<Badges> getBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param id null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{id}/reputation-history/full 
	 */		
	IDataSet<ReputationHistoryResponse> getUsersReputation_historyFullReputationHistoryResponse(Integer id, String filter, String callback, String site);
	
	/**
	 * null
	 * @param tags String list (semicolon delimited).
	 * @param order null
	 * @param max sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param min sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /tags/{tags}/info 
	 */		
	IDataSet<Tags> getTagsInfoTags(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = creation => date
	              sort = approval => date
	              sort = rejection => date
	 * @param min sort = creation => date
	              sort = approval => date
	              sort = rejection => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /suggested-edits 
	 */		
	IDataSet<SuggestedEdits> getSuggested_editsSuggestedEdits(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/reputation-history 
	 */		
	IDataSet<ReputationHistoryResponse> getMeReputation_historyReputationHistoryResponse(String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = creation => date
	              sort = approval => date
	              sort = rejection => date
	 * @param min sort = creation => date
	              sort = approval => date
	              sort = rejection => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/suggested-edits 
	 */		
	IDataSet<SuggestedEdits> getUsersSuggested_editsSuggestedEdits(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/top-question-tags 
	 */		
	IData<TopTagObjects> getMeTop_question_tagsTopTagObjects(String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /questions/{ids} 
	 */		
	IDataSet<QuestionsResponse> getQuestionsQuestionsResponseByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param tagged String list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /questions/featured 
	 */		
	IDataSet<QuestionsResponse> getQuestionsFeaturedQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /privileges 
	 */		
	IDataSet<Privileges> getPrivileges(String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = creation => date
	              sort = votes => number
	 * @param min sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /comments/{ids} 
	 */		
	IDataSet<Comments> getCommentsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param inname null
	 * @param order null
	 * @param max sort = rank => string
	              sort = name => string
	 * @param min sort = rank => string
	              sort = name => string
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /badges/tags 
	 */		
	IDataSet<Badges> getBadgesTagsBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param tags String list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/tags/{tags}/top-answers 
	 */		
	IDataSet<Answers> getMeTagsTop_answersAnswers(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = creation => date
	              sort = votes => number
	 * @param min sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/mentioned 
	 */		
	IDataSet<Comments> getMeMentionedComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /me/merges 
	 */		
	IDataSet<AccountMerge> getMeMergesAccountMerge(String filter, String callback);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /badges/{ids}/recipients 
	 */		
	IDataSet<Badges> getBadgesRecipientsBadges(String ids, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /errors 
	 */		
	IDataSet<Errors> getErrors(String filter, String callback);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = rank => string
	              sort = name => string
	              sort = type => string
	 * @param min sort = rank => string
	              sort = name => string
	              sort = type => string
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/badges 
	 */		
	IDataSet<Badges> getMeBadges(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @param since Unix date.
	 * @return OK
	 * @path /events 
	 */		
	IDataSet<Events> getEvents(String filter, String callback, String site, Integer since);
	
	/**
	 * null
	 * @param base null
	 * @param exclude String list (semicolon delimited).
	 * @param include String list (semicolon delimited).
	 * @param unsafe null
	 * @return OK
	 * @path /filters/create 
	 */		
	IData<SingleFilter> getFiltersCreateSingleFilter(String base, String exclude, String include, Boolean unsafe);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = reputation => number
	              sort = creation => date
	              sort = name => string
	              sort = modified => date
	 * @param min sort = reputation => number
	              sort = creation => date
	              sort = name => string
	              sort = modified => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/moderators 
	 */		
	IDataSet<UsersResponse> getUsersModeratorsUsersResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param id null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @param since Unix date.
	 * @return OK
	 * @path /users/{id}/inbox/unread 
	 */		
	IDataSet<InboxItems> getUsersInboxUnreadInboxItems(Integer id, String filter, String callback, String site, Integer since);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /users/{ids}/merges 
	 */		
	IDataSet<AccountMerge> getUsersMergesAccountMerge(String ids, String filter, String callback);
	
	/**
	 * null
	 * @param id null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{id}/write-permissions 
	 */		
	IData<WritePermissions> getUsersWrite_permissionsWritePermissions(Integer id, String filter, String callback, String site);
	
	/**
	 * null
	 * @param toId null
	 * @param order null
	 * @param max sort = creation => date
	              sort = votes => number
	 * @param min sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/comments/{toId} 
	 */		
	IDataSet<Comments> getMeCommentsByToId(Integer toId, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/top-answer-tags 
	 */		
	IData<TopTagObjects> getMeTop_answer_tagsTopTagObjects(String filter, String callback, String site);
	
	/**
	 * null
	 * @param id null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{id}/top-answer-tags 
	 */		
	IData<TopTagObjects> getUsersTop_answer_tagsTopTagObjects(Integer id, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/questions 
	 */		
	IDataSet<QuestionsResponse> getUsersQuestionsQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param inname null
	 * @param order null
	 * @param max sort = rank => string
	              sort = name => string
	 * @param min sort = rank => string
	              sort = name => string
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /badges/name 
	 */		
	IDataSet<Badges> getBadgesNameBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /posts/{ids}/revisions 
	 */		
	IDataSet<Revisions> getPostsRevisions(String ids, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param tags String list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = hot => none
	              sort = week => none
	              sort = month => none
	              sort = relevance => none
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = hot => none
	              sort = week => none
	              sort = month => none
	              sort = relevance => none
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/tags/{tags}/top-questions 
	 */		
	IDataSet<QuestionsResponse> getMeTagsTop_questionsQuestionsResponse(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = creation => date
	              sort = votes => number
	 * @param min sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/comments 
	 */		
	IDataSet<Comments> getMeComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/questions/no-answers 
	 */		
	IDataSet<QuestionsResponse> getMeQuestionsNo_answersQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/notifications/unread 
	 */		
	IDataSet<Notifications> getMeNotificationsUnreadNotifications(String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/questions 
	 */		
	IDataSet<QuestionsResponse> getMeQuestionsQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/reputation-history/full 
	 */		
	IDataSet<ReputationHistoryResponse> getMeReputation_historyFullReputationHistoryResponse(String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/write-permissions 
	 */		
	IData<WritePermissions> getMeWrite_permissionsWritePermissions(String filter, String callback, String site);
	
	/**
	 * null
	 * @param id null
	 * @param tags String list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{id}/tags/{tags}/top-answers 
	 */		
	IDataSet<Answers> getUsersTagsTop_answersAnswers(Integer id, String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = rank => none
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = rank => none
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /questions/{ids}/related 
	 */		
	IDataSet<QuestionsResponse> getQuestionsRelatedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = creation => date
	              sort = votes => number
	 * @param min sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/mentioned 
	 */		
	IDataSet<Comments> getUsersMentionedComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = creation => date
	              sort = votes => number
	 * @param min sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /answers/{ids}/comments 
	 */		
	IDataSet<Comments> getAnswersComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param inname null
	 * @param order null
	 * @param max sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param min sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /tags/moderator-only 
	 */		
	IDataSet<Tags> getTagsModerator_onlyTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = creation => date
	              sort = votes => number
	 * @param min sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/comments 
	 */		
	IDataSet<Comments> getUsersComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/questions/unanswered 
	 */		
	IDataSet<QuestionsResponse> getMeQuestionsUnansweredQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param accessTokens String list (semicolon delimited).
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /access-tokens/{accessTokens}/invalidate 
	 */		
	IDataSet<AccessTokens> getAccess_tokensInvalidateAccessTokens(String accessTokens, String filter, String callback);
	
	/**
	 * null
	 * @param tags String list (semicolon delimited).
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /tags/{tags}/related 
	 */		
	IDataSet<Tags> getTagsRelatedTags(String tags, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/answers 
	 */		
	IDataSet<Answers> getMeAnswers(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param min sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/tags 
	 */		
	IDataSet<Tags> getUsersTags(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = creation => date
	              sort = votes => number
	 * @param min sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /questions/{ids}/comments 
	 */		
	IDataSet<Comments> getQuestionsComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = added => date
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = added => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/favorites 
	 */		
	IDataSet<QuestionsResponse> getUsersFavoritesQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param accessTokens String list (semicolon delimited).
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /access-tokens/{accessTokens} 
	 */		
	IDataSet<AccessTokens> getAccess_tokensAccessTokensByAccessTokens(String accessTokens, String filter, String callback);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/questions/unaccepted 
	 */		
	IDataSet<QuestionsResponse> getMeQuestionsUnacceptedQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /sites 
	 */		
	IDataSet<Sites> getSites(String filter, String callback);
	
	/**
	 * null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/timeline 
	 */		
	IData<UserTimelineObjects> getMeTimelineUserTimelineObjects(Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/reputation-history 
	 */		
	IDataSet<ReputationHistoryResponse> getUsersReputation_historyReputationHistoryResponse(String ids, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/notifications 
	 */		
	IDataSet<Notifications> getMeNotifications(String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = creation => date
	              sort = votes => number
	 * @param min sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /comments 
	 */		
	IDataSet<Comments> getComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param id null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{id}/inbox 
	 */		
	IDataSet<InboxItems> getUsersInboxInboxItems(Integer id, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = rank => string
	              sort = name => string
	              sort = type => string
	 * @param min sort = rank => string
	              sort = name => string
	              sort = type => string
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /badges/{ids} 
	 */		
	IDataSet<Badges> getBadgesByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param inname null
	 * @param order null
	 * @param max sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param min sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /tags/required 
	 */		
	IDataSet<Tags> getTagsRequiredTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = creation => date
	              sort = approval => date
	              sort = rejection => date
	 * @param min sort = creation => date
	              sort = approval => date
	              sort = rejection => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /suggested-edits/{ids} 
	 */		
	IDataSet<SuggestedEdits> getSuggested_editsSuggestedEditsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param accessTokens String list (semicolon delimited).
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /apps/{accessTokens}/de-authenticate 
	 */		
	IDataSet<AccessTokens> getAppsDe_authenticateAccessTokens(String accessTokens, String filter, String callback);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /posts 
	 */		
	IDataSet<Posts> getPosts(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /notifications 
	 */		
	IDataSet<Notifications> getNotifications(String filter, String callback);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = reputation => number
	              sort = creation => date
	              sort = name => string
	              sort = modified => date
	 * @param min sort = reputation => number
	              sort = creation => date
	              sort = name => string
	              sort = modified => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids} 
	 */		
	IDataSet<UsersResponse> getUsersUsersResponseByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /inbox 
	 */		
	IDataSet<InboxItems> getInboxInboxItems(String filter, String callback);
	
	/**
	 * null
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /info 
	 */		
	IData<InfoObject> getInfoInfoObject(String site);
	
	/**
	 * null
	 * @param tag null
	 * @param period null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /tags/{tag}/top-answerers/{period} 
	 */		
	IDataSet<TagScoreObjects> getTagsTop_answerersTagScoreObjectsByPeriod(String tag, String period, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/reputation 
	 */		
	IDataSet<ReputationResponse> getUsersReputationReputationResponse(String ids, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param tags String list (semicolon delimited).
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /tags/{tags}/faq 
	 */		
	IDataSet<QuestionsResponse> getTagsFaqQuestionsResponse(String tags, String filter, String callback, String site);
	
	/**
	 * null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /badges/recipients 
	 */		
	IDataSet<Badges> getBadgesRecipientsBadges(Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param min sort = popular => number
	              sort = activity => date
	              sort = name => string
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/tags 
	 */		
	IDataSet<Tags> getMeTags(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param id null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{id}/privileges 
	 */		
	IDataSet<Privileges> getUsersPrivileges(Integer id, String filter, String callback, String site);
	
	/**
	 * null
	 * @param tag null
	 * @param period null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /tags/{tag}/top-askers/{period} 
	 */		
	IDataSet<TagScoreObjects> getTagsTop_askersTagScoreObjectsByPeriod(String tag, String period, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = creation => date
	              sort = approval => date
	              sort = rejection => date
	 * @param min sort = creation => date
	              sort = approval => date
	              sort = rejection => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/suggested-edits 
	 */		
	IDataSet<SuggestedEdits> getMeSuggested_editsSuggestedEdits(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/questions/featured 
	 */		
	IDataSet<QuestionsResponse> getUsersQuestionsFeaturedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = added => date
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = added => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /me/favorites 
	 */		
	IDataSet<QuestionsResponse> getMeFavoritesQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /answers 
	 */		
	IDataSet<Answers> getAnswers(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param since Unix date.
	 * @return OK
	 * @path /inbox/unread 
	 */		
	IDataSet<InboxItems> getInboxUnreadInboxItems(String filter, String callback, Integer since);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /questions/{ids}/answers 
	 */		
	IDataSet<Answers> getQuestionsAnswers(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = rank => string
	              sort = name => string
	              sort = type => string
	              sort = awarded => date
	 * @param min sort = rank => string
	              sort = name => string
	              sort = type => string
	              sort = awarded => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/badges 
	 */		
	IDataSet<Badges> getUsersBadges(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /answers/{ids} 
	 */		
	IDataSet<Answers> getAnswersByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filters String list (semicolon delimited).
	 * @return OK
	 * @path /filters/{filters} 
	 */		
	IDataSet<Filters> getFiltersByFilters(String filters);
	
	/**
	 * null
	 * @param tagged String list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = hot => none
	              sort = week => none
	              sort = month => none
	              sort = relevance => none
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = hot => none
	              sort = week => none
	              sort = month => none
	              sort = relevance => none
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /questions 
	 */		
	IDataSet<QuestionsResponse> getQuestionsQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = creation => date
	              sort = approval => date
	              sort = rejection => date
	 * @param min sort = creation => date
	              sort = approval => date
	              sort = rejection => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /posts/{ids}/suggested-edits 
	 */		
	IDataSet<SuggestedEdits> getPostsSuggested_editsSuggestedEdits(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = reputation => number
	              sort = creation => date
	              sort = name => string
	              sort = modified => date
	 * @param min sort = reputation => number
	              sort = creation => date
	              sort = name => string
	              sort = modified => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/moderators/elected 
	 */		
	IDataSet<UsersResponse> getUsersModeratorsElectedUsersResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Guid list (semicolon delimited).
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /revisions/{ids} 
	 */		
	IDataSet<Revisions> getRevisionsByIds(String ids, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = rank => none
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = rank => none
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /questions/{ids}/linked 
	 */		
	IDataSet<QuestionsResponse> getQuestionsLinkedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param id null
	 * @return OK
	 * @path /errors/{id} 
	 */		
	IData<Errordefinition> getErrorsErrordefinitionById(Integer id);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = creation => date
	              sort = votes => number
	 * @param min sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /posts/{ids}/comments 
	 */		
	IDataSet<Comments> getPostsComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param order null
	 * @param max sort = creation => date
	              sort = applied => number
	              sort = activity => date
	 * @param min sort = creation => date
	              sort = applied => number
	              sort = activity => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /tags/synonyms 
	 */		
	IDataSet<TagSynonyms> getTagsSynonymsTagSynonyms(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /notifications/unread 
	 */		
	IDataSet<Notifications> getNotificationsUnreadNotifications(String filter, String callback);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param toid null
	 * @param order null
	 * @param max sort = creation => date
	              sort = votes => number
	 * @param min sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/comments/{toid} 
	 */		
	IDataSet<Comments> getUsersCommentsByToid(String ids, Integer toid, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param tags String list (semicolon delimited).
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /tags/{tags}/wikis 
	 */		
	IDataSet<TagWikis> getTagsWikisTagWikis(String tags, String filter, String callback, String site);
	
	/**
	 * null
	 * @param inname null
	 * @param order null
	 * @param max sort = reputation => number
	              sort = creation => date
	              sort = name => string
	              sort = modified => date
	 * @param min sort = reputation => number
	              sort = creation => date
	              sort = name => string
	              sort = modified => date
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users 
	 */		
	IDataSet<UsersResponse> getUsersUsersResponse(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/timeline 
	 */		
	IData<UserTimelineObjects> getUsersTimelineUserTimelineObjects(String ids, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /questions/{ids}/timeline 
	 */		
	IDataSet<QuestionTimelineEvents> getQuestionsTimelineQuestionTimelineEvents(String ids, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @return OK
	 * @path /users/{ids}/associated 
	 */		
	IDataSet<NetworkUsers> getUsersAssociatedNetworkUsers(String ids, String filter, String callback);
	
	/**
	 * null
	 * @param tagged String list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /questions/unanswered 
	 */		
	IDataSet<QuestionsResponse> getQuestionsUnansweredQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param id null
	 * @param tags String list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{id}/tags/{tags}/top-questions 
	 */		
	IDataSet<QuestionsResponse> getUsersTagsTop_questionsQuestionsResponse(Integer id, String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param id null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{id}/notifications 
	 */		
	IDataSet<Notifications> getUsersNotifications(Integer id, String filter, String callback, String site);
	
	/**
	 * null
	 * @param id null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{id}/top-question-tags 
	 */		
	IData<TopTagObjects> getUsersTop_question_tagsTopTagObjects(Integer id, String filter, String callback, String site);
	
	/**
	 * null
	 * @param ids Number list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{ids}/questions/no-answers 
	 */		
	IDataSet<QuestionsResponse> getUsersQuestionsNo_answersQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param id null
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /users/{id}/notifications/unread 
	 */		
	IDataSet<Notifications> getUsersNotificationsUnreadNotifications(Integer id, String filter, String callback, String site);
	
	/**
	 * null
	 * @param tagged String list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @return OK
	 * @path /questions/no-answers 
	 */		
	IDataSet<QuestionsResponse> getQuestionsNo_answersQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site);
	
	/**
	 * null
	 * @param tagged String list (semicolon delimited).
	 * @param order null
	 * @param max sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = relevance => none
	 * @param min sort = activity => date
	              sort = creation => date
	              sort = votes => number
	              sort = relevance => none
	 * @param sort null
	 * @param fromdate Unix date.
	 * @param todate Unix date.
	 * @param filter #Discussion
	                 
	                 The Stack Exchange API allows applications to exclude almost every field returned. For example, if an application did not care about a user's badge counts it could exclude user.badge_counts whenever it calls a method that returns users.
	                 
	                 An application excludes fields by creating a filter (via /filter/create) and passing it to a method in the filter parameter.
	                 
	                 Filters are immutable and non-expiring. An application can safely "bake in" any filters that are created, it is not necessary (or advisable) to create filters at runtime.
	                 
	                 The motivation for filters are several fold. Filters allow applications to reduce API responses to just the fields they are concerned with, saving bandwidth. With the list of fields an application is actually concerned with, the API can avoid unneccessary queries thereby decreasing response time (and reducing load on our infrastructure). Finally, filters allow us to be more conservative in what the API returns by default without a proliferation of parameters (as was seen with body, answers, and comments in the 1.x API family).
	                 
	                 #Safety
	                 
	                 Filters also carry a notion of safety, which is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
	                 
	                 Note that this does not mean that "safe" filter is mearly an "unsafe" one with all fields passed though UrlEncode(...). Many fields can and will contain HTML in all filter types (most notably, the *.body fields).
	                 
	                 When using unsafe filters, the API returns the highest fidelity data it can reasonably access for the given request. This means that in cases where the "safe" data is the only accessible data it will be returned even in "unsafe" filters. Notably the *.body fields are unchanged, as they are stored in that form. Fields that are unchanged between safe and unsafe filters are denoted in their types documentation.
	                 
	                 #Built In Filters
	                 
	                 The following filters are built in:
	                 
	                 default, each type documents which fields are returned under the default filter (for example, answers).
	                 withbody, which is default plus the *.body fields
	                 none, which is empty
	                 total, which includes just .total
	                 
	                 #Compatibility with V1.x
	                 
	                 For ease of transition from earlier API versions, the filters _b, _ba, _bc, _bca, _a, _ac, and _c are also built in. These are unsafe, and exclude a combination of question and answer body, comments, and answers so as to mimic the body, answers, and comments parameters that have been removed in V2.0. New applications should not use these filters.
	 * @param callback All API responses are JSON, we do support JSONP with the callback query parameter.
	 * @param site Each of these methods operates on a single site at a time, identified by the site parameter. This parameter can be the full domain name (ie. "stackoverflow.com"), or a short form identified by api_site_parameter on the site object.
	 * @param nottagged String list (semicolon delimited).
	 * @param title null
	 * @return OK
	 * @path /similar 
	 */		
	IDataSet<QuestionsResponse> getSimilarQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site, String nottagged, String title);
	
}