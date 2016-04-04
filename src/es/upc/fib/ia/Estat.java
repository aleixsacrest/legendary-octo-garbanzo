package es.upc.fib.ia;

import IA.DistFS.*;

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
    private double t_pitjor0; //valor pitjor de temps de pitjor servidor = valor inicial

    public Estat() {
        servidors = new HashMap<Integer, InfoServ>();
    }

    public Estat(Estat state) {
        //servidors = new HashMap<Integer, InfoServ>(state.getServidors());
        this.servidors = new HashMap<Integer, InfoServ>();
        for (int serv : state.getServidors().keySet()) {
            this.servidors.put(serv, new InfoServ(state.getServidors().get(serv)));
        }
        this.S = state.getS();
        this.R = state.getR();
        this.FdC0 = state.getFdC0();
        this.temps0 = state.getTemps0();
        this.t_pitjor0 = state.getTPijtor0();
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
            int s = -1;
            for (int candidat : this.S.fileLocations(pet[1])) {
                if (!this.servidors.containsKey(candidat)) this.servidors.put(candidat, new InfoServ(new HashSet<Integer>(), 0));
                int trans = this.S.tranmissionTime(candidat, pet[0]);
                if (min == -1 || trans < min) {
                    min = trans;
                    s = candidat;
                }
            }
            try {
                if (s == -1) throw new Exception("no s'ha trobat cap servidor");
                this.peticions[i] = s;
                this.servidors.get(s).p.add(i);
                this.servidors.get(s).temps += min; //TODO
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        FdC0 = factorDeCarrega();
        temps0 = tempsTransmissio();
        t_pitjor0 = getTempsPitjorServidor();
    }

    public void initEqCarrega() {
        Comparator<Integer[]> cmp = new ComparadorCarregar();
        //PriorityQueue<Integer[]> ocu = new PriorityQueue<Integer[]>(this.S.size(), cmp);
        ArrayList<Integer[]> ocupacio = new ArrayList<Integer[]>();
        omplirServidors();
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
        t_pitjor0 = getTempsPitjorServidor();
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
        t_pitjor0 = getTempsPitjorServidor();
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

    public double getTPijtor0() { return this.t_pitjor0; }

    public Set<Integer> getServidorsArxiu(int IDpet) { return this.S.fileLocations(this.R.getRequest(IDpet)[1]); }

    public void setTimeToMin() {
        for (int serv : this.servidors.keySet()) {
            this.servidors.get(serv).p = new HashSet<Integer>();
            this.servidors.get(serv).temps = 0;
        }
        for (int i = 0; i < this.peticions.length; ++i) {
            int[] r = this.R.getRequest(i);
            int minT = -1;
            int s = -1;
            for (int candidat : this.S.fileLocations(r[1])) {
                int t = this.S.tranmissionTime(candidat, r[0]);
                if (minT == -1 || t < minT) {
                    minT = t;
                    s = candidat;
                }
            }
            this.peticions[i] = s;
            this.servidors.get(s).p.add(i);
            this.servidors.get(s).temps += minT;
        }
    }

    public int getMinPosTime() {
        int retT = 0;
        for (int i = 0; i < this.peticions.length; ++i) {
            int[] r = this.R.getRequest(i);
            int minT = -1;
            for (int candidat : this.S.fileLocations(r[1])) {
                int t = this.S.tranmissionTime(candidat, r[0]);
                if (minT == -1 || t < minT) {
                    minT = t;
                }
            }
            retT += minT;
        }
        return retT;
    }

    public void canviarAssignacio(int IDpeticio, int IDnou) {
        int IDantic = this.peticions[IDpeticio];
        this.peticions[IDpeticio] = IDnou;
        this.servidors.get(IDantic).p.remove(IDpeticio);
        this.servidors.get(IDantic).temps -= this.S.tranmissionTime(IDantic, this.R.getRequest(IDpeticio)[0]);
        this.servidors.get(IDnou).p.add(IDpeticio);
        this.servidors.get(IDnou).temps += this.S.tranmissionTime(IDnou, this.R.getRequest(IDpeticio)[0]);
    }

    public void canviarAssignacioS(int servNou, int pet) {
        int[] req = this.R.getRequest(pet);
        int servAntic = this.peticions[pet];
        this.servidors.get(servNou).temps += this.S.tranmissionTime(servNou, req[0]);
        this.servidors.get(servNou).p.add(pet);
        this.servidors.get(servAntic).temps -= this.S.tranmissionTime(servAntic, req[0]);
        this.servidors.get(servAntic).p.remove(pet);
        this.peticions[pet] = servNou;
    }

    public void intercanviarAssignacions(int IDpet1, int IDpet2) {
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

    public double getSumaTemps() {
        double suma = 0.;
        for (int serv : this.servidors.keySet()) {
            suma += this.servidors.get(serv).temps;
        }
        return suma;
    }

    public double factorDeCarrega() {
        double ret = 0;
        //double avg = getAvg();
        double avg = 0;
        for (int serv : this.servidors.keySet()) {
            avg += this.servidors.get(serv).temps;
        }
        avg /= this.servidors.size();
        for (InfoServ inf : this.servidors.values()) {
            ret += Math.pow((inf.temps-avg), 2);
        }
        ret /= this.servidors.size();
        return ret;
    }

    // temps actual del pitjor servidor
    public double getAvgPitjorServidor() {
        double t = -1.;
        Integer id = 0;
        for (int s : this.servidors.keySet()) {
            if (this.servidors.get(s).p.size() != 0) {
                if (t == -1. || this.servidors.get(s).temps > t) {
                    t = this.servidors.get(s).temps / this.servidors.get(s).p.size();
                    id = s;
                }
            }
        }
        return t;
    }

    public double getTempsPitjorServidor() {
        double t = -1.;
        Integer id = 0;
        for (int s : this.servidors.keySet()) {
            if (this.servidors.get(s).p.size() != 0) {
                if (t == -1. || this.servidors.get(s).temps > t) {
                    t = this.servidors.get(s).temps / this.servidors.get(s).p.size();
                    id = s;
                }
            }
        }
        return this.servidors.get(id).temps;
    }

    public double tempsTransmissio() {
        double ret = 0;
        for (InfoServ inf : this.servidors.values()) {
            ret += inf.temps;
        }
        return ret;
    }

    @Override
    public boolean equals(Object e) {
        Estat other = (Estat) e;
        if (this.servidors.size() != other.getServidors().size()) return false;
        for (int k : this.servidors.keySet()) {
            if (!other.getServidors().containsKey(k)) return false;
            if (!this.servidors.get(k).equals(other.getServidors().get(k))) return false;
        }
        for (int i = 0; i < this.peticions.length; ++i)
            if (this.peticions[i] != other.getPeticions()[i]) return false;
        return true;
    }

    public boolean intercanviPossible(int IDpet1, int IDpet2)
    {
        Set<Integer> fl = S.fileLocations(IDpet1);
        for (Integer i : fl) {
            if (i == peticions[IDpet2])
                return true;
        }
        return false;
    }

    public String toString() {
        String ret = "";
        /*for (int s : this.servidors.keySet()) {
            ret += s + " " + this.servidors.get(s).p.size() + '\n';
        }*/
        ret += "\nfactor de carrega: " + this.factorDeCarrega();
        ret += "\ntemps pitjor servidor: " + this.getTempsPitjorServidor();
        ret += "\ntemps de transmissio: " + this.tempsTransmissio();
        ret += "\nvalor de l'heuristic1: " + (new DistFSHeuristicFunction1()).getHeuristicValue(this);
        ret += "\nvalor de l'heuristic2: " + (new DistFSHeuristicFunction2()).getHeuristicValue(this);
        ret += "\n;" + (new DistFSHeuristicFunction1()).getHeuristicValue(this);
        return ret;
    }
}
