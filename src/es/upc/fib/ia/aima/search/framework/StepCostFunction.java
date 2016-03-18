package es.upc.fib.ia.aima.search.framework;

public interface StepCostFunction {
	Double calculateStepCost(Object fromState, Object toState, String action);
}