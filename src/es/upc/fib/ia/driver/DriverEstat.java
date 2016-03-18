package es.upc.fib.ia.driver;

import es.upc.fib.ia.ComparadorCarregar;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Msacrest on 16/03/2016.
 */
public class DriverEstat {
    public static void main(String[] args) {
        System.out.println("Hola miro");
        Comparator<Integer[]> cmp = new ComparadorCarregar();
        PriorityQueue<Integer[]> ocupacio = new PriorityQueue<Integer[]>(3, cmp);
        //TODO: priority o ArrayList
        ArrayList<Integer[]> aa = new ArrayList<Integer[]>();
        Integer[] a = {1,3};
        Integer[] b = {1,2};
        Integer[] c = {1,1};
        Integer[] d = {1,4};
        ocupacio.add(a);
        //ocupacio.add(d);
        ocupacio.add(b);
        ocupacio.add(c);
        aa.add(a);
        aa.add(b);
        aa.add(c);
        Collections.sort(aa, cmp);
        for (Integer[] i : ocupacio) {
            System.out.print(i[0]);
            System.out.println(i[1]);
        }
    }
}
