/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.github.api;

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
