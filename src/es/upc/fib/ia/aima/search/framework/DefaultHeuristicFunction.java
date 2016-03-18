/*
 * Created on Sep 12, 2004
 *
 */
package es.upc.fib.ia.aima.search.framework;

/**
 * @author Ravi Mohan
 *  
 */
public class DefaultHeuristicFunction implements HeuristicFunction {

	public double getHeuristicValue(Object state) {
		return 1;
	}

}