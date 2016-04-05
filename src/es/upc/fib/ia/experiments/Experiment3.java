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
            int llavor = (int) (Math.random() * 100.);
            Requests req = new Requests(200, 5, llavor);
            Servers serv = new Servers(50, 5, llavor);
            Estat e1 = new Estat(serv, req);
            e1.initMinTemps();

            System.out.println("heuristic inicial: " + e1.toString().split(";")[1] + "\n");

            double hV_millor = Double.parseDouble(e1.toString().split(";")[1]);
            /*int k_millor = -1;

            for (int k = 20; k >= 0; k -= 1) {
                double heur = 0;
                for (int i = 0; i < 10; ++i)
                    heur += DistFSSimulatedAnnealing(e1, 1, 0, false, true, 10000, 100, k, 0.005D);
                heur /= 10;
                if (k_millor == -1 || heur < hV_millor) {
                    k_millor = k;
                    hV_millor = heur;
                }

                System.out.println("k = " + k + " heuristic: " + heur);
            }

            System.out.println("\n millor k: " + k_millor);//*/

            int it_millor = -1;

            for (int it = 10000; it <= 50000; it += 10000) {
                double heur = 0;
                for (int i = 0; i < 20; ++i)
                    heur += DistFSSimulatedAnnealing(e1, 1, 0, false, it, 100, 5, 0.005D);
                heur /= 20;
                if (it_millor == -1 || heur < hV_millor) {
                    it_millor = it;
                    hV_millor = heur;
                }

                System.out.println("iteracions = " + it + " heuristic: " + heur);
            }

            System.out.println("\n millor it: " + it_millor);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
