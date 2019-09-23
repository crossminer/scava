package org.eclipse.scava.metricprovider.trans.migrationissuesmaracas.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class MigrationIssueMaracasTransMetric extends PongoDB {
	
	public MigrationIssueMaracasTransMetric() {}
	
	public MigrationIssueMaracasTransMetric(DB db) {
		setDb(db);
	}
	
	protected MaracasMeasurementCollection maracasMeasurements = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public MaracasMeasurementCollection getMaracasMeasurements() {
		return maracasMeasurements;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		maracasMeasurements = new MaracasMeasurementCollection(db.getCollection("MigrationIssueMaracasTransMetric.maracasMeasurements"));
		pongoCollections.add(maracasMeasurements);
	}
}