package es.upc.fib.ia.driver;

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import es.upc.fib.ia.DistFSHeuristicFunction2;
import es.upc.fib.ia.DistFSSuccessorFunctionHC;
import es.upc.fib.ia.Estat;
import es.upc.fib.ia.aima.search.framework.Successor;
import es.upc.fib.ia.experiments.Experiment;

import java.util.List;

/**
 * Created by aleixsacrest on 16/03/2016.
 */
public class DriverEstat extends Experiment{
    public static void main(String[] args) {
        try {
            Requests req = new Requests(200, 5, 1);
            Servers serv = new Servers(50, 5, 1);
            Estat e = new Estat(serv, req);
            e.initMinTemps();

            double r = DistFSSimulatedAnnealing(e, 1, 0, false, 50000, 100, 9, 0.00436);
            System.out.println(r);

            System.out.println("fin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
