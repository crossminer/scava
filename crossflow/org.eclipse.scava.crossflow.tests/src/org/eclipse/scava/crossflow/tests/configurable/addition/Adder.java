package org.eclipse.scava.crossflow.tests.configurable.addition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Adder extends AdderBase {

	private String operation;
	private String post;

	private int executions = 0;

	@Override
	public Number consumeAdditions(NumberPair numberPair) throws Exception {

		System.out.print("Expression: " + numberPair.a + operation + numberPair.b);

		Number numberInst = new Number();

		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		String ret = engine.eval(numberPair.a + operation + numberPair.b).toString();
		System.out.println(", Script output: " + ret + " " + workflow.getName());

		numberInst.setN(Integer.parseInt(ret));

		executions++;
		return numberInst;

	}

	@Override
	public void consumeOperationConfigTopic(Operation operationConfiguration) throws Exception {

		System.out.println("config received ("+workflow.getName()+"): " + operationConfiguration);

		if (operation == null)
			operation = operationConfiguration.operation;

	}

	@Override
	public void consumePostConfigTopic(Post postConfiguration) throws Exception {

		System.out.println("config received ("+workflow.getName()+"): " + postConfiguration);

	}

	public int getExecutions() {
		return executions;
	}

}
