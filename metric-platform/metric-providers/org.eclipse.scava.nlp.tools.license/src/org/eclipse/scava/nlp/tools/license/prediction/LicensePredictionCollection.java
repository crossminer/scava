package org.eclipse.scava.nlp.tools.license.prediction;


import java.util.HashMap;
import java.util.List;

import org.eclipse.scava.nlp.tools.predictions.externalExtra.ExternalExtraFeaturesObject;
import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPredictionCollection;

public class LicensePredictionCollection extends SingleLabelPredictionCollection
{
	public LicensePredictionCollection()
	{
		super();
	}
	
	public LicensePredictionCollection(int numberOfEntries)
	{
		super(numberOfEntries);
	}
	
	@Override
	public void addText(String id, String text)
	{
		if(idsSet==null)
			idsSet=true;
		if(extraSet==null)
			extraSet=false;
		else if(idsSet==false)
			throw new IllegalArgumentException("Can't mix entries with id and without it.");
		else if(extraSet==true)
			throw new IllegalArgumentException("Can't mix entries with extra elements and without them.");
		data.add(new LicensePrediction(id, text));
	}
	
	@Override
	public void addText(String text)
	{
		if(idsSet==null)
			idsSet=false;
		if(extraSet==null)
			extraSet=false;
		else if(idsSet==true)
			throw new IllegalArgumentException("Can't mix entries with id and without it.");
		else if(extraSet==true)
			throw new IllegalArgumentException("Can't mix entries with extra external elements and without them.");
		data.add(new LicensePrediction(text));
	}
	
	/**
	 * This method is not supported
	 */
	@Override
	public void addText(String text,  ExternalExtraFeaturesObject externalExtra)
	{
	}
	
	/**
	 * This method is not supported
	 */
	@Override
	public void setPredictions(List<Object> predictions)
	{
	}
	
	/**
	 * This method is not supported
	 */
	@Override
	public void setPredictions(List<Object> predictions, String defaultLabel)
	{
	}
	
	public void setPredictionSet(boolean status)
	{
		predictionSet=status;
	}
	
	public HashMap<Object, LicensePrediction> getIdsWithPredictedLicenseInformation()
	{
		if(idsSet==false)
			throw new UnsupportedOperationException("The collection doesn't have IDs.");
		HashMap<Object, LicensePrediction> idsWithLicenseInformation = new HashMap<Object, LicensePrediction>(data.size());
		data.stream().forEachOrdered(p->{
			idsWithLicenseInformation.put(p.getId(), (LicensePrediction) p);
		});
		return idsWithLicenseInformation;
	}
	
}
