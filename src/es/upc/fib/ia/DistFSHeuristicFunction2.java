package es.upc.fib.ia;

import es.upc.fib.ia.aima.search.framework.HeuristicFunction;


/**
 * Created by alexmiro on 15/3/16.
 *
 *                          Que s'ha de mesurar?
 *                          --------------------
 *   Compromis entre factor de carrega i temps total de transmissions.
 */

public class DistFSHeuristicFunction2 implements HeuristicFunction {

    public double getHeuristicValue(Object s) { //TODO: he canviat de int a double
        Estat state = (Estat) s;
        double FdC0 = state.getFdC0();              //FdC inicial, es el que considerem pitjor, xq a partir d'aqui millorem
        double temps0 = state.getTemps0();          //temps total inicial, es el que considerem pitjor, pel mateix que FdC
        double FdC = state.factorDeCarrega();
        double temps = state.tempsTransmissio();

        //heuristic value FdC:
        double hv_fdc = FdC / FdC0;


        //heuristic value temps:
        double  hv_temps = temps / temps0;

        return hv_temps + hv_fdc;
    }

}