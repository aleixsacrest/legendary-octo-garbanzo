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
    private static boolean print = false;

    public static void main(String[] args) {
        try {
            Requests req = new Requests(200, 5, 1);
            Servers serv = new Servers(50, 5, 1);
            e1 = new Estat(serv, req);
            e1.initMinTemps();

            System.out.println("heuristic inicial: " + e1.toString().split(";")[1] + "\n");

            double hV_millor = Double.parseDouble(e1.toString().split(";")[1]);

            double lambL = 0.002, lambR = 0.01;
            int kL = 1, kR = 100;
            System.out.println("10000 iteracions");
            determinacioParametres(kL, kR, lambL, lambR, 10000, 0);

            lambL = 0.0006;
            System.out.println("20000 iteracions");
            determinacioParametres(kL, kR, lambL, lambR, 20000, 0);

            lambL = 0.0004;
            System.out.println("30000 iteracions");
            determinacioParametres(kL, kR, lambL, lambR, 30000, 0);

            lambL = 0.0003;
            System.out.println("40000 iteracions");
            determinacioParametres(kL, kR, lambL, lambR, 40000, 0);

            lambL = 0.00025;
            System.out.println("50000 iteracions");
            determinacioParametres(kL, kR, lambL, lambR, 50000, 0);

            System.out.println("100000 iteracions");
            determinacioParametres(kL, kR, lambL, lambR, 100000, 0);

            System.out.println("300000 iteracions");
            determinacioParametres(kL, kR, lambL, lambR, 300000, 0);

            System.out.println("heuristic inicial: " + e1.toString().split(";")[1] + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void determinacioParametres(int kL, int kR, double lambL, double lambR, int it, int level) {
        //System.out.println(level);
        int nIterMitja = 20;
        int auxk = (kR+kL) / 2;
        double auxlamb = (lambR+lambL) / 2;
        //System.out.println("\n\n\nk: " + auxk + " lambda: " + auxlamb);
        double heurM = 0;
        for (int i = 0; i < nIterMitja; ++i)
            heurM += DistFSSimulatedAnnealing(e1, 1, 1, print, it, 100, auxk, auxlamb);
        heurM /= nIterMitja;

        if (level == MAX_LEVEL) {
            System.out.println("k: " + auxk + " lambda: " + auxlamb + " heuristic: " + heurM + "\nlevel: " + level);
            return;
        }

        int auxkL = (auxk+kL) / 2, auxkR = (kR+auxk) / 2;
        double auxlambL = (auxlamb+lambL) / 2, auxlambR = (lambR+auxlamb) / 2;
        int millor = -1;
        double hV = heurM;

        //System.out.println("\n\n\nk: " + auxkL + " lambda: " + auxlambL);
        double heur1 = 0;
        for (int i = 0; i < nIterMitja; ++i)
            heur1 += DistFSSimulatedAnnealing(e1, 1, 1, print, it, 100, auxkL, auxlambL);
        heur1 /= nIterMitja;
        if (heur1 < hV) {
            millor = 1;
            hV = heur1;
        }
        //System.out.println("\n\n\nk: " + auxkL + " lambda: " + auxlambR);
        double heur2 = 0;
        for (int i = 0; i < nIterMitja; ++i)
            heur2 += DistFSSimulatedAnnealing(e1, 1, 1, print, it, 100, auxkL, auxlambR);
        heur2 /= nIterMitja;
        if (heur2 < hV) {
            millor = 2;
            hV = heur2;
        }
        //System.out.println("\n\n\nk: " + auxkR + " lambda: " + auxlambL);
        double heur3 = 0;
        for (int i = 0; i < nIterMitja; ++i)
            heur3 += DistFSSimulatedAnnealing(e1, 1, 1, print, it, 100, auxkR, auxlambL);
        heur3 /= nIterMitja;
        if (heur3 < hV) {
            millor = 3;
            hV = heur3;
        }
        //System.out.println("\n\n\nk: " + auxkR + " lambda: " + auxlambR);
        double heur4 = 0;
        for (int i = 0; i < nIterMitja; ++i)
            heur4 += DistFSSimulatedAnnealing(e1, 1, 1, print, it, 100, auxkR, auxlambR);
        heur4 /= nIterMitja;
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
