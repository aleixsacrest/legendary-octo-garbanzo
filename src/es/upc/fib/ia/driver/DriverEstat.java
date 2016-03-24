package es.upc.fib.ia.driver;

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import es.upc.fib.ia.Estat;
import es.upc.fib.ia.InfoServ;

import javax.sound.midi.MidiDevice;
import java.util.HashMap;

/**
 * Created by aleixsacrest on 16/03/2016.
 */
public class DriverEstat {
    public static void main(String[] args) {
        try {
            Requests req = new Requests(200, 5, 1);
            Servers serv = new Servers(50, 5, 1);
            Estat e = new Estat(serv, req);
            e.initMinTemps();
            System.out.println("fin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
