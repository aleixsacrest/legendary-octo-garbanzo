package es.upc.fib.ia;

import java.util.HashSet;

/**
 * Created by aleixsacrest on 18/03/2016.
 */
public class InfoServ {
    public HashSet<Integer> p;
    public Integer temps;

    public InfoServ(HashSet<Integer> s, Integer d) {
        this.p = s;
        this.temps = d;
    }

    public InfoServ(InfoServ inf) {
        this.p = new HashSet<Integer>(inf.p);
        this.temps = inf.temps;
    }

    @Override
    public boolean equals(Object o) {
        InfoServ inf = (InfoServ) o;
        if (!this.p.equals(inf.p)) return false;
        if (!this.temps.equals(inf.temps)) return false;
        return true;
    }
}
