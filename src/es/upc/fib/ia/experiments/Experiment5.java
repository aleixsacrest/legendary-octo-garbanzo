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
public class Experiment5 extends Experiment {
    public static void main(String[] args) {
        try {
            boolean tendencia = false;
            for (int usuaris = 100; !tendencia; usuaris += 50) {
                //estudi1
                long temps1 = 0;
                for (int i = 0; i < 1; ++i) {
                    int llavor = (int) (Math.random() * 100.);
                    Requests req = new Requests(usuaris, 5, llavor);
                    Servers serv = new Servers(50, 5, llavor);

                    System.out.println("LLAVOR:" + llavor);
                    System.out.println("===================");

                    Estat e1 = new Estat(serv, req);
                    e1.initMinTemps();

                    //HC: HF1 + SF1 (sense intercanviarAssig)
                    System.out.println("\nHC: HF1 + SF1 (sense intercanviarAssig)");
                    System.out.println("---------------------------------------");
                    System.out.print("Estat inicial" + e1);
                    long startTime = System.currentTimeMillis();
                    DistFSHillClimbing(e1, 1, 0, true);
                    temps1 += (System.currentTimeMillis() - startTime);
                }
                System.out.println("TEMPS EXEC (" + usuaris + " usuaris): " + (temps1/10.));
                Scanner keyboard = new Scanner(System.in);
                System.out.println("Tendencia? s/n");
                String t = keyboard.next();
                if (t == "s")
                    tendencia = true;
            }
            tendencia = false;
            for (int servidors = 100; !tendencia; servidors += 50) {
                //estudi2
                long temps2 = 0;
                for (int i = 0; i < 10; ++i) {
                    int llavor = (int) (Math.random() * 100.);
                    Requests req = new Requests(200, 5, llavor);
                    Servers serv = new Servers(servidors, 5, llavor);

                    System.out.println("LLAVOR:" + llavor);
                    System.out.println("===================");

                    Estat e1 = new Estat(serv, req);
                    e1.initMinTemps();

                    //HC: HF1 + SF1 (sense intercanviarAssig)
                    System.out.println("\nHC: HF1 + SF1 (sense intercanviarAssig)");
                    System.out.println("---------------------------------------");
                    System.out.print("Estat inicial" + e1);
                    long startTime = System.currentTimeMillis();
                    DistFSHillClimbing(e1, 1, 0, true);
                    temps2 += (System.currentTimeMillis() - startTime);
                }
                System.out.println("TEMPS EXEC (" + servidors + " servidors): " + (temps2/10.));
                Scanner keyboard = new Scanner(System.in);
                System.out.println("Tendencia? s/n");
                String t = keyboard.next();
                if (t == "s")
                    tendencia = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
