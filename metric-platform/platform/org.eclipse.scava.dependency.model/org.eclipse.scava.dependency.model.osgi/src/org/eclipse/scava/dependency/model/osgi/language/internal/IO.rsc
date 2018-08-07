@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::dependency::model::osgi::language::internal::IO

import Prelude;
import IO;

@javaClass{org.eclipse.scava.dependency.model.osgi.language.internal.ManifestIO}
@reflect{for debugging}
public java map[str, str] loadManifest(loc file);

