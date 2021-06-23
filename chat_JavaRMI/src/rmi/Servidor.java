package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Servidor{
	 public static Scanner scanner = new Scanner(System.in);
	 public static MensagemDistribuido stub;
	
	 public static void main(String[] args){
	        try {
	        	Mensagem m = new Mensagem();
	            System.setProperty("java.rmi.server.hostname","127.0.0.1");
	            
	            Registry registro = LocateRegistry.createRegistry(12345);
	            stub = (MensagemDistribuido) UnicastRemoteObject.exportObject(m, 0);
	            registro.bind("Chat", stub);
	            
	            System.out.println("Servidor rodando...");
	            
	            new Thread(thread_ler).start();
			    new Thread(thread_escrever).start();
	            
	        } catch (Exception e) {
	            System.out.println("Erro: "+e);        
	        }
	    }
	 
	 	private static Runnable thread_ler = new Runnable() {
	        public void run() {
	            try{
	            	int contadorU = stub.contarMensagens();
					while (true) {
						int contadorA = stub.contarMensagens();
						if (contadorU != contadorA) {
							System.out.println(stub.ler());
							contadorU = contadorA;
						}
						Thread.sleep(100);
					}
	            } catch (Exception e){
		            System.out.println(e);
	            }

	        }
	    };

	    private static Runnable thread_escrever = new Runnable() {
	        public void run() {
	            try{
	            	while(true) {
	            		stub.enviar("Servidor: " + scanner.nextLine());
	            	}
	            } catch (Exception e){}
	        }
	    };
	}