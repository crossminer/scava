/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jacob Carter - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.bugtrackingsystem.github.api;

import java.util.List;
import java.util.Map;

import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_PULLS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_REPOS;
import static org.eclipse.egit.github.core.client.PagedRequest.PAGE_FIRST;
import static org.eclipse.egit.github.core.client.PagedRequest.PAGE_SIZE;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.PullRequestService;

import com.google.gson.reflect.TypeToken;

public class ExtendedPullRequestService extends PullRequestService {

    public ExtendedPullRequestService() {
      super();
    }
    
    public ExtendedPullRequestService(GitHubClient client) {
        super(client);
      }
    
    public PageIterator<PullRequest> pagePullRequests(
            IRepositoryIdProvider repository, Map<String, String> filterData) {
        PagedRequest<PullRequest> request = createPullsRequest(repository,
                filterData, PAGE_FIRST, PAGE_SIZE);
        return createPageIterator(request);
    }

    protected PagedRequest<PullRequest> createPullsRequest(
            IRepositoryIdProvider provider, Map<String, String> filterData,
            int start, int size) {
        final String id = getId(provider);

        StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
        uri.append('/').append(id);
        uri.append(SEGMENT_PULLS);
        PagedRequest<PullRequest> request = createPagedRequest(start, size);
        request.setUri(uri);
        if (filterData != null) {
            request.setParams(filterData);
        }
        request.setType(new TypeToken<List<PullRequest>>() {
        }.getType());
        return request;
    }
}
