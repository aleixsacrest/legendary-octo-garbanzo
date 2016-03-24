package es.upc.fib.ia;

import es.upc.fib.ia.aima.search.framework.Successor;
import es.upc.fib.ia.aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexmiro on 15/3/16.
 */

//TODO: nomes s'implementa l'operador de canviar assignacio + successorfunction x SA

public class DistFSSuccessorFunction implements SuccessorFunction {

    public List getSuccessors(Object s){
        Estat state = (Estat) s;
        ArrayList retVal = new ArrayList();
        DistFSHeuristicFunction hf = new DistFSHeuristicFunction();

        for (int i = 0; i <= state.getPeticions().length; ++i) {
            for (int j : state.getServidorsArxiu(i)) {
                Estat newState = new Estat(state);
                //if (i != j)
                newState.canviarAssignacio(i, j);
                //TODO: perque es fa servir?
                double hv = hf.getHeuristicValue(newState);
                String S = "CANVI ASSIGNACIO " + i + " del Servidor " + state.getServei(i) + " al " + j;
                retVal.add(new Successor(S, newState));
            }
        }
        return retVal;
    }

}