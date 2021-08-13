package rmiBerkeley;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Cliente {
    public static List<RMIInterface> stubs = new ArrayList<RMIInterface>();

    static public void criarProcessos() {
        try {
            Registry registro = LocateRegistry.getRegistry("localhost", 12345);

            RMIInterface stub1 = (RMIInterface) registro.lookup("Processo1");
            RMIInterface stub2 = (RMIInterface) registro.lookup("Processo2");
            RMIInterface stub3 = (RMIInterface) registro.lookup("Processo3");
            RMIInterface stub4 = (RMIInterface) registro.lookup("Processo4");
            RMIInterface stub5 = (RMIInterface) registro.lookup("Processo5");
            RMIInterface stub6 = (RMIInterface) registro.lookup("Processo6");
            RMIInterface stub7 = (RMIInterface) registro.lookup("Processo7");
            RMIInterface stub8 = (RMIInterface) registro.lookup("Processo8");

            stub1.setIdentificador(1);
            stub1.setCoordenador(false);
            stub1.setDisponibilidade(true);
            stub1.setSucessor(stub2);
            stubs.add(stub1);

            stub2.setIdentificador(2);
            stub2.setCoordenador(false);
            stub2.setDisponibilidade(true);
            stub2.setSucessor(stub3);
            stubs.add(stub2);

            stub3.setIdentificador(3);
            stub3.setCoordenador(false);
            stub3.setDisponibilidade(true);
            stub3.setSucessor(stub4);
            stubs.add(stub3);

            stub4.setIdentificador(4);
            stub4.setCoordenador(false);
            stub4.setDisponibilidade(true);
            stub4.setSucessor(stub5);
            stubs.add(stub4);

            stub5.setIdentificador(5);
            stub5.setCoordenador(false);
            stub5.setDisponibilidade(true);
            stub5.setSucessor(stub6);
            stubs.add(stub5);

            stub6.setIdentificador(6);
            stub6.setCoordenador(false);
            stub6.setDisponibilidade(true);
            stub6.setSucessor(stub7);
            stubs.add(stub6);

            stub7.setIdentificador(7);
            stub7.setCoordenador(false);
            stub7.setDisponibilidade(true);
            stub7.setSucessor(stub8);
            stubs.add(stub7);

            stub8.setIdentificador(8);
            stub8.setCoordenador(false);
            stub8.setDisponibilidade(true);
            stub8.setSucessor(stub1);
            stubs.add(stub8);

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> gerarAleatorio(Integer coordenador) {
        Random gerador = new Random();

        Set<Integer> randomInts = new LinkedHashSet<Integer>();
        randomInts.add(coordenador);

        while(randomInts.size() < 3) {
            randomInts.add(gerador.nextInt(8));
        }

        List<Integer> randomList = new ArrayList<>(randomInts);

        return randomList;
    }

    public static List<Integer> eleicao(RMIInterface stub, List<Integer> randomList, RMIInterface coordenador) throws RemoteException {
        System.out.println("Coordenador caiu.");
        System.out.println("\n----------------------------------------------------------");
        System.out.println("Convocar eleição: " + stub.getIdentificador());

        List<Integer> ids = new ArrayList<Integer>();

        RMIInterface primeiroStub = stub;
        ids.add(primeiroStub.getIdentificador());
        RMIInterface stubAtual = primeiroStub.getSucessor();

        while (!stubAtual.getIdentificador().equals(primeiroStub.getIdentificador())) {
            if (stubAtual.getDisponibilidade()) {
                ids.add(stubAtual.getIdentificador());
            }
            stubAtual = stubAtual.getSucessor();
        }

        coordenador.setCoordenador(false);
        coordenador.setDisponibilidade(true);

        RMIInterface stubInativo = stubs.get(randomList.get(1));
        stubInativo.setDisponibilidade(true);
        stubInativo = stubs.get(randomList.get(2));
        stubInativo.setDisponibilidade(true);

        Integer novoCoordenadorId = Collections.max(ids);
        RMIInterface novoCoordenador = stubs.get(novoCoordenadorId - 1);
        novoCoordenador.setCoordenador(true);
        novoCoordenador.setDisponibilidade(false);

        System.out.println("Coordenador: " + novoCoordenadorId);


        try { Thread.sleep (1000); } catch (InterruptedException ex) {}

        List<Integer> randomList2 = gerarAleatorio(Collections.max(ids) - 1);

        try { Thread.sleep (700); } catch (InterruptedException ex) {}


        System.out.println("Processo inativo: " + (randomList2.get(1) + 1));
        System.out.println("Processo inativo: " + (randomList2.get(2) + 1));
        System.out.println("----------------------------------------------------------");

        try { Thread.sleep (800); } catch (InterruptedException ex) {}

        RMIInterface novoStubInativo = stubs.get(randomList2.get(1));
        novoStubInativo.setDisponibilidade(false);
        novoStubInativo = stubs.get(randomList2.get(2));
        novoStubInativo.setDisponibilidade(false);

        return randomList2;
    }


    public static void main(String[] args) throws RemoteException {
        criarProcessos();

        Random gerador = new Random();
        Integer coordeador = gerador.nextInt(8);

        List<Integer> randomList = gerarAleatorio(coordeador);

        System.out.println("Coordenador: " + (randomList.get(0) + 1));
        RMIInterface coordenador = stubs.get(randomList.get(0));
        coordenador.setCoordenador(true);
        coordenador.setDisponibilidade(false);

        System.out.println("Processo inativo: " +  (randomList.get(1) + 1));
        RMIInterface inativo1 = stubs.get(randomList.get(1));
        inativo1.setDisponibilidade(false);

        System.out.println("Processo inativo: " +  (randomList.get(2) + 1));
        RMIInterface inativo2 = stubs.get(randomList.get(2));
        inativo2.setDisponibilidade(false);
        System.out.println("----------------------------------------------------------");

        Integer execucoes = 12;
        Integer contador = 0;

        while (true) {
            for (RMIInterface stub : stubs) {
                if (stub.getDisponibilidade()) {
                    System.out.println("Processo " + (stub.getIdentificador()) + " executou.");
                    try { Thread.sleep (800); } catch (InterruptedException ex) {}
                    contador++;
                    if (contador == execucoes) {
                        randomList = eleicao(stub, randomList, stubs.get(randomList.get(0)));
                        contador = 0;
                    }
                }
            }
        }
    }
}
