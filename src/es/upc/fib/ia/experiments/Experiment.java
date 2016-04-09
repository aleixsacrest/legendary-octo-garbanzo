package es.upc.fib.ia.experiments;

import es.upc.fib.ia.*;
import es.upc.fib.ia.aima.search.framework.*;
import es.upc.fib.ia.aima.search.informed.HillClimbingSearch;
import es.upc.fib.ia.aima.search.informed.SimulatedAnnealingSearch;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Created by aleixsacrest on 04/04/2016.
 */
public class Experiment extends Component {
    protected static boolean printTots;

    public void Experiment() {
        printTots = false;
    }

    protected static double DistFSHillClimbing (Estat estat, int heurF, int succeFunc, boolean print) {
        double ret = 0.;
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
            //System.out.println();
            ret = printActions(agent.getActions(), print);
            if (print) System.out.println("---");
            printInstrumentation(agent.getInstrumentation(), print);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    protected static double DistFSSimulatedAnnealing (Estat estat, int heurF, int succeFunc, boolean print, int steps, int stiter, int k, double lamb) {
        double ret = 0.;
        try {

            Problem problem;
            HeuristicFunction heur;
            SuccessorFunction succe;

            //temps + factor de carrega
            if (heurF == 0) heur = new DistFSHeuristicFunction2();

            //temps pitjor servidor
            else heur = new DistFSHeuristicFunction1();

            //canviar
            if (succeFunc == 0) succe = new DistFSSuccessorFunctionSA2();

            //intercanviar
            else succe = new DistFSSuccessorFunctionSA();

            problem = new Problem(estat, succe, new DistFSGoalTest(), heur);
            Search search;
            search = new SimulatedAnnealingSearch(steps,stiter,k,lamb);
            SearchAgent agent = new SearchAgent(problem, search);
            if (print) System.out.println();
            ret = printActions(agent.getActions(), print);
            if (print) System.out.println("---");
            printInstrumentation(agent.getInstrumentation(), print);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static double DistFSSimulatedAnnealing(Estat estat, int heurF, int succeFunc, boolean print) {
        double ret = 0.;
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
            search = new SimulatedAnnealingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            if (print) System.out.println();
            ret = printActions(agent.getActions(), print);
            if (print) System.out.println("---");
            printInstrumentation(agent.getInstrumentation(), print);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static void printInstrumentation(Properties properties, boolean print) {
        if (print) {
            Iterator keys = properties.keySet().iterator();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                String property = properties.getProperty(key);
                System.out.println(key + " : " + property);
            }
        }

    }

    private static double printActions(java.util.List actions, boolean print) { //TODO: print diu si imprimir o no, i printTots si fals nomes imprimeix el final
        String[] arr = actions.get(actions.size()-1).toString().split(";");
        if (print) {
            if (printTots) {
                for (int i = 0; i < actions.size(); ++i) {
                    String action = actions.get(i).toString();
                    System.out.println(action);
                }
            } else {
                System.out.println("\nEstat Final\n" + arr[0]);
            }
        }
        return Double.parseDouble(arr[1]);
    }
}
