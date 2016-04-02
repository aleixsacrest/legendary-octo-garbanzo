package es.upc.fib.ia;

import es.upc.fib.ia.aima.search.framework.HeuristicFunction;

/**
 * Created by Msacrest on 01/04/2016.
 */
public class DistFSHeuristicFunction1 implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object s) {
        Estat state = (Estat) s;
        double temps = state.getTempsPitjorServidor();
        /*double t_pitjor0 = state.getTPijtor0();
        double p = temps / t_pitjor0;
        double log = Math.log(p);
        //return -p * log;*/
        return Math.pow(temps,2);
    }
}
