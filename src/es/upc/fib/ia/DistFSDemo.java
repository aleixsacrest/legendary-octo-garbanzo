package es.upc.fib.ia;

import IA.DistFS.*;
import es.upc.fib.ia.aima.search.framework.Problem;
import es.upc.fib.ia.aima.search.framework.Search;
import es.upc.fib.ia.aima.search.framework.SearchAgent;
import es.upc.fib.ia.aima.search.informed.HillClimbingSearch;
import es.upc.fib.ia.aima.search.informed.SimulatedAnnealingSearch;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by aleixsacrest on 24/03/2016.
 *
 *
 *
 *
 */
public class DistFSDemo extends Component {
    public static void main(String[] args) {
        try {
            Requests req = new Requests(200, 5, 1);
            Servers serv = new Servers(50, 5, 1);
            Estat e = new Estat(serv, req);

            int alg = algorisme();

            int ini = inicialitzacio();
            if (ini == 0) {
                e.initMinTemps();
            }
            else if (ini == 1) e.initEqCarrega();
            else if (ini == 2) e.initRandom();
            else throw new Exception("error de selecció");
            System.out.print("Estat inicial" + e);

            if (alg == 0) DistFSHillClimbing(e);
            else DistFSSimulatedAnnealing(e);

            System.out.println("\nmin temps possible " + e.getMinPosTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int inicialitzacio() {
        Object[] possibilities = {"initMinTemps", "initEqCarrega", "initRandom"};
        Component c = new Component() {
            @Override
            public String getName() {
                return super.getName();
            }
        };
        int st = (int) JOptionPane.showOptionDialog(
                c, "Escull la manera d'inicialitzar estat",
                "init", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, possibilities, null);
        /*String s = (String) JOptionPane.showInputDialog(
                c,"Escull la manera d'inicialitzar estat", "init",
                JOptionPane.PLAIN_MESSAGE,
                null, possibilities, null);*/
        return st;
    }

    public static int algorisme() {
        Object[] possibilities = {"Hill Climbing", "Simulated Annealing"};
        Component c = new Component() {
            @Override
            public String getName() {
                return super.getName();
            }
        };
        int st = (int) JOptionPane.showOptionDialog(
                c, "Escull l'algorisme a utilitzar",
                "algorisme", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, possibilities, null);
        return st;
    }

    private static void DistFSHillClimbing (Estat estat) {
        try {

            Problem problem = new Problem(estat, new DistFSSuccessorFunction(), new DistFSGoalTest(), new DistFSHeuristicFunction2());
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

    private static void DistFSSimulatedAnnealing (Estat estat) {
        try {

            Problem problem = new Problem(estat, new DistFSSuccessorFunctionSA(), new DistFSGoalTest(), new DistFSHeuristicFunction2());
            Search search = new SimulatedAnnealingSearch();
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
        for(int i = 0; i < actions.size(); ++i) {
            String action = actions.get(i).toString();
            System.out.println(action);
        }

    }
}
