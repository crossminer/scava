package org.eclipse.scava.crossflow.examples.simple.nbody.cf;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;
import org.eclipse.scava.crossflow.runtime.utils.LogLevel;

public class Data extends DataBase {
	// CSV file writer(s)
	protected CsvWriter writer1;
	
	@Override
	public void consumePerformance(Line line) {
		System.out.println("Data results " + LocalDateTime.now());
		System.out.println(Arrays.toString(line.toObjectArray()));
		try {
			if ( writer1 == null ) {
				writer1 = new CsvWriter(new File(workflow.getOutputDirectory(), "timing.csv").getAbsolutePath(), "text",  "cached");
			}
//			if (writeHeader) {
//				writer1.writeRecord( "phi,prep,accel,vel,pos,total,flops,mem,hash,total",  false );
//				writeHeader = false;
//			}
			writer1.writeRecord( line.getText(),  line.isCached() );
			writer1.flush();
		} catch (Exception e) {
			workflow.log(LogLevel.ERROR, e.getMessage());
		}
	}
	

}