package es.upc.fib.ia;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import IA.DistFS.*;

/**
 * Created by aleixsacrest on 08/03/2016.
 */
public class Estat {
    //TODO: public o privat
    public HashMap<Integer, HashSet<Integer>> servidors;
    public int[] peticions;
    private Servers serv;
    private Requests req;

    public Estat(Servers serv, Requests req) {
        servidors = new HashMap<Integer, HashSet<Integer>>();
        //TODO: this.sevidors insertar tantes posicions com servidors
        this.serv = serv;
        this.req = req;
        this.peticions = new int[this.req.size()];
    }

    public void innitMinTemps() {
        int i;
        for (i = 0; i < this.peticions.length; ++i) {
            int[] pet = this.req.getRequest(i);
            int min = -1;
            int s = 0;
            for (int candidat : this.serv.fileLocations(pet[1])) {
                int trans = this.serv.tranmissionTime(candidat, pet[0]);
                if (min == -1 || trans < min) {
                    min = trans;
                    s = candidat;
                }
            }
            try {
                if (s == 0) throw new Exception("no s'ha trobat cap servidor");
                this.peticions[i] = s;
                this.servidors.get(s).add(i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void innitEqCarrega() {
        Comparator<Integer[]> cmp = new ComparadorCarregar();
        PriorityQueue<Integer[]> ocupacio = new PriorityQueue<Integer[]>(this.serv.size(), cmp);

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
