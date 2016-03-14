package es.upc.fib.ia;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Msacrest on 08/03/2016.
 */
public class Estat {
    private HashMap<Integer, HashSet<Integer>> servidors;
    private int[] peticions;

    public Estat() {
        servidors = new HashMap<Integer, HashSet<Integer>>();
    }
}
