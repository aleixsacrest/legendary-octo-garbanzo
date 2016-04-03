package es.upc.fib.ia;

import es.upc.fib.ia.aima.search.framework.HeuristicFunction;

/**
 * Created by aleixsacrest on 01/04/2016.
 *
 *                          Que s'ha de mesurar?
 *                          --------------------
 *   Minimitzar el temps de transmissio dels fitxers del servidor que triga mes.
 */
public class DistFSHeuristicFunction1 implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object s) {
        Estat state = (Estat) s;
        double temps = state.getTempsPitjorServidor();
        return temps;
    }
}
