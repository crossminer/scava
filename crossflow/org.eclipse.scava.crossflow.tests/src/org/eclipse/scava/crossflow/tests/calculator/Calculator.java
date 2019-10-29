package org.eclipse.scava.crossflow.tests.calculator;


public class Calculator extends CalculatorBase {
	
	protected int executions = 0;
	protected int delay = 0;
	
	@Override
	public CalculationResult consumeCalculations(Calculation calculation) {
		
		try { Thread.sleep(delay); } catch (InterruptedException e) {}
		
		String result = "";
		
		switch (calculation.getOperator()) {
			case "+": result = calculation.getA() + calculation.getB() + ""; break;
			case "-": result = calculation.getA() - calculation.getB() + ""; break;
			case "*": result = calculation.getA() * calculation.getB() + ""; break;
			case "^": result = Math.pow(calculation.getA(), calculation.getB()) + ""; break;
			default: result = "unknown";
		}
		
		CalculationResult calculationResult = new CalculationResult();
		calculationResult.setCorrelationId(calculation.getJobId());
		calculationResult.setA(calculation.getA());
		calculationResult.setB(calculation.getB());
		calculationResult.setOperator(calculation.getOperator());
		calculationResult.setWorker(workflow.getName());
		calculationResult.setResult(result);
		return calculationResult;
		
	}
	
	public int getExecutions() {
		return executions;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
}