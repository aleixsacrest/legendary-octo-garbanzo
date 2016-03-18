package es.upc.fib.ia;

import es.upc.fib.ia.aima.search.framework.Successor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexmiro on 15/3/16.
 */

//TODO: nomes s'implementa l'operador de canviar assignacio
public class SuccessorFunction {

    List getSuccessors(Estat state){
        ArrayList retVal = new ArrayList();
        HeuristicFunction hf = new HeuristicFunction();

        for (int i = 0; i <= state.getPeticions().length; ++i) {
            for (int j : state.servidorsArxiu(i)) {
                Estat newState = new Estat(state);
                newState.canviarAssignacio(i, j);
                int hv = hf.getHeuristicValue(newState);
                String S = "CANVI ASSIGNACIO " + i + " del Servidor " + state.getServei(i) + " al " + j;
                retVal.add(new Successor(S, newState));
            }
        }
        return retVal;
    }

}