package vasttext.recordreader.file;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.datavec.api.conf.Configuration;
import org.datavec.api.records.Record;
import org.datavec.api.records.metadata.RecordMetaData;
import org.datavec.api.records.metadata.RecordMetaDataLine;
import org.datavec.api.records.reader.impl.LineRecordReader;
import org.datavec.api.split.InputSplit;
import org.datavec.api.writable.NDArrayWritable;
import org.datavec.api.writable.Writable;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * This reader is charged of reading the extra features for VastText
 * @author Adrián Cabrera
 *
 */
public class VasttextExtraFileReader extends LineRecordReader {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3509144068309233344L;
	
	private static String NAME_SPACE = VasttextExtraFileReader.class.getName();
	public final static String FEATURES = NAME_SPACE + ".features";
	
    private int numericFeaturesSize;
    
    private List<Integer> linesToAvoid;
    private int lineCounter;
    
    public VasttextExtraFileReader(List<Integer> linesToAvoid)
    {
		this.linesToAvoid=linesToAvoid;
		lineCounter=0;
	}
    
    @Override
    public void reset()
    {
    	lineCounter=0;
    	super.reset();
    }
    
    @Override
    public void initialize(Configuration conf, InputSplit split) throws IOException, InterruptedException {
        super.initialize(conf, split);
        numericFeaturesSize=conf.getInt(FEATURES, -1);
        checkNumericFeatures();
    }
    
    private void checkNumericFeatures()
    {
    	if(numericFeaturesSize==0)
    		throw new UnsupportedOperationException("Number of features has been defined as 0, therefore, do not use this reader.");
    	else if(numericFeaturesSize<0)
    		throw new UnsupportedOperationException("Please intialize before checking the number of features.");
    }
    
    public Record processNextRecord() {
        //We need to split and find the label(s)
        String[] line = super.next().get(0).toString().split(" ");
        double[] extraFeatures = new double[numericFeaturesSize];
        if(line.length != numericFeaturesSize)
        	 throw new UnsupportedOperationException("Features defined and features found do not match. Found: "+ line.length + " Declared:" +numericFeaturesSize);
        for(int i=0; i<numericFeaturesSize; i++)
        {
        	extraFeatures[i]=Double.valueOf(line[i]);
        }
        INDArray transformed = Nd4j.create(extraFeatures,new int[]{extraFeatures.length,1});
        
        URI uri = (locations == null || locations.length < 1 ? null : locations[splitIndex]);
        RecordMetaData meta = new RecordMetaDataLine(this.lineIndex - 1, uri, LineRecordReader.class); //-1 as line number has been incremented already...
        return new org.datavec.api.records.impl.Record(new ArrayList<>(Collections.<Writable>singletonList(new NDArrayWritable(transformed)))
        		, meta);
    }
    
    @Override
    public List<String> getLabels() {
        return null;
    }
    
    @Override
    public List<Writable> next() {
    		return nextRecord().getRecord();
    }

    @Override
    public Record nextRecord() {
            return processNextRecord();
    }

    @Override
    public boolean hasNext() {
    	if(linesToAvoid.contains(lineCounter))
    	{
    		System.err.println("WARNING: The entry "+ (lineCounter+1) + " has been skipped from data set due to a label problem.");
    		if(super.hasNext())
    			next();
    		else
    			return false;
    		lineCounter++;
    	}
    	lineCounter++;
        return super.hasNext();
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public Configuration getConf() {
        return conf;
    }

    public int getNumericFeaturesSize() {
        return numericFeaturesSize;
    }

    @Override
    public Record loadFromMetaData(RecordMetaData recordMetaData) throws IOException {
        return loadFromMetaData(Collections.singletonList(recordMetaData)).get(0);
    }

    @Override
    public List<Record> loadFromMetaData(List<RecordMetaData> recordMetaDatas) throws IOException {
        return super.loadFromMetaData(recordMetaDatas);
    }
}

