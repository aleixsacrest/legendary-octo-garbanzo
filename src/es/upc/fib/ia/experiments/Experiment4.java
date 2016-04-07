package es.upc.fib.ia.experiments;

import IA.DistFS.*;
import es.upc.fib.ia.*;
import es.upc.fib.ia.aima.search.framework.*;
import es.upc.fib.ia.aima.search.informed.HillClimbingSearch;
import es.upc.fib.ia.aima.search.informed.SimulatedAnnealingSearch;

import javax.swing.*;
import java.awt.*;
import java.util.*;


/**
 * Created by alexmiro on 04/04/2016.
 */
public class Experiment4 extends Experiment {
    public static void main(String[] args) {
        try {
            boolean tendencia = false;
            for (int usuaris = 100; usuaris <= 100 * 10; usuaris += 100) {
                //estudi1
                long temps1 = 0;
                for (int i = 0; i < 10; ++i) {
                    int llavor = (int) (Math.random() * 100.);
                    Requests req = new Requests(usuaris, 5, llavor);
                    Servers serv = new Servers(50, 5, llavor);

                    Estat e1 = new Estat(serv, req);
                    e1.initMinTemps();

                    long startTime = System.currentTimeMillis();
                    DistFSHillClimbing(e1, 1, 1, false);
                    temps1 += (System.currentTimeMillis() - startTime);
                }
                System.out.println("TEMPS CERCA (" + usuaris + " usuaris): " + (temps1/10.));
                System.out.println("--------------------------------------------------------------------------------------------------------------------------");
            }
            tendencia = false;
            for (int servidors = 50; servidors <= 50 * 30 ; servidors += 50) {
                //estudi2
                long temps2 = 0;
                for (int i = 0; i < 1; ++i) {
                    int llavor = (int) (Math.random() * 100.);
                    Requests req = new Requests(200, 5, llavor);
                    Servers serv = new Servers(servidors, 5, llavor);

                    Estat e1 = new Estat(serv, req);
                    e1.initMinTemps();

                    long startTime = System.currentTimeMillis();
                    DistFSHillClimbing(e1, 1, 1, false);
                    temps2 += (System.currentTimeMillis() - startTime);
                }
                System.out.println("TEMPS CERCA (" + servidors + " servidors): " + (temps2/10.));
                System.out.println("--------------------------------------------------------------------------------------------------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
