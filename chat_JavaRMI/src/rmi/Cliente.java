package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente{
	public static Scanner scanner = new Scanner(System.in);
	public static MensagemDistribuido stub;
	
	  public static void main(String[] args){
	    try {
	    	Registry registro = LocateRegistry.getRegistry("127.0.0.1", 12345);
	    	stub = (MensagemDistribuido) registro.lookup("Chat");
	    	
	    	new Thread(thread_ler).start();
		    new Thread(thread_escrever).start();
		    
	    } catch (Exception e) {
	      System.err.println("erro: " + e.toString());
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
	            		stub.enviar("Cliente: " + scanner.nextLine());
	            	}
	            } catch (Exception e){}
	        }
	    };
	}