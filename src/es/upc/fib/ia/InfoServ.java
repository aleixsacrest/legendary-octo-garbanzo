package es.upc.fib.ia;

import java.util.HashSet;

/**
 * Created by aleixsacrest on 18/03/2016.
 */
public class InfoServ {
    public HashSet<Integer> p;
    public double temps;

    public InfoServ(HashSet<Integer> s, double d) {
        this.p = s;
        this.temps = d;
    }
}
