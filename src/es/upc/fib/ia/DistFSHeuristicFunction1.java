package es.upc.fib.ia;

import es.upc.fib.ia.aima.search.framework.HeuristicFunction;

/**
 * Created by aleixsacrest on 01/04/2016.
 */
public class DistFSHeuristicFunction1 implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object s) {
        Estat state = (Estat) s;
        double temps = state.getAvgPitjorServidor();
        //double temps = state.getTempsPitjorServidor();
        return temps;
    }
}
