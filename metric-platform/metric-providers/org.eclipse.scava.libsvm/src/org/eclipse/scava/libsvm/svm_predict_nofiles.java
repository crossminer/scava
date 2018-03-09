/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.libsvm;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_print_interface;

import java.io.*;
import java.util.*;

public class svm_predict_nofiles {
	
	private static int predict_probability;
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

		if(predict_probability == 1)
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
			if (predict_probability==1 && (svm_type==svm_parameter.C_SVC || svm_type==svm_parameter.NU_SVC))
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

	public static svm_model parse_args_and_load_model(String argv[]) {
		int i;
		predict_probability=0;
		svm_print_string = svm_print_stdout;

		// parse options
		for(i=0;i<argv.length;i++)
		{
			if(argv[i].charAt(0) != '-') break;
			++i;
			switch(argv[i-1].charAt(1))
			{
			case 'b':
				predict_probability = atoi(argv[i]);
				break;
			case 'q':
				svm_print_string = svm_print_null;
				i--;
				break;
			default:
				System.err.print("Unknown option: " + argv[i-1] + "\n");
				exit_with_help();
			}
		}
		if(i>=argv.length)
			exit_with_help();
		model_filename = argv[i];
		
		svm_model model = null;
		try {
			model = svm.svm_load_model(model_filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (model == null)
		{
			System.err.print("can't open model file "+model_filename+"\n");
			System.exit(1);
		}
		if(predict_probability == 1)
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
	
	public static void main(String argv[]) throws IOException
	{
		// Load testing file externally:
		String input_filename = "libsvmFiles/sets/a1a.t";
		List<Double> target_list = new ArrayList<Double>();
		List<svm_node[]> svm_node_list = new ArrayList<svm_node[]>();
		BufferedReader input = new BufferedReader(new FileReader(input_filename ));
		while(true)
		{
			String line = input.readLine();
			if(line == null) break;

			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

			double target = atof(st.nextToken());
			target_list.add(target);
			
			int m = st.countTokens()/2;
			svm_node[] x = new svm_node[m];
			for(int j=0;j<m;j++)
			{
				x[j] = new svm_node();
				x[j].index = atoi(st.nextToken());
				x[j].value = atof(st.nextToken());
			}
			svm_node_list.add(x);
		}
		input.close();
		
		// Load model and predict: 
		svm_model model = parse_args_and_load_model(argv);
		List<List<Double>> output_list = predict(model, target_list, svm_node_list);
		
		// Print output
		for (List<Double> output_line: output_list) {
			for (double element: output_line)
				System.out.print(element + "\t");
			System.out.println();
		}
	}
	
}
