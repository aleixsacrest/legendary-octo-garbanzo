package es.upc.fib.ia.experiments;

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import es.upc.fib.ia.Estat;

/**
 * Created by aleixsacres on 04/04/2016.
 */
public class Experiment3 extends Experiment{
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
                e1.initMinTemps();
                System.out.println("\nHC: HF1 (initRandom)");
                System.out.println("---------------------------------------");
                System.out.print("Estat inicial" + e1);
                DistFSSimulatedAnnealing(e1, 1, 0, false, 3, 3, 3, 3);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
