/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package vasttext.evaluation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.deeplearning4j.eval.BaseEvaluation;
import org.deeplearning4j.eval.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;

public class EvaluationMultiLabel extends BaseEvaluation<Evaluation>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1712945001456629280L;
	int nLabels;
	List<List<Integer>> actualList;
	List<List<Integer>> predictedList;
	long nExamples;
	double activationThreshold;
	
	public EvaluationMultiLabel(int nLabels, double activationThreshold)
	{
		this.nLabels=nLabels;
		this.activationThreshold=activationThreshold;
		reset();
	}
	
	public EvaluationMultiLabel(int nLabels)
	{
		this.nLabels=nLabels;
		this.activationThreshold=0.5;
		reset();
	}

	@Override
	public void eval(INDArray labels, INDArray networkPredictions)
	{
		// Length of real labels must be same as length of predicted labels
		if (!Arrays.equals(labels.shape(),networkPredictions.shape())) {
            throw new IllegalArgumentException("Unable to evaluate. Predictions and labels arrays are not same shape." +
                    " Predictions shape: " + Arrays.toString(networkPredictions.shape()) + ", Labels shape: " + Arrays.toString(labels.shape()));
        }
        INDArray guess;
        INDArray realOutcome;
        //The nExamples are given by mini batch, so we need to keep the total length
        nExamples += networkPredictions.rows();
        List<Integer> actual;
        List<Integer> predicted;
        for (int i = 0; i < networkPredictions.rows(); i++)
        {
        	//get the first row to analyze
        	guess = networkPredictions.getRow(i);
        	realOutcome=labels.getRow(i);
        	guess=guess.gt(activationThreshold);
        	actual=new ArrayList<Integer>();
        	predicted=new ArrayList<Integer>();
        	for(int j = 0; j < nLabels; j++)
        	{
        		actual.add((int) realOutcome.getDouble(j));
                predicted.add((int) guess.getDouble(j));
        	}
        	actualList.add(actual);
        	predictedList.add(predicted);
        }
	}
	
	public HashMap<String, List<Integer>> ActualVsPrediction()
	{
		int actual;
		int prediction;
		int tp;
		int zeroFlag;
		List<Integer> tpCounter = new ArrayList<Integer>();
		List<Integer> actualCounter = new ArrayList<Integer>();
		List<Integer> predictionCounter = new ArrayList<Integer>();
		List<Integer> zeroCounter = new ArrayList<Integer>();
		HashMap<String, List<Integer>> results = new HashMap<String, List<Integer>>();
		for(int j=0; j<nLabels; j++)
		{
			tpCounter.add(0);
			actualCounter.add(0);
			predictionCounter.add(0);
		}
		zeroCounter.add(0);
		for(int i=0; i<nExamples; i++)
		{
			zeroFlag=0;
			for(int j=0; j<nLabels; j++)
			{	
				actual = actualList.get(i).get(j);
				prediction = predictedList.get(i).get(j);
				tp=actual*prediction;
				if(tp==1)
					tpCounter.set(j, tpCounter.get(j)+1);
				if(actual==1)	
					actualCounter.set(j, actualCounter.get(j)+1);
				if(prediction==1)
				{
					predictionCounter.set(j, predictionCounter.get(j)+1);
					zeroFlag=1;
				}
			}
			if(zeroFlag==0)
				zeroCounter.set(0, zeroCounter.get(0)+1);
		}
		results.put("ExactMatch", tpCounter);
		results.put("Actual", actualCounter);
		results.put("Prediction", predictionCounter);
		results.put("ZeroLabels", zeroCounter);
		return results;
	}
	
	public double MicroF()
	{
		if(nExamples==0 || actualList== null || predictedList==null)
			throw new IllegalArgumentException("Unable to calculate MicroF. No examples to evaluate were found");
		if( nExamples!=actualList.size() || nExamples!=predictedList.size())
			throw new IllegalArgumentException("Unable to calculate MicroF. Number of examples do not match");
		HashMap<String, List<Integer>> results = ActualVsPrediction();
		List<Integer> tpCounter = results.get("ExactMatch");
		List<Integer> actualCounter = results.get("Actual");
		List<Integer> predictionCounter = results.get("Prediction");
		double microF=0.0;
		int num=0;
		int den=0;
		for(int j=0; j<nLabels; j++)
		{
			num+=tpCounter.get(j);
			den+=actualCounter.get(j)+predictionCounter.get(j);		
		}
		if(den!=0)
			microF+=2.0*num/den;
		return microF;
	}
	
	public double MacroF()
	{
		if(nExamples==0 || actualList== null || predictedList==null)
			throw new IllegalArgumentException("Unable to calculate MacroF. No examples to evaluate were found");
		if( nExamples!=actualList.size() || nExamples!=predictedList.size())
			throw new IllegalArgumentException("Unable to calculate MacroF. Number of examples do not match");
		HashMap<String, List<Integer>> results = ActualVsPrediction();
		List<Integer> tpCounter = results.get("ExactMatch");
		List<Integer> actualCounter = results.get("Actual");
		List<Integer> predictionCounter = results.get("Prediction");
		double macroF=0.0;
		for(int j=0; j<nLabels; j++)
		{
			if(tpCounter.get(j)!=0)
				macroF+=2.0*tpCounter.get(j)/(actualCounter.get(j)+predictionCounter.get(j));
		}
		macroF/=nLabels;
		return macroF;
	}
	
	public double HammingLoss()
	{
		if(nExamples==0 || actualList== null || predictedList==null)
			throw new IllegalArgumentException("Unable to calculate Hamming Loss. No examples to evaluate were found");
		if( nExamples!=actualList.size() || nExamples!=predictedList.size())
			throw new IllegalArgumentException("Unable to calculate Hamming Loss. Number of examples do not match");
		int actual;
		int prediction;
		int lossLabel;
		double hammingLoss=0.0;
		for(int i=0; i<nExamples; i++)
		{
			lossLabel=0;
			for(int j=0; j<nLabels; j++)
			{
				actual = actualList.get(i).get(j);
				prediction = predictedList.get(i).get(j);
				lossLabel += actual ^ prediction;
			}
			hammingLoss += lossLabel;
		}
		hammingLoss/=nExamples*nLabels;
		return hammingLoss;
	}
	
	public double subset01Loss()
	{
		if(nExamples==0 || actualList== null || predictedList==null)
			throw new IllegalArgumentException("Unable to calculate Subset 0/1 Loss. No examples to evaluate were found");
		if( nExamples!=actualList.size() || nExamples!=predictedList.size())
			throw new IllegalArgumentException("Unable to calculate Subset 0/1 Loss. Number of examples do not match");
		double subsetLoss = 0.0;
		for(int i=0; i<nExamples; i++)
		{
			if(!actualList.get(i).equals(predictedList.get(i)))
			{
				subsetLoss++;
			}
		}
		return subsetLoss/nExamples;
	}
	
	@Override
	public void merge(Evaluation other) {
				
	}

	@Override
	public void reset() {
		actualList= new ArrayList<List<Integer>>();
		predictedList= new ArrayList<List<Integer>>();
		nExamples=0;
	}

	@Override
	public String stats() {
		System.err.println("Statistics not not implemented");
		return null;
	}

}
