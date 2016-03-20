package es.upc.fib.ia;

import IA.DistFS.*;
import com.sun.xml.internal.bind.v2.model.core.ID;
import com.sun.xml.internal.fastinfoset.algorithm.IntegerEncodingAlgorithm;

import java.util.*;

/**
 * Created by aleixsacrest on 08/03/2016.
 */
public class Estat {
    private HashMap<Integer, InfoServ> servidors;
    private int[] peticions;
    private Servers S;
    private Requests R;

    private double FdC0;    //valor pitjor de FdC = valor inicial
    private double temps0;  //valor pitjor de temps = valor inicial

    public Estat() {
        servidors = new HashMap<Integer, InfoServ>();
    }

    public Estat(Estat state) {
        servidors = new HashMap<Integer, InfoServ>(state.getServidors());
        this.S = state.getS();
        this.R = state.getR();
        this.FdC0 = state.getFdC0();
        this.temps0 = state.getTemps0();
        this.peticions = new int[this.R.size()];
        for (int i = 0; i < peticions.length; ++i)
            this.peticions[i] = state.getPeticions()[i];
    }

    public Estat(Servers S, Requests R) {
        servidors = new HashMap<Integer, InfoServ>();
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
                if (!this.servidors.containsKey(candidat)) this.servidors.put(candidat, new InfoServ(new HashSet<Integer>(), 0));
                int trans = this.S.tranmissionTime(candidat, pet[0]);
                if (min == -1 || trans < min) {
                    min = trans;
                    s = candidat;
                }
            }
            try {
                if (s == 0) throw new Exception("no s'ha trobat cap servidor");
                this.peticions[i] = s;
                this.servidors.get(s).p.add(i);
                this.servidors.get(s).temps += min;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        FdC0 = factorDeCarrega();
        temps0 = tempsTransmissio();
    }

    public void initEqCarrega() {
        Comparator<Integer[]> cmp = new ComparadorCarregar();
        //PriorityQueue<Integer[]> ocu = new PriorityQueue<Integer[]>(this.S.size(), cmp);
        ArrayList<Integer[]> ocupacio = new ArrayList<Integer[]>();
        omplirServidors();
        //TODO: fer llista amb temps
        for (int serv : this.servidors.keySet()) {
            Integer[] o = {serv, this.servidors.get(serv).temps};
            //ocu.add(o);
            ocupacio.add(o);
        }
        for (int i = 0; i < this.peticions.length; ++i) {
            for (Integer[] ii : ocupacio) {
                if (this.S.fileLocations(this.R.getRequest(i)[1]).contains(ii[0])) {
                    Integer ttemps = this.S.tranmissionTime(ii[0], this.R.getRequest(i)[0]);
                    ii[1] += ttemps;
                    this.servidors.get(ii[0]).p.add(i);
                    this.servidors.get(ii[0]).temps += ttemps;
                    this.peticions[i] = ii[0];
                    break;
                }
            }
            Collections.sort(ocupacio, cmp);
        }
        FdC0 = factorDeCarrega();
        temps0 = tempsTransmissio();
    }

    public void initRandom() {
        Random rnd = new Random();
        for (int i = 0; i < this.peticions.length; ++i) {
            int j = rnd.nextInt(this.S.fileLocations(this.R.getRequest(i)[1]).size());
            int loc = (Integer) this.S.fileLocations(this.R.getRequest(i)[1]).toArray()[j];
            if (!this.servidors.containsKey(loc)) this.servidors.put(loc, new InfoServ(new HashSet<Integer>(), 0));
            this.servidors.get(loc).p.add(i);
            this.servidors.get(loc).temps += this.S.tranmissionTime(loc, this.R.getRequest(i)[0]);
            this.peticions[i] = loc;
        }
        if (this.S.size() != this.servidors.size()) omplirServidors();
        FdC0 = factorDeCarrega();
        temps0 = tempsTransmissio();
    }

    private void omplirServidors() {
        for (int i = 0; i < this.peticions.length; ++i) {
            int [] req = this.R.getRequest(i);
            for (int serv : this.S.fileLocations(req[1])) {
                if (!this.servidors.containsKey(serv)) this.servidors.put(serv, new InfoServ(new HashSet<Integer>(), 0));
            }
        }
    }

    public void computeTemps() {
        for (int serv : this.servidors.keySet()) {
            this.servidors.get(serv).temps = 0;
            for (int p : this.servidors.get(serv).p) {
                this.servidors.get(serv).temps += this.S.tranmissionTime(serv, this.R.getRequest(p)[0]);
            }
        }
    }

    public void setServidors(HashMap<Integer, InfoServ> s) {
        this.servidors = s;
    }

    public HashMap<Integer, InfoServ> getServidors() {
        return this.servidors;
    }

    public void setPeticions(int[] p) {
        this.peticions = p;
    }

    public int[] getPeticions() {
        return this.peticions;
    }

    public HashSet<Integer> getAssignacions(int IDserv) {
        return this.servidors.get(IDserv).p;
    }

    public int getServei(int IDpet) {
        return this.peticions[IDpet];
    }

    public void setS (Servers S) { this.S = S; }

    public Servers getS () { return this.S; }

    public void setR (Requests R) { this.R = R; }

    public Requests getR () { return this.R; }

    public double getFdC0() { return this.FdC0; }

    public double getTemps0() { return this.temps0; }

    public Set<Integer> servidorsArxiu(int IDArxiu) { return this.S.fileLocations(IDArxiu); }

    public void canviarAssignacio(int IDpeticio, int IDnou) {
        //TODO: restar transmissio temps a actual i sumarili a la nova
        int IDantic = this.peticions[IDpeticio];
        this.servidors.get(IDantic).p.remove(IDpeticio);
        this.servidors.get(IDantic).temps -= this.S.tranmissionTime(IDantic, this.R.getRequest(IDpeticio)[0]);
        this.servidors.get(IDnou).p.add(IDpeticio);
        this.servidors.get(IDnou).temps += this.S.tranmissionTime(IDnou, this.R.getRequest(IDpeticio)[0]);
    }

    public void intervanviarAssignacions(int IDpet1, int IDpet2) {
        //TODO: intercanviar temps de transmissio
        int IDserv1 = peticions[IDpet1];
        int IDserv2 = peticions[IDpet2];
        this.peticions[IDpet1] = IDserv2;
        this.peticions[IDpet2] = IDserv1;
        this.servidors.get(IDserv1).p.remove(IDpet1);
        this.servidors.get(IDserv1).p.add(IDpet2);
        this.servidors.get(IDserv2).p.remove(IDpet2);
        this.servidors.get(IDserv2).p.add(IDpet1);

        this.servidors.get(IDserv1).temps -= this.S.tranmissionTime(IDserv1, this.R.getRequest(IDpet1)[0]);
        this.servidors.get(IDserv2).temps += this.S.tranmissionTime(IDserv2, this.R.getRequest(IDpet1)[0]);
        this.servidors.get(IDserv2).temps -= this.S.tranmissionTime(IDserv2, this.R.getRequest(IDpet2)[0]);
        this.servidors.get(IDserv1).temps += this.S.tranmissionTime(IDserv1, this.R.getRequest(IDpet2)[0]);
    }

    public double getAvg(){
        double avg = 0.;
        for (int serv : this.servidors.keySet()) {
            avg += this.servidors.get(serv).temps;
        }
        avg /= this.servidors.size();
        return avg;
    }

    //TODO: classe Estat / HeuristicFunction
    public double factorDeCarrega() {
        double ret = 0;
        double avg = getAvg();
        /*for (int serv : this.servidors.keySet()) {
            avg += this.servidors.get(serv).temps;
        }
        avg /= this.servidors.size();*/
        for (InfoServ inf : this.servidors.values()) {
            ret += Math.pow((inf.temps-avg), 2);
        }
        ret /= this.servidors.size();
        return ret;
    }

    public double getTempsPitjorSevidor() {
        double temps = 0.;
        for (int s : this.servidors.keySet()) {
            if (temps < this.servidors.get(s).temps)
                temps = this.servidors.get(s).temps;
        }
        return temps;
    }

    public double tempsTransmissio() {
        double ret = 0;
        for (InfoServ inf : this.servidors.values()) {
            ret += inf.temps;
        }
        return ret;
    }
}
