package rmiBerkeley;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor {
    public static void main(String[] args) {
        try {
            Processo processo1 = new Processo();
            Processo processo2 = new Processo();
            Processo processo3 = new Processo();
            Processo processo4 = new Processo();
            Processo processo5 = new Processo();
            Processo processo6 = new Processo();
            Processo processo7 = new Processo();
            Processo processo8 = new Processo();

            System.setProperty("java.rmi.server.hostname","localhost");
            Registry registro = LocateRegistry.createRegistry(12345);

            RMIInterface stub1 = (RMIInterface) UnicastRemoteObject.exportObject(processo1, 0);
            RMIInterface stub2 = (RMIInterface) UnicastRemoteObject.exportObject(processo2, 0);
            RMIInterface stub3 = (RMIInterface) UnicastRemoteObject.exportObject(processo3, 0);
            RMIInterface stub4 = (RMIInterface) UnicastRemoteObject.exportObject(processo4, 0);
            RMIInterface stub5 = (RMIInterface) UnicastRemoteObject.exportObject(processo5, 0);
            RMIInterface stub6 = (RMIInterface) UnicastRemoteObject.exportObject(processo6, 0);
            RMIInterface stub7 = (RMIInterface) UnicastRemoteObject.exportObject(processo7, 0);
            RMIInterface stub8 = (RMIInterface) UnicastRemoteObject.exportObject(processo8, 0);

            registro.bind("Processo1", stub1);
            registro.bind("Processo2", stub2);
            registro.bind("Processo3", stub3);
            registro.bind("Processo4", stub4);
            registro.bind("Processo5", stub5);
            registro.bind("Processo6", stub6);
            registro.bind("Processo7", stub7);
            registro.bind("Processo8", stub8);

            System.out.println("Servidor aguardando execuções...");
        } catch (Exception e) {
            System.err.println("erro: " + e.toString());
        }
    }
}
