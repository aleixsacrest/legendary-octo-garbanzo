package es.upc.fib.ia;

import es.upc.fib.ia.aima.search.framework.Successor;
import es.upc.fib.ia.aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by aleixsacrest on 02/04/2016.
 */
public class DistFSSuccessorFunctionSA2 implements SuccessorFunction{

    @Override
    public List getSuccessors(Object s) {
        ArrayList retVal = new ArrayList();
        Estat state = (Estat) s;
        DistFSHeuristicFunction2 hf = new DistFSHeuristicFunction2();
        Random myRandom = new Random();
        int IDpet1, nouServ, IDpet2;

        IDpet1 = myRandom.nextInt(state.getPeticions().length);

        do {
            nouServ = (Integer) state.getServidors().keySet().toArray()[myRandom.nextInt(state.getServidors().size())];
        } while (state.getServei(IDpet1) == nouServ);

        do {
            IDpet2 = myRandom.nextInt(state.getPeticions().length);
        } while (IDpet1 == IDpet2 && !state.intercanviPossible(IDpet1,IDpet2));

        Estat newState = new Estat(state);

        int opt = myRandom.nextInt(2);

        if (opt == 0) newState.canviarAssignacio(IDpet1, nouServ);
        else newState.intercanviarAssignacions(IDpet1, IDpet2);

        double hValue = hf.getHeuristicValue(newState);
        String S;
        if (opt == 0) S = "CANVI ASSIGNACIO " + IDpet1 + ": del Servidor " + state.getServei(IDpet1) + " al " + nouServ + newState;
        else S = "INTERCANVI ASSIGNACIONS " + IDpet1 + " i " + IDpet2 + newState;
        retVal.add(new Successor(S, newState));
        return retVal;
    }
}
