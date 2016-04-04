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
            for (int k = 20; k >= 0; k -= 2) {
                int llavor = (int) (Math.random() * 100.);
                Requests req = new Requests(200, 5, 1);
                Servers serv = new Servers(50, 5, 1);

                Estat e1 = new Estat(serv, req);
                e1.initMinTemps();
                System.out.println("heuristic inicial: " + e1.toString().split(";")[1]);
                double heur = 0;
                for (int i = 0; i < 10; ++i) {


                    //System.out.print("Estat inicial" + e1);
                    heur += DistFSSimulatedAnnealing(e1, 1, 0, false, true, 10000, 100, k, 0.005D);
                }
                heur /= 10;
                System.out.println("k = " + k + " heuristic: " + heur + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
