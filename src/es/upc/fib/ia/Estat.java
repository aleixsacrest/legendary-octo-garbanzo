package es.upc.fib.ia;

import java.util.HashMap;
import java.util.HashSet;
import IA.DistFS.*;


/**
 * Created by aleixsacrest on 08/03/2016.
 */
public class Estat {
    //TODO: public o privat
    public HashMap<Integer, HashSet<Integer>> servidors;
    public int[] peticions;

    public Estat() {
        servidors = new HashMap<Integer, HashSet<Integer>>();
        //peticions = new int[]
    }

    public void setServidors(HashMap<Integer, HashSet<Integer>> s) {
        this.servidors = s;
    }

    public HashMap<Integer, HashSet<Integer>> getServidors() {
        return this.servidors;
    }

    public void setPeticions(int[] p) {
        this.peticions = p;
    }

    public int[] getPeticions() {
        return this.peticions;
    }

    public HashSet<Integer> getAssignacions(int IDserv) {
        return this.servidors.get(IDserv);
    }

    public int getServei(int IDpet) {
        return this.peticions[IDpet];
    }

    public void canviarAssignacio(int IDpeticio, int IDnou) {
        int IDantic = this.peticions[IDpeticio];
        this.servidors.get(IDantic).remove(IDpeticio);
        this.servidors.get(IDnou).add(IDpeticio);
    }

    public void intervanviarAssignacions(int IDpet1, int IDpet2) {
        int IDserv1 = peticions[IDpet1];
        int IDserv2 = peticions[IDpet2];
        this.peticions[IDpet1] = IDserv2;
        this.peticions[IDpet2] = IDserv1;
        this.servidors.get(IDserv1).remove(IDpet1);
        this.servidors.get(IDserv1).add(IDpet2);
        this.servidors.get(IDserv2).remove(IDpet2);
        this.servidors.get(IDserv2).add(IDpet1);
    }
}
