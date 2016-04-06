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
public class Experiment9 extends Component {
    public static void main(String[] args) {
        try {
            long mitja_temps = 0;
            for (int i = 0; i < 10; ++i) {
                Requests req = new Requests(200, 5, 1234);
                Servers serv = new Servers(50, 5, 1234);

                //InitTemps
                long startTime = System.currentTimeMillis();
                Estat e = new Estat(serv, req);
                e.initMinTemps();
                System.out.println("\niteracion numero #" + i);
                System.out.println("---------------------------------------");
                System.out.print("Estat inicial" + e);
                DistFSHillClimbing(e, 1, 1);
                long endTime = System.currentTimeMillis();
                System.out.println("TEMPS EXEC: " + (endTime-startTime));
                mitja_temps += (endTime-startTime);
            }
            System.out.println("==============");
            System.out.println("TEMPS MITJA: " + mitja_temps/10);
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
