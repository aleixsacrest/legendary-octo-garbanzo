package es.upc.fib.ia.experiments;

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import es.upc.fib.ia.Estat;

/**
 * Created by aleixsacrest on 10/04/2016.
 */
public class Experiment6 extends Experiment {
    public static void main() {
        try {
            for (int i = 0; i < 10; ++i) {
                int llavor = (int) (Math.random() * 100.);
                Requests req = new Requests(200, 5, llavor);
                Servers serv = new Servers(50, 5, llavor);

                System.out.println("LLAVOR:" + llavor);
                System.out.println("===================");

                //Hill Climbing
                Estat e = new Estat(serv, req);
                e.initMinTemps();
                System.out.println("\nHC: HF1");
                System.out.println("---------------------------------------");
                long startTime = System.currentTimeMillis();
                DistFSHillClimbing(e, 1, 1, true);
                System.out.println("TEMPS CERCA1 = " + (System.currentTimeMillis() - startTime));

                //Simulated Annealing
                System.out.println("\nSA: HF1");
                System.out.println("---------------------------------------");
                startTime = System.currentTimeMillis();
                DistFSSimulatedAnnealing(e, 1, 1, true, 300000, 100, 54, 0.002375);
                System.out.println("TEMPS CERCA1 = " + (System.currentTimeMillis() - startTime));

                //Hill Climbing
                System.out.println("\nHC: HF2");
                System.out.println("---------------------------------------");
                startTime = System.currentTimeMillis();
                DistFSHillClimbing(e, 0, 1, true);
                System.out.println("TEMPS CERCA2 = " + (System.currentTimeMillis() - startTime));

                //Simulated Annealing
                System.out.println("\nSA: HF2");
                System.out.println("---------------------------------------");
                startTime = System.currentTimeMillis();
                DistFSSimulatedAnnealing(e, 0, 1, true, 300000, 100, 54, 0.002375);
                System.out.println("TEMPS CERCA2 = " + (System.currentTimeMillis() - startTime));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
