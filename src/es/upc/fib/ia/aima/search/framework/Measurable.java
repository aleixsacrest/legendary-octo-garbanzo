/*
 * Created on Sep 8, 2004
 *
 */
package es.upc.fib.ia.aima.search.framework;

/**
 * @author Ravi Mohan
 *  
 */
public interface Measurable {
	void setSearchMetric(String name, Object value);

	Object getSearchMetric(String name);
}