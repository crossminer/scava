package org.eclipse.scava.metricprovider.trans.migrationissuesmaracas;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.metricprovider.rascal.trans.model.Measurement;
import org.eclipse.scava.metricprovider.rascal.trans.model.RascalMetrics;
import org.eclipse.scava.metricprovider.trans.migrationissuesmaracas.model.MaracasMeasurement;
import org.eclipse.scava.metricprovider.trans.migrationissuesmaracas.model.MigrationIssueMaracasTransMetric;
import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class MigrationIssueMaracasTransMetricProvider implements ITransientMetricProvider<MigrationIssueMaracasTransMetric> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	private static Pattern maracasStart;
	private static Pattern classDetector;
	private static Pattern nestedClassDetector;
	private static Pattern methodDetector;
	private static Pattern paramsClassDetector;
	private static Pattern paramsDetector;
	private static Pattern slash;
	private static Pattern dot;
	private static Pattern comma;
	
	static
	{
		maracasStart=Pattern.compile("java\\+(?:method|constructor)://");
		classDetector=Pattern.compile("^((?:/\\p{Ll}+)*)/(\\p{Lu}\\p{L}*)/");
		nestedClassDetector=Pattern.compile("^((?:\\p{Lu}\\p{L}*/)+)");
		methodDetector=Pattern.compile("^(\\p{L}+)\\(");
		comma=Pattern.compile(", ?");
		paramsDetector=Pattern.compile("^([^\\)]+)\\)");
		paramsClassDetector=Pattern.compile("^(?:(?:(?:\\p{Ll}+[./])*(\\p{Lu}\\p{L}*))|(\\p{Ll}+))$");
		dot=Pattern.compile("(^\\\\.|\\\\.$)");
		slash=Pattern.compile("/");
	}
	
	@Override
	public String getIdentifier() {
		return MigrationIssueMaracasTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.bugs.migrationissuesmaracas";
	}

	@Override
	public String getFriendlyName() {
		return "Migration Issues Detection Maracas in Bug Trackers";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric detects migration issues in Bug Tracking Systems along with input from Maracas.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return !project.getBugTrackingSystems().isEmpty();
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList("trans.rascal.api.changedMethods");
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public MigrationIssueMaracasTransMetric adapt(DB db) {
		return new MigrationIssueMaracasTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, MigrationIssueMaracasTransMetric db) {
		
		RascalMetrics maracasMetric = getMaracasDB(project, uses.get(0));
		
		//Process all the changes occured in the delta and that have to be stored for future analysis
		for(Measurement measurement : maracasMetric.getMeasurements())
		{
			String processedMeasurement = (String) measurement.getDbObject().get("value");
			
			MaracasMeasurement maracasMeasurement = findMeasurementMaracas(db,processedMeasurement);
			
			if(maracasMeasurement==null)
			{
				maracasMeasurement = new MaracasMeasurement();
				maracasMeasurement.setChange(processedMeasurement);
				maracasMeasurement.setLastUpdateDate(Integer.valueOf(delta.getDate().toString()));
				
				String packageName="";
				String className="";
				String nestedClassesName="";
				String methodName="";
				List<String> parametersClassName=null;
				
				processedMeasurement=maracasStart.matcher(processedMeasurement).replaceFirst("");
				
				Matcher m = classDetector.matcher(processedMeasurement);
				if(m.find())
				{
					packageName=slash.matcher(m.group(1)).replaceAll("\\\\.");
					packageName=dot.matcher(packageName).replaceAll("");
					className=m.group(2);
					processedMeasurement=m.replaceAll("");
				}
				
				m = nestedClassDetector.matcher(processedMeasurement);
				if(m.find())
				{
					nestedClassesName= slash.matcher(m.group(1)).replaceAll("\\\\.");
					processedMeasurement=m.replaceAll("");
					nestedClassesName=dot.matcher(nestedClassesName).replaceAll("");			
				}
				
				m = methodDetector.matcher(processedMeasurement);
				if(m.find())
				{
					methodName=m.group(1);
					processedMeasurement=m.replaceAll("");
				}
				
				m=paramsDetector.matcher(processedMeasurement);
				if(m.find())
				{
					String[] params=comma.split(m.group(1));
					parametersClassName=new ArrayList<String>(m.groupCount());
					for(String param : params)
					{
						m=paramsClassDetector.matcher(param);
						while(m.find())
						{
							if(m.group(1)!=null)
								parametersClassName.add(m.group(1));
							else
								parametersClassName.add(m.group(2));
						}
					}
				}
					
				if(!packageName.isEmpty())
					maracasMeasurement.getRegex().add(packageName);
				
				maracasMeasurement.getRegex().add(className);
				
				if(!nestedClassesName.isEmpty())
				{
					maracasMeasurement.getRegex().add(nestedClassesName);
					maracasMeasurement.getRegex().add(className+"\\\\."+nestedClassesName);
				}
				
				if(parametersClassName!=null)
				{
					String finalMethod=methodName+"\\(";
					for(int i=0; i<parametersClassName.size(); i++)
					{
						finalMethod+="\\h*"+parametersClassName.get(i)+"\\h[^\\h,\\)]+\\h*";
						if(parametersClassName.size()>1 && i<parametersClassName.size()-1)
							finalMethod+=",";
					}
					maracasMeasurement.getRegex().add(finalMethod+"\\)");
				}
				else
				{
					maracasMeasurement.getRegex().add(methodName+"\\(\\h*\\)");
				}
				
				db.getMaracasMeasurements().add(maracasMeasurement);
			}
			else
				maracasMeasurement.setLastUpdateDate(Integer.valueOf(delta.getDate().toString()));
			db.sync();
		}
	}
	
	
	private RascalMetrics getMaracasDB(Project project, IMetricProvider iMetricProvider)
	{
		Mongo mongo;
		RascalMetrics rascalMetrics=null;
		try {
			mongo = Configuration.getInstance().getMongoConnection();
			DB db = mongo.getDB(project.getShortName());
			rascalMetrics = new RascalMetrics(db, iMetricProvider.getIdentifier());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return rascalMetrics;
	}
	
	private MaracasMeasurement findMeasurementMaracas(MigrationIssueMaracasTransMetric db, String change) {
		MaracasMeasurement maracasMeasurement = null;
		Iterable<MaracasMeasurement> maracasMeasurementIt = db.getMaracasMeasurements().find(
				MaracasMeasurement.CHANGE.eq(change));
		for (MaracasMeasurement mm : maracasMeasurementIt) {
			maracasMeasurement = mm;
		}
		return maracasMeasurement;
	}

}
