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

import org.eclipse.scava.business.model.migration.DetectionMetaData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Juri Di Rocco
 *
 */
public interface DetectionMetadataRepository extends MongoRepository<DetectionMetaData, String> {
	public DetectionMetaData findOneByCoordinate1AndCoordinate2AndClientM3
			(String coordinvate1, String coordinvate2, String filename);
//	@Query("{'dependencies.oldLibraryLocatio': ?0}")
	public List<DetectionMetaData> findByDetectionsOldLibraryLocation(String oldLibraryLocations);
}
