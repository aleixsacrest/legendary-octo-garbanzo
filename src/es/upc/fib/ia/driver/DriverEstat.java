package es.upc.fib.ia.driver;

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import es.upc.fib.ia.DistFSHeuristicFunction2;
import es.upc.fib.ia.DistFSSuccessorFunctionHC;
import es.upc.fib.ia.Estat;
import es.upc.fib.ia.aima.search.framework.Successor;

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

            DistFSSuccessorFunctionHC succ = new DistFSSuccessorFunctionHC();
            List arr = succ.getSuccessors(e);
            double h = -1;
            String s = "";
            for (Object o : arr) {
                Estat state = (Estat) ((Successor) o).getState();
                double hV = (new DistFSHeuristicFunction2()).getHeuristicValue(state);
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

            /*ArrayList<Integer[]> arr = new ArrayList<Integer[]>();
            Integer[] ii = {1,1};
            arr.add(ii);
            Integer[] jj = {1,2};
            Integer[] kk = {1,3};
            arr.add(kk);
            arr.add(jj);
            ComparadorCarregar cmp = new ComparadorCarregar();
            Collections.sort(arr, cmp);
            for (Integer[] in : arr) {System.out.print(in[0]); System.out.println(in[1]);}*/

            System.out.println("fin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
