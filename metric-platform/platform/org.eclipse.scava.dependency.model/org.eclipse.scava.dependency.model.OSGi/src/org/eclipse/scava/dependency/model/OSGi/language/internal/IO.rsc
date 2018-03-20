module org::eclipse::scava::dependency::model::OSGi::language::internal::IO

import Prelude;
import IO;

@javaClass{org.eclipse.scava.dependency.model.OSGi.language.internal.ManifestIO}
@reflect{for debugging}
public java map[str, str] loadManifest(loc manifestLoc);

@javaClass{org.eclipse.scava.dependency.model.OSGi.language.internal.ManifestIO}
@reflect{for debugging}
public java map[str, str] loadManifest(str manifestLoc);
