/*
 * Created on Sep 2, 2004
 *
 */
package es.upc.fib.ia.aima.search.framework;

/**
 * @author Ravi Mohan
 *  
 */
public class DefaultStepCostFunction implements StepCostFunction {

	public Double calculateStepCost(Object fromState, Object toState,
			String action) {

		return new Double(1);
	}

}