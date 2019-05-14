/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package vasttext;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.datavec.api.conf.Configuration;
import org.deeplearning4j.datasets.iterator.MultiDataSetWrapperIterator;
import org.deeplearning4j.eval.ConfusionMatrix;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.WorkspaceMode;
import org.deeplearning4j.nn.conf.graph.MergeVertex;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.EmbeddingSequenceLayer;
import org.deeplearning4j.nn.conf.layers.GlobalPoolingLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.PoolingType;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

import vasttext.datasets.file.MultiLabelFileDataSet;
import vasttext.datasets.file.NoLabelFileDataSet;
import vasttext.datasets.file.SingleLabelFileDataSet;
import vasttext.datasets.memory.MultiLabelMemoryDataSet;
import vasttext.datasets.memory.NoLabelMemoryDataSet;
import vasttext.datasets.memory.SingleLabelMemoryDataSet;
import vasttext.evaluation.EvaluationMultiLabel;
import vasttext.recordreader.DataIteratorConstructor;
import vasttext.vectorizer.VasttextTextVectorizer;

public class Vasttext
{
	private static String NAME_SPACE = Vasttext.class.getName();
	
	private MultiLayerNetwork vasttextText=null;
	private ComputationGraph vasttextTextAndNumeric=null;
	private Boolean multiLabel;
	private Integer labelsSize;
	private List<String> labels;
	private int textFeaturesSize;
	private int numericFeaturesSize;
	private int denseDimension;
	private int epoch;
	private double lr;
	private double multiLabelActivation;
	private String typeVasttext;
	private VasttextTextVectorizer vectorizer;

//	private UIServer uiServer;
//	private StatsStorage statsStorage;
	
	//Configuration variables
	public final static String DENSE_DIMENSION = NAME_SPACE +".densedimension";
	public final static String LR = NAME_SPACE +  ".lr";
	public final static String EPOCHS = NAME_SPACE + ".epochs";
	public final static String MULTILABEL_ACTIVATION_THRESHOLD = NAME_SPACE + ".multilabelactivation";
	
	public void train(Configuration configuration, MultiLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		train(configuration, new DataIteratorConstructor(configuration, dataset));
	}
	
	public void train(Configuration configuration, SingleLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		train(configuration, new DataIteratorConstructor(configuration, dataset));
	}
	
	
	public void train(Configuration configuration, MultiLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		train(configuration, new DataIteratorConstructor(configuration, dataset));
	}
	
	public void train(Configuration configuration, SingleLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		train(configuration, new DataIteratorConstructor(configuration, dataset));
	}
	
	private void train(Configuration configuration, DataIteratorConstructor dataIteratorConstructor)
	{
		this.multiLabel=dataIteratorConstructor.isMultilabel();
		this.labels=dataIteratorConstructor.getLabels();
		this.labelsSize=labels.size();
		this.textFeaturesSize=dataIteratorConstructor.getTextFeaturesSize();
		this.numericFeaturesSize=dataIteratorConstructor.getNumericFeaturesSize();
		this.vectorizer=dataIteratorConstructor.getVectorizer();
		this.denseDimension=configuration.getInt(DENSE_DIMENSION, 100);
		this.lr=configuration.getFloat(LR, 0.05f);
		this.epoch=configuration.getInt(EPOCHS, 5);
		this.multiLabelActivation=configuration.getFloat(MULTILABEL_ACTIVATION_THRESHOLD, 0.5f);
		
		vasttextText=null;
		vasttextTextAndNumeric=null;
		
		MultiDataSetIterator train = dataIteratorConstructor.getDataIterator();
		
		if(numericFeaturesSize==0)
		{
			vasttextText = VasttextTextual();
			System.err.println("Training\nEpoch:");
			for (int i = 0; i < epoch; i++)
			{	
				System.err.print((i+1)+"\r");
				vasttextText.fit(train);
				train.reset();
			}
			System.err.println("\nTraining finished");
		}
		else
		{
			vasttextTextAndNumeric = VasttextTextualAndNumeric();
			System.err.println("Training\nEpoch:");
			for (int i = 0; i < epoch; i++)
			{	
				System.err.print((i+1)+"\r");
				vasttextTextAndNumeric.fit(train);
				train.reset();
			}
			System.err.println("\nTraining finished");
		}
	}
	
	public HashMap<String, Object> evaluate(MultiLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return evaluate(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public HashMap<String, Object> evaluate(SingleLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return evaluate(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public HashMap<String, Object> evaluate(MultiLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return evaluate(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public HashMap<String, Object> evaluate(SingleLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return evaluate(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public HashMap<String, Object> evaluate(Configuration configuration, MultiLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return evaluate(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public HashMap<String, Object> evaluate(Configuration configuration, SingleLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return evaluate(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public HashMap<String, Object> evaluate(Configuration configuration, MultiLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return evaluate(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public HashMap<String, Object> evaluate(Configuration configuration, SingleLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return evaluate(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	private HashMap<String, Object> evaluate(DataIteratorConstructor vasttextMemoryDataContrustor)
	{
		if(vasttextTextAndNumeric==null && vasttextText==null)
			throw new UnsupportedOperationException("Train or load a model before evaluate");
		HashMap<String, Object> evaluationResults = new HashMap<String, Object>(); 
		MultiDataSetIterator test = vasttextMemoryDataContrustor.getDataIterator();
		if(multiLabel)
		{
			System.err.println("Evaluating multi-label model:");
			EvaluationMultiLabel evaluationMultilLabel =  new EvaluationMultiLabel(labelsSize, multiLabelActivation);
			//I do not know why it ask for an array of evaluations
			//We skip the net2.evaluate otherwise the evaluation would be using Evaluation instead of EvaluationMultiLabel
			if(vasttextTextAndNumeric!=null)
				evaluationMultilLabel = vasttextTextAndNumeric.doEvaluation(test, new EvaluationMultiLabel[] {evaluationMultilLabel})[0];
			else if(vasttextText!=null)
				evaluationMultilLabel = vasttextText.doEvaluation(test, new EvaluationMultiLabel[] {evaluationMultilLabel})[0];

			evaluationResults.put("HammingLoss", evaluationMultilLabel.HammingLoss());
			evaluationResults.put("SubsetLoss", evaluationMultilLabel.subset01Loss());
			evaluationResults.put("MicroFscore", evaluationMultilLabel.MicroF());
			evaluationResults.put("MacroFscore", evaluationMultilLabel.MacroF());
			evaluationResults.put("VectorsSummary", evaluationMultilLabel.ActualVsPrediction());
			evaluationResults.put("Labels", labels);
		}
		else
		{
			Evaluation evaluation=null;
			if(vasttextTextAndNumeric!=null)
				evaluation = vasttextTextAndNumeric.evaluate(test);
			else //For an extrange reason you can train with a multidataset but not test with it
				evaluation = vasttextText.evaluate(new MultiDataSetWrapperIterator(test));
			evaluationResults.put("ConfusionMatrix", evaluation.confusionMatrix());
			evaluationResults.put("Labels", labels);
			double macro=0.0;
			double microNum=0.0;
			double microDen=0.0;
			int entryConfusion=0;
			ConfusionMatrix<Integer> confusion = evaluation.getConfusion();
			for(String label: labels)
			{
				evaluationResults.put("Precision"+label, evaluation.precision(labels.indexOf(label)));
				evaluationResults.put("Recall"+label, evaluation.recall(labels.indexOf(label)));
				evaluationResults.put("Fscore"+label, evaluation.f1(labels.indexOf(label)));
				macro+=evaluation.f1(labels.indexOf(label));
				for(String label2 :labels)
				{
					entryConfusion=confusion.getCount(labels.indexOf(label), labels.indexOf(label2));
					if(label.equals(label2))
						microNum+=entryConfusion;
					microDen+=entryConfusion;
				}
			}
			evaluationResults.put("MacroFscore", macro/labels.size());
			if(microDen>0.0)
				evaluationResults.put("MicroFscore", microNum/microDen);
			else
				evaluationResults.put("MicroFscore", 0.0);
		}
		
		return evaluationResults;
	}
	
	//Prediction labels
	
	public List<Object> predictLabels(MultiLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public List<Object> predictLabels(SingleLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public List<Object> predictLabels(NoLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public List<Object> predictLabels(MultiLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public List<Object> predictLabels(SingleLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public List<Object> predictLabels(NoLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
		
	public List<Object> predictLabels(Configuration configuration, MultiLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public List<Object> predictLabels(Configuration configuration, SingleLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public List<Object> predictLabels(Configuration configuration, NoLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public List<Object> predictLabels(Configuration configuration, MultiLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public List<Object> predictLabels(Configuration configuration, SingleLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public List<Object> predictLabels(Configuration configuration, NoLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictLabels(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	private List<Object> predictLabels(DataIteratorConstructor vasttextMemoryDataContrustor)
	{
		INDArray predictions = predict(vasttextMemoryDataContrustor);
		List<Object> predictionsLabels = new ArrayList<Object>();
		if(multiLabel)
		{
			predictions=predictions.gt(multiLabelActivation);
			List<String> activatedLabels;
			for(int i=0; i<predictions.rows(); i++)
			{
				//This is the worst case scenario in which all the labels are present
				activatedLabels = new ArrayList<String>(labelsSize);
				for(int j=0; j<labelsSize; j++)
				{
					if(predictions.getDouble(i, j)==1.0)
						activatedLabels.add(labels.get(j));
				}
				predictionsLabels.add(activatedLabels);
			}
		}
		else
		{
			INDArray predictionIndexes = Nd4j.argMax(predictions, 1);
			for(int i=0; i<predictionIndexes.length(); i++)
			{
				predictionsLabels.add(labels.get(predictionIndexes.getInt(i)));
			}
		}
		return predictionsLabels;
	}
	
	//Prediction Probabilities
	
	public List<HashMap<String, Double>> predictProbs(MultiLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public List<HashMap<String, Double>> predictProbs(SingleLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public List<HashMap<String, Double>> predictProbs(NoLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public List<HashMap<String, Double>> predictProbs(MultiLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public List<HashMap<String, Double>> predictProbs(SingleLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public List<HashMap<String, Double>> predictProbs(NoLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(new Configuration(), dataset, vectorizer));
	}
	
	public List<HashMap<String, Double>> predictProbs(Configuration configuration, MultiLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public List<HashMap<String, Double>> predictProbs(Configuration configuration, SingleLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public List<HashMap<String, Double>> predictProbs(Configuration configuration, NoLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public List<HashMap<String, Double>> predictProbs(Configuration configuration, MultiLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public List<HashMap<String, Double>> predictProbs(Configuration configuration, SingleLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	public List<HashMap<String, Double>> predictProbs(Configuration configuration, NoLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		return predictProbs(new DataIteratorConstructor(configuration, dataset, vectorizer));
	}
	
	private List<HashMap<String, Double>> predictProbs(DataIteratorConstructor vasttextMemoryDataContrustor)
	{
		INDArray predictions = predict(vasttextMemoryDataContrustor);
		List<HashMap<String, Double>> predictionsProbs = new ArrayList<HashMap<String, Double>>();
		INDArray prediction;
		HashMap<String, Double> probabilitiesLabels;
		for(int i=0; i<predictions.rows(); i++)
		{
			prediction=predictions.getRow(i);
			probabilitiesLabels= new HashMap<String, Double>(labelsSize);
			for(int j=0; j<labelsSize; j++)
			{
				probabilitiesLabels.put(labels.get(j), prediction.getDouble(j));
			}
			predictionsProbs.add(probabilitiesLabels);
		}
		return predictionsProbs;
	}
	
	private INDArray predict(DataIteratorConstructor vasttextMemoryDataContrustor)
	{
		if(vasttextTextAndNumeric!=null) //As we have only one output, the position 0 is the one of interest
			return vasttextTextAndNumeric.output(vasttextMemoryDataContrustor.getDataIterator())[0];
		else if(vasttextText!=null)
			return vasttextText.output(new MultiDataSetWrapperIterator(vasttextMemoryDataContrustor.getDataIterator()));
		else
			throw new UnsupportedOperationException("Train or load a model before predict.");
	}
	
	private ComputationGraph VasttextTextualAndNumeric()
	{
		Activation activation = null;
		LossFunction loss = null;
		//If multilabel, it is considered according to the book "Deep Learning with Python" to use the following parameters
		if(multiLabel)
		{
			activation = Activation.SIGMOID;
			loss = LossFunction.XENT; //Binary Crossentropy
		}
		else
		{
			//We're using a softmax/cross entropy for the binary classification, as the number of neurons is two. If the number of neurons would be one, then
			//the activation would be sigmoid and the loss binary crossentropy
			activation = Activation.SOFTMAX;
			loss = LossFunction.MCXENT;	//CATEGORICAL_CROSSENTROPY
		}

		System.err.println("LR:"+lr);
		
		System.err.println("Dense:"+denseDimension);

		ComputationGraphConfiguration  nnConf = new NeuralNetConfiguration.Builder()
				.updater(new Adam(lr))
				.weightInit(WeightInit.XAVIER)
				.trainingWorkspaceMode(WorkspaceMode.ENABLED)
                .inferenceWorkspaceMode(WorkspaceMode.ENABLED)
				.graphBuilder()
				.addInputs("Text", "Extra")
				//Embeddings Parts
				.addLayer("Embeddings", new EmbeddingSequenceLayer.Builder()
                        .nIn(textFeaturesSize)
                        .nOut(denseDimension)
                        .activation(Activation.IDENTITY)
                        //.activation(Activation.TANH)
                        //.dropOut(0.0)
                        .build(), "Text")
				.addLayer("GlobalPooling", new GlobalPoolingLayer.Builder()
                        .poolingType(PoolingType.AVG)
                        .poolingDimensions(2)
                        .collapseDimensions(true)
                        //.dropOut(0.0)
                        .build(), "Embeddings")
				//We're merging directly the values from the extra
				.addVertex("Merge", new MergeVertex(), "GlobalPooling","Extra")
				.addLayer("DenseAll", new DenseLayer.Builder()
						.nIn(denseDimension+numericFeaturesSize)
						.nOut(denseDimension/2)
						//.dropOut(0.5)
						//.l2(0.001)
						.build(), "Merge")
				.addLayer("Output", new OutputLayer.Builder()
						//.dropOut(0.5)
						.nIn(denseDimension/2)
                        .nOut(labelsSize)
                        .activation(activation)
                        .lossFunction(loss)
                        .build(), "DenseAll")
				.setOutputs("Output")
				.pretrain(false)
				.backprop(true)
				.build();

		return new ComputationGraph(nnConf);
	}
	
	private MultiLayerNetwork VasttextTextual()
	{
		Activation activation = null;
		LossFunction loss = null;
		//If multilabel, it is considered according to the book "Deep Learning with Python" to use the following parameters
		if(multiLabel)
		{
			activation = Activation.SIGMOID;
			loss = LossFunction.XENT; //Binary Crossentropy
		}
		else
		{
			//We're using a softmax/cross entropy for the binary classification, as the number of neurons is two. If the number of neurons would be one, then
			//the activation would be sigmoid and the loss binary crossentropy
			activation = Activation.SOFTMAX;
			loss = LossFunction.MCXENT;	//CATEGORICAL_CROSSENTROPY
		}

		MultiLayerConfiguration nnConf = new NeuralNetConfiguration.Builder()
                .updater(new Adam(lr))
                .weightInit(WeightInit.XAVIER)
                .trainingWorkspaceMode(WorkspaceMode.ENABLED)
                .inferenceWorkspaceMode(WorkspaceMode.ENABLED)
                .list()
                .layer(0, new EmbeddingSequenceLayer.Builder()
                        .nIn(textFeaturesSize)
                        .nOut(denseDimension)
                        .activation(Activation.IDENTITY)
                        .build())
                .layer(1, new GlobalPoolingLayer.Builder()
                        .poolingType(PoolingType.AVG)
                        .poolingDimensions(2)
                        .collapseDimensions(true)
                        .build())
                .layer(2, new OutputLayer.Builder()
                        .nIn(denseDimension)
                        .nOut(labelsSize)
                        .activation(activation)
                        .lossFunction(loss)
                        .build())
                .pretrain(false).backprop(true).build();

        return new MultiLayerNetwork(nnConf);
	}
	
	public void loadModel(InputStream inputStream) throws IOException, ClassNotFoundException
	{
		File tmpFile = File.createTempFile("vasttextModelLoader", "bin");
		try {
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(tmpFile));
	        IOUtils.copy(inputStream, bufferedOutputStream);
	        bufferedOutputStream.flush();
	        IOUtils.closeQuietly(bufferedOutputStream);
	        loadModel(tmpFile);
	        if(tmpFile!=null)
	        	tmpFile.delete();
		} catch (IOException e)
		{
            if(tmpFile != null){
                tmpFile.delete();
            }
            throw e;
        }
	}
	
	@SuppressWarnings("unchecked")
	public void loadModel(File file) throws FileNotFoundException, ClassNotFoundException, IOException
	{
		HashMap<String, Object> configuration = (HashMap<String, Object>) ModelSerializer.getObjectFromFile(file, "vasttext.config");
		multiLabel = (Boolean) configuration.get("multiLabel");
		vectorizer = new VasttextTextVectorizer();
		vectorizer.loadDictionary(configuration.get("dictionary"));
		labels = vectorizer.getLabels();
		labelsSize = labels.size();
		typeVasttext= (String) configuration.get("typeVasttext");
		multiLabelActivation = (Double) configuration.get("multiLabelActivation");
		
		if(typeVasttext.equalsIgnoreCase("textAndNumeric"))
		{
			vasttextTextAndNumeric=ModelSerializer.restoreComputationGraph(file); 
		}
		else if(typeVasttext.equalsIgnoreCase("onlyText"))
		{
			vasttextText=ModelSerializer.restoreMultiLayerNetwork(file);
		}
		else
		{
			throw new UnsupportedOperationException("Unknown type of model.");
		}
	}
	
	public void storeModel(File file) throws IOException
	{
		HashMap<String, Object> configuration = new HashMap<String, Object>();
		
		//We do not store the updaters
		if(vasttextText!=null)
		{
			ModelSerializer.writeModel(vasttextText, file, false);
			configuration.put("typeVasttext", "onlyText");
		}
		else if(vasttextTextAndNumeric!=null)
		{
			ModelSerializer.writeModel(vasttextTextAndNumeric, file, false);
			configuration.put("typeVasttext", "textAndNumeric");
		}
		else
			throw new UnsupportedOperationException("Train before store model");
		
		configuration.put("multiLabelActivation", multiLabelActivation);
		configuration.put("multiLabel", multiLabel);
		configuration.put("dictionary", vectorizer.getDictionary());
		
		ModelSerializer.addObjectToFile(file, "vasttext.config", configuration);
	}

}

