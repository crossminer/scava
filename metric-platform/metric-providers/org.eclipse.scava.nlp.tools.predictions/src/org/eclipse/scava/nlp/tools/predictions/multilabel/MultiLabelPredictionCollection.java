/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.predictions.multilabel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MultiLabelPredictionCollection
{
	protected List<MultiLabelPrediction> data;
	protected Boolean idsSet=null;
	protected boolean predictionSet=false;
		
	public MultiLabelPredictionCollection()
	{
		data = new ArrayList<MultiLabelPrediction>();
	}
	
	public MultiLabelPredictionCollection(int numberOfEntries)
	{
		data = new ArrayList<MultiLabelPrediction>(numberOfEntries);
	}
	
	public int size()
	{
		return data.size();
	}
	
	public void addText(String id, String text)
	{
		if(idsSet==null)
			idsSet=true;
		else if(idsSet==false)
			throw new IllegalArgumentException("Can't mix entries with id and without it.");
		data.add(new MultiLabelPrediction(id, text));
	}
	
	public void addText(String text)
	{
		if(idsSet==null)
			idsSet=false;
		else if(idsSet==true)
			throw new IllegalArgumentException("Can't mix entries with id and without it.");
		data.add(new MultiLabelPrediction(text));
	}
	
	public List<String> getTextCollection()
	{
		List<String> output= new ArrayList<String>(data.size());
		data.stream().forEachOrdered(p->{
			output.add(p.getText());
		});
		return output;
	}
	
	@SuppressWarnings("unchecked")
	public void setPredictions(List<Object> predictions)
	{
		if(predictions.size()!=data.size())
			throw new IllegalArgumentException("The number of predictions ["+ predictions.size()+"] doesn't match the number of data entries ["+data.size()+"]." );
		for(int i=0; i<data.size(); i++)
		{
			data.get(i).setLabels((List<String>) predictions.get(i));
		}
		predictionSet=true;
	}
	
	@SuppressWarnings("unchecked")
	public void setPredictions(List<Object> predictions, List<String> defaultLabels)
	{
		if(predictions.size()!=data.size())
			throw new IllegalArgumentException("The number of predictions ["+ predictions.size()+"] doesn't match the number of data entries ["+data.size()+"]." );
		for(int i=0; i<data.size(); i++)
		{
			if(data.get(i).getText().isEmpty())
				data.get(i).setLabels(defaultLabels);
			else
				data.get(i).setLabels((List<String>) predictions.get(i));
		}
		predictionSet=true;
	}
	
	public List<MultiLabelPrediction> getPredictionCollection()
	{
		return data;
	}
	
	public List<List<String>> getPredictedLabels()
	{
		if(predictionSet==false)
			return null;
		List<List<String>> output= new ArrayList<List<String>>(data.size());
		data.stream().forEachOrdered(p->{
			output.add(p.getLabels());
		});
		return output;
	}
	
	public List<MultiLabelPrediction> getDataPredictedWithLabel(String label)
	{
		if(predictionSet==false)
			return null;
		List<MultiLabelPrediction> output = data.stream()
				.filter(p->p.getLabels().contains(label)).collect(Collectors.toList());
		return(output);
	}
	
	public List<MultiLabelPrediction> getDataPredictedWithLabels(List<String> labels)
	{
		if(predictionSet==false)
			return null;
		List<MultiLabelPrediction> output = new ArrayList<MultiLabelPrediction>();
		data.stream().forEachOrdered(p->{
			if(!Collections.disjoint(labels, p.getLabels()))
				output.add(p);
		});
		return(output);
	}
	
	public List<MultiLabelPrediction> getDataPredictedWithoutLabel(String label)
	{
		if(predictionSet==false)
			return null;
		List<MultiLabelPrediction> output = data;
		output.removeIf(p -> p.getLabels().contains(label));
		return(output);
	}
	
	public List<MultiLabelPrediction> getDataPredictedWithoutLabels(List<String> labels)
	{
		if(predictionSet==false)
			return null;
		List<MultiLabelPrediction> output = new ArrayList<MultiLabelPrediction>();
		data.stream().forEachOrdered(p->{
			if(Collections.disjoint(labels, p.getLabels()))
				output.add(p);
		});
		return(output);
	}
	
	public List<String> getTextsPredictedWithLabel(String label)
	{
		return getFilteredTexts(getDataPredictedWithLabel(label));
	}
	
	public List<String> getTextsPredictedWithLabels(List<String> labels)
	{
		return getFilteredTexts(getDataPredictedWithLabels(labels));
	}	
	
	public List<String> getTextsPredictedWithoutLabel(String label)
	{
		return getFilteredTexts(getDataPredictedWithoutLabel(label));
	}
	
	public List<String> getTextsPredictedWithoutLabels(List<String> labels)
	{
		return getFilteredTexts(getDataPredictedWithoutLabels(labels));
	}
	
	private List<String> getFilteredTexts(List<MultiLabelPrediction> filteredList)
	{
		List<String> output= new ArrayList<String>(filteredList.size());
		filteredList.stream().map(p-> p.getText()).forEachOrdered(p->output.add(p));
		return output;
	}
	
	public List<Object> getIdsPredictedWithLabel(String label)
	{
		if(idsSet==false)
			throw new UnsupportedOperationException("The collection doesn't have IDs.");
		return getFilteredIds(getDataPredictedWithLabel(label));
	}
	
	public List<Object> getIdsPredictedWithLabels(List<String> labels)
	{
		if(idsSet==false)
			throw new UnsupportedOperationException("The collection doesn't have IDs.");
		return getFilteredIds(getDataPredictedWithLabels(labels));
	}
	
	public List<Object> getIdsPredictedWithoutLabel(String label)
	{
		if(idsSet==false)
			throw new UnsupportedOperationException("The collection doesn't have IDs.");
		return getFilteredIds(getDataPredictedWithoutLabel(label));
	}
	
	public List<Object> getIdsPredictedWithoutLabels(List<String> labels)
	{
		if(idsSet==false)
			throw new UnsupportedOperationException("The collection doesn't have IDs.");
		return getFilteredIds(getDataPredictedWithoutLabels(labels));
	}
	
	private List<Object> getFilteredIds(List<MultiLabelPrediction> filteredList)
	{
		List<Object> output= new ArrayList<Object>(filteredList.size());
		filteredList.stream().map(p-> p.getId()).forEachOrdered(p->output.add(p));
		return output;
	}
	
	public HashMap<Object, List<String>> getIdsWithPredictedLabels()
	{
		if(idsSet==false)
			throw new UnsupportedOperationException("The collection doesn't have IDs.");
		HashMap<Object, List<String>> idsWithLabels = new HashMap<Object, List<String>>(data.size());
		data.stream().forEachOrdered(p->{
			idsWithLabels.put(p.getId(), p.getLabels());
		});
		return idsWithLabels;
	}
}
