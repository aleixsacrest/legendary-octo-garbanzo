package es.upc.fib.ia;

import es.upc.fib.ia.aima.search.framework.HeuristicFunction;

/**
 * Created by Msacrest on 01/04/2016.
 */
public class DistFSHeuristicFunction1 implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object s) {
        Estat state = (Estat) s;
        double FdC0 = state.getFdC0();              //FdC inicial, es el que considerem pitjor, xq a partir d'aqui millorem
        double temps0 = state.getTemps0();          //temps total inicial, es el que considerem pitjor, pel mateix que FdC
        double FdC = state.factorDeCarrega();
        double temps = state.tempsTransmissio();

        double avg = state.getAvg();
        //heuristic value pitjorServidor
        //volem minimitzar el temps del pitjor servidor, per tant, quan més proper sigui al avg del temps dels altres millor,
        //voldra dir que estem satisfent la clausula a. Si es major al avg, restarà punts al valor de H, si es igual
        //no en sumara, i si es menor fara que sigui major

        double hv_pitjorServidor = avg - state.getTempsPitjorServidor();



        double H = (int) hv_pitjorServidor; //si el temps del pitjor servidor es major a l'avg, donem menys puntuacio



        return H;
    }
}
