package es.upc.fib.ia;

import es.upc.fib.ia.aima.search.framework.Successor;
import es.upc.fib.ia.aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Msacrest on 29/03/2016.
 */
public class DistFSSuccessorFunctionSA implements SuccessorFunction {

    @Override
    public List getSuccessors(Object s) {
        ArrayList retVal = new ArrayList();
        Estat state = (Estat) s;
        DistFSHeuristicFunction hf = new DistFSHeuristicFunction();
        Random myRandom = new Random();
        int IDpet, nouServ;

        IDpet = myRandom.nextInt(state.getPeticions().length);

        do {
            nouServ = (Integer) state.getServidors().keySet().toArray()[myRandom.nextInt(state.getServidors().size())];
        } while (state.getServei(IDpet) == nouServ);

        Estat newState = new Estat(state);

        newState.canviarAssignacio(IDpet, nouServ);

        double hValue = hf.getHeuristicValue(newState);
        String S = "CANVI ASSIGNACIO " + IDpet + ": del Servidor " + state.getServei(IDpet) + " al " + nouServ + " amb cost: " + hValue;

        retVal.add(new Successor(S, newState));
        return retVal;
    }
}
