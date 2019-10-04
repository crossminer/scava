package rWorkflow;

import javax.script.*;
import org.renjin.script.*;
import org.renjin.sexp.*;
import org.apache.commons.math.complex.Complex;
import java.util.stream.StreamSupport;
import java.util.ArrayList;
import java.util.List;

public class Analyse extends AnalyseBase {

	@Override
	public RData consumeSoQ(RConfiguration rConfiguration) throws Exception {

		String analysisType = rConfiguration.analysisType.toString();
		
		RenjinScriptEngine engine = new RenjinScriptEngineFactory().getScriptEngine();
		
		engine.eval("library(e1071)\ndata(iris)\nsvmfit <- svm("+analysisType+", data=iris)\nprint(svmfit)\nstr(svmfit)\nls()");
		
		Object svmfit$nclassesObject = engine.eval("svmfit$nclasses");
		int svmfit$nclasses = (int) ((Vector) svmfit$nclassesObject).getElementAsInt(0);
		Object svmfit$levelsObject = engine.eval("svmfit$levels");
		List<String> svmfit$levels = new ArrayList<>();
		((Iterable<String>)svmfit$levelsObject).forEach(svmfit$levels::add);
		Object svmfit$gammaObject = engine.eval("svmfit$gamma");
		double svmfit$gamma = (double) ((Vector) svmfit$gammaObject).getElementAsDouble(0);
		
		RData rDataOutput = new RData();		
		rDataOutput.svmfit$nclasses = svmfit$nclasses;	
		rDataOutput.svmfit$levels = svmfit$levels;	
		rDataOutput.svmfit$gamma = svmfit$gamma;	
		
		return rDataOutput;

	}


}
