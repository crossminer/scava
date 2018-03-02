/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.integration;

import java.util.List;

import org.eclipse.scava.business.model.Artifact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author Juri Di Rocco
 *
 */
public interface ArtifactRepository extends MongoRepository<Artifact, String> {

	@Query("{ $where : 'this.dependencies.length < ?0'}")
	public List<Artifact> findByNumdependencies(int numDependencies);
	public Page<Artifact> findAll(Pageable pageable); 
	public Artifact findOneByName(String name);
	public List<Artifact> findFirst10ByOrderByIdDesc();
	public Artifact findOneByFullName(String fullName);
}
