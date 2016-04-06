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
 * Created by alexmiro on 03/04/2016.
 */
public class Experiment2 extends Experiment{
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10; ++i) {

                int llavor = (int) (Math.random() * 100.);
                Requests req = new Requests(200, 5, llavor);
                Servers serv = new Servers(50, 5, llavor);

                System.out.println("LLAVOR:" + llavor);
                System.out.println("===================");

                //InitRandom
                Estat e1 = new Estat(serv, req);
                e1.initRandom();
                System.out.println("\nHC: HF1 (initRandom)");
                System.out.println("---------------------------------------");
                System.out.print("Estat inicial" + e1);
                DistFSHillClimbing(e1, 1, 1,true);

                //InitTemps
                Estat e3 = new Estat(serv, req);
                e3.initMinTemps();
                System.out.println("\nHC: HF1 (initMinTemps)");
                System.out.println("---------------------------------------");
                System.out.print("Estat inicial" + e3);
                DistFSHillClimbing(e3, 1, 1, true);

                //InitFdC
                Estat e5 = new Estat(serv, req);
                e5.initEqCarrega();
                System.out.println("\nHC: HF1 (initEqCarrega)");
                System.out.println("--------------------------------------");
                System.out.print("Estat inicial" + e5);
                DistFSHillClimbing(e5, 1, 1, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
