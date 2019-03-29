/*******************************************************************************
 * Copyright (c) 2018-2019 Edge Hill University
 * Copyright (c) 2015-2018 Skymind, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ******************************************************************************/
package vasttext.vectorizer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import org.datavec.api.conf.Configuration;
import org.datavec.api.records.Record;
import org.datavec.api.records.metadata.RecordMetaDataURI;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.vector.Vectorizer;
import org.datavec.api.writable.NDArrayWritable;
import org.datavec.api.writable.Writable;
import org.datavec.nlp.stopwords.StopWords;
import org.datavec.nlp.tokenization.tokenizer.Tokenizer;
import org.datavec.nlp.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.datavec.nlp.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import vasttext.dictionary.VasttextDictionary;

public class VasttextTextVectorizer implements Vectorizer<INDArray>
{
	private static String NAME_SPACE = VasttextTextVectorizer.class.getName();
	
	protected TokenizerFactory tokenizerFactory;
    protected int minWordFrequency = 0;
    private int maxNgrams=1;
    private int maxSkipBigrams=1;
    public final static String MIN_WORD_FREQUENCY = "org.nd4j.nlp.minwordfrequency";
    public final static String DELETE_STOP_WORDS =  NAME_SPACE+ ".deleteStopWords";
    public final static String STOP_WORDS = "org.nd4j.nlp.stopwords";
    public final static String NGRAMS = NAME_SPACE+".ngrams";
    public final static String SKIP_NGRAMS = NAME_SPACE+".skipbigrams";
    protected Collection<String> stopWords=null;
    protected VasttextDictionary cache;
    private int featuresStartAt=0;
    private boolean fitFinished=false;

    public void setfitFinished()
    {
    	fitFinished=true;
    }
    
    public boolean hasfitFinished()
    {
    	return fitFinished;
    }

    public int getLabelIndex(String label)
    {
    	return cache.getLabelIndex(label, fitFinished);
    }

    public List<String> getLabels()
    {
    	return cache.getLabels();
    }

    public HashMap<String, Integer> getLabelsAndIndexes()
    {
    	HashMap<String, Integer> labelsIndex = new HashMap<String, Integer>(getLabels().size());

    	for(String label : getLabels())
    	{
    		labelsIndex.put(label, getLabelIndex(label));
    	}
    	return labelsIndex;
    }

    private List<String> ngramsGenerator(List<String> tokens)
    {
    	List<String> ngrams = new ArrayList<String>();
    	String ngram;
    	//Generalization for all the ngrams
    	for(int n=2; n<=maxNgrams; n++)
    	{
    		for(int i=0; i<tokens.size()-(n-1); i++)
    		{
    			ngram = "";
    			for(int j=0; j<n; j++)
    			{
    				ngram+=tokens.get(i+j);
    				if(j!=n-1)
    					ngram+=" ";
    			}
    			ngrams.add(ngram);
    		}
    	}
    	return ngrams;
    }

    private List<String> skipBigramsGenerator(List<String> tokens)
    {
    	List<String> ngrams = new ArrayList<String>();
    	String ngram;
    	//Generalization for all the skip ngrams
   		for(int i=0; i<tokens.size()-1; i++)
		{
			ngram = "";
			//We're skipping the generation of bigrams, we're just generating bigrams with holes of at least 1
			for(int j=i+2; j<i+maxSkipBigrams+2; j++)
			{
				if(j<tokens.size())
				{
					ngram=tokens.get(i)+" "+tokens.get(j);
					ngrams.add(ngram);
				}
			}
		}
    	return ngrams;
    }

	@Override
	public INDArray createVector(Object[] args) {
		List<Integer> document = new ArrayList<Integer>();
		//We get all the extra features
    	@SuppressWarnings("unchecked")
		List<String> tokens = (List<String>) args[0];

    	for(int i=0; i<tokens.size(); i++)
    	{
    		if(cache.indexOf(tokens.get(i))>=0)
    			document.add(cache.indexOf(tokens.get(i)));
    	}
    	if(maxNgrams>1)
    	{
    		List<String> ngrams = ngramsGenerator(tokens);
    		for(int i=0; i<ngrams.size(); i++)
        	{
        		if(cache.indexOf(ngrams.get(i))>=0)
        			document.add(cache.indexOf(ngrams.get(i)));
        	}
    	}
    	if(maxSkipBigrams>0)
    	{
    		List<String> ngrams = skipBigramsGenerator(tokens);
    		for(int i=0; i<ngrams.size(); i++)
        	{
        		if(cache.indexOf(ngrams.get(i))>=0)
        			document.add(cache.indexOf(ngrams.get(i)));
        	}
    	}

    	//Now I get, why FastText adds a newline to all the texts, to force that all the documents will have at least one feature
    	document.add(cache.indexOf("\n"));

    	if(args[1] != null)
    	{
	    	@SuppressWarnings("unchecked")
			List<Integer> featuresWeights = (List<Integer>) args[1];
	    	for(int i=0; i<featuresWeights.size(); i++)
	    	{
	    		for(int j=0; j<featuresWeights.get(i); j++)
	    			document.add(featuresStartAt+i);
	    	}
    	}

    	return Nd4j.create(document.stream().mapToDouble(d->d).toArray(),new int[]{document.size(),1});
	}

	@Override
	public void initialize(Configuration conf) {

		tokenizerFactory = new DefaultTokenizerFactory();
        minWordFrequency = conf.getInt(MIN_WORD_FREQUENCY, 5);
        maxNgrams = conf.getInt(NGRAMS, 1);
        maxSkipBigrams = conf.getInt(SKIP_NGRAMS, 0);

        if(conf.getBoolean(DELETE_STOP_WORDS, false))
        {
        	System.err.println("StopWord Removal: Yes");
	        stopWords = conf.getStringCollection(STOP_WORDS);
	        if (stopWords == null || stopWords.isEmpty())
	            stopWords = StopWords.getStopWords();
        }
        else
        	System.err.println("StopWord Removal: No");
        System.err.println("Freq min:"+minWordFrequency);
        
        System.err.println("N-grams:"+maxNgrams);
        
        System.err.println("Skip bigrams:"+maxSkipBigrams);
        
		cache = new VasttextDictionary();
        cache.initialize(conf);
		cache.setMaxNgrams(maxNgrams);
		cache.setMaxSkipBigrams(maxSkipBigrams);
	}

	@Override
	public void fit(RecordReader reader) {
		fit(reader, null);

	}

	@Override
	public void fit(RecordReader reader, RecordCallBack callBack) {
        while (reader.hasNext()) {
            Record record = reader.nextRecord();
            String s = record.getRecord().get(0).toString();
            Tokenizer tokenizer = tokenizerFactory.create(s);
            cache.incrementNumDocs(1);
            List<String> tokens = new ArrayList<String>(); //These tokens might be different from those of the tokenizer if used with stopwords
            if(stopWords==null)
            	tokens=doWithTokens(tokenizer);
            else
            	tokens=doWithTokensStopWords(tokenizer);
            if(maxNgrams>1)
            	doWithNgram(ngramsGenerator(tokens));
            if (callBack != null)
                callBack.onRecord(record);
        }

	}

	@Override
	public INDArray fitTransform(RecordReader reader) {
		return fitTransform(reader, null);
	}

	@Override
	public INDArray fitTransform(RecordReader reader, RecordCallBack callBack) {
		final List<Record> records = new ArrayList<>();
        fit(reader, new RecordCallBack() {
            @Override
            public void onRecord(Record record) {
                records.add(record);
            }
        });



        if (records.isEmpty())
            throw new IllegalStateException("No records found!");

       //The features will have an index out of the vocabulary index one
       featuresStartAt=cache.vocabWords().size();

        for (Record record : records)
        {

        	INDArray transformed = transform(record);
            org.datavec.api.records.impl.Record transformedRecord = new org.datavec.api.records.impl.Record(
            				Arrays.asList(new NDArrayWritable(transformed), record.getRecord().get(record.getRecord().size() - 1)),
                            new RecordMetaDataURI(record.getMetaData().getURI(), reader.getClass()));
            if (callBack != null) {
                callBack.onRecord(transformedRecord);
            }
        }
        return null;
	}

	@Override
	public INDArray transform(Record record) {
		List<String> tokens = tokensFromRecord(record.getRecord().get(0));
		List<Integer> featuresWeights = null;
		if(record.getRecord().size()>2)
		{
			featuresWeights = featuresWeightsFromRecord(record.getRecord().get(1));
		}
    	return createVector(new Object[] {tokens, featuresWeights});
	}

	private List<Integer> featuresWeightsFromRecord(Writable writable)
	{
		String weightsAsText=writable.toString();
		Tokenizer tokenizer = tokenizerFactory.create(weightsAsText);
		List<Integer> features = new ArrayList<Integer>();
		while(tokenizer.hasMoreTokens())
			features.add(Integer.valueOf(tokenizer.nextToken()));
		return features;
	}

	protected List<String> tokensFromRecord(Writable writable)
    {

    	String text = writable.toString();
    	Tokenizer tokenizer = tokenizerFactory.create(text);
    	List<String> tokens = new ArrayList<String>();
    	while (tokenizer.hasMoreTokens())
            tokens.add(tokenizer.nextToken());
    	return tokens;
    }

	private List<String> doWithTokens(Tokenizer tokenizer)
	{
		List<String> tokens = new ArrayList<String>();
		String token;
        while (tokenizer.hasMoreTokens()) {
        	token = tokenizer.nextToken();
        	tokens.add(token);
            doWithNgram(token);
        }
        return tokens;
    }

	private List<String> doWithTokensStopWords(Tokenizer tokenizer)
	{
		List<String> tokens = new ArrayList<String>();
		String token;
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            if (!stopWords.contains(token))
            {
            	tokens.add(token);
                doWithNgram(token);
            }
        }
        return tokens;
    }

	private void doWithNgram(List<String> ngrams)
	{
		for(String ngram : ngrams)
			doWithNgram(ngram);
	}

	private void doWithNgram(String ngram)
	{
		cache.incrementCount(ngram);
	}

	public int getVocabularySize()
	{
		return cache.vocabWords().size();
	}

	public void loadDictionary(Path dictionary) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(new InflaterInputStream(new FileInputStream(dictionary.toFile())));
		loadDictionary(ois);
		ois.close();
	}
	
	public void loadDictionary(Object dictionary) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		cache = (VasttextDictionary) dictionary;
		tokenizerFactory = new DefaultTokenizerFactory();
		maxNgrams=cache.getMaxNgrams();
		maxSkipBigrams=cache.getMaxSkipBigrams();
		featuresStartAt=cache.vocabWords().size();
		setfitFinished();
	}



	public void saveDictionary(String dictionary) throws FileNotFoundException, IOException
    {
    	ObjectOutputStream oos = new ObjectOutputStream(new DeflaterOutputStream(new FileOutputStream(dictionary)));
		oos.writeObject(cache);
		oos.close();
    }

	public VasttextDictionary getDictionary()
	{
		return cache;
	}


}
