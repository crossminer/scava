module org::eclipse::scava::dependency::model::osgi::language::internal::IO

import Prelude;
import IO;

@javaClass{org.eclipse.scava.dependency.model.osgi.language.internal.ManifestIO}
@reflect{for debugging}
public java map[str, str] loadManifest(loc file);

