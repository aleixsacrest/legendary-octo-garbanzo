package es.upc.fib.ia.experiments;

import es.upc.fib.ia.*;
import es.upc.fib.ia.aima.search.framework.*;
import es.upc.fib.ia.aima.search.informed.HillClimbingSearch;
import es.upc.fib.ia.aima.search.informed.SimulatedAnnealingSearch;

import java.awt.*;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by aleixsacrest on 04/04/2016.
 */
public class Experiment extends Component {
    protected static boolean printTots;

    public void Experiment() {
        printTots = false;
    }

    protected static void DistFSHillClimbing (Estat estat, int heurF, int succeFunc) {
        try {

            Problem problem;
            HeuristicFunction heur;
            SuccessorFunction succe;

            //temps + factor de carrega
            if (heurF == 0) heur = new DistFSHeuristicFunction2();

            //temps pitjor servidor
            else heur = new DistFSHeuristicFunction1();

            //canviar
            if (succeFunc == 0) succe = new DistFSSuccessorFunctionHC();

            //canviar + intercanviar
            else succe = new DistFSSuccessorFunctionHC2();

            problem = new Problem(estat, succe, new DistFSGoalTest(), heur);
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            System.out.println();
            printActions(agent.getActions());
            System.out.println("---");
            printInstrumentation(agent.getInstrumentation());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void DistFSSimulatedAnnealing (Estat estat, int heurF, int succeFunc, boolean paramsValids, int steps, int stiter, int k, double lamb) {
        try {

            Problem problem;
            HeuristicFunction heur;
            SuccessorFunction succe;

            //temps + factor de carrega
            if (heurF == 0) heur = new DistFSHeuristicFunction2();

            //temps pitjor servidor
            else heur = new DistFSHeuristicFunction1();

            //canviar
            if (succeFunc == 0) succe = new DistFSSuccessorFunctionSA();

            //intercanviar
            else succe = new DistFSSuccessorFunctionSA2();
            problem = new Problem(estat, succe, new DistFSGoalTest(), heur);
            Search search;
            if (paramsValids) search = new SimulatedAnnealingSearch(steps,stiter,k,lamb);
            else search = new SimulatedAnnealingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            System.out.println();
            printActions(agent.getActions());
            System.out.println("---");
            printInstrumentation(agent.getInstrumentation());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();

        while(keys.hasNext()) {
            String key = (String)keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(java.util.List actions) {
        if (printTots) {
            for(int i = 0; i < actions.size(); ++i) {
                String action = actions.get(i).toString();
                System.out.println(action);
            }
        }
        else System.out.println("\nFinal\n" + actions.get(actions.size()-1).toString());

    }
}
