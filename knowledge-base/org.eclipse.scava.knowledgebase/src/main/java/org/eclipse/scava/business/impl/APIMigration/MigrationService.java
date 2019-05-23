package org.eclipse.scava.business.impl.APIMigration;

import org.eclipse.scava.business.model.MavenLibrary;
import org.eclipse.scava.business.model.migration.Delta;
import org.springframework.stereotype.Service;
@Service
public class MigrationService {
	public Delta createDelta(MavenLibrary libV1, MavenLibrary libV2) {
		//TODO Implements
		return null;
	}
//	public List<Detection> calculateDetection(Artifact art){
//		//TODO
//		Maracas m = new Maracas();
//		Collection<Detection> detections = m.getDetections("/Users/juri/Desktop/report/", "gitlfs-common-0.8.0");
//		detections.forEach(z->System.out.println(z.getType()));
//		return Lists.newArrayList(detections);
//	}
}
