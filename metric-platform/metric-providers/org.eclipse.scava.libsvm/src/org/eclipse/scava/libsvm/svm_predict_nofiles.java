package org.eclipse.scava.libsvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.scava.platform.logging.OssmeterLogger;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_print_interface;

public class svm_predict_nofiles {
	
	private static boolean predict_probability;
	private static String model_filename;

	private static svm_print_interface svm_print_null = new svm_print_interface()
	{
		public void print(String s) {}
	};

	private static svm_print_interface svm_print_stdout = new svm_print_interface()
	{
		public void print(String s)
		{
			System.out.print(s);
		}
	};

	private static svm_print_interface svm_print_string = svm_print_stdout;

	static void info(String s) 
	{
		svm_print_string.print(s);
	}

	public static double atof(String s)
	{
		return Double.valueOf(s).doubleValue();
	}

	public static int atoi(String s)
	{
		return Integer.parseInt(s);
	}

	public static List<List<Double>> predict(svm_model model,
								List<Double> target_list, List<svm_node[]> svm_node_list) throws IOException
	{
//		int correct = 0;
		int total = 0;
		double error = 0;
		double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;

		int svm_type=svm.svm_get_svm_type(model);
		int nr_class=svm.svm_get_nr_class(model);
		double[] prob_estimates=null;

		if(predict_probability)
		{
			if(svm_type == svm_parameter.EPSILON_SVR ||
			   svm_type == svm_parameter.NU_SVR)
			{
				svm_predict_nofiles.info("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="+svm.svm_get_svr_probability(model)+"\n");
			}
			else
			{
				prob_estimates = new double[nr_class];
//				int[] labels=new int[nr_class];
//				svm.svm_get_labels(model,labels);
//				output.writeBytes("labels");
//				for(int j=0;j<nr_class;j++)
//					output.writeBytes(" "+labels[j]);
//				output.writeBytes("\n");
			}
		}
		
		List<List<Double>> output = new ArrayList<List<Double>>(target_list.size());
		for (int i=0; i<target_list.size(); i++)
		{
			double target = target_list.get(i);
			svm_node[] node = svm_node_list.get(i);
			
			double v;
			List<Double> output_line;
			if (predict_probability && (svm_type==svm_parameter.C_SVC || svm_type==svm_parameter.NU_SVC))
			{
				v = svm.svm_predict_probability(model,node,prob_estimates);
				output_line = new ArrayList<Double>(nr_class+1);
				output_line.add(v);
				for(int j=0;j<nr_class;j++)
					output_line.add(prob_estimates[j]);
			}
			else
			{
				v = svm.svm_predict(model,node);
				output_line = new ArrayList<Double>(1);
				output_line.add(v);
			}
			output.add(output_line);

//			if(v == target)
//				++correct;
			error += (v-target)*(v-target);
			sumv += v;
			sumy += target;
			sumvv += v*v;
			sumyy += target*target;
			sumvy += v*target;
			++total;
		}
		if(svm_type == svm_parameter.EPSILON_SVR ||
		   svm_type == svm_parameter.NU_SVR)
		{
			svm_predict_nofiles.info("Mean squared error = "+error/total+" (regression)\n");
			svm_predict_nofiles.info("Squared correlation coefficient = "+
				 ((total*sumvy-sumv*sumy)*(total*sumvy-sumv*sumy))/
				 ((total*sumvv-sumv*sumv)*(total*sumyy-sumy*sumy))+
				 " (regression)\n");
		}
//		else
//			svm_predict_nofiles.info("Accuracy = "+(double)correct/total*100+
//				 "% ("+correct+"/"+total+") (classification)\n");
		return output;
	}

	private static void exit_with_help()
	{
		System.err.print("usage: svm_predict [options] model_file\n"
		+"options:\n"
		+"-b probability_estimates: whether to predict probability estimates, 0 or 1 (default 0); one-class SVM not supported yet\n"
		+"-q : quiet mode (no outputs)\n");
		System.exit(1);
	}

	public static svm_model parse_args_and_load_model(ClassLoader cl, String folderModel, String modelName, boolean predictProb) {
		OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("libsvm");
		int i;
		predict_probability=predictProb;
		svm_print_string = svm_print_stdout;

		model_filename = folderModel+"/"+modelName;
		
		svm_model model = null;
		try {
			URL resource = cl.getResource("/" + model_filename);
			if(resource==null)
			{
				unzipModel(cl.getResource("/" + model_filename +".zip"));
				resource = cl.getResource("/" + model_filename);
			}
			logger.info("Searching the model at " + FileLocator.toFileURL(resource).getPath());
			model = svm.svm_load_model(FileLocator.toFileURL(resource).getPath());
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (model == null)
		{
			logger.info("Model hasn't been found");
		}
		else
			logger.info("Model has been sucessfully loaded");
		if(predict_probability)
		{
			if(svm.svm_check_probability_model(model)==0)
			{
				System.err.print("Model does not support probabiliy estimates\n");
				System.exit(1);
			}
		}
		else
		{
			if(svm.svm_check_probability_model(model)!=0)
			{
				svm_predict_nofiles.info("Model supports probability estimates, but disabled in prediction.\n");
			}
		}
		return model;
	}
	
	private static void unzipModel(URL zipModelURL) throws IOException, URISyntaxException
	{
		File zipmodelFile = new File(FileLocator.toFileURL(zipModelURL).getPath());
		String unzipModel = zipmodelFile.toString();
		unzipModel=(String) unzipModel.subSequence(0, unzipModel.length()-4);
		FileInputStream fis = new FileInputStream(zipmodelFile);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry entry = zis.getNextEntry();
        
        if(entry != null)
        {
        	byte[] buffer = new byte[1024];
        	int len;
            File newFile = new File(unzipModel);
            System.out.println("Unzipping to "+newFile.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(newFile);
            while ((len = zis.read(buffer)) > 0)
            {
           		fos.write(buffer, 0, len);
            }
            fos.close();
        }
        zis.closeEntry();
        zis.close();
        fis.close();
	}
}