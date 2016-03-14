package es.upc.fib.ia;

import java.util.Comparator;

/**
 * Created by Msacrest on 14/03/2016.
 */
public class ComparadorCarregar implements Comparator<Integer[]> {

    @Override
    public int compare(Integer[] o1, Integer[] o2) {
        if (o2[1] > o1[1]) return -1;
        return 1;
    }
}
