package es.upc.fib.ia.experiments;

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import es.upc.fib.ia.Estat;

/**
 * Created by aleixsacres on 04/04/2016.
 */
public class Experiment3 extends Experiment{
    private static Estat e1;
    private static int MAX_LEVEL = 5;

    public static void main(String[] args) {
        try {
            int llavor = (int) (Math.random() * 100.);
            Requests req = new Requests(200, 5, llavor);
            Servers serv = new Servers(50, 5, llavor);
            e1 = new Estat(serv, req);
            e1.initMinTemps();

            System.out.println("heuristic inicial: " + e1.toString().split(";")[1] + "\n");

            double hV_millor = Double.parseDouble(e1.toString().split(";")[1]);

            /*int it_millor = -1;

            for (int it = 10000; it <= 50000; it += 10000) {
                double heur = 0;
                for (int i = 0; i < 100; ++i)
                    heur += DistFSSimulatedAnnealing(e1, 1, 0, false, it, 100, 5, 0.005D);
                heur /= 100;
                if (it_millor == -1 || heur < hV_millor) {
                    it_millor = it;
                    hV_millor = heur;
                }

                System.out.println("iteracions = " + it + " heuristic: " + heur);
            }

            System.out.println("\n millor it: " + it_millor);*/

            double lambL = 0.0005, lambR = 0.01;
            int kL = 5, kR = 25;
            determinacioParametres(kL, kR, lambL, lambR, 40000, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void determinacioParametres(int kL, int kR, double lambL, double lambR, int it, int level) {
        System.out.println(level);
        int auxk = (kR+kL) / 2;
        double auxlamb = (lambR+lambL) / 2;
        double heurM = 0;
        for (int i = 0; i < 10; ++i)
            heurM += DistFSSimulatedAnnealing(e1, 1, 0, false, it, 100, auxk, auxlamb);
        heurM /= 10;

        if (level == MAX_LEVEL) {
            System.out.println("k: " + auxk + " lambda: " + auxlamb + " heuristic: " + heurM + "\nlevel: " + level);
            return;
        }

        int auxkL = (auxk+kL) / 2, auxkR = (kR+auxk) / 2;
        double auxlambL = (auxlamb+lambL) / 2, auxlambR = (lambR+auxlamb) / 2;
        int millor = -1;
        double hV = heurM;

        double heur1 = 0;
        for (int i = 0; i < 10; ++i)
            heur1 += DistFSSimulatedAnnealing(e1, 1, 0, false, it, 100, auxkL, auxlambL);
        heur1 /= 10;
        if (heur1 < hV) {
            millor = 1;
            hV = heur1;
        }
        double heur2 = 0;
        for (int i = 0; i < 10; ++i)
            heur2 += DistFSSimulatedAnnealing(e1, 1, 0, false, it, 100, auxkL, auxlambR);
        heur2 /= 10;
        if (heur2 < hV) {
            millor = 2;
            hV = heur2;
        }
        double heur3 = 0;
        for (int i = 0; i < 10; ++i)
            heur3 += DistFSSimulatedAnnealing(e1, 1, 0, false, it, 100, auxkR, auxlambL);
        heur3 /= 10;
        if (heur3 < hV) {
            millor = 3;
            hV = heur3;
        }
        double heur4 = 0;
        for (int i = 0; i < 10; ++i)
            heur4 += DistFSSimulatedAnnealing(e1, 1, 0, false, it, 100, auxkR, auxlambR);
        heur4 /= 10;
        if (heur4 < hV) {
            millor = 4;
            hV = heur4;
        }
        if (millor == -1) {
            System.out.println("k: " + auxk + " lambda: " + auxlamb + " heuristic: " + hV + "\nlevel: " + level);
            return;
        }
        else if (millor == 1)
            determinacioParametres(kL, auxk, lambL, auxlamb, it, level+1);
        else if (millor == 2)
            determinacioParametres(kL, auxk, auxlamb, lambR, it, level+1);
        else if (millor == 3)
            determinacioParametres(auxk, kR, lambL, auxlamb, it, level+1);
        else if (millor == 4)
            determinacioParametres(auxk, kR, auxlamb, lambR, it, level+1);
    }
}
