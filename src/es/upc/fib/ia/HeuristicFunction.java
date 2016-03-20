package es.upc.fib.ia;

/**
 * Created by alexmiro on 15/3/16.
 *
 *                          Que s'ha de mesurar?
 *                          --------------------
 *  a. Minimitzar el temps de transmissio dels fitxers del servidor que triga mes.
 *  b. Compromis entre factor de carrega i temps total de transmissions.
 */

public class HeuristicFunction {


    //1. Minimitzem clausula b, pero aixo donaria moltes messetes (dolent pel HC) --> funcio de variancia i sumatori temps.
    //2. Minimitzem clausula a -> desempat de les messetes --> diferencia entre avg i temps del pitjor.
    public int getHeuristicValue(Estat state) {
        double FdC0 = state.getFdC0();
        double temps0 = state.getTemps0();
        double FdC = state.factorDeCarrega();
        double temps = state.tempsTransmissio();

        double hv_fdc = FdC0 - FdC;
        double hv_temps = temps0 - temps;
        int H = (int) (hv_fdc + hv_temps);

        double avg = state.getAvg();
        double hv_pitjorServidor = avg - state.getTempsPitjorSevidor();
        H += (int) hv_pitjorServidor; //si el temps del pitjor servidor es major a l'avg, donem menys puntuacio

        return H;
    }

}
