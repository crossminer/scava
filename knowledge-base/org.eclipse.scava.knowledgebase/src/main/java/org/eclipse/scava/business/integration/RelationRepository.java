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

import org.bson.types.ObjectId;
import org.eclipse.scava.business.model.Relation;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Juri Di Rocco
 *
 */
public interface RelationRepository extends MongoRepository<Relation, String> {
	List<Relation> findByToArtifactIdAndTypeName(ObjectId id, String name);
	List<Relation> findByFromArtifactIdAndTypeName(ObjectId id, String name);
	List<Relation> findAllByTypeName(String name);
	//Relation findOneByFromArtifactIdAndToArtifactIdAndTypeName(ObjectId prj1Id, ObjectId Prj2Id, String name);
	//Relation findOneByToArtifactIdAndFromArtifactIdAndTypeName(String prj1Id, String Prj2Id, String name);
}
