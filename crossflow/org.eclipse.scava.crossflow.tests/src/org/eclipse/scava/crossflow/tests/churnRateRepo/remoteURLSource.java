package org.eclipse.scava.crossflow.tests.churnRateRepo;

import java.util.List;

public class remoteURLSource extends remoteURLSourceBase {

	protected List<String> remoteURLs = null;
	protected long interval = 100;

	@Override
	public void produce() throws Exception {
		
			
				String remoteURLs = ("https://github.com/nf718/testingRepo.git");
				// pushing jobs of type URL (url strings) to URLs queue	
				sendToURLs(new URL(remoteURLs));
				
				

			
		}
	}





