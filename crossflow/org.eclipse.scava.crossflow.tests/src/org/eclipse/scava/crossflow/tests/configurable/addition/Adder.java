package org.eclipse.scava.crossflow.tests.configurable.addition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Adder extends AdderBase {

	private String operation;
	private String post;

	@Override
	public Number consumeAdditions(NumberPair numberPair) throws Exception {

		System.out.println("Expression: " + numberPair.a + operation + numberPair.b);

		Number numberInst = new Number();

		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		String ret = engine.eval(numberPair.a + operation + numberPair.b).toString();
		System.out.println("Script output: " + ret);

		numberInst.setN(Integer.parseInt(ret));
		return numberInst;

	}

	@Override
	public void consumeOperationConfigurationConfigTopic(OperationConfiguration operationConfiguration) throws Exception {

		System.out.println("config received: " + operationConfiguration);

		// TODO: handle configuration
		operation = operationConfiguration.operation;

	}

	@Override
	public void consumePostConfigurationConfigTopic(PostConfiguration postConfiguration) throws Exception {

		// TODO: handle configuration

	}

}
