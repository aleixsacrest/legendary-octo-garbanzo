package es.upc.fib.ia.experiments;

import IA.DistFS.*;
import es.upc.fib.ia.*;
import es.upc.fib.ia.aima.search.framework.*;
import es.upc.fib.ia.aima.search.informed.HillClimbingSearch;
import es.upc.fib.ia.aima.search.informed.SimulatedAnnealingSearch;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by alexmiro on 03/04/2016.
 */
public class Experiment1 extends Component {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10; ++i) {

                int llavor = (int) 28;
                Requests req = new Requests(200, 5, llavor);
                Servers serv = new Servers(50, 5, llavor);

                System.out.println("LLAVOR:" + llavor);
                System.out.println("===================");

                Estat e1 = new Estat(serv, req);
                e1.initRandom();
                Estat e2 = new Estat(e1);
                Estat e3 = new Estat(e1);
                Estat e4 = new Estat(e1);

                //HC: HF1 + SF1 (sense intercanviarAssig)
                System.out.println("\nHC: HF1 + SF1 (sense intercanviarAssig)");
                System.out.println("---------------------------------------");
                System.out.print("Estat inicial" + e1);
                DistFSHillClimbing(e1, 1, 0);

                //HC: HF1 + SF2 (amb intercanviarAssig)
                System.out.println("\nHC: HF1 + SF2 (amb intercanviarAssig)");
                System.out.println("---------------------------------------");
                System.out.print("Estat inicial" + e2);
                DistFSHillClimbing(e2, 1, 1);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void DistFSHillClimbing (Estat estat, int heurF, int succeFunc) {
        try {

            Problem problem;
            HeuristicFunction heur;
            SuccessorFunction succe;
            if (heurF == 0) heur = new DistFSHeuristicFunction2();
            else heur = new DistFSHeuristicFunction1();
            if (succeFunc == 0) succe = new DistFSSuccessorFunctionHC();
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
