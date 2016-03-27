package es.upc.fib.ia;

import es.upc.fib.ia.aima.search.framework.HeuristicFunction;


/**
 * Created by alexmiro on 15/3/16.
 *
 *                          Que s'ha de mesurar?
 *                          --------------------
 *  a. Minimitzar el temps de transmissio dels fitxers del servidor que triga mes.
 *  b. Compromis entre factor de carrega i temps total de transmissions.
 */

public class DistFSHeuristicFunction implements HeuristicFunction {


    //1. Minimitzem clausula b, pero aixo donaria moltes messetes (dolent pel HC) --> funcio de variancia i sumatori temps.
    //2. Minimitzem clausula a -> desempat de les messetes --> diferencia entre avg i temps del pitjor.
    public double getHeuristicValue(Object s) { //TODO: he canviat de int a double
        Estat state = (Estat) s;
        double FdC0 = state.getFdC0();              //FdC inicial, es el que considerem pitjor, xq a partir d'aqui millorem
        double temps0 = state.getTemps0();          //temps total inicial, es el que considerem pitjor, pel mateix que FdC
        double FdC = state.factorDeCarrega();
        double temps = state.tempsTransmissio();

        //heuristic value FdC:
        //quan més proper sigui al inicial (pitjor), menys valor tindrà l'heurístic
        //quan més proper sigui a 0, més valor tindrà l'heurístic
        //el maxim es quan la variancia es 0 --> hv_fdc = FdC0
        double hv_fdc = FdC0 - FdC;

        //aleix
        double hv_fdc_aleix = (FdC0 - FdC) / FdC0;
        //if (hv_fdc_aleix < 0) hv_fdc_aleix = 0;

        //heuristic value temps:
        //mateix raonament que pel FdC
        double hv_temps = temps0 - temps;

        //aleix
        double hv_temps_aleix = (temps0 - temps) / temps0;
        //if (hv_temps_aleix < 0) hv_temps_aleix = 0;

        //H es la funcio heuristica
        int H = (int) (hv_fdc + hv_temps);

        //aleix
        double H_aleix = (hv_fdc_aleix / 2) + (hv_temps_aleix / 2);

        double avg = state.getAvg();
        //heuristic value pitjorServidor
        //volem minimitzar el temps del pitjor servidor, per tant, quan més proper sigui al avg del temps dels altres millor,
        //voldra dir que estem satisfent la clausula a. Si es major al avg, restarà punts al valor de H, si es igual
        //no en sumara, i si es menor fara que sigui major
        double hv_pitjorServidor = avg - state.getTempsPitjorSevidor();

        //aleix
        double tot = state.getSumaTemps();
        double hv_pitjorServidor_aleix = (tot - state.getTempsPitjorSevidor()) / tot;

        H += (int) hv_pitjorServidor; //si el temps del pitjor servidor es major a l'avg, donem menys puntuacio

        //aleix
        H_aleix = (H_aleix) + (hv_pitjorServidor_aleix);

        return H;
    }

}
