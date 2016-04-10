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
 * Created by alexmiro on 10/04/2016.
 */
public class Experiment7 extends Experiment {
    public static void main(String[] args) {
        try {
            for (int reps = 5; reps <= 25; reps += 5) {
                System.out.println("==============================================");
                System.out.println("#REPLICACIONS: " + reps);
                System.out.println("==============================================");
                for (int i = 0; i < 10; ++i) {
                    int llavor = (int) (Math.random() * 100.);
                    Requests req = new Requests(200, 5, llavor);
                    Servers serv = new Servers(50, reps, llavor);

                    System.out.println("LLAVOR: " + llavor);
                    System.out.println("===================");

                    Estat e = new Estat(serv, req);
                    e.initMinTemps();
                    System.out.println("\nHC: HF1");
                    System.out.println("---------------------------------------");
                    long startTime = System.currentTimeMillis();
                    DistFSHillClimbing(e, 1, 0, true);
                    System.out.println("TEMPS CERCA1 = " + (System.currentTimeMillis() - startTime));

                    System.out.println("\nHC: HF2");
                    System.out.println("---------------------------------------");
                    startTime = System.currentTimeMillis();
                    DistFSHillClimbing(e, 0, 0, true);
                    System.out.println("TEMPS CERCA2 = " + (System.currentTimeMillis() - startTime));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
