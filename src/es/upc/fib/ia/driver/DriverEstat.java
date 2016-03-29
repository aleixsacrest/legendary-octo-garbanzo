package es.upc.fib.ia.driver;

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import com.sun.net.httpserver.Authenticator;
import es.upc.fib.ia.DistFSHeuristicFunction;
import es.upc.fib.ia.DistFSSuccessorFunction;
import es.upc.fib.ia.Estat;
import es.upc.fib.ia.InfoServ;
import es.upc.fib.ia.aima.search.framework.HeuristicFunction;
import es.upc.fib.ia.aima.search.framework.Successor;

import javax.sound.midi.MidiDevice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by aleixsacrest on 16/03/2016.
 */
public class DriverEstat {
    public static void main(String[] args) {
        try {
            Requests req = new Requests(200, 5, 1);
            Servers serv = new Servers(50, 5, 1);
            Estat e = new Estat(serv, req);
            e.initMinTemps();

            DistFSSuccessorFunction succ = new DistFSSuccessorFunction();
            List arr = succ.getSuccessors(e);
            double h = -1;
            String s = "";
            for (Object o : arr) {
                Estat state = (Estat) ((Successor) o).getState();
                double hV = (new DistFSHeuristicFunction()).getHeuristicValue(state);
                if (hV == 0.0) {
                    int i = 2+2;
                    s = "pet";
                }
                if (h == -1 || hV < h) {
                    h = hV;
                    s = ((Successor) o).getAction();
                }
            }
            System.out.println(h);
            System.out.println(s);

            System.out.println("fin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
