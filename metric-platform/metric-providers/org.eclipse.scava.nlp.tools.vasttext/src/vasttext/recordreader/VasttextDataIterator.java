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

package vasttext.recordreader;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import org.datavec.api.records.Record;
import org.datavec.api.records.metadata.RecordMetaData;
import org.datavec.api.records.metadata.RecordMetaDataComposableMap;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.writable.NDArrayWritable;
import org.datavec.api.writable.Writable;
import org.deeplearning4j.datasets.datavec.exception.ZeroLengthSequenceException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.MultiDataSet;
import org.nd4j.linalg.dataset.api.MultiDataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.primitives.Pair;


/**
 * RecordReaderMultiDataSetIterator: A {@link MultiDataSetIterator} for data
 * from one or more RecordReaders and SequenceRecordReaders<br>
 * The idea: generate multiple inputs and multiple outputs from one or more
 * Sequence/RecordReaders. Inputs and outputs may be obtained from subsets of
 * the RecordReader and SequenceRecordReaders columns (for examples, some inputs
 * and outputs as different columns in the same record/sequence); it is also
 * possible to mix different types of data (for example, using both
 * RecordReaders and SequenceRecordReaders in the same
 * RecordReaderMultiDataSetIterator).<br>
 * Uses a builder pattern ({@link VasttextDataIterator.Builder} to
 * specify the various inputs and subsets.
 *
 * @author Adri�n Cabrera
 * @author Alex Black
 */
public class VasttextDataIterator implements MultiDataSetIterator, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2525752599611862914L;

	private int batchSize;
	private Map<String, RecordReader> recordReaders = new HashMap<>();

	private List<SubsetDetails> inputs = new ArrayList<>();
	private List<SubsetDetails> outputs = new ArrayList<>();

	private boolean collectMetaData = false;

	private MultiDataSetPreProcessor preProcessor;

	private boolean resetSupported = true;

	private VasttextDataIterator(Builder builder) {
		this.batchSize = builder.batchSize;
		this.recordReaders = builder.recordReaders;
		this.inputs.addAll(builder.inputs);
		this.outputs.addAll(builder.outputs);

		if (recordReaders != null) {
			for (RecordReader rr : recordReaders.values()) {
				resetSupported &= rr.resetSupported();
			}
		}
	}

	@Override
	public MultiDataSet next() {
		return next(batchSize);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove not supported");
	}

	@Override
	public MultiDataSet next(int num) {
		if (!hasNext())
			throw new NoSuchElementException("No next elements");

		// First: load the next values from the RR / SeqRRs
		Map<String, List<List<Writable>>> nextRRVals = new HashMap<>();
		List<RecordMetaDataComposableMap> nextMetas = (collectMetaData ? new ArrayList<RecordMetaDataComposableMap>()
				: null);

		for (Map.Entry<String, RecordReader> entry : recordReaders.entrySet()) {
			RecordReader rr = entry.getValue();
			// Standard case
				List<List<Writable>> writables = new ArrayList<>(Math.min(num, 100000)); // Min op: in case user puts
																							// batch size >> amount of
																							// data
				for (int i = 0; i < num && rr.hasNext(); i++) 
				{
					List<Writable> record;
					if (collectMetaData) {
						Record r = rr.nextRecord();
						record = r.getRecord();
						if (nextMetas.size() <= i) {
							nextMetas.add(new RecordMetaDataComposableMap(new HashMap<String, RecordMetaData>()));
						}
						RecordMetaDataComposableMap map = nextMetas.get(i);
						map.getMeta().put(entry.getKey(), r.getMetaData());
					} else {
						record = rr.next();
					}
					writables.add(record);
				}

				nextRRVals.put(entry.getKey(), writables);
		}

		return nextMultiDataSet(nextRRVals, nextMetas);
	}

	public MultiDataSet nextMultiDataSet(Map<String, List<List<Writable>>> nextRRVals,
			List<RecordMetaDataComposableMap> nextMetas) {
		int minExamples = Integer.MAX_VALUE;
		for (List<List<Writable>> exampleData : nextRRVals.values()) {
			minExamples = Math.min(minExamples, exampleData.size());
		}

		// Support for VastText
		int[] longestRecord = new int[inputs.size()];
		int recordLength;

		//This allows to aling text vectors of different lenghts in the batch
		int recordPos = inputs.get(0).subsetStart;
		int inputPos = 0;
		List<List<Writable>> list;
		int[][] inputsLength = new int[inputs.size()][minExamples];
		for (Entry<String, List<List<Writable>>> entry : nextRRVals.entrySet()) {

			list = entry.getValue();
			for (int i = 0; i < list.size() && i < minExamples; i++) {
				// Analysis of Records
				recordLength = (int) ((NDArrayWritable) list.get(i).get(recordPos)).length();
				inputsLength[inputPos][i] = Math.max(inputsLength[inputPos][i], recordLength);
				longestRecord[inputPos] = Math.max(longestRecord[inputPos], recordLength);
			}
			inputPos++;
		}

		if (minExamples == Integer.MAX_VALUE)
			throw new RuntimeException("Error occurred during data set generation: no readers?"); // Should never happen

		Pair<INDArray[], INDArray[]> features = convertFeatures(new INDArray[inputs.size()],
				new INDArray[inputs.size()], inputs, minExamples, nextRRVals, longestRecord, inputsLength);

		Pair<INDArray[], INDArray[]> labels = convertLabels(new INDArray[outputs.size()],
				new INDArray[outputs.size()], outputs, minExamples, nextRRVals);

		MultiDataSet mds = new org.nd4j.linalg.dataset.MultiDataSet(features.getFirst(), labels.getFirst(),
				features.getSecond(), labels.getSecond());
		if (collectMetaData) {
			mds.setExampleMetaData(nextMetas);
		}
		if (preProcessor != null)
			preProcessor.preProcess(mds);
		return mds;
	}

	private Pair<INDArray[], INDArray[]> convertLabels(INDArray[] Labels, INDArray[] masks,
			List<SubsetDetails> subsetDetails, int minExamples, Map<String, List<List<Writable>>> nextRRVals) {
		boolean hasMasks = false;
		int i = 0;

		for (SubsetDetails d : subsetDetails) {
			if (nextRRVals.containsKey(d.readerName)) {
				List<List<Writable>> list = nextRRVals.get(d.readerName);
				Labels[i] = convertLabels(list, minExamples, d);
			}
			i++;
		}
		return new Pair<>(Labels, hasMasks ? masks : null);
	}

	private Pair<INDArray[], INDArray[]> convertFeatures(INDArray[] features, INDArray[] masks,
			List<SubsetDetails> subsetDetails, int minExamples, Map<String, List<List<Writable>>> nextRRVals,
			int longestFeaturesInput[], int[][] lengthFeaturesInputs) {
		boolean hasMasks = false;
		int i = 0;

		for (SubsetDetails d : subsetDetails) {
			if (nextRRVals.containsKey(d.readerName)) {
				List<List<Writable>> list = nextRRVals.get(d.readerName);
					Pair<INDArray, INDArray> p = paddingFeatures(list, minExamples, longestFeaturesInput[i], d, lengthFeaturesInputs[i]);
					features[i] = p.getFirst();
					masks[i] = p.getSecond();
					if (masks[i] != null)
						hasMasks = true;
			}
			i++;
		}

		return new Pair<>(features, hasMasks ? masks : null);
	}

	private INDArray convertLabels(List<List<Writable>> list, int minValues, SubsetDetails details) {
		try {
			INDArray arr = Nd4j.zeros(minValues, details.oneHotNumClasses);
			for (int i = 0; i < minValues; i++)
			{
				List<Writable> c = list.get(i);
				if (details.oneHot) 
				{
					// Convert a single column to a one-hot representation
					Writable w = c.get(details.subsetStart);
					// Multilabel
					if (w instanceof NDArrayWritable)
					{
						int classIdx = -1;
						INDArray toPut = ((NDArrayWritable) w).get();
						for (int j = 0; j < toPut.length(); j++)
						{
							classIdx = toPut.getInt(j);
							if (classIdx >= details.oneHotNumClasses) {
								throw new IllegalStateException("Cannot convert sequence writables to one-hot: class index "
										+ classIdx + " >= numClass (" + details.oneHotNumClasses
										+ "). (Note that classes are zero-"
										+ "indexed, thus only values 0 to nClasses-1 are valid)");
							}
							else if (classIdx != -1)
								arr.putScalar(i, classIdx, 1.0);
						}

					}
					else
					{
						//  Multiclass
						// Index of class
						int classIdx = w.toInt();
						if (classIdx >= details.oneHotNumClasses)
						{
							throw new IllegalStateException("Cannot convert sequence writables to one-hot: class index "
									+ classIdx + " >= numClass (" + details.oneHotNumClasses
									+ "). (Note that classes are zero-"
									+ "indexed, thus only values 0 to nClasses-1 are valid)");
						}
						arr.putScalar(i, w.toInt(), 1.0);
					}
				}
			}

			return arr;
		} catch (NumberFormatException e) {
			throw new RuntimeException("Error parsing data (writables) from record readers - value is non-numeric", e);
		} catch (IllegalStateException e) {
			throw e;
		} catch (Throwable t) {
			throw new RuntimeException("Error parsing data (writables) from record readers", t);
		}
	}

	private Pair<INDArray, INDArray> paddingFeatures(List<List<Writable>> list, int minExamples,
			int longestVector, SubsetDetails details, int[] vectorLength) {
		if (list.isEmpty()) {
			throw new ZeroLengthSequenceException("Zero length sequence encountered");
		}

		// We do not take into account the label in this vector, just the features
		INDArray arr = Nd4j.create(new int[] { list.size(), longestVector }, 'f');

		INDArray maskArray = Nd4j.zeros(list.size(), longestVector);

		for (int i = 0; i < list.size(); i++)
		{
			List<Writable> record = list.get(i);
			Writable w = null;
			if (details.subsetStart < 0)
			{
				w = record.get(0);
			}
			else
			{
				w = record.get(details.subsetStart);
			}

			if (w instanceof NDArrayWritable)
			{
				INDArray value = ((NDArrayWritable) w).get();
				arr.put(new INDArrayIndex[] { NDArrayIndex.point(i), NDArrayIndex.interval(0, value.length()) }, value);
			} 
			else
			{
				arr.putScalar((long) 0, (long) 0, w.toDouble());
			}

			// Masking array entries at start (for align end)
			for (int t2 = 0; t2 < vectorLength[i]; t2++) {
				maskArray.putScalar(i, t2, 1.0);
			}
		}

		return new Pair<>(arr, maskArray);
	}

	@Override
	public void setPreProcessor(MultiDataSetPreProcessor preProcessor) {
		this.preProcessor = preProcessor;
	}

	@Override
	public MultiDataSetPreProcessor getPreProcessor() {
		return preProcessor;
	}

	@Override
	public boolean resetSupported() {
		return resetSupported;
	}

	@Override
	public boolean asyncSupported() {
		return true;
	}

	@Override
	public void reset() {
		if (!resetSupported) {
			throw new IllegalStateException("Cannot reset iterator - reset not supported (resetSupported() == false):"
					+ " one or more underlying (sequence) record readers do not support resetting");
		}

		for (RecordReader rr : recordReaders.values())
			rr.reset();
	}

	@Override
	public boolean hasNext() {
		for (RecordReader rr : recordReaders.values())
			if (!rr.hasNext())
				return false;
		return true;
	}

	public static class Builder {

		private int batchSize;
		private Map<String, RecordReader> recordReaders = new HashMap<>();

		private List<SubsetDetails> inputs = new ArrayList<>();
		private List<SubsetDetails> outputs = new ArrayList<>();

		/**
		 * @param batchSize
		 *            The batch size for the RecordReaderMultiDataSetIterator
		 */
		public Builder(int batchSize) {
			this.batchSize = batchSize;
		}

		/**
		 * Add a RecordReader for use in .addInput(...) or .addOutput(...)
		 *
		 * @param readerName
		 *            Name of the reader (for later reference)
		 * @param recordReader
		 *            RecordReader
		 */
		public Builder addReader(String readerName, RecordReader recordReader) {
			recordReaders.put(readerName, recordReader);
			return this;
		}

		/**
		 * Set as an input, a subset of the specified RecordReader or
		 * SequenceRecordReader
		 *
		 * @param readerName
		 *            Name of the reader
		 */
		public Builder addInput(String readerName) {
			inputs.add(new SubsetDetails(readerName, false, -1, 0));
			return this;
		}


		/**
		 * An an output, where the output is taken from a single column from the
		 * specified RecordReader / SequenceRecordReader The assumption is that the
		 * specified column contains integer values in range 0..numClasses-1; this
		 * integer will be converted to a one-hot representation (usually for
		 * classification)
		 *
		 * @param readerName
		 *            Name of the RecordReader / SequenceRecordReader
		 * @param numClasses
		 *            Number of classes
		 */
		public Builder addOutput(String readerName, int numClasses) {
			outputs.add(new SubsetDetails(readerName, true, numClasses, 1));
			return this;
		}

		/**
		 * Create the RecordReaderMultiDataSetIterator
		 */
		public VasttextDataIterator build() {
			// Validate input:
			if (recordReaders.isEmpty()) {
				throw new IllegalStateException("Cannot construct RecordReaderMultiDataSetIterator with no readers");
			}

			if (batchSize <= 0)
				throw new IllegalStateException(
						"Cannot construct RecordReaderMultiDataSetIterator with batch size <= 0");

			if (inputs.isEmpty() && outputs.isEmpty()) {
				throw new IllegalStateException(
						"Cannot construct RecordReaderMultiDataSetIterator with no inputs/outputs");
			}

			for (SubsetDetails ssd : inputs) {
				if (!recordReaders.containsKey(ssd.readerName)) {
					throw new IllegalStateException(
							"Invalid input name: \"" + ssd.readerName + "\" - no reader found with this name");
				}
			}

			for (SubsetDetails ssd : outputs) {
				if (!recordReaders.containsKey(ssd.readerName)) {
					throw new IllegalStateException(
							"Invalid output name: \"" + ssd.readerName + "\" - no reader found with this name");
				}
			}

			return new VasttextDataIterator(this);
		}
	}

	/**
	 * Load a single example to a DataSet, using the provided RecordMetaData. Note
	 * that it is more efficient to load multiple instances at once, using
	 * {@link #loadFromMetaData(List)}
	 *
	 * @param recordMetaData
	 *            RecordMetaData to load from. Should have been produced by the
	 *            given record reader
	 * @return DataSet with the specified example
	 * @throws IOException
	 *             If an error occurs during loading of the data
	 */
	public MultiDataSet loadFromMetaData(RecordMetaData recordMetaData) throws IOException {
		return loadFromMetaData(Collections.singletonList(recordMetaData));
	}

	/**
	 * Load a multiple sequence examples to a DataSet, using the provided
	 * RecordMetaData instances.
	 *
	 * @param list
	 *            List of RecordMetaData instances to load from. Should have been
	 *            produced by the record reader provided to the
	 *            SequenceRecordReaderDataSetIterator constructor
	 * @return DataSet with the specified examples
	 * @throws IOException
	 *             If an error occurs during loading of the data
	 */
	public MultiDataSet loadFromMetaData(List<RecordMetaData> list) throws IOException {
		// First: load the next values from the RR / SeqRRs
		Map<String, List<List<Writable>>> nextRRVals = new HashMap<>();
		List<RecordMetaDataComposableMap> nextMetas = (collectMetaData ? new ArrayList<RecordMetaDataComposableMap>()
				: null);

		for (Map.Entry<String, RecordReader> entry : recordReaders.entrySet()) {
			RecordReader rr = entry.getValue();

			List<RecordMetaData> thisRRMeta = new ArrayList<>();
			for (RecordMetaData m : list) {
				RecordMetaDataComposableMap m2 = (RecordMetaDataComposableMap) m;
				thisRRMeta.add(m2.getMeta().get(entry.getKey()));
			}

			List<Record> fromMeta = rr.loadFromMetaData(thisRRMeta);
			List<List<Writable>> writables = new ArrayList<>(list.size());
			for (Record r : fromMeta) {
				writables.add(r.getRecord());
			}

			nextRRVals.put(entry.getKey(), writables);
		}

		return nextMultiDataSet(nextRRVals, nextMetas);

	}

	public void setCollectMetaData(boolean collectMetaData) {
		this.collectMetaData = collectMetaData;

	}

	private static class SubsetDetails implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6468754606804622993L;
		
		public SubsetDetails(String readerName, boolean oneHot, int oneHotNumClasses,
				int subsetStart)
		{
			this.readerName = readerName;
			this.oneHot = oneHot;
			this.oneHotNumClasses = oneHotNumClasses;
			this.subsetStart = subsetStart;
		}

		private final String readerName;
		private final boolean oneHot;
		private final int oneHotNumClasses;
		private final int subsetStart;
	}

}
