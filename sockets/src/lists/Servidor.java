package lists;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	static HashMap<String, ArrayList<String>> lists = new HashMap<String, ArrayList<String>>();
	static String lastValue = null;

	public static String getLists () {
		String value = "\nLISTAS:\n";
		for (String key : lists.keySet()) {
			value += key + "\n";
			for (String element: lists.get(key)) {
		      value += "- " + element + "\n";
		    }
			value += "\n\n";
		}
		return value;
	}

	public static void add (String listName, String item) {
		ArrayList<String> list = lists.get(listName);
		if (list == null) {
			return;
		}

		lists.get(listName).add(item);
		lastValue = item;
	}

	public static void remove (String listName, String item) {
		ArrayList<String> list = lists.get(listName);
		if (list == null) {
			return;
		}
		lists.get(listName).remove(item);
	}
	 
	public static String showMenu () {
		String menu = "\n\n1) Imprimir listas\n"
				+ "2) Inserir em uma lista\n"
				+ "3) Criar lista\n"
				+ "4) Imprimir último valor adicionado\n"
				+ "5) Remover elemento de uma lista\n"
				+ "6) Fechar conexão\n";
		return menu;
	}
	
	public static void main(String[] args) throws IOException{
		ServerSocket servidor = new ServerSocket(1234);
		int op = -1;
		
		System.out.println("Aguardando por conexões");
		Socket conexao = servidor.accept();
		System.out.println("Cliente conectou" + conexao);
		
		DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
		DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());
		
		String message;
		String name;
		String element;
		
		while (true) {
			switch(op) {
				case 1: 
					fluxoSaida.writeUTF(getLists() + showMenu());
					break;
				case 2:
					fluxoSaida.writeUTF("Digite o nome de uma lista existente");
					name = fluxoEntrada.readUTF();
					
					fluxoSaida.writeUTF("Digite o nome do elemento a ser inserido");
					element = fluxoEntrada.readUTF();
					
					add(name, element);
					fluxoSaida.writeUTF(showMenu());
					break;
				case 3:
					fluxoSaida.writeUTF("Coloque o nome da nova lista");
					name = fluxoEntrada.readUTF();
					
					lists.put(name, new ArrayList<String>());
					fluxoSaida.writeUTF(showMenu());
					break;
				case 4:
					fluxoSaida.writeUTF(lastValue + showMenu());
					break;
				case 5:
					fluxoSaida.writeUTF("Digite o nome de uma lista existente");
					name = fluxoEntrada.readUTF();
					
					fluxoSaida.writeUTF("Digite o nome do elemento a ser deletado");
					element = fluxoEntrada.readUTF();
					
					remove(name, element);
					fluxoSaida.writeUTF(showMenu());
					break;
				case 6:
					fluxoSaida.writeUTF("Encerrando conexão...");
				default:
					fluxoSaida.writeUTF(showMenu());
					break;
			}
			
			if(op == 6) {
				fluxoEntrada.close();
				fluxoSaida.close();
				conexao.close();
				servidor.close();
				break;
			}
			
			message = fluxoEntrada.readUTF();
			op = Integer.parseInt(message);
			name = "";
			element = "";
		}
	}
}