/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.bugtrackingsystem.github.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_REPOS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_ISSUES;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_COMMENTS;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.IssueService;

import com.google.gson.reflect.TypeToken;

public class ExtendedIssueService extends IssueService {

    public ExtendedIssueService() {
        super();
    }

    public ExtendedIssueService(GitHubClient client) {
        super(client);
    }

    public PageIterator<ExtendedComment> getComments(String user,
            String repository, Map<String, String> filterData)
            throws IOException {
        verifyRepository(user, repository);

        String repoId = user + '/' + repository;

        StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
        uri.append('/').append(repoId);
        uri.append(SEGMENT_ISSUES);
        uri.append(SEGMENT_COMMENTS);
        PagedRequest<ExtendedComment> request = createPagedRequest();
        request.setParams(filterData).setUri(uri);
        request.setType(new TypeToken<List<ExtendedComment>>() {
        }.getType());
        return createPageIterator(request);
    }

}
