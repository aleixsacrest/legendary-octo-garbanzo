package es.upc.fib.ia;

import es.upc.fib.ia.aima.search.framework.Successor;
import es.upc.fib.ia.aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexmiro on 15/3/16.
 */

public class DistFSSuccessorFunctionHC implements SuccessorFunction {

    public List getSuccessors(Object s){
        Estat state = (Estat) s;
        ArrayList retVal = new ArrayList();
        DistFSHeuristicFunction2 hf = new DistFSHeuristicFunction2();

        for (int i = 0; i < state.getPeticions().length; ++i) {
            for (int j : state.getServidorsArxiu(i)) {
                Estat newState = new Estat(state);
                if (state.getServei(i) != j) {
                    newState.canviarAssignacio(i, j);
                    //TODO: perque es fa servir?
                    double hv = hf.getHeuristicValue(newState);
                    String S = "CANVI ASSIGNACIO " + i + " del Servidor " + state.getServei(i) + " al " + j + newState;
                    retVal.add(new Successor(S, newState));
                }
            }
        }
        return retVal;
    }

}