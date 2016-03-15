package es.upc.fib.ia;

import java.util.*;

import IA.DistFS.*;

/**
 * Created by aleixsacrest on 08/03/2016.
 */
public class Estat {
    private HashMap<Integer, HashSet<Integer>> servidors;
    private int[] peticions;
    //TODO: getters i setters:
    private Servers S;
    private Requests R;

    public Estat() {
        servidors = new HashMap<Integer, HashSet<Integer>>();
    }

    public Estat(Servers S, Requests R) {
        servidors = new HashMap<Integer, HashSet<Integer>>();
        this.S = S;
        this.R = R;
        this.peticions = new int[this.R.size()];
    }

    public void initMinTemps() {
        for (int i = 0; i < this.peticions.length; ++i) {
            int[] pet = this.R.getRequest(i);
            int min = -1;
            int s = 0;
            for (int candidat : this.S.fileLocations(pet[1])) {
                if (!this.servidors.containsKey(candidat)) this.servidors.put(candidat, new HashSet<Integer>());
                int trans = this.S.tranmissionTime(candidat, pet[0]);
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

    public void initEqCarrega() {
        omplirServidors();
        for (int serv : this.servidors.keySet()) {
            int[] o = {serv, this.servidors.get(serv).size()};
        }
        Comparator<Integer[]> cmp = new ComparadorCarregar();
        //TODO: this.S.size()??
        PriorityQueue<Integer[]> ocupacio = new PriorityQueue<Integer[]>(this.S.size(), cmp);
        for (int i = 0; i < this.peticions.length; ++i) {
            for (Integer[] ii : ocupacio) {
                //TODO: acutalitzar� b� la PriorityQueue?
                if (this.S.fileLocations(this.R.getRequest(i)[1]).contains(ii[0])) {
                    ii[1] += this.S.tranmissionTime(ii[0], this.R.getRequest(i)[0]);
                    this.servidors.get(ii[0]).add(i);
                    this.peticions[i] = ii[0];
                    break;
                }
            }
        }
    }

    public void initRandom() {
        Random rnd = new Random();
        for (int i = 0; i < this.peticions.length; ++i) {
            int j = rnd.nextInt(this.S.fileLocations(this.R.getRequest(i)[1]).size());
            int s = (Integer) this.S.fileLocations(this.R.getRequest(i)[1]).toArray()[j];
            if (!this.servidors.containsKey(s)) this.servidors.put(s, new HashSet<Integer>());
            this.servidors.get(s).add(i);
            this.peticions[i] = s;
        }
        if (this.S.size() != this.servidors.size()) omplirServidors();
    }

    private void omplirServidors() {
        for (int i = 0; i < this.peticions.length; ++i) {
            int [] req = this.R.getRequest(i);
            for (int serv : this.S.fileLocations(req[1])) {
                if (!this.servidors.containsKey(serv)) this.servidors.put(serv, new HashSet<Integer>());
            }
        }
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
