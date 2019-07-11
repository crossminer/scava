package org.eclipse.scava.nlp.classifiers.migrationpatternsdetector;

import java.util.List;
import java.util.regex.Pattern;

public class MigrationPatternDetector {
	
	private List<List<Pattern>> patternGroups;
	
	//Once Maracas is going to to be available, we will need to include the libraries here
	public MigrationPatternDetector() {
		patternGroups = MigrationPatternDetectorSingleton.getInstance().getPatternGroups();
	}
	
	public boolean analyzeTitle(String title)
	{
		int counter;
		for(List<Pattern> group : patternGroups)
		{
			counter=0;
			for(Pattern pattern : group)
			{
				if(pattern.matcher(title).find())
					counter++;
			}
			if(counter==group.size())
				return true;
		}
		return false;
	}

}
