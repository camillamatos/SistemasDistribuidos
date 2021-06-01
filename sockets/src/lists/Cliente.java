package lists;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	
	public static void main(String[] args) throws IOException{
		
		int porta = 1234;
		String ip = "127.0.0.1";
		Scanner scanner = new Scanner(System.in);
		
		Socket conexao = new Socket(ip, porta);
		System.out.println("Conectado ao servidor!");
		
		DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());
		DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
		
		while (true) {
			String message = fluxoEntrada.readUTF();
			System.out.println(message);
			
			if (message.equals("Encerrando conexão...")) {
				break;
			}

			fluxoSaida.writeUTF(scanner.nextLine());
			fluxoSaida.flush();
		}
		
		fluxoSaida.close();
		fluxoEntrada.close();
		conexao.close();
	}
}


