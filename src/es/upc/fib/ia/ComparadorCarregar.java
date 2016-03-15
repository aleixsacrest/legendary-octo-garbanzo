package es.upc.fib.ia;

import java.util.Comparator;

/**
 * Created by aleixsacrest on 14/03/2016.
 */
public class ComparadorCarregar implements Comparator<Integer[]> {

    @Override
    public int compare(Integer[] o1, Integer[] o2) {
        if (o2[1] > o1[1]) return -1;
        else if (o2[1] == o1[2])return 0;
        return 1;
    }
}
